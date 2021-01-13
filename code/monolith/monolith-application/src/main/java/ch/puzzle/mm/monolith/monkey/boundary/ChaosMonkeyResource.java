package ch.puzzle.mm.monolith.monkey.boundary;

import ch.puzzle.mm.monolith.monkey.control.ChaosMonkeyService;
import ch.puzzle.mm.monolith.monkey.control.Monkey;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/chaos-monkey")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChaosMonkeyResource {

    @Inject
    ChaosMonkeyService monkeyService;

    @GET
    public Response list() {
        return Response.ok(monkeyService.getAllMonkeys()).build();
    }

    @POST
    public void create(List<Monkey> monkey) {
        monkey.forEach(monkeyService::addMonkey);
    }

    @PUT
    @Path("/{id}")
    public void update(Monkey monkey) {
        monkeyService.addMonkey(monkey);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id) {
        monkeyService.removeMonkey(id);
    }
}