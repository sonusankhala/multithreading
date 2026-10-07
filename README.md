# Java Multithreading

## Table of Contents

1. [Introduction](#1-introduction)
2. [What is a Thread?](#2-what-is-a-thread)
3. [Creating a Thread](#3-creating-a-thread)
4. [Thread Life Cycle](#4-thread-life-cycle)
5. [Thread Methods](#5-thread-methods)
6. [Synchronization](#6-synchronization)
7. [Inter-Thread Communication](#7-inter-thread-communication)
8. [Common Interview Questions](#8-common-interview-questions)

---

## 1. Introduction

Multithreading is a Java feature that allows multiple threads
to execute concurrently within a program.

### Why do we need Multithreading?

- Better CPU utilization
- Improved application responsiveness
- Parallel execution of independent tasks
- Useful for server-side applications

---

## 2. What is a Thread?

A thread is the smallest unit of execution within a process.

### Example

```java
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

public class Main {
    public static void main(String[] args) {

        MyThread thread = new MyThread();
        thread.start();
    }
}
```
...

## 3. Creating a Thread

There are two ways to create a new thread in Java. They are as follows:

One is by extending java.lang.Thread class
Another is by implementing java.lang.Runnable interface

### 3.1 Extending Thread
👉 [Extending Thread Example – TaskExample.java](src/sankhala.multithread.ExtendeThread.java)

...

### 3.2 Implementing Runnable
👉 [Implementing Runnable Example – TaskExample.java](src/sankhala.multithread.RunnableThread.java)

👉 [Implementing using Lambda Example – TaskExample.java](src/sankhala.multithread.LambdaThread.java)

...

## 4. Thread Life Cycle

...

## 5. Thread Methods

...

## 6. Synchronization

...

## 7. Inter-Thread Communication

...

## 8. Common Interview Questions

...
