package de.neusta.quarkus;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("hello-wait")
public class GreetingWaitResource {
  
  @Path("classic")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() throws InterruptedException {
      TimeUnit.SECONDS.sleep(1);
      return "Hello!";
  }

  @Path("virtual")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @RunOnVirtualThread
  public String helloVirtual() throws InterruptedException {
    TimeUnit.SECONDS.sleep(1);
    return "Hello!";
  }

  @Path("reactive")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Uni<String> helloReactice() {
    return Uni.createFrom()
      .item("Hello!")
      .onItem()
      .delayIt()
      .by(Duration.ofSeconds(1));
  }

  @Path("nonblocking")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @NonBlocking
  public String helloNonBlocking() throws InterruptedException {
    TimeUnit.SECONDS.sleep(1);
    return "Hello!";
  }

}
