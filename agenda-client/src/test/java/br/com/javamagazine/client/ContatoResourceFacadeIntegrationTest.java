package br.com.javamagazine.client;

import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.MARCH;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.javamagazine.client.exceptions.AgendaServerException;

public class ContatoResourceFacadeIntegrationTest {

	private static ContatoResourceFacade facade;
	private static Contato fernandoRubbo;
	private static Contato fernandoSantos;
	private static Contato michaelJackson;

	@BeforeClass
	public static void setup(){
		facade = new ContatoResourceFacade();

		Calendar cfr = Calendar.getInstance();
		cfr.set(1970, JANUARY, 2, 0, 0, 0);
		fernandoRubbo = new Contato("Fernando Rubbo", "9999999", "9999999", cfr.getTime());

		Calendar cfs = Calendar.getInstance();
		cfs.set(1970, MARCH, 15, 0, 0, 0);
		fernandoSantos = new Contato("Fernando Rubbo", "9999999", "9999999", cfs.getTime());

		Calendar cm = Calendar.getInstance();
		cm.set(1980, FEBRUARY, 4, 0, 0, 0);
		michaelJackson = new Contato("Michael Jackson", "88888888", "88888888", cm.getTime());

	}

	@Test
	public void testBuscarNaoExistente(){
		Contato c = facade.buscar(999999);
		assertThat(c, is(nullValue()));
	}

	@Test
	public void testCriar(){
		Contato c = null;
		try{
			c = facade.salvar(fernandoRubbo);
			assertThat(c.getId(), is(notNullValue()));
		} finally {
			facade.remover(c.getId());
		}
	}

	@Test(expected=AgendaServerException.class)
    public void testCriarComNomeIncorretoMinimo(){
        Contato clone = fernandoRubbo.clone();
        clone.setNome("f");
        facade.salvar(clone);
    }

	@Test(expected=AgendaServerException.class)
    public void testCriarComNomeIncorretoMaximo(){
        Contato clone = fernandoRubbo.clone();
        clone.setNome("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        facade.salvar(clone);
    }

    @Test(expected=AgendaServerException.class)
    public void testCriarComTelefoneIncorreto(){
        Contato clone = fernandoRubbo.clone();
        clone.setTelefone("(54)1111.1111");
        facade.salvar(clone);
    }

    @Test(expected=AgendaServerException.class)
    public void testCriarComCeluarIncorreto(){
        Contato clone = fernandoRubbo.clone();
        clone.setCelular("(54) 9999.1111");
        facade.salvar(clone);
    }

    @Test(expected=AgendaServerException.class)
    public void testCriarComDataNascimentoIncorreta(){
        Contato clone = fernandoRubbo.clone();
        Calendar data = Calendar.getInstance();
        data.add(Calendar.DATE, 1);
        clone.setDataNascimento(data.getTime());
        facade.salvar(clone);
    }

	@Test
	public void testPesquisarPorVazio(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = facade.salvar(fernandoRubbo);
			fs = facade.salvar(fernandoSantos);
			mj = facade.salvar(michaelJackson);
			List<Contato> expected = Arrays.asList(fr, fs, mj);
			Collections.sort(expected);

			List<Contato> result = facade.pesquisar("");
			Collections.sort(result);
			assertThat(result, is(expected));
		} finally {
			facade.remover(fr.getId());
			facade.remover(fs.getId());
			facade.remover(mj.getId());
		}
	}

