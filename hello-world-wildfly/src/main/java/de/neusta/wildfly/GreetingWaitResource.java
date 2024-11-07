package de.neusta.wildfly;

import java.util.concurrent.TimeUnit;

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

}
