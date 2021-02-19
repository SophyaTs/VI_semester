import matplotlib.pyplot as plt
from matplotlib.collections import LineCollection
import math

class Edge:
    def __init__(self, pfrom, pto) -> None:
        self.pfrom = pfrom
        self.pto = pto
        self.weight = 1
        self.rotation = math.atan2(pto.y - pfrom.y, pto.x - pfrom.x)
        
        # linked list of edges which have same pto
        self.next_in = None       

    def __gt__(self, o: object) -> bool:
        if self.pfrom == o.pfrom:
            return self.rotation < o.rotation
        else:
            return self.rotation > o.rotation

    def __repr__(self) -> str:
        return "<" + str(self.pfrom) + ", " + str(self.pto) + ">" + " w = " + str(self.weight)

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

def calculate_cross(edge, y) -> float:
    x1 = edge.pfrom.x
    y1 = edge.pfrom.y
    x2 = edge.pto.x
    y2 = edge.pto.y
    return (x2*y1 - x1*y2 + y*(x1 - x2))/(y1 - y2)

# utility function: deletes unnecessary edges OR adds necessary edge
def check_vertex(vertex, current_edges, current_vertexes, top_to_bottom = False) -> int:
        j = 0

        # vertex is regular       
        if (not top_to_bottom and vertex.edges_in != None) or (top_to_bottom and len(vertex.edges_out) > 0):
            # find edges which end in this vertex           
            while j < len(current_edges):
                if ((not top_to_bottom and current_edges[j].pto == vertex) or 
                    (top_to_bottom and current_edges[j].pfrom == vertex)):
                    break
                j+=1
            
            # remove them from status lists
            while j < len(current_edges) and ((not top_to_bottom and current_edges[j].pto == vertex) or 
                                            (top_to_bottom and current_edges[j].pfrom == vertex)):
                current_edges.remove(current_edges[j])
                current_vertexes.remove(current_vertexes[j])
                j += 1
            current_vertexes[j-1] = vertex
        
        # vertex is irregular, requires fixing
        else:
            # locate vertex between current edges
            while j < len(current_edges):
                if calculate_cross(current_edges[j], vertex.y) > vertex.x:
                    break
                j += 1
            # add edge to vertex (regularize vertex)
            current_vertexes[j].add_edge(vertex)

        return j

def regularize(vertexes = []):
    # sort edges from the leftmost edge to the rightmost
    for v in vertexes:
        v.edges_out.sort()
    
    # ============================================================ STATUS LISTS =====================================================================
    # current_edges:                                    E1                               E2                             E3
    # 
    # current_vertexes: V1 (closest to E1 on the left)     V2 (closest between E1 E2)        V3 (closest between E2 E3)    V4 (closest to E3 on the right)
    current_edges = []
    current_vertexes = []

    # from bottom to top
    # init status lists with edges from the first vertex
    for e in vertexes[0].edges_out:
        current_edges.append(e)
        current_vertexes.append(vertexes[0])
    current_vertexes.append(vertexes[0])

    for i in range(1,len(vertexes)-1): 
        j = check_vertex(vertexes[i],current_edges, current_vertexes)
        
        # get left unchanges part of status lists
        tmp_edges = current_edges[0:j]
        tmp_vertexes = current_vertexes[0:j]
        
        # add new edges to status lists
        for e in vertexes[i].edges_out:
            tmp_edges.append(e)
            tmp_vertexes.append(vertexes[i])
        tmp_vertexes.append(vertexes[i])

        # get right unchanged part of status lists  
        if (j < len(current_edges)):
            tmp_edges += current_edges[j:len(current_edges)]
            tmp_vertexes += current_vertexes[j+1:len(current_vertexes)]

        current_edges = tmp_edges.copy()
        current_vertexes = tmp_vertexes.copy()
    
    # from top to bottom
    # init status lists with edges from the last vertex
    current_edges.clear()
    current_vertexes.clear()
    
    edge = vertexes[-1].edges_in
    while edge != None:
        current_edges.append(edge)
        edge = edge.next_in
        current_vertexes.append(vertexes[-1])
    current_vertexes.append(vertexes[-1])
    current_edges.sort()

    for i in range (len(vertexes)-2, 0, -1):
        j = check_vertex(vertexes[i],current_edges, current_vertexes, True)

        # get left unchanges part of status lists
        tmp_edges = current_edges[0:j]
        tmp_vertexes = current_vertexes[0:j]

        # add new edges to status lists
        edges_in = []
        edge = vertexes[i].edges_in
        while edge != None:
            edges_in.append(edge)
            edge = edge.next_in
            tmp_vertexes.append(vertexes[i])
        tmp_vertexes.append(vertexes[i])
        edges_in.sort()
        tmp_edges += edges_in

        # get right unchanged part of status lists  
        if (j < len(current_edges)):
            tmp_edges += current_edges[j:len(current_edges)]
            tmp_vertexes += current_vertexes[j+1:len(current_vertexes)]

        current_edges = tmp_edges.copy()
        current_vertexes = tmp_vertexes.copy()

def balance(vertexes = []):
        # sort edges from the leftmost edge to the rightmost
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
        for i in range(len(vertexes)-2, 0, -1):
            wIn = vertexes[i].wIn()
            wOut = vertexes[i].wOut()

            # d2 - leftmost edge which goes into current vertex
            d2 = vertexes[i].leftmost_in_edge()
            
            # if there is more edges/chains out than in, it means that several chains will
            # disperse, coming from the same in-edge 
            if wOut > wIn:
                d2.weight = wOut - wIn + d2.weight

def create_chains(x,y, vertexes = []):
        vertexes.sort()
        regularize(vertexes)
        draw_graph(x,y,vertexes)       
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
        vertexes[numbers[0]-1].add_edge(vertexes[numbers[1]-1])
    fe.close()
   
    return vertexes


vertexes = readGraph()

x = 6
y = 2.5

draw_graph(x,y,vertexes)

chains = create_chains(x,y, vertexes)

""" for c in chains:
    line = ""
    for v in c:
        line += str(v)+" "
    print(line) """

locate_point(x,y,chains, vertexes)