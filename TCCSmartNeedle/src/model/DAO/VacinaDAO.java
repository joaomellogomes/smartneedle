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

import model.Vacina;

public class VacinaDAO {

	public ArrayList<String> listarNomesVacinas(){
		
		Connection con = null;
		String sql = null;
		
		ArrayList<String> listaVacinas = new ArrayList<String>();
		
		try{
			
			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();
			
			sql = "select * from vacina";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next() == true){
				
				Vacina vacina = new Vacina();
				
				vacina.setNome(rs.getString("vacNome"));
				
				listaVacinas.add(vacina.getNome());
				vacina = null;
				
			}
			stmt.close();
			rs.close();
			
			Collections.sort(listaVacinas);
			
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
		
		return listaVacinas;
		
	}
	
	public Vacina consultar(String nomeVacina){
		
		Connection con = null;
		String sql = "";
		
		//Objeto de vacina com todos dados de uma vacina que ser� retornado
		Vacina vacina = new Vacina();
		
		try{
			
			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();
			
			sql = "select * from vacina where vacNome = '" + nomeVacina + "';";
			ResultSet rs = stmt.executeQuery(sql);
			
			
			while(rs.next()){
				
				vacina.setIdVacina(rs.getInt("vacId"));
				vacina.setIdDistribuidor(rs.getInt("disId"));
				vacina.setFabricante(rs.getString("vacFabricante"));
				vacina.setLote(rs.getString("vacLote"));
				vacina.setDisponivel(rs.getInt("vacDisponivel"));
				vacina.setSolicitacoes(rs.getInt("vacSolicitacoes"));
				
				String dataValidade = rs.getTimestamp("vacValidade").toLocalDateTime().toString();
				vacina.setValidade(LocalDateTime.parse(dataValidade, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
				vacina.setAnos(rs.getInt(("vacAnos")));
				vacina.setMeses(rs.getInt("vacMeses"));
				vacina.setNome(rs.getString("vacNome"));
				vacina.setDescricao(rs.getString("vacDescricao"));
				vacina.setIndicacao(rs.getString("vacIndicacao"));
				vacina.setContraIndicacao(rs.getString("vacContraIndicacao"));
				vacina.setTipo(rs.getString("vacTipo"));
				
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
		
		return vacina;
		
		
	}
	
	public void inserir(Vacina vacina){
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try{
			
			con = ConnectionFactory.getConnection();

			Statement stmt = con.createStatement();
			String sqlIdValido = "select vacId from vacina;";//String que ao ser executada retorna todos os Id's 
			ResultSet rs = stmt.executeQuery(sqlIdValido);//ResultSet que recebe estes Id's

			ArrayList<Integer> idsIndisponiveis = new ArrayList<Integer>();

			while(rs.next()){//Enquanto rs.next() for igual a true adiciona os Id's	indispon�veis no ArrayList

				idsIndisponiveis.add(rs.getInt("vacId"));

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
			
			String sql = "insert into vacina() values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, idValido);
			ps.setString(2, vacina.getFabricante());
			ps.setString(3, vacina.getLote());
			ps.setInt(4, vacina.getDisponivel());
			ps.setInt(5, vacina.getSolicitacoes());
			ps.setString(6, vacina.getValidade().toString());
			ps.setInt(7, vacina.getAnos());
			ps.setInt(8, vacina.getMeses());
			ps.setString(9, vacina.getIndicacao());
			ps.setString(10, vacina.getContraIndicacao());
			ps.setString(11, vacina.getTipo());
			ps.setString(12, vacina.getNome());
			ps.setString(13, vacina.getDescricao());
			ps.setInt(14, vacina.getIdDistribuidor());
			
			ps.executeUpdate();
			ps.close();
			
			JOptionPane.showMessageDialog(null, "Vacina cadastrada com sucesso!", "Cadastrar",
					JOptionPane.INFORMATION_MESSAGE);
			
		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro de banco de dados ao cadastrar\no nova vacina!\n" + erro,
					"Erro ao cadastrar!", JOptionPane.ERROR_MESSAGE);
		}catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado ao cadastar nova vacina!",
					"Erro ao cadastrar!", JOptionPane.ERROR_MESSAGE);
		}finally {

			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao encerrar conex�o\ncom o banco de dados!",
						"Erro ao fechar conex�o", JOptionPane.ERROR_MESSAGE);
			}

		}
		
	}
	
	public void excluir(Vacina vacina){
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try{
			
			con = ConnectionFactory.getConnection();
			
			String sql = "delete from vacina where vacId = ?";
			
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, vacina.getIdVacina());
			ps.executeUpdate();
			ps.close();
			JOptionPane.showMessageDialog(null, "Vacina exclu�da com sucesso!", "Excluir", JOptionPane.INFORMATION_MESSAGE);
			
		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro de banco de dados ao excluir!",
					"Erro ao excluir", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado ao excluir! \n" + erro, "Erro ao excluir", JOptionPane.ERROR_MESSAGE);
		}finally {

			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao encerrar conex�o\ncom o banco de dados!",
						"Erro ao fechar conex�o", JOptionPane.ERROR_MESSAGE);
			}

		}
		
	}
	
	public void alterar(Vacina vacina){
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try{
			
			con = ConnectionFactory.getConnection();
			
			String sql = "update vacina set vacFabricante = ?,"
					+ "vacLote = ?,"
					+ "vacDisponivel = ?,"
					+ "vacSolicitacoes = ?,"
					+ "vacValidade = ?,"
					+ "vacAnos = ?,"
					+ "vacMeses = ?,"
					+ "vacIndicacao = ?,"
					+ "vacContraIndicacao = ?,"
					+ "vacTipo = ?,"
					+ "vacNome = ?,"
					+ "vacDescricao = ?,"
					+ "disId = ? "
					+ "where vacId = ?;";	
			
			ps = con.prepareStatement(sql);
			
			//atribuir
			ps.setString(1, vacina.getFabricante());
			ps.setString(2, vacina.getLote());
			ps.setInt(3, vacina.getDisponivel());
			ps.setInt(4, vacina.getSolicitacoes());
			ps.setString(5, vacina.getValidade().toString());
			ps.setInt(6, vacina.getAnos());
			ps.setInt(7, vacina.getMeses());
			ps.setString(8, vacina.getIndicacao());
			ps.setString(9, vacina.getContraIndicacao());
			ps.setString(10, vacina.getTipo());
			ps.setString(11, vacina.getNome());
			ps.setString(12, vacina.getDescricao());
			ps.setInt(13, vacina.getIdDistribuidor());
			ps.setInt(14, vacina.getIdVacina());
			
			ps.executeUpdate();
			ps.close();
			
			JOptionPane.showMessageDialog(null, "Vacina altearada com sucesso!", "Alterar", JOptionPane.INFORMATION_MESSAGE);
			
		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro de banco de dados!\n" + erro,
					"Erro ao alterar!", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado! \n" + erro,
					"Erro desconhecido ao alterar!", JOptionPane.ERROR_MESSAGE);
		}finally {
			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro ao encerrar conex�o com banco de dados!",
						"Erro ao fechar conex�o!", JOptionPane.ERROR_MESSAGE);
			}    
		}
		
	}

}


















