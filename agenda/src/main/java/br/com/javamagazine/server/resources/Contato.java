package br.com.javamagazine.server.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="contato")
public class Contato implements Cloneable, Comparable<Contato> {
	private Integer id;
	@NotNull @Size(min=2, max=100, message="O nome deve possuir no mínimo {min} e no máximo {max} caracteres")
	private String nome;
	@Pattern(regexp="^([0-9]*)$", message="O telefone aceita apenas números")
	private String telefone;
	@Pattern(regexp="^([0-9]*)$", message="O celuar aceita apenas números")
	private String celular;
	@Past(message="A data de nascimento deve estar no passado")
	private Date dataNascimento;

	public Contato(){}

	public Contato(final String nome, final String telefone, final String celular,
			final Date dataNascimento) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.celular = celular;
		this.dataNascimento = dataNascimento;
	}


	public Integer getId() {
		return id;
	}
	public void setId(final Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(final String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(final String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(final String celular) {
		this.celular = celular;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(final Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id!=null?id:-1);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		Contato other = (Contato) obj;
		if (id != other.id) {
            return false;
        }
		return true;
	}

	@Override
	public String toString() {
		final DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
		return "Contato [id=" + id
				+ ", nome=" + nome
				+ ", telefone=" + telefone
				+ ", celular=" + celular
				+ ", dataNascimento=" + (dataNascimento!=null ? df.format(dataNascimento) : "null" )+ "]";
	}

	@Override
	protected Contato clone()  {
		Contato c = new Contato();
		c.id=this.id;
		c.nome=this.nome;
		c.telefone=this.telefone;
		c.celular=this.celular;
		c.dataNascimento=this.dataNascimento;
		return c;
	}

	@Override
	public int compareTo(final Contato o) {
		return this.id.compareTo(o.id);
	}
}
