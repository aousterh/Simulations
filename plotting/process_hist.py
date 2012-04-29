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


  # what percentage of nodes received each msg
  num_completed = [len(x) for x in x_lists]
  completion_lists.append(num_completed)


# print out completion percentage CDF data
x_data = [sorted(list(set(x))) for x in completion_lists]


for i in range(len(x_data)):
  if (len(x_data[i]) > 1):
    for j in range(len(x_data[i])):
      print x_data[i][j],
      if j != len(x_data[i]) - 1:
        print ",",
    print
  else:
    print ""
    print ""

