package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import model.Distribuidor;

public class DistribuidorDAO {

	public ArrayList<String> listarNomesDistribuidores(){

		Connection con = null;
		String sql = null;

		ArrayList<String> listaDistribuidores = new ArrayList<String>();

		try{

			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();

			sql = "select * from distribuidor";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next() == true){

				Distribuidor distribuidor = new Distribuidor();

				distribuidor.setNome(rs.getString("disNome"));

				listaDistribuidores.add(distribuidor.getNome());
				distribuidor = null;

			}
			stmt.close();
			rs.close();

			Collections.sort(listaDistribuidores);

		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro capturado!:\nERRO: " + erro,
					"Erro de instrun��o SQL!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado!\nERRO: " + erro,
					"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}finally{
			try{
				con.close();
			}catch(SQLException erro){
				JOptionPane.showMessageDialog(null, "Erro capturado ao fechar conex�o!\nERRO: " + erro,
						"Erro ao encerrar conex�o com o banco de dados", JOptionPane.ERROR_MESSAGE);
			}catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro desconhecido capturado ao fechar conex�o com banco de dados!\nERRO: " + erro,
						"Erro desconhecido de banco de dados!", JOptionPane.ERROR_MESSAGE);
			}
		}

		return listaDistribuidores;

	}

	public Distribuidor consultar(String nomeDistribuidor){

		Connection con = null;
		String sql = "";

		Distribuidor distribuidor = new Distribuidor();

		try{

			con = ConnectionFactory.getConnection();
			Statement stmt = con.prepareStatement(sql);

			sql = "select * from distribuidor where disNome = '" + nomeDistribuidor + "';";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){

				distribuidor.setIdDistribuidor(rs.getInt("disId"));
				distribuidor.setNome(rs.getString("disNome"));
				distribuidor.setCpnj(rs.getString("disCnpj"));
				distribuidor.setEmail(rs.getString("disEmail"));
				distribuidor.setTelefone(rs.getString("disTelefone"));
				distribuidor.setUf(rs.getString("disUf"));
				distribuidor.setEndereco(rs.getString("disEndereco"));
				distribuidor.setCidade(rs.getString("disCidade"));
				distribuidor.setBairro(rs.getString("disBairro"));
				distribuidor.setCep(rs.getString("disCep"));

			}
			stmt.close();
			rs.close();


		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro de instun��o SQL "
					+ "capturado! \nERRO: \n" + erro , "Erro de instrun��o SQL!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, "Erro imprevisto n�o "
					+ "capturado! \nERRO: \n" + erro, "Erro n�o capturado!", JOptionPane.ERROR_MESSAGE);
		}finally{
			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro imprevisto n�o "
						+ "capturado! \nERRO: \n" + erro, "Erro n�o capturado!", JOptionPane.ERROR_MESSAGE);
			}
		}

		return distribuidor;

	}
	
	public Distribuidor consultar(int idDistribuidor){

		Connection con = null;
		String sql = "";

		Distribuidor distribuidor = new Distribuidor();

		try{

			con = ConnectionFactory.getConnection();
			Statement stmt = con.prepareStatement(sql);

			sql = "select * from distribuidor where disId = '" + idDistribuidor + "';";
			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()){

				distribuidor.setIdDistribuidor(rs.getInt("disId"));
				distribuidor.setNome(rs.getString("disNome"));
				distribuidor.setCpnj(rs.getString("disCnpj"));
				distribuidor.setEmail(rs.getString("disEmail"));
				distribuidor.setTelefone(rs.getString("disTelefone"));
				distribuidor.setUf(rs.getString("disUf"));
				distribuidor.setEndereco(rs.getString("disEndereco"));
				distribuidor.setCidade(rs.getString("disCidade"));
				distribuidor.setBairro(rs.getString("disBairro"));
				distribuidor.setCep(rs.getString("disCep"));

			}
			stmt.close();
			rs.close();


		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro de instun��o SQL "
					+ "capturado! \nERRO: \n" + erro , "Erro de instrun��o SQL!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, "Erro imprevisto n�o "
					+ "capturado! \nERRO: \n" + erro, "Erro n�o capturado!", JOptionPane.ERROR_MESSAGE);
		}finally{
			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro imprevisto n�o "
						+ "capturado! \nERRO: \n" + erro, "Erro n�o capturado!", JOptionPane.ERROR_MESSAGE);
			}
		}

		return distribuidor;

	}

	public void cadastrar(Distribuidor distribuidor) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = ConnectionFactory.getConnection();

			Statement stmt = con.createStatement();
			String sqlIdValido = "select disId from distribuidor;";//String que ao ser executada retorna todos os Id's 
			ResultSet rs = stmt.executeQuery(sqlIdValido);//ResultSet que recebe estes Id's

			ArrayList<Integer> idsIndisponiveis = new ArrayList<Integer>();

			while(rs.next()){//Enquanto rs.next() for igual a true adiciona os Id's	indispon�veis no ArrayList

				idsIndisponiveis.add(rs.getInt("disId"));

			}

			Collections.sort(idsIndisponiveis);//Os valores do select j� vem em ordem, por�m o uso do .sort() s� garante 
			//que realmente est�o em ordem crescente

			idsIndisponiveis.add(idsIndisponiveis.size(), 0);//Adiciono um valor 0 ao index final do ArrayList + 1 para 
			//ser comparado posteriomente ser gerar erros referentes a index inexistente no ArrayList

			rs.close();

			int idValido = 1;//Recebe um para verificar se existe um c�digo igual a ele no ArrayList (1� Id do sistema come�a no 1)
			int posicaoArrayList = 0;//O primeiro index do ArrayList come�a no 0

			while(idsIndisponiveis.get(posicaoArrayList) == idValido){

				if(idsIndisponiveis.get(posicaoArrayList) == idValido){

					idValido++;
					posicaoArrayList++;

				}

			}

			String sql = "insert into distribuidor() values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			ps = con.prepareStatement(sql);

			ps.setInt(1, idValido);
			ps.setString(2, distribuidor.getNome());
			ps.setString(3, distribuidor.getCpnj());
			ps.setString(4, distribuidor.getEmail());
			ps.setString(5, distribuidor.getTelefone());
			ps.setString(6, distribuidor.getUf());
			ps.setString(7, distribuidor.getEndereco());
			ps.setString(8, distribuidor.getCidade());
			ps.setString(9, distribuidor.getBairro());
			ps.setString(10, distribuidor.getCep());

			ps.executeUpdate();
			ps.close();

			JOptionPane.showMessageDialog(null, "Distribuidor cadastrado com sucesso!", "Cadastrar distribuidor",
					JOptionPane.INFORMATION_MESSAGE);

		}catch(SQLException erro) {

			JOptionPane.showMessageDialog(null, "Erro de banco de dados: \n" + erro,
					"Erro ao cadastrar!", JOptionPane.ERROR_MESSAGE);

		}catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado: \nERRO: " + erro,
					"Erro desconhecido!", JOptionPane.INFORMATION_MESSAGE);
		}finally {

			try {
				con.close();
			}catch(Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao encerrar a conex�o!",
						"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	public void alterar(Distribuidor distribuidor) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = ConnectionFactory.getConnection();

			String sql = "update distribuidor set disNome = ?,"
					+ "disCnpj = ?,"
					+ "disEmail = ?,"
					+ "disTelefone = ?,"
					+ "disUf = ?,"
					+ "disEndereco = ?,"
					+ "disCidade = ?,"
					+ "disBairro = ?,"
					+ "disCep = ? "
					+ "where disId = ?;";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, distribuidor.getNome());
			ps.setString(2, distribuidor.getCpnj());
			ps.setString(3, distribuidor.getEmail());
			ps.setString(4, distribuidor.getTelefone());
			ps.setString(5, distribuidor.getUf());
			ps.setString(6, distribuidor.getEndereco());
			ps.setString(7, distribuidor.getCidade());
			ps.setString(8, distribuidor.getBairro());
			ps.setString(9, distribuidor.getCep());
			ps.setInt(10, distribuidor.getIdDistribuidor());
			
			ps.executeUpdate();
			ps.close();
			
			JOptionPane.showMessageDialog(null, "Distribuidor alterado com sucesso!",
					"Alterar", JOptionPane.INFORMATION_MESSAGE);

		}catch(SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido ao alterar!\nERRO: " + erro,
					"Erro ao alterar", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "", "Erro desconhecido capturado", JOptionPane.ERROR_MESSAGE);
		}finally {
			try {
				con.close();
			}catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "ERRO: " + erro, "Erro ao fechar conex�o com banco de dados!",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public void excluir(Distribuidor distribuidor) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = ConnectionFactory.getConnection();

			String sql = "delete from distribuidor where disId = ?;";

			ps = con.prepareStatement(sql);

			ps.setInt(1, distribuidor.getIdDistribuidor());
			ps.executeUpdate();
			ps.close();

			JOptionPane.showMessageDialog(null, "Distribuidor exclu�do com sucesso!",
					"Excluir", JOptionPane.INFORMATION_MESSAGE);

		}catch(SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro de banco de dados ao excluir!\nERRO: " + erro,
					"Erro ao excluir!", JOptionPane.ERROR_MESSAGE);			
		}catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado ao excluir!\nERRO: " + erro,
					"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}finally {
			try {
				con.close();
			}catch(Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro ao encerrar conex�o com o banco de dados!\nERRO: " + erro,
						"Erro de banco de dados", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}




















