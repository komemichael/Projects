# MichaelOghenekomeA3Q1.py 
# 
# Course:      COMP 1012 
# Instructor:  Randy Cooper 
# Assignement: 3 Question 1
# Author:      Michael Oghenekome Victor 
# Version:     2014/06/23 
# 
# Purpose:     The purpose of this python program is to compute the exact
#              approximate circumference of an ellipse using infinite series
#              which converges quickly so the circumference of the ellipse is known

from time import ctime
from math import pi

print '---------------------------------------------------------------------'

def displayTerminationMessage():        #Terminate the program.
    print'''
programmed by Michael Oghenekome Victor.
Date: %s
End of processing.'''% ctime()

def circumference(a, b, tolerance):
    '''A function that computes the circumference of the ellipse given the 
    semi-major axis, a, the semi-minor axis,b , and the tolerance'''
    #h       - A value used in the computation to evaluate the circumference
    #factor1 - The factor of the numerator which is increased after every loop
    #factor  - The factor of the denominator which is increased after every loop
    #a       - length of the semi major axis
    #b       - length of the semi minor axis
    #term    - The Value of the term to be added to the series after every loop
    #total   - The sum of each term in the series after a specific loop
    #circumferenceEllipse - The value of the circumference of the ellipse 
    #tolerance            - the value to which term is being controled by in the loop
    h= (a-b)**2/(a+b)**2
    factor1=1
    factor=1
    total=0.0
    term=(1./4)*h
    while abs(term)> tolerance:
        total+=term
        term=term*h*(factor1*(factor1+1)/((factor+1)*factor*4.))**2
        factor1+=2
        factor+=1
    circumferenceEllipse=pi*(a+b)*(1+ total)
    # get the circumference of the ellipse   
    return circumferenceEllipse

def getPositiveNumber(prompt,EOF):
    '''return a positve number which is also an integer given a prompt and EOF'''
    #number - The value for prompt to be assigned to.
    #prompt - A string for getting input.
    #EOF    - End of file to control the loop.
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
    
def main():
    #EOF       - End of file to control the loop 
    #a         - length of the semi major axis
    #b         - length of the semi minor axis
    #tolerance - the value to which term is being controled by in the loop
    #circumferenceEllipse - The value of the circumference of the ellipse 
    EOF= -1
    print '''To terminate the program enter 0 after any prompt.'''
    tolerance = float(getPositiveNumber('\nEnter the value of tolerance (> 0 ): ', EOF))
    while tolerance >= 1:
        print tolerance,'''The value of tolerance greater than or equal to one'''
        tolerance= float(getPositiveNumber('\nEnter the value of tolerance (> 0 ): ', EOF))
        break
    while tolerance!= EOF:
        a= float(getPositiveNumber('Enter the length of the semi-major axis in cm (> 0): ',EOF))
        if a== EOF:
            break
        b= float(getPositiveNumber('Enter the length of the semi-minor axis in cm (> 0): ',EOF))
        if b== EOF:
            break
        circumferenceEllipse= circumference(a, b, tolerance)
        print '\nThe circumference of the ellipse is %.14e cm.'%circumferenceEllipse
    displayTerminationMessage()
main()