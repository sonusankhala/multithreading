# Multithreading in Java


📘 1. Introduction

Multi-threading refers to establishing multiple concurrent flows of execution control within a single program environment.

Core Advantages

1. Resource Maximization: When one thread blocks (e.g., waiting for Network I/O or File access), another thread grabs the CPU rather than letting it sit idle.
2. Concurrent Problem Modeling: Complex applications can run animation, sound streams, document rendering, and background downloads simultaneously.
[!NOTE]
Java threads are lightweight processes that execute inside the same memory space. This allows seamless inter-thread communications since objects can access shared states directly without hefty OS-level context switching overhead.

⚙️ 2. Basics of a Thread

Everything governing thread behavior in Java is encapsulated inside the java.lang.Thread class.

A. Creating and Running Threads

There are two core architectural methodologies to implement a custom execution thread:

Method 1: Subclassing the Thread Class

Extend Thread and override its entry-point run() method.
java
class CustomWorker extends Thread {
    @Override
    public void run() {
        for(int i = 1; i <= 5; i++) {
            System.out.println("Subclass Thread counter: " + i);
        }
    }
}

// Execution call inside main
CustomWorker threadA = new CustomWorker();
threadA.start(); // Spawns the underlying system runtime thread context
Use code with caution.

Method 2: Implementing the Runnable Interface (Preferred)

Decouples your processing logic from the inheritance structure by passing your class task directly to a blank Thread target container.
java
class TaskLogic implements Runnable {
    @Override
    public void run() {
        System.out.println("Executing task independently via interface mapping.");
    }
}

// Execution call inside main
Thread threadB = new Thread(new TaskLogic());
threadB.start();
Use code with caution.

B. The 5 States of the Thread Lifecycle

A Java thread always lives within one of these specific lifecycle operational nodes:
  [ Newborn ] 
       │  (t.start())
       ▼
  [ Runnable ] <────► [ Running ] 
       ▲                  │
       │ (Event Fired)    │ (Blocked / Sleep / Wait)
       │                  ▼
       └──────────── [ Blocked ]
                          │
                          │ (Execution Finishes)
                          ▼
                       [ Dead ]
1. Newborn: The object is instantiated via new statement, but resources haven't allocated runtime contexts yet (start() not called).
2. Runnable: The thread is fully configured, registered, and waiting in line for its allocated slice of CPU time from the OS thread scheduler.
3. Running: The thread gains active control of the processor core; its compilation instructions are currently executing.
4. Blocked: The thread is temporarily barred from entering the execution pool due to events like sleep() delays, waiting for structural locks, or resource input blockages.
5. Dead: The thread finishes its specified run() block or is forcefully terminated.

C. Primary State Control Methods

• start(): Allocates system thread resources and changes state from Newborn to Runnable.
• sleep(long ms): Transitions a thread to a Blocked/Timed Waiting state for a designated length of time. Keep in mind it keeps its acquired monitor locks active.
• yield(): Forces the currently running thread to willingly pause context and hand resource priority back to the scheduler, letting other waiting threads of equal standing execute.

🔒 3. Synchronization and Inter-Thread Communication

Because all threads execute inside a shared memory sandbox, multiple processes reading and writing to the exact same memory attributes concurrently leads to data inconsistency and corruption anomalies known as Data Races.

A. Thread Synchronization (Object Monitors)

To fix critical concurrent collisions, Java assigns an implicit, hardware-level Object Monitor Lock to every single instantiated object.
The synchronized keyword acts as a gatekeeper to guarantee mutual exclusion:
java
class BankAccount {
    private int balance = 1000;

    // Mutex lock ensures only one thread can adjust balance at a time
    public synchronized void deposit(int amount) {
        balance += amount;
    }
    
    public synchronized void withdraw(int amount) {
        balance -= amount;
    }
}
Use code with caution.
[!TIP]
If a class wasn't originally written to be thread-safe, you can inject an ad-hoc Synchronized Block to target a specific resource monitor without locking entire methods:
java
synchronized(accountInstance) {
    accountInstance.unsafeMethodCall();
}
Use code with caution.

B. Inter-Thread Communication Protocol

These coordination methods reside within the java.lang.Object base package and can only be executed safely inside a synchronized context:
Method Primitive	Functional Action	Lock State Handling
wait()	Relinquishes its acquired monitor lock immediately and blocks execution indefinitely.	Releases the held monitor lock.
notify()	Wakes up a single thread currently waiting on that specific object monitor.	Holds lock until the current thread finishes the synchronized block.
notifyAll()	Dispatches a wake-up broadcast to all threads waiting on that object monitor's queue.	Holds lock until the active thread exits.

👥 4. Thread Groups and Daemon Threads


Thread Groups

A management structure designed to classify and manipulate multiple threads simultaneously. Every single thread instance is structurally a member of exactly one group. By default, the JVM initializes a SystemThreadGroup acting as the foundational root node.

Daemon Threads

Low-priority background service layers (e.g., the JVM Garbage Collector).
• They run continuously inside indefinite service loops to support user threads.
• The moment all active User Threads finish execution, the JVM shuts down completely, terminating all remaining Daemon processes instantly.
java
Thread serviceThread = new Thread(new BackupTask());
serviceThread.setDaemon(true); // Must be configured before calling start()!
serviceThread.start();
Use code with caution.

📝 5. Academic Assignment Tasks

Practice implementing these classic programmatic concurrency design structures:

Assignment 1: Thread-Safe Concurrent Stack

Implement a custom bounded Stack array handler that safely coordinates concurrent push() and pop() operations using structural synchronization rules.

Assignment 2: Parallel Number Classifier

Write an execution pipeline where a master thread populates an unchecked array of random numbers, while three downstream worker threads filter and evaluate negative values, positive-even values, and positive-odd values concurrently.

❓ 6. Core Technical Interview Q&A


Q: Can a class constructor method be marked as synchronized?

No. Constructors cannot be synchronized. An object is under construction during execution and is only reachable by the thread initializing it. Attempting to add a synchronization keyword to a constructor layout triggers an unsupported modifier compilation error.

Q: What is the vital difference between starting execution via start() and calling run() directly?

• start(): Requests a new system call-stack allocation from the OS runtime scheduler and triggers its internal asynchronous execution sequence.
• run(): Performs a normal, synchronous procedural method invocation. No separate thread stack is generated; the logic runs entirely on the caller's thread.

Q: How does Thread.sleep() differ fundamentally from Object.wait()?

• sleep() pauses execution for a set duration but retains all active object locks. It is a static utility of the Thread class.
• wait() pauses execution indefinitely until a notification is received, and it unconditionally drops its acquired monitor lock to prevent system-wide deadlock jams. It is inherited from the Object archetype.
