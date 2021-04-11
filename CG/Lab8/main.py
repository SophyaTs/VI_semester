import matplotlib.pyplot as plt
from matplotlib.collections import LineCollection
import random

MINX = 0.
MAXX = 10.
MINY = 0.
MAXY = 10.

# number of slabs
k = 10

# total number of points
n = 50


def generate_points(n):
    points = []
    for i in range(n):
        points.append([random.uniform(MINX,MAXX),random.uniform(MINY,MAXY)])
    return points

# utility function: finds points within set with min and max y-coordinate
def find_extreme(extreme_points, set):
    if len(set) > 1:
        extreme_points.append(min(set, key = lambda p: p[1]))
        extreme_points.append(max(set, key = lambda p: p[1]))
    elif len(set) == 1:
        extreme_points.append(set[0])

# utility function: divides points to slabs and determines their extreme points
def pick_extreme_points(points = [], k = 2):
    slabs = [[] for i in range(k)]
    
    minx = min(points, key = lambda p: p[0])[0]
    maxx = max(points, key = lambda p: p[0])[0]

    # lists of points which have min and max x
    extreme_left = []
    extreme_right = []
    
    points.sort(key = lambda p: p[0])

    # distribute points to slabs  
    step = (maxx-minx)/k
    current = minx+step
    i = 0
    for p in points:
        if p[0] == minx:
            extreme_left.append(p)
            continue
        if p[0] == maxx:
            extreme_right.append(p)
            continue
        if p[0] > current:
            while p[0] > current:
                current += step
                i += 1
        slabs[i].append(p)

    # find points with min and max y within every slab
    extreme_points = []
    find_extreme(extreme_points,extreme_left)
    find_extreme(extreme_points,extreme_right)
    for slab in slabs:
        find_extreme(extreme_points, slab)
    return extreme_points, minx, maxx


# Jarvis Algorithm from Lab 6
TURN_LEFT, TURN_RIGHT, TURN_NONE = (1, -1, 0)

def turn(p, q, r):
    angle = (q[0] - p[0])*(r[1] - p[1]) - (r[0] - p[0])*(q[1] - p[1])
    if angle > 0:
        return TURN_LEFT
    elif angle < 0:
        return TURN_RIGHT
    return TURN_NONE

def distance(p, q):
    dx, dy = q[0] - p[0], q[1] - p[1]
    return dx * dx + dy * dy

def next_point(points, p):
    next_p = p
    for curr_point in points:
        t = turn(p, next_p, curr_point)
        if t == TURN_RIGHT or t == TURN_NONE and distance(p, curr_point) > distance(p, next_p):
            next_p = curr_point
    return next_p

def jarvis(points):
    hull = [min(points)]
    for p in hull:
        next_p = next_point(points, p)
        if next_p != hull[0]:
            hull.append(next_p)
    return hull

# main function! finds extreme points in slabs and builds a hull from them using Jarvis Algorithm
def approximate_hull(points = [], k = 2):
    extreme_points, x1, x2 = pick_extreme_points(points,k)
    hull = jarvis(extreme_points)
    return hull, extreme_points, x1, x2

# red points are extreme points which were used for building a hull
# blue points are all other points 
def draw(points = [], extreme_points = [], hull = [],k = 2, x1 = 0.,x2 = 0.):
    fig, ax = plt.subplots()
    ax.set_xlim([MINX-1, MAXX+1])
    ax.set_ylim([MINY-1, MAXY+1])

    for point in points:
        plt.scatter(point[0], point[1], s=10, edgecolors='b', facecolor='b')
    for point in extreme_points:
        plt.scatter(point[0], point[1], s=15, edgecolors='r', facecolor='r')

    lines = [[hull[-1], hull[0]]]
    for i in range(1, len(hull)):
        lines.append([hull[i - 1], hull[i]])
    lc = LineCollection(lines, linewidths=1)
    ax.add_collection(lc)

    x = x1
    step = (x2-x1)/k
    for i in range(k+1):
        lines.clear()
        lines = [[(x,MINY-1),(x,MAXY+1)]]
        lc = LineCollection(lines, linewidths=1, color = 'k')
        ax.add_collection(lc)
        x+=step
    plt.show()    

points = generate_points(n)
hull, extreme_points,x1,x2 = approximate_hull(points, k)
draw(points,extreme_points,hull,k,x1,x2)