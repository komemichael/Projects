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
#include <string.h>

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

void execute(char * s);

int main(int argc, char *argv[]) 
{
	printf("Type 'file' to run the file: Else enter command to run \n >> ");
	
	while (fgets(s, 50, stdin) != NULL) 
	{
		printf("%s\n",s);

		if(strstr(s, "file") != NULL) 
		{
			
    			FILE * file = fopen(".shell_init", "r");
			char line[255];
			while(fgets(line, 255, (FILE*) file)) {
			    execute(line);
			}
		}
		else
		{
			execute(s);
		}
		printf("\n >> ");
	}
	//fclose(file);
	printf("\n");
} 


void execute(char * s)
{

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

	pid=fork();

	if (pid!=0)
	{
		wait(0);
	} 
	else 
	{
		int i = 0;
		char *arg[20];
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
		    	i++;
		    	token = strtok_r(NULL, " ",&saveptr3);
		  }

		  arg[i] = NULL;

		  char * arg0 = (char *)malloc((strlen(arg[0]))); 
		  memcpy(arg0, arg[0], (strlen(arg[0])));
	
		  char * bin = (char *)malloc(5);
		  char * comm = strcat(strcpy(bin, "/bin/"),arg0);

		  rc = execv(comm, arg);

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
	
}




