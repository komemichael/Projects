/**
* LAB2 ECE 3790
*
* ECE 3790 SECTION A01
* INSTRUCTOR Bob McLeod
* ASSIGNMENT Lab 4, question 1
* @author Michael Oghenekome
* @version 2017-March 29
*
* PURPOSE: The purpose of this java program is to 
*            Compare the run times to see how the runtime of a greedy
*               algorithm changes with variance in size
* 
*  References:
*             Lab 4, ECE3790. Written by Kome Michael - 
* */

import java.util.*;
import java.io.*;
import java.lang.*;

public class GeneticAlgorithm
{
  public static void main(String[] args)
  {
    int size = 100;
    int popLength = 100;
    int numGeneration = 1000;
    int crossPosition = 5;
    String filename = "AdjMatAsym.txt";
    
    Solution solution = createPopulations(popLength, size);
    
    int[][] graph = createGraphUtility(filename,100);
  
    //double costSolution1 = averageCost(solution, graph);
    //System.out.println("Initial average cost solution: " + costSolution1);
    
//    System.out.println();
//    System.out.println();
    
    double start = System.nanoTime();
    solution = geneticAlgorithm(solution, size, numGeneration , crossPosition ,popLength, graph); 
    double end = System.nanoTime();
    
    double constant = end - start;
    System.out.println("Time taken to run Genentic Algorithm for "
                         + size + " components and population size of "
                         + popLength + " is " + constant + " nS");
    ////////////////////////////////////////////////////////////////////////////
    System.out.println();
    int size1 = 100;
    Solution solution1 = createPopulations(popLength, size1);
    
    double start1 = System.nanoTime();
    solution1 = geneticAlgorithm(solution1, size1, numGeneration , crossPosition ,popLength, graph); 
    double end1 = System.nanoTime();
    
    double constant1 = end1 - start1;
    System.out.println("Time taken to run Genentic Algorithm for "
                         + size1 + " components and population size of "
                         + popLength + " is " + constant1 + " nS");
    
    ////////////////////////////////////////////////////////////////////////////
    System.out.println();
    int popLength2 = 1000;
    Solution solution2 = createPopulations(popLength2, size);
    
    double start2 = System.nanoTime();
    solution2 = geneticAlgorithm(solution2, size, numGeneration , crossPosition ,popLength2, graph); 
    double end2 = System.nanoTime();
    
    double constant2 = end2 - start2;
    System.out.println("Time taken to run Genentic Algorithm for "
                         + size + " components and population size of "
                         + popLength2 + " is " + constant2 + " nS");
    
    System.out.println();
    double costSolution = averageCost(solution, graph);
    System.out.println("Final average cost solution after GA: " + costSolution);
    
    System.out.println();
    int minCost = minSolution(solution, graph);
    System.out.println("Minimum cost solution after GA: " + minCost);
    testCost(graph);
    
  }
  
  public static int minSolution(Solution population, int[][] graph)
  {
    Node curr = population.head;
    int minCost = Integer.MAX_VALUE;

    while (curr != null)
    {
      if(costSolution(curr.data, graph) < minCost)
      {
        minCost = costSolution(curr.data, graph);
        curr.print();
        System.out.println();
      }
      curr = curr.next;
    } 
    return minCost;
  }
  
  public static void testCost(int[][] graph)
  {
    int[] list = createPopulations(100);
    int cost = costSolution(list, graph);
    
    System.out.println("Cost is " + cost);
  }
  
  
  public static double averageCost(Solution population, int[][] graph)
  {
    Node curr = population.head;
    int cost = 0;
    int i = 0;
    while (curr != null)
    {
      cost = cost + costSolution(curr.data, graph);
      i++;
      curr = curr.next;
    } 
    double averageCost = cost / i;
    return averageCost;
  }
  
