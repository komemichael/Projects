/*
    shell.c
    ------
        Simple example of some code that creates processes ...
*/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>
#include <fcntl.h>

struct globals
{
  char *name;
  char *value;
};


int pid, rc;
char s[50];

struct globals glob[20];
int size_of_global = 0;
_Bool process = false;
_Bool isSET = false;
//save pointer variables
char *saveptr1, *saveptr2;
char *saveptr3;
char *saveptr4;
char *saveptr5;
char *saveptr11;
char *saveptr7;
char *saveptr8;

void execute(char * s);

int main(int argc, char *argv[]) 
{
	
	printf(">> ");
	
	while (fgets(s, 50, stdin) != NULL) 
	{
		if(strstr(s, "|") != NULL) 
		{
			pid_t process1, process2;
			int pipedescriptor[2];
			char * firstString = strtok_r(s, "|", &saveptr8);
			char * secondString = strtok_r(NULL, "|", &saveptr8);
			pipe(pipedescriptor);
                        int pid1 = fork();
		        if (pid1 == 0) {
				dup2(pipedescriptor[1], STDOUT_FILENO);
			        close(pipedescriptor[0]);
				execute(firstString);
				perror("exec");
		  		return(1);
			}
			int pid2 = fork();
			if (pid2 == 0){
				dup2(pipedescriptor[0], STDIN_FILENO);
				close(pipedescriptor[1]);
				execute(secondString);
				perror("exec");
				return(1);
			}
			close(pipedescriptor[0]);
			close(pipedescriptor[1]);
			waitpid(pid1,NULL,NULL);
			waitpid(pid2,NULL,NULL);		
		} else
		{
			int processid1 = fork();
			if (processid1==0)
			{
				execute(s);
			}
			else
			{
				wait(0);
			}
		}
		printf("\n>> ");
	}
	printf("\n");
} 


void execute(char * s)
{

	if(strstr(s, ">") != NULL)
	{
		char * tempCommand = strtok(s,">");
		char * tempCommand1 = strtok(NULL,">");//,&saveptr11);
		strcpy(s , tempCommand);

		char * output = (char *)malloc((strlen(tempCommand1))-1);
	        memcpy(output, tempCommand1, (strlen(tempCommand1))-1);

		close(STDOUT_FILENO);
		open(output, O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU);
	}

	//gets the command and strip the last white space
	char * dupcommand = (char *)malloc((strlen(s))-1); 
	memcpy(dupcommand, s, (strlen(s))-1);
	char * dupcommand1 = (char *)malloc((strlen(s))-1); 
	memcpy(dupcommand1, s, (strlen(s))-1);
	 
	//process if this is a set command
	if((strstr(dupcommand, "set") != NULL) && (strstr(dupcommand, "$") != NULL))
	{
		char *dollarname = strtok_r(dupcommand, "=", &saveptr1);

		strtok_r(dollarname, "$", &saveptr2);
		char *name = strtok_r(NULL, "$", &saveptr2);

		//do the changes and set next
		glob[size_of_global].name = name;
		glob[size_of_global].value = strtok_r(NULL, "=", &saveptr1);
		
		//set process to be false
		int k = 0;
		while (k <= size_of_global)
		{
			k++;
		}

		size_of_global++;
		process = true;
		isSET = true;
	}

			
	int i = 0;
	char *arg[8];
	char *token = strtok_r(dupcommand1, " ",&saveptr3) ;
	
	while((token != NULL))
	{
	      	arg[i] = (char *) strdup(token);
	      	//checks if a variable exists in the string
	      	if (strstr(arg[i], "$") != NULL)
	        {
			int j = 0;
		  	//parses the $ variable to the string
		  	char *stringss = strtok_r(arg[i], "$", &saveptr4);
		  	
		  	if(isSET)
		  	{
		  	    char *strings = strtok_r(stringss, "=", &saveptr5);
		  	    (void)strncpy(stringss, strings, sizeof(stringss));
		  	    isSET = false;
		  	}
		  	//loops through the variable to check if exist
		  	while (j <= size_of_global)
	    	 	{
				if(strcmp(stringss, glob[j].name)==0)
    				{
    					(void)strncpy(arg[i] , glob[j].value, sizeof(arg[i]));	
    					break;
    			    	}
			      	j++;
		    	}
		}


		//printf("%s > This is one of the strings",arg[i]);
	    	i++;
	    	token = strtok_r(NULL, " ",&saveptr3);
	  }

	  arg[i] = NULL;

	  char * arg0 = (char *)malloc((strlen(arg[0]))); 
	  memcpy(arg0, arg[0], (strlen(arg[0])));

	  char * bin = (char *)malloc(5);
	  char * comm = strcat(strcpy(bin, "/bin/"),arg0);

//	  int l;
//	  for (l = 0; l < 8; l++)
//	    {printf("%s - \n", arg[l]);}

	  rc = execv(comm, arg);

	  printf("done");

	  if (rc!=0) 
	  {
		if(process)
		{
			process = false;
		}
		else
		{
			printf("Error - unknown program\n");
		}
	  }	
}





