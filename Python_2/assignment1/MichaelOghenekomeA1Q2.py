# MichaelOghenekomeA1Q2
#
# course: Comp1012
# Instructor: Randy Cooper
# Assignment: 1 Question 1
# Authour: Michael Oghenekome Victor 
# version:2014/05/20
#
# purpose: Thepurpose of this program is to calculate and display the value of 
#         a complex number using cartesian coordinate, polar coordinate and Euler
#         representation.
#
# Var1:     The use/meaning of each variable in the program
# a:        the real part of the omplex number
# b         The imaginary part of the complex number
#countC     The number of extra term in the cosine series
#countS     The number of extra term in the sine series
#countE     The number of extra term in the e series
#term       The value of the term in the nth position
#factor     The value for which the next term is to be calculated

from time import ctime
from math import sqrt, sin, cos, atan
from cmath import exp
print '\n------------------------------------------------------------------\n'
#a is the real part of the complex number 
#b is the imaginary part of the complex number
a= float(raw_input('Enter the value of the real part of the complex number: '))
b= float(raw_input('Enter the value of the imaginary part of the complex number: '))
TOLERANCE= float(raw_input('Enter the value of the tolerance: '))
C= a + b*1j
print '''
The complex number is %r'''%(C)

print '\n'
r= sqrt(a*a+ b*b)               # the radius of the complex number
theta= atan(b/a)                # the angle in radians between r and the real part
c = r * (cos(theta) + sin(theta) * 1j)
print "   Python's value of r(cos(theta)+i*sin(theata)) is %r"% c

# for cosine series
x= theta
factor= 1
cosine=0.0
term= 1
xsquared= x* x
countC= 0
while abs(term) > TOLERANCE:
    cosine+= term
    countC+=1
    term= term * -xsquared/ (factor* (factor+ 1))
    factor+=2

# for sine series
x= theta
factor= 2
sine= 0.0
term = x
xsquared= x* x
countS= 0
while abs(term) > TOLERANCE:
    sine+= term
    countS+=1
    term = term * -xsquared/(factor* (factor+ 1))
    factor+= 2


c = r * (cosine + sine * 1j)
print 'The approximation to r(cos(theta)+i*sin(theata)) is %r'% c

# for e series
x= theta *1j 
e= 0.0
factor= 1
term=1
countE= 0
while abs(term) > TOLERANCE:
    e+= term
    countE+= 1
    term = term * x/factor
    factor+=1
print'''
  Python's value for r(e^(ix)) is %r'''%(exp(x) * r)

c2 = r * e
print 'The approximation to r(e^(ix)) is %r'% c2

print'''
The number of terms in the series for sin(theta) is %d
The number of terms in the series for cos(theta) is %d
The number of terms in the series for e^(ix) is %d'''%(countS,countC,countE)


print"""
Programmed by Michael Oghenekome Victor.
Date: %s
End of processing.""" % ctime()




