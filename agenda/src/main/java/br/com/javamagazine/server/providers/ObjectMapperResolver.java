package br.com.javamagazine.server.providers;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.AnnotationIntrospector.Pair;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {

    final ObjectMapper combinedObjectMapper;

    public ObjectMapperResolver() {
        combinedObjectMapper = createCombinedObjectMapper();
    }

	@Override
	public ObjectMapper getContext(final Class<?> type) {
		return combinedObjectMapper;
	}

    private static ObjectMapper createCombinedObjectMapper() {
        Pair combinedIntrospector = createJaxbJacksonAnnotationIntrospector();

        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
        om.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
        om.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
        om.setDeserializationConfig(om.getDeserializationConfig().withAnnotationIntrospector(combinedIntrospector));
        om.setSerializationConfig(om.getSerializationConfig().withAnnotationIntrospector(combinedIntrospector));
        return om;
    }

	private static Pair createJaxbJacksonAnnotationIntrospector() {
		AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector();
		AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();
		return new AnnotationIntrospector.Pair(jacksonIntrospector, jaxbIntrospector);
	}
}