/*
 * final1.c
 * 
 * Copyright 2024 loongson <loongson@loongson>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */


#include <stdio.h>
#include <stdlib.h>
#include <linux/types.h>
#include <stdint.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <linux/i2c.h>
#include <linux/i2c-dev.h>
#include <unistd.h>
#include <sys/ioctl.h>
#define BH1750_DEVICE_ADDR 0x23

//    i2c相关代码

//初始化i2c
//i2c_dev设备文件 i2c_addr 设备地址 
//返回 0
int i2c_init1( char *i2c_dev, unsigned char i2c_addr)
{
	int res = 0;
	int fd = 0;
	
	fd = open(i2c_dev, O_RDWR);
	if(fd < 0)
	{
		printf("[%s]:[%d] open i2c file error\r\n", __FUNCTION__, __LINE__);
		return -1;
	}
	res = ioctl(fd,I2C_TENBIT,0);				   //7位模式 
	res = ioctl(fd,I2C_SLAVE, (i2c_addr ));    //设置I2C从设备地址
	
	return fd;
}

//读i2c
//buf数据 len长度 
//返回 实际读取的长度 
int i2c_readNbyte1(int fd, unsigned char *buf, int len)
{
	int res = 0;
	
	res = read(fd, buf,len);
	return res;
}

//写i2c
//buf数据 len长度 
//返回 实际写的长度 
int i2c_writeNbyte1(int fd, unsigned char *buf, int len)
{
	int res = 0;
	
	res = write(fd, buf,len);
	if(res<0)
	{   
		printf("[%s]:[%d] error !\r\n", __FUNCTION__, __LINE__);
	}

	return res;
}

int i2c_readReg1(int fd, unsigned int reg_addr, unsigned char *buf, int len)
{
	int res = 0;
	unsigned char buff[2];
	
	buff[0] = reg_addr >> 8;
	buff[1] = reg_addr & 0xff;
	res = write(fd, buff, 2);
	if(res<0)
	{   
		printf("[%s]:[%d] error!\r\n", __FUNCTION__, __LINE__);
	}
	res = read(fd, buf,len);
	if(res<0)
	{   
		printf("[%s]:[%d] error!\r\n", __FUNCTION__, __LINE__);
	}
	
	return res;
}

int i2c_writeReg1(int fd, unsigned int reg_addr, unsigned char *buf, int len)
{
	int res = 0,i;
	unsigned char *buff = 0;
	
	buff = (unsigned char *)malloc((len+2));
	buff[0] = reg_addr >> 8;
	buff[1] = reg_addr & 0xff;
	for(i = 0; i < len; i++)
		buff[(i+2)] = buf[i];
	res = write(fd, buff, (len+2));
	if(res<0)
	{   
		printf("[%s]:[%d] error!\r\n", __FUNCTION__, __LINE__);
	}

	free(buff);
	
	return res;
}

int i2c_close1(int fd)
{
	if(close(fd)<0)
	{   
		printf("[%s]:[%d] error!\r\n", __FUNCTION__, __LINE__);
	}

	return 0;
}

//    bh1750相关代码
int show_data1(unsigned char *readbuf)
{
    u_int32_t lux;
    lux = readbuf[0];
    lux = (lux << 8) | readbuf[1];
    return lux;
}

int guangqiang_open1(void)
{

    printf("----start---------\n");
    unsigned char writebuf[2] = {0x01,0x10};
 
	char DEFAULT_I2C_BUS[] = "/dev/i2c-0" ;
	int fd = i2c_init1(DEFAULT_I2C_BUS,  BH1750_DEVICE_ADDR);
 
    if (fd < 0)
    {
        printf("open failed/n");
        return -1;
    }

    i2c_writeNbyte1(fd, &writebuf[0], 1);
    usleep(100 * 1000); // 休眠一秒  
    i2c_writeNbyte1(fd, &writebuf[1], 1);    
    usleep(100 * 1000); // 休眠一秒
    
    return fd;
} 
 
int guangqiang_show1(int fd)
{    
	
	unsigned char readbuf[2];
	memset(readbuf, 0, sizeof(readbuf));
	i2c_readNbyte1(fd, readbuf, 2);
	return show_data1(readbuf);
}



//    GPIO相关代码

int open_write1(const char *file, const char *value)
{
    int fd, len;

    fd = open(file, O_WRONLY); // 以只写的方式打开文件
    if (fd < 0)
    {
        return -1;
    }

    len = write(fd, value, strlen(value)); // 向文件写数据
    if (len != strlen(value))
    {
        return -2;
    }

    close(fd);
    return 0;
}

int open_read1(const char *file, char *value, int nbytes)
{
    int fd, len;

    fd = open(file, O_RDONLY); // 以只读的方式打开文件
    if (fd < 0)
    {
        return -1;
    }

    len = read(fd, value, nbytes); // 从文件读取数据
    if (len != nbytes)
    {
        return -2;
    }

    close(fd);
    return 0;
}



//    红外传感器相关代码

void hongwai1_open1()
{
    char *file_export = "/sys/class/gpio/export";
    char pin[] = "2"; 

    if (access("/sys/class/gpio/gpio2/value", F_OK)) // 检查文件是否存在
    {
        if (open_write1(file_export, pin)) // 导出GPIO
        {
            printf("open_write %s %s failed!\n", file_export, pin);
        }
    }

    if (open_write1("/sys/class/gpio/gpio2/direction", "in")) // 设置为输入模式
    {
        printf("open_write %s failed!\n", pin);
    }
}

