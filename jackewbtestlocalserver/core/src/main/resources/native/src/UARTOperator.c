
//串口相关的头文件
#include<stdio.h>      /*标准输入输出定义*/
#include<stdlib.h>     /*标准函数库定义*/
#include<unistd.h>     /*Unix 标准函数定义*/
#include<sys/types.h>
#include<sys/stat.h>
#include<fcntl.h>      /*文件控制定义*/
#include<termios.h>    /*PPSIX 终端控制定义*/
#include<errno.h>      /*错误号定义*/
#include<string.h>

#include<stdint.h>  /*for uint8_t */
#include<sys/time.h>

//#include "com_cic_localserver_UARTOperator.h"  // created from JNI java file
#include "uart.h" 	// for UART communication
#include <jni.h>


//Global variables for local server
//int fd;                            //file handle  for UART port
char receive_buf[200];
char send_buf[100];

/** the default setting of controlReg:
	set relay is enabled: [0][3]=1,
     controlReg writable: [1][1]=1,
     retry is disabled: [1][2]=0.  */

 uint8_t controlReg[31] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
 uint8_t controlReg_ls[31] = {0x08, 0x03, 0x00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};	// local server copy
 uint16_t paramReg[18] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
 uint16_t paramReg_gc[18] = {0, 1023, 1000, 60, 110, 110, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19};	//golden copy
 uint16_t dataReg[23] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0 , 0, 0, 0, 0};   // data register
 uint8_t smNumber[2] = {0, 0};  // number of smart meters in the coordinator
 uint8_t addressTable[11] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  // 8 bytes of IEEE address + 2 bytes of network address + 1 byte of validation
 uint8_t timestampTable[12] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //year, month, day, hour, minute, second, 2 byte of each
 uint16_t calReg[12] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

int controlReg_length = 37;
int paramReg_length = 18;
int dataReg_length = 23;
int smNumber_length = 2;
int addressTable_length = 11;
int timestampTable_length = 12;
int calReg_length = 12;
int configReg_length = 6;
int ieeeAddress_length = 8;

int rs485_extra_length = 13;

uint8_t ack_array[3] = {0x00, 0x00, 0x00};
int ack_array_length = 3;


int retry_max = 3;

jbyte *inCArray;		// for message passed by Java to C
jbyte outCArray[200];
uint8_t data[200];
uint8_t commandParameter[100];
jsize inJNIArray_length;

//int data_size =0 ;
//uint8_t status = 0; // the default is 0;


// local functions

void reset_send_buf();
void reset_receive_buf();
void reset_data_array();
void reset_ack_array();
void reset_controlReg_ls();
void reset_command_parameter();

int assemble_send_pk(uint8_t command_id, int command_flag, int ack_flag, int retry_flag); // to assemble the send package according to RS485
void print_send_pk(int coordinator_id, int buf_size);	// to print out send buffer
void print_receive_pk(int coordinator_id, int buf_size); 	// to print out receive buffer
int issue_command(int uart_fd, int send_pk_size);	// to issue command to the coordinator through UART.
int receive_pk(int coordinator_id, int wait_time, uint8_t command_id, int fd); // to receive package from the coordinator through UART. it also handles checksum error.
void process_ack(int receive_buf_size);		// to process acknowledgment from the receive package.
int get_timeout();	// to process the timeout bit.
int get_retry();	// to get retry flag from the acknowledge.
int process_data(uint8_t command_id);	// to process data from the receive package according to the RS485 format
int get_controlReg_write();		// to get control register writable flag from the acknowledge.
void update_controlReg_ls();	// to update the local server control register from the acknowledge.
void set_timeout(int flag);


int send_command_or_ack(int coordinator_id, uint8_t command_id, int command_flag, int ack_flag, int retry_flag, int uart_fd);  // success: 1,else 0
int receive_message(int coordinator_id, uint8_t command_id, int uart_fd);  // return data_size if success, else 0
int assemble_outCArray(int coordinator_id, int receive_pk_size); // return the size of outCArray, and assemble the outCArray.


/**
	for JNI library functions:
	private native int init(int id);  // returns the uart file handle
	private native byte[] sendAndRetrieveMessage(int id, int fd, byte[] cmd);
	private native byte[] ackAndRetrieveMessage(int id, int fd, byte command_id, byte[] ack);
	private native void close(int id, int fd);
*/

