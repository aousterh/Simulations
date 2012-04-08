import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt

"""
This script reads in data from a csv file (current in the momosecpp/plotting/text.csv file).
It assumes that the first row contains the number of x, y pairs of data that there will be.
The second row is a list of values to be plotted in a histogram.
The rest of the rows are pairs of x and y coordinates for different lines, so that the even
numbered rows are x-coordinates, and the odd numbered rows are y-coordinates.
"""


fig1 = plt.figure()
sub1 = fig1.add_subplot(211)

row0 = np.genfromtxt("./text.csv", delimiter=",", usecols=(0))
num_lines = int(row0.tolist()[0])

l = np.genfromtxt("./text.csv", delimiter=",", skip_header=1, skip_footer=(2*num_lines))
latency = l.tolist()


n, bins, patches = sub1.hist(latency, 10, normed=1, cumulative=False, histtype='bar', alpha=0.75)
sub1.set_xlim(min(latency), max(latency))
sub1.set_ylim(0, max(n) * 1.1)

sub2 = fig1.add_subplot(212)

x_max = 0
for i in range(num_lines):
  h = 2 + 2*i
  f = 2 * (num_lines - i) - 1
  x_data = np.genfromtxt("./text.csv", delimiter=",", skip_header= h, skip_footer = f)
  y_data = np.genfromtxt("./text.csv", delimiter=",", skip_header= h+1, skip_footer = f-1)
  sub2.plot(x_data.tolist(), y_data.tolist(), '-')
  if max(x_data) > x_max:
    x_max = max(x_data)
sub2.set_xlim(0, x_max)
sub2.set_ylim(0, 1)

plt.show()
