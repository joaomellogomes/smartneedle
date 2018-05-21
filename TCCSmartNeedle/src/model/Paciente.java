package model;

import java.time.LocalDateTime;

public class Paciente extends Pessoa{
	
	//Atributos
	private String historico;
	private String numeroProntuario;
	private LocalDateTime proximaVacina;
	
	//Construtores
	public Paciente() {
		this(0, "", "", "", "", "", "", "", "", "", "", "", "", "", null, "", "", null);
	}
	
	public Paciente(int id, String nome, String email, String telefone, String rg, String cpf, String sexo, String uf, 
			String login, String senha, String endereco, String cidade, String bairro, String cep, LocalDateTime dataNascimento,
			String historico, String numeroProntuario, LocalDateTime proximaVacina) {
		
		super(id, nome, email, telefone, rg, cpf, sexo, login, senha, uf, endereco, cidade, bairro, cep, dataNascimento);
		
		this.historico = historico;
		this.numeroProntuario = numeroProntuario;
		this.proximaVacina = proximaVacina;
	}

	//Métodos de acesso
	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getNumeroProntuario() {
		return numeroProntuario;
	}

	public void setNumeroProntuario(String numeroProntuario) {
		this.numeroProntuario = numeroProntuario;
	}

	public LocalDateTime getProximaVacina() {
		return proximaVacina;
	}

	public void setProximaVacina(LocalDateTime proximaVacina) {
		this.proximaVacina = proximaVacina;
	}
	
	//Funcionalidades
	
}
