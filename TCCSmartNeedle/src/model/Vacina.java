package model;

import java.time.LocalDateTime;

public class Vacina {
	
	//Atributos
	private int idVacina;
	private int IdDistribuidor;
	private int disponivel;
	private int solicitacoes;
	private int anos;
	private int meses;
	private String nome;
	private String descricao;
	private String fabricante;
	private String lote;
	private String indicacao;
	private String contraIndicacao;
	private String tipo;
	private LocalDateTime validade;
	
	//Construtores
	public Vacina() {
		this(0, 0, 0, 0, 0, 0, "", "", "", "", "", "", "", null);
	}

	public Vacina(int idVacina, int disponivel, int solicitacoes, int anos, int meses, int idDistribuidor, String nome, 
			String descricao, String fabricante, String lote, String indicacao,String contraIndicacao, String tipo, LocalDateTime validade) {
		this.idVacina = idVacina;
		this.disponivel = disponivel;
		this.solicitacoes = solicitacoes;
		this.anos = anos;
		this.meses = meses;
		this.IdDistribuidor = idDistribuidor;
		this.nome = nome;
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.lote = lote;
		this.indicacao = indicacao;
		this.contraIndicacao = contraIndicacao;
		this.tipo = tipo;
		this.validade = validade;
	}

	//M�todos de acesso
	public int getIdVacina() {
		return idVacina;
	}

	public void setIdVacina(int idVacina) {
		this.idVacina = idVacina;
	}

	public int getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(int disponivel) {
		this.disponivel = disponivel;
	}
	
	public int getSolicitacoes(){
		return solicitacoes;
	}
	
	public void setSolicitacoes(int solicitacoes){
		this.solicitacoes = solicitacoes;
	}
	
	public int getAnos(){
		return anos;
	}
	
	public void setAnos(int anos){
		this.anos = anos;
	}
	
	public int getMeses() {
		return meses;
	}
	
	public void setMeses(int meses) {
		this.meses = meses;
	}
	
	public int getIdDistribuidor() {
		return IdDistribuidor;
	}
	
	public void setIdDistribuidor(int IdDistribuidor) {
		this.IdDistribuidor = IdDistribuidor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(String indicacao) {
		this.indicacao = indicacao;
	}

	public String getContraIndicacao() {
		return contraIndicacao;
	}

	public void setContraIndicacao(String contraIndicacao) {
		this.contraIndicacao = contraIndicacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getValidade() {
		return validade;
	}

	public void setValidade(LocalDateTime validade) {
		this.validade = validade;
	}
	
	//Funcionalidades

}
