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

import model.Funcionario;

public class FuncionarioDAO {

	//Autentica o acesso do usu�rio (Pessoa) ao Sistema como um Funcionario
	public Funcionario autenticar(String login, String senha){

		Connection con = null;
		String sql = "";

		//Objeto do tipo Funcionario que recebe o resultado da 
		//busca do select e ser� retornado neste m�todo
		Funcionario funProcurado = new Funcionario();

		try{

			//Conex�o com o banco
			con = ConnectionFactory.getConnection();

			Statement stmt = con.createStatement();

			//String que ser� executada
			sql = "select * from funcionario where funLogin = '" + login + "' and funSenha COLLATE utf8_bin = '" + senha + "';";

			//				Executa a string SQL e atribui os dados resultantes em um ResultSet
			ResultSet rs = stmt.executeQuery(sql);
			//Tenta posicionar o ponteiro no pr�ximo registro do rs
			//Se conseguir (o select retornou um resultado) retorna "true", sen�o (se o rs estiver vazio) ocorrer� uma exception

			if(rs.next() == true){
				//Recupera o c�digo e o nome do registro encontrado no banco e armazena no objeto admProcurado
				//Atributos de Pessoa
				funProcurado.setId(rs.getInt("funId"));
				funProcurado.setNome(rs.getString("funNome"));
				funProcurado.setEmail(rs.getString("funEmail"));
				funProcurado.setTelefone(rs.getString("funTelefone"));
				funProcurado.setRg(rs.getString("funRg"));
				funProcurado.setCpf(rs.getString("funCpf"));
				funProcurado.setSexo(rs.getString("funSexo"));
				funProcurado.setLogin(rs.getString("funLogin"));
				funProcurado.setSenha(rs.getString("funSenha"));
				funProcurado.setUf(rs.getString("funUf"));
				funProcurado.setEndereco(rs.getString("funEndereco"));
				funProcurado.setCidade(rs.getString("funCidade"));
				funProcurado.setBairro(rs.getString("funBairro"));
				funProcurado.setCep(rs.getString("funCep"));
				String dataNascimento = rs.getTimestamp("funDataNascimento").toLocalDateTime().toString();
				funProcurado.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				//Atributos espec�ficos de Funcionario:
				funProcurado.setNumeroDocumento(rs.getString("funNumeroDocumento"));
				funProcurado.setNumeroUnidade(rs.getString("funNumeroUnidade"));
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

		return funProcurado;
	}

	public Funcionario consultar(Funcionario funcionario){

		Connection con = null;
		String sql = "";

		Funcionario funProcurado = new Funcionario();

		try{

			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();

			sql = "select * from funcionario where funId = '" + funcionario.getId() + "';";

			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next() == true){
				funProcurado.setId(rs.getInt("funId"));
				funProcurado.setNome(rs.getString("funNome"));
				funProcurado.setEmail(rs.getString("funEmail"));
				funProcurado.setTelefone(rs.getString("funTelefone"));
				funProcurado.setRg(rs.getString("funRg"));
				funProcurado.setCpf(rs.getString("funCpf"));
				funProcurado.setSexo(rs.getString("funSexo"));
				funProcurado.setLogin(rs.getString("funLogin"));
				funProcurado.setSenha(rs.getString("funSenha"));
				funProcurado.setUf(rs.getString("funUf"));
				funProcurado.setEndereco(rs.getString("funEndereco"));
				funProcurado.setCidade(rs.getString("funCidade"));
				funProcurado.setBairro(rs.getString("funBairro"));
				funProcurado.setCep(rs.getString("funCep"));
				
				String dataNascimento = rs.getTimestamp("funDataNascimento").toLocalDateTime().toString();
				funProcurado.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
				funProcurado.setNumeroDocumento(rs.getString("funNumeroDocumento"));
				funProcurado.setNumeroUnidade(rs.getString("funNumeroUnidade"));
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

		return funProcurado;

	}

	public ArrayList<Funcionario> listarFuncionarios(String filtro, String opcaoFiltro){

		Connection con = null;
		String sql = "";
		String colunaPesquisa = "";

		if(opcaoFiltro.equals("nome") || opcaoFiltro.equals("")){
			colunaPesquisa = "funNome";
		}else if(opcaoFiltro.equals("cpf")){
			colunaPesquisa = "funCpf";
		}

		//ArrayList que receber� o retorno do select
		ArrayList<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();

		try{

			con = ConnectionFactory.getConnection();
			Statement stmt = con.createStatement();
			
//			sql = "select * from funcionario where " + colunaPesquisa + " LIKE '%" + filtro + "%';";
			sql = "select * from funcionario;";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next() == true){

				//Objeto que receber� cada Funcionario, cada linha do ResultSet
				Funcionario umFuncionario = new Funcionario();

				//Popula o objeto com os dados do ResultSet
				umFuncionario.setId(rs.getInt("funId"));
				umFuncionario.setNome(rs.getString("funNome"));
				umFuncionario.setEmail(rs.getString("funEmail"));
				umFuncionario.setTelefone(rs.getString("funTelefone"));
				umFuncionario.setRg(rs.getString("funRg"));
				umFuncionario.setCpf(rs.getString("funCpf"));
				umFuncionario.setSexo(rs.getString("funSexo"));
				umFuncionario.setLogin(rs.getString("funLogin"));
				umFuncionario.setSenha(rs.getString("funSenha"));
				umFuncionario.setUf(rs.getString("funUf"));
				umFuncionario.setEndereco(rs.getString("funEndereco"));
				umFuncionario.setCep(rs.getString("funCep"));
				
				String dataNascimento = rs.getTimestamp("funDataNascimento").toLocalDateTime().toString();
				umFuncionario.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
				umFuncionario.setNumeroDocumento(rs.getString("funNumeroDocumento"));
				umFuncionario.setNumeroUnidade(rs.getString("funNumeroUnidade"));

				listaFuncionarios.add(umFuncionario);
				umFuncionario = null; //Elimina a refer�ncia do objeto par ser usado no pr�ximo registro

			}

			stmt.close();
			rs.close();

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

		//ArrayList carregado com os dados do ResultSet
		return listaFuncionarios;

	}

	public void alterar(Funcionario funcionario){

		Connection con = null;
		PreparedStatement ps = null;

		try{

			con = ConnectionFactory.getConnection();

			String sql = "update funcionario set funNome = ?,"
					+ "funEmail = ?,"
					+ "funTelefone = ?,"
					+ "funRg = ?,"
					+ "funCpf = ?,"
					+ "funSexo = ?,"
					+ "funLogin = ?,"
					+ "funSenha = ?,"
					+ "funUf = ?,"
					+ "funEndereco = ?,"
					+ "funCidade = ?,"
					+ "funBairro = ?,"
					+ "funCep = ?,"
					+ "funDataNascimento = ?,"
					+ "funNumeroDocumento = ?,"
					+ "funNumeroUnidade = ? "
					+ "where funId = ?;";

			ps = con.prepareStatement(sql);

			ps.setString(1, funcionario.getNome());
			ps.setString(2, funcionario.getEmail());
			ps.setString(3, funcionario.getTelefone());
			ps.setString(4, funcionario.getRg());
			ps.setString(5, funcionario.getCpf());
			ps.setString(6, funcionario.getSexo());
			ps.setString(7, funcionario.getLogin());
			ps.setString(8, funcionario.getSenha());
			ps.setString(9, funcionario.getUf());
			ps.setString(10, funcionario.getEndereco());
			ps.setString(11, funcionario.getCidade());
			ps.setString(12, funcionario.getBairro());
			ps.setString(13, funcionario.getCep());
			ps.setString(14, funcionario.getDataNascimento().toString());
			ps.setString(15, funcionario.getNumeroDocumento());
			ps.setString(16, funcionario.getNumeroUnidade());
			ps.setInt(17, funcionario.getId());

			ps.executeUpdate();
			ps.close();

			JOptionPane.showMessageDialog(null, "Funcion�rio alterado com sucesso!",
					"Alterar", JOptionPane.INFORMATION_MESSAGE);

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

	public void excluir(Funcionario funcionario){

		Connection con = null;
		PreparedStatement ps = null;

		try{

			con = ConnectionFactory.getConnection();

			String sql = "delete from funcionario where funId = ?;";

			ps = con.prepareStatement(sql);

			ps.setInt(1, funcionario.getId());
			ps.executeUpdate();
			ps.close();
			
			JOptionPane.showMessageDialog(null, "Funcion�rio exclu�do com sucesso!", "Excluir", JOptionPane.INFORMATION_MESSAGE);

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

	public void cadastrar(Funcionario funcionario){

		Connection con = null;
		PreparedStatement ps = null;

		try{

			con = ConnectionFactory.getConnection();

			Statement stmt = con.createStatement();
			String sqlIdValido = "select funId from funcionario;";//String que ao ser executada retorna todos os Id's 
			ResultSet rs = stmt.executeQuery(sqlIdValido);//ResultSet que recebe estes Id's

			ArrayList<Integer> idsIndisponiveis = new ArrayList<Integer>();

			while(rs.next()){//Enquanto rs.next() for igual a true adiciona os Id's	indispon�veis no ArrayList

				idsIndisponiveis.add(rs.getInt("funId"));

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

			String sql = "insert into funcionario() values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			ps = con.prepareStatement(sql);
			
			ps.setInt(1, idValido);
			ps.setString(2, funcionario.getNome());
			ps.setString(3, funcionario.getEmail());
			ps.setString(4, funcionario.getTelefone());
			ps.setString(5, funcionario.getRg());
			ps.setString(6, funcionario.getCpf());
			ps.setString(7, funcionario.getSexo());
			ps.setString(8, funcionario.getLogin());
			ps.setString(9, funcionario.getSenha());
			ps.setString(10, funcionario.getUf());
			ps.setString(11, funcionario.getEndereco());
			ps.setString(12, funcionario.getCidade());
			ps.setString(13, funcionario.getBairro());
			ps.setString(14, funcionario.getCep());
			ps.setString(15, funcionario.getDataNascimento().toString());
			ps.setString(16, funcionario.getNumeroDocumento());
			ps.setString(17, funcionario.getNumeroUnidade());

			ps.executeUpdate();
			ps.close();

			JOptionPane.showMessageDialog(null, "Funcion�rio cadastrado com sucesso!",
					"Cadastrar funcion�rios", JOptionPane.INFORMATION_MESSAGE);

		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro de banco de dados ao cadastrar\no novo funcion�rio!\n" + erro,
					"Erro ao cadastrar!", JOptionPane.ERROR_MESSAGE);
		}catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado ao cadastar novo funcion�rio!",
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

}