//private native int init(int id);  // returns the uart file handle
JNIEXPORT jint JNICALL Java_com_cic_localserver_UARTOperator_init(JNIEnv *env, jobject thisObj, jint id) {


	int uart_fd;
	int err = 0;
	//char uart_port[2][30];
	 //uart_port[0][]="/dev/ttyUSB2";  // this is manually set for now
	 //uart_port[1][]="/dev/ttyUSB5";  // this is manually set for now
	char uart_port[2][30];
	//strcpy(uart_port[0], "/dev/ttyUSB1");  // this is manually set for nowc"/dev/coordinatorF191"
	//strcpy(uart_port[1], "/dev/ttyUSB3");  // this is manually set for now"/dev/coordinator05B3"

	strcpy(uart_port[0], "/dev/ttyACM0");
	//strcpy(uart_port[1], "/dev/ttyACM1");

	int speed = 256000;


    jint status = 0;

	printf("Open and initial UART port.\n");


    uart_fd = UART0_Open(uart_port[id]); // open the uart port and return the file handle fd
    //status = UART0_Open(uart_fd, uart_port[id]); // open the uart port and return the file handle fd
    printf("In C, coordinator ID: %d opens UART port: %d!\n", id, uart_fd);
   // do{
    	//err = UART0_Init(uart_fd,115200,0,8,1,'N');
    	err = UART0_Init(uart_fd,speed,0,8,1,'N');
    	printf("Set Port: %d!, err: %d\n", uart_fd, err);
   // }while(FALSE == err || FALSE == fd);

   status = uart_fd;

   if (status >0)
   {
	   //status =uart_fd;  //the UART port is initiated successfully
	   printf("In C, UART port is initiated successfully!\n");
   }
   else
   {
	  // status = 0;	//the UART port is initialized failed
	   printf("In C, UART port is initiated failed! Please check out the connection.\n");
   }

   return status;
}


//private native byte[] sendAndRetrieveMessage(int id, int fd, byte[] cmd);
JNIEXPORT jbyteArray JNICALL Java_com_cic_localserver_UARTOperator_sendAndRetrieveMessage
			(JNIEnv *env, jobject thisObj, jint id, jint fd, jbyteArray inJNIArray) {


	uint8_t command_id;	// the current command_id;
	//int localServer_id = id;
	int coordinator_id = id;
	int uart_fd = fd;

	// Step 1: Convert the incoming JNI jbytearray to C's jbyte[]
	//(*env)->MonitorEnter(env, thisObj);
		inCArray = (*env)->GetByteArrayElements(env, inJNIArray, NULL);
		if (NULL == inCArray)
		{
			return NULL;
		}
		inJNIArray_length = (*env)->GetArrayLength(env, inJNIArray);

		command_id = inCArray[0];  // the first one of the commandParameter is command_id
		printf("In C, the command id is: 0x%02x, coordinator ID: %d, uart_fd: %d\n", command_id, coordinator_id, uart_fd);

		if (inJNIArray_length > 1)
		{
			// still need to process other parameters
			printf("In C sendAndRetreiveMessage(), there is more than on parameter. \n");
			reset_command_parameter();
			int i;
			for (i = 0; i < inJNIArray_length; i++) {
				printf("In C, the inJNIArray[%d] is 0x%02x\n", i, inCArray[i]);

				commandParameter[i] = inCArray[i];  // depend on different commands, there will be different format of parameters, the first on is command_id
			}
		}


	(*env)->ReleaseByteArrayElements(env, inJNIArray, inCArray, 0); // release resources

	// Step two: start to handle command

	int send_pk_size = 0;
	int receive_pk_size = 0;
	int retry_time = 0;
	int timeout_flag = 0;
	int retry_flag = 0; // default =0
	//int data_size = 0;

	int command_flag = 1;  // need to send command
	int ack_flag = 0;    // ack_flag default = 0



	do {

		usleep(100000);//100ms

		receive_pk_size = 0;
		send_pk_size = send_command_or_ack(coordinator_id, command_id, command_flag, ack_flag, retry_flag, uart_fd);

		if (send_pk_size > 0)
		{
			receive_pk_size = receive_message(coordinator_id, command_id, uart_fd);
			timeout_flag = get_timeout();

			if ((receive_pk_size > 0) || (timeout_flag == 1))
			{
				break;
			}
			else   // need to retry the previous command
			{
				ack_flag = 0;

			}
		}

		retry_time++;

	} while ((receive_pk_size == 0) && (retry_time < retry_max));

	if (retry_time == retry_max)
	{
		timeout_flag = 1;
		set_timeout(timeout_flag);
		update_controlReg_ls();
		printf("Command failed. Tries: %d times.\n", retry_time);

	}

	// Step 3: Convert the C's Native jbyte[] to JNI jbytearray, and return
	//int size = sizeof(outCArray);

	int size = assemble_outCArray(coordinator_id, receive_pk_size);

	jbyteArray outJNIArray = (*env)->NewByteArray(env, size);  // allocate
	   if (NULL == outJNIArray) return NULL;
	   (*env)->SetByteArrayRegion(env, outJNIArray, 0 , size, outCArray);  // copy

	//(*env)->MonitorExit(env, thisObj);

	   return outJNIArray;
}

