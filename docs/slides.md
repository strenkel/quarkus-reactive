---
title: Quarkus Reactive in Action
marp: true
theme: gaia
backgroundColor: white
color: black
footer: Matthias Bremer, Stefan Trenkel | team neusta
style: |
  footer {
    width: 100%;
    text-align: center;
  }
---

<!-- _class: lead --> 
# Quarkus Reactive

---

# Java, wir haben ein Problem!

- Java-Threads: Wrapper für OS-Threads
- Java-Threads sind Platform Threads
- Threads sind teuer:
  - Hoher Speicherverbrauch
  - Beschränkte Anzahl
- Java-Webserver 'von Haus aus' limitiert

---

# Threads in Action

```java
for (int i = 0; i < 1_000_000; i++) {
  new Thread(() -> {
    try {
      Thread.sleep(100_000); // 100 sec
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }).start();
}
```

---

# Thread.start()

- OutOfMemoryError nach 9.000 Threads
- Failed to start the native thread "Thread-9011"
- 180 ms pro 1.000 Thread
- 90 MB pro 1.000 Threads

---

# Lösungen

- Virtual-Threads
- Eventual Programming

---

# Lösung 1: Virtual Threads

- Project Loom
- Java 21
- JVM-verwaltet

---

# Virtual Threads in Action

```java
for (int i = 0; i < 1_000_000; i++) {
  Thread.startVirtualThread(() -> {
    try {
      Thread.sleep(100_000); // 100 sec
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  });
}
```

---

# Thread.startVirtualThread()

- 1.000.000 Virual Threads problemlos möglich
- 20 ms pro 1.000 VirtualThread
- 1MB pro 1.000 Virtual Thread
- 100 x mehr 'Threads' möglich
- Speicherverbrauch 100 x geringer
- Erzeugung 10 x schneller

---

# Lösung 2: Eventual Programming

- Event Loop
- Callbacks

---

# Problem: Callback Hell

### Lösungen

- Promises
- Async / Await
- Reactive Streams

