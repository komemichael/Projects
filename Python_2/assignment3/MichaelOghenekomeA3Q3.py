# MichaelOghenekomeA3Q3.py 
# 
# Course:      COMP 1012 
# Instructor:  Randy Cooper 
# Assignement: 3 Question 3
# Author:      Michael Oghenekome Victor 
# Version:     2014/06/23
# 
# Purpose:     THe purpose of this python program is to sum the length of 
#              the hypotenuses of very small triangles to compute the 
#              approximate circumference of an ellipse using arrays without
#              vector arithmetics and checking the time it took to compute the
#              circumference of the ellipse.

from time import ctime
from math import sqrt
import numpy as np
import timeit

print '---------------------------------------------------------------------'

def displayTerminationMessage():        #Terminate the program.
    print'''
programmed by Michael Oghenekome Victor.
Date: %s
End of processing.'''% ctime()

def getPositiveNumber(prompt,EOF):
    '''return a positve number which is also an integer given a prompt and EOF'''
    #number - The value for prompt to be assigned to.
    #prompt - A string for getting input.
    #EOF    - End of file to control the loop
    while True:
        number = raw_input(prompt).strip()
        if number != '':
            try:
                number = eval(number, {}, {})
            except:                                     # handle the error
                print '%r is not valid!'%number
            else:                                       # no error, do this
                if type(number) is int or type(number) is float:
                    if number<0:
                        print '%g is less than zero!'%number
                    else:
                        if number==0:
                            number= EOF
                            break
                        else:
                            break      
                else:
                    print '%r is not a number!'%number
        else:
            print 'missing input!'
        
    return number

def computeXcoordinates(a, intervals):
    '''A function to create and return an array of x values given the 
    value of the semi major values, a ,and number of intervals'''
    #xValues   - An array displaying x values
    #a         - length of the semi major axis
    #intervals - the number of elements in a list or array
    xValues= np.linspace(0,a,intervals+1)   
    return xValues
    
def computeYcoordinates (a, b, xValues):
    '''A function to create and return a list of y values given the 
    value of the semi major values, a , the semi minor axis ,b ,and x values'''
    #xValues   - An array displaying x values
    #yValues   - An array displaying y values
    #a         - length of the semi major axis
    #b         - length of the semi minor axis
    #x         - the new element to be appended to the list of xValues
    yValues=[]
    for x in xValues:
        y= b* sqrt(1-(x**2/a**2))
        yValues.append(y)
    return np.array(yValues)
    

def calcCircumference(a, b, intervals):
    '''A function to create the circumference of an ellipse given the 
    value of the semi major values, a , the semi minor axis ,b ,and intervals'''
    #xValues   - An array displaying x values
    #yValues   - An array displaying y values
    #yP        - the value to which y gets assigned to after going through the loop
    #            successively
    #y         - the value for yValues to get assigned to
    #a         - length of the semi major axis
    #b         - length of the semi minor axis
    #intervals - the number of elements in a list or array
    #circumference - the circumference of a quater of the ellipse
    #Circumference - the circumference of the ellipse
    xValues= computeXcoordinates(a, intervals)
    yValues= computeYcoordinates (a, b, xValues)
    circumference=0
    yP= b                                                  #first value for yP
    deltaX= a/intervals
    for y in yValues[1:]:
        circumference+= sqrt(((yP-y)**2)+(deltaX**2))
        yP=y
    Circumference= circumference*4
    print '\nThe approximate circumference of the ellipse is %.14e cm,'%Circumference
    
#a         - length of the semi major axis
#b         - length of the semi minor axis
#intervals - the number of elements in a list or array 
#EOF       - End of file to control the loop   
#computeTime -  A function that computes the time python took to run the program  
#t         - the actual time taken to run the  program.
EOF = -1 
print 'To terminate the program enter 0 after any prompt.'
while True:
    a= float(getPositiveNumber('Enter the length of the semi-major axis in cm (> 0): ',EOF))
    if a== EOF:
        break                                               #break the loop
    b= float(getPositiveNumber('Enter the length of the semi-minor axis in cm (> 0): ',EOF))
    if b== EOF:
        break                                               #break the loop
    intervals= int(getPositiveNumber('Enter the number of intervals (> 0): ',EOF))
    if intervals== EOF:
        break                                               #break the loop
    #TIme computation
    t = timeit.Timer('calcCircumference(a, b, intervals)',          #displays the time
    'from __main__ import calcCircumference, a, b, intervals')      #to calculate the 
    computeTime = t.timeit(1)                                       #circumference
    print "The compute time using array is %.3f seconds." % computeTime
displayTerminationMessage()