import sys
import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
import csv

"""
This script reads in data from a csv file (current in the momosecpp/plotting/text.csv file).
It assumes that the first row contains the number of x, y pairs of data that there will be.
The second row is a list of values to be plotted in a histogram.
The rest of the rows are pairs of x and y coordinates for different lines, so that the even
numbered rows are x-coordinates, and the odd numbered rows are y-coordinates.
"""

file = sys.argv[1]


reader = csv.reader(open(file, "rb"), delimiter=",")
row0 = reader.next()
row0 = reader.next()
num_ads = int(row0[2])
row0 = reader.next()
print "adversaries:"
print num_ads

data = []
for row in reader:
  if int(row[2]) != int(row[3]):
    t = (int(row[0]), int(row[1]), int(row[2]), int(row[3]))
    data.append(t)


a_to_a = [x[1] for x in data if x[2] < num_ads and x[3] < num_ads]
a_to_c = [x[1] for x in data if x[2] < num_ads and x[3] >= num_ads]
c_to_a = [x[1] for x in data if x[2] >= num_ads and x[3] < num_ads]
c_to_c = [x[1] for x in data if x[2] >= num_ads and x[3] >= num_ads]


fig1 = plt.figure()
sub1 = fig1.add_subplot(411)
sub2 = fig1.add_subplot(412)
sub3 = fig1.add_subplot(413)
sub4 = fig1.add_subplot(414)

bins = 20
range = (0, 200)
if len(a_to_a) > 0:
  sub1.hist(a_to_a, bins, range)
if len(a_to_c) > 0:
  sub2.hist(a_to_c, bins, range)
if len(c_to_a) > 0:
  sub3.hist(c_to_a, bins, range)
if len(c_to_c) > 0:
  sub4.hist(c_to_c, bins, range)

"""
x_max = 200
y_max = 7000
sub1.set_xlim(0, x_max)
sub1.set_ylim(0, y_max)
sub2.set_xlim(0, x_max)
sub2.set_ylim(0, y_max)
sub3.set_xlim(0, x_max)
sub3.set_ylim(0, y_max)
sub4.set_xlim(0, x_max)
sub4.set_ylim(0, y_max)
"""

plt.show()