	@Test
	public void testPesquisarPorNull(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = facade.salvar(fernandoRubbo);
			fs = facade.salvar(fernandoSantos);
			mj = facade.salvar(michaelJackson);
			List<Contato> expected = Arrays.asList(fr, fs, mj);
			Collections.sort(expected);

			List<Contato> result = facade.pesquisar(null);
			Collections.sort(result);
			assertThat(result, is(expected));
		} finally {
			facade.remover(fr.getId());
			facade.remover(fs.getId());
			facade.remover(mj.getId());
		}
	}

	@Test
	public void testPesquisarPorFernando(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = facade.salvar(fernandoRubbo);
			fs = facade.salvar(fernandoSantos);
			mj = facade.salvar(michaelJackson);
			List<Contato> expected = Arrays.asList(fr, fs);
			Collections.sort(expected);

			List<Contato> result = facade.pesquisar("Fernando");
			Collections.sort(result);
			assertThat(result, is(expected));
		} finally {
			facade.remover(fr.getId());
			facade.remover(fs.getId());
			facade.remover(mj.getId());
		}
	}

	@Test
	public void testPesquisarPorM(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = facade.salvar(fernandoRubbo);
			fs = facade.salvar(fernandoSantos);
			mj = facade.salvar(michaelJackson);

			List<Contato> list = facade.pesquisar("M");
			assertThat(list, is(Arrays.asList(mj)));
		} finally {
			facade.remover(fr.getId());
			facade.remover(fs.getId());
			facade.remover(mj.getId());
		}
	}

	@Test
	public void testBuscarTodos(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = facade.salvar(fernandoRubbo);
			fs = facade.salvar(fernandoSantos);
			mj = facade.salvar(michaelJackson);
			List<Contato> expected = Arrays.asList(fr, fs, mj);
			Collections.sort(expected);

			List<Contato> result = facade.buscarTodos();
			Collections.sort(result);
			assertThat(result, is(expected));
		} finally {
			facade.remover(fr.getId());
			facade.remover(fs.getId());
			facade.remover(mj.getId());
		}
	}

	@Test
	public void testBuscarExistente(){
		Contato c = null;
		try {
			c = facade.salvar(fernandoRubbo);
			Contato b = facade.buscar(c.getId());
			assertThat(b, is(notNullValue()));
			assertThat(b.getId(), is(c.getId()));
			assertThat(b.getNome(), is(fernandoRubbo.getNome()));
			assertThat(b.getTelefone(), is(fernandoRubbo.getTelefone()));
			assertThat(b.getCelular(), is(fernandoRubbo.getCelular()));
			assertThat(b.getDataNascimento(), is(fernandoRubbo.getDataNascimento()));
		} finally {
			facade.remover(c.getId());
		}
	}

	@Test
	public void testDeletar(){
		Contato c = facade.salvar(fernandoRubbo);
		facade.remover(c.getId());
		Contato b = facade.buscar(c.getId());
		assertThat(b, is(nullValue()));
	}

	@Test
	public void testAlterar(){
		String novoNome = "Fernando Barden Rubbo";

		Contato c = null;
		try{
			c = facade.salvar(fernandoRubbo);
			c.setNome(novoNome);
			facade.salvar(c);
			Contato b = facade.buscar(c.getId());
			assertThat(b.getNome(), is(novoNome));
		} finally {
			facade.remover(c.getId());
		}
	}

	@Test(expected=Exception.class)
	public void testUploadFotoFakeSemContato() throws Exception {
		byte[] foto = new byte[]{3,2,4,2,3,4,2,3,4,3,2};
		facade.uploadFoto(999999, foto);
	}

	@Test
	public void testUploadFotoFake() {
		byte[] foto = new byte[]{3,2,4,2,3,4,2,3,4,3,2};

		Contato c = null;
		try{
			c = facade.salvar(fernandoRubbo);
			facade.uploadFoto(c.getId(), foto);
		} finally {
			facade.remover(c.getId());
		}
	}

	@Test
	public void testDownloadFotoFake() {
		byte[] foto = new byte[]{3,2,4,2,3,4,2,3,4,3,2};

		Contato c = null;
		try{
			c = facade.salvar(fernandoRubbo);
			facade.uploadFoto(c.getId(), foto);
			byte[] d = facade.downloadFoto(c.getId());
			assertArrayEquals(foto, d);
		} finally {
			facade.remover(c.getId());
		}
	}

	@Test
	public void testUploadFoto() throws Exception {
		byte[] foto = carregarFoto("/MichaelJackson.jpg");

		Contato c = null;
		try{
			c = facade.salvar(michaelJackson);
			facade.uploadFoto(c.getId(), foto);
		} finally {
			facade.remover(c.getId());
		}
	}

	@Test
	public void testDownloadFoto() throws Exception {
		byte[] foto = carregarFoto("/MichaelJackson.jpg");

		Contato c = null;
		try{
			c = facade.salvar(michaelJackson);
			facade.uploadFoto(c.getId(), foto);
			byte[] d = facade.downloadFoto(c.getId());
			assertArrayEquals(foto, d);
		} finally {
			facade.remover(c.getId());
		}
	}

	private byte[] carregarFoto(final String fileName) throws IOException {
		InputStream in = this.getClass().getResourceAsStream(fileName);
        return inputStreamToBytes(in);
	}

	private byte[] inputStreamToBytes(final InputStream in) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] buf = new byte[16384];
        int len = in.read(buf);
        while(len!=-1) {
        	buffer.write(buf,0,len);
            len = in.read(buf);
        }
        buffer.close();

        byte[] foto = buffer.toByteArray();
		return foto;
	}

}
