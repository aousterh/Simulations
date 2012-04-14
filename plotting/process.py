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
import csv

reader = csv.reader(open("../momosecpp/output/messageRecorderOutput.csv", "rb"), delimiter=",")
# skip 3 lines of info
reader.next()
reader.next()
reader.next()

data = []
latency = []
uuid = set()
for row in reader:
  t = (int(row[0]), int(row[1]))
  data.append(t)
  uuid.add(t[0])
  latency.append(t[1])

# number of messages is the number of uuids right now
# assuming one msg per uuid 
m = len(uuid)

data = sorted(data, key=lambda x: x[0])

x_lists = [[x[1] for x in data if x[0] == i] for i in uuid]

# sort the lists - each list is a list of latencies for one message
x_lists2 = [sorted(x) for x in x_lists]

# create list of lists where the ith list is the list of the ith
# latencies from each message
x_lists3 = []
for i in range(m-1):
  new_list = []
  for j in range(len(x_lists2)):
    m_l = x_lists2[j]  # latencies for message j
    if i < len(m_l):
      new_list.append(m_l[i])
  x_lists3.append(new_list)

l_0_01 = int(len(x_lists3)*0.01)
l_0_10 = int(len(x_lists3)*0.1)
l_0_50 = int(len(x_lists3)*0.5)
l_0_90 = int(len(x_lists3)*0.9)
l_0_99 = int(len(x_lists3)*0.99)


x_lists4 = [x_lists3[l_0_01], x_lists3[l_0_10], x_lists3[l_0_50], x_lists3[l_0_90], x_lists3[l_0_99]]

x_data = [sorted(list(set(x))) for x in x_lists4]

y_data = [[1.0 * x_lists4[i].count(c) / m for c in x_data[i]] for i in range(len(x_lists4))]
y_data = [list(np.cumsum(x)) for x in y_data]


num_lists = 0
for x in x_data:
  if len(x) > 1:
    num_lists = num_lists + 1

print num_lists,
print ","
for i in range(len(latency)):
  print latency[i],
  if i != len(latency) - 1:
    print ",",
print
for i in range(len(x_data)):
  if (len(x_data[i]) > 1):
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
