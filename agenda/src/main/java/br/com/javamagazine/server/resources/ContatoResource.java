package br.com.javamagazine.server.resources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.StreamingOutput;

@Path("contato")
public class ContatoResource {
	private static final Logger LOGGER = Logger.getLogger(ContatoResource.class.getName());

    private static Integer PROXIMO_ID = 1;
    private static final Map<Integer, Contato> CONTATOS = new ConcurrentHashMap<>();
    private static final Map<Integer, byte[]> CONTATOS_FOTOS = new ConcurrentHashMap<>();

    @GET
    @Produces({"application/json; qs=1", "application/xml ; qs=0.75"})
    public List<Contato> pesquisar(@DefaultValue("") @QueryParam("nome") final String nome){
        List<Contato> result = new ArrayList<>();
        Collection<Contato> contatos = CONTATOS.values();
        for (Contato contato : contatos) {
            if(contato.getNome().startsWith(nome)) {
                result.add(contato);
            }
        }
        return result;
    }

	@GET
	@Path("{id}")
	@Produces({"application/json; qs=1", "application/xml ; qs=0.75"})
	public Contato buscar(@PathParam("id") final Integer id){
		return CONTATOS.get(id);
	}

    @POST
    @Consumes("application/json")
    @Produces({"application/json; qs=1", "application/xml ; qs=0.75"})
    public Contato salvar(@Valid final Contato contato) {
        if(contato.getId()==null){
            Contato clone = contato.clone();

            Integer id = pegarProximoId();
            clone.setId(id);
            LOGGER.log(Level.INFO, "Criando " + clone);
            CONTATOS.put(id, clone);
            return clone;
        }
        LOGGER.log(Level.INFO, "Alterando " + contato);
        CONTATOS.put(contato.getId(), contato);
        return contato;
    }

	@DELETE
	@Path("{id}")
	public void remover(@PathParam("id") final Integer id){
		if(CONTATOS.get(id)!=null) {
			LOGGER.log(Level.INFO, "Removendo Contato com id " + id);
			CONTATOS.remove(id);
			CONTATOS_FOTOS.remove(id);
		} else {
			LOGGER.log(Level.INFO, "Contato com id " + id + " não existe e, portanto, nada foi excluído");
		}
	}

	@POST
	@Path("{id}/foto")
	@Consumes("application/octet-stream")
	public void uploadFoto(@PathParam("id") final Integer id, final InputStream in) throws Exception {
		if(!CONTATOS.containsKey(id)){
			throw new RuntimeException("Não foi possível encontrar contato com id '" + id + "' para anexar a foto!");
		}

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[16384];
        int len = in.read(buf);
        while(len!=-1) {
            out.write(buf,0,len);
            len = in.read(buf);
        }
        out.close();

        CONTATOS_FOTOS.put(id, out.toByteArray());
	}

	@GET
	@Path("{id}/foto")
	@Produces("application/octet-stream")
	public StreamingOutput downloadFoto(final @PathParam("id") Integer id) throws Exception {
		if(!CONTATOS.containsKey(id) || !CONTATOS_FOTOS.containsKey(id)){
			return null;
		}

        return new StreamingOutput() {
            @Override
            public void write(final OutputStream out) throws IOException {
                long init = System.currentTimeMillis();

                ByteArrayInputStream in = new ByteArrayInputStream(CONTATOS_FOTOS.get(id));
                byte[] buf = new byte[16384];
                int len = in.read(buf);
                while(len!=-1) {
                    out.write(buf,0,len);
                    len = in.read(buf);
                }

                LOGGER.log(Level.INFO, "Tempo total de download da foto do Contato com id {0} foi de {1}ms", new Object[]{id, (System.currentTimeMillis()-init)});
            }
        };
    }

	private synchronized Integer pegarProximoId() {
		return PROXIMO_ID++;
	}
}
