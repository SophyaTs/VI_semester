from decimal import *
from set import *
from regression import *

# defines in which mode program should run
mulptiple_p = True

# degree
p = 1.1

#number of cases
m = 10

# number of variables
k = 1

# precision
eps = 1e-3

# initial radius
radius = 20

# precision of Decimal
getcontext().prec *= 1

min_x = 0.
max_x = 9.

def function(x : float) -> float:
    return -2*x-1


x0f = np.zeros((k+1, 1)).reshape(k+1, 1)
x0d = np.array([Decimal(x0f[i][0]) for i in range(k+1)], dtype=Decimal).reshape(k+1, 1)

""" Ad, bd, Af, bf = generate_set(
    k,
    function,
    min_x,
    max_x,
    m,
    Position.EVERYWHERE,
    ValueDeviation.RANDOM,
    0.2,
    0.03,
    5,
    10
) """

Af = np.linspace(min_x, max_x, m).reshape(m, k)
Af = np.hstack((Af, np.array([1. for i in range(m)]).reshape(m, 1)))
bf = np.array([function(float(i)) for i in range(m)]).reshape(m, 1)
bf[1][0] = 20.0
bf[3][0] = -70.0

Ad = np.array([Decimal(Af[i][0])for i in range(m)], dtype=Decimal).reshape(m, k)
Ad = np.hstack((Ad, np.array([Decimal(1)
                for i in range(m)]).reshape(m, 1)))
bd = np.array([Decimal(bf[i][0])
                for i in range(m)], dtype=Decimal).reshape(m, 1)
