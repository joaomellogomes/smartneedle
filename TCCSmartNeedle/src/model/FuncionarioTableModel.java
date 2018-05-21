package model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

import org.joda.time.DateTime;

@SuppressWarnings("serial")
public class FuncionarioTableModel extends AbstractTableModel{

	//Declara��o das constantes para controle das colunas
	private final int colNome = 0;
	private final int colId = 1;
	private final int colEmail = 2;
	private final int colTelefone = 3;
	private final int colCpf = 4;
	private final int colSexo = 5;
	private final int colDataNascimento = 6;
	private final int colLogin = 7;
	private final int colNumeroDocumento = 8;

	//Atributos
	private ArrayList<Funcionario> listaFuncionario = new ArrayList<Funcionario>();

	//Construtores
	public FuncionarioTableModel(){
		listaFuncionario.clear();
	}

	public FuncionarioTableModel(ArrayList<Funcionario> listaFuncionario){
		this.listaFuncionario.clear();
		this.listaFuncionario.addAll(listaFuncionario);
	}

	//M�todos de acesso
	public ArrayList<Funcionario> getListaFuncionario(){
		return listaFuncionario;
	}

	public Funcionario getFuncionario(int indice){
		if(indice < 0 || indice >= listaFuncionario.size()){
			return null;
		}
		return listaFuncionario.get(indice);
	}

	public void setListaFuncionario(ArrayList<Funcionario> listaFuncionario){
		this.listaFuncionario = listaFuncionario;

		//Notifica o JTable que houve uma altera��o na listaFuncionario
		fireTableDataChanged();
	}

	//M�otodos obrigat�rios que vem por heran�a de AbstractTableModel
	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public int getRowCount() {
		return getListaFuncionario().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		//Recupera o objeto Funcionario para montar uma linha da tabela
		Funcionario funcionario = listaFuncionario.get(rowIndex);
		Object valorCel = null;

		//Formatador para a data
		DateTimeFormatter formatador = null;
		String data = "";

		//Verifica qual valor deve ser retornado
		if(columnIndex == colNome){
			valorCel = funcionario.getNome();
		}else if(columnIndex == colId){
			valorCel = funcionario.getId();
		}else if(columnIndex == colEmail){
			valorCel = funcionario.getEmail();
		}else if(columnIndex == colTelefone){
			valorCel = funcionario.getTelefone();
		}else if(columnIndex == colCpf){
			valorCel = funcionario.getCpf();
		}else if(columnIndex == colSexo){
			valorCel = funcionario.getSexo();
		}else if(columnIndex == colDataNascimento){
			formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			data = funcionario.getDataNascimento().format(formatador);

			valorCel = data;
		}else if(columnIndex == colLogin){
			valorCel = funcionario.getLogin();
		}else if(columnIndex == colNumeroDocumento){
			valorCel = funcionario.getNumeroDocumento();
		}

		return valorCel;
	}

	//Implementa��o de mais 2 m�todos (da interface TableModel) para melhorar a visualiza��o dos dados na tabela
	@Override
	public String getColumnName(int column){

		//Defini��o dos nomes das colunas
		if(column == colNome){
			return "Nome";
		}else if(column == colId){
			return "ID";
		}else if(column == colEmail){
			return "E-mail";
		}else if(column == colTelefone){
			return "Telefone";
		}else if(column == colCpf){
			return "CPF";
		}else if(column == colSexo){
			return "Sexo";
		}else if(column == colDataNascimento){
			return "Nascimento";
		}else if(column == colLogin){
			return "Login";
		}else if(column == colNumeroDocumento){
			return "N� documento";
		}

		return "";

	}

	@Override
	public Class<?> getColumnClass(int columnIndex){

		//Retorna a classe que representa o tipo de  dado da c�lula
		if(columnIndex == colNome){
			return String.class;
		}else if(columnIndex == colId){
			return Integer.class;
		}else if(columnIndex == colEmail){
			return String.class;
		}else if(columnIndex == colTelefone){
			return String.class;
		}else if(columnIndex == colCpf){
			return String.class;
		}else if(columnIndex == colSexo){
			return String.class;
		}else if(columnIndex == colDataNascimento){
			return DateTime.class;
		}else if(columnIndex == colLogin){
			return String.class;
		}else if(columnIndex == colNumeroDocumento){
			return String.class;
		}

		return String.class;

	}

	//M�todos de ordena��o
	public void ordenarNome(){

		Collections.sort(listaFuncionario, new Comparator<Funcionario>(){

			@Override
			public int compare(Funcionario a1, Funcionario a2){	

				return a1.getNome().compareTo(a2.getNome());
			}

		});

		fireTableDataChanged();

	}

	public void ordenarLogin(){

		Collections.sort(listaFuncionario, new Comparator<Funcionario>(){

			@Override
			public int compare(Funcionario a1, Funcionario a2){

				return a1.getLogin().compareTo(a2.getLogin());
			}

		});

		fireTableDataChanged();

	}

}
