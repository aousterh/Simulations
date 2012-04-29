import sys
import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
import csv
import math
from pylab import *

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

reader1 = csv.reader(open(file1, "rb"), delimiter=",")
reader2 = csv.reader(open(file2, "rb"), delimiter=",")
#column = np.genfromtxt(file, delimiter=",", usecols=(0))
row0 = reader1.next()
row0 = reader2.next()

#column = column.tolist()
num_groups = int(row0[0])
lines_per_group = 5   # TODO: don't hard code this

readers = [reader1, reader2]
for group in range(num_groups):
  x_max = 450.0
  # read in first line to figure out how many real lines there are
  row1 = reader1.next()
  row2 = reader2.next()
  actual_lines = int(row1[0])

  for i in range(actual_lines):
    for j in range(len(readers)):
      reader = readers[j]
      x_data = reader.next()
      y_data = reader.next()
      x_data = [int(x) for x in x_data]
      x_data = [x / x_max for x in x_data]
      y_data = [1 - float(y) for y in y_data]
      
      if group == 2 and i == 0:
        sub = fig1.add_subplot(1, 1, 1)
        if j == 0:
          sub.plot(x_data, y_data, '-')
        if j == 1:
          sub.plot(x_data, y_data, '--')
        leg = sub.legend(('without trust', 'with trust'), loc=1)

        sub.set_ylim(0, 1)
        sub.set_xlabel("Percent of Collaborators Reached")
        sub.set_ylabel("Percent of Messages Sent by Collaborators")
        title("Message Completion Rates for Trust vs. No Trust in Data Dissemination")

plt.show()
