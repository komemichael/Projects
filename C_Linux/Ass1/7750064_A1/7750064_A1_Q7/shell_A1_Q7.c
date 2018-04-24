/*
    shell.c
    ------
        Simple example of some code that creates processes ...
*/

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>
#include <pthread.h>
#include <ctype.h>
#include <limits.h>

//global variable
int numSubElements;


//define thread
typedef struct thread {
	pthread_t thread_id;
	int * threadElements;
	int starts;
	int res;
} thread;


//copy function - copies ann array into another array
int * copy (int * array, unsigned size) {
	int * new_array = malloc(sizeof(int) * size);
	int i;
	for (i = 0; i < size; i++) {
      		new_array[i] = array[i];
   	}
	return new_array;
}


//Finds the minimum in an array of integers and returns the minimum
static void *FindMin(void *threadds)
{
	struct thread *t = threadds;
	int *minimum = (int *) malloc(sizeof(int));
	minimum[0] = INT_MAX;
	int s  = t->starts;

	int i;
	for(i = s; i < s + numSubElements; i++)
	{
		int num	= t->threadElements[i];

		if(num < minimum[0])
		{
			minimum[0] = num;
		}
	}
	return minimum;
}




//Main function - Main Function
int main(int argc, char *argv[]) 
{
	//Initialization of variables
	int numThreads = atoi(argv[1]);
	int numElements = atoi(argv[2]);
	numSubElements = numElements/numThreads;
	struct thread *tinfo;
	int start = 0;
	int i = 0;
	int *ar = (int *) malloc(numElements * sizeof(int) );
	int *res = (int *) malloc(numThreads * sizeof(int) );
	long rc, t;
	tinfo = calloc(numThreads, sizeof(struct thread));
	int Minimum = INT_MAX;


	//Read the file into array
	FILE * file = fopen("numbers.txt", "r");
	for (i = 0; i < numElements; i++) {
        	fscanf(file, "%d", &ar[i]);
    	}
	printf("\n");
	fclose(file);

	
	for (t = 0; t < numThreads; t++) 
	{
		tinfo[t].threadElements = copy(ar, numElements);
		tinfo[t].starts = start;
		start = start + numSubElements;
               	rc = pthread_create(&tinfo[t].thread_id, NULL,&FindMin, &tinfo[t]);
        }


	//wait for the remaining threads
	int j;
	for (j = 0; j < numThreads; j++) {
		void *result;
		pthread_join(tinfo[j].thread_id, &result); 
		if(*(int *)result < Minimum)
		{
			Minimum = *(int *)result;
		}
		
		free(result);
	}
	
	printf("The minimum of num is %d \n", Minimum);

	free(tinfo);
	return 0;
}

//-lpthread --> compiler addition


