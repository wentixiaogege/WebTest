    /************************Copyright(c)******************************* 
    *******************************************************************/
    #include<stdio.h>       
    #include<stdlib.h>     
    #include<unistd.h>      
    #include<sys/types.h>   
    #include<sys/stat.h>     
    #include<fcntl.h>      
    #include<termios.h>     
    #include<errno.h>      
    #include<string.h>     
    #include<stdint.h>  
    #include<sys/time.h>
    
    #define FALSE  0  
    #define TRUE   1
   /******************************************************************* 
    * function Name：                  UART0_Open 
    * function is：                    OPEN THE PORT AND RETURN THE device FILE DESCRIBER 
    * input parameter：                fd    :file describer     port :uart port(ttyUSB_X) X can be 1 2...
    * output parameter：               right:return 1 else return 0 
    *******************************************************************/  
    int UART0_Open(char* port);
    
    
    /******************************************************************* 
    * function Name：                UART0_Close 
    * function is：                  close the uart port and return the device FILE DESCRIBER  
    * input parameter：              fd    :file describer     port :uart port(ttyUSB_X) X can be 1 2...
    * output parameter：             void 
    *******************************************************************/  
    void UART0_Close(int fd);
    
    
    /******************************************************************* 
    * function Name：               UART0_Set 
    * function is：                 setting uart port data bits stop bits and checksum bits 
    * input parameter：             fd    :file describer 
    *                               speed     port speed can be 115200
    *                               flow_ctrl   data flow control
    *                               databits   can be 1 or 2
    *                               stopbits   can be 1 or 2
    *                               parity     can be N,E,O,,S 
    *output parameter：             right:return 1 else return 0
    *******************************************************************/  
    int UART0_Set(int fd,int speed,int flow_ctrl,int databits,int stopbits,int parity);
    
    
    
    /******************************************************************* 
    * function Name：                UART0_Init() 
    * function is：                  prot init 
    * input parameter：             fd    :file describer     
    *                               speed     port speed can be 115200
    *                               flow_ctrl   data flow control
    *                               databits   can be 1 or 2
    *                               stopbits   can be 1 or 2
    *                               parity     can be N,E,O,,S 
    *output parameter：             right:return 1 else return 0
    *******************************************************************/  
    int UART0_Init(int fd, int speed,int flow_ctrl,int databits,int stopbits,int parity);
    
    
    
    /******************************************************************* 
    * function Name：                  UART0_Recv 
    * function is：                    recieveing uart port data 
    * input parameter：                fd    :file describer    
    *                                  rcv_buf     :recieve data from uart port and save them in rcv_buf. 
    *                                  data_len    :length of one frame
    * output parameter：               right:return 1 else return 0
    *******************************************************************/  
    int UART0_Recv(int fd, char *buf, int nbytes, unsigned int timout);
    
    
    
    /******************************************************************** 
    * function Name：                  UART0_Send 
    * function is：                    send data to uart port
    * input parameter：                fd    :file describer    
    *                                  send_buf    :store data before sending 
    *                                  data_len    :length of one frame
    * output parameter：                right:return 1 else return 0
    *******************************************************************/  
    int UART0_Send(int fd, char *send_buf,int data_len);
    

    
    
    
    
    
    
