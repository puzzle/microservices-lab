package ch.puzzle.mm.kafka.order.monkey.boundary;



import ch.puzzle.mm.kafka.order.monkey.control.ChaosMonkeyService;
import ch.puzzle.mm.kafka.order.monkey.control.Monkey;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Dependent
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClassMonkeySubResource {

    @Inject
    ChaosMonkeyService monkeyService;

    @GET
    public Response getClassMonkey(@PathParam("class") String clazz) {
        return Response.ok(monkeyService.getMonkey(clazz, null)).build();
    }

    @PUT
    public void updateClassMonkey(@PathParam("class") String clazz, Monkey monkey) {
        monkeyService.addMonkey(monkey, clazz, null);
    }

    @DELETE
    public void deleteClassMonkey(@PathParam("class") String clazz) {
        monkeyService.removeMonkey(clazz, null);
    }
}