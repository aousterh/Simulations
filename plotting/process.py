"""
This file processes a list of (uuid, latency) pairs into a format
that can easily be plotted a list of x and y coordinates, so that
each row is either x coordinates or y coordinates, alternating,
with some header info at the top. This is the output format:

number of pairs of coordinates
values to be plotted in a histogram (latencies)
x coordinates 1
y coordinates 1
...
...
x coordinates n
y coordinates n
"""

import numpy as np


r = np.genfromtxt("./data.csv", delimiter=",", skip_header=4)
r = r.tolist()

data = []
latency = []
uuid = set()
for i in range(len(r)):
  t = r[i]
  data.append((t[0], t[1]))
  uuid.add(t[0])
  latency.append(t[1])

m = len(uuid)
data = sorted(data, key=lambda x: x[0])

x_lists = [[x[1] for x in data if x[0] == i] for i in uuid]

x_data = [sorted(list(set(x))) for x in x_lists]

y_data = [[1.0 * x_lists[i].count(c) / m for c in x_data[i]] for i in range(len(x_lists))]
y_data = [list(np.cumsum(x)) for x in y_data]

print len(x_data),
print ","
for i in range(len(latency)):
  print latency[i],
  if i != len(latency) - 1:
    print ",",
print
for i in range(len(x_data)):
  for j in range(len(x_data[i])):
    print x_data[i][j],
    if j != len(x_data[i]) - 1:
      print ",",
  print
  for j in range(len(y_data[i])):
    print y_data[i][j],
    if j != len(y_data[i]) - 1:
      print ",",
  print

"""
sub2 = fig1.add_subplot(212)

for i in range(len(x_data)):
  sub2.plot(x_data[i], y_data[i], '-')
sub2.set_xlim(0, max([max(x) for x in x_data]))
sub2.set_ylim(0, 1)

plt.show()
"""
