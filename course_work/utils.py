from threading import Thread, Lock
import time
import matplotlib.pyplot as plt
from regression import *
from parameters import *

mutex = Lock()
result = {}
COLORS_BY_NAME = {
    "===      DECIMAL     ===": 'forestgreen',
    "=== DECIMAL modified ===": 'firebrick',
    "===       FLOAT      ===": 'navy',
    "===  FLOAT modified  ===": 'darkorange'
}
COLORS_BY_ITERATION = {
    0:'red',
    9: 'orangered',
    10: 'chocolate',
    1: 'darkorange',
    12: 'goldenrod',
    2: 'gold',
    3: 'forestgreen',
    13: 'darkgreen',
    4: 'teal',
    16: 'steelblue',
    5: 'dodgerblue',
    6: 'blue',
    15: 'navy',
    16: 'darkslateblue',
    7: 'indigo',
    17: 'darkviolet',
    18: 'purple',
    8: 'magenta',
    19: 'deeppink',
} 

SHORT_NAMES = {
    "===      DECIMAL     ===": 'Decimal',
    "=== DECIMAL modified ===": 'Decimal modified',
    "===       FLOAT      ===": 'float',
    "===  FLOAT modified  ===": 'float modified'
}

def draw(coef, color, label_str):
    dots = 100
    x_coor = np.linspace(min_x, max_x, dots)
    y_coor = []
    for i in range(dots):
        y_coor.append(float(coef[0][0])*x_coor[i] + float(coef[1][0]))
    y_coor = np.array(y_coor)
    plt.plot(x_coor, y_coor, color, label = label_str, linewidth= 3)

def execute(name, timestamp ,method, positional_argumnets):
    try:
        start = time.time()
        x, it = method(*positional_argumnets)
        success = True
    except Exception as e:
        error = e
        success = False
    finally:
        stop = time.time()

    f = open("samples/"+timestamp+"_results.txt", "a")
    if success:
        f.write(name + "\nelapsed "+ str(stop - start)+ "seconds, iterations = "+ str(it)+ '\n')
        f.write(str(x)) 
        f.write("\n\n")
    else:
        f.write(name + "\nelapsed" + str(stop - start) + "seconds, failed\n")
        f.write(str(error))
        f.write("\n\n")
    f.close()

    """ if success:
        xf = np.array([float(x[i][0]) for i in range(k+1)])
        xf = np.around(xf,4)
        f = open("samples/table_info_1.csv","a")
        f.write(','.join(xf)) """

    try:
        mutex.acquire()
        if success:
            result[name] = x
            print(name, "\nelapsed", stop - start, "seconds, iterations =", it)
            print(x, '\n')
        else:
            print(name, "\nelapsed", stop - start, "seconds, failed")
            print(error, '\n')
    finally:
        mutex.release()


axes = plt.gca()
axes.set_xlim([min_x-1, max_x+1])
y1 = function(min_x)
y2 = function(max_x)
y_min = min(bf)
y_max = max(bf)
axes.set_ylim([y_min-1, y_max+1])
