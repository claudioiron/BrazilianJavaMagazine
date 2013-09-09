package br.com.javamagazine.client;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;

import br.com.javamagazine.client.exceptions.AgendaServerException;
import br.com.javamagazine.client.providers.ObjectMapperProvider;

public class ContatoResourceFacade {
	private final GenericType<List<Contato>> LIST_CONTATO_TYPE = new GenericType<List<Contato>>() {};
    private final Client client;
    private final WebTarget target;

    public ContatoResourceFacade() {
    	this("localhost", 8080);
    }

    public ContatoResourceFacade(final String host, final int port) {
        client = ClientBuilder.newClient().register(new JacksonFeature()).register(new ObjectMapperProvider());
        target = client.target("http://" + host + ":" + port + "/agenda/api/contato");
    }

	public List<Contato> buscarTodos(){
	    return pesquisar(null);
	}

	public List<Contato> pesquisar(final String nome){
        return target.queryParam("nome", nome)
                .request(APPLICATION_JSON).get(LIST_CONTATO_TYPE);
    }

	public Contato buscar(final Integer id){
		return target.path("{id}").resolveTemplate("id", id)
			.request(APPLICATION_JSON)
			.get(Contato.class);
	}

	public Contato salvar(final Contato contato){
		Response resp = target.request(APPLICATION_JSON)
				.post(Entity.json(contato));
		throwExceptionIFError(resp);
        return resp.readEntity(Contato.class);
	}

	public void remover(final Integer id){
	    Response resp = target.path("{id}").resolveTemplate("id", id)
			.request().delete();
	    throwExceptionIFError(resp);
	}

	public void uploadFoto(final Integer id, final byte[] foto) {
		Response resp = target.path("{id}/foto").resolveTemplate("id", id)
			.request()
			.post(Entity.entity(foto, APPLICATION_OCTET_STREAM));
		throwExceptionIFError(resp);
	}

	public byte[] downloadFoto(final Integer id) {
		return target.path("{id}/foto").resolveTemplate("id", id)
			.request(APPLICATION_OCTET_STREAM)
			.get(byte[].class);
    }

    private void throwExceptionIFError(final Response resp) {
        int status = resp.getStatus();
        if(status>=400 && status<600){
             AgendaError error = resp.readEntity(AgendaError.class);
             throw new AgendaServerException(error);
        }
    }
}