//private native byte[] ackAndRetrieveMessage(int id, int fd, byte command_id, byte[] ack)
JNIEXPORT jbyteArray JNICALL Java_com_cic_localserver_UARTOperator_ackAndRetrieveMessage
			(JNIEnv *env, jobject thisObj, jint id, jint fd, jbyte command_id, jbyteArray inJNIArray) {

	//uint8_t current_command_id = command_id;	// the current command_id;
	int coordinator_id = id;
	int uart_fd = fd;

	// Step 1: Convert the incoming JNI jbytearray to C's jbyte[]
	//(*env)->MonitorEnter(env, thisObj);
	inCArray = (*env)->GetByteArrayElements(env, inJNIArray, NULL);
	if (NULL == inCArray)
	{
		return NULL;
	}
	inJNIArray_length = (*env)->GetArrayLength(env, inJNIArray);

	//command_id = inCArray[0];
	//printf("In C, the command id is: 0x%02x\n", command_id);

	if (inJNIArray_length == ack_array_length + 8)  // receive the ack_array and coordinator ieee address from local server
	{
		// still need to process other parameters
		int i;
		for (i = 0; i < ack_array_length; i++)
		{
			printf("In C, the inJNIArray[%d] is 0x%02x\n", i, (uint8_t)inCArray[i]);
			ack_array[i] = (uint8_t)inCArray[i];
		}

		for (i =0; i < inJNIArray_length; i++)
		{
			printf("In C, the inJNIArray[%d] is 0x%02x\n", i, (uint8_t)inCArray[i]);
			commandParameter[i] = inCArray[i];
		}
	}


	(*env)->ReleaseByteArrayElements(env, inJNIArray, inCArray, 0); // release resources

	// Step two: start to handle command

	int send_pk_size = 0;
	int receive_pk_size = 0;
	int retry_time = 0;
	int timeout_flag = 0;
	int retry_flag = 0;   // only needs to be set for acknowledge package from local server to coordinator
	//int data_size = 0;

	int ack_flag = 0;
	int command_flag = 0;  // not to send command

	if ((ack_array[2] >> 3) == 1)
	{
		ack_flag = 1;    // acknowledge from local server that it should continue;

		do {

			usleep(100000);//100ms

			receive_pk_size = 0;
			send_pk_size = send_command_or_ack(coordinator_id, command_id, command_flag, ack_flag, retry_flag, uart_fd);  // sending ack to coordinator instead

			if (send_pk_size > 0)
			{
				receive_pk_size = receive_message(coordinator_id, command_id, uart_fd);
				timeout_flag = get_timeout();

				if ((receive_pk_size > 0) || (timeout_flag == 1))
				{

					break;
				}

			}

			retry_time++;
			retry_flag = 1;

		} while ((receive_pk_size == 0) && (retry_time < retry_max));
	}

	if (retry_time == retry_max)
	{
		timeout_flag = 1;
		set_timeout(timeout_flag);
		update_controlReg_ls();
		printf("Command failed. Tries: %d times.\n", retry_time);

	}


	//set_timeout(timeout_flag);
	//update_controlReg_ls();

	// Step 3: Convert the C's Native jbyte[] to JNI jbytearray, and return
	//int size = sizeof(outCArray);

	int size = assemble_outCArray(coordinator_id, receive_pk_size);

	jbyteArray outJNIArray = (*env)->NewByteArray(env, size);  // allocate
	   if (NULL == outJNIArray) return NULL;
	   (*env)->SetByteArrayRegion(env, outJNIArray, 0 , size, outCArray);  // copy

	// (*env)->MonitorExit(env, thisObj);

	   return outJNIArray;

}


//private native void close(int id, int fd);

JNIEXPORT void JNICALL Java_com_cic_localserver_UARTOperator_close(JNIEnv *env, jobject thisObj, jint id, jint fd) {

	UART0_Close(fd); // this close function doesn't return value
	printf("In C, local server id: %d UART port id: %d is closed!\n", id, fd);
	return;
}


void reset_send_buf()
{
	memset(send_buf, 0, sizeof(send_buf));
}

void reset_receive_buf()
{
	memset(receive_buf, 0, sizeof(receive_buf));
}

void reset_data_array()
{
	memset(data, 0, sizeof(data));
	data[0] = '\0';
}

