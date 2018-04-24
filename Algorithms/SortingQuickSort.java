/**
* LAB1 ECE 3790
*
* ECE 3790 SECTION A01
* INSTRUCTOR Bob McLeod
* ASSIGNMENT Lab 1, question 1
* @author Michael Oghenekome
* @version 2017-February-07
*
* PURPOSE: The purpose of this java program is to 
*            Create an ajacency matrix from an input
*              and sparseness parameter.
* 
*  References: http://stackoverflow.com/questions/13727030/mergesort-in-java
*             Assignment 1, Comp2140. Written by Kome Michael - Quick sort.
*             http://www.javatpoint.com/insertion-sort-in-java
*/

import java.io.*;
import java.util.*;
import java.lang.*;

public class SortingQuickSort
{
  public static void main(String[] args)
  {
    
    int[] rand = createRandom(10000);
    //printList(rand);
    double unsorted, startU, endU;
    double sorted, startS, endS;
    double constant, start, end;
    
    start = System.nanoTime();
    int a = 9 ;
    end  = System.nanoTime();
    
    constant = end - start;
    System.out.println(" Constant time is " + constant);
    
    startU = System.nanoTime();
    int[] sort = quickSort(rand);
    endU = System.nanoTime();
    
    unsorted = endU - startU;
    
    startS = System.nanoTime();
    int[] sort1 = quickSort(sort);
    endS = System.nanoTime();
    
    sorted = endS - startS;
    
    
    
      System.out.println("------For unsorted data of " + rand.length + " elements------");
      System.out.println("Algorithm        ||  Runtimes               ||\n_________________________________________________");
      System.out.println("Quicksort          || " + unsorted);
      
      System.out.println("\n\n");
      
      System.out.println("------For sorted data of " + rand.length + " elements------");
      System.out.println("Algorithm        ||  Runtimes               ||\n_________________________________________________");
      System.out.println("Quicksort          || " + sorted );
      
      
    
    System.out.println("END OF PROGRAM by Kome Michael");

  }
  
  /**
   * createRandom - creates the array of the input size of random numbers.
   * @param list - an instance of the list to be created and modified.
   * @param size - size of the matrix or number of nodes of the graph.
   * @return - int[] the array created.
   */
  public static int[] createRandom(int size)
  {
    int[] list = new int[size];
    for (int i = 0; i < size; i++)
    {
      list[i] = (int)(Math.random() * 9999);
    }
    return list;
  }
  
/***********QUICK*********************************************************************/
  /**
   * Assignment 1, Comp2140. Written by Kome Michael - Quick sort.
   * 
   * quickSort - take one slice of array and sorts it using quick sort from start to end-1.
   * swap - a method to swap two items in an array.
   * choosePivot - a method to select the insert value.
   * @param start - the beginning position of the array to be sorted through
   * @param end - the end position of the array to be sorted through
   * @param pivPosn - the position of the value used for comparison
   * @param a - the array to be sorted 
   * @return - void
   */
  private static void quickSort(int[] a, int start, int end){
    int pivPosn;
    if (2 == end - start){
      if(a[start+1] < a[start]){
        swap(a, start, start+1);
      }
    }else if ( 2 < end-start){
      choosePivot( a, start, end);
      pivPosn = partition( a, start, end);
      quickSort( a, start, pivPosn);
      quickSort( a, pivPosn+1, end);
    }
  }
  
  /**
   * partition - this method seperates the list into parts.
   * swap - a method to swap two items in an array.
   * @param pivot - the value to which all selected item is compared to(bigger or smaller).
   * @param start - the beginning position of the array to be sorted through.
   * @param end - the end position of the array to be sorted through.
   * @param bigStart - the position where elements greater than pivot starts.
   * @param curr - the position of the item being compared.
   * @param a - the array to be sorted 
   * @return - void
   */
  private static int partition( int[] a, int start, int end){
    int bigStart = start + 1;
    int pivot = a[start];
    for (int curr = start+1; curr < end; curr++){
      if ( a[curr] < pivot){ 
        swap( a, curr, bigStart);
        bigStart++;
      }
    }
    swap( a, start, bigStart - 1);
    return bigStart-1;
  }
  
  /* swap - swaps the position of two items in an array.
   * @param b - the position of item to be swap with a.
   * @param temp - a temporary value to swap.
   * @param a - the position of item to be swap with b.
   * @param arr = the array where the elements are swapped.
   * @return - void
   */
  public static void swap( int[] arr, int a, int b) {
    int temp = arr[a];
    arr[a] = arr[b];
    arr[b] = temp;
  }
  
  /**
   * choosePivot - this method selects a value to be used as pivot position.
   * swap - a method used to swap two elements from an array.
   * @param start - the beginning position of the array.
   * @param end - the end position of the array.
   * @param a - the array to select pivot from
   * @return - void
   */
  private static void choosePivot(int[] a, int start, int end){
    int pivPosn = start + (end - start)/2;
    swap(a, start, pivPosn);
  }
  
  /**
   * quickSort - take one slice of array and sorts it using quick sort from start to end-1.
   * @param start - the beginning position of the array to be sorted through
   * @param end - the end position of the array to be sorted through
   * @param a - the array to be sorted 
   * @return - void
   */
  public static int[] quickSort(int[] a){
    int start = 0;
    int end = a.length ;
    quickSort( a, start, end );
    return a;
  }
  
  /**
   * printList - displays in the output the elements of the list.
   * @param i - the loop counter. 
   * @param list - the list to be printed
   * @return - void
   */
  public static void printList(int[] list)
  {
    for(int i = 0; i < list.length; i++)
    {
      System.out.print(list[i] + ", ");
    }
  }
  
  
}