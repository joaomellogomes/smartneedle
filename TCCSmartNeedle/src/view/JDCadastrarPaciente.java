package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import model.Funcionario;
import model.Paciente;
import model.DAO.FuncionarioDAO;
import model.DAO.PacienteDAO;

public class JDCadastrarPaciente extends JDialog {
	
	//Criação dos obejetos
	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	
	Paciente paciente = new Paciente();
	PacienteDAO pacienteDAO = new PacienteDAO();

	private final JPanel contentPanel = new JPanel();
	private JPasswordField jpfSenha;
	private JTextField jtfNome;
	private JTextField jtfEmail;
	private JTextField jtfLogin;
	private JTextField jtfEndereco;
	private JTextField jtfCidade;
	private JTextField jtfBairro;
	private JTextField jtfNumeroProntuario;
	private JFormattedTextField ftfTelefone;
	private JFormattedTextField ftfRg;
	private JFormattedTextField ftfCpf;
	private JFormattedTextField ftfCep;
	private JDateChooser jdcDataNascimento;
	private JTextArea jtaHistorico;
	private JDateChooser jdcProximaVacina;
	private JComboBox<?> jcbSexo;
	private JComboBox<?> jcbUf;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			JDCadastrarPaciente dialog = new JDCadastrarPaciente();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public JDCadastrarPaciente(Funcionario funcionarioLogado) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDCadastrarPaciente.class.getResource("/imagens/icone.png")));
		setTitle("Cadastrar paciente");
		setBounds(100, 100, 696, 720);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel pnlBotoes = new JPanel();
			pnlBotoes.setBounds(0, 644, 680, 37);
			contentPanel.add(pnlBotoes);
			pnlBotoes.setLayout(new GridLayout(0, 3, 0, 0));
			
			JButton btnOk = new JButton("Ok");
			getRootPane().setDefaultButton(btnOk);
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
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

						opcao = JOptionPane.showOptionDialog(JDCadastrarPaciente.this, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir o cadastro",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[0]);

						senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

						if(opcao == JOptionPane.OK_OPTION){

							final Funcionario funAutenticado = funcionarioDAO.autenticar(funcionarioLogado.getLogin(), senhaDigitada);

								if(funAutenticado.getSenha().equals(senhaDigitada)){
		
									try{
										paciente.setNome(jtfNome.getText());
										paciente.setEmail(jtfEmail.getText());
										paciente.setTelefone(ftfTelefone.getText());
										paciente.setRg(ftfRg.getText());
										paciente.setCpf(ftfCpf.getText());
										paciente.setSexo(jcbSexo.getSelectedItem().toString());
										paciente.setLogin(jtfLogin.getText());
										paciente.setSenha(String.valueOf(jpfSenha.getPassword()));
										paciente.setUf(jcbUf.getSelectedItem().toString());
										paciente.setEndereco(jtfEndereco.getText());
										paciente.setCidade(jtfCidade.getText());
										paciente.setBairro(jtfBairro.getText());
										paciente.setCep(ftfCep.getText());
										
										SimpleDateFormat formatadorDataNascimento = new SimpleDateFormat("yyyy-MM-dd");
										String dataNascimento = formatadorDataNascimento.format(jdcDataNascimento.getDate());
										dataNascimento += "T00:00:00";
										paciente.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
										
										SimpleDateFormat formatadorProximaVacina = new SimpleDateFormat("yyyy-MM-dd");
										String proximaVacina = formatadorProximaVacina.format(jdcProximaVacina.getDate());
										proximaVacina += "T00:00:00";
										paciente.setProximaVacina(LocalDateTime.parse(proximaVacina, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
										paciente.setHistorico(jtaHistorico.getText());
										paciente.setNumeroProntuario(jtfNumeroProntuario.getText());
										
										pacienteDAO.cadastrar(paciente);
										dispose();
										
									}catch(DateTimeParseException erro){
										JOptionPane.showMessageDialog(null, "Verifique a data inserida!",
												"Erro ao inserir data", JOptionPane.ERROR_MESSAGE);
									}
		
								}else{
									JOptionPane.showMessageDialog(null, "Senha inválida! Verifique sua senha e \n"
											+ "clique em OK novamente.", "Senha incorreta", JOptionPane.ERROR_MESSAGE);
								}

						}

					}
					
				}
			});
			pnlBotoes.add(btnOk);
			
			JButton btnLimparCampos = new JButton("Limpar campos");
			btnLimparCampos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limparCampos();
				}
			});
			pnlBotoes.add(btnLimparCampos);
			
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			pnlBotoes.add(btnCancelar);
		}
		
		JPanel pnlPai = new JPanel();
		pnlPai.setLayout(null);
		pnlPai.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlPai.setBounds(0, 0, 690, 647);
		contentPanel.add(pnlPai);
		
		JLabel lblId = new JLabel("ID Paciente: ");
		lblId.setFont(new Font("Arial", Font.BOLD, 16));
		lblId.setBounds(423, 6, 102, 24);
		pnlPai.add(lblId);
		
		JLabel lblIdPaciente = new JLabel("Autom\u00E1tico");
		lblIdPaciente.setFont(new Font("Arial", Font.BOLD, 16));
		lblIdPaciente.setBounds(537, 6, 102, 24);
		pnlPai.add(lblIdPaciente);
		
		JPanel pnlDadosPessoais = new JPanel();
		pnlDadosPessoais.setLayout(null);
		pnlDadosPessoais.setBorder(new TitledBorder(null, "Dados pessoais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDadosPessoais.setBounds(12, 30, 645, 441);
		pnlPai.add(pnlDadosPessoais);
		
		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setFont(new Font("Arial", Font.BOLD, 14));
		lblNome.setBounds(25, 33, 55, 24);
		pnlDadosPessoais.add(lblNome);
		
		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
		lblEmail.setBounds(25, 79, 55, 24);
		pnlDadosPessoais.add(lblEmail);
		
		JLabel lblTelefone = new JLabel("Telefone: ");
		lblTelefone.setFont(new Font("Arial", Font.BOLD, 14));
		lblTelefone.setBounds(25, 125, 70, 24);
		pnlDadosPessoais.add(lblTelefone);
		
		JLabel lblRg = new JLabel("RG: ");
		lblRg.setFont(new Font("Arial", Font.BOLD, 14));
		lblRg.setBounds(355, 125, 35, 24);
		pnlDadosPessoais.add(lblRg);
		
		JLabel lblCpf = new JLabel("CPF: ");
		lblCpf.setFont(new Font("Arial", Font.BOLD, 14));
		lblCpf.setBounds(25, 169, 42, 24);
		pnlDadosPessoais.add(lblCpf);
		
		JLabel lblSexo = new JLabel("Sexo: ");
		lblSexo.setFont(new Font("Arial", Font.BOLD, 14));
		lblSexo.setBounds(355, 169, 48, 24);
		pnlDadosPessoais.add(lblSexo);
		
		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setFont(new Font("Arial", Font.BOLD, 14));
		lblLogin.setBounds(25, 215, 55, 24);
		pnlDadosPessoais.add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setFont(new Font("Arial", Font.BOLD, 14));
		lblSenha.setBounds(355, 215, 55, 24);
		pnlDadosPessoais.add(lblSenha);
		
		jpfSenha = new JPasswordField((String) null);
		jpfSenha.setBackground(UIManager.getColor("ArrowButton.background"));
		jpfSenha.setBounds(412, 213, 200, 26);
		pnlDadosPessoais.add(jpfSenha);
		
		JLabel lblUf = new JLabel("UF: ");
		lblUf.setFont(new Font("Arial", Font.BOLD, 14));
		lblUf.setBounds(25, 263, 42, 24);
		pnlDadosPessoais.add(lblUf);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o: ");
		lblEndereo.setFont(new Font("Arial", Font.BOLD, 14));
		lblEndereo.setBounds(177, 263, 81, 24);
		pnlDadosPessoais.add(lblEndereo);
		
		jtfNome = new JTextField((String) null);
		jtfNome.setColumns(10);
		jtfNome.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfNome.setBounds(92, 31, 520, 28);
		pnlDadosPessoais.add(jtfNome);
		
		jtfEmail = new JTextField((String) null);
		jtfEmail.setColumns(10);
		jtfEmail.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfEmail.setBounds(92, 77, 520, 28);
		pnlDadosPessoais.add(jtfEmail);
		
		jtfLogin = new JTextField((String) null);
		jtfLogin.setColumns(10);
		jtfLogin.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfLogin.setBounds(92, 213, 251, 28);
		pnlDadosPessoais.add(jtfLogin);
		
		jtfEndereco = new JTextField((String) null);
		jtfEndereco.setColumns(10);
		jtfEndereco.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfEndereco.setBounds(270, 261, 342, 28);
		pnlDadosPessoais.add(jtfEndereco);
		
		JLabel lblCidade = new JLabel("Cidade: ");
		lblCidade.setFont(new Font("Arial", Font.BOLD, 14));
		lblCidade.setBounds(25, 305, 57, 24);
		pnlDadosPessoais.add(lblCidade);
		
		jtfCidade = new JTextField((String) null);
		jtfCidade.setColumns(10);
		jtfCidade.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfCidade.setBounds(92, 303, 200, 26);
		pnlDadosPessoais.add(jtfCidade);
		
		JLabel lblBairro = new JLabel("Bairro: ");
		lblBairro.setFont(new Font("Arial", Font.BOLD, 14));
		lblBairro.setBounds(304, 305, 57, 24);
		pnlDadosPessoais.add(lblBairro);
		
		jtfBairro = new JTextField((String) null);
		jtfBairro.setColumns(10);
		jtfBairro.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfBairro.setBounds(373, 301, 239, 28);
		pnlDadosPessoais.add(jtfBairro);
		
		JLabel lblCep = new JLabel("CEP: ");
		lblCep.setFont(new Font("Arial", Font.BOLD, 14));
		lblCep.setBounds(25, 356, 42, 24);
		pnlDadosPessoais.add(lblCep);
		
		JLabel lblDataDeNascimento = new JLabel("Data de nascimento: ");
		lblDataDeNascimento.setFont(new Font("Arial", Font.BOLD, 14));
		lblDataDeNascimento.setBounds(304, 351, 146, 24);
		pnlDadosPessoais.add(lblDataDeNascimento);
		
		jcbSexo = new JComboBox();
		jcbSexo.setModel(new DefaultComboBoxModel(new String[] {"Feminino", "Maculino"}));
		jcbSexo.setBounds(402, 168, 210, 28);
		pnlDadosPessoais.add(jcbSexo);
		
		jcbUf = new JComboBox();
		jcbUf.setModel(new DefaultComboBoxModel(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		jcbUf.setBounds(92, 262, 73, 28);
		pnlDadosPessoais.add(jcbUf);
		
		jdcDataNascimento = new JDateChooser();
		jdcDataNascimento.setBounds(462, 352, 150, 28);
		pnlDadosPessoais.add(jdcDataNascimento);
		
		ftfTelefone = new JFormattedTextField();
		MaskFormatter mascara = null;
		try {
			mascara = new MaskFormatter("(##)####-####");
		} catch (ParseException erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: " + erro, "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		mascara.install(ftfTelefone);
		
		ftfTelefone.setBounds(102, 123, 241, 28);
		pnlDadosPessoais.add(ftfTelefone);
		
		ftfRg = new JFormattedTextField();
		try {
			mascara = new MaskFormatter("##.###.###-#");
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: " + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}
		mascara.install(ftfRg);
		
		ftfRg.setBounds(390, 123, 222, 28);
		pnlDadosPessoais.add(ftfRg);
		
		ftfCpf = new JFormattedTextField();
		try {
			mascara = new MaskFormatter("###.###.###-##");
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: " + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}
		mascara.install(ftfCpf);
		
		ftfCpf.setBounds(92, 167, 251, 28);
		pnlDadosPessoais.add(ftfCpf);
		
		ftfCep = new JFormattedTextField();
		try {
			mascara = new MaskFormatter("#####-###");
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: " + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}
		mascara.install(ftfCep);
		
		ftfCep.setBounds(92, 354, 200, 28);
		pnlDadosPessoais.add(ftfCep);
		
		JPanel pnlDadosPaciente = new JPanel();
		pnlDadosPaciente.setLayout(null);
		pnlDadosPaciente.setBorder(new TitledBorder(null, "Dados do paciente", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		pnlDadosPaciente.setBounds(12, 469, 645, 172);
		pnlPai.add(pnlDadosPaciente);
		
		JLabel lblHistorico = new JLabel("Hist\u00F3rico:");
		lblHistorico.setFont(new Font("Arial", Font.BOLD, 14));
		lblHistorico.setBounds(299, 21, 81, 24);
		pnlDadosPaciente.add(lblHistorico);
		
		jtfNumeroProntuario = new JTextField((String) null);
		jtfNumeroProntuario.setColumns(10);
		jtfNumeroProntuario.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfNumeroProntuario.setBounds(20, 46, 212, 28);
		pnlDadosPaciente.add(jtfNumeroProntuario);
		
		JLabel lblNDoPronturio = new JLabel("N\u00BA do prontu\u00E1rio: ");
		lblNDoPronturio.setFont(new Font("Arial", Font.BOLD, 14));
		lblNDoPronturio.setBounds(20, 21, 134, 24);
		pnlDadosPaciente.add(lblNDoPronturio);
		
		JLabel lblPrximaVacina = new JLabel("Pr\u00F3xima vacina:");
		lblPrximaVacina.setFont(new Font("Arial", Font.BOLD, 14));
		lblPrximaVacina.setBounds(20, 97, 118, 24);
		pnlDadosPaciente.add(lblPrximaVacina);
		
		JScrollPane jspHistorico = new JScrollPane();
		jspHistorico.setBounds(299, 43, 324, 116);
		pnlDadosPaciente.add(jspHistorico);
		
		jtaHistorico = new JTextArea((String) null);
		jtaHistorico.setWrapStyleWord(true);
		jtaHistorico.setLineWrap(true);
		jspHistorico.setViewportView(jtaHistorico);
		
		jdcProximaVacina = new JDateChooser();
		jdcProximaVacina.setBounds(20, 131, 212, 28);
		pnlDadosPaciente.add(jdcProximaVacina);
	}
	
	public void limparCampos() {
		jpfSenha.setText("");
		jtfNome.setText("");
		jtfEmail.setText("");
		jtfLogin.setText("");
		jtfEndereco.setText("");
		jtfCidade.setText("");
		jtfBairro.setText("");
		jtfNumeroProntuario.setText("");
		ftfTelefone.setText("");
		ftfRg.setText("");
		ftfCpf.setText("");
		ftfCep.setText("");
		jdcDataNascimento.setDate(null);
		jtaHistorico.setText("");
		jdcProximaVacina.setDate(null);
		jcbSexo.setSelectedIndex(0);
		jcbUf.setSelectedIndex(0);	
	}
}
