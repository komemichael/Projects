
// include stdio.h to get a definition of NULL
#include <stdio.h>

// include header file for linked list data structure definitions
# include "list.h"

// include header file for memory management routine definitions
# include "mymem.h"


int main(int argc, char*argv[]) {

	myinit(128);
  	void * p[8];
	
	int i;
	for (i = 0 ; i < 8; i++) p[i] = mymalloc(16); //Allocates 8 16 block 
	printf("NumNode of FreeList %d.\n",numNodesOnFreeList());

	
	printf("\nFreeing node 2\n");
	myfree(p[2],16);
	printf("NumNode of FreeList %d.\n",numNodesOnFreeList());
	printList();
	
	printf("\nFreeing node 4\n");
	myfree(p[4],16);
	printf("NumNode of FreeList %d.\n",numNodesOnFreeList());
	printList();
	
	printf("\nFreeing node 3\n");
	myfree(p[3],16);
	printf("NumNode of FreeList %d.\n",numNodesOnFreeList());
	printList();
	
	printf("\nFreeing node 1\n");
	myfree(p[1],16);
	printf("NumNode of FreeList %d.\n",numNodesOnFreeList());
	printList();

	printf("\nFreeing node 5\n");
	myfree(p[5],16);
	printf("NumNode of FreeList %d.\n",numNodesOnFreeList());
	printList();

	printf("Allocting 16 * 3 bytes\n");
	p[1] = mymalloc(16 * 3);
	printf("NumNode of FreeList %d.\n",numNodesOnFreeList());
	printList();
}
