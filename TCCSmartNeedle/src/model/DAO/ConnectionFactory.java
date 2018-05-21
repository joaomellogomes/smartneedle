package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class ConnectionFactory {
	
	//Atributos (url do banco, login e senha do MySQL
	private static String url = "jdbc:mysql://localhost:3306/Smart_Needle";
	private static String usuario = "root";
	private static String senha = "N1nteress@";
	
	//Método que realiza a conexão com o Banco de dados
	public static Connection getConnection() throws SQLException{
		
		Connection conexao = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");/* Aqui registra o driver do mysql*/
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		conexao = DriverManager.getConnection(url, usuario, senha);
		
		return conexao;
		
	}

}
