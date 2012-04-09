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


r = np.genfromtxt("../momosecpp/output/messageRecorderOutput.csv", delimiter=",", skip_header=4)
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

# sort the lists by the value of their median
x_lists2 = [sorted(x) for x in x_lists]
x_lists3 = sorted(x_lists2, key=lambda x: x[len(x) / 2])

l_0_01 = int(len(x_lists3)*0.01)
l_0_10 = int(len(x_lists3)*0.1)
l_0_50 = int(len(x_lists3)*0.5)
l_0_90 = int(len(x_lists3)*0.9)
l_0_99 = int(len(x_lists3)*0.99)

x_lists4 = [x_lists3[l_0_01], x_lists3[l_0_10], x_lists3[l_0_50], x_lists3[l_0_90], x_lists3[l_0_99]]

x_data = [sorted(list(set(x))) for x in x_lists4]

y_data = [[1.0 * x_lists4[i].count(c) / m for c in x_data[i]] for i in range(len(x_lists4))]
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
