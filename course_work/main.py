from utils import *
from datetime import datetime
import warnings

timestamp = datetime.now().strftime("%Y_%b_%d__%H_%M_%S")
warnings.simplefilter('error')

print("LAUNCH PARAMETERS:")
#print("p =", p)
print("Number of experiments =", m)
print("Decimal precision:", getcontext().prec, '\n')

if mulptiple_p:

    ps = [
        1.,
        1.05,
        1.1,
        #1.2,
        #1.3,
        #1.4,
        #1.5,
        #1.6,
        #1.8,
        2.,
        #3.,
        5.,
        10.,
        50.,
        100.,
        1000.,
        ]

    color_count = 0

    th = [None for i in range(len(ps))]
    for i,p in enumerate(ps):
        execute(
            "p = "+str(ps[-1-i]),
            timestamp,
            solve_regression,
            [Ad, bd, ps[-1-i], x0d, radius, eps, lp_norm]
        )
        """ th[i] = Thread(target=execute, args=(
            "p = "+str(p),
            timestamp,
            solve_regression,
            [Ad, bd, p, x0d, radius, eps, lp_norm_modified]
        ))
    for t in th:
        t.start()

    for t in th:
        t.join() """


    for key in result.keys():
        if color_count == 14:
            print()
        draw(result[key], COLORS_BY_ITERATION[color_count], key)
        color_count += 1
else:
    th_d = Thread(target=execute, args=(
        "===      DECIMAL     ===",
        timestamp,
        solve_regression,
        [Ad, bd, p, x0d, radius, eps, lp_norm]
    ))
    th_dm = Thread(target=execute, args=(
        "=== DECIMAL modified ===",
        timestamp,
        solve_regression,
        [Ad, bd, p, x0d, radius, eps, lp_norm_modified]
    ))
    th_f = Thread(target=execute, args=(
        "===       FLOAT      ===",
        timestamp,
        solve_regression_float,
        [Af, bf, p, x0f, radius, eps, lp_norm_float]
    ))
    th_fm = Thread(target=execute, args=(
        "===  FLOAT modified  ===",
        timestamp,
        solve_regression_float,
        [Af, bf, p, x0f, radius, eps, lp_norm_float_modified]
    ))
    th_d.start()
    #th_dm.start()
    th_f.start()
    #th_fm.start()
    th_d.join()
    #th_dm.join()
    th_f.join()
    #th_fm.join()

    for key in result.keys():
        draw(result[key], COLORS_BY_NAME[key], SHORT_NAMES[key])

plt.plot(Af[:, [0]].reshape(m, 1), bf, 'o', color='#282828', markersize=4)
leg = plt.legend()
#plt.show()
plt.savefig("samples/"+timestamp+"_plot.png", bbox_inches='tight')
