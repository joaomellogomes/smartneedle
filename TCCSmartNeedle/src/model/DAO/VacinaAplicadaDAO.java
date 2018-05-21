package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import model.VacinaAplicada;

public class VacinaAplicadaDAO {
	
	public void cadastrar(VacinaAplicada vacinaAplicada) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			
			con = ConnectionFactory.getConnection();
			
			Statement stmt = con.createStatement();
			
			String sqlIdValido = "select vacAplId from vacinaAplicada;";//String que ao ser executada retorna todos os Id's 
			ResultSet rs = stmt.executeQuery(sqlIdValido);//ResultSet que recebe estes Id's

			ArrayList<Integer> idsIndisponiveis = new ArrayList<Integer>();

			while(rs.next()){//Enquanto rs.next() for igual a true adiciona os Id's	indisponíveis no ArrayList

				idsIndisponiveis.add(rs.getInt("vacAplId"));

			}

			Collections.sort(idsIndisponiveis);//Os valores do select já vem em ordem, porém o uso do .sort() só garante 
			//que realmente estão em ordem crescente

			idsIndisponiveis.add(idsIndisponiveis.size(), 0);//Adiciono um valor 0 ao index final do ArrayList + 1 para 
			//ser comparado posteriomente ser gerar erros referentes a index inexistente no ArrayList
			
			stmt.close();
			rs.close();

			int idValido = 1;//Recebe um para verificar se existe um código igual a ele no ArrayList (1º Id do sistema começa no 1)
			int posicaoArrayList = 0;//O primeiro index do ArrayList começa no 0

			while(idsIndisponiveis.get(posicaoArrayList) == idValido){

				if(idsIndisponiveis.get(posicaoArrayList) == idValido){

					idValido++;
					posicaoArrayList++;

				}

			}
			
			String sql = "insert into vacinaAplicada() values(?, ?, ?, ?, ?, ?, ?);";
			
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, idValido);
			ps.setString(2, vacinaAplicada.getDataAplicada().toString());
			ps.setString(3, vacinaAplicada.getDose());
			ps.setInt(4, vacinaAplicada.getIdVacina());
			ps.setInt(5, vacinaAplicada.getIdDistribuidor());
			ps.setInt(6, vacinaAplicada.getIdPaciente());
			ps.setInt(7, vacinaAplicada.getIdFuncionario());
			
			ps.executeUpdate();
			ps.close();
			
			JOptionPane.showMessageDialog(null, "Os dados foram registrados com sucesso!",
					"Aplicar Vacina", JOptionPane.INFORMATION_MESSAGE);
			
		}catch(SQLException erro){
			JOptionPane.showMessageDialog(null, "Erro de banco de dados!\n" + erro,
					"Erro ao aplicar!", JOptionPane.ERROR_MESSAGE);
		}catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido capturado!",
					"Erro ao aplicar!", JOptionPane.ERROR_MESSAGE);
		}finally {

			try{
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao encerrar conexão\ncom o banco de dados!",
						"Erro ao fechar conexão", JOptionPane.ERROR_MESSAGE);
			}

		}
		
	}

}
