#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  qianrushi.py
#
#  Copyright 2024 loongson <loongson@loongson>
#
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#  MA 02110-1301, USA.
#
#


# 示例数据：发送 GET 请求获取特定教室的预约信息
def get_booking_info(classroom_name, time):
    url = f"{base_url}/get_booking_info/{classroom_name}/{time}"
    response = requests.get(url)
    # print(f"Get Booking Info for {classroom_name} at {time} Response:", response.json())
    return response.json()


def update_classroom_status(lux, people_count):
    tt = time.localtime()
    if 8 <= tt.tm_hour < 12:
        period = "8:00-12:00"
    elif 14 <= tt.tm_hour < 18:
        period = "14:00-18:00"
    elif 18 <= tt.tm_hour < 22:
        period = "18:00-22:00"
    url = f"{base_url}/update_classroom_status"
    data = {
        "classroom_name": "A101",
        "time": period,
        "brightness": lux,
        "people_count": people_count,
    }
    response = requests.post(url, json=data)
    # --conding:utf-8--
    # print("Update Classroom Status Response:", response.json())


import requests
import json
import time
import threading
import schedule
from cffi import FFI

# 服务器的URL
base_url = "http://123.60.9.71:5000"


# 调用写好的与GPIO和I2C有关的C代码
ffi = FFI()
ffi.cdef(
    """
#define BH1750_DEVICE_ADDR 0x23

int i2c_init1( char *i2c_dev, unsigned char i2c_addr);
int i2c_readNbyte1(int fd, unsigned char *buf, int len);
int i2c_writeNbyte1(int fd, unsigned char *buf, int len);
int i2c_readReg1(int fd, unsigned int reg_addr, unsigned char *buf, int len);
int i2c_writeReg1(int fd, unsigned int reg_addr, unsigned char *buf, int len);
int i2c_close1(int fd);
int show_data1(unsigned char *readbuf);
int guangqiang_open1(void);
int guangqiang_show1(int fd);
int open_write1(const char *file, const char *value);
int open_read1(const char *file, char *value, int nbytes);
void hongwai1_open1();
int hongwai1_show1(void);
void hongwai2_open1();
int hongwai2_show1(void);
void jiaohsilight_open1();
void jiaoshilight_on_show1();
void jiaoshilight_off_show1();
void zuowei1_open();
void zuowei1_on_show();
void zuowei1_off_show();
void zuowei2_open();
void zuowei2_on_show();
void zuowei2_off_show();
void zuowei3_open();
void zuowei3_on_show();
void zuowei3_off_show();
void zuowei4_open();
void zuowei4_on_show();
void zuowei4_off_show();
"""
)
lib = ffi.dlopen("./final1.so")


# Events for signaling value changes
value_changed_event_hongwai1 = threading.Event()
value_changed_event_hongwai2 = threading.Event()


def monitor_hongwai1():
    global hongwai1, hongwai2, people, people_change

    while True:
        people_change = 0
        hongwai1 = lib.hongwai1_show1()

        if hongwai1 == 48:
            value_changed_event_hongwai1.set()
            value_changed_event_hongwai2.wait()
            value_changed_event_hongwai2.clear()
            people += 1
            people_change = 1


def monitor_hongwai2():
    global hongwai1, hongwai2, people, people_change

    while True:
        people_change = 0
        hongwai2 = lib.hongwai2_show1()

        if hongwai2 == 48:
            value_changed_event_hongwai2.set()
            value_changed_event_hongwai1.wait()
            value_changed_event_hongwai1.clear()
            people -= 1
            people_change = 1


