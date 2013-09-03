package br.com.javamagazine.server.providers;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        StringBuilder builder = new StringBuilder(violations.size() * 30);
        for (ConstraintViolation<?> v : violations) {
            final String message = v.getMessage();
            builder.append(builder.length() > 0 ? "\n" : "").append(message);
        }

        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new AgendaError(builder.toString(), e))
                .build();
    }
}
