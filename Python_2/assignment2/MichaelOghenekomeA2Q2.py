# -*- coding: utf-8 -*-
# MichaelOghenekome.py 
# 
# Course:      COMP 1012 
# Instructor:  Randy Cooper 
# Assignement: 2 Question 2
# Author:      Michael Oghenekome Victor 
# Version:     2014/06/23 
# 
# Purpose:     The purpose is to write a complete python program that 
#              computes the product of two fractions represented by large
#              integers. each fraction must be to the same number of decimal 
#              places.The number of decimal place must be a positive integer.

from time import ctime
from math import log10

def displayTerminationMessage():        #Terminate the program.
    print'''
programmed by Michael Oghenekome Victor.
Date: %s
End of processing.'''% ctime()

def getPositiveInt(prompt):
    #number - The value for prompt to be assigned to.
    #prompt- A string for getting input.
    while True:
        number=int(raw_input(prompt))
        if number<=0:
            print '%d is not greater then 0'%number
        else:
            break
    return number

def getFraction(one):
    #one - The large integer value that represent 1
    #num - The numerator of the fraction.
    #denom - The denominator of the fraction.
    #fraction - The value of the numerator divided by denominator
    num= int(raw_input('Enter the numerator as an integer: '))
    denom= int(raw_input('Enter the denominator as an integer: '))
    while True:
        if denom == 0:
            print 'The denominator cannot be zero!'
            denom= int(raw_input('Enter the denominator as an integer: '))   
        else:
            fraction=num* one/denom
            if fraction<0:
                fraction= -(abs(num)*one/abs(denom))
            break
    return fraction
        
def multiplyFractions(fraction1, fraction2, one):
    #product- The answer to the mmultipication of the two fractions.
    #fraction1 - The first fraction given to be computed.
    #fraction2 - The second fraction given to be compute.
    product= fraction1 * fraction2/one
    if product <0:
        product= - (abs(fraction1) * abs(fraction2) / one)
    return product
    
def displayFraction(fraction, places):
    #fraction - The large integer that represent the fraction to display
    #places - The number of decimal places to which the fraction is to be displayed
    #sign - The negative or positive value for the fraction
    #intPart - The integer part of the fraction
    #fracPart - The fractional part of the fraction i.e part after the decimal
    if fraction <0:
        sign= '-'
    else: 
        sign= ''
    intPart= abs(fraction)/ 10**places
    fracPart= abs(fraction)% 10**places
    
    if fracPart==0:
        print sign+ str(intPart)
    else:
        print sign + str(intPart) + '.' + '0'*(places - (int(log10(fracPart)) + 1))+ str(fracPart)

def main():
    #places - The number of deccimal places to which the fraction is to be computed to.
    #one- The large integer value that represent 1.
    #fraction1 - The multiplicand.
    #fraction2 - The multiplier.
    #FractionProduct - The value of the product of the two fractions
    print '\n------------------------------------------------------------------'
    places= getPositiveInt('Enter the number of decimal places(>0): ')
    print '\n----------------------------------------------------------------\n'
    print 'Enter the first fraction.'
    one = 10**places
    fraction1= getFraction(one)
    while True:
        if fraction1==0:
            break
        else:
            #dis playing fraction one
            displayFraction(fraction1,places)
            print '\nEnter the second fraction.'
            fraction2= getFraction(one)
            # displaying fraction two
            displayFraction(fraction2,places)
            FractionProduct= multiplyFractions(fraction1,fraction2,one)
            print '\nThe product of the fraction is: '
            displayFraction(FractionProduct,places)
            #re_entering the fractions until fraction equate zero.
            print '\n-------------------------------------------------------------\n'
            print 'Enter the first fraction.'
            fraction1= getFraction(one)
    displayTerminationMessage()
main()
            
            
        
        

    