# MichaelOghenekomeA4Q3.py 
# 
# Course:      COMP 1012 
# Instructor:  Randy Cooper 
# Assignement: 4 Question 3
# Author:      Michael Oghenekome Victor 
# Version:     2014/07/21 
# 
# Purpose:    The purpose of this python program is to write a complete	program
#             that reads X and Y coordinates from filesand plot the curves 
#             defined by the coordinates in different ways.

print '----------------------------------------------------------------------'

# Establish the current working directory
from time import ctime
from sys import path
from os import chdir
chdir(path[0])
import numpy as np
import matplotlib.pyplot as plt


def displayTerminationMessage():                        #Terminate the program.
    print'''
programmed by Michael Oghenekome Victor.
Date: %s
End of processing.'''% ctime()


def readFile(filename):
    '''This function creates a list of x and y coordinates and returns the
    coordinates as an array. The coordinates are read from a file called file
    name and the function brings out and arranges the coordinates into lists'''
    #xCoords     - Alist of x coordinates.
    #yCoords     - Alist of y coordinates.
    #filename    - The name of the file for which data is extracted from.
    #lines       - A long lists of lines from file name
    #line       - A single line from the read file.
    #title       - the title of the file extracted from filename.
    #point       - A single string produced from the string of point.
    #points      - the breaking down of a large string from line into 
    #               smaller tuples of strings
    #coord       - The splitting of sting from point into smaller bit of strings.
    xCoords=[]
    yCoords=[]
    infile = open(filename,'r')
    lines= infile.readlines()
    title =lines[0].strip()
    for line in lines[1:]:
        points = line.strip().split()
        for point in points:
            coord = point.split(',')
            xCoords.append(float(coord[0]))
            yCoords.append(float(coord[1]))
    infile.close
    return np.array(xCoords),np.array(yCoords),title 
    
    
def plotOneCurve(title, colour, xValues, yValues):
    '''creates a plot given an array of x coordinates, y coordinates, the title 
    of the plot and the colour of a graph.'''
    #xValues    - An array of x coordinates with a corresponding y coordinates.
    #yValues    - An array of y coordinates with a corresponding x coordinates.
    #title      - The title of the plot.
    #colour     - The colour of the graph.
    #'X'          - The x legend.
    #'Y'          - The y legend.
    plt.figure()
    plt.plot(xValues, yValues, colour)  # create the plot
    plt.xlabel('X')
    plt.ylabel('Y')
    plt.title(title)
    plt.show()
    
    
def plotTwoCurves(xValues1, yValues1, heading1, xValues2, yValues2, heading2):
    '''creates two plots in the same graph given two different sets of array of
     x coordinates, y coordinates for two plots respectively, the two headings
    of the plot respectively.'''
    #xValues1   - An array of x coordinates for the first plot.
    #xValues2   - An array of x coordinates for the second plot.
    #yValues1   - An array of y coordinates for the first plot.
    #yValues2   - An array of y coordinates for the second plot.
    #heading1   - The heading of the  first plot.
    #heading2   - The heading of the  second plot.
    #'X'          - The x legend.
    #'Y'          - The y legend.
    plt.figure()
    plt.plot(xValues1, yValues1, 'g-')  # red solid line
    plt.hold('on')
    plt.plot(xValues2, yValues2, 'b-') # blue solid line
    plt.xlabel('x')
    plt.ylabel('y')
    plt.legend([heading1, heading2], loc='upper right')
    plt.title(heading1+ ' and ' + heading2)
    plt.show()      # display the plot
    

    
def subplotTwoCurves(xValues1, yValues1, title1, xValues2, yValues2, title2):
    '''creates two plots in different graphs given two different sets of array of
     x coordinates, y coordinates for two plots respectively, the two titles
    of the plots respectively.'''
    #xValues1   - An array of x coordinates for the first plot.
    #xValues2   - An array of x coordinates for the second plot.
    #yValues1   - An array of y coordinates for the first plot.
    #yValues2   - An array of y coordinates for the second plot.
    #title1   - The title of the  first plot.
    #title2   - The title of the  second plot.
    #'X'          - The x legend.
    #'Y'          - The y legend.
    plt.figure()
    plt.subplot(2, 1, 1)
    plt.plot(xValues1, yValues1, 'c--')
    plt.xlabel('X')
    plt.ylabel('Y')
    plt.title(title1)

    plt.subplot(2, 1, 2)
    plt.plot(xValues2, yValues2, 'm--') 
    plt.xlabel('X')
    plt.ylabel('Y')
    plt.title(title2)
    plt.tight_layout() 
    plt.show()      # display the plot!
    
    
def main():
    #xCoords1   - An array of x coordinates for the first plot.
    #xCoords2   - An array of x coordinates for the second plot.
    #yCoords1   - An array of y coordinates for the first plot.
    #Coords2   - An array of y coordinates for the second plot.
    #title1   - The title of the  first plot.
    #title2   - The title of the  second plot.
    xCoords1,yCoords1,title1 = readFile('astroid.txt')
    xCoords2,yCoords2,title2 = readFile('circle.txt')
    plotOneCurve(title1, 'k-', xCoords1, yCoords1)
    plotOneCurve(title2, 'r-', xCoords2, yCoords2)
    plotTwoCurves(xCoords1, yCoords1, title1, xCoords2, yCoords2, title2)
    subplotTwoCurves(xCoords1, yCoords1, title1, xCoords2, yCoords2, title2)
    displayTerminationMessage()
main()