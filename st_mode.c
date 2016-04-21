#include<stdlib.h>
#include<stdio.h>
#include<sys/stat.h>
#include<time.h>
 int main(int argc, char const *argv[])
{
	struct stat sb;
	const char *filetype[] = {"unknown","FIFO","Character device","unknown","directory","unknown","block device","unknown","file","unknown","symlink","unknown","socket"};
	if (argc !=2 ){
		fprintf(stderr, "Usage: listfile filename \n" );
		exit(1);
	}
	if (stat(argv[1],&sb)<0)
	{
		perror(argv[1]);
		exit(1);
	}

char m = (sb.st_mode & S_IRUSR) ? 'r':'-';
char n = (sb.st_mode & S_IWUSR) ? 'w':'-';
char o = (sb.st_mode & S_IXUSR) ? 'x':'-';
char p = (sb.st_mode & S_IRGRP) ? 'r':'-';
char q = (sb.st_mode & S_IWGRP) ? 'w':'-';
char r = (sb.st_mode & S_IXGRP) ? 'x':'-';
char s = (sb.st_mode & S_IROTH) ? 'r':'-';
char t = (sb.st_mode & S_IWOTH) ? 'w':'-';
char u = (sb.st_mode & S_IXOTH) ? 'x':'-';

printf("FileType:%s\n",filetype[(sb.st_mode >> 12 ) & 017] );
printf("Permission :  %c%c%c|%c%c%c|%c%c%c\n",m,n,o,p,q,r,s,t,u);

printf("Last Accessed:%s\n",ctime(&sb.st_atime) );
printf("Last modified:%s\n",ctime(&sb.st_mtime) );
printf("Last change:%s\n",ctime(&sb.st_ctime) );
return 0;
}