void reset_ack_array()
{
	ack_array[0] = 0x08;
	ack_array[1] = 0x03;
	ack_array[2] = 0x00;
}

void reset_command_parameter()
{
	memset(commandParameter, 0, sizeof(commandParameter));
	commandParameter[0] = '\0';
}


// get send package according to the RS485 format
// use char send_buf[50] as the global variable
// return the size of the send package.

int send_command_or_ack(int coordinator_id, uint8_t command_id, int command_flag, int ack_flag, int retry_flag, int uart_fd)
{

	//printf("command: %d, UART port id: %d.\n", command, uart_fd);
	int send_pk_size = 0;
	int issue_success_flag = 0;

	reset_controlReg_ls();
	reset_send_buf();

	send_pk_size = assemble_send_pk(command_id, command_flag, ack_flag, retry_flag); // will use command and inCArray to assemble
	if (send_pk_size > 0)
	{
		printf("Assembled send package.\n");
		print_send_pk(coordinator_id, send_pk_size);
		printf("Send package size: %d.\n", send_pk_size);
		//usleep(100000);//100ms

		issue_success_flag = issue_command(uart_fd, send_pk_size);

		printf("In C, Coordinator ID: %d, Issue command success_flag: %d\n", coordinator_id, issue_success_flag);

	}

	return issue_success_flag;

}

