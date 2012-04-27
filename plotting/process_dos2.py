"""
This file processes a list of (uuid, latency, sender, recipient) tuples into a
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
row2 = reader.next()
num_nodes = int(row2[1])
num_adversaries = int(row2[2])
reader.next()

# two lists - one of msgs sent by collaborators, the other of msgs sent by adversaries
# each list is a list of (uuid, latency) tuple
collaborators = []  # msgs sent by collaborators
adversaries = []  # msgs sent by adversaries
for row in reader:
  t = (int(row[0]), int(row[1])) 
  # only care about msgs received by collaborators
  if int(row[3]) >= num_adversaries:
    if int(row[2]) >= num_adversaries:
      collaborators.append(t)
    else:
      adversaries.append(t)

plot_data_sets = [collaborators, adversaries]
completion_lists = []
print len(plot_data_sets) + 1

for data_set in plot_data_sets:
  data_list = data_set
  latency = []
  uuid = set()
  for t in data_list:
    uuid.add(t[0])
    latency.append(t[1])

  # generate a list of latency lists where each latency list is
  # all the latencies for one msg
  x_lists = [[x[1] for x in data_list if x[0] == i] for i in uuid]

  # sort the lists - each list is a list of latencies for one message
  x_lists2 = [sorted(x) for x in x_lists]

  print "STUFF"
  for y in x_lists2:
    print len(y)

  # the total number of messages
  m = len(uuid)

  # create list of lists where the ith list is the list of the ith
  # latencies from each message
  x_lists3 = []
  percents = [0.01, 0.10, 0.50, 0.90, 0.99]
  indices = [int(x * m) for x in percents]
#  print m
#  print "INDICES"
#  print indices
  for p in percents:
    x_lists3.append([])
  for lat_list in x_lists2:
    for i in range(len(percents)):
    #  index = int(percents[i] * len(lat_list))
      index = indices[i]
      if index < len(lat_list):
        x_lists3[i].append(lat_list[index])

  print "DATA"
  for x in x_lists3:
    print len(x)

  # each list in x_data is a sorted list of the latency it took for
  # each msg to be received by X% of nodes
  x_data = [sorted(list(set(x))) for x in x_lists3]

  y_data = [[1.0 * x_lists3[i].count(c) / m for c in x_data[i]] for i in range(len(x_lists3))]
  y_data = [list(np.cumsum(x)) for x in y_data]


  # what percentage of nodes received each msg
  num_completed = [len(x) for x in x_lists]
  completion_lists.append(num_completed)


  num_lists = 0
  for i in range(len(x_data)):
    if len(x_data[i]) > 1:
      num_lists = num_lists + 1

  print num_lists

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


# print out completion percentage CDF data
x_data = [sorted(list(set(x))) for x in completion_lists]

y_data = [[1.0 * completion_lists[i].count(c)/len(completion_lists[i]) for c in x_data[i]] for i in range(len(completion_lists))]
y_data = [list(np.cumsum(x)) for x in y_data]

print 2

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

