/******************************************************************************

                            Online C Compiler.
                Code, Compile, Run and Debug C program online.
Write your code in this editor and press "Run" button to compile and execute it.

*******************************************************************************/

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h> 
#include <string.h>
#include <fcntl.h> 
#include <sys/stat.h>  
#include <semaphore.h>


#define MAX_FILE_SIZE 40000
#define MIN_FILE_SIZE 500



typedef struct PR
{
    long clientID;
    char fileName[64];
    int fileSize;
} PrintRequest;


key_t 	key;
int pid, rc, shmid;
int SHMSZ =  sizeof(PrintRequest);

typedef struct Print
{
    PrintRequest BoundedBuffer[4];
	int head ;
	int tail ;
	int numinbuffer;	
} print;

sem_t *mutex;
sem_t *empty;
sem_t *full;

print * shmseg;
print * m;

void *PrintServer(void);
void *PrintClient(void);
void *Insert(PrintRequest pr);
PrintRequest Remove(void);


int main(int argc, char *argv[])
{
    	
	key = 7750064;
	
	if ((shmid = shmget(key, SHMSZ, IPC_CREAT | 0644)) < 0)
	{
        perror("shmget failed");
        exit(1);
    }
    if ((m = shmat(shmid, NULL, 0)) == (char *) -1) 
	{
        perror("shmat failed");
        exit(1);
    }
	
	m->head =0;
	m->tail = 3;
	m->numinbuffer=0;
	
	//mutex = sem_open("mutex", O_CREAT, 0644,1);
	//empty = sem_open("empty", O_CREAT, 0644,3);
	//full = sem_open("full", O_CREAT, 0644,0);
	
	mutex = sem_open("mutex_sem", O_CREAT, 0644, 4);
	if (empty == SEM_FAILED) {
		 perror("Failed to open semphore for empty");
		 exit(-1);
	}
	empty = sem_open("empty_sem", O_CREAT, 0644, 4);
	if (empty == SEM_FAILED) {
		 perror("Failed to open semphore for empty");
		 exit(-1);
	}

	full = sem_open("full_sem", O_CREAT, 0644, 0);
	if (full == SEM_FAILED) {
		 sem_close(empty);
		 perror("Failed to open semphore for full");
		 exit(-1);
	}

	int sval1;
	int sval2;

	sem_getvalue(&full, &sval1);
	sem_getvalue(&empty, &sval2);

	printf("Intitially, Full is %d and empty is %d", sval1, sval2);

	sem_post(&mutex); 
	sem_post(&full); 
	
    int pid = fork();
	if (pid==0)
	{
		PrintClient();
		exit(0);
	}
	else
	{
		PrintServer();
	}
	
}


void *PrintServer(void) 
{
	time_t t;
	long id = getpid();
	

	while(1)
	{
		/*********************REMOVE*********************/
		sem_wait(&full);
		sem_wait(&mutex);

		PrintRequest pr = m->BoundedBuffer[m->head];
		m->head = (m->head+1)%4;
		m->numinbuffer--;
		
		int sval1;
		int sval2;
		
		sem_getvalue(&full, &sval1);
		sem_getvalue(&empty, &sval2);
		
		printf("Full is %d and empty is %d", sval1, sval2);

		sem_post(&mutex); 
		sem_post(&empty);
		/*********************REMOVE*********************/
		
		int filesize = pr.fileSize;

		time(&t);
		char *currTime = ctime(&t);        
		printf("\n[Printer%ld]...Printing [%s] of File size: %d at %s",id, pr.fileName,filesize, currTime);

		int seconds = (int) (filesize/4000);
		sleep(seconds);

		time(&t);
		char *nowTime = ctime(&t);
		printf("\n[Printer%ld] Done printing [%s] of File size: %d at %s",id, pr.fileName, filesize, currTime);
	}
} 



void *PrintClient(void) 
{
	PrintRequest pr;
	int  i;
	char filename_long[20];
	char *filename;
	long id = getpid(); 
	int  seconds = (int) (rand() % (3)) + 1;

	
	for (i = 0; i < 6; i++) 
	{
		
		filename = malloc(300*sizeof(char));
		sprintf(filename_long, "File-%ld", id);
		sprintf(filename, "%s-%d",filename_long, i);

		int filesize = rand() % (MAX_FILE_SIZE + 1 - MIN_FILE_SIZE) + MIN_FILE_SIZE;

		strcpy(pr.fileName, filename);
		pr.fileSize = filesize;
		pr.clientID = id;
		
		
		/*********************INSERT**********************/
		sem_wait(&empty);
		sem_wait(&mutex);

		m->tail = (m->tail+1)%4;
		m->BoundedBuffer[m->tail] = pr;
		m->numinbuffer++;
		
		int sval1;
		int sval2;
		
		sem_getvalue(&full, &sval1);
		sem_getvalue(&empty, &sval2);
		
		printf("Full is %d and empty is %d", sval1, sval2);

		sem_post(&mutex); 
		sem_post(&full); 
		
		printf("[Client%ld] File sent to Printer: %s\n", pr.clientID,pr.fileName);
		//sleep(seconds);
		
		/*********************INSERT**********************/
	}
	if(shmdt(m) != 0)
	{
		perror("counld not detatch memory segment\n");
	}
	printf("Client exiting...");
} 



