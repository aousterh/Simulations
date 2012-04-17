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


c_msg_count = 0
a_msg_count = 0
c_msgs = set()
a_msgs = set()
for row in reader:
  if int(row[3]) >= num_adversaries:
    c_msg_count = c_msg_count +  1
    c_msgs.add(int(row[0]))
  else:
    a_msg_count = a_msg_count + 1
    a_msgs.add(int(row[0]))

print c_msg_count
print a_msg_count
print len(c_msgs)
print len(a_msgs)
