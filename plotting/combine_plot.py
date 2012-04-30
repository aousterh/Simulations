import sys
import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
import csv
from pylab import *
import pylab as P

"""
This script reads in data from a csv file (current in the momosecpp/plotting/text.csv file).
It assumes that the first row contains the number of x, y pairs of data that there will be.
The second row is a list of values to be plotted in a histogram.
The rest of the rows are pairs of x and y coordinates for different lines, so that the even
numbered rows are x-coordinates, and the odd numbered rows are y-coordinates.
"""

file1 = sys.argv[1]
file2 = sys.argv[2]


fig1 = plt.figure()
#sub1 = fig1.add_subplot(211)

reader1 = csv.reader(open(file1, "rb"), delimiter=",")
reader2 = csv.reader(open(file2, "rb"), delimiter=",")
#column = np.genfromtxt(file, delimiter=",", usecols=(0))
row0 = reader1.next()
row0 = reader2.next()

#column = column.tolist()
num_groups = int(row0[0])
lines_per_group = 5   # TODO: don't hard code this
x_max = 0
sub = fig1.add_subplot(111)
readers = [reader1, reader2]
for group in range(num_groups):
  # read in first line to figure out how many real lines there are
  row1 = reader1.next()
  row2 = reader2.next()
  actual_lines = int(row1[0])

  for j in range(len(readers)):
    for i in range(actual_lines):
      reader = readers[j]
      x_data = reader.next()
      y_data = reader.next()
      x_data = [int(x) for x in x_data]
      y_data = [float(y) for y in y_data]

      if group == 0 and (i == 2 or i == 3):
        if j == 0:
          sub.plot(x_data, y_data, '-')
        if j == 1:
          sub.plot(x_data, y_data, '--')
      if max(x_data) > x_max:
        x_max = max(x_data)

  if group < 2:
    leg = sub.legend(('without trust - 50%', 'without trust - 90%', 'with trust - 50%', 'with trust - 90%'), loc=4)
    for i in range(lines_per_group - actual_lines):
      for reader in readers:
        reader.next();
        reader.next();

if len(sys.argv) > 3:
  x_max = int(sys.argv[3])
sub.set_xlim(0, x_max)
sub.set_ylim(0, 1)

sub.set_xlabel("Latency (timesteps)")
sub.set_ylabel("Percent of Messages Received")
title("Latency to Reach a Percentage of Receiving Collaborators")

plt.show()
