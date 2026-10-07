# Multithreading in Java

### Introduction: 
Multi-threading means multiple flow of control. Multi-threading programming is a conceptual paradigm for programming where one can divide a program into two or more processes which can be run in parallel. There are two main advantages of multi-threading : Fist, program with multiple threads will, in general, result in better utilization of system resources, including the CPU, because another line of execution can grab the CPU when one line of execution is blocked. Second, there are several problems better solved by multiple threads. For example, we can easily write a multi-threaded program to show animation, play music, display documents, and down load files from the network at the same time.

Java is a multi-threaded language. Java allows to write a program where more than one processes can be executed concurrently within the single program. Java's threads are often referred to as light weight threads, which means that they run in the same memory space. Because Java threads run in the same memory space, they can easily communicate among themselves because an object in one thread can call a method in another thread without any overhead from the operating system. In this Tutorial we will learn how to do multi-threaded programming in Java.

### Basics of a thread:
As with the Java concepts, everything about thread are defined in a class Thread. The Thread class encapsulates all of the control one will need over threads. The Thread class is our only link to manage how threads behave. In this Section, we will learn about : how to create and run a thread, the life cycle of a thread, and the thread controlling methods.

### Creating and Running a Thread:

Life cycle of threads
Status of a Thread
Synchronization and Inter-Thread Communication
Synchronization
Inter-thread Communication
Thread Groups and Daemon
Practice Questions
Assignment
Q&A