int assemble_send_pk(uint8_t command_id, int command_flag, int ack_flag, int retry_flag)
{

	// need to add some coding about the inCArray to assemble the send package

	int i;
	int size;
	// build the package according to the RS485 format
	// assemble the heading first


	send_buf[0] = 0x68;
	for(i=0; i<8; i++)  // Coordinator IEEE Address, the last 8 bytes
	{
		//send_buf[i] = 0;
		send_buf[1+i] = commandParameter[inJNIArray_length-8+i];  // starting from the second byte of send_buf
	}
	send_buf[9] = 0x68;

	if (command_flag == 1)   // assemble command of control Register
	{
		switch (command_id) {
			case 0x00:  // paramReg read
				controlReg_ls[0] = controlReg_ls[0] | 0x01; //byte0 bit0=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0

				// set the IEEE address for the smart meter
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[1+i];
				}
				break;
			case 0x01:  // paramReg write
				controlReg_ls[0] = controlReg_ls[0] | 0x02; //byte0 bit1=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0

				// set the IEEE address for the smart meter
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[1+i];
				}
				break;
			case 0x02:  // energy calculation reset
				controlReg_ls[0] = controlReg_ls[0] | 0x04; //byte0 bit2=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0

				//set energy reset value, 4 bytes so now
				for (i=0; i<4; i++)
				{
					controlReg_ls[3+i] = commandParameter[1+i];
				}

				//set the smart meter IEEE address,
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[5+i];
				}
				break;
			case 0x03:  // relay control
				if (commandParameter[1] == 1)	//enable as default
				{
					controlReg_ls[0] = controlReg_ls[0] | 0x08; //byte0 bit3=1
				}
				else  // disable
				{
					controlReg_ls[0] = controlReg_ls[0] & 0xF7;  //byte0 bit3=0
				}
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0
				// set the IEEE address for the smart meter
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[2+i];
				}
				break;
			case 0x04:  // network discovery
				controlReg_ls[0] = controlReg_ls[0] | 0x10; //byte0 bit4=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0
				// set the IEEE address for the smart meter
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[1+i];
				}
				break;
			case 0x05:  // route table read
				controlReg_ls[0] = controlReg_ls[0] | 0x20; //byte0 bit5=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0
				break;
			case 0x06: // controlReg read
				controlReg_ls[0] = controlReg_ls[0] | 0x40; //byte0 bit6=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit0=0

				// set the IEEE address for the smart meter
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[1+i];
				}
				break;
			case 0x07:  // dataReg read
				controlReg_ls[0] = controlReg_ls[0] | 0x80; //byte0 bit7=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit0=0
				break;
			case 0x08:  // power calculation
				if (commandParameter[1] == 1)  //enable as default
				{
					controlReg_ls[1] = controlReg_ls[1] | 0x02; //byte1 bit1=1
				}
				else  //disable
				{
					controlReg_ls[1] = controlReg_ls[1] & 0xFD; //byte1 bite1=0
				}
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit0=0
				break;
			case 0x09:  // voltage calibration
				controlReg_ls[1] = controlReg_ls[1] | 0x04; //byte1 bit2=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit0=0

				controlReg_ls[27] = commandParameter[1];  // V_CAL
				controlReg_ls[28] = commandParameter[2];  // I_Cal
				controlReg_ls[29] = commandParameter[3]; // T_CAL
				controlReg_ls[30] = commandParameter[4];  // N_CAL

				// set the IEEE address for the smart meter
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[5+i];
				}
				break;
			case 0x0A:  // current calibration
				controlReg_ls[1] = controlReg_ls[1] | 0x04; //byte1 bit2=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit0=0

				controlReg_ls[27] = commandParameter[1];  // V_CAL
				controlReg_ls[28] = commandParameter[2];  // I_Cal
				controlReg_ls[29] = commandParameter[3]; // T_CAL
				controlReg_ls[30] = commandParameter[4];  // N_CAL

				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[5+i];  // the IEEE Address 8 bytes
				}
				break;
			case 0x0B:  // energy calibration
				controlReg_ls[1] = controlReg_ls[1] | 0x04; //byte1 bit2=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit0=0

				controlReg_ls[27] = commandParameter[1];  // V_CAL
				controlReg_ls[28] = commandParameter[2];  // I_Cal
				controlReg_ls[29] = commandParameter[3]; // T_CAL
				controlReg_ls[30] = commandParameter[4];  // N_CAL

				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[5+i];  // the IEEE Address 8 bytes
				}
				break;
			case 0x0C:  // control register write
				controlReg_ls[0] = controlReg_ls[0] | 0x01; //byte0 bit0=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0
				break;
			case 0x0D:  // time set
				controlReg_ls[1] = controlReg_ls[1] | 0x20; //byte1 bit5=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0

				// set the time, 12 bytes for now
				for (i=0; i<timestampTable_length; i++)
				{
					controlReg_ls[15+i] = commandParameter[1+i];  // the first one is command_id
				}

				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[1+timestampTable_length+i];  // the IEEE Address 8 bytes
				}
				break;

			case 0x0E:  // calibration register read
				controlReg_ls[1] = controlReg_ls[1] | 0x40; //byte1 bit6=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit1=0

				// set ieeeAddress
				//set the smart meter IEEE address, 8 bytes so now
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[1+i];
				}
				break;

			case 0x0F:  // configuration register write
				controlReg_ls[1] = controlReg_ls[1] | 0x80; //byte1 bit7=1
				controlReg_ls[1] = controlReg_ls[1] & 0xFE; //byte1 bit0=0

				// set Configuration register
				for (i=0; i<configReg_length; i++)
				{
					controlReg_ls[31+i] = commandParameter[1+i];  // the first one is command_id
				}

				// set ieeeAddress
				//set the smart meter IEEE address, 8 bytes so now
				for (i=0; i<ieeeAddress_length; i++)
				{
					controlReg_ls[7+i] = commandParameter[1+configReg_length+i];
				}
				break;

			default:
				printf("Wrong command!\n");
				size = 0;
				return size;
		}
		send_buf[10] = controlReg_length; // the length of control register

		for (i=0; i<controlReg_length; i++)
		{
			send_buf[11+i] = controlReg_ls[i];  // this will change according to RS485 format later

		}
		size = controlReg_length;
	}
	else  // assemble 3 byte of ack. There will be only two commands: dataReg read and routingTable read so far
	{
		if (ack_flag == 1)  // succeed to receive the previous data, continue to next record
		{
			ack_array[2] = (uint8_t)(ack_array[2] | 0x08);
		}
		else
		{
			ack_array[2] = (uint8_t)(ack_array[2] & 0xF7);  // fail to receive the previous data, retry
		}
		if (retry_flag == 1)
		{
			ack_array[2] = (uint8_t)(ack_array[2] | 0x01);  // need to retry
		}
		else
		{
			ack_array[2] = (uint8_t)(ack_array[2] & 0xFE);
		}
		send_buf[10] = 0x03;  // the length of the ack_array

		for (i = 0; i < ack_array_length; i++)
		{
			send_buf[11+i] = (uint8_t)ack_array[i];
		}
/**
		for (i = 0; i < 8; i++)
		{
			send_buf[14+i] = (uint8_t)commandParameter[ack_array_length+i];
		}
		*/

		size = ack_array_length;
	}

	size = size + rs485_extra_length;  // the whole RS485 package size

	send_buf[size-2] = 0;   // the second last byte: checksum.
	for (i=0; i<(size-2); i++)
	{
		send_buf[size-2] += send_buf[i];
	}

	send_buf[size-1] = 0x16;  // the last byte: end byte
	send_buf[size] = '\0';	// extra byte at the end

	if (command_id == 01)  // parameter write: need to assemble another package according to the paramReg_gc
	{
		send_buf[size] = 0x68;
		for(i=1; i<9; i++)
		{
			send_buf[size+i] = 0;
		}
		send_buf[size+9] = 0x68;
		send_buf[size+10] = paramReg_length*2;
		uint16_t value = 0;
		uint8_t partA = 0;
		uint8_t partB = 0;
		for (i = 0; i< paramReg_length; i++)    // split uint16_t into two uint_8 first
		{
			value = paramReg_gc[i];
			partA = (uint8_t)((value & 0xFF00) >> 8);
			partB = (uint8_t)((value & 0x00FF));
			send_buf[size+11 + i*2] = (uint8_t)partA;
			send_buf[size+12 + i*2] = (uint8_t)partB;

		}
		int size1 = paramReg_length*2 + rs485_extra_length;   // the whole RS485 package size
		send_buf[size+ size1 -2] = 0;
		for (i=0; i< (size1-2); i++)
		{
			send_buf[size + size1 -2] += send_buf[i+size];
		}

		send_buf[size+ size1 -1] = 0x16;
		send_buf[size+ size1] = '\0';
		size = size + size1;   // the totale size of two packages
	}

	return size;
	//printf("%s\n", send_buf);

}

