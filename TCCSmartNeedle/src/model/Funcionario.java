package model;

import java.time.LocalDateTime;

public class Funcionario extends Pessoa{

	//Atributos
	private String numeroDocumento;
	private String numeroUnidade;
	
	//Construtores
	public Funcionario() {
		this(0, "", "", "", "", "", "", "", "", "", "", "", "", "", null, "", "");
	}

	public Funcionario(int id, String nome, String email, String telefone, String rg, String cpf, String sexo, 
			String login, String senha, String uf, String endereco, String cidade, String bairro, String cep, 
			LocalDateTime dataNascimento, String numeroDocumento, String numeroUnidade) {
		
		super(id, nome, email, telefone, rg, cpf, sexo, login, senha, uf, endereco, cidade, bairro, cep, dataNascimento);
		
		this.numeroDocumento = numeroDocumento;
		this.numeroUnidade = numeroUnidade;
		
	}

	//Métodos de acesso
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(String numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}
	
	//Funcionalidades
	
}
