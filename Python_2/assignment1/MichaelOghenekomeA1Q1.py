# MichaelOghenekomeA1Q1.py
#
# course:  Comp 1012
# Insturctor:  Randy Cooper
# Assignment:  1 Question 1
# Author:  Michael Oghenekome  Victor
# Version: 2014/05/20
#
# Purpose:The purpose is to write a complete python program thatcalculates and 
#   displays:
#   -The area and circumference of a circle given the radius of the circle
#   -The volume and surface area of a sphere given the radius of the sphere
#   -The volume and surface area of a cylinder given the radius and height of 
#       the cylinder
#   -The volume and surface area of a cone given the radius and height of the cone
#
# Var1- The use/meaning o each variable in program
# r - The radius of the object
# h - The height of the object
# Area - The area of the object
# Circumference - The length of the circumference of the circle
# Volume - The volume of thne object
# surface area- The solid area of the surface of the object
from time import ctime 
from math import pi, sqrt
print '\n----------------------------------------------------------------\n'
#the radius of the ball is r in cm
#the height of the ball is h in cm
r= float(raw_input('Enter the radius, r, in cm: '))
h= float(raw_input('Enter the height, h, in cm: '))
area= pi*r*r
circumference= 2*pi*r
print '''
For a circlre of radius %g cm:
The area is %.14e cm^2
The circumference is %.14e cm'''%(r,area,circumference)

volume= 4*pi*r**3/3
surfacearea= 4*pi*r**2
print'''
For a sphere of radius %g cm:
The surface area is %.14e cm^2
The volume is %.14e cm^3'''%(r,surfacearea,volume)

volume_cy= h*pi*r**2
surface_cy= (2*pi*r*h)+(2*pi*r**2)
print'''
For a cylinder of radius %g cm and height %g cm:
The surface area is %.14e cm^2
The volume is %.14e cm^3'''%(r,h,surface_cy,volume_cy)

volume_cone= (pi*r**2*h)/3
surface_cone= pi*r*(r+sqrt(h**2+r**2))
print'''
For a cone of radius %g cm and height %g cm:
The surface area is %.14e cm^2
The volume is %.14e cm^3'''%(r,h,surface_cone,volume_cone)

print'''
Programed by Michael Oghenekome Victor.
Date: %s. 
End of processing.''' % ctime()

        
 