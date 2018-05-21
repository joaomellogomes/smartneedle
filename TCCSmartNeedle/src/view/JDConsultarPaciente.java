package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;

import model.Paciente;
import model.DAO.PacienteDAO;

import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class JDConsultarPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField jpfSenha;
	private JTextField jtfNome;
	private JTextField jtfEmail;
	private JTextField jtfTelefone;
	private JTextField jtfRg;
	private JTextField jtfSexo;
	private JTextField jtfCpf;
	private JTextField jtfLogin;
	private JTextField jtfUf;
	private JTextField jtfEndereco;
	private JTextField jtfCidade;
	private JTextField jtfBairro;
	private JTextField jtfCep;
	private JTextField jtfDataNascimento;
	private JTextField jtfNumeroProntuario;
	private JTextField jtfProximaVacina;
	private JTextArea jtaHistorico;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			JDConsultarPaciente dialog = new JDConsultarPaciente();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public JDConsultarPaciente(Paciente pacienteConsulta) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDConsultarPaciente.class.getResource("/imagens/icone.png")));
		setTitle("Consultar Paciente");
		setBounds(100, 100, 696, 711);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblIdPaciente = new JLabel("ID Paciente: ");
		lblIdPaciente.setFont(new Font("Arial", Font.BOLD, 16));
		lblIdPaciente.setBounds(456, 6, 120, 24);
		contentPanel.add(lblIdPaciente);
		
		JLabel lblId = new JLabel("N/D");
		lblId.setText(String.valueOf(pacienteConsulta.getId()));
		lblId.setFont(new Font("Arial", Font.BOLD, 16));
		lblId.setBounds(600, 6, 55, 24);
		contentPanel.add(lblId);
		
		JPanel pnlDadosPessoais = new JPanel();
		pnlDadosPessoais.setLayout(null);
		pnlDadosPessoais.setBorder(new TitledBorder(null, "Dados pessoais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDadosPessoais.setBounds(12, 30, 645, 441);
		contentPanel.add(pnlDadosPessoais);
		
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
		
		jpfSenha = new JPasswordField(pacienteConsulta.getSenha());
		jpfSenha.setEditable(false);
		jpfSenha.setBackground(new Color(214, 217, 223));
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
		
		jtfNome = new JTextField(pacienteConsulta.getNome());
		jtfNome.setBackground(new Color(214, 217, 223));
		jtfNome.setEditable(false);
		jtfNome.setColumns(10);
		jtfNome.setBounds(92, 31, 520, 28);
		pnlDadosPessoais.add(jtfNome);
		
		jtfEmail = new JTextField(pacienteConsulta.getEmail());
		jtfEmail.setEditable(false);
		jtfEmail.setColumns(10);
		jtfEmail.setBackground(new Color(214, 217, 223));
		jtfEmail.setBounds(92, 77, 520, 28);
		pnlDadosPessoais.add(jtfEmail);
		
		jtfTelefone = new JTextField(pacienteConsulta.getTelefone());
		jtfTelefone.setEditable(false);
		jtfTelefone.setColumns(10);
		jtfTelefone.setBackground(new Color(214, 217, 223));
		jtfTelefone.setBounds(107, 123, 236, 28);
		pnlDadosPessoais.add(jtfTelefone);
		
		jtfRg = new JTextField(pacienteConsulta.getRg());
		jtfRg.setEditable(false);
		jtfRg.setColumns(10);
		jtfRg.setBackground(new Color(214, 217, 223));
		jtfRg.setBounds(402, 123, 210, 28);
		pnlDadosPessoais.add(jtfRg);
		
		jtfSexo = new JTextField(pacienteConsulta.getSexo());
		jtfSexo.setEditable(false);
		jtfSexo.setColumns(10);
		jtfSexo.setBackground(new Color(214, 217, 223));
		jtfSexo.setBounds(402, 167, 210, 28);
		pnlDadosPessoais.add(jtfSexo);
		
		jtfCpf = new JTextField(pacienteConsulta.getCpf());
		jtfCpf.setEditable(false);
		jtfCpf.setColumns(10);
		jtfCpf.setBackground(new Color(214, 217, 223));
		jtfCpf.setBounds(92, 167, 251, 28);
		pnlDadosPessoais.add(jtfCpf);
		
		jtfLogin = new JTextField(pacienteConsulta.getLogin());
		jtfLogin.setEditable(false);
		jtfLogin.setColumns(10);
		jtfLogin.setBackground(new Color(214, 217, 223));
		jtfLogin.setBounds(92, 213, 251, 28);
		pnlDadosPessoais.add(jtfLogin);
		
		jtfUf = new JTextField(pacienteConsulta.getUf());
		jtfUf.setEditable(false);
		jtfUf.setColumns(10);
		jtfUf.setBackground(new Color(214, 217, 223));
		jtfUf.setBounds(64, 261, 94, 26);
		pnlDadosPessoais.add(jtfUf);
		
		jtfEndereco = new JTextField(pacienteConsulta.getEndereco());
		jtfEndereco.setEditable(false);
		jtfEndereco.setColumns(10);
		jtfEndereco.setBackground(new Color(214, 217, 223));
		jtfEndereco.setBounds(270, 261, 342, 28);
		pnlDadosPessoais.add(jtfEndereco);
		
		JLabel lblCidade = new JLabel("Cidade: ");
		lblCidade.setFont(new Font("Arial", Font.BOLD, 14));
		lblCidade.setBounds(25, 305, 57, 24);
		pnlDadosPessoais.add(lblCidade);
		
		jtfCidade = new JTextField(pacienteConsulta.getCidade());
		jtfCidade.setEditable(false);
		jtfCidade.setColumns(10);
		jtfCidade.setBackground(new Color(214, 217, 223));
		jtfCidade.setBounds(92, 303, 200, 26);
		pnlDadosPessoais.add(jtfCidade);
		
		JLabel lblBairro = new JLabel("Bairro: ");
		lblBairro.setFont(new Font("Arial", Font.BOLD, 14));
		lblBairro.setBounds(304, 305, 57, 24);
		pnlDadosPessoais.add(lblBairro);
		
		jtfBairro = new JTextField(pacienteConsulta.getBairro());
		jtfBairro.setEditable(false);
		jtfBairro.setColumns(10);
		jtfBairro.setBackground(new Color(214, 217, 223));
		jtfBairro.setBounds(373, 301, 239, 28);
		pnlDadosPessoais.add(jtfBairro);
		
		JLabel lblCep = new JLabel("CEP: ");
		lblCep.setFont(new Font("Arial", Font.BOLD, 14));
		lblCep.setBounds(25, 356, 42, 24);
		pnlDadosPessoais.add(lblCep);
		
		jtfCep = new JTextField(pacienteConsulta.getCep());
		jtfCep.setEditable(false);
		jtfCep.setColumns(10);
		jtfCep.setBackground(new Color(214, 217, 223));
		jtfCep.setBounds(92, 351, 200, 28);
		pnlDadosPessoais.add(jtfCep);
		
		JLabel lblDataDeNascimento = new JLabel("Data de nascimento: ");
		lblDataDeNascimento.setFont(new Font("Arial", Font.BOLD, 14));
		lblDataDeNascimento.setBounds(304, 351, 146, 24);
		pnlDadosPessoais.add(lblDataDeNascimento);
		
		DateTimeFormatter formatadorDataNascimento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataNascimento = pacienteConsulta.getDataNascimento().format(formatadorDataNascimento);
		jtfDataNascimento = new JTextField(dataNascimento);
		jtfDataNascimento.setEditable(false);
		jtfDataNascimento.setColumns(10);
		jtfDataNascimento.setBackground(new Color(214, 217, 223));
		jtfDataNascimento.setBounds(461, 351, 151, 29);
		pnlDadosPessoais.add(jtfDataNascimento);
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(0, 647, 690, 37);
		contentPanel.add(pnlBotoes);
		pnlBotoes.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnOk = new JButton("OK");
		getRootPane().setDefaultButton(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlBotoes.add(btnOk);
		
		JPanel pnlDadosPaciente = new JPanel();
		pnlDadosPaciente.setLayout(null);
		pnlDadosPaciente.setBorder(new TitledBorder(null, "Dados do paciente", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		pnlDadosPaciente.setBounds(12, 476, 645, 172);
		contentPanel.add(pnlDadosPaciente);
		
		JLabel lblHistrico = new JLabel("Hist\u00F3rico:");
		lblHistrico.setFont(new Font("Arial", Font.BOLD, 14));
		lblHistrico.setBounds(299, 21, 81, 24);
		pnlDadosPaciente.add(lblHistrico);
		
		jtfNumeroProntuario = new JTextField(pacienteConsulta.getNumeroProntuario());
		jtfNumeroProntuario.setEditable(false);
		jtfNumeroProntuario.setColumns(10);
		jtfNumeroProntuario.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfNumeroProntuario.setBounds(20, 46, 212, 28);
		pnlDadosPaciente.add(jtfNumeroProntuario);
		
		JLabel lblNDoPronturio = new JLabel("N\u00BA do prontu\u00E1rio: ");
		lblNDoPronturio.setFont(new Font("Arial", Font.BOLD, 14));
		lblNDoPronturio.setBounds(20, 21, 134, 24);
		pnlDadosPaciente.add(lblNDoPronturio);
		
		DateTimeFormatter formatadorProximaVacina = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataProximaVacina = pacienteConsulta.getProximaVacina().format(formatadorProximaVacina);
		jtfProximaVacina = new JTextField(dataProximaVacina);
		jtfProximaVacina.setEditable(false);
		jtfProximaVacina.setColumns(10);
		jtfProximaVacina.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfProximaVacina.setBounds(20, 131, 212, 28);
		pnlDadosPaciente.add(jtfProximaVacina);
		
		JLabel lblPrximaVacina = new JLabel("Pr\u00F3xima vacina:");
		lblPrximaVacina.setFont(new Font("Arial", Font.BOLD, 14));
		lblPrximaVacina.setBounds(20, 97, 118, 24);
		pnlDadosPaciente.add(lblPrximaVacina);
		
		JScrollPane jspHistorico = new JScrollPane();
		jspHistorico.setBounds(299, 43, 324, 116);
		pnlDadosPaciente.add(jspHistorico);
		
		jtaHistorico = new JTextArea(pacienteConsulta.getHistorico());
		jtaHistorico.setLineWrap(true);
		jtaHistorico.setWrapStyleWord(true);
		jtaHistorico.setWrapStyleWord(true);
		jtaHistorico.setLineWrap(true);
		jspHistorico.setViewportView(jtaHistorico);
		
		try{
			PlasticLookAndFeel.setPlasticTheme(new DarkStar() {
			});
			try{

				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			}catch(InstantiationException erro){
				Logger.getLogger(JFLogin.class.getName()).log(Level.SEVERE, null, erro);
			}catch(IllegalAccessException erro){
				Logger.getLogger(JFLogin.class.getName()).log(Level.SEVERE, null, erro);
			}catch(UnsupportedLookAndFeelException erro){
				Logger.getLogger(JFLogin.class.getName()).log(Level.SEVERE, null, erro);
			}
		}catch(ClassNotFoundException erro){
			JOptionPane.showMessageDialog(null, "Classe não encontrada", "Classe não encontrada!",
					JOptionPane.ERROR_MESSAGE);
		}
		SwingUtilities.updateComponentTreeUI(this);
		
	}
}