void reset_controlReg_ls()
{
	controlReg_ls[0] = 0x08;
	controlReg_ls[1] = 0x03;
	controlReg_ls[2] = 0x00;
}

int issue_command(int uart_fd, int send_pk_size)
{
	int flag;
	//flag= UART0_Send(uart_fd, send_buf, send_pk_size-1);
	flag= UART0_Send(uart_fd, send_buf, send_pk_size);
	if (flag == 0)
	{
		return 0;
	}
	else
	{
		return 1;
	}
}

int receive_message(int coordinator_id, uint8_t command_id, int uart_fd) // return the data_size if it receives message successfully
{
	int receive_pk_size = 0;
	int wait_time = 8; // default 6 seconds;
	int data_size = 0;
	reset_send_buf();
	reset_receive_buf();
	reset_data_array();

	//reset_ack_array();   // need to keep the ack_array from previous command

	if ((command_id == 0x04) || (command_id == 0x07)) // it takes longer time to discover network or to data register read
	{
		wait_time = 10;  // set 10 seconds for now
	}

	if ((command_id == 0x09) || (command_id == 0x0A) || (command_id == 0x0B))   // for calibrations
	{
		wait_time = 60;   // will be determined by the t_cal
	}

	printf("Command_id: %d, Wait time: %d. \n", command_id, wait_time);

	receive_pk_size = receive_pk(coordinator_id, wait_time, command_id, uart_fd);

	if (receive_pk_size == 0)  // no stream returned from coordinator
	{
		printf("Received packed NULL. Receive_pk_size: %d.\n", receive_pk_size);
		return 0;
	}

	//  returned stream will be either only ack_array, or data[] + ack_array
	//printf("Receive package size: %d\n", receive_pk_size);
	//print_receive_pk(receive_pk_size);

	process_ack(receive_pk_size);

	int timeout_flag = get_timeout();  // set by coordinator
	printf("####Timeout flag: %d\n", timeout_flag);

	if (timeout_flag == 1)
	{
		set_timeout(timeout_flag);
		if (command_id == 0x07)   // data register read
		{
			data_size = process_data(command_id);  // still have data returned
			update_controlReg_ls();
			return receive_pk_size;  // It returns the normal package size
		}
		else
		{
			data[0] = '\0';  // timeout, no data
			data_size = 0;
			//receive_pk_size = rs485_extra_length + ack_array_length;
			printf("Timeout from coordinator: %d. Continue to assemble outArray\n", timeout_flag);
			update_controlReg_ls();
			return receive_pk_size;  // It only returns the ack short package.
		}
	}

	int retry_flag = get_retry();
	printf("retry flag: %d\n", retry_flag);


	if (retry_flag == 0)  // no retry, there is data in it
	{
		data_size = process_data(command_id);

		int controlReg_write_flag = get_controlReg_write();
		printf("contrelReg_write flag: %d\n", controlReg_write_flag);
		if (controlReg_write_flag == 1)
		{
			update_controlReg_ls();
			printf("Command: %d is success. Move on to the next command.\n", command_id);
			return receive_pk_size; //move on to the next command.
		}
		else
		{
			printf("Command does not reset control write control bit. ControlReg_write flag: %d.\n", controlReg_write_flag);
			return 0;
		}
	}
	else
	{
		printf("Command needs to retry. Retry_flag: %d.\n", retry_flag);
		return 0;
	}
}

void print_send_pk(int coordinator_id, int pk_size)
{
	int i;
	printf("Coordinator id: %d: ", coordinator_id);
	for (i=0; i < pk_size; i++)
	{
		printf("0x%02x ", send_buf[i]);
	}
	printf("\n");
}

