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
f_size=20
num = 450.0

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
  data = [int(x) / num for x in row]
  
  if i == 1:
    sub.hist(data, bins, histtype = 'step', range=[0, 1])
  else:
    sub.hist(data, bins, range=[0, 1])

leg = sub.legend(('with trust', 'without trust'))
for t in leg.get_texts():
  t.set_fontsize(f_size)

sub.set_xlabel("Fraction of Collaborators who Received Message", fontsize=f_size)
sub.set_ylabel("Number of Messages", fontsize=f_size)
title("Fraction of Collaborators who Received Each Message", fontsize=f_size)

plt.show()
