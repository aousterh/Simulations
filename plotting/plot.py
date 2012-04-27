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

fig1 = plt.figure()
#sub1 = fig1.add_subplot(211)

reader = csv.reader(open(file, "rb"), delimiter=",")
#column = np.genfromtxt(file, delimiter=",", usecols=(0))
row0 = reader.next()

#column = column.tolist()
num_groups = int(row0[0])
lines_per_group = 5   # TODO: don't hard code this


for group in range(num_groups):
  sub = fig1.add_subplot(num_groups, 1, group + 1)
  x_max = 0

  # read in first line to figure out how many real lines there are
  row = reader.next()
  actual_lines = int(row[0])

  for i in range(actual_lines):
    x_data = reader.next()
    y_data = reader.next()
    x_data = [int(x) for x in x_data]
    y_data = [float(y) for y in y_data]

    sub.plot(x_data, y_data, '-')
    try:
      if max(x_data) > x_max:
        x_max = max(x_data)
    except:
      x_max = x_max

  if group < 2:
    for i in range(lines_per_group - actual_lines):
      reader.next();
      reader.next();

  if len(sys.argv) > 2 and group < 2:
    x_max = int(sys.argv[2])
  sub.set_xlim(0, x_max)
  sub.set_ylim(0, 1)

  if group < 2:
    sub.set_xlabel("Latency (timesteps)")
    sub.set_ylabel("Percent of Messages")

plt.show()
