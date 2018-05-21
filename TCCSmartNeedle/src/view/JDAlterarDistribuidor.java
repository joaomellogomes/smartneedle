package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import org.joda.time.DateTime;

import com.toedter.calendar.JDateChooser;

import model.Administrador;
import model.Distribuidor;
import model.DAO.AdministradorDAO;
import model.DAO.DistribuidorDAO;

public class JDAlterarDistribuidor extends JDialog {

	//Criação dos objetos
	AdministradorDAO admDAO = new AdministradorDAO();

	Distribuidor distribuidor =  new Distribuidor();
	DistribuidorDAO distribuidorDAO = new DistribuidorDAO();

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfIdDistribuidor;
	private JTextField jtfNome;
	private JTextField jtfEmail;
	private JTextField jtfEndereco;
	private JFormattedTextField ftfCnpj;
	private JTextField jtfCidade;
	private JTextField jtfBairro;
	private JComboBox jcbUf;
	private JFormattedTextField ftfTelefone;
	private JFormattedTextField ftfCep;
	private JButton btnOk;
	private JButton btnCancelar;
	private JButton btnEditar;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			JDAlterarDistribuidor dialog = new JDAlterarDistribuidor(null, null);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public JDAlterarDistribuidor(Distribuidor distribuidorAlterado, Administrador admLogado) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				escurecerCampos();
				desabilitarCampos();
				desabilitarBotoes();
				jtfNome.grabFocus();
			}
		});
		setModal(true);
		setTitle("Alterar Distribuidor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AWAdministrador.class.getResource("/imagens/icone.png")));
		setResizable(false);
		setBounds(100, 100, 694, 560);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel pnlDadosPessoais = new JPanel();
		pnlDadosPessoais.setBorder(new TitledBorder(null, "Dados do distribuidor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		pnlDadosPessoais.setBounds(22, 55, 645, 411);
		contentPanel.add(pnlDadosPessoais);
		pnlDadosPessoais.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 16));
		lblNome.setBounds(41, 57, 61, 24);
		pnlDadosPessoais.add(lblNome);
		
		jtfNome = new JTextField(distribuidorAlterado.getNome());
		jtfNome.setEditable(false);
		jtfNome.setColumns(10);
		jtfNome.setBounds(114, 56, 252, 28);
		pnlDadosPessoais.add(jtfNome);
		
		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setFont(new Font("Arial", Font.BOLD, 16));
		lblCnpj.setBounds(390, 57, 55, 24);
		pnlDadosPessoais.add(lblCnpj);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(41, 119, 61, 24);
		pnlDadosPessoais.add(lblEmail);
		
		jtfEmail = new JTextField();
		jtfEmail.setEditable(false);
		jtfEmail.setText(distribuidorAlterado.getEmail());
		jtfEmail.setColumns(10);
		jtfEmail.setBounds(114, 118, 252, 28);
		pnlDadosPessoais.add(jtfEmail);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Arial", Font.BOLD, 16));
		lblTelefone.setBounds(41, 185, 82, 24);
		pnlDadosPessoais.add(lblTelefone);
		
		JLabel lblUf = new JLabel("UF:");
		lblUf.setFont(new Font("Arial", Font.BOLD, 16));
		lblUf.setBounds(390, 119, 55, 24);
		pnlDadosPessoais.add(lblUf);
		
		jcbUf = new JComboBox();
		jcbUf.setEnabled(false);
		
		jcbUf.setSelectedItem(distribuidorAlterado.getUf());
		
		jcbUf.setModel(new DefaultComboBoxModel(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		jcbUf.setBounds(457, 118, 167, 30);
		pnlDadosPessoais.add(jcbUf);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setFont(new Font("Arial", Font.BOLD, 16));
		lblEndereo.setBounds(41, 243, 82, 24);
		pnlDadosPessoais.add(lblEndereo);
		
		jtfEndereco = new JTextField(distribuidorAlterado.getEndereco());
		jtfEndereco.setEditable(false);
		jtfEndereco.setColumns(10);
		jtfEndereco.setBounds(135, 242, 488, 28);
		jtfEndereco.setText(distribuidorAlterado.getEndereco());
		pnlDadosPessoais.add(jtfEndereco);
		
		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Arial", Font.BOLD, 16));
		lblCep.setBounds(390, 185, 40, 24);
		pnlDadosPessoais.add(lblCep);
		
		ftfCnpj = new JFormattedTextField();
		ftfCnpj.setEditable(false);
		MaskFormatter mascara = null;
		try {
			mascara = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: " + erro, "Erro", JOptionPane.ERROR_MESSAGE);
		}

		mascara.install(ftfCnpj);
		
		ftfCnpj.setText(distribuidorAlterado.getCpnj());
		ftfCnpj.setBounds(457, 56, 167, 28);
		pnlDadosPessoais.add(ftfCnpj);
		
		try {
			mascara = new MaskFormatter("##-######");
		}catch(ParseException erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: " + erro, "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		ftfTelefone = new JFormattedTextField();
		ftfTelefone.setEditable(false);
		
		try {
			mascara = new MaskFormatter("(##)####-####");
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: " + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}
		mascara.install(ftfTelefone);
		ftfTelefone.setText(distribuidorAlterado.getTelefone());
		ftfTelefone.setBounds(135, 184, 231, 28);
		pnlDadosPessoais.add(ftfTelefone);
		
		ftfCep = new JFormattedTextField();
		ftfCep.setEditable(false);
		
		try {
			mascara = new MaskFormatter("##-#######");
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: " + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}
		
		mascara.install(ftfCep);
		ftfCep.setText(distribuidorAlterado.getCep());
		ftfCep.setBounds(457, 184, 167, 28);
		pnlDadosPessoais.add(ftfCep);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Arial", Font.BOLD, 16));
		lblCidade.setBounds(41, 304, 82, 24);
		pnlDadosPessoais.add(lblCidade);
		
		jtfCidade = new JTextField(distribuidorAlterado.getCidade());
		jtfCidade.setEditable(false);
		jtfCidade.setColumns(10);
		jtfCidade.setBounds(135, 303, 489, 28);
		pnlDadosPessoais.add(jtfCidade);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Arial", Font.BOLD, 16));
		lblBairro.setBounds(41, 359, 82, 24);
		pnlDadosPessoais.add(lblBairro);
		
		jtfBairro = new JTextField(distribuidorAlterado.getBairro());
		jtfBairro.setEditable(false);
		jtfBairro.setColumns(10);
		jtfBairro.setBounds(135, 358, 489, 28);
		pnlDadosPessoais.add(jtfBairro);
		
		JLabel lblIdDistribuidor = new JLabel("ID Distribuidor:");
		lblIdDistribuidor.setFont(new Font("Arial", Font.BOLD, 16));
		lblIdDistribuidor.setBounds(417, 19, 116, 24);
		contentPanel.add(lblIdDistribuidor);
		
		jtfIdDistribuidor = new JTextField(String.valueOf(distribuidorAlterado.getIdDistribuidor()));
		jtfIdDistribuidor.setEditable(false);
		jtfIdDistribuidor.setFont(new Font("SansSerif", Font.PLAIN, 12));
		jtfIdDistribuidor.setColumns(10);
		jtfIdDistribuidor.setBounds(545, 15, 122, 28);
		contentPanel.add(jtfIdDistribuidor);
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(0, 498, 688, 33);
		contentPanel.add(pnlBotoes);
		pnlBotoes.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnOk = new JButton("OK");
		btnOk.setEnabled(false);
		getRootPane().setDefaultButton(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				cadastrarDistribuidor(admLogado);
				
			}
		});
		pnlBotoes.add(btnOk);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				escurecerCampos();
				desabilitarBotoes();
				desabilitarCampos();
			}
		});
		pnlBotoes.add(btnCancelar);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clarearCampos();
				habilitarBotoes();
				habilitarCampos();
			}
		});
		pnlBotoes.add(btnEditar);
		
	}
	
	public void cadastrarDistribuidor(Administrador admLogado) {
		
		Object[] botoes = {"Sim", "Cancelar"};
		String senhaDigitada = "";

		int opcao = JOptionPane.showOptionDialog(null, "Deseja continuar com este cadastro?", "Cadastrar", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoes, botoes[1]);

		if(opcao == JOptionPane.YES_OPTION){

			JPasswordField jpfSenhaConfirmacao = new JPasswordField();
			jpfSenhaConfirmacao.setBackground(new Color(255, 255, 255));
			jpfSenhaConfirmacao.setEditable(true);
			jpfSenhaConfirmacao.setBounds(412, 213, 200, 26);
			jpfSenhaConfirmacao.setBounds(0, 0, 200, 30);
			jpfSenhaConfirmacao.setVisible(true);
			jpfSenhaConfirmacao.setFont(new Font("Arial", Font.PLAIN, 11));
			jpfSenhaConfirmacao.requestFocus();

			JLabel lblSenhaa = new JLabel("Senha");
			lblSenhaa.setFont(new Font("Arial", Font.BOLD, 12));

			botoes[0] = "Cadastrar";
			botoes[1] = "Cancelar";

			opcao = JOptionPane.showOptionDialog(JDAlterarDistribuidor.this, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir o cadastro",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[0]);

			senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

			if(opcao == JOptionPane.OK_OPTION){

				final Administrador admAutenticado = admDAO.autenticar(admLogado.getLogin(), senhaDigitada);

					if(admAutenticado.getSenha().equals(senhaDigitada)){

						try{
							distribuidor.setIdDistribuidor(Integer.parseInt(jtfIdDistribuidor.getText()));
							distribuidor.setNome(jtfNome.getText());
							distribuidor.setEmail(jtfEmail.getText());
							distribuidor.setTelefone(ftfTelefone.getText());
							distribuidor.setEndereco(jtfEndereco.getText());
							distribuidor.setCidade(jtfCidade.getText());
							distribuidor.setBairro(jtfBairro.getText());
							distribuidor.setCep(ftfCep.getText());
							distribuidor.setCpnj(ftfCnpj.getText());
							distribuidor.setUf(jcbUf.getSelectedItem().toString());
							
							distribuidorDAO.alterar(distribuidor);
							dispose();

						}catch(Exception erro){
							JOptionPane.showMessageDialog(null, "Erro desconhecido" + erro, "Erro desconhecido!", 
									JOptionPane.ERROR_MESSAGE);
						}

					}else{
						JOptionPane.showMessageDialog(null, "Senha inválida! Verifique sua senha e \n"
								+ "clique em OK novamente.", "Senha incorreta", JOptionPane.ERROR_MESSAGE);
					}

			}

		}
		
	}
	
	public void habilitarBotoes(){

		btnEditar.setEnabled(false);
		btnOk.setEnabled(true);
		btnCancelar.setEnabled(true);

	}

	public void desabilitarBotoes(){

		btnOk.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnEditar.setEnabled(true);

	}

	public void habilitarCampos(){

		jtfNome.setEditable(true);
		jtfEmail.setEditable(true);
		jtfEndereco.setEditable(true);
		jtfCidade.setEditable(true);
		jtfBairro.setEditable(true);
		ftfTelefone.setEditable(true);
		ftfCnpj.setEditable(true);
		ftfCep.setEditable(true);
		jcbUf.setEnabled(true);
		jtfNome.grabFocus();

	}

	public void desabilitarCampos(){

		jtfNome.setEditable(false);
		jtfEmail.setEditable(false);
		jtfEndereco.setEditable(false);
		jtfCidade.setEditable(false);
		jtfBairro.setEditable(false);
		ftfTelefone.setEditable(false);
		ftfCnpj.setEditable(false);
		ftfCep.setEditable(false);
		jcbUf.setEnabled(false);
		jtfNome.grabFocus();

	}

	public void clarearCampos(){

		jtfNome.setBackground(new Color(255, 255, 255));
		jtfEmail.setBackground(new Color(255, 255, 255));
		jtfEndereco.setBackground(new Color(255, 255, 255));
		jtfCidade.setBackground(new Color(255, 255, 255));
		jtfBairro.setBackground(new Color(255, 255, 255));
		ftfTelefone.setBackground(new Color(255, 255, 255));
		ftfCep.setBackground(new Color(255, 255, 255));
		jcbUf.setBackground(new Color(255, 255, 255));
		ftfCnpj.setBackground(new Color(255, 255, 255));

	}

	public void escurecerCampos(){
		
		jtfNome.setBackground(new Color(214, 217, 223));
		jtfEmail.setBackground(new Color(214, 217, 223));
		jtfEndereco.setBackground(new Color(214, 217, 223));
		jtfCidade.setBackground(new Color(214, 217, 223));
		jtfBairro.setBackground(new Color(214, 217, 223));
		ftfTelefone.setBackground(new Color(214, 217, 223));
		ftfCep.setBackground(new Color(214, 217, 223));
		jcbUf.setBackground(new Color(214, 217, 223));
		ftfCnpj.setBackground(new Color(214, 217, 223));

	}
	
	public void limparCampos() {
		jtfIdDistribuidor.setText("");
		jtfNome.setText("");
		jtfEmail.setText("");
		jtfEndereco.setText("");
		ftfCnpj.setText("");
		jtfCidade.setText("");
		jtfBairro.setText("");
		jcbUf.setSelectedIndex(0);
		ftfTelefone.setText("");
		ftfCep.setText("");
	}

}
