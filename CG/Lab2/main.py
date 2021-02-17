import matplotlib.pyplot as plt
from matplotlib.collections import LineCollection
import math

class Edge:
    def __init__(self, pfrom, pto) -> None:
        self.pfrom = pfrom
        self.pto = pto
        self.weight = 1
        self.next_in = None
        self.rotation = math.atan2(pto.y - pfrom.y, pto.x - pfrom.x)

    def __gt__(self, o: object) -> bool:
        return self.rotation < o.rotation

    def __repr__(self) -> str:
        return "<" + str(self.pfrom) + ", " + str(self.pto) + ">" + " w = " + str(self.weight)

class Vertex:
    number = 0

    def __init__(self, x, y) -> None:
        Vertex.number+=1
        self.n = Vertex.number        
        self.x = float(x)
        self.y = float(y)
        self.edges_out = []
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

    def add_edge(self, pto) -> None:
        edge = Edge(self,pto)
        self.edges_out.append(edge)
        edge.next_in = pto.edges_in
        pto.edges_in = edge

    def wIn(self) -> int:
        weight = 0
        edge = self.edges_in
        while edge != None:
            weight += edge.weight
            edge = edge.next_in
        return weight

    def wOut(self) -> int:
        weight = 0
        for edge in self.edges_out:
            weight += edge.weight
        return weight  

    def leftmost_out_edge(self) -> Edge:
        return self.edges_out[0]

    def leftmost_in_edge(self) -> Edge:
        edge = answer = self.edges_in
        while edge != None:
            if answer > edge:
                answer = edge
            edge = edge.next_in
        return answer

    def leftmost_unused(self) -> Edge:
        for edge in self.edges_out:
            if edge.weight > 0:
                return edge
        return None


def balance(vertexes = []):
        # sort edges from the most left edge to the most right
        for v in vertexes:
            v.edges_out.sort()

        # the first and the last vetrexes are exceptions and do not require balancing
        # first iterartion: for every vertex make wOut >= wIn, iterate from v1 to vn 
        for i in range(1,len(vertexes)-1):
            wIn = vertexes[i].wIn()
            vOut = len(vertexes[i].edges_out)
            
            # d1 - leftmost edge which goes out from current vertex
            d1 = vertexes[i].leftmost_out_edge()

            # if there is more edges in than out, it means that several chains will
            # collide and contain the same out-edge 
            if wIn > vOut:
                 d1.weight = wIn - vOut + 1

        # second iterartion: for every vertex make wOut <= wIn, iterate from vn to v1
        # after these iterations wOut = wIn
        for i in range(len(vertexes)-1, 1, -1):
            wIn = vertexes[i].wIn()
            wOut = vertexes[i].wOut()

            # d2 - leftmost edge which goes into current vertex
            d2 = vertexes[i].leftmost_in_edge()
            
            # if there is more edges/chains out than in, it means that several chains will
            # disperse, coming from the same in-edge 
            if wOut > wIn:
                d2.weight = wOut - wIn + d2.weight

def create_chains(vertexes = []):
        balance(vertexes)

        # because weight of the edge means how many chains will contain this edge,
        # if we sum up weights of all edges from starting point
        # than we'll know how many chains there will be
        total_chains = vertexes[0].wOut()
        chains = [[] for i in range(total_chains)]
        for chain in chains:
            # every chain starts at the lowest (by y) point 
            v = vertexes[0]
            chain.append(v) 

            # building chain while we don't reach the last (highest by y) point         
            while v != vertexes[len(vertexes)-1]:               
                # building chains from left to right
                # we wouldn't move on to the next (right-er) edge 
                # until we've built all chains containing left edge
                e = v.leftmost_unused()
                
                # decrease weight so that we know that one more chain 
                # with this edge was built
                e.weight -= 1

                # add the next vertex to the chain
                v = e.pto
                chain.append(v)
        return chains

LEFT = 1
RIGHT = 2
OUT = 0
INSIDE = 4
BELONGS = 3

