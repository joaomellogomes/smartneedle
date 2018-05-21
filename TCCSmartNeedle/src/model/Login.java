package model;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Login {

	//Atributos
	private int codigo;
	private String tipo;
	private String login;
	private LocalDateTime dataLogin;
	private LocalDateTime dataLogout;
	private String tempoLogado;

	//Construtores
	public Login() {
		this(0, "", "", null, null, "");
	}

	//Métodos de acesso
	public Login(int codigo, String tipo, String login, LocalDateTime dataLogin, LocalDateTime dataLogout, String tempoLogado) {
		this.codigo = codigo;
		this.tipo = tipo;
		this.login = login;
		this.dataLogin = dataLogin;
		this.dataLogout = dataLogout;
		this.tempoLogado = tempoLogado;
	}


	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public LocalDateTime getDataLogin() {
		return dataLogin;
	}

	public void setDataLogin(LocalDateTime dataLogin) {
		this.dataLogin = dataLogin;
	}

	public LocalDateTime getDataLogout() {
		return dataLogout;
	}

	public void setDataLogout(LocalDateTime dataLogout) {
		this.dataLogout = dataLogout;
	}

	public String getTempoLogado() {
		return tempoLogado;
	}

	public void setTempoLogado(String tempoLogado) {
		this.tempoLogado = tempoLogado;
	}

	//Funcionalidades
	public String calcularTempoLogado(LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal) throws DateTimeParseException, DateTimeException{

		String tempoLogado = "";

		//Duas formas de criar um LocalDateTime parseando um String:
		//Pelos calculos de data (Classes Period e Duration), ou pelos formatadores, no caso é necessário realizar as contas entre duas datas

		//		JOptionPane.showMessageDialog(null, "Inicial: " + dataHoraInicial + "\nFinal: " + dataHoraFinal);

		Period periodoData = Period.between(dataHoraInicial.toLocalDate(), dataHoraFinal.toLocalDate());
		Duration duracaoHorario = Duration.between(dataHoraInicial, dataHoraFinal);

		long duracaoHorarioSegundosTotal = duracaoHorario.getSeconds(),
				duracaoHorarioHoras = duracaoHorarioSegundosTotal / 3600,
				duracaoHorarioMinutos = (duracaoHorarioSegundosTotal % 3600) / 60,
				duracaoHorarioSegundos = duracaoHorarioSegundosTotal - ((duracaoHorarioHoras * 3600) + (duracaoHorarioMinutos * 60));

		int ano = Integer.parseInt(String.format("%04d", periodoData.getYears())), 
				mes = Integer.parseInt(String.format("%02d", periodoData.getMonths())),
				dia = Integer.parseInt(String.format("%02d", periodoData.getDays())),
				hora = Integer.parseInt(String.format("%02d", duracaoHorarioHoras)),
				minuto = Integer.parseInt(String.format("%02d", duracaoHorarioMinutos)),
				segundo = Integer.parseInt(String.format("%02d", duracaoHorarioSegundos));
		
		if(ano + mes + dia + hora + minuto == 0 && segundo > 0) {
			tempoLogado = segundo + " seg.";
		}else if(ano + mes + dia + hora == 0 && minuto > 0) {
			tempoLogado = minuto + "min." + segundo + "seg.";
		}else if(ano + mes + dia == 0 && hora > 0) {
			if(hora > 1) {
				tempoLogado = hora + "hrs. ";
			}else if(hora <= 1) {
				tempoLogado = hora + "hr. ";
			}
			tempoLogado += minuto + "min. " + segundo + "seg."; 
		}else if(ano + mes == 0 && dia > 0) {
			if(dia > 1) {
				if(hora > 1) {
					tempoLogado = dia + " dias " + hora + " hrs." + minuto + "min. " + segundo + "seg.";
				}else {
					tempoLogado = dia + "dias " + hora + " hr" + minuto + "min. " + segundo + "seg.";
				}

			}else if(dia == 1) {
				if(hora > 1) {
					tempoLogado = dia + " dia " + hora + " hrs." + minuto + "min. " + segundo + "seg.";
				}else {
					tempoLogado = dia + "dia " + hora + " hr" + minuto + "min. " + segundo + "seg.";
				}
			}
		}else if(ano == 0 && mes > 0) {
			if(mes > 1) {
				if(dia > 1) {
					tempoLogado = mes + "meses " + dia + "dias"; 
				}else if(dia == 1) {
					tempoLogado = mes + "meses " + dia + "dia";
				}
			}else if(mes == 1) {
				if(dia > 1) {
					tempoLogado = mes + "mes " + dia + "dias"; 
				}else if(dia == 1) {
					tempoLogado = mes + "mes " + dia + "dia";
				}
			}
		}else if(ano > 0) {
			if(ano > 1) {
				if(mes > 1) {
					tempoLogado = ano + "anos " + mes + "meses";
				}else if(mes == 1) {
					tempoLogado = ano + "anos " + mes + "mes";
				}
			}else if(ano == 1) {
				if(mes > 1) {
					tempoLogado = ano + "ano " + mes + "meses";
				}else if(mes == 1) {
					tempoLogado = ano + "ano " + mes + "mes";
				}
			}
		}

		return tempoLogado;

	}

	public LocalDateTime calcularDataHoraAtual() {

		LocalDateTime dataHoraAtual = LocalDateTime.now();

		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String data = dataHoraAtual.format(formatador);

		formatador = DateTimeFormatter.ofPattern("HH:mm:ss");
		String hora = dataHoraAtual.format(formatador);

		LocalDateTime dataHoraCalculada = LocalDateTime.parse(data + "T" + hora);

		return dataHoraCalculada;

	}

	public LocalDateTime formatarLocalDateTime(LocalDateTime dataHoraCalculo) {

		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String data = dataHoraCalculo.format(formatador);

		formatador = DateTimeFormatter.ofPattern("HH:mm:ss");
		String hora = dataHoraCalculo.format(formatador);

		LocalDateTime dataHoraFormatada = LocalDateTime.parse(data + "T" + hora);

		return dataHoraFormatada;

	}

}










