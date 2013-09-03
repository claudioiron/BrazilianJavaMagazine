package br.com.javamagazine.server.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new AgendaError(e))
                    .build();
    }
}
