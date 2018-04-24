# MichaelOghenekomeA4Q1.py 
# 
# Course:      COMP 1012 
# Instructor:  Randy Cooper 
# Assignement: 4 Question 1
# Author:      Michael Oghenekome Victor 
# Version:     2014/07/21 
# 
# Purpose:     The purpose pf this question is to write a complete python 
#              program that computes the volume of a cone using MonteCarlo 
#              technique. We also calculate the actual volume using the same 
#              parameter and find the difference between them.
 	

from time import ctime
from numpy.random import uniform 
from numpy import sum,pi

print '\n-----------------------------------------------------------------------'

def displayTerminationMessage():                        #Terminate the program.
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
    
def monteCone(radius,height,points):
    '''Returns the volume of a cone and the probability of finding a point inside 
    a cone, given the radius of the cone, the height and the number of points to 
    calculate'''
    #x      - The random points generated along side corresponding y and z point
    #y      - The random points generated along side corresponding x and z point
    #z      - The random points generated along side corresponding y and x point
    #height - The height of the cone.
    #radius - The radius of the base of the cone.
    #points - The nmber of random points to create.
    #inCone - The boolean logic if a point is in the cone to every corresponding 
    #          x, y and z point generated
    #probaility - The probability of a number of points being inside the cone.
    #volume - The volume of the cone.
    y = uniform(0,height,points)
    x = uniform(-radius, radius, points)
    z = uniform(-radius, radius, points)
    inCone = (x*x+z*z)<=((height- y)*radius/height)**2
    probability = float(sum(inCone)) / points
    volume = probability * (4*radius*radius*height)
    return probability, volume

def main():
    #EOF    - End of file to control the loop
    #height - The height of the cone.
    #radius - The radius of the base of the cone.
    #points - The nmber of random points to create.
    #inCone - The boolean logic if a point is in the cone to every corresponding 
    #          x, y and z point generated
    #probaility - The probability of a number of points being inside the cone.
    #volume - The volume of the cone.
    #actualVolume - The real volume of the cone given the same parameters.
    #error  - The difference between the actual volume and volume calculated 
    #       - using monte carlo technique
    EOF = -1
    print 'To terminate the program enter 0 after any prompt.'
    while True:
        radius =float(getPositiveNumber('Enter the radius of the cone in cm (>0): ',EOF))
        if radius==EOF:
            break
        height =float(getPositiveNumber('Enter the height of the cone in cm (>0): ',EOF))
        if height==EOF:
            break
        points =int(getPositiveNumber('Enter the number of random points to create (>0): ',EOF))
        if points==EOF:
            break
        probability, volume= monteCone(radius,height,points)
        
        actualVolume = height*pi*radius*radius/3.
        
        error = abs(volume - actualVolume)
        
        print '\nThe probability of a point being inside the cone is %.4f'%probability
        
        print 'The approximate volume of the cone is %.14e cm^3'%volume
        
        print '     The actual volume of the cone is %.14e cm^3'%actualVolume
        
        print '    The error in the approximation is %e cm^3'%error
        
    displayTerminationMessage()

main()