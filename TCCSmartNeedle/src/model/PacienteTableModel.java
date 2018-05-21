package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class PacienteTableModel extends AbstractTableModel{

	//Delara��o das constantes para controle das colunas
	private final int colNome = 0;
	private final int colId = 1;
	private final int colEmail = 2;
	private final int colTelefone = 3;
	private final int colCpf = 4;
	private final int colSexo = 5;
	private final int colDataNascimento = 6;
	private final int colNumeroProntuario = 7;
	private final int colProximaVacina = 8;

	//Atributos
	private ArrayList<Paciente> listaPaciente = new ArrayList<Paciente>();

	//Construtores
	public PacienteTableModel() {
		listaPaciente.clear();
	}

	public PacienteTableModel(ArrayList<Paciente> listaPaciente) {
		this.listaPaciente.clear();
		this.listaPaciente.addAll(listaPaciente);
	}

	//M�todos de acesso
	public ArrayList<Paciente> getListaPaciente(){
		return listaPaciente;
	}

	public void setListaPaciente(ArrayList<Paciente> listaPaciente) {
		this.listaPaciente = listaPaciente;
		fireTableDataChanged();
	}

	public Paciente getPaciente(int indice) {
		if(indice< 0 || indice >= listaPaciente.size()){
			return null;
		}
		return listaPaciente.get(indice);
	}

	@Override
	public int getRowCount() {
		return this.getListaPaciente().size();
	}

	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		//Recupera o objeto Paciente para montar uma linha da tabela
		Paciente paciente = listaPaciente.get(rowIndex);
		Object valorCel = null;

		//Formatador para a data
		DateTimeFormatter formatador = null;
		String data = "";

		//Verifica qual o valor deve ser retornado
		if(columnIndex == colNome) {
			valorCel = paciente.getNome();
		}else if(columnIndex == colId) {
			valorCel = paciente.getId();
		}else if(columnIndex == colEmail) {
			valorCel = paciente.getEmail();
		}else if(columnIndex == colTelefone) {
			valorCel = paciente.getTelefone();
		}else if(columnIndex == colCpf) {
			valorCel = paciente.getCpf();
		}else if(columnIndex == colSexo) {
			valorCel = paciente.getSexo();
		}else if(columnIndex == colDataNascimento) {
			formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			data = paciente.getDataNascimento().format(formatador);
			
			valorCel = data;
		}else if(columnIndex == colNumeroProntuario) {
			valorCel = paciente.getNumeroProntuario();
		}else if(columnIndex == colProximaVacina) {
			
			formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			data = paciente.getProximaVacina().format(formatador);
			
			valorCel = data;
		}

		return valorCel;
	}
	
	public String getColumnName(int column) {
		
		if(column == colNome) {
			return "Nome";
		}else if(column == colId) {
			return "ID";
		}else if(column == colEmail) {
			return "E-mail";
		}else if(column == colTelefone) {
			return "Telefone";
		}else if(column == colCpf) {
			return "CPF";
		}else if(column == colSexo) {
			return "Sexo";
		}else if(column == colDataNascimento) {
			return "Nascimento";
		}else if(column == colNumeroProntuario) {
			return "N� Prontu�rio";
		}else if(column == colProximaVacina) {
			return "Pr�xima vacina";
		}
		
		return "";
	}

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
			return LocalDateTime.class;
		}else if(columnIndex == colNumeroProntuario){
			return String.class;
		}else if(columnIndex == colProximaVacina){
			return LocalDateTime.class;
		}

		return String.class;

	}
	
	//M�todos de ordena��o
	public void ordenarNome(){

		Collections.sort(listaPaciente, new Comparator<Paciente>(){

			@Override
			public int compare(Paciente a1, Paciente a2){

				return a1.getNome().compareTo(a2.getNome());
			}

		});

		fireTableDataChanged();

	}

	public void ordenarLogin(){
		
		Collections.sort(listaPaciente, new Comparator<Paciente>(){

			@Override
			public int compare(Paciente a1, Paciente a2){

				return a1.getLogin().compareTo(a2.getLogin());
			}

		});

		fireTableDataChanged();

	}

}