# 任务一：控制教室灯光
def task_function1():
    biaoji = 0
    biaoji1 = 0
    biaoji2 = 0
    # people = 0

    hongwai1 = 0
    hongwai2 = 0
    people = 0
    people_change = 0

    thread1 = threading.Thread(target=monitor_hongwai1, daemon=True)
    thread2 = threading.Thread(target=monitor_hongwai2, daemon=True)

    thread1.start()
    thread2.start()
    while True:
        hongwai1 = lib.hongwai1_show1()
        hongwai2 = lib.hongwai2_show1()
        people_change = 0
        if hongwai1 == 48 and hongwai2 == 49:
            if biaoji2 == 1:
                if people > 0:
                    people = people - 1
                biaoji2 = 0
            else:
                biaoji1 = 1
        if hongwai1 == 49 and hongwai2 == 48:
            if biaoji1 == 1:
                people = people + 1
                people_change = 1
                biaoji1 = 0
            else:
                biaoji2 = 1

        if people_change == 1 or lib.guangqiang_show1(fd) <= 200:
            update_classroom_status(lib.guangqiang_show1(fd), people)

        if lib.guangqiang_show1(fd) <= 200 and people > 0 and biaoji == 0:
            lib.jiaoshilight_on_show1()
            biaoji = 1

        elif lib.guangqiang_show1(fd) > 200 or people == 0 and biaoji == 1:
            lib.jiaoshilight_off_show1()
            biaoji = 0

        # print (f"教室光照强度为 {lib.guangqiang_show1(fd)} ,教室人数为 {people}")
        # time.sleep(0.1)


# 任务二：接受服务器消息，控制座位插口通电
def task_function2():
    zuowei = {}
    last_zuowei = {}
    while True:

        tt = time.localtime()
        if 8 <= tt.tm_hour < 12:
            period = "8:00-12:00"
        elif 14 <= tt.tm_hour < 18:
            period = "14:00-18:00"
        elif 18 <= tt.tm_hour < 22:
            period = "18:00-22:00"
        # time.sleep(10)
        zuowei = get_booking_info("A101", period)
        print(zuowei)
        if last_zuowei == zuowei:
            continue
        else:
            for key in zuowei.keys():
                if key == "brightness" or key == "people_count":
                    time.sleep(10)
                    continue
                else:
                    if zuowei[key] == "seated":
                        if (int(key) % 4) == 1:
                            lib.zuowei1_on_show()
                        elif (int(key) % 4) == 2:
                            lib.zuowei2_on_show()
                        elif (int(key) % 4) == 3:
                            lib.zuowei3_on_show()
                        elif (int(key) % 4) == 0:
                            lib.zuowei4_on_show()

                last_zuowei = zuowei


# 任务三：在每天的特定时间段对所有插口进行断电
def task_function3():
    schedule.every().day.at("12:00").do(lib.zuowei1_off_show)
    schedule.every().day.at("12:00").do(lib.zuowei2_off_show)
    schedule.every().day.at("12:00").do(lib.zuowei3_off_show)
    schedule.every().day.at("12:00").do(lib.zuowei4_off_show)
    schedule.every().day.at("18:00").do(lib.zuowei1_off_show)
    schedule.every().day.at("18:00").do(lib.zuowei2_off_show)
    schedule.every().day.at("18:00").do(lib.zuowei3_off_show)
    schedule.every().day.at("18:00").do(lib.zuowei4_off_show)
    schedule.every().day.at("22:00").do(lib.zuowei1_off_show)
    schedule.every().day.at("22:00").do(lib.zuowei2_off_show)
    schedule.every().day.at("22:00").do(lib.zuowei3_off_show)
    schedule.every().day.at("22:00").do(lib.zuowei4_off_show)
    while True:
        schedule.run_pending()
        time.sleep(1)


# 打开所有接口
fd = lib.guangqiang_open1()
lib.hongwai1_open1()
lib.hongwai2_open1()
lib.jiaohsilight_open1()
lib.zuowei1_open()
lib.zuowei2_open()
lib.zuowei3_open()
lib.zuowei4_open()

# 执行多线程
thread1 = threading.Thread(target=task_function1, args=())
thread2 = threading.Thread(target=task_function2, args=())
thread3 = threading.Thread(target=task_function3, args=())

thread1.start()
thread2.start()
thread3.start()
