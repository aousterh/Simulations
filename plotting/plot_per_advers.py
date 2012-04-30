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

f_size=15

fig1 = plt.figure()

sub = fig1.add_subplot(111)

x_data = [0.004, 0.008, 0.016, 0.031, 0.064, 0.125, 0.25, 0.5]

# median latency, no trust
y1 = [264, 251, 218, 168, 94, 55, 24, 11]

# median completion, no trust
y2 = [25, 28, 21, 17, 10, 7, 4, 2]
y2 = [y / 450.0 for y in y2]

# median latency, trust
y3 = [301, 309, 289, 309, 304, 304, 323, 335]

# median completion, trust
y4 = [28, 33, 32, 34, 36, 30, 30, 29]
y4 = [y / 450.0 for y in y4]


sub.plot(x_data[0:len(y2)], y2, '-o')
sub.plot(x_data[0:len(y4)], y4, '-v')

#sub.set_xlim(0, x_max)
#sub.set_ylim(0, 1)

leg = sub.legend(('without trust', 'with trust'), loc=1)
for t in leg.get_texts():
  t.set_fontsize(f_size)
 
sub.set_xlabel("Percentage of Nodes who are Adversaries", fontsize=f_size)
sub.set_ylabel("Median % of Collaborators who Received Message", fontsize=f_size)
title("Median Completion Rate vs. Percent Adversaries", fontsize=f_size)

plt.show()