void print_receive_pk(int coordinator_id, int pk_size)
{
	int i;
	printf("Coordinator id: %d: ", coordinator_id);
	for (i=0; i < pk_size; i++)
	{
		printf("0x%02x ", receive_buf[i]);
	}
	printf("\n");
}


// return the length of the receive package, it also handles checksum
int receive_pk(int coordinator_id, int wait_time, uint8_t command_id, int uart_fd)
{
	int pk_size=0;  // default = 0
	int buf_size=0;
	int i;
	//char buf[0];
	uint8_t sum;

	switch (command_id) {
		case 0x00:  // read paramReg
			buf_size = paramReg_length * 2;
			break;
		//case 0x04:  // network discovery  no data returned
		//	buf_size = smNumber_length;
		//	break;
		case 0x05:  // route table
			buf_size = addressTable_length;
			break;
		case 0x06: // read controlReg
			buf_size = controlReg_length;
			break;
		case 0x07:  // read dataReg
			buf_size = dataReg_length * 2;
			break;
		case 0x0E:  // calibration register read
			buf_size = calReg_length * 2;
			break;
		default:
			buf_size = 0;
			break;  // no data will be returned
	}
	buf_size = buf_size + ack_array_length + rs485_extra_length;
	//buf_size = 16; // only has three bytes of ack_array

	pk_size = UART0_Recv(uart_fd, receive_buf, buf_size, wait_time);
	printf("In C receive_pk(), received buffer size: %d, pk_size: %d \n", buf_size, pk_size);

	print_receive_pk(coordinator_id, buf_size);

	if (pk_size !=buf_size)  // only receive three bytes of acknowledge
	{
		printf("In C, Coordinator ID: %d, only receive ack_array from coordinator \n", coordinator_id);
	}

	if ((receive_buf[0] == 0x68) & (receive_buf[pk_size-1] == 0x16))
	{
		for (i=0; i<pk_size-2; i++)
		{
			sum += receive_buf[i];
		}

		printf("In C, Coordinator ID: %d, Check sum: 0x%2x, sum of data: 0x%2x\n", coordinator_id, sum, (uint8_t)receive_buf[pk_size-2]);

		if ((uint8_t)receive_buf[pk_size-2] == sum)   // check sume is over flow, disable this for now.
		{
			receive_buf[pk_size] = '\0';
			printf("In C, Coordinator ID: %d, return from receive_pk() received buffer size: %d\n", coordinator_id, pk_size);
			//return pk_size;
		}
		else
		{
			pk_size = 0;
		}
	}
	else
	{
		pk_size = 0;
	}

	return pk_size;

}

void set_timeout(int flag)
{
	ack_array[2] = ack_array[2] | 0x02;
}

void process_ack(int receive_buf_size)
{

		ack_array[0] = receive_buf[receive_buf_size - 5];
		ack_array[1] = receive_buf[receive_buf_size - 4];
		ack_array[2] = receive_buf[receive_buf_size - 3];


		printf("acknowledge from coordinator: ");
		printf("0x%02x ", ack_array[0]);
		printf("0x%02x ", ack_array[1]);
		printf("0x%02x ", ack_array[2]);
		printf("\n");

}

