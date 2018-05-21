package model;

import java.time.LocalDateTime;

public class Pessoa {
	
	//Atributos
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String rg;
	private String cpf;
	private String sexo;
	private String login;
	private String senha;
	private String uf;
	private String endereco;
	private String cidade;
	private String bairro;
	private String cep;
	private LocalDateTime dataNascimento;
	
	//Construtores
	public Pessoa() {
		this(0, "", "", "", "", "", "", "", "", "", "", "", "", "", null);
	}

	public Pessoa(int id, String nome, String email, String telefone, String rg, String cpf, String sexo, 
			String login, String senha, String uf, String endereco, String cidade, String bairro, String cep, LocalDateTime dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.rg = rg;
		this.cpf = cpf;
		this.sexo = sexo;
		this.login = login;
		this.senha = senha;
		this.uf = uf;
		this.endereco = endereco;
		this.cidade = cidade;
		this.bairro = bairro;
		this.cep = cep;
		this.dataNascimento = dataNascimento;
	}

	//Métodos de acesso
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public LocalDateTime getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDateTime dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	//Funcionalidades

}
