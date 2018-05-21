package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class LoginTableModel extends AbstractTableModel{

	//Declara��o das constantes para controle das colunas
	private final int colCodLogin = 0;
	private final int colTipo = 1;
	private final int colLogin = 2;
	private final int colDataLogin = 3;
	private final int colDataLogout = 4;
	private final int colTempoLogado = 5;

	//Atributos
	private ArrayList<Login> listaLogin = new ArrayList<Login>();

	//Construtores
	public LoginTableModel() {
		this.listaLogin.clear();
	}

	public LoginTableModel(ArrayList<Login> listaLogin) {
		this.listaLogin.clear();
		this.listaLogin.addAll(listaLogin);
	}

	//M�todos de acesso
	public ArrayList<Login> getListaLogin(){
		return this.listaLogin;
	}

	public Login getLogin(int indice) {

		if(indice < 0 || indice >= listaLogin.size()){
			return null;
		}

		return listaLogin.get(indice);
	}

	public void setListaLogin(ArrayList<Login> listaLogin) {
		this.listaLogin = listaLogin;
		fireTableDataChanged();
	}

	//M�todos obrigat�rios que vem por heran�a de AbstractTableModel
	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return getListaLogin().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		//Recupera o objeto Login para montar uma linha da tabela
		Login login = listaLogin.get(rowIndex);
		Object valorCel = null;

		//Formatador para a data
		DateTimeFormatter formatador = null;
		String data = "", hora = "";

		//Verifica qual valor deve ser retornado
		if(columnIndex == colCodLogin) {
			valorCel = login.getCodigo();
		}else if(columnIndex == colTipo) {
			valorCel = login.getTipo();
		}else if(columnIndex == colLogin) {
			valorCel = login.getLogin();
		}else if(columnIndex == colDataLogin) {

			formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			data = login.getDataLogin().format(formatador);

			formatador = DateTimeFormatter.ofPattern("HH:mm:ss");
			hora = login.getDataLogin().format(formatador);

			valorCel = data + " �s " + hora;

		}else if(columnIndex == colDataLogout) {

			formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			data = login.getDataLogout().format(formatador);

			formatador = DateTimeFormatter.ofPattern("HH:mm:ss");
			hora = login.getDataLogout().format(formatador);

			valorCel = data + " �s " + hora;
			
			if(valorCel.toString().equals("01/01/0001 �s 00:00:00")) {
				valorCel = "Logado";				
			}

		}else if(columnIndex == colTempoLogado) {
			valorCel = login.getTempoLogado();
		}
		
		

		return valorCel;
	}

	//Implementa��o de mais 2 m�todos (da interface TableModel) para melhorar a visualiza��o dos dados na tabela
	@Override
	public String getColumnName(int column) {

		//Defini��o dos nomes das colunas
		if(column == colCodLogin) {
			return "C�digo do login";
		}else if(column == colTipo) {
			return "Tipo de login";
		}else if(column == colLogin) {
			return "Login";
		}else if(column == colDataLogin) {
			return "Data de login";
		}else if(column == colDataLogout) {
			return "Data de logout";
		}else if(column == colTempoLogado) {
			return "Tempo logado";
		}

		return "";

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int columnIndex) {

		//Retorna a classe que representa o tipo de dado da c�lula
		if(columnIndex == colCodLogin) {
			return Integer.class;
		}else if(columnIndex == colTipo) {
			return String.class;
		}else if(columnIndex == colLogin) {
			return String.class;
		}else if(columnIndex == colDataLogin) {
			return LocalDateTime.class;
		}else if(columnIndex == colDataLogout) {
			return LocalDateTime.class;
		}else if(columnIndex == colTempoLogado) {
			return String.class;
		}

		return String.class;

	}

}












