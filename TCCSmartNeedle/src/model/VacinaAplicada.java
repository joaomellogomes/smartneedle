package model;

import java.time.LocalDateTime;

public class VacinaAplicada {
	
	//Atributos
	private String dose;
	private int idVacinaAplicada;
	private int idVacina;
	private int idDistribuidor;
	private int idPaciente;
	private int idFuncionario;
	private LocalDateTime dataAplicada;
	
	//Construtores
	public VacinaAplicada() {
		this("", 0, 0, 0, 0, 0, null);
	}

	public VacinaAplicada(String dose, int idVacinaAplicada, int idVacina, int idDistribuidor, int idPaciente,
			int idFuncionario, LocalDateTime dataAplicada) {
		this.dose = dose;
		this.idVacinaAplicada = idVacinaAplicada;
		this.idVacina = idVacina;
		this.idDistribuidor = idDistribuidor;
		this.idPaciente = idPaciente;
		this.idFuncionario = idFuncionario;
		this.dataAplicada = dataAplicada;
	}

	//Métodos de acesso
	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public int getIdVacinaAplicada() {
		return idVacinaAplicada;
	}

	public void setIdVacinaAplicada(int idVacinaAplicada) {
		this.idVacinaAplicada = idVacinaAplicada;
	}

	public int getIdVacina() {
		return idVacina;
	}

	public void setIdVacina(int idVacina) {
		this.idVacina = idVacina;
	}

	public int getIdDistribuidor() {
		return idDistribuidor;
	}

	public void setIdDistribuidor(int idDistribuidor) {
		this.idDistribuidor = idDistribuidor;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public LocalDateTime getDataAplicada() {
		return dataAplicada;
	}

	public void setDataAplicada(LocalDateTime dataAplicada) {
		this.dataAplicada = dataAplicada;
	}

}
