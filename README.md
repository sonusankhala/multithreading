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
👉 [Extending Thread Example](src/sankhala/multithread/ExtendeThread.java)

...

### 3.2 Implementing Runnable
👉 [Implementing Runnable Example](src/sankhala/multithread/RunnableThread.java)

👉 [Implementing using Lambda Example](src/sankhala/multithread/LambdaThread.java)

...

## 4. Thread Life Cycle

A thread goes through different states during its lifetime, from the time it is created until it finishes execution.

Java provides the `Thread.State` enum to represent the state of a thread.

### Thread States

A Java thread can be in one of the following six states:

1. `NEW`
2. `RUNNABLE`
3. `BLOCKED`
4. `WAITING`
5. `TIMED_WAITING`
6. `TERMINATED`

---

### Thread Life Cycle

```text
                    Thread Created
                         |
                         ↓
                       NEW
                         |
                      start()
                         |
                         ↓
                    RUNNABLE
                   /    |     \
                  /     |      \
                 ↓      ↓       ↓
             BLOCKED  WAITING  TIMED_WAITING
                 \      |       /
                  \     |      /
                   \    |     /
                    ↓   ↓    ↓
                    RUNNABLE
                       |
                       ↓
                   TERMINATED

```
RUNNING is not a separate state in Java's Thread.State enum.
A thread that is actually executing is considered to be in the RUNNABLE state.

Important Difference: BLOCKED vs WAITING vs TIMED_WAITING

These three states are frequently asked in interviews.

| State | Meaning | Common Causes |
|---|---|---|
| `BLOCKED` | Waiting to acquire a monitor lock | `synchronized` |
| `WAITING` | Waiting indefinitely for another thread to perform an action | `Object.wait()`, `Thread.join()`, `LockSupport.park()` |
| `TIMED_WAITING` | Waiting for a specified amount of time | `Thread.sleep()`, `Object.wait(timeout)`, `Thread.join(timeout)` |
