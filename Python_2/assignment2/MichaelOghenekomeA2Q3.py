    # -*- coding: utf-8 -*-
# MichaelOghenekome.py 
# 
# Course:      COMP 1012 
# Instructor:  Randy Cooper 
# Assignement: 2 Question 3 
# Author:      Michael Oghenekome Victor 
# Version:     2014/06/23 
# 
# Purpose:     	The purpose of this question is to write a complete Ptyhon 
#              program that create four	letter combinations(word)
# 

from time import ctime
from wordTable import matchWord                   #calls matchWord from wordTable

def displayTerminationMessage():                    #displays Termination message
    print'''
programmed by Michael Oghenekome Victor.
Date: %s
End of processing.'''% ctime()

def findWords():
#   SecretWords - The list of 
#   numWords - The number of secret words in the list.
#   numSecret - The total number of four letter words that was created
#   vowels - The vowel letters of the alphabet.
#   consonant - The consonant letters of the alphabet
#   Word - The combination of the letters.
#   secretWords - A list of secret words.
    secretWords=[]
    numSecret= 0
    numWords= 0
    vowels= ('a','e','i','o','u')
    consonant= ('b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z')
    for l1 in consonant:
        for l2 in vowels:
            for l3 in vowels:
                for l4 in consonant:
                    word= l1+l2+l3+l4                #adding the letters to make word
                    if matchWord(word):
                        secretWords.append(word)     #geting the list of words
                        numSecret+=1                 #count possible secret
                    numWords+=1                      #count words
    return secretWords,numSecret,numWords

def displayWords(secretWords,numSecret,numWords):
    #secretWords - A list of secret words.
    #numWords - The number of secret words in the list.
    #numSecret - The total number of four letter words that was created
    #words - The value compnents of the srcret words gets assigned to.
    for words in secretWords:
        print '%4s'%words                           # four letter words.
        
    print '\n%d out of %d possible words were found in the table of secret words.'%(numSecret,numWords)
    
def main():
    #secretWords - A list of secret words.
    #numWords - The number of secret words in the list.
    #numSecret - The total number of four letter words that was created
    print '------------------------------------------------------------------------\n'
    secretWords,numSecret,numWords = findWords()
    print 'The secret words are:'
    displayWords(secretWords,numSecret,numWords)
    displayTerminationMessage()
main()