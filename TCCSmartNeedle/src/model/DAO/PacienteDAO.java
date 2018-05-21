package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import model.Paciente;

public class PacienteDAO {

	public ArrayList<Paciente> listarPacientes(String filtro, String opcaoFiltro){

		Connection con = null;
		String sql = "";
		String colunaPesquisa = "";

		if(opcaoFiltro.equals("nome") || opcaoFiltro.equals("")){
			colunaPesquisa = "pacNome";
		}else if(opcaoFiltro.equals("cpf")){
			colunaPesquisa = "pacCpf";
		}

		//ArrayList que receber� o retorno do select
		ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();

		try {

			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();

			sql = "select * from paciente where " + colunaPesquisa + " LIKE '%" + filtro + "%';";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {

				//Objeto que receber� cada Paciente, cada linha do ResultSet
				Paciente umPaciente = new Paciente();

				//Popula o objeto com os dados do ResultSet
				umPaciente.setId(rs.getInt("pacId"));
				umPaciente.setNome(rs.getString("pacNome"));
				umPaciente.setEmail(rs.getString("pacEmail"));
				umPaciente.setTelefone(rs.getString("pacTelefone"));
				umPaciente.setRg(rs.getString("pacRg"));
				umPaciente.setCpf(rs.getString("pacCpf"));
				umPaciente.setSexo(rs.getString("pacSexo"));
				umPaciente.setLogin(rs.getString("pacLogin"));
				umPaciente.setSenha(rs.getString("pacSenha"));
				umPaciente.setUf(rs.getString("pacUf"));
				umPaciente.setEndereco(rs.getString("pacEndereco"));
				umPaciente.setCidade(rs.getString("pacCidade"));
				umPaciente.setBairro(rs.getString("pacBairro"));
				umPaciente.setCep(rs.getString("pacCep"));
				
				String dataNascimento = rs.getTimestamp("pacDataNascimento").toLocalDateTime().toString();
				umPaciente.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
				umPaciente.setHistorico(rs.getString("pacHistorico"));
				umPaciente.setNumeroProntuario(rs.getString("pacNumeroProntuario"));
				
				String dataProximaVacina = rs.getTimestamp("pacProximaVacina").toLocalDateTime().toString();
				umPaciente.setProximaVacina(LocalDateTime.parse(dataProximaVacina, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
				listaPacientes.add(umPaciente);
				umPaciente = null;

			}
			stmt.close();
			rs.close();
			
		}catch(SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro capturado!:\nERRO: " + erro,
					"Erro de instrun��o SQL!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado!\nERRO: " + erro,
					"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}

		return listaPacientes;

	}

	public Paciente consultar(Paciente paciente) {

		Connection con = null;
		String sql = "";

		Paciente pacienteProcurado = new Paciente();

		try {

			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();

			sql = "select * from paciente where pacId = " + paciente.getId() + ";";

			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()) {
				
				//Popula o objeto com os dados do ResultSet
				pacienteProcurado.setId(rs.getInt("pacId"));
				pacienteProcurado.setNome(rs.getString("pacNome"));
				pacienteProcurado.setEmail(rs.getString("pacEmail"));
				pacienteProcurado.setTelefone(rs.getString("pacTelefone"));
				pacienteProcurado.setRg(rs.getString("pacRg"));
				pacienteProcurado.setCpf(rs.getString("pacCpf"));
				pacienteProcurado.setSexo(rs.getString("pacSexo"));
				pacienteProcurado.setLogin(rs.getString("pacLogin"));
				pacienteProcurado.setSenha(rs.getString("pacSenha"));
				pacienteProcurado.setUf(rs.getString("pacUf"));
				pacienteProcurado.setEndereco(rs.getString("pacEndereco"));
				pacienteProcurado.setCidade(rs.getString("pacCidade"));
				pacienteProcurado.setBairro(rs.getString("pacBairro"));
				pacienteProcurado.setCep(rs.getString("pacCep"));
				
				String dataNascimento = rs.getTimestamp("pacDataNascimento").toLocalDateTime().toString();
				pacienteProcurado.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
				pacienteProcurado.setHistorico(rs.getString("pacHistorico"));
				pacienteProcurado.setNumeroProntuario(rs.getString("pacNumeroProntuario"));
				
				String dataProximaVacina = rs.getTimestamp("pacProximaVacina").toLocalDateTime().toString();
				pacienteProcurado.setProximaVacina(LocalDateTime.parse(dataProximaVacina, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
			}
			stmt.close();
			rs.close();

		}catch(SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro capturado!:\nERRO: " + erro,
					"Erro de instrun��o SQL!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado!\nERRO: " + erro,
					"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}finally {
			try {
				con.close();
			}catch(Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro imprevisto n�o "
						+ "capturado! \nERRO: \n" + erro, "Erro n�o capturado!", JOptionPane.ERROR_MESSAGE);
			}
		}

		return pacienteProcurado;

	}

	public void alterar(Paciente paciente) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = ConnectionFactory.getConnection();

			String sql = "update paciente set pacNome = ?,"
												+ "pacEmail = ?,"
												+ "pacTelefone = ?,"
												+ "pacRg = ?,"
												+ "pacCpf = ?,"
												+ "pacSexo = ?,"
												+ "pacLogin = ?,"
												+ "pacSenha = ?,"
												+ "pacUf = ?,"
												+ "pacEndereco = ?,"
												+ "pacCidade = ?,"
												+ "pacBairro = ?,"
												+ "pacCep = ?,"
												+ "pacDataNascimento = ?,"
												+ "pacHistorico = ?,"
												+ "pacNumeroProntuario = ?,"
												+ "pacProximaVacina = ? "
												+ "where pacId = ?;";

			ps = con.prepareStatement(sql);

			//ps.setString(1, atributo);
			ps.setString(1, paciente.getNome());
			ps.setString(2, paciente.getEmail());
			ps.setString(3, paciente.getTelefone());
			ps.setString(4, paciente.getRg());
			ps.setString(5, paciente.getCpf());
			ps.setString(6, paciente.getSexo());
			ps.setString(7, paciente.getLogin());
			ps.setString(8, paciente.getSenha());
			ps.setString(9, paciente.getUf());
			ps.setString(10, paciente.getEndereco());
			ps.setString(11, paciente.getCidade());
			ps.setString(12, paciente.getBairro());
			ps.setString(13, paciente.getCep());
			ps.setString(14, paciente.getDataNascimento().toString());
			ps.setString(15, paciente.getHistorico());
			ps.setString(16, paciente.getNumeroProntuario());
			ps.setString(17, paciente.getProximaVacina().toString());
			ps.setInt(18, paciente.getId());

			ps.executeUpdate();
			ps.close();

			JOptionPane.showMessageDialog(null, "Paciente alterado com sucesso!",
					"Alterar", JOptionPane.INFORMATION_MESSAGE);

		}catch(SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro capturado!:\nERRO: " + erro,
					"Erro de instrun��o SQL!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado!\nERRO: " + erro,
					"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}finally {
			try {
				con.close();
			}catch(Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro ao encerrar conex�o com banco de dados!",
						"Erro ao fechar conex�o!", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public void excluir(Paciente paciente) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = ConnectionFactory.getConnection();

			String sql = "delete from paciente where pacId = ?;";

			ps = con.prepareStatement(sql);

			ps.setInt(1, paciente.getId());
			ps.executeUpdate();
			ps.close();

			JOptionPane.showMessageDialog(null, "Paciente exclu�do com sucesso!", "Excluir", JOptionPane.INFORMATION_MESSAGE);

		}catch(SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro capturado!:\nERRO: " + erro,
					"Erro de instrun��o SQL!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado!\nERRO: " + erro,
					"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}finally {
			try {
				con.close();
			}catch(Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao encerrar conex�o\ncom o banco de dados!",
						"Erro ao fechar conex�o", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public 	void cadastrar(Paciente paciente) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = ConnectionFactory.getConnection();

			Statement stmt = con.createStatement();
			String sqlIdValido = "select pacId from paciente;";//String que ao ser executada retorna todos os Id's 
			ResultSet rs = stmt.executeQuery(sqlIdValido);//ResultSet que recebe estes Id's

			ArrayList<Integer> idsIndisponiveis = new ArrayList<Integer>();

			while(rs.next()){//Enquanto rs.next() for igual a true adiciona os Id's	indispon�veis no ArrayList

				idsIndisponiveis.add(rs.getInt("pacId"));

			}

			Collections.sort(idsIndisponiveis);//Os valores do select j� vem em ordem, por�m o uso do .sort() s� garante 
			//que realmente est�o em ordem crescente

			idsIndisponiveis.add(idsIndisponiveis.size(), 0);//Adiciono um valor 0 ao index final do ArrayList + 1 para 
			//ser comparado posteriomente ser gerar erros referentes a index inexistente no ArrayList

			stmt.close();
			rs.close();

			int idValido = 1;//Recebe um para verificar se existe um c�digo igual a ele no ArrayList (1� Id do sistema come�a no 1)
			int posicaoArrayList = 0;//O primeiro index do ArrayList come�a no 0

			while(idsIndisponiveis.get(posicaoArrayList) == idValido){

				if(idsIndisponiveis.get(posicaoArrayList) == idValido){

					idValido++;
					posicaoArrayList++;

				}

			}
			
			String sql = "insert into paciente() values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			ps = con.prepareStatement(sql);
			
			ps.setInt(1, idValido);
			ps.setString(2, paciente.getNome());
			ps.setString(3, paciente.getEmail());
			ps.setString(4, paciente.getTelefone());
			ps.setString(5, paciente.getRg());
			ps.setString(6, paciente.getCpf());
			ps.setString(7, paciente.getSexo());
			ps.setString(8, paciente.getLogin());
			ps.setString(9, paciente.getSenha());
			ps.setString(10, paciente.getUf());
			ps.setString(11, paciente.getEndereco());
			ps.setString(12, paciente.getCidade());
			ps.setString(13, paciente.getBairro());
			ps.setString(14, paciente.getCep());
			ps.setString(15, paciente.getDataNascimento().toString());
			ps.setString(16, paciente.getHistorico());
			ps.setString(17, paciente.getNumeroProntuario());
			ps.setString(18, paciente.getProximaVacina().toString());

			ps.executeUpdate();
			ps.close();

			JOptionPane.showMessageDialog(null, "Paciente cadastrado com sucesso!",
					"Cadastrar", JOptionPane.INFORMATION_MESSAGE);

		}catch(SQLException erro) {

		}catch(Exception erro) {

		}finally {
			try {
				con.close();
			}catch(Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao encerrar conex�o\ncom o banco de dados!",
						"Erro ao fechar conex�o", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}


















