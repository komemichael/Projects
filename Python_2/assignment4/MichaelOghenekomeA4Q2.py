# MichaelOghenekomeA4Q2.py 
# 
# Course:      COMP 1012 
# Instructor:  Randy Cooper 
# Assignement: 4 Question 2
# Author:      Michael Oghenekome Victor 
# Version:     2014/07/21 
# 
# Purpose:     The purpose of this question is to write a complete python 
#              program that computes the area under a curve using the trapezoid 
#              technique and simpson's rule, calculate the difference between 
#              the areas and to plot the curve using arrays of x and y
#              coordinates.

from time import ctime
from sys import path
from os import chdir                 # Establish the current working directory
chdir(path[0])
import numpy as np
import matplotlib.pyplot as plt 
from numpy import pi,cos,sin

print '----------------------------------------------------------------------'


def displayTerminationMessage():                        #Terminate the program.
    print'''
programmed by Michael Oghenekome Victor.
Date: %s
End of processing.'''% ctime()

def getPositiveNumber(prompt):
    '''return a positve number which is also an integer given a prompt.'''
    #number - The value for prompt to be assigned to.
    #prompt - A string for getting input.
    while True:
        number = raw_input(prompt).strip()
        if number != '':
            try:
                number = eval(number, {}, {})
            except: # handle the error
                print '%r is not valid!'%number
            else:    # no error, do this
                if type(number) is int or type(number) is float:
                    if number>=0:
                        break
                    else:
                        print number, 'is less than zero!'
                else:
                    print '%r is not a number!'%number
        else:
            print 'missing input!'
    return number
    
def plotCurves(xCoords,yCoords,title,fileName):
    '''creates a plot given an array of x coordinates, y coordinates, the title 
    of the plot and the name of a file.'''
    #xCoords    - An array of x coordinates with a corresponding y coordinates.
    #yCoords    - An array of y coordinates with a corresponding x coordinates.
    #title      - The title of the plot.
    #filename   - The name of the file to which the coordinates originates.
    #'x'          - The x legend.
    #'F(x)'       - The y legend.
    plt.figure()
    plt.plot(xCoords, yCoords, 'b-')  # create the plot
    plt.xlabel('x')
    plt.ylabel('F(x)')
    plt.title(title)
    plt.savefig(fileName +'.png') # png file
    plt.show()
    
def computeCoordinates(scaleFactor, intervals):
    '''Creates and return an array of x cordinates and y coordinates given the 
    scale factor and the number of intervals.'''
    #scaleFactor - The size of the radius in calculating the x and y coordinates.
    #angle       - The angle of the corresponding x and y coordinates.
    #intervals   - The number of intervals used to determine the number of points
    #xCoords     - An array of x coordinates.
    #yCoords     - An array of y coordinates.
    angle = np.linspace(pi, 0, intervals+1)
    xCoords = scaleFactor * cos(angle) ** 3
    yCoords = scaleFactor * sin(angle) ** 3
    return xCoords,yCoords
    
def trapezoids(xCoords,yCoords):
    '''This function creates and return the area of a shape given the x
    coordinate and the corresponding y coordinates using the trapezoid 
    technique.'''
    #xCoords     - An array of x coordinates.
    #yCoords     - An array of y coordinates.
    #deltaX      - The size difference between two successive intervals.
    #vectorArea  - The area of the shape using trapezoid technique.
    deltaX= (xCoords[-1]-xCoords[0])/len(xCoords)
    vectorArea = (np.sum(yCoords[1:] + yCoords[:-1]) * deltaX)/2.
    return vectorArea
    
def simpson(xCoords,yCoords):
    '''This function creates and return the area of a shape given the x
    coordinate and the corresponding y coordinates using the simpson's rule''' 
    #xCoords     - An array of x coordinates.
    #yCoords     - An array of y coordinates.
    #h           - The size difference between two successive intervals.
    #simpsonArea - The area of the shape using simpson's rule.
    h= (xCoords[-1]-xCoords[0])/len(xCoords)
    simpsonArea=(h/3. *(yCoords[0]+ yCoords[-1]+ 4*np.sum(yCoords[1:-1:2])+ 2*np.sum(yCoords[2:-2:2])))
    return simpsonArea
    
def main():
    #xCoords     - An array of x coordinates.
    #yCoords     - An array of y coordinates.
    #intervals   - The number of intervals used to determine the number of points
    #scaleFactor - The size of the radius in calculating the x and y coordinates.
    #simpsonArea - The area of the shape using simpson's rule.
    #vectorArea  - The area of the shape using trapezoid technique.
    #difference  - The difference in the two areas given the same parameters.
    intervals= int(getPositiveNumber('Enter the number of intervals (> 0): '))
    scaleFactor = float(getPositiveNumber('Enter the scale factor (> 0): '))
    xCoords,yCoords = computeCoordinates(scaleFactor, intervals)
    plotCurves(xCoords,yCoords,'The Upper Half of an Astroid','astroidHalf')
    vectorArea = trapezoids(xCoords,yCoords)
    simpsonArea = simpson(xCoords,yCoords)
    print '\nThe area under the curve using the trapezoid technique is %.14e'%vectorArea
    print "       The area under the curve using simpson's method is %.14e"%simpsonArea
    difference= abs(vectorArea-simpsonArea)
    print '                   The difference between the two area is %e'%difference
    
    displayTerminationMessage()
main()