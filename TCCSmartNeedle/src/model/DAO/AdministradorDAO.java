package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import model.Administrador;

public class AdministradorDAO {

	//Autentica o acesso do usu�rio (Pessoa) ao Sistema como um Administrador
	public Administrador autenticar(String login, String senha){

		Connection con = null;
		String sql = "";

		//Objeto do tipo Administrativo que recebe o resultado da 
		//busca do select e ser� retornado neste m�todo
		Administrador admProcurado = new Administrador();

		try{

			//Conex�o com o banco
			con = ConnectionFactory.getConnection();

			Statement stmt = con.createStatement();

			//String que ser� executada
			sql = "select * from administrador where admLogin = '" + login + "' and admSenha = '" + senha + "';";

			//			Executa a string SQL e atribui os dados resultantes em um ResultSet
			ResultSet rs = stmt.executeQuery(sql);
			//Tenta posicionar o ponteiro no pr�ximo registro do rs
			//Se conseguir (o select retornou um resultado) retorna "true", sen�o (se o rs estiver vazio) ocorrer� uma exception

			if(rs.next() == true){
				//Recupera o Id, nome etc do registro encontrado no banco e armazena no objeto admProcurado
				admProcurado.setId(rs.getInt(1));
				admProcurado.setNome(rs.getString(2));
				admProcurado.setEmail(rs.getString(3));
				admProcurado.setTelefone(rs.getString(4));
				admProcurado.setRg(rs.getString(5));
				admProcurado.setCpf(rs.getString(6));
				admProcurado.setSexo(rs.getString(7));
				admProcurado.setLogin(rs.getString(8));
				admProcurado.setSenha(rs.getString(9));
				admProcurado.setUf(rs.getString(10));
				admProcurado.setEndereco(rs.getString(11));
				admProcurado.setCidade(rs.getString(12));
				admProcurado.setBairro(rs.getString(13));
				admProcurado.setCep(rs.getString(14));
				String dataNascimento = rs.getTimestamp("admDataNascimento").toLocalDateTime().toString();
				admProcurado.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			}
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

		return admProcurado;
	}
	
	public void alterar(Administrador administrador, String opcaoAlterar){
		
		JOptionPane.showMessageDialog(null, administrador.getSenha());
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try{
			
			con = ConnectionFactory.getConnection();
			String sql = "";
			
			if(opcaoAlterar.equals("Senha")){
				sql = "update administrador set admSenha = ? where admId = ?;";
				
				ps = con.prepareStatement(sql);
				ps.setString(1, administrador.getSenha());
				ps.setInt(2, administrador.getId());
				ps.execute();
				ps.close();
				
				JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!", "Alterar senha", JOptionPane.INFORMATION_MESSAGE);
				
			}else if(opcaoAlterar.equals("Administrador")){
				
			}
			
		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel alterar! \nERRO: " + erro,
					"Erro ao alterar", JOptionPane.ERROR_MESSAGE);
		}catch(Exception erro){
			JOptionPane.showMessageDialog(null, "Erro desconhecido ao alterar!\nERRO: " + erro,
					"Erro desconhecido", JOptionPane.ERROR_MESSAGE);
		}finally {
			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro imprevisto n�o "
						+ "capturado! \nERRO: \n" + erro, "Erro n�o capturado!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}






















