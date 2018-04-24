#include <stdio.h>
#include <stdlib.h>

#define NUM_THREADS 2

void *Thread1(void *dummy);
void *Thread2(void *dummy);

int main(int argc, char * argv[])
{
   	pthread_t tids[NUM_THREADS];
	
   	int rc, t;

   	
	pthread_create(&tids[0],NULL,Thread1,NULL);
	pthread_create(&tids[1],NULL,Thread2,NULL);
   	
	
	pthread_join(tids[0],NULL);
	pthread_join(tids[1],NULL);

   	pthread_exit(NULL);
} // end main


void *Thread1(void *dummy)
{
	int i = 0;
   	
	while (i < 50)
	{
		printf("Thread 1 --------------------------Hello world + %d.\n", i);
		i++;
	}
} // end Thread

void *Thread2(void *dummy)
{
	int i = 0;
   	
	while (i < 50)
	{
		printf("Thread 2 +++++++++++++++++++++++++++Hello world + %d.\n", i);
		if(i == 3)
		{
			//System call to sleep
			printf("Thread 2 Sleeping for 1 second\n");			
			sleep(1);
		}
		i++;
	}
} 
