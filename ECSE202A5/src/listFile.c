/*
 * listFile.c
 *
 *  Created on: Mar. 25, 2019
 *      Author: ferrie
 */


#define MAXBUF 100
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char *argv[]) {

// Internal declarations
	FILE * FileD;			     // File descriptor (an object)!
	char *line;				     // Pointer to buffer used by getline function
	int bufsize = MAXBUF;	     // Size of buffer to allocate for file reading
	int linelen,i;		         // Length of string returned (getline)

// Argument check
	if (argc != 2) {
		printf("Usage: fileReader [text file name]\n");
		return -1;
	}

// Attempt to open the user-specified file.  If no file with
// the supplied name is found, exit the program with an error
// message.

	if ((FileD=fopen(argv[1],"r"))==NULL) {
		printf("Can't read from file %s\n",argv[1]);
		return -2;
	}

// Allocate a buffer for the getline function to return data to.

	line=(char *)malloc(bufsize+1);
	if (line==NULL) {
		printf("Unable to allocate a buffer for reading.\n");
		return -3;
	}

	printf("\nContents of file %s:\n\n",argv[1]);

// Read file line by line and dislay on output console

	while ((linelen=getline(&line,(size_t *)&bufsize,FileD))>0) {
		printf("%s\n",line);
	}

	fclose(FileD);
}
