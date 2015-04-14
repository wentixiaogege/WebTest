    /************************Copyright(c)******************************* 
    ** Descriptions: 
    * modification by Jack
    *******************************************************************/   
     
#include "uart.h" 
int UART0_Open(char* port)
    {  

	        printf("%s ",port);
             int fd = open(port, O_RDWR|O_NOCTTY|O_NDELAY);  //|O_NDELAY
             if (FALSE == fd)  
                    {  
                           perror("Can't Open Serial Port");  
                           return(FALSE);  
                    }  
         //restore to blocked                                
         if(fcntl(fd, F_SETFL, 0) < 0)
                    {  
                           printf("fcntl failed!\n");  
                         return(FALSE);  
                    }       
             else  
                    {  
                      printf("fcntl=%d\n",fcntl(fd, F_SETFL,0));  
                    }  
          //testing is terminal device or not     
         /* if(0 == isatty(STDIN_FILENO))
                    {  
                           printf("standard input is not a terminal device\n");  
                      return(FALSE);  
                    }  
      else  
                    {  
                         printf("isatty success!\n");  
                    }    */
      printf("fd->open=%d\n",fd);  
      return fd;  
    }  
       
void UART0_Close(int fd)  
    {  
        close(fd);  
    }  
       
  
int UART0_Set(int fd,int speed,int flow_ctrl,int databits,int stopbits,int parity)  
    {  
         
        int   i;  
        //int   status;
        int   speed_arr[] = { B115200, B19200, B9600, B4800, B2400, B1200, B300};  
        int   name_arr[] = {115200,  19200,  9600,  4800,  2400,  1200,  300};  
               
        struct termios options;  
         
        /*
         * tcgetattr(fd,&options) will get the crresponding parameters with fd,and save them into options .
         * the function is to test the configuration is right or not ,and test the port is useable or not.
         * if calling right ,return 0 or 1/
        */  
        if  ( tcgetattr( fd,&options)  !=  0)  
           {  
              perror("SetupSerial 1");      
              return(FALSE);   
           }  
        
        //setting input and output baud
        for ( i= 0;  i < sizeof(speed_arr) / sizeof(int);  i++)  
                    {  
                         if  (speed == name_arr[i])  
                                {               
                                     cfsetispeed(&options, speed_arr[i]);   
                                     cfsetospeed(&options, speed_arr[i]);    
                                }  
                  }       
         
      
        //modify the control mode .not letting the program occupying the port.
        //options.c_cflag &= ~CLOCAL;
        options.c_cflag &= ~ CLOCAL;
        
        // modify the control mode,reading the port available.
        options.c_cflag |= CREAD;  
        
 
        //setting the flow control
        switch(flow_ctrl)  
        {  
            
           case 0 :
					// no flow control 
                  options.c_cflag &= ~CRTSCTS;  
                  break;     
            
           case 1 : 
					//using hardware flow control
                  options.c_cflag |= CRTSCTS;  
                  break;  
           case 2 :  
				   //using software flow control
                  options.c_cflag |= IXON | IXOFF | IXANY;  
                  break;  
        }  
        
        //setting the data bits and blocking other flag bits.
        options.c_cflag &= ~CSIZE;  
        switch (databits)  
        {    
           case 5    :  
                         options.c_cflag |= CS5;  
                         break;  
           case 6    :  
                         options.c_cflag |= CS6;  
                         break;  
           case 7    :      
                     options.c_cflag |= CS7;  
                     break;  
           case 8:      
                     options.c_cflag |= CS8;  
                     break;    
           default:     
                     fprintf(stderr,"Unsupported data size\n");  
                     return (FALSE);   
        }  
       
        //setting checking bit 
        switch (parity)  
        {    
           case 'n':  
           case 'N':   
					  //no Parity
                     options.c_cflag &= ~PARENB;   
                     options.c_iflag &= ~INPCK;
                     options.c_iflag &= ~IXON;

                     break;   
           case 'o':    
           case 'O':   
                     //setting Odd  check 
                     options.c_cflag |= (PARODD | PARENB);   
                     options.c_iflag |= INPCK;               
                     break;   
           case 'e':   
           case 'E':  
					//setting even check
                     options.c_cflag |= PARENB;         
                     options.c_cflag &= ~PARODD;         
                     options.c_iflag |= INPCK;        
                     break;  
           case 's':  
           case 'S':   
					//setting Sapce
                     options.c_cflag &= ~PARENB;  
                     options.c_cflag &= ~CSTOPB;  
                     break;   
            default:    
                     fprintf(stderr,"Unsupported parity\n");      
                     return (FALSE);   
        }   

        //setting stop bits  
        switch (stopbits)  
        {    
           case 1:     
                     options.c_cflag &= ~CSTOPB; break;   
           case 2:     
                     options.c_cflag |= CSTOPB; break;  
           default:     
                           fprintf(stderr,"Unsupported stop bits\n");   
                           return (FALSE);  
        }  
         
        //setting output mode to raw data output  
        options.c_oflag &= ~OPOST;  
        
        options.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);  
    
        //setting waiting time and minimum receiving data bytes.
        options.c_cc[VTIME] = 0; /* waiting time1*(1/10)s */
        options.c_cc[VMIN] = 1; /* minimum receiving data bytes =1 */
         
        
        //if data overflow,receiving data but not read it,in the mean time flushing the receiving data buffer.
        tcflush(fd,TCIFLUSH);  
          
        //activating the configuration
        if (tcsetattr(fd,TCSANOW,&options) != 0)    
               {  
                   perror("com set error!\n");    
                  return (FALSE);   
               }  
        return (TRUE);   
    }  
   
