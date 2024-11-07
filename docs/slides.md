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
- 180 ms pro 1.000 Thread
- 90 MB pro 1.000 Threads

---

# Lösungen

- Eventual Programming
- Virtual-Threads

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
--
- mind. 100 x mehr 'Threads' möglich
- Speicherverbrauch 100 x geringer
- Erzeugung 10 x schneller

---

# Lösung 2: Eventual Programming

- Event Loop
- Callbacks

---

# Reactive in Quarkus

**Baut auf drei Frameworks auf:**

- **Netty**: NIO client server framework (2004)
- **Vert.x**: Event-Loop basiertes Application-Framework (2011)
- **Mutiny**: Reactive Stream Library (2019)

---

# Vert.x

- "Node.js - Implementierung" für die JVM
- Hieß ursprünglich Node.x

---

# Hello Endpoint

```java
@GET
@Blocking // or @NonBlocking or @RunOnVirtualThread
public String hello() {
  return "Hello!";
}

@GET
public Uni<String> helloReactice() {
  return Uni.createFrom().item("Hello!");
}
```

---

# Hello Times

- Classic: 80us - 120.000rps
- Virtual: 80us - 115.000rps
- Reactive: 34us - 135.000rps
--
- Go: 60us - 120.000rps
- Wildfly: 110us - 40.000rps

*wrk: 1/1 and 10/100 threads/connections*

---

# Hello Wait Endpoint

```java
@GET
@Blocking // or @RunOnVirtualThread
public String hello() {
  TimeUnit.MILLISECONDS.sleep(5);
  return "Hello!";
}

@GET
public Uni<String> helloReactice() {
  return Uni.createFrom().item("Hello!")
      .onItem().delayIt().by(Duration.ofMillis(5));
}
```

---

# Hello Wait RPS

- Classic: 39.000 (default) - 85.000
- Virtual: 115.000
- Reactive: 98.000

*quarkus.thread-pool.max-threads=2000 (default ~ 200)*
*wrk: 10/1000 threads/connections*

---

# Zusammenfassung

- Quarkus ist supersonic
- Reactive / Virtual Threads lohnen nur in Spezialfällen 

---

<!-- _class: lead --> 
# Vielen Dank!

---

# Links

- [Quarkus vs. Go Performance](https://medium.com/deno-the-complete-reference/quarkus-vs-go-frameworks-hello-world-performance-03b8eb84dec7)


---

# Notes

- Tests auf einem i7-4702MQ Notebook mit 2,2 GHz von 2014
- Mit Ubuntu 22.04 LTS
- wrk Version 4.2.0
- go version 1.23.1
- Quarkus 3.16.2
- Wildfly 34.0.0
- Java 21