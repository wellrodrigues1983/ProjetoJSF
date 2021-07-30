package br.com.projetojsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ApplicationScoped
@ManagedBean(name = "pessoaBean") 
public class PessoaBean {

	private String nome;
	
	
	private List<String> nomes = new ArrayList<String>();

	public String addNome() {
		nomes.add(nome);
		return "";
	}	

	public List<String> getNomes() {
		return nomes;
	}

	public void setNomes(List<String> nomes) {
		this.nomes = nomes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	

}