int UART0_Init(int fd, int speed,int flow_ctrl,int databits,int stopbits,int parity)  
    {   
        //setting uart data pack Format
        if (UART0_Set(fd,speed,flow_ctrl,databits,stopbits,parity) == FALSE)  
           {                                                           
            return FALSE;  
           }  
        else  
           {  
                   return  TRUE;  
            }  
    }  


int  UART0_Recv(int fd, char* buf, int nbytes, unsigned int timout)       //return read how mang bytes of data
  {
	   int                        nfds;  
	   int totalread=0;
       int nread = 0;
       fd_set                    readfds;
       struct timeval  tv1;
	   tv1.tv_sec = timout;                                                       //setting every frame maxmum reading time passed by local server
	   tv1.tv_usec = 0;

	   struct timeval tv2;
	   tv2.tv_sec = 4;                                                       //setting every frame maxmum reading time 4s
	   tv2.tv_usec = 0;
       int i =0;
	   struct timeval start;
	   struct timeval end;   
       //gettimeofday(&start,NULL);

       FD_ZERO(&readfds);                                                       //everytime should 0 first 
	   FD_SET(fd, &readfds);
	   tcflush(fd,TCIOFLUSH);
	   nfds = select(fd+1, &readfds, NULL, NULL, &tv1);	                        //will always waiting for XXXs

	   gettimeofday(&start,NULL);

	   printf("Before loop: ###totalread %d\n",totalread);
	   printf("Before loop: ###nbytes %d\n",nbytes);

       while ((totalread < nbytes)&&(nfds)) 
       {			
		    nread = read(fd, buf, nbytes);                                      //everytime read
		    //printf("nread %d\n",nread);
		    totalread += nread;
		    buf += nread;
		    gettimeofday(&end,NULL);

		    if(totalread >= nbytes)
		    {	
				*(buf-totalread+nbytes) = '\0';
				printf("print in uart ###################################");
				for(;i<totalread;i++)
					printf("%02x ",*(buf-totalread+i));
				printf("\n");
		        return nbytes;   
		    }     
		    printf("Inside loop: ###totalread %d\n",totalread);                                                      //reading here!
		    printf("Inside loop: ###nbytes %d\n",nbytes);
		    printf("################################################################# %d\n",(int)((end.tv_sec-start.tv_sec)*1000000+(end.tv_usec-start.tv_usec)));
		    //if((nread == 0)||((end.tv_sec-start.tv_sec)*1000000+(end.tv_usec-start.tv_usec))>(tv.tv_sec) * 1000000)  // no more reading, or timeout
		    if ((end.tv_sec-start.tv_sec)>(tv2.tv_sec))
		    {
		        //if(totalread == nbytes)
		    	printf("timeout ###\n");
		    	printf("start time: %d, end time: %d, tv2: %d\n", (int)start.tv_sec, (int)end.tv_sec, (int)tv2.tv_sec);
		    	printf("print in uart###################################");
		    	for(i=0;i<totalread;i++)
		    		printf("%02x ",*(buf-totalread+i));
		    	printf("\n");
				return totalread;
				//else
					//return FALSE;
		    }
		}
      return FALSE;
  }



/*******************************************************************/
/**
//int UART0_Recv(int fd, char *buf, int nbytes, unsigned int timout);
 int UART0_Recv(int fd, char *rcv_buf, int data_len, unsigned int seconds)
 {
     int len,fs_sel;
     fd_set fs_read;
     char buf[0];
     int i;

     struct timeval time;

     FD_ZERO(&fs_read);
     FD_SET(fd,&fs_read);

     time.tv_sec = seconds;
     time.tv_usec = 0;

     //使用select实现串口的多路通信
     fs_sel = select(fd+1,&fs_read,NULL,NULL,&time);

     if(fs_sel)
        {
             // len = read(fd,rcv_buf,data_len);
             //printf("Receive char one by one\n");
				buf[0]='\0';
				for (i=0; i<data_len; i++)
				{
					len = read(fd, buf, 1);
					rcv_buf[i] = buf[0];
					//printf("0x%2x ", buf[0]);
				}
        //   printf("I am right!(version1.2) len = %d fs_sel = %d\n",len,fs_sel);
               return len;
        }
     else
        {
      //     printf("Sorry,I am wrong!");
               return FALSE;
        }
 }
 */

 /********************************************************************
 * 名称：                  UART0_Send
 * 功能：                发送数据
 * 入口参数：        fd                  :文件描述符
 *                              send_buf    :存放串口发送数据
 *                              data_len    :一帧数据的个数
 * 出口参数：        正确返回为1，错误返回为0
 *******************************************************************/
 int UART0_Send(int fd, char *send_buf,int data_len)
 {
     int len = 0;

     len = write(fd,send_buf,data_len);
     if (len == data_len )
               {
                      return len;
               }
     else
         {

                 tcflush(fd,TCOFLUSH);
                 return FALSE;
         }

 }


 /**
    int UART0_Send(int fd, char *send_buf,int data_len)  
    {  
        int len = 0;  
         
        len = write(fd,send_buf,data_len);  
        if (len == data_len )  
                  {  
                         return len;  
                  }       
        else     
            {  
                     
                    tcflush(fd,TCOFLUSH);  
                    return FALSE;  
            }  
         
    }  
 */
 
    /*********************************************************************                            End Of File                          ** 
    *******************************************************************/  
