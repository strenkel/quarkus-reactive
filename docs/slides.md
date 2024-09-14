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
for (int i = 0; i < 100_000; i++) {
  new Thread(() -> {
    try {
      Thread.sleep(10_000); // 10 sec
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
}).start();
```

---

# Thread.start()

- OutOfMemoryError nach 9.000 Threads
- Failed to start the native thread "Thread-9011"
- 5 ms pro Thread
- 100 MB pro 1000 Threads

---

# Lösungen

- Event-Loops
- Virtual-Threads

---


