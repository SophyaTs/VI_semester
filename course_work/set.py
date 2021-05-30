from enum import Enum
import numpy as np
import random
from decimal import *

class ValueDeviation(Enum):
    LOWER = 0
    RANDOM = 1
    HIGHER = 2

class Position(Enum):
    LEFT = 10
    EVERYWHERE = 11
    RIGHT = 12


""" Ad = np.array([Decimal(i) for i in range(m)], dtype=Decimal).reshape(m, k)
Ad = np.hstack((Ad, np.array([Decimal(1) for i in range(m)]).reshape(m, 1)))
bd = np.array([Decimal(i) for i in range(m)], dtype=Decimal).reshape(m, 1)
bd[m-1][0] = Decimal(0)


Af = np.array([float(i) for i in range(m)]).reshape(m, k)
Af = np.hstack((Af, np.array([1. for i in range(m)]).reshape(m, 1)))
bf = np.array([float(i) for i in range(m)]).reshape(m, 1)
bf[m-1][0] = 0
 """



def generate_set(
    k,
    func, 
    min_x,
    max_x,
    size: int, 
    position: Position, 
    value_deviation: ValueDeviation, 
    length_fraction: float,
    value_fraction: float,
    min_deviation: float,
    max_deviation: float
):
    x_f = np.linspace(min_x, max_x, size)
    y_f = [func(x_f[i]) for i in range(size)]

    indexes = [i for i in range(size)]
    if position == Position.LEFT:
        indexes = indexes[:int(size*length_fraction)]
    elif position == Position.RIGHT:
        indexes = indexes[size - int(size*length_fraction) : size]

    n = int(size * value_fraction) #if position == Position.EVERYWHERE else int(size * length_fraction * value_fraction)
    anomaly_indexes = random.sample(indexes, n)

    if value_deviation == ValueDeviation.LOWER:
        for i in anomaly_indexes:
            y_f[i] -= random.uniform(min_deviation, max_deviation)
    elif value_deviation == ValueDeviation.RANDOM:
        for i in anomaly_indexes:
            sgn = 1 if random.choice([True, False]) else -1
            y_f[i] += sgn*random.uniform(min_deviation, max_deviation)
    else:
        for i in anomaly_indexes:
            y_f[i] += random.uniform(min_deviation, max_deviation)

    A_d = np.array([Decimal(x_f[i]) for i in range(size)], dtype=Decimal).reshape(size, k)
    A_d = np.hstack((A_d, np.array([Decimal(1) for i in range(size)]).reshape(size, 1)))
    b_d = np.array([Decimal(y_f[i]) for i in range(size)], dtype=Decimal).reshape(size, 1)

    A_f = x_f.reshape(size,k)
    A_f = np.hstack((A_f, np.array([1. for i in range(size)]).reshape(size, 1)))
    b_f = np.array(y_f).reshape(size,1)

    return A_d, b_d, A_f, b_f
