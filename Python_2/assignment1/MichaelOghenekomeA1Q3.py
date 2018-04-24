# MichaelOghenekomeA1Q3
#
# Course: Comp1012
# Instructor: Randy Cooper
# Assignment: 1 Question 3
# Author: Michael Oghenekome Victor
# version: 2014/05/20
#
# purpose:
# var1:     The use/ meaning of
# places    The number of decimal places to calculate n
# power     The power to thich n is to be computed to
# extra     The value of the extra decimals to get a more accurate result
# term      The value of the term of the nth position in the series
# num         The index to which the next term is to be calculated
#count      The number of terms at a particular time in the series
# intpart   The integer part of the real number
# frapart   The fractional part of the real number
from time import ctime
from math import log
print '\n-------------------------------------------------------------------\n'
n= int(raw_input('Enter the value of n: '))
places= int(raw_input('Enter the number of decimals to which n is to be computed: '))
x=1
power=10**places
extra=10**4
term= power * extra * n / 2
num=0
count=0
while term>0:
    num+= term
    count+=1
    term= term * (x+1)/(x*(x+2))
    x+=1
num= num/extra
intpart=num/ power
frapart= num- intpart * power
num= str(intpart)+ '.' + '0' * (places - (int(log(frapart))+ 1))+ str(frapart)

print'''
the approximate value of %g to %d decimal places is:
%s 

The number of terms in the series is %d'''%(n,places,num,count)

# The program doesn't output the exact value of n because, we are representing a 
#    real number by a value to an infinite series by printing the integer part 
#    and fractional part seperately after dividing by power, merging both part and
#    adding the leading zero with the log function
    
print"""
Programmed by Michael Oghenekome Victor.
Date: %s
End of processing.""" % ctime()
