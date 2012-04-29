import sys
import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
import csv
import math
from pylab import *
import pylab as P

"""
This script reads in data from a csv file (current in the momosecpp/plotting/text.csv file).
It assumes that the first row contains the number of x, y pairs of data that there will be.
The second row is a list of values to be plotted in a histogram.
The rest of the rows are pairs of x and y coordinates for different lines, so that the even
numbered rows are x-coordinates, and the odd numbered rows are y-coordinates.
"""
bins = 10

fig = plt.figure()
sub = fig.add_subplot(111)

file1 = sys.argv[1]
file2 = sys.argv[2]

reader1 = csv.reader(open(file1, "rb"), delimiter=",")
reader2 = csv.reader(open(file2, "rb"), delimiter=",")

readers = [reader1, reader2]
for i in range(len(readers)):
  reader = readers[i]

  row = reader.next()
  data = [int(x) for x in row]
  
  if i == 1:
    sub.hist(data, bins, histtype = 'step', range=[0, 450])
  else:
    sub.hist(data, bins, range=[0, 450])

sub.legend(('with trust', 'without trust'))
sub.set_xlabel("Number of Receiving Nodes")
sub.set_ylabel("Number of Messages")
title("Number of Nodes who Received Messages")

plt.show()