     /**
   * createGraphUtility - create a two dimentional array of integers from a file.
   * @param filename - input file
   * @param inputFile - the a scanner reference to an input file
   * @param graph - array of integers
   */
  public static int[][] createGraphUtility(String fileName, int length){
    int[][] graph = new int[length][length];
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
  
  public static Solution geneticAlgorithm(Solution population, int size, int generation, int crossPosition, int popLength, int[][] graph)
  {
    int epoch = 0;
    while (epoch != generation)
    {
      population = crossOver(population, size, crossPosition, popLength);
      population = mutation(population, size, 0.005);
      population = selection(population , popLength, graph);
      epoch++;
    }
    
    
    return population;
  }
  
  
  public static Solution selection (Solution population, int popLength, int[][] graph)
  {
    int i = 0;
    Node curr = population.head;
    Node first = curr;
    Node second = curr.next;
    
    while (i < popLength && curr.next.next != null)
    {
      int firstCost = costSolution(first.data, graph);
      int secondCost = costSolution(second.data, graph);
      
      if(firstCost <= secondCost)
      {
        curr = curr.next.next;
        population.delete(second);
      }
      else
      {
        curr = curr.next.next;
        population.delete(first);
      }

       first = curr;
       second = curr.next;
      
      i++;
    }
    return population;
  }
  
  
  public static Solution mutation(Solution population,int size, double probMutation)
  {
    Node curr = population.head;
    
    while(curr != null)
    {
      if (Math.random() < probMutation)
      {
        int rand = (int)(Math.random() * (size - 1));
        if (curr.data[rand] == 0)
        {
          curr.data[rand] = 1;
        }
        else
        {
          curr.data[rand] = 0;
        }
      }
      curr = curr.next;
    }
    return population;
  }
  
  public static Solution crossOver(Solution population, int size, int crossPosition, int  popLength)
  {
    int[] newList1 = new int[size];
    int[] newList2 = new int[size];
    Node curr = population.head;
    
    int i = 0;
    while(i < popLength)
    {
      for (int j = 0;j < size; j++)
      {
        if (j > crossPosition)
        {
          newList1[j] = curr.data[j];
          newList2[j] = curr.next.data[j];
        }
        else
        {
          newList1[j] = curr.next.data[j];
          newList2[j] = curr.data[j];
        }
      }  
      population.addSolution(newList2);
      population.addSolution(newList1);
      curr = curr.next.next;
      i = i + 2;
    }   
    return population;
  }
  
  public static int costSolution(int[] solution, int[][] graph)
  {
    int cost = 0;
    int penalty = 3;
    for (int i = 0; i < solution.length; i++)
    {
      for (int j = i; j < solution.length; j++)
      {
        if(solution[i] != solution[j])
        {
          if (j > graph.length/2 && i < graph.length/2)
          {
            cost = cost + graph[i][j];
          }
          else if (j < graph.length/2 && i > graph.length/2)
          {
            cost = cost + graph[i][j];
          }
        }
      }
    }
    
    int j = 0;
    int k = 0;
    int difference = 0;
    for (int i = 0; i < solution.length; i++)
    {
      if (solution[i] == 1)
      {
        j++;
      }
      if(solution[i] == 0)
      {
        k++;
      }
    }
    
    if (j > k)
    {
      difference = j - k;
    }
    else
    {
      difference = k - j;
    }
    cost = cost + (penalty * difference);
    return cost;
  }
  
  public static Solution createPopulations(int sizePopulation, int sizeSolution)
  {
    Solution solutions = new Solution(createPopulations(sizeSolution));
    
    for (int i = 0; i < sizePopulation; i++)
    {
      solutions.addSolution(createPopulations(sizeSolution));
    }
    return solutions;
  }
  
  public static int[] createPopulations(int size)
  {
    int[] list = new int[size];
    for (int i = 0 ; i < size; i++)
    {
      if(Math.random() < 0.5) {
        list[i] = 0;
      }
      else
      {
        list[i] = 1;
      }
    }
    return list;
  }
  
  public static void displayOneSolution(int[] solution)
  {
    for(int i= 0; i < solution.length; i++)
    {
      System.out.print(solution[i] + " ");
    }
  }
  
}






class Node
{
  int[] data;
  Node next;
  
  public Node(Node next, int[] data)
  {
    this.data = data;
    this.next = next;
  }
  
  public void print()
  {
    for (int i = 0; i < data.length; i++)
    {
      System.out.print(data[i]);
    }
  }
}






class Solution
{
  int[] data;
  Node head;
  Node curr;
  
  public Solution(int[] data)
  {
    head = new Node(head, data);
  }
  
  public void addSolution(int[] solution)
  {
    if(curr != null)
    {
      Node newNode = new Node(curr.next, solution);
      curr.next = newNode;
      curr = newNode;
    }
    else
    {
      head = new Node(null, solution);
      curr = head;
    }
  }
  
  public void delete(Node node)
  {
    if (node == head)
    {
      head = head.next;
    }
    
    Node prev = head;
    curr = head.next;

    while (curr.next != null)
    {

      if(curr == node)
      {
        prev.next = curr.next;
      }
      else
      {
        prev = prev.next;
      }
      curr = curr.next;
    }
  }
  
  public int[] getData()
  {
    return this.data;
  }
  
  public void setData(int[] data)
  {
    this.data = data;
  }
  
  public void print()
  {
    for (Node curr = head; curr != null; curr = curr.next)
    {
      int i = 0;
      while (i < curr.data.length)
      {
        System.out.print(curr.data[i] + " ");
        i++;
      }
      System.out.println();
    }
  }
}