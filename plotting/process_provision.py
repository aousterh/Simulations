"""
This file processes a list of (uuid, latency, trust distance, recipient) tuples into a
format that can easily be plotted - a list of x and y coordinates, so that
each row is either x coordinates or y coordinates, alternating, with some
header info at the top. This is the output format:

number of groups
number of pairs of coordinates in first group
x coordinates 1
y coordinates 1
...
...
x coordinates n
y coordinates n
number of pairs of coordinates in second group
...
"""
import sys
import numpy as np
import csv

file = sys.argv[1]

reader = csv.reader(open(file, "rb"), delimiter=",")

# skip 3 lines of info
reader.next()
reader.next()
reader.next()

# note: this is not ideal!!
num_adversaries = 0
if len(sys.argv) > 2:
  num_adversaries = int(sys.argv[2])

# right now, extract 3 data sets for friends, friends + friends of friends, and all
# data sets we want to plot
# each list is a list of (uuid, latency) tuples
friends = []
fof = []
all_nodes = []
for row in reader:
  t = (int(row[0]), int(row[1])) 
  td = int(row[2])
  # we only care about messages sent by collaborators
  if int(row[3]) >= num_adversaries:
    if td <= 1:
      friends.append(t)
    if td <= 2:
      fof.append(t)
    all_nodes.append(t)

plot_data_sets = [friends]
if len(fof) > len(friends):
  plot_data_sets = [friends, fof]
if len(all_nodes) > len(fof):
  plot_data_sets = [friends, fof, all_nodes]

print len(plot_data_sets)

for data_set in plot_data_sets:
  data_list = data_set
  data = []
  latency = []
  uuid = set()
  for t in data_list:
    data.append(t)
    uuid.add(t[0])
    latency.append(t[1])

  x_lists = [[x[1] for x in data if x[0] == i] for i in uuid]

  # sort the lists - each list is a list of latencies for one message
  x_lists2 = [sorted(x) for x in x_lists]

  # the total number of messages
  m = len(uuid)

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

  # determine the average number of nodes that received any given message
  #av = (1.0 * len(data_list)) / len(uuid)

  l_0_01 = int(m*0.01)
  l_0_10 = int(m*0.1)
  l_0_50 = int(m*0.5)
  l_0_90 = int(m*0.9)
  l_0_99 = int(m*0.99)
  if l_0_90 >= len(x_lists3):
    l_0_90 = len(x_lists3) - 1
  if l_0_99 >= len(x_lists3):
    l_0_99 = len(x_lists3) - 1

  x_lists4 = [x_lists3[l_0_01], x_lists3[l_0_10], x_lists3[l_0_50], x_lists3[l_0_90], x_lists3[l_0_99]]

  x_data = [sorted(list(set(x))) for x in x_lists4]

  y_data = [[1.0 * x_lists4[i].count(c) / m for c in x_data[i]] for i in range(len(x_lists4))]
  y_data = [list(np.cumsum(x)) for x in y_data]


  num_lists = 0
  for i in range(len(x_data)):
    if len(x_data[i]) > 1:
      num_lists = num_lists + 1

  print num_lists,
  print ",",
  print 0

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
    else:
      print ""
      print ""
