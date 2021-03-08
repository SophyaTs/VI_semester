import matplotlib.pyplot as plt
from matplotlib.collections import LineCollection
import math
from enum import Enum

class Location(Enum):
    LEFT = 0
    RIGHT = 1
    BELONGS = 2
    OUTSIDE = 3
    LOWER = 4
    HIGHER = 5

class Edge:
    def __init__(self, pfrom, pto) -> None:
        self.pfrom = pfrom
        self.pto = pto
        self.weight = 1
        self.rotation = math.atan2(pto.y - pfrom.y, pto.x - pfrom.x)
        self.horizontal = True if self.pfrom.y == self.pto.y else False
        
        # linked list of edges which have same pto
        self.next_in = None       

    def __gt__(self, o: object) -> bool:
        if self.pfrom == o.pfrom:
            return self.rotation < o.rotation
        elif self.pto == o.pto:
            return self.rotation > o.rotation
        else:
            y_low = self.pto.y if self.pto.y < o.pto.y else o.pto.y
            xself = self.horizontal_cross(y_low)
            xo = o.horizontal_cross(y_low)
            return True if xself > xo else False

    def __repr__(self) -> str:
        return "<" + str(self.pfrom) + ", " + str(self.pto) + ">"

    def horizontal_cross(self,y) -> float:
        x1 = self.pfrom.x
        y1 = self.pfrom.y
        x2 = self.pto.x
        y2 = self.pto.y
        return (x2*y1 - x1*y2 + y*(x1 - x2))/(y1 - y2)

    # define point location relatively to an edge
    def check_side(self,xch,ych) -> Location:
        x1 = self.pfrom.x
        y1 = self.pfrom.y
        x2 = self.pto.x
        y2 = self.pto.y
        n = (y1 - y2) * xch + (x2 - x1) * ych + (x1 * y2 - x2 * y1)
        return Location.BELONGS if abs(n) < 10e-3 else Location.RIGHT if n < 0 else Location.LEFT

class Vertex:
    number = 0
    def __init__(self, x, y) -> None:
        Vertex.number+=1
        self.n = Vertex.number        
        self.x = float(x)
        self.y = float(y)
        
        # list of edges which start from this vertex (have same pfrom == this vertex) 
        self.edges_out = []
        # pointer to linked list of edges which end in this vertex (have same pto == this vertex)
        self.edges_in = None

    def __gt__(self, o: object) -> bool:
        if self.y > o.y:
            return True
        elif self.y == o.y and self.x > o.x:
            return True
        else:
            return False

    def __eq__(self, o: object) -> bool:
        if self.x == o.x and self.y == o.y:
            return True
        return False

    def __repr__(self) -> str:
        return str(self.n)

    def add_edge_in(self, pto):
        edge = Edge(self,pto)
        self.edges_out.append(edge)
        edge.next_in = pto.edges_in
        pto.edges_in = edge

    def add_edge(self, other) -> None:
        if self < other:
            self.add_edge_in(other)
        else:
            other.add_edge_in(self)

class Slab:
    def __init__(self, y) -> None:
        self.highest_y = y
        self.current = []
        """ self.inserted = []
        self.deleted = [] """
    
    def insert_edge(self, edge: Edge) -> None:
        self.current.append(edge)

    def delete_edge(self, edge: Edge):
        self.current.remove(edge)

    def sort_current(self) -> None:
        self.current.sort()

    def set_current(self, current) -> None:
        self.current = current.copy()

    # check if point belongs to lower or higher half plane relatively to y = slab.highest_y
    def hlf_plane(self, y) -> Location:
        return Location.LOWER if y < self.highest_y else Location.HIGHER

    # binary search in current edges
    def bs(self,x,y,left,right):
        middle = int((left+right)/2)
        side = self.current[middle].check_side(x,y)
        if side == Location.BELONGS:
            return (side, self.current[middle])
        
        if left == right:
            return (side, self.current[middle])
        elif side == Location.LEFT:
            return self.bs(x,y,left,middle)
        else:
            return self.bs(x,y,middle+1,right)

    def locate_point(self, x, y):
        if len(self.current) != 0:
            return self.bs(x,y,0,len(self.current)-1)
        return Location.OUTSIDE, None


def create_slabs(vertexes = []):
    slabs = []
    slabs.append(Slab(vertexes[0].y))
    for v in vertexes:
        if v.y > slabs[-1].highest_y:
            slabs.append(Slab(v.y))
    slabs.append(Slab(float("inf")))

    i = 1
    for v in vertexes:
        # check if we should move on to the next slab
        if slabs[i].highest_y == v.y:
            slabs[i].sort_current()
            i += 1
            slabs[i].set_current(slabs[i-1].current)

        # correct current edges of slab
        e = v.edges_in
        while e != None:
            if not e.horizontal:
                slabs[i].delete_edge(e)
            e = e.next_in

        for e in v.edges_out:
            if not e.horizontal:
                slabs[i].insert_edge(e)
    
    return slabs

# binary search in slabs
def bs_slabs(x, y, left, right, slabs = []):
    middle = int((left+right)/2)
    side = slabs[middle].hlf_plane(y)

    if left == right:
        return middle, slabs[middle].locate_point(x,y)
    elif side == Location.LOWER:
        return bs_slabs(x,y,left,middle,slabs)
    else:
        return bs_slabs(x,y,middle+1,right,slabs)

# finds slab, the closest edge and specifies a point location relatively to that edge 
# (is point on the left or on ther right from that edge)
def locate_point(slabs,x,y):
    slab, (loc, edge) = bs_slabs(x,y,0,len(slabs)-1,slabs)
    print("Slab:",slab,',',edge,loc)


def draw_graph(x,y, vertexes = [], slabs = []):
        lines = []
        fig, ax = plt.subplots()

        minx = vertexes[0].x
        maxx = vertexes[0].x

        for v in vertexes:
            plt.text(v.x-0.2, v.y, v.n)
            if v.x > maxx:
                maxx = v.x
            if v.x < minx:
                minx = v.x
            for e in v.edges_out:
                lines.append([(e.pfrom.x,e.pfrom.y),(e.pto.x,e.pto.y)])       
        lc = LineCollection(lines, linewidths = 2, color = 'k')
        ax.add_collection(lc)

        for i in range(len(slabs)-1):
            ylevel = slabs[i].highest_y
            plt.text(minx-1, ylevel - 0.25, i, color = 'r')
            lines.clear()
            lines.append([(minx-1,ylevel),(maxx+1,ylevel)])
            sl = LineCollection(lines, linewidths = 1, color = 'r')
            ax.add_collection(sl)
        
        plt.plot(x,y,'bo') 

        ax.autoscale()
    
        plt.show()

def readGraph():
    fv = open("vertexes.txt")
    vertexes = []
    for line in fv.readlines():
        coordinates = [float(i) for i in line.split(" ")]
        vertexes.append(Vertex(coordinates[0], coordinates[1]))
    fv.close()

    fe = open("edges.txt")
    for line in fe.readlines():
        numbers = [int(i) for i in line.split(" ")]
        vertexes[numbers[0]-1].add_edge(vertexes[numbers[1]-1])
    fe.close()
   
    vertexes.sort()
    for v in vertexes:
        v.edges_out.sort()
    return vertexes


vertexes = readGraph()
slabs = create_slabs(vertexes)
x = 3.5
y = 7
locate_point(slabs,x,y)

""" for s in slabs:
    ce = ''
    for e in s.current:
        ce += str(e) + ' '
    print(ce) """

draw_graph(x,y,vertexes,slabs)