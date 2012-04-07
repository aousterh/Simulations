import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt


fig1 = plt.figure()
sub1 = fig1.add_subplot(211)

r = mlab.csv2rec("../momose/output/scramRecorderOutput.csv", skiprows=3)
latency = list(r.latency)
uuid = list(r.uuid)
m = len(set(uuid))
print m

n, bins, patches = sub1.hist(latency, 10, normed=1, cumulative=False, histtype='bar', alpha=0.75)
sub1.set_xlim(min(latency), max(latency))
sub1.set_ylim(0, max(n) * 1.1)


data = [(uuid[i], latency[i]) for i in range(len(latency))]
data = sorted(data, key=lambda x: x[0])

x_lists = [[x[1] for x in data if x[0] == i] for i in set(uuid)]

x_data = [sorted(list(set(x))) for x in x_lists]

y_data = [[1.0 * x_lists[i].count(c) / m for c in x_data[i]] for i in range(len(x_lists))]
y_data = [list(np.cumsum(x)) for x in y_data]

sub2 = fig1.add_subplot(212)

for i in range(len(x_data)):
  sub2.plot(x_data[i], y_data[i], '-')
sub2.set_xlim(0, max([max(x) for x in x_data]))
sub2.set_ylim(0, 1)

plt.show()
