package ch.puzzle.mm.rest.monkey.boundary;


import ch.puzzle.mm.rest.monkey.control.ChaosMonkeyService;
import ch.puzzle.mm.rest.monkey.control.Monkey;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Dependent
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MethodMonkeySubResource {

    @Inject
    ChaosMonkeyService monkeyService;

    @GET
    public Response getMethodMonkey(@PathParam("class") String clazz, @PathParam("method") String method) {
        return Response.ok(monkeyService.getMonkey(clazz, method)).build();
    }

    @PUT
    public void updateMethodMonkey(@PathParam("class") String clazz, @PathParam("method") String method, Monkey monkey) {
        monkeyService.addMonkey(monkey, clazz, method);
    }

    @DELETE
    public void deleteMethodMonkey(@PathParam("class") String clazz, @PathParam("method") String method) {
        monkeyService.removeMonkey(clazz, method);
    }
}