int get_timeout()
{
	if (((ack_array[2] >> 1) & 0x01) == 1)  // byte2 bit 1 is 1
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

int get_retry()
{
		if ((ack_array[2] & 0x01) == 1)  // byte2 bit 0 is 1
		{
			return 1;
		}
		else
		{
			return 0;
		}
}


int process_data(uint8_t command_id)  // return the data_size
{

	int i;
	uint8_t partA;
	uint8_t partB;
	int data_size;

	memset(data, 0, sizeof(data));

	switch (command_id) {

		case 0x00:  // paramReg read
			printf("Read paramReg  through UART: \n");
			for (i=0; i< paramReg_length; i++)
			{
				partA = (uint8_t)receive_buf[2*i+11];
				partB = (uint8_t)receive_buf[2*i+12];
				paramReg[i] = ((partA << 8) | 0x0000) | (partB | 0x0000);
				printf("%d ", paramReg[i]);
			}
			printf("\n");

			for (i=0; i< paramReg_length*2; i++)
			{
				data[i] = (uint8_t)receive_buf[i+11];
			//printf("0x%02x ", outCArray[i]);
			}
			//printf("\n");
			data[paramReg_length*2] = '\0';
			data_size = paramReg_length*2;

			break;
/**
		case 0x04:  // network discovery  : now it only returns ack. No data returned.
			printf("Network discovery through UART: \n");
			for (i=0; i< smNumber_length; i++)
			{
				data[i] = (uint8_t)receive_buf[i+11];
			}
			data[smNumber_length] = '\0';
			data_size = smNumber_length;

			break;
*/

		case 0x05:  // route table read
			printf("Read route table through UART: \n");
			for (i=0; i< addressTable_length; i++)
			{
				data[i] = (uint8_t)receive_buf[i+11];
			}
			data[addressTable_length] = '\0';
			data_size = addressTable_length;

			break;

		case 0x06: // read controlReg
			printf("Read controlReg from register through UART: \n");
			for (i=0; i< controlReg_length; i++)
			{
				controlReg[i] = (uint8_t)receive_buf[i+11];
				outCArray[i] =  (uint8_t)receive_buf[i+11];
				//printf("0x%02x ", controlReg[i]);
			}
			//printf("\n");

			for (i=0; i< controlReg_length; i++)
			{
				data[i] = (uint8_t)controlReg[i];
				//printf("0x%02x ", outCArray[i]);
			}
			//printf("\n");
			data[controlReg_length] = '\0';
			data_size = controlReg_length;

			break;


		case 0x07:  // dataReg read
			printf("Read DataReg through UART: \n");
			for (i=0; i< dataReg_length; i++)
			{
				partA = (uint8_t)receive_buf[2*i+11];
				partB = (uint8_t)receive_buf[2*i+12];
				dataReg[i] = ((partA << 8) | 0x0000) | (partB | 0x0000);
				printf("%d ", dataReg[i]);
			}
			printf("\n");

			for (i=0; i< dataReg_length*2; i++)
			{
				data[i] = (uint8_t)receive_buf[i+11];
				//printf("0x%02x ", outCArray[i]);
			}
			//printf("\n");
			data[dataReg_length*2] = '\0';
			data_size = dataReg_length*2;

			break;

		case 0x0E:  // calReg read
			printf("Read CaliReg through UART: \n");
			for (i=0; i< calReg_length; i++)
			{
				partA = (uint8_t)receive_buf[2*i+11];
				partB = (uint8_t)receive_buf[2*i+12];
				calReg[i] = ((partA << 8) | 0x0000) | (partB | 0x0000);
				printf("%d ", calReg[i]);
			}
			printf("\n");

			for (i=0; i< calReg_length*2; i++)
			{
				data[i] = (uint8_t)receive_buf[i+11];
				//printf("0x%02x ", outCArray[i]);
			}
			//printf("\n");
			data[calReg_length*2] = '\0';
			data_size = calReg_length*2;

			break;

		default:
			printf("No data return.\n");
			data[0] = '\0';
			data_size = 0;
			break;
	}
	return data_size;

}

int get_controlReg_write()
{

		if ((ack_array[1] & 0x01) == 1)  // byte1 bit 0 is 1
		{
			return 1;
		}
		else
		{
			return 0;
		}
}

void update_controlReg_ls()
{

	controlReg_ls[0] = ack_array[0];
	controlReg_ls[1] = ack_array[1];
	controlReg_ls[2] = ack_array[2];

	printf("Server control register: ");
	printf("0x%02x ", ack_array[0]);
	printf("0x%02x ", ack_array[1]);
	printf("0x%02x ", ack_array[2]);
	printf("\n");

}

int assemble_outCArray(int coordinator_id, int receive_pk_size)
{
	int i;
	int size;
	int data_size = receive_pk_size - rs485_extra_length - ack_array_length;

	printf("In C, returning to Java, coordinator id: %d, outCArray: \n", coordinator_id);
	for ( i = 0; i < data_size; i++)
	{
		outCArray[i] = data[i];
		printf("0x%02x ", outCArray[i]);
	}
	//outCArray[data_size] = status;
	for ( i = 0; i < ack_array_length; i++)
	{
		outCArray[data_size+i] = ack_array[i];
	}

	printf("In C, status: 0x%02x\n", outCArray[data_size+ack_array_length-1]);

	size = data_size + ack_array_length;

	return size;
}




/**

int
input_timeout (int filedes, unsigned int seconds)
{
		fd_set set;
	struct timeval timeout;
	// Initialize the file descriptor set.
	FD_ZERO (&set);
	FD_SET (filedes, &set);

	// Initialize the timeout data structure.
	timeout.tv_sec = seconds;
	timeout.tv_usec = 0;

	// select returns 0 if timeout, 1 if input available, -1 if error.
	return TEMP_FAILURE_RETRY (select (FD_SETSIZE,
                                 &set, NULL, NULL,
                                 &timeout));
}

*/

/*********************************************************************                            End Of File                          **
*******************************************************************/



