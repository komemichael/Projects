/**
* LAB2 ECE 3790
*
* ECE 3790 SECTION A01
* INSTRUCTOR Bob McLeod
* ASSIGNMENT Lab 1, question 1
* @author Michael Oghenekome
* @version 2017-February-29
*
* PURPOSE: The purpose of this java program is to 
*            Compare the run times to see how the runtime of a greedy
*               algorithm changes with variance in size
* 
*  References:
*             Lab 1, ECE3790. Written by Kome Michael - 
*/
import java.util.*;
import java.io.*;
import java.lang.*;


public class OghenekomeMichael7750064Lab2{
  public static void main(String[] args){

    String fileName1 = "Adjacency Matrix";
    double constant1, start11, end1;
    double constant2, start21, end2;
    double constant3, start31, end3;
    
    int size1 = 100; 
    int probSize = 50;
    int[][] graph1 = create2dGraph(size1);
    int halfSize1 = size1/2;
    int start1 = 0;
    int[] list01 = createRandom(halfSize1, start1);
    int[] list02 = createRandom(halfSize1, halfSize1); 
    start11 = System.nanoTime();
    int cost1 = greed(probSize,list01,list02,graph1,fileName1);
    end1  = System.nanoTime();
    constant1 = end1 - start1;
    System.out.println("Time taken to run greed algorithm for "
                         + size1 + " components and problem size of "
                         + probSize + " is " + constant1 + " nS");
    
    
    System.out.println("\n\n\n");
    
    int size2 = 100; 
    int probSize2 = 100;
    int[][] graph2 = create2dGraph(size2);
    int halfSize2 = size2/2;
    int start2 = 0;
    int[] list21 = createRandom(halfSize1, start1);
    int[] list22 = createRandom(halfSize1, halfSize1); 
    start21 = System.nanoTime();
    int cost2 = greed(probSize2,list21,list22,graph2,fileName1);
    end2  = System.nanoTime();
    constant2 = end2 - start21;
    System.out.println("Time taken to run greed algorithm for "
                         + size1 + " components and problem size of "
                         + probSize2 + " is " + constant2 + " nS");
    
    System.out.println("\n\n\n");
    
    int size3 = 200; 
    int probSize3 = 50;
    int[][] graph3 = create2dGraph(size3);
    int halfSize3 = size3/2;
    int start3 = 0;
    int[] list31 = createRandom(halfSize3, start3);
    int[] list32 = createRandom(halfSize3, halfSize3); 
    start31 = System.nanoTime();
    int cost3 = greed(probSize3,list31,list32,graph3,fileName1);
    end3  = System.nanoTime();
    constant3 = end3 - start31;
    System.out.println("Time taken to run greed algorithm for "
                         + size3 + " components and problem size of "
                         + probSize3 + " is " + constant3 + " nS");

  }
  
   /**
   * createGraphUtility - create a two dimentional array of integers from a file.
   * @param filename - input file
   * @param inputFile - the a scanner reference to an input file
   * @param graph - array of integers
   */
  public static int[][] createGraphUtility(String fileName){
    int[][] graph = new int[100][100];
    try {
      File inputFile = new File(fileName);
      Scanner inFile = new Scanner(inputFile);
      for (int i = 0; i  < graph.length ; i++) 
      {
        String line = inFile.nextLine();
        for (int j = 0; j < graph.length ; j++)
        {
          String[] split = line.split(" ");   
          graph[i][j] = Integer.parseInt(split[j]);
        }
      }
      inFile.close();
    } catch (IOException e) { 
      System.out.println("Error: " + e.getMessage());
    } 
    return graph;
  }
  
  /**
   * isConnected - checks to see if two nodes are connected by checking for a 0, returns the weight of the connection
   * @param graph - an ajacency matrix to chec if  the re is a connection between two nodes.
   * @param i - the position of element in the first list.
   * @param j - the position of element in the second list.
   * @return - int the weight of the connection vertices if the re is a connection
   */
  public static int isConnected(int i, int j, int[][] graph)
  {
    if (graph[i-1][j-1] > 0 && i <= j)
    {
      return graph[i-1][j-1];
    }
    else
    {
      return 0;
    }
  }
  
  
  /**
   * calcCost - calculates the current state cost of the interconnection of the two lists.
   * @param list1 - an instance of first list the list representing an edge.
   * @param list2 - an instance of second list the list representing an edge.
   * @param graph - an ajacency matrix to chec if  the re is a connection between two nodes.
   * @param cost - the current cost of the connection between the two compnent list.
   * @return - int the cost of the interconnection between the two lists.
   */
  public static int calcCost(int[] list1, int[] list2, int[][] graph)
  {
    int cost = 0;
    
    for (int i = 0; i < list1.length; i++)
    {
      for (int j = 0; j < list2.length; j++)
      {
        cost = cost + (isConnected(list1[i], list2[j], graph));
      }
    }
    return cost;
  }
  