# utility function: binary search within single chain
def check_chain(x,y, chain, left, right) -> int:
    middle = int((left+right)/2)
    if left == right:
        # first greater or equal by y-coordinate vertex is the first one
        if left == 0:
            # dot is lower than the first vertex, therefore out of the graph completely
            if y < chain[0].y:
                return OUT
            
            # else dot is on the same line as first vertex
            # there are 3 cases
            if x < chain[0].x:
                return LEFT
            elif x > chain[0].x:
                return RIGHT
            else:
                return BELONGS

        # first greater or equal by y-coordinate vertex is the last one
        # so it's possible that dot is higher than entire graph
        if left == len(chain)-1 and y > chain[len(chain)-1].y:
            # dot is higher than the last vertex, therefore out of the graph completely
            if y > chain[len(chain)-1].y:
                return OUT
            
            # else dot is on the same line as last vertex
            if x < chain[len(chain)-1].x:
                return LEFT
            elif x > chain[len(chain)-1].x:
                return RIGHT
            else:
                return BELONGS

        # dot is somewhere in the middle of the chain
        prev = left -1
        while chain[prev].y == chain[left].y:
            prev -= 1
        return check_side(x,y,chain[prev],chain[left])

    elif y > chain[middle].y:
        return check_chain(x, y, chain, middle + 1, right)
    elif y <= chain[middle].y:
        return check_chain(x,y,chain, left, middle)

# utility function: define dot position relatively to edge <v1, v2>
def check_side(xch,ych,v1,v2) -> int:
    x1 = v1.x
    y1 = v1.y
    x2 = v2.x
    y2 = v2.y
    n = (y1 - y2) * xch + (x2 - x1) * ych + (x1 * y2 - x2 * y1)
    return BELONGS if n == 0 else RIGHT if n < 0 else LEFT 

# utility function: binary search within all chains          
def check_all_chains(x,y, chains, left, right):
    middle = int((left+right)/2)
    side = check_chain(x,y,chains[middle],0,len(chains[middle])-1)

    # maybe we can already tell that dot is outside of graph
    # or it happens to be a vertex or lay exactly on the edge
    if side == OUT:
        return OUT, -1
    if side == BELONGS:
        return BELONGS, middle

    if left == right:
        # if we are checking the leftmost and rightmost edges,
        # dot can actually be on the wrong sides
        if left == 0:
            if side == RIGHT:
                return INSIDE, left
            else:
                return OUT, -1
        if left == len(chains)-1:
            if side == LEFT:
                return INSIDE, left
            else:
                return OUT, -1

        # regular case, dot is between middle chains
        return INSIDE, left

    elif side == LEFT:
        return check_all_chains(x,y,chains,left,middle)
    else:
        return check_all_chains(x,y,chains,middle+1,right)

def locate_point(x,y,chains, vertexes):
    location, chain_number = check_all_chains(x,y,chains,0,len(chains)-1)
    if location == OUT:
        print("Dot is out of Graph")
        draw_graph(x,y,vertexes,[])
    elif location == BELONGS:
        print("Dot belongs to chain",chain_number)
        draw_graph(x,y,vertexes,[chains[chain_number]])
    else:
        print("Dot is located between chain",chain_number-1,"and chain",chain_number)
        draw_graph(x,y,vertexes,[chains[chain_number-1],chains[chain_number]])
        

def draw_graph(x,y, vertexes = [], chains = []):
        lines = []
        fig, ax = plt.subplots()

        for v in vertexes:
            for e in v.edges_out:
                lines.append([(e.pfrom.x,e.pfrom.y),(e.pto.x,e.pto.y)])       
        lc = LineCollection(lines, linewidths = 1, color = 'k')
        ax.add_collection(lc)

        col = "r"
        for c in chains:
            lines.clear()
            for i in range(len(c)-1):
                lines.append([(c[i].x,c[i].y),(c[i+1].x,c[i+1].y)])
            ch = LineCollection(lines, linewidths = 2, color = col)
            col = "g"
            ax.add_collection(ch)
        
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
        if vertexes[numbers[1]-1] > vertexes[numbers[0]-1]:
            vertexes[numbers[0]-1].add_edge(vertexes[numbers[1]-1])
        else:
            vertexes[numbers[1]-1].add_edge(vertexes[numbers[0]-1])
    fe.close()

    vertexes.sort()
    return vertexes


vertexes = readGraph()
chains = create_chains(vertexes)

for c in chains:
    line = ""
    for v in c:
        line += str(v)+" "
    print(line)

x = 0
y = 0
locate_point(x,y,chains, vertexes)