package model;

public class Distribuidor {
	
	//Atributos
	private int idDistribuidor;
	private String nome;
	private String cpnj;
	private String email;
	private String telefone;
	private String uf;
	private String endereco;
	private String cidade;
	private String bairro;
	private String cep;
	
	//Construtores
	public Distribuidor() {
		this(0, "", "", "", "", "", "", "", "", "");
	}

	public Distribuidor(int idDistribuidor, String nome, String cpnj, String email, String telefone, String uf,
			String endereco, String cidade, String bairro, String cep) {
		super();
		this.idDistribuidor = idDistribuidor;
		this.nome = nome;
		this.cpnj = cpnj;
		this.email = email;
		this.telefone = telefone;
		this.uf = uf;
		this.endereco = endereco;
		this.cidade = cidade;
		this.bairro = bairro;
		this.cep = cep;
	}

	//Métodos de acesso
	public int getIdDistribuidor() {
		return idDistribuidor;
	}

	public void setIdDistribuidor(int idDistribuidor) {
		this.idDistribuidor = idDistribuidor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpnj() {
		return cpnj;
	}

	public void setCpnj(String cpnj) {
		this.cpnj = cpnj;
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
	
	//Funcionalidades
	

}
