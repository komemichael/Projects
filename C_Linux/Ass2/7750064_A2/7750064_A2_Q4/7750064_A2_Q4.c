/******************************************************************************

                            Online C Compiler.
                Code, Compile, Run and Debug C program online.
Write your code in this editor and press "Run" button to compile and execute it.

*******************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <time.h> 
#include <string.h>

#define MAX_FILE_SIZE 40000
#define MIN_FILE_SIZE 500

typedef struct PR
{
    long clientID;
    char *fileName; //ptr to a dynamically allocd string
    int fileSize;
} PrintRequest ,*PrintRequestPTR;

typedef struct Monitor
{
    	PrintRequest BoundedBuffer[4];
	int head ;
	int tail ;
	int numinbuffer;

	pthread_mutex_t buff_mutex;		// the 'method' mutex
	pthread_cond_t  buff_condvar_e;
	pthread_cond_t  buff_condvar_f;
} monitor ,*monitorPTR;

monitor *m;
void initializeMonitor()
{
	m = malloc(sizeof(PrintRequest));
	m->head =0;
	m->tail = 3;
	m->numinbuffer=0;

	pthread_mutex_init(&m->buff_mutex);
	pthread_cond_init(&m->buff_condvar_e);
	pthread_cond_init(&m->buff_condvar_f);
}


void *PrintServer(void *dummy);
void *PrintClient(void *dummy);
void *Insert(PrintRequest pr);
PrintRequest Remove(void);


int main(int argc, char *argv[])
{
    	int NumPrintClients = atoi(argv[1]);
   	int NumPrinters = atoi(argv[2]);

    	pthread_t tidsS[NumPrinters];
    	pthread_t tidsC[NumPrintClients];
	initializeMonitor();

    
	int t;

    	for(t = 0; t < NumPrinters; t++)
	{
		pthread_create(&tidsS[t] ,NULL, PrintServer, NULL);
	}

	for(t = 0; t < NumPrintClients; t++)
	{
		pthread_create(&tidsC[t] ,NULL, PrintClient, NULL);
	}
	
   	pthread_exit(NULL);
}


void *PrintServer(void *dummy) 
{
	time_t t;
    	long id = pthread_self();

    	while (1)
    	{
		
		PrintRequest pr = Remove();

        	int filesize = pr.fileSize;
		
		time(&t);
        	char *currTime = ctime(&t);        
        	printf("\nPrinter%ld Printing %s of File size: %d at %s",id, pr.fileName,filesize, currTime);
		
		int seconds = (int) (filesize/4000);
        	sleep(seconds);

		time(&t);
		char *nowTime = ctime(&t);
        	printf("\nPrinter%ld Done printing %s of File size: %d at %s",id, pr.fileName, filesize, currTime);
    	}
} 



void *PrintClient(void *dummy) 
{
    	int seconds = 3;
    	int i;
	char filename_long[18];
        char *filename;
	long id = pthread_self(); 
	long clientid = id;
	PrintRequest pr;

    	for (i = 0; i < 6; i++) 
    	{
		filename = malloc(300*sizeof(char));
		sprintf(filename_long, "File-%ld", id);
		sprintf(filename, "%s-%d",filename_long, i);

        	int filesize = rand() % (MAX_FILE_SIZE + 1 - MIN_FILE_SIZE) + MIN_FILE_SIZE;

		 //= {clientid, filename, filesize}; 
		pr.fileName = filename;
		pr.fileSize = filesize;
		pr.clientID = id;

        	Insert(pr);
		printf("[Thread%ld] File sent to Printer: %s\n", pr.clientID,pr.fileName);
        	sleep(seconds);
    	}
} 



void *Insert(PrintRequest pr)
{
	pthread_mutex_unlock(&m->buff_mutex);
	while (m->numinbuffer == 4) 
	{
		pthread_cond_wait(&m->buff_condvar_f, &m->buff_mutex);
	}

	m->tail = (m->tail+1)%4;

	m->BoundedBuffer[m->tail] = pr;
	m->numinbuffer++;

	//if (m->numinbuffer == 1) 
	//{
		pthread_cond_signal(&m->buff_condvar_e);
	//}
	
	pthread_mutex_unlock(&m->buff_mutex);
}


PrintRequest Remove(void)
{
	pthread_mutex_lock(&m->buff_mutex);

	while (m->numinbuffer == 0)
	{
		pthread_cond_wait(&m->buff_condvar_e, &m->buff_mutex);
	}

	PrintRequest pr = m->BoundedBuffer[m->head];
	m->head = (m->head+1)%4;
	m->numinbuffer--;

	//if (m->numinbuffer == 3) 
	//{
		pthread_cond_signal(&m->buff_condvar_f);
	//}
	pthread_mutex_unlock(&m->buff_mutex);
	return pr;
}

