# Java Multithreading

## Table of Contents

1. [Introduction](#1-introduction)
2. [What is a Thread?](#2-what-is-a-thread)
3. [Creating a Thread](#3-creating-a-thread)
4. [Thread Life Cycle](#4-thread-life-cycle)
5. [Thread Methods](#5-thread-methods)
6. [Synchronization](#6-synchronization)
7. [Common Interview Questions](#8-common-interview-questions)

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

## 3. Creating a Thread

There are two ways to create a new thread in Java. They are as follows:

One is by extending java.lang.Thread class
Another is by implementing java.lang.Runnable interface

### 3.1 Extending Thread
👉 [Extending Thread Example](src/sankhala/multithread/ExtendeThread.java)

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

## 5. Thread Method
<img width="675" height="513" alt="image" src="https://github.com/user-attachments/assets/1932e319-cc28-4a40-b7b5-41661383aa32" />

----

## 6. Synchronization

### 6.1 What Is Synchronization in Java?
Synchronization is a mechanism used to control access to shared mutable resources when multiple threads execute concurrently.
It helps prevent race conditions and maintains data consistency by allowing only one thread at a time to execute code protected by the same monitor lock.

Why Do We Need Synchronization?

Consider two threads incrementing a shared counter.

class Counter {
    int count = 0;

    void increment() {
        count++;
    }
}

The statement count++ is not atomic. It involves reading the value, incrementing it, and writing the result.

If two threads execute it concurrently, one update may be lost.

### Example: Without Synchronization

class Counter {
    int count = 0;

    void increment() {
        count++;
    }
}

public class Main {
    public static void main(String[] args)
            throws InterruptedException {

        Counter counter = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter.count);
    }
}

Expected output: 200000

The actual output may be lower because of a race condition.

### Example: With Synchronization

class Counter {
    int count = 0;

    synchronized void increment() {
        count++;
    }
}

Now, only one thread at a time can execute increment() on the same Counter object.

The final count will be 200000.

### 6.2 What Is a Monitor Lock?

Every Java object can be used as a monitor lock.
A thread must acquire the relevant object's monitor before entering a synchronized instance method or block protected by that monitor.
If another thread already owns the lock, the requesting thread must wait until the lock becomes available.

synchronized (lockObject) {
    // Critical section
}

### Important Terms

| Term | Meaning |
|---|---|
| Monitor lock | Lock associated with an object's monitor |
| Critical section | Code that accesses shared data and needs protection |
| Mutual exclusion | Only one thread at a time can hold a particular monitor |
| Race condition | Result depends on the timing of concurrent operations |
| Thread contention | Multiple threads compete for the same resource or lock |

### Interview Question: How can you check whether the current thread holds a monitor lock?

Use `Thread.holdsLock()`.

```java
synchronized (lockObject) {
    System.out.println(Thread.holdsLock(lockObject)); // true
}
```
It returns `true` if the current thread holds the monitor of the specified object.

[Monitor Lock Example](src/sankhala/multithread/MonitorLock.java)


### 6.3 Types of Synchronization in Java
#### Synchronized Instance Method

A synchronized instance method acquires the monitor of the current object, represented by `this`.

```java
class Printer {

    synchronized void printMessage(String message) {
        System.out.println(
            Thread.currentThread().getName() + ": " + message
        );
    }
}
```
If two threads call this method on the **same `Printer` instance**, only one can execute the synchronized method at a time.
However, different instances have different monitors, so their synchronized instance methods can execute concurrently.

#### Interview Question: Does a synchronized method lock the entire class?

**No.** A synchronized instance method locks the particular object on which it is invoked.

#### Synchronized Block

A synchronized block protects only a specific section of code.

```java
class Counter {

    private int count = 0;

    void increment() {
        synchronized (this) {
            count++;
        }
    }

    int getCount() {
        synchronized (this) {
            return count;
        }
    }
}
```

Here, both methods synchronize on the same object, `this`.

#### Why Use a Synchronized Block?

- It protects only the critical section.
- It allows non-critical code to execute without acquiring the lock.
- It can reduce unnecessary lock contention.

#### Using a Separate Lock Object

```java
class Counter {

    private int count = 0;
    private final Object lock = new Object();

    void increment() {
        synchronized (lock) {
            count++;
        }
    }
}
```

A private lock object is useful when you want to control access to a resource without exposing the object's own monitor.

**Important:** Threads must synchronize using the same lock object to coordinate with one another.

#### Static Synchronized Method

A static synchronized method acquires the monitor of the class's `Class` object.

```java
class Counter {

    private static int count = 0;

    static synchronized void increment() {
        count++;
    }

    static synchronized int getCount() {
        return count;
    }
}
```

The lock is `Counter.class`, not an instance of `Counter`.

The following is equivalent in terms of locking:

```java
class Counter {

    private static int count = 0;

    static void increment() {
        synchronized (Counter.class) {
            count++;
        }
    }
}
```

#### Interview Question: Can a static synchronized method and an instance synchronized method block each other?

**Not by default.**
- An instance synchronized method locks `this`.
- A static synchronized method locks `ClassName.class`.

These are different monitors, so the methods do not block each other merely because they belong to the same class.

---

#### Difference Between Synchronized Method and Synchronized Block

| Feature | Synchronized Method | Synchronized Block |
|---|---|---|
| Scope | Entire method | Selected code section |
| Instance lock | `this` | Specified object |
| Static lock | `ClassName.class` | Specified object |
| Flexibility | Less granular | More granular |
| Typical use | Entire method needs protection | Only part of method needs protection |

### Interview Answer

A synchronized block provides finer-grained control over synchronization because we can choose the lock object and protect only the critical section.

---

#### What Is the Difference Between Instance and Static Synchronization?

| Feature | Instance Synchronization | Static Synchronization |
|---|---|---|
| Lock target | Current object (`this`) | Class object (`ClassName.class`) |
| Shared among | Calls on the same instance | Calls using the same class monitor |
| Different instances | Can execute concurrently | Still use the same class monitor |
| Common use | Instance-level shared state | Static shared state |

#### Example

```java
class Example {

    synchronized void instanceMethod() {
        // Locks this
    }

    static synchronized void staticMethod() {
        // Locks Example.class
    }
}
```

---

#### Can Two Synchronized Methods Execute at the Same Time?

It depends on the lock.

```java
class Example {

    synchronized void methodA() {
        // Uses this
    }

    synchronized void methodB() {
        // Also uses this
    }
}
```

If two threads call `methodA()` and `methodB()` on the **same instance**, they cannot execute these methods simultaneously because both require the same monitor.

However:

```java
Example obj1 = new Example();
Example obj2 = new Example();
```

Calls on `obj1` and `obj2` can execute concurrently because the objects have different monitors.

**Interview takeaway:** Synchronization is about the lock being acquired, not simply the method or class name.

##### What Is Reentrant Synchronization?

Java's intrinsic monitors are **reentrant**.

This means a thread that already owns a monitor can acquire the same monitor again without blocking itself.

```java
class Example {

    synchronized void methodA() {
        System.out.println("Inside methodA");
        methodB();
    }

    synchronized void methodB() {
        System.out.println("Inside methodB");
    }
}
```

When a thread invokes `methodA()`, it acquires the object's monitor. It can then call `methodB()` on the same object because it already owns that monitor.

### Interview Question: Can a synchronized method call another synchronized method on the same object?

**Yes.** Intrinsic locks are reentrant, so the same thread can reacquire a monitor it already owns.

---

### What Is Inter-Thread Communication?

Inter-thread communication allows threads to coordinate their activities rather than continuously checking whether a condition has changed.

Java provides three important methods on `Object`:

- `wait()`
- `notify()`
- `notifyAll()`

### `wait()`

Causes the current thread to wait and releases the monitor of the object on which `wait()` is called.

### `notify()`

Wakes one thread waiting on that object's monitor.

### `notifyAll()`

Wakes all threads waiting on that object's monitor. They must still compete to reacquire the monitor.

### Example

```java
class SharedResource {

    private boolean ready = false;

    synchronized void awaitReady() throws InterruptedException {

        while (!ready) {
            wait();
        }

        System.out.println("Resource is ready");
    }

    synchronized void makeReady() {

        ready = true;
        notifyAll();
    }
}
```

### Important Rules

- Call `wait()`, `notify()`, and `notifyAll()` while owning the relevant object's monitor.
- Otherwise, Java throws `IllegalMonitorStateException`.
- Use a `while` loop around `wait()` to recheck the condition after waking up.
- `notify()` does not immediately transfer the lock to the notified thread.

---

### What Is Deadlock?

A **deadlock** occurs when two or more threads wait indefinitely for resources or locks held by one another.

### Example

```java
public class DeadlockExample {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (LOCK_A) {
                System.out.println("Thread 1 acquired LOCK_A");

                synchronized (LOCK_B) {
                    System.out.println("Thread 1 acquired LOCK_B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (LOCK_B) {
                System.out.println("Thread 2 acquired LOCK_B");

                synchronized (LOCK_A) {
                    System.out.println("Thread 2 acquired LOCK_A");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
```

If `t1` acquires `LOCK_A` while `t2` acquires `LOCK_B`, each thread may wait for the lock held by the other.

The program can then remain stuck.

### How to Prevent Deadlock

1. Acquire multiple locks in a consistent global order.
2. Avoid unnecessary locks.
3. Keep critical sections small.
4. Consider timed lock acquisition with `Lock.tryLock()` when appropriate.
5. Avoid waiting indefinitely while holding locks.

### How to Detect Deadlock

A thread dump can help identify deadlocks.

Useful tools include:

- `jstack`
- JConsole
- Java VisualVM

---

#### What Is Livelock?

A **livelock** occurs when threads remain active and repeatedly respond to each other, but make no useful progress.

### Deadlock vs Livelock

| Feature | Deadlock | Livelock |
|---|---|---|
| Thread activity | Threads are stuck waiting | Threads remain active |
| Progress | No progress | No useful progress |
| Typical cause | Circular lock dependency | Repeated reactions or retries |
| Possible solution | Change locking strategy/order | Change retry strategy or introduce backoff |

### Example Scenario

Two threads repeatedly give way to each other, each waiting for the other to proceed. They keep changing their state but never finish their tasks.

## 11. What Is Starvation?

**Starvation** occurs when a thread is repeatedly denied the CPU or access to a resource and therefore makes little or no progress.

It may happen because of unfair scheduling, excessive lock contention, or a thread repeatedly losing access to a shared resource.

### How Is It Different From Deadlock?

In a deadlock, a group of threads is stuck waiting on one another. In starvation, other threads may continue executing while one thread is unable to make progress.

#### What Are the Disadvantages of Synchronization?

Synchronization helps maintain correctness, but excessive or poorly designed synchronization can cause:

- **Performance overhead:** Threads spend time acquiring locks.
- **Lock contention:** Threads compete for the same monitor.
- **Deadlock:** Threads wait indefinitely for one another's locks.
- **Starvation:** Some threads repeatedly fail to acquire resources.
- **Reduced concurrency:** Unnecessarily large critical sections prevent independent work from running concurrently.

### Best Practices

- Synchronize only where necessary.
- Keep critical sections short.
- Use a consistent lock ordering.
- Prefer private lock objects when appropriate.
- Do not assume that `volatile` makes compound operations atomic.
- Consider `java.util.concurrent` utilities when they better fit the problem.

---

### What Is the Difference Between `synchronized` and `Lock`?

Java also provides explicit locking APIs through `java.util.concurrent.locks`.

| Feature | `synchronized` | `Lock` API |
|---|---|---|
| Lock management | Automatic | Explicit |
| Lock release | When the synchronized block/method exits | Usually requires `unlock()` in `finally` |
| Timed acquisition | Not directly supported | `tryLock(timeout, unit)` |
| Interruptible acquisition | Not directly supported | `lockInterruptibly()` |
| Flexibility | Simple and built into the language | More locking options |
| Best use | Straightforward mutual exclusion | When advanced lock control is needed |

### Example Using `ReentrantLock`

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {

    private int count = 0;
    private final Lock lock = new ReentrantLock();

    void increment() {

        lock.lock();

        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
}
```

The `finally` block ensures the lock is released even if an exception occurs.

---
