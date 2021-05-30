from decimal import *
import numpy as np
import math
import copy


def identity_matrix(n):
    decimals = [[Decimal(0) for j in range(n)] for i in range(n)]
    for i in range(n):
        decimals[i][i] = Decimal(1)
    return np.array(decimals)

def sign(x):
    return 1 if x > 0 else -1 if x < 0 else 0

def lp_norm_modified(vector, p : Decimal):
    v = copy.deepcopy(vector)
    max_deviation = abs(max(v, key = lambda x: abs(x)))[0]
    if max_deviation == 0:
        return Decimal(0)
    v /= max_deviation
    sum = Decimal(0)
    for d in v:
        sum += abs(d[0])**p
    return abs(max_deviation) * sum ** (Decimal(1)/Decimal(p))

def lp_norm(vector, p: Decimal):
    sum = Decimal(0)
    for d in vector:
        sum += abs(d[0])**p
    return sum ** (Decimal(1)/Decimal(p)) 

def lp_norm_float_modified(vector, p: float):
    v = vector.copy()
    max_deviation = abs(max(v, key = lambda x: abs(x)))[0]
    if max_deviation == 0:
        return 0.
    v /= max_deviation
    sum = 0.
    for d in v:
        sum += abs(d[0])**p
    return abs(max_deviation) * sum ** (1/p)

# np.linalg.norm doesn't work for high p
def lp_norm_float(vector, p: Decimal):
    sum = 0.
    for d in vector:
        sum += abs(d[0])**p
    return sum ** (1/p)


def solve_regression(A, b, p, x0, rad, eps, norm_function):
    # number of experiments, number of variables
    [m, n] = np.shape(A)  
    
    # space transformation coefficient
    beta = Decimal((n - 1) / (n + 1)).sqrt() - 1  
    
    # initial radius
    r = Decimal(rad) 

    # space transformation matrix
    B = identity_matrix(n)  
    x = copy.deepcopy(x0)
    
    # variable for storing result
    xr = copy.deepcopy(x0) 
    fmin = Decimal('Infinity')
    itr = 0

    while True:
        itr += 1
        tmp = A.dot(x) - b
        f = norm_function(tmp, Decimal(p))
        if f < fmin:
            fmin = f
            xr = copy.deepcopy(x)

        sumv = np.array([Decimal(0) for i in range(n)]).reshape(n, 1)
        delta = Decimal(1) / Decimal(10) ** Decimal(getcontext().prec)
        power = Decimal(p)-1 if p-1 > delta else delta
        for i in range(m):           
            sumv += Decimal(sign(tmp[i][0])) * (abs(tmp[i][0]) ** power) * A[i].reshape(n, 1)
        subgradient = (f ** (1 - Decimal(p))) * sumv

        ksi = B.T.dot(subgradient)
        norm = norm_function(ksi, Decimal(2))

        if (r * norm < eps):
            return xr, itr

        ksi /= norm
        hk = Decimal(1/(n + 1)) * r
        x -= hk * B.dot(ksi)
        B += beta * B.dot(ksi).dot(ksi.T)
        r *= Decimal(n) / Decimal(n**2 - 1).sqrt()


def solve_regression_float(A,b,p,x0,rad,eps, norm_function):
    [m,n] = np.shape(A)
    beta = math.sqrt((n - 1) / (n + 1)) - 1
    r = rad
    B = np.identity(n)
    x = x0.copy()
    xr = x0.copy()  # variable for storing result
    fmin = float('inf')
    itr = 0

    while True:
        itr += 1
        tmp = A.dot(x) - b
        f = norm_function(tmp, p)
        if f < fmin:
            fmin = f
            xr = x.copy()

        sumv = np.array([0. for i in range(n)],dtype = float).reshape(n,1)
        power = p - 1 if p - 1 > 1e-10 else 1e-10
        for i in range(m):           
            sumv += sign(tmp[i][0]) * (abs(tmp[i][0])** power) * A[i].reshape(n,1)
        subgradient = (f **(1 - p)) * sumv

        ksi = B.T.dot(subgradient)
        norm = norm_function(ksi, 2)        
        
        if (r * norm < eps):
            return xr, itr

        ksi /= norm
        hk = (1/(n + 1)) * r
        x -= hk * B.dot(ksi)
        B += beta * B.dot(ksi).dot(ksi.T)
        r *= n / math.sqrt(n**2 - 1)
       
