import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt


fig1 = plt.figure()
sub1 = fig1.add_subplot(211)

r = mlab.csv2rec("../momose/output/scramRecorderOutput.csv", skiprows=3)
x = r.latency

n, bins, patches = sub1.hist(x, 5, normed=1, cumulative=False, histtype='bar', alpha=0.75)
sub1.set_xlim(min(x), max(x))
sub1.set_ylim(0, max(n) * 1.1)

sub2 = fig1.add_subplot(212)
x_list = list(x)
x_data = list(set(x))
y = [1.0 * x_list.count(c) / len(x_list) for c in x_data]
y_data = np.cumsum(y)

sub2.plot(x_data, y_data, 'o-')
sub2.set_xlim(0, max(x_data))


plt.show()