  /**
   * greed - rearranges components in two lists to try to get the minimum cost of the connection between them.
   * @param list1 - an instance of first list the list representing an edge.
   * @param list2 - an instance of second list the list representing an edge.
   * @param graph - an ajacency matrix to chec if  the re is a connection between two nodes.
   * @param tempcost - a calculated cost from the computed calculated cost to be compared with cost.
   * @param cost - the current cost of the connection between the two compnent list.
   * @param num1 - a temporal random number to select a random position to make a swap of elements.
   * @return - int the cost of the interconnection between the two lists.
   */
  public static int greed(int iteration,int[] list1, int[] list2, int[][] graph, String filename)
  {
    int cost = Integer.MAX_VALUE;
    int i = 0;
    while (i < iteration)
    {
      int num1 = (int)(Math.random() * list1.length - 1);
      swap(list1, list2, num1);
      
      int tempCost = calcCost(list1,list2,graph);
      
      if( tempCost > cost)
      {
        cost = cost;
        swap(list1, list2, num1);
      }
      else
      {
        cost = tempCost;
      }
      i++;
    }
    System.out.println("Cost for " + filename + " matrix is : " + cost);
    return cost;
  }
  

  
  /**
   * createRandom - creates the array of the input size of random numbers.
   * @param list - an instance of the list to be created and modified.
   * @param size - size of the matrix or number of nodes of the graph.
   * @param start - the offset on where the data on the list should start from.
   * @return - int[] the array created.
   */
  public static int[] createRandom(int size, int start)
  {
    int[] list = new int[size];

    for (int i = 0; i < list.length; i++)
    {
      list[i] = i+start + 1;
    }
    return list;
  }
  
  /**
   * create2dGraph - creates the 2D array of the input size of random numbers.
   * @param graph - an instance of the graph to be created and modified.
   * @param size - size of the matrix or number of nodes of the graph.
   * @return - int[][] the array created.
   */
  public static int[][] create2dGraph(int size)
  {
    int[][] graph = new int[size][size];
    for (int i =0; i < size; i++)
    {
      for (int j = 0 ; j < size; j++)
      {
        if( i==j )
        {
          graph[i][j] = 0;
        }
        else if ( i < j )
        {
          graph[i][j] = (int)(Math.random() * 9 + 1);
          graph[j][i] = graph[i][j];
        }
      }
    }
    return graph;
  }
  
  
  /**
   * swap - swaps the elements of two numbers at a particular position in two arrays.
   * @param in1 - data at position of first element to swap.
   * @param in2 - data at position of second element to swap.
   * @param temp - a temporary element to store the data of the first element.
   * @param pos - the position to swap both values.
   * @return - void
   */
  public static void swap(int[] in1, int[] in2, int pos)
  { 
     int temp = in1[pos];
     in1[pos] = in2[pos];
     in2[pos] = temp;
     
     temp=0;
    
  }
  


  
/////////////////////////////////////////////////////////////////
  /**
   * printList - displays in the output the elements of the list.
   * @param i - the loop counter. 
   * @param list - the list to be printed
   * @return - void
   */
  public static void printList(int[] list)                    ///
  {                                                           ///
    for(int i = 0; i < list.length; i++)                      ///
    {                                                         ///
      System.out.print(list[i] + ", ");                       ///
    }                                                         ///
  }                                                           ///
       
  ///
  /**
   * printGraph - displays in the output the elements of the graph matrix.
   * @param i - the loop counter. 
   * @param j - the loop counter. 
   * @param grpah - the graaph to be printed
   * @return - void
   */
  public static void printGraph(int[][] graph)                ///
  {                                                           ///
    for(int i = 0; i < graph.length; i++)                     ///
    {                                                         ///
      for (int j = 0 ; j < graph.length; j++)                 ///
      {                                                       ///
        System.out.print(graph[i][j] + " ");                  ///
      }                                                       ///
      System.out.println();
    }                                                         ///
  }                                                           ///
/////////////////////////////////////////////////////////////////
 
  
}