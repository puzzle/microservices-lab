package ch.puzzle.mm.kafka.order.monkey.boundary;



import ch.puzzle.mm.kafka.order.monkey.control.ChaosMonkeyService;
import ch.puzzle.mm.kafka.order.monkey.control.Monkey;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/chaos-monkey")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChaosMonkeyResource {

    @Inject
    ChaosMonkeyService monkeyService;

    @Inject
    ClassMonkeySubResource classMonkeySubResource;

    @Inject
    MethodMonkeySubResource methodMonkeySubResource;

    @Path("/{class}")
    public ClassMonkeySubResource classMonkey() { return classMonkeySubResource; }

    @Path("/{class}/{method}")
    public MethodMonkeySubResource methodMonkey() { return methodMonkeySubResource; }

    @GET
    public Response list() {
        return Response.ok(monkeyService.getAllMonkeys()).build();
    }

    @PUT
    public void update(Monkey monkey) {
        monkeyService.addMonkey(monkey, null, null);
    }
}