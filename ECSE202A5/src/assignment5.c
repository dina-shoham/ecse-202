/*
 ============================================================================
 Name        : assignment5.c
 Author      : Dina Shoham
 ============================================================================
 */

#define MAXBUF 100 // max string length

#include <stdio.h> // imports
#include <stdlib.h>
#include <string.h>


// creating bNode struct and defining some variables (data, left, and right)
struct bNode {
	char data[MAXBUF];
	struct bNode* left;
	struct bNode* right;
};


struct bNode* root = NULL; // initializing root pointer to null
struct bNode** indexArray; // initializing indexArray variable
int recCount; // initializing recursion count variable


// addNode function sorts data using a binary tree
void addNode (char* data){
	struct bNode** current = &root;  // initializing current node to be root
	while(*current != NULL) // if current node is not NULL
	{
		int cmp = strcmp(data, (*current)->data); // cmp uses strcmp function to compare current data w data at current node
		if(cmp > 0 ) {  // if cmp is greater than 0 (current is greater than data)
			current = &((*current)->right); // add to the right side of the tree
		}else if(cmp < 0) { // if current is less than data
			current = &((*current)->left); // add to the left side
		}else{
			return;
		}
	}

	struct bNode* newNode = (struct bNode*)malloc(sizeof(struct bNode)); // allocating memory for new bNode
	newNode->left = NULL; // setting left and right successor nodes to null
	newNode->right = NULL;
	strcpy(newNode->data, data); // copies data to new node
	*current = newNode; // sets current to new node
}


// traverse function traverses binary tree
void traverse(struct bNode* node)
{
	if(node == NULL)
		return; // if node is null, return
	traverse(node->left); // traverse to the left
	indexArray[recCount] = node; // create recursion count array
	recCount++; // increment recursion count
	traverse(node->right); // traverse to the right
}


// makeIndex function uses name count to allocate memory
void makeIndex (int nameCount){
	indexArray = (struct bNode**)malloc(sizeof(struct bNode*) * nameCount); // allocates memory for all of the names
	recCount = 0; // sets recursion count to 0
	traverse(root); // traverses back to root
}


/*main function - takes input from terminal
 * contains listFile code provided by Prof. Ferrie
 */

int main(int argc, char* argv[]) {
	FILE* FileD;			     // File descriptor (an object)!
	char* line;				     // Pointer to buffer used by getline function
	int bufsize = MAXBUF;	     // Size of buffer to allocate for file reading
	int length;
	int i;		         // Length of string returned (getline)
	int numrec = 0;	// number of records
	// Argument check
	if (argc != 2) {
		printf("Usage: fileReader [text file name]\n");
		return -1;
	}

	// Attempt to open the user-specified file.  If no file with
	// the supplied name is found, exit the program with an error
	// message.
	if ((FileD = fopen(argv[1],"r")) == NULL) {
		printf("Can't read from file %s\n",argv[1]);
		return -2;
	}

	// Allocate a buffer for the getline function to return data to.
	line = (char*)malloc(bufsize+1);
	if (line == NULL) {
		printf("Unable to allocate a buffer for reading.\n");
		return -3;
	}

	// Read file line by line and display on output console
	while ((length = getline(&line,(size_t *)&bufsize,FileD))>0) {
		addNode(line);
		numrec++;
	}

	makeIndex(numrec);

	// printing in forward order - using for loop to iterate through list
	printf("File %s in sort order:\n", argv[1]);
	for (i = 0; i < numrec; ++i) {
		printf("%d. %s", i + 1, indexArray[i]->data);
	}

	// printing in reverse order
	printf("\nFile %s in reverse sort order:\n", argv[1]);
	for (i = numrec-1 ; i >= 0; --i) {
		printf("%d. %s", numrec - i, indexArray[i]->data);
	}

	printf("\nProgram terminated.\n");

	fclose(FileD);
}
