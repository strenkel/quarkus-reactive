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
--
- 100 x mehr 'Threads' möglich
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

# Hello World Endpoint

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

# Requests per Second

- Classic: 120.000
- Virtual: 115.000
- Reactive: 135.000
--
- Go: 120.000

*wrk: 10 threads and 100 connections*

---

# Hello Wait Endpoint

```java
@GET
@Blocking // or @RunOnVirtualThread
public String hello() {
  TimeUnit.SECONDS.sleep(1);
  return "Hello!";
}

@GET
public Uni<String> helloReactice() {
  return Uni.createFrom().item("Hello!")
      .onItem().delayIt().by(Duration.ofSeconds(1));
}
```

---

# Max Requests per Second

- Classic: 200 (default) - 8000
- Virtual: bis zu 15.000
- Reactive: bis zu 15.000

*quarkus.thread-pool.max-threads=8000 (default ~ 200)*
*Gemessen mit k6*

---

# Zusammenfassung

- Quarkus ist supersonic
- Reactive Datenbank-Anbindung keine Empfehlung
- Reactive Http-Clients nutzen 

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