int hongwai1_show1(void)
{
    char value[2] = {0};
    if (open_read1("/sys/class/gpio/gpio2/value", value, 1)) // 读取端口值
    {
        printf("open_read failed!\n" );
    }

    return (int)value[0];
}

void hongwai2_open1()
{
    char *file_export = "/sys/class/gpio/export";
    char pin[] = "3"; 

    if (access("/sys/class/gpio/gpio3/value", F_OK)) // 检查文件是否存在
    {
        if (open_write1(file_export, pin)) // 导出GPIO
        {
            printf("open_write %s %s failed!\n", file_export, pin);
        }
    }

    if (open_write1("/sys/class/gpio/gpio3/direction", "in")) // 设置为输入模式
    {
        printf("open_write %s failed!\n", pin);
    }
}

int hongwai2_show1(void)
{
    char value[2] = {0};
    if (open_read1("/sys/class/gpio/gpio3/value", value, 1)) // 读取端口值
    {
        printf("open_read failed!\n" );
    }

    return (int)value[0];
}



//    教室灯光相关代码


void jiaohsilight_open1()
{
    char *file_export = "/sys/class/gpio/export";
    char pin[] = "1"; 

    if (access("/sys/class/gpio/gpio1/value", F_OK)) // 检查文件是否存在
    {
        if (open_write1(file_export, pin)) // 导出GPIO
        {
            printf("open_write %s %s failed!\n", file_export, pin);
        }
    }

    if (open_write1("/sys/class/gpio/gpio1/direction", "out")) 
    {
        printf("open_write %s failed!\n", pin);
    }
}

void jiaoshilight_on_show1()
{
	char on[] = "1" ;
    if (open_write1("/sys/class/gpio/gpio1/value", on)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}

void jiaoshilight_off_show1()
{
	char off[] = "0" ;
    if (open_write1("/sys/class/gpio/gpio1/value", off)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}



//   座位通电相关代码

void zuowei1_open()
{
    char *file_export = "/sys/class/gpio/export";
    char pin[] = "37"; 

    if (access("/sys/class/gpio/gpio37/value", F_OK)) // 检查文件是否存在
    {
        if (open_write1(file_export, pin)) // 导出GPIO
        {
            printf("open_write %s %s failed!\n", file_export, pin);
        }
    }

    if (open_write1("/sys/class/gpio/gpio37/direction", "out")) 
    {
        printf("open_write %s failed!\n", pin);
    }
}

void zuowei1_on_show()
{
	char on[] = "1" ;
    if (open_write1("/sys/class/gpio/gpio37/value", on)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}

void zuowei1_off_show()
{
	char off[] = "0" ;
    if (open_write1("/sys/class/gpio/gpio37/value", off)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}

void zuowei2_open()
{
    char *file_export = "/sys/class/gpio/export";
    char pin[] = "38"; 

    if (access("/sys/class/gpio/gpio38/value", F_OK)) // 检查文件是否存在
    {
        if (open_write1(file_export, pin)) // 导出GPIO
        {
            printf("open_write %s %s failed!\n", file_export, pin);
        }
    }

    if (open_write1("/sys/class/gpio/gpio38/direction", "out")) 
    {
        printf("open_write %s failed!\n", pin);
    }
}

void zuowei2_on_show()
{
	char on[] = "1" ;
    if (open_write1("/sys/class/gpio/gpio38/value", on)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}

void zuowei2_off_show()
{
	char off[] = "0" ;
    if (open_write1("/sys/class/gpio/gpio38/value", off)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}

void zuowei3_open()
{
    char *file_export = "/sys/class/gpio/export";
    char pin[] = "41"; 

    if (access("/sys/class/gpio/gpio41/value", F_OK)) // 检查文件是否存在
    {
        if (open_write1(file_export, pin)) // 导出GPIO
        {
            printf("open_write %s %s failed!\n", file_export, pin);
        }
    }

    if (open_write1("/sys/class/gpio/gpio41/direction", "out")) 
    {
        printf("open_write %s failed!\n", pin);
    }
}

void zuowei3_on_show()
{
	char on[] = "1" ;
    if (open_write1("/sys/class/gpio/gpio41/value", on)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}

void zuowei3_off_show()
{
	char off[] = "0" ;
    if (open_write1("/sys/class/gpio/gpio41/value", off)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}

void zuowei4_open()
{
    char *file_export = "/sys/class/gpio/export";
    char pin[] = "57"; 

    if (access("/sys/class/gpio/gpio57/value", F_OK)) // 检查文件是否存在
    {
        if (open_write1(file_export, pin)) // 导出GPIO
        {
            printf("open_write %s %s failed!\n", file_export, pin);
        }
    }

    if (open_write1("/sys/class/gpio/gpio57/direction", "out")) 
    {
        printf("open_write %s failed!\n", pin);
    }
}

void zuowei4_on_show()
{
	char on[] = "1" ;
    if (open_write1("/sys/class/gpio/gpio57/value", on)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}

void zuowei4_off_show()
{
	char off[] = "0" ;
    if (open_write1("/sys/class/gpio/gpio57/value", off)) // 设置输出值
    {
        printf("open_write failed!\n");
    }
}
