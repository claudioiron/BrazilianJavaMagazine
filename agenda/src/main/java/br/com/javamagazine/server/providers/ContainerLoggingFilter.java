package br.com.javamagazine.server.providers;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
public class ContainerLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
	private static final String START_TIME = "ContainerLoggingFilter#START_TIME";
    private static final Logger LOGGER = Logger.getLogger(ContainerLoggingFilter.class.getName());

    public ContainerLoggingFilter(){}

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
    	requestContext.setProperty(START_TIME, System.currentTimeMillis());
        String method = requestContext.getMethod();
    	String path = requestContext.getUriInfo().getPath();
    	MediaType mediaType = requestContext.getMediaType();
        String body = readBody(requestContext);
        LOGGER.log(Level.INFO, "Request: {0} {1} - ContentType: {2} -> body: {3}", new Object[]{method, path, mediaType, body});
    }

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) throws IOException {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        int status = responseContext.getStatus();
        long totalTime = calcularTempoTotalDaRequest(requestContext);
		MediaType mediaType = responseContext.getMediaType();

		Object entity = responseContext.getEntity();
		if(entity instanceof List) {
			int size = ((List<?>) entity).size();
			entity = "List com " + size + " elementos";
		}
		LOGGER.log(Level.INFO, "Response: {0} {1} : {2} in {3}ms - ContentType: {4} -> {5}", new Object[]{method, path, status, totalTime, mediaType, entity});
    }

	private long calcularTempoTotalDaRequest(final ContainerRequestContext requestContext) {
		long startTime = (long)requestContext.getProperty(START_TIME);
		requestContext.removeProperty(START_TIME);
    	long endTime = System.currentTimeMillis();
        return (endTime-startTime);
	}

	private String readBody(final ContainerRequestContext requestContext) {
		final MediaType mediaType = requestContext.getMediaType();
		if(APPLICATION_JSON_TYPE.equals(mediaType)) {
	        InputStream in = requestContext.getEntityStream();
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
	        try{
	            final byte[] buf = new byte[16384];
		        int len = in.read(buf);
		        while(len!=-1) {
		            out.write(buf,0,len);
		            len = in.read(buf);
		        }
		        requestContext.setEntityStream(new ByteArrayInputStream(out.toByteArray()));
	        } catch(Exception e){
	        	e.printStackTrace();
	        }
	        return new String(out.toByteArray());
		} else {
			int length = requestContext.getLength();
			return length!=-1?"<Length:"+length+">" : "null";
		}
	}
}
