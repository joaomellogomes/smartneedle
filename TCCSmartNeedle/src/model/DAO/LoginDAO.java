package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Login;

public class LoginDAO {
	
	public ArrayList<Login> listarLogins(String filtro, String opcaoFiltro){
		
//		Login login = new Login();
		Connection con = null;
		String sql = "";
		String colunaPesquisa = "";
		
		if(opcaoFiltro.equals("login") || opcaoFiltro.equals("")){
			colunaPesquisa = "logLogin";
		}else if(opcaoFiltro.equals("tipo")){
			colunaPesquisa = "logTipo";
		}
		
		//ArrayList que receber� o retorno do select
		ArrayList<Login> listaLogins = new ArrayList<Login>();
		
		try {
			
			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();
			
//			sql = "select logCodigo from login where logTempoLogado = 'Logado';";
//			ResultSet rs = stmt.executeQuery(sql);
//			
//			ArrayList<Integer> codigosLogin = new ArrayList<Integer>();
//			
//			while(rs.next()) {
//				codigosLogin.add(rs.getInt("logCodigo"));
//			}
//			
//			stmt.close();
//			rs.close();
//			
//			//Altera (Atualiza) os logins para poderem ser visualizados na tabela, de "Logado" para o tempo que o 
//			//usu�rio est� logado
//			
//			String dataHoraLogin = "";
//			for(int i = 0; i < codigosLogin.size(); i++) {
//				
//				//Consulta a data de login do c�digo da posi��o do ArrayList
//				stmt = con.createStatement();
//				sql = "select logDataLogin from login where logCodigo = '" + codigosLogin.get(i) + "';";
//				rs = stmt.executeQuery(sql);
//				
//				if(rs.next()) {
//					dataHoraLogin = rs.getTimestamp("logDataLogin").toLocalDateTime().toString();
//				}
//				stmt.close();
//				rs.close();
//				
//				sql = "update login set logTempoLogado = ? where logCodigo = ?;";
//				
//				ps = con.prepareStatement(sql);
//				ps.setString(1, login.calcularTempoLogado(LocalDateTime.parse(dataHoraLogin, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
//						LocalDateTime.now()));
//				ps.setInt(2, codigosLogin.get(i));
//				
//				ps.executeUpdate();
//				ps.close();
//			}			
			
			//Lista os logins
			stmt = con.createStatement();
			sql = "select * from login where " + colunaPesquisa + " LIKE  '%" + filtro + "%';";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				//Objeto que receber� cada Login, cada linha do ResultSet
				Login umLogin = new Login();
				
				umLogin.setCodigo(rs.getInt("logCodigo"));
				umLogin.setTipo(rs.getString("logTipo"));
				umLogin.setLogin(rs.getString("logLogin"));
				
				
				//Pegar e inserir datas no LocalDateTime
				String dataLogin = rs.getTimestamp("logDataLogin").toLocalDateTime().toString();
				String dataLogout = rs.getTimestamp("logDataLogout").toLocalDateTime().toString();
				umLogin.setDataLogin(LocalDateTime.parse(dataLogin, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				umLogin.setDataLogout(LocalDateTime.parse(dataLogout, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
				String tempoLogado = rs.getString("logTempoLogado");
				
				if(tempoLogado.equals("Logado")) {
					tempoLogado = umLogin.calcularTempoLogado(umLogin.getDataLogin(), LocalDateTime.now());
				}
				umLogin.setTempoLogado(tempoLogado);
				
				listaLogins.add(umLogin); //Adiciona um Login ao ArrayList
				umLogin = null; //Limpa o objeto para os pr�ximos registros
			}
			
			stmt.close();
			rs.close();
			
		}catch(SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro de banco de dados\n" + erro , "Erro.", JOptionPane.ERROR_MESSAGE);
		}catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: \n" + erro, "Erro desconhecido ao logar", JOptionPane.ERROR_MESSAGE);
		}finally {
			try {
				con.close();
			}catch (SQLException erro) {
				JOptionPane.showMessageDialog(null, "ERRO: \n" + erro, "Erro ao encerrar conex�o", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return listaLogins;
		
	}

	public Login inserirLogin (Login login){

		Connection con = null; 
		PreparedStatement ps = null;

		try{

			con = ConnectionFactory.getConnection();
			
			//A fun��o max() retorna o maior valor da coluna especificada (nesse caso o �ltimo c�digo da tabela login)
			Statement stmt = con.createStatement();
			String sqlCodigo  = "select max(logCodigo) from login";
			ResultSet rs = stmt.executeQuery(sqlCodigo);
			rs.next();
			//Obtendo o c�digo do novo login
			int proximoCodigo = rs.getInt(1) + 1;
			rs.close();

			//A classe PreparedStatement permite a inser��o de par�metros (?) na constru��o da string de SQL
			String sql = "insert into login values (?, ?, ?, ?, ?, ?);";

			//A defini��o dos valores que ser�o inseridos nos par�metros da String de SQL s�o indicados
			//pelo m�todo set de um objeto preparedStatement passando o �ndice (a ordem / sequencia) e o valor por par�metro)

			//Cria um objeto preparedStatement
			ps = con.prepareStatement(sql);

			//Hora de logout e tempo Inicial de tempo logado
			String dataHoraLogout = "0000-01-01 00:00:00";

			//3 - Chave prim�ria na tabela Login para poder excluir ,sem conflitos, caso o Adm queira
			//Ao exibir a JTable, pode-se saber 

			//Define os valores dos par�metros
			ps.setInt(1, proximoCodigo);
			ps.setString(2, login.getTipo());
			ps.setString(3, login.getLogin());
			ps.setString(4, login.calcularDataHoraAtual().toString());
			ps.setString(5, dataHoraLogout);
			ps.setString(6, "Logado");
			
			//Setar o codigo no objeto login
			login.setCodigo(proximoCodigo);

			//Executa a string SQL
			ps.executeUpdate();
			ps.close();			

		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro ao inserir! \nERRO: \n\n" + erro,
					"Erro de inser��o!", JOptionPane.ERROR_MESSAGE);
		}finally{
			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro desconhecido capturado! \nERRO: " + erro,
						"Erro ao fechar conex�o!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return login;

	}

	public void atualizarLogin(Login usuarioLogado, String opcaoAlterar){//deslogar

		String sql = "";
		
		Connection con = null;
		PreparedStatement ps = null;
		
		Login loginAtualizado = new Login();
		
		try {
			
			con = ConnectionFactory.getConnection();
			
			//Monta a String sql de execu��o conforme a op��o de altera��o na tabela solicitada
			if(opcaoAlterar.equals("tempoLogado")) {
				
				sql = "update login set logTempoLogado = ? where logCodigo = ?";
				
				//Cria��o do objeto PreparedStatement
				ps = con.prepareStatement(sql);
				
				loginAtualizado = consultar(usuarioLogado);
				
				String tempoLogado = loginAtualizado.calcularTempoLogado(loginAtualizado.getDataLogin(),
						LocalDateTime.now());
				
				ps.setString(1, tempoLogado);
				ps.setInt(2, usuarioLogado.getCodigo());
				
			}else if(opcaoAlterar.equals("dataLogoutTempoLogado")) {
				
				sql = "update login set logDataLogout = ?, logTempoLogado = ? where logCodigo = ?";

				loginAtualizado = consultar(usuarioLogado);
				
				String dataHoraLogout = String.valueOf(loginAtualizado.calcularDataHoraAtual());
				String tempoLogado = loginAtualizado.calcularTempoLogado(loginAtualizado.getDataLogin(), LocalDateTime.now());
				
				//Cria��o do objeto PreparedStatement
				ps = con.prepareStatement(sql);
				
				ps.setString(1, dataHoraLogout);
				ps.setString(2, tempoLogado);
				ps.setInt(3, usuarioLogado.getCodigo());
				
			}
			
			ps.executeUpdate();
			ps.close();
			
		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "ERRO: \n" + erro, "Erro ao inserir!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, "ERRO: \n" + erro, "Erro desconhecido ao deslogar!", JOptionPane.ERROR_MESSAGE);
		}finally{
			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "ERRO: \n" + erro, "Erro desconhecido ao fechar conex�o!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public Login consultar(Login login) {
		
		Connection con = null;
		String sql = "";
		
		Login loginProcurado = new Login();
		
		try {
			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();
			
			sql = "select * from login where logCodigo = '" + login.getCodigo() + "';";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				loginProcurado.setCodigo(rs.getInt("logCodigo"));
				loginProcurado.setTipo(rs.getString("logTipo"));
				loginProcurado.setLogin(rs.getString("logLogin"));
				
				//Pegar datas
				String dataLogin = rs.getTimestamp("logDataLogin").toLocalDateTime().toString(),
						dataLogout = rs.getTimestamp("logDataLogout").toLocalDateTime().toString();
				
				loginProcurado.setDataLogin(LocalDateTime.parse(dataLogin,DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				loginProcurado.setDataLogout(LocalDateTime.parse(dataLogout, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//				loginProcurado.setTempoLogado(LocalDateTime.parse(null));
				
			}
			rs.close();
			
		}catch(SQLException erro) {
			JOptionPane.showMessageDialog(null, "ERRO: \n" + erro, "Erro de instrun��o de banco de dados!", JOptionPane.ERROR_MESSAGE);
		}catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: \n" + erro, "Erro desconhecido capturado!", JOptionPane.ERROR_MESSAGE);
		}finally {
			try {
				con.close();
			}catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro ao encerrar conex�o com banco de dados! \nERRO: " + erro,
						"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return loginProcurado;
		
	}

}
