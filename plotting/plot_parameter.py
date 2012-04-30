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

x_data = [1, 2, 4, 8, 16, 32, 64, 128, 256, 512]

# median latency, no trust
y1 = [175, 59, 70, 57, 61, 53, 53, 65]

# median completion, no trust
y2 = [3, 3, 5, 7, 7, 9, 9, 10]
y2 = [y / 450.0 for y in y2]

# median latency, trust
y3 = [325, 227, 235, 262, 291, 300, 329, 319]

# median completion, trust
y4 = [4, 6, 8, 13, 20, 40, 73, 232]
y4 = [y / 450.0 for y in y4]


sub.plot(x_data[0:len(y2)], y2, '-o')
sub.plot(x_data[0:len(y4)], y4, '-v')

#sub.set_xlim(0, x_max)
#sub.set_ylim(0, 1)

leg = sub.legend(('without trust', 'with trust'), loc=2)
for t in leg.get_texts():
  t.set_fontsize(f_size)
 
sub.set_xlabel("Maximum Messages Exchanged per Timestep", fontsize=f_size)
sub.set_ylabel("Median % of Collaborators who Received Message", fontsize=f_size)
title("Median Completion Rate vs. Exchange Number", fontsize=f_size)

plt.show()
