package de.neusta.quarkus;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("hello")
public class GreetingResource {

    @Path("classic")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello!";
    }

    @Path("virtual")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread
    public String helloVirtual() {
      return "Hello!";
    }

    @Path("reactive")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> helloReactice() {
      return Uni.createFrom().item("Hello!");
    }

    @Path("nonblocking")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public String helloNonBlocking() {
      return "Hello!";
    }
}
