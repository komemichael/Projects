/**
* LAB2 ECE 3790
*
* ECE 3790 SECTION A01
* INSTRUCTOR Bob McLeod
* ASSIGNMENT Lab 3, question 1
* @author Michael Oghenekome
* @version 2017-March-15
*
* PURPOSE: The purpose of this java program is to 
*            Compare the run times to see how the runtime of a greedy
*               algorithm changes with variance in size
* 
*  References:
*             Lab 2, ECE3790. Written by Kome Michael - 
*/
import java.util.*;
import java.io.*;
import java.lang.*;


public class UtilityFunctionSA7750064{
  public static void main(String[] args){
    
    String prompt ="Select which Ajacency Matrix you want to solve: \n" +  
                         "(1) For AdjMatAsym.txt\n(2) For AdjMatCC.txt\n(3) For AdjMatAsym.txt\n" + 
                         "Check Program directory for .CSV file and open with excel";
    
    System.out.println(prompt);

    String fileName1 = "AdjMatAsym.txt";
    String fileName2 = "AdjMatCC.txt";
    String fileName3 = "AdjMatRand.txt";
    
    Scanner kbd = new Scanner(System.in);
    int input = kbd.nextInt();
    
    while (input != 0)
    {
      if (input == 1)
      {
        int[][] graph1 = createGraphUtility(fileName1); 
        int size1 = graph1.length;
        int halfSize1 = size1/2;
        int start1 = 0;
        int[] list01 = createRandom(halfSize1, start1);
        int[] list02 = createRandom(halfSize1, halfSize1); 
        int cost1 = simAneal(100,list01,list02,graph1,fileName1);
        printGraph(graph1);
      }
      else if (input == 2)
      {
        System.out.println("\n\n\n");
        
        int[][] graph2 = createGraphUtility(fileName2); 
        int size2 = graph2.length;
        int halfSize2 = size2/2;
        int start2 = 0;
        int[] list11 = createRandom(halfSize2, start2);
        int[] list12 = createRandom(halfSize2, halfSize2); 
        int cost2 = simAneal(100,list11,list12,graph2,fileName2);
        printGraph(graph2);
      }
      else if (input == 3)
      {        
        System.out.println("\n\n\n");
        
        int[][] graph3 = createGraphUtility(fileName3); 
        int size3 = graph3.length;
        int halfSize3 = size3/2;
        int start3 = 0;
        int[] list21 = createRandom(halfSize3, start3);
        int[] list22 = createRandom(halfSize3, halfSize3); 
        int cost3 = simAneal(100,list21,list22,graph3,fileName3);
        printGraph(graph3);
      }
      System.out.println(prompt + "\nEnter 0 to quit: ");
      input = kbd.nextInt();
    }
    System.out.println("\nEnd of Program");
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
   * simAneal- rearranges components in two lists to try to get the minimum cost of the connection between them.
   * @param list1 - an instance of first list the list representing an edge.
   * @param list2 - an instance of second list the list representing an edge.
   * @param graph - an ajacency matrix to chec if  the re is a connection between two nodes.
   * @param tempcost - a calculated cost from the computed calculated cost to be compared with cost.
   * @param cost - the current cost of the connection between the two compnent list.
   * @param num1 - a temporal random number to select a random position to make a swap of elements.
   * @return - int the cost of the interconnection between the two lists.
   */
  public static int simAneal(int probSize, int[] list1, int[] list2, int[][] graph, String filename)
  {
    int cost = Integer.MAX_VALUE;
    double controlParam = 400;
    int epoch = 40;//100
    String storeFilename = "the-file-name.csv";
    try{
      PrintWriter writer = new PrintWriter(storeFilename, "UTF-8");

      for(int k = 0; k < probSize; k++)
      { 
        int i = 0;
        while( i < epoch )
        {
          int num1 = (int)(Math.random() * list1.length - 1);
          //int num2 = (int)(Math.random() * list1.length - 1);
          swap(list1, list2, num1);//,num2);
          
          int tempCost = calcCost(list1,list2,graph);
          
          if(tempCost <= cost)
          {
            cost = tempCost; // swap immediately
          }
          else 
          {
            double random = Math.random();
            if (Math.exp((cost - tempCost)/controlParam) > random)
            {
              cost  = tempCost;
            }
            else
            {
              i++;
              swap(list1, list2, num1);
            }
          }
          writer.println(cost + "," + controlParam);
        }
        controlParam = controlParam * 0.9;
      }
      System.out.println("Cost for " + filename + " matrix is : " + cost);
      writer.close();
    } catch (IOException e) {
      System.out.println("Error");
    }
 
    return cost;
  }
  

  
  /**
   * createRandom - creates the array of the input size of random numbers.
   * @param list - an instance of the list to be created and modified.
   * @param size - size of the matrix or number of nodes of the graph.
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
  
  /**
   * printGraph - displays in the output the elements of the graph matrix.
   * @param i - the loop counter. 
   * @param j - the loop counter. 
   * @param grpah - the graaph to be printed
   * @return - void
   *////
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