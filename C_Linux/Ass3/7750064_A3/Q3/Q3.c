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
#include <unistd.h>
#include <time.h> 
#include <string.h>
#include <fcntl.h> 
#include <sys/stat.h>  
#include <semaphore.h>
#include <signal.h>



#define MAX_FILE_SIZE 40000
#define MIN_FILE_SIZE 500



typedef struct PR
{
    long clientID;
    char fileName[64];
    int fileSize;
} PrintRequest;



typedef struct Print
{
    PrintRequest BoundedBuffer[4];
	int head ;
	int tail ;
	int numinbuffer;	
} print;


print * m;
key_t 	key;
int pid, rc, shmid;
int SHMSZ =  sizeof(PrintRequest);
sem_t *mutexS, *emptyS, *fullS, *semaS;

void *PrintServer(void);
void *PrintClient(void);
void *printsem(void);
void *init(void);
void *Insert(PrintRequest pr);
void *catchSignal(int signal);
PrintRequest Remove(void);



int main(int argc, char *argv[])
{	
    int pid = fork();
	if (pid==0)
	{
		init();
		PrintClient();
		exit(0);
	}
	else
	{
		init();
		PrintServer();
	}
	
	return 0;
}



void *init(void)
{	
	
	unsigned int mu = 1;
	unsigned int e = 4;
	unsigned int f = 0;
	
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
	
		
	if ((mutexS = sem_open ("mutexS", O_CREAT , S_IRUSR|S_IWUSR , 1)) == SEM_FAILED)
	{
		 perror("Failed to open semphore for mutex");
		 exit(-1);
	}
	if ((emptyS = sem_open ("emptyS", O_CREAT , S_IRUSR|S_IWUSR , 4)) == SEM_FAILED)
	{
		 perror("Failed to open semphore for empty");
		 exit(-1);
	}
	if ((fullS = sem_open ("fullS", O_CREAT , S_IRUSR|S_IWUSR , 0)) == SEM_FAILED)
	{
		 perror("Failed to open semphore for full");
		 exit(-1);
	}
}



void *printsem(void)
{
	
	int sval1;
	int sval2;
	int sval3;

	sem_getvalue(fullS, &sval1);
	sem_getvalue(emptyS, &sval2);
	sem_getvalue(mutexS, &sval3);

	printf("\nFull:%d - Empty:%d - Mutex:%d\n\n", sval1, sval2, sval3);
}



void *PrintServer(void) 
{
	time_t t;
	long id = getpid();
	
	
	
	if (signal(SIGINT, catchSignal)== SIG_ERR)
	{
		perror("SIGINT init failed");
	}
	if (signal(SIGQUIT, catchSignal)== SIG_ERR)
	{
		perror("SIGQUIT init failed");
	}
	if (signal(SIGUSR1, catchSignal)== SIG_ERR)
	{
		perror("SIGUSR1 init failed");
	}
	
	while(1)
	{
		/*********************REMOVE*********************/
		sem_wait(fullS);
		sem_wait(mutexS);
		
		PrintRequest pr = m->BoundedBuffer[m->head];
		m->head = (m->head+1)%4;
		m->numinbuffer--;

		sem_post(mutexS); 
		sem_post(emptyS);
		/*********************REMOVE*********************/
		
		int filesize = pr.fileSize;

		time(&t);
		char *currTime = ctime(&t);        
		printf("\n[Printer%ld] Printing [%s] of File size: %d at %s",id, pr.fileName,filesize, currTime);

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
		sem_wait(emptyS);
		sem_wait(mutexS);		

		m->tail = (m->tail+1)%4;
		m->BoundedBuffer[m->tail] = pr;
		m->numinbuffer++;

		sem_post(mutexS); 
		sem_post(fullS); 
		printf("[Client%ld] File sent to Printer: %s\n", pr.clientID,pr.fileName);
		sleep(seconds);
		
		/*********************INSERT**********************/
	}
	if(shmdt(m) != 0)
	{
		perror("couldn't not detatch memory segment\n");
	}
	printf("Client exiting");
} 	



void *catchSignal(int signal)
{
	printf("Catching Signal\n");
	
	if (signal == SIGQUIT)
	{
		exit(0);
	}
	
	if (signal == SIGUSR1)
	{
		printf("Server is shutting down...\n");
		if(shmdt(m) != 0)
		{
			perror("counld not detatch memory segment\n");
		}
		else
		{
			printf("Server shut down\n");
			exit(0);
		}
	}
}
