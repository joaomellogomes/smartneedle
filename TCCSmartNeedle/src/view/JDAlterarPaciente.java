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
import java.util.Date;

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

import org.joda.time.DateTime;

import com.toedter.calendar.JDateChooser;

import model.Funcionario;
import model.Paciente;
import model.DAO.FuncionarioDAO;
import model.DAO.PacienteDAO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JDAlterarPaciente extends JDialog {
	
	//Criação dos obejetos
	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	
	Paciente paciente = new Paciente();
	PacienteDAO pacienteDAO = new PacienteDAO();

	private final JPanel contentPanel = new JPanel();
	private JLabel lblIdPaciente;
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
	
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnOk;
	private JButton btnLimparCampos;

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
	public JDAlterarPaciente(Paciente pacienteAlterado, Funcionario funcionarioLogado) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				escurecerCampos();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDCadastrarPaciente.class.getResource("/imagens/icone.png")));
		setTitle("Alterar paciente " + pacienteAlterado.getLogin());
		setBounds(100, 100, 696, 720);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel pnlBotoes = new JPanel();
			pnlBotoes.setBounds(0, 644, 680, 37);
			contentPanel.add(pnlBotoes);
			
			btnOk = new JButton("Ok");
			getRootPane().setDefaultButton(btnOk);
			btnOk.setEnabled(false);
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Object[] botoes = {"Sim", "Cancelar"};
					String senhaDigitada = "";

					int opcao = JOptionPane.showOptionDialog(null, "Deseja continuar com esta alteração?", "Alterar", 
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

						opcao = JOptionPane.showOptionDialog(JDAlterarPaciente.this, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir a alteração",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[0]);

						senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

						if(opcao == JOptionPane.OK_OPTION){

							final Funcionario funAutenticado = funcionarioDAO.autenticar(funcionarioLogado.getLogin(), senhaDigitada);

								if(funAutenticado.getSenha().equals(senhaDigitada)){
		
									try{
										paciente.setId(Integer.parseInt((lblIdPaciente.getText())));
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
										
										pacienteDAO.alterar(paciente);
										dispose();
										
									}catch(Exception erro){
										JOptionPane.showMessageDialog(null, "Verifique os campos inseridos!",
												"Erro ao alterar", JOptionPane.ERROR_MESSAGE);
									}
		
								}else{
									JOptionPane.showMessageDialog(null, "Senha inválida! Verifique sua senha e \n"
											+ "clique em OK novamente.", "Senha incorreta", JOptionPane.ERROR_MESSAGE);
								}

						}

					}
					
				}
			});
			pnlBotoes.setLayout(new GridLayout(0, 4, 0, 0));
			pnlBotoes.add(btnOk);
			
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setEnabled(false);
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					escurecerCampos();
					desabilitarBotoes();
					desabilitarCampos();
				}
			});
			pnlBotoes.add(btnCancelar);
			
			btnEditar = new JButton("Editar");
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clarearCampos();
					habilitarBotoes();
					habilitarCampos();
				}
			});
			pnlBotoes.add(btnEditar);
			
			btnLimparCampos = new JButton("Limpar campos");
			btnLimparCampos.setEnabled(false);
			pnlBotoes.add(btnLimparCampos);
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
		
		lblIdPaciente = new JLabel("Automático");
		lblIdPaciente.setFont(new Font("Arial", Font.BOLD, 16));
		lblIdPaciente.setBounds(537, 6, 102, 24);
		lblIdPaciente.setText(String.valueOf(pacienteAlterado.getId()));
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
		
		jpfSenha = new JPasswordField(pacienteAlterado.getSenha());
		jpfSenha.setEditable(false);
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
		
		jtfNome = new JTextField(pacienteAlterado.getNome());
		jtfNome.setEditable(false);
		jtfNome.setColumns(10);
		jtfNome.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfNome.setBounds(92, 31, 520, 28);
		pnlDadosPessoais.add(jtfNome);
		
		jtfEmail = new JTextField(pacienteAlterado.getEmail());
		jtfEmail.setEditable(false);
		jtfEmail.setColumns(10);
		jtfEmail.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfEmail.setBounds(92, 77, 520, 28);
		pnlDadosPessoais.add(jtfEmail);
		
		jtfLogin = new JTextField(pacienteAlterado.getLogin());
		jtfLogin.setEditable(false);
		jtfLogin.setColumns(10);
		jtfLogin.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfLogin.setBounds(92, 213, 251, 28);
		pnlDadosPessoais.add(jtfLogin);
		
		jtfEndereco = new JTextField(pacienteAlterado.getEndereco());
		jtfEndereco.setEditable(false);
		jtfEndereco.setColumns(10);
		jtfEndereco.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfEndereco.setBounds(270, 261, 342, 28);
		pnlDadosPessoais.add(jtfEndereco);
		
		JLabel lblCidade = new JLabel("Cidade: ");
		lblCidade.setFont(new Font("Arial", Font.BOLD, 14));
		lblCidade.setBounds(25, 305, 57, 24);
		pnlDadosPessoais.add(lblCidade);
		
		jtfCidade = new JTextField(pacienteAlterado.getCidade());
		jtfCidade.setEditable(false);
		jtfCidade.setColumns(10);
		jtfCidade.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfCidade.setBounds(92, 303, 200, 26);
		pnlDadosPessoais.add(jtfCidade);
		
		JLabel lblBairro = new JLabel("Bairro: ");
		lblBairro.setFont(new Font("Arial", Font.BOLD, 14));
		lblBairro.setBounds(304, 305, 57, 24);
		pnlDadosPessoais.add(lblBairro);
		
		jtfBairro = new JTextField(pacienteAlterado.getBairro());
		jtfBairro.setEditable(false);
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
		if(pacienteAlterado.getSexo().equals("Feminino")) jcbSexo.setSelectedIndex(0);else {
			jcbSexo.setSelectedIndex(1);
		}
		jcbSexo.setEnabled(false);
		jcbSexo.setBounds(402, 168, 210, 28);
		pnlDadosPessoais.add(jcbSexo);
		
		jcbUf = new JComboBox();
		jcbUf.setModel(new DefaultComboBoxModel(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		jcbUf.setSelectedItem(pacienteAlterado.getUf());
		jcbUf.setEnabled(false);
		jcbUf.setBounds(92, 262, 73, 28);
		pnlDadosPessoais.add(jcbUf);
		
		jdcDataNascimento = new JDateChooser(new DateTime(pacienteAlterado.getDataNascimento().toString()).toDate());
		jdcDataNascimento.setEnabled(false);
		jdcDataNascimento.setBounds(462, 352, 150, 28);
		pnlDadosPessoais.add(jdcDataNascimento);
		
		ftfTelefone = new JFormattedTextField(pacienteAlterado.getTelefone());
		ftfTelefone.setEditable(false);
		MaskFormatter mascara = null;
		try {
			mascara = new MaskFormatter("(##)####-####");
		} catch (ParseException erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: " + erro, "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		mascara.install(ftfTelefone);
		
		ftfTelefone.setBounds(102, 123, 241, 28);
		pnlDadosPessoais.add(ftfTelefone);
		
		ftfRg = new JFormattedTextField(pacienteAlterado.getRg());
		ftfRg.setEditable(false);
		try {
			mascara = new MaskFormatter("##.###.###-#");
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: " + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}
		mascara.install(ftfRg);
		
		ftfRg.setBounds(390, 123, 222, 28);
		pnlDadosPessoais.add(ftfRg);
		
		ftfCpf = new JFormattedTextField(pacienteAlterado.getCpf());
		ftfCpf.setEditable(false);
		try {
			mascara = new MaskFormatter("###.###.###-##");
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: " + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}
		mascara.install(ftfCpf);
		
		ftfCpf.setBounds(92, 167, 251, 28);
		pnlDadosPessoais.add(ftfCpf);
		
		ftfCep = new JFormattedTextField(pacienteAlterado.getCep());
		ftfCep.setEditable(false);
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
		
		jtfNumeroProntuario = new JTextField(pacienteAlterado.getNumeroProntuario());
		jtfNumeroProntuario.setEditable(false);
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
		
		jtaHistorico = new JTextArea(pacienteAlterado.getHistorico());
		jtaHistorico.setEditable(false);
		jtaHistorico.setWrapStyleWord(true);
		jtaHistorico.setLineWrap(true);
		jspHistorico.setViewportView(jtaHistorico);
		
		jdcProximaVacina = new JDateChooser(new DateTime(pacienteAlterado.getProximaVacina().toString()).toDate());
		jdcProximaVacina.setEnabled(false);
		jdcProximaVacina.setBounds(20, 131, 212, 28);
		pnlDadosPaciente.add(jdcProximaVacina);
	}
	
	public void habilitarBotoes(){

		btnEditar.setEnabled(false);
		btnOk.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnLimparCampos.setEnabled(true);

	}

	public void desabilitarBotoes(){

		btnOk.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnLimparCampos.setEnabled(false);		

	}

	public void habilitarCampos(){

		jpfSenha.setEditable(true);
		jtfNome.setEditable(true);
		jtfEmail.setEditable(true);
		jcbSexo.setEditable(true);
		jtfLogin.setEditable(true);
		jcbUf.setEditable(true);
		jtfEndereco.setEditable(true);
		jtfCidade.setEditable(true);
		jtfBairro.setEditable(true);
		jtfNumeroProntuario.setEditable(true);
		jtaHistorico.setEditable(true);
		ftfTelefone.setEditable(true);
		ftfRg.setEditable(true);
		ftfCpf.setEditable(true);
		ftfCep.setEditable(true);
		jdcDataNascimento.setEnabled(true);
		jdcProximaVacina.setEnabled(true);
		jtfNome.grabFocus();

	}

	public void desabilitarCampos(){

		jpfSenha.setEditable(false);
		jtfNome.setEditable(false);
		jtfEmail.setEditable(false);
		jcbSexo.setEditable(false);
		jtfLogin.setEditable(false);
		jcbUf.setEditable(false);
		jtfEndereco.setEditable(false);
		jtfCidade.setEditable(false);
		jtfBairro.setEditable(false);
		jtfNumeroProntuario.setEditable(false);
		jtaHistorico.setEditable(false);
		ftfTelefone.setEditable(false);
		ftfRg.setEditable(false);
		ftfCpf.setEditable(false);
		ftfCep.setEditable(false);
		jdcDataNascimento.setEnabled(false);
		jdcProximaVacina.setEnabled(false);

	}

	public void limparCampos(){

		jpfSenha.setText(null);
		jtfNome.setText("");
		jtfEmail.setText("");
		jcbSexo.setSelectedIndex(0);
		jtfLogin.setText("");
		jcbUf.setSelectedIndex(0);
		jtfEndereco.setText("");
		jtfCidade.setText("");
		jtfBairro.setText("");
		jtfNumeroProntuario.setText("");
		jtaHistorico.setText("");
		ftfTelefone.setText("");
		ftfRg.setText("");
		ftfCpf.setText("");
		ftfCep.setText("");
		jdcDataNascimento.setDate(null);
		jdcProximaVacina.setDate(null);

	}

	public void clarearCampos(){

		jpfSenha.setBackground(new Color(255, 255, 255));
		jtfNome.setBackground(new Color(255, 255, 255));
		jtfEmail.setBackground(new Color(255, 255, 255));
		jcbSexo.setBackground(new Color(255, 255, 255));
		jtfLogin.setBackground(new Color(255, 255, 255));
		jcbUf.setBackground(new Color(255, 255, 255));
		jtfEndereco.setBackground(new Color(255, 255, 255));
		jtfCidade.setBackground(new Color(255, 255, 255));
		jtfBairro.setBackground(new Color(255, 255, 255));
		jtfNumeroProntuario.setBackground(new Color(255, 255, 255));
		jtaHistorico.setBackground(new Color(255, 255, 255));
		ftfTelefone.setBackground(new Color(255, 255, 255));
		ftfRg.setBackground(new Color(255, 255, 255));
		ftfCpf.setBackground(new Color(255, 255, 255));
		ftfCep.setBackground(new Color(255, 255, 255));
		jdcDataNascimento.setBackground(new Color(255, 255, 255));
		jdcProximaVacina.setBackground(new Color(255, 255, 255));

	}

	public void escurecerCampos(){

		jpfSenha.setBackground(new Color(214, 217, 223));
		jtfNome.setBackground(new Color(214, 217, 223));
		jtfEmail.setBackground(new Color(214, 217, 223));
		jcbSexo.setBackground(new Color(214, 217, 223));
		jtfLogin.setBackground(new Color(214, 217, 223));
		jcbUf.setBackground(new Color(214, 217, 223));
		jtfEndereco.setBackground(new Color(214, 217, 223));
		jtfCidade.setBackground(new Color(214, 217, 223));
		jtfBairro.setBackground(new Color(214, 217, 223));
		jtfNumeroProntuario.setBackground(new Color(214, 217, 223));
		jtaHistorico.setBackground(new Color(214, 217, 223));
		ftfTelefone.setBackground(new Color(214, 217, 223));
		ftfRg.setBackground(new Color(214, 217, 223));
		ftfCpf.setBackground(new Color(214, 217, 223));
		ftfCep.setBackground(new Color(214, 217, 223));
		jdcDataNascimento.setBackground(new Color(214, 217, 223));
		jdcProximaVacina.setBackground(new Color(214, 217, 223));

	}
}
