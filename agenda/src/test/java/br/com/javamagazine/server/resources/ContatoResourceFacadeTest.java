package br.com.javamagazine.server.resources;


import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.MARCH;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;


public class ContatoResourceFacadeTest {

	private static ContatoResource res;
	private static Contato fernandoRubbo;
	private static Contato fernandoSantos;
	private static Contato michaelJackson;

	@BeforeClass
	public static void setup(){
		res = new ContatoResource();

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
		Contato c = res.buscar(999999);
		assertThat(c, is(nullValue()));
	}

	@Test
	public void testCriar(){
		Contato c = null;
		try{
			c = res.salvar(fernandoRubbo);
			assertThat(c.getId(), is(notNullValue()));
		} finally {
			res.remover(c.getId());
		}
	}

	@Test
	public void testPesquisarPorVazio(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = res.salvar(fernandoRubbo);
			fs = res.salvar(fernandoSantos);
			mj = res.salvar(michaelJackson);
			List<Contato> expected = Arrays.asList(fr, fs, mj);
			Collections.sort(expected);

			List<Contato> result = res.pesquisar("");
			Collections.sort(result);
			assertThat(result, is(expected));
		} finally {
			res.remover(fr.getId());
			res.remover(fs.getId());
			res.remover(mj.getId());
		}
	}

	@Test
	public void testPesquisarPorFernando(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = res.salvar(fernandoRubbo);
			fs = res.salvar(fernandoSantos);
			mj = res.salvar(michaelJackson);
			List<Contato> expected = Arrays.asList(fr, fs);
			Collections.sort(expected);

			List<Contato> result = res.pesquisar("Fernando");
			Collections.sort(result);
			assertThat(result, is(expected));
		} finally {
			res.remover(fr.getId());
			res.remover(fs.getId());
			res.remover(mj.getId());
		}
	}

	@Test
	public void testPesquisarPorM(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = res.salvar(fernandoRubbo);
			fs = res.salvar(fernandoSantos);
			mj = res.salvar(michaelJackson);

			List<Contato> list = res.pesquisar("M");
			assertThat(list, is(Arrays.asList(mj)));
		} finally {
			res.remover(fr.getId());
			res.remover(fs.getId());
			res.remover(mj.getId());
		}
	}

	@Test
	public void testBuscarTodos(){
		Contato fr = null;
		Contato fs = null;
		Contato mj = null;
		try{
			fr = res.salvar(fernandoRubbo);
			fs = res.salvar(fernandoSantos);
			mj = res.salvar(michaelJackson);
			List<Contato> expected = Arrays.asList(fr, fs, mj);
			Collections.sort(expected);

			List<Contato> result = res.pesquisar("");
			Collections.sort(result);
			assertThat(result, is(expected));
		} finally {
			res.remover(fr.getId());
			res.remover(fs.getId());
			res.remover(mj.getId());
		}
	}

	@Test
	public void testBuscarExistente(){
		Contato c = null;
		try {
			c = res.salvar(fernandoRubbo);
			Contato b = res.buscar(c.getId());
			assertThat(b, is(notNullValue()));
			assertThat(b.getId(), is(c.getId()));
			assertThat(b.getNome(), is(fernandoRubbo.getNome()));
			assertThat(b.getTelefone(), is(fernandoRubbo.getTelefone()));
			assertThat(b.getCelular(), is(fernandoRubbo.getCelular()));
			assertThat(b.getDataNascimento(), is(fernandoRubbo.getDataNascimento()));
		} finally {
			res.remover(c.getId());
		}
	}

	@Test
	public void testRemover(){
		Contato c = res.salvar(fernandoRubbo);
		res.remover(c.getId());
		Contato b = res.buscar(c.getId());
		assertThat(b, is(nullValue()));
	}

	@Test
	public void testAlterar() throws Exception {
		String novoNome = "Fernando Barden Rubbo";

		Contato c = null;
		try{
			c = res.salvar(fernandoRubbo);
			c.setNome(novoNome);
			res.salvar(c);
			Contato b = res.buscar(c.getId());
			assertThat(b.getNome(), is(novoNome));
		} finally {
			res.remover(c.getId());
		}
	}

	@Test(expected=Exception.class)
	public void testUploadFotoFakeSemContato() throws Exception {
		byte[] foto = new byte[]{3,2,4,2,3,4,2,3,4,3,2};
		res.uploadFoto(999999, new ByteArrayInputStream(foto));
	}

	@Test
	public void testUploadFotoFake() throws Exception {
		byte[] foto = new byte[]{3,2,4,2,3,4,2,3,4,3,2};

		Contato c = null;
		try{
			c = res.salvar(fernandoRubbo);
			res.uploadFoto(c.getId(), new ByteArrayInputStream(foto));
		} finally {
			res.remover(c.getId());
		}
	}
}
