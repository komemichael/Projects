# -*- coding: utf-8 -*-
# MichaelOghenekome.py 
# 
# Course:      COMP 1012 
# Instructor:  Randy Cooper 
# Assignement: 2 Question 1 
# Author:      Michael Oghenekome Victor 
# Version:     2014/06/23
# 
# Purpose:     The purpose of the python program is to compute and display
#              the trajectory of a projectile given the initial launch angle,
#              the initial velocity, the initial height and the number of time
#              intervals.The launch angle is specified in degrees. The initial
#              velocity is in meters per seconds and the initial height is in 
#	       metres.The number of time intervals is used to compute the number 
#              of time values for which the X and Y coordinates are to be computed
#              for the trajectory.The output from the program is a table of time
#              values and the corresponding X and Y coordinates of the projectile.

from time import ctime
from math import cos, sin, pi, sqrt

def displayTerminationMessage():                #displays termination message
    print'''
programmed by Michael Oghenekome Victor.
Date: %s
End of processing.'''% ctime()

def getBounded(prompt,lowerBound,upperBound): 
    #prompt - The string raw input is to display to user when requesting input.
    #lowerBound - The lower bound for valid input from the user.
    #upperBound - The upper bound for valid input from the user.
    #number - The value to which prompt gets assigned to.
    while True:
        number= float(raw_input(prompt))
        if number <= lowerBound or number >= upperBound: 
            print '%g is not between %d and %d!'%(number,lowerBound,upperBound)
        else:
            break
    return number

def getPositiveInt(prompt):
    #prompt - The string raw input is to display to user when requesting input.
    #number - The value to which prompt gets assigned to.
    while True:
        number= int(raw_input(prompt))
        if number<= 0:
            print '%d is not greater than zero!'%number
        else:
            break
    return number
      
def getPositiveFloat(prompt):
    #prompt - The string raw input is to display to user when requesting input.
    #number - The value to which prompt gets assigned to.
    while True:
        number= float(raw_input(prompt))
        if number<= 0:
            print '%g is not greater than zero!'%number
        else:
            break
    return number

def getNonNegativeFloat(prompt):
    #prompt - The string raw input is to display to user when requesting input.
    #number - The value to which prompt gets assigned to.
    while True:
        number= float(raw_input(prompt))
        if number<0:
            print '%g is not greater than or equal to zero!'%number
        else:
            break
    return number

def computeVelocities(v0,degrees):
    #v0 - The initial velocity of the projectile in m/s.
    #degrees - The launch angle of the projectile in degrees.
    #theta - The angle converted to radians in radians
    #v0x - The x component of the initial velovity in m/s.
    #v0y - The y component of the initial velocity in m/s.
    theta= degrees * pi/ 180
    v0x= v0*cos(theta)
    v0y= v0*sin(theta)
    return v0x,v0y

def computeDistanceTimeHeight(v0x,v0y,y0):
    #v0x­-The x component of the initial velocity in m/s	.
    #v0y-The y component of the initial velocity in m/s.
    #y0- The height in meters from which the projectile is launched.
    #G -  The acceleration due to gravity in m/s^2.
    #time - The time it takes the projectile to travelthe horizontal distance in seconds
    #distance - The horizontal distance between the launch to landing point in metres
    #peakHeight - The maximum height achieved by the projectiles in metres
    #peakTime - The time the peak height was achieved in seconds.
    G=9.81
    time= (v0y/G)+ sqrt((v0y/G)**2 +(2*y0/G))
    distance= v0x*time
    peakHeight= ((v0y**2)/(2*G))+ y0
    peakTime= v0y/G
    return distance,time,peakHeight,peakTime
    
def computePositionFromTime(distance,time,intervals,v0x,v0y,y0):	
    #intervals - The number of time intervals.
    #v0x - The X component of the initial velocity in m/s.
    #v0y - The Y component of the initial velocity in m/s.	
    #y0 - The height in metres from which the projectile is launched.	
    #G - The acceleration due to gravity of the projectile.
    #time - The time it takes the projectile to travelthe horizontal distance in seconds
    #distance - The horizontal distance between the launch to landing point in metres
    #step - The step size of the loop interval
    #t - The time for each interval
    G=9.81
    Time=[]
    Height=[]
    Distance=[]
    step= time/intervals
    for time in range(intervals+1):
        t= step*time
        Time.append(t)
        height= (v0y*t)-(G*t*t/2)+ y0
        Height.append(height)   
        distance= v0x*t
        Distance.append(distance)
    return Time,Distance,Height                         #getting list for time,height and distance.

def display3Lists(heading1,heading2,heading3,list1,list2,list3):
    #heading3 - The heading to display for the values in list3
    #heading2 - The heading to display for the values in list2
    #heading - The heading to display for the values in list1
    #list1 - The first list of real numbers.
    #list1 - The second list of real numbers
    #list1 - The third list of real numbers
    print '\n%21s %21s %21s'%(heading1,heading2,heading3)
    for l1,l2,l3 in zip(list1,list2,list3):                
        print '%21.14e %21.14e %21.14e'%(l1,l2,l3)   #displaying the list.

def main():
    #lowerBound - The lower bound for valid input from the user.
    #upperBound - The upper bound for valid input from the user.
    #prompt - The string raw input is to display to user when requesting input
    #launchAngle - The angle to which the progectile is released.
    #iniVelocity - The initial velocity of the progectile.
    #y0 - the height in metres in which the projectile is launched.
    #intervals - The number of tim intervals.
    #time - The time it takes the projectile to travelthe horizontal distance in seconds
    #distance - The horizontal distance between the launch to landing point in metres
    #peakHeight - The maximum height achieved by the projectiles in metres
    #peakTime - The time the peak height was achieved in seconds.
    #v0x - The X component of the initial velocity in m/s.
    #v0y - The Y component of the initial velocity in m/s.
    print '-------------------------------------------------------------------'
    lowerBound= 0
    upperBound= 90
    prompt= 'Enter the launch angle in degrees(>0 and <90): '
    launchAngle= getBounded(prompt,lowerBound,upperBound)
    iniVelocity= getPositiveFloat('Enter the initial velocity in m/s(>0): ')
    y0= getNonNegativeFloat('Enter the initial height in meters (>=0): ')
    intervals= getPositiveInt('Enter the number of time intervals (>0): ')
    v0x,v0y= computeVelocities(iniVelocity,launchAngle)
    distance,time,peakHeight,peakTime= computeDistanceTimeHeight(v0x,v0y,y0)
    Time,Distance,Height= computePositionFromTime(distance,time,intervals,v0x,v0y,y0)
    print '''
The distance the object travelled was %.3f meters.
The peak height of the object was %.3f meters.
The time of the peak height was %.3f seconds.
The duration of the flight was %.3f seconds.'''%(distance,peakHeight,peakTime,time)
    display3Lists('Time (seconds)','Distance (meters)','Height(meters)',Time,Distance,Height)
    displayTerminationMessage()   
main()