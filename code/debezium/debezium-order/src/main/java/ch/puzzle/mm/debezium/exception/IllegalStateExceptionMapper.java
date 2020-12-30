package ch.puzzle.mm.debezium.exception;

import javax.json.Json;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {

    @Override
    public Response toResponse(IllegalStateException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(Json.createObjectBuilder()
                        .add("message", exception.getMessage())
                        .build())
                .build();
    }
}
