package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.joda.time.DateTime;

import com.toedter.calendar.JDateChooser;

import model.Funcionario;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.Component;
import javax.swing.JPasswordField;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JDConsultarFuncionario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfId;
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
	private JTextField jtfNumeroDocumento;
	private JTextField jtfNumeroUnidade;

	/**
	 * Create the dialog.
	 */
	public JDConsultarFuncionario(Funcionario funcionarioConsulta) {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDConsultarFuncionario.class.getResource("/imagens/icone.png")));
		setTitle("Consultar Funcion\u00E1rio (" + funcionarioConsulta.getLogin() + ")");
		setResizable(false);
		setBounds(100, 100, 694, 710);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel pnlDadosPessoais = new JPanel();
		pnlDadosPessoais.setBorder(new TitledBorder(null, "Dados pessoais", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDadosPessoais.setBounds(22, 52, 645, 441);
		contentPanel.add(pnlDadosPessoais);
		pnlDadosPessoais.setLayout(null);
		
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
		
		jpfSenha = new JPasswordField(funcionarioConsulta.getSenha());
		jpfSenha.setBackground(new Color(214, 217, 223));
		jpfSenha.setEditable(false);
		jpfSenha.setBounds(412, 213, 200, 26);
		pnlDadosPessoais.add(jpfSenha);
		
		JLabel lblUf = new JLabel("UF: ");
		lblUf.setFont(new Font("Arial", Font.BOLD, 14));
		lblUf.setBounds(25, 263, 42, 24);
		pnlDadosPessoais.add(lblUf);
		
		JLabel lblEndereco = new JLabel("Endere\u00E7o: ");
		lblEndereco.setFont(new Font("Arial", Font.BOLD, 14));
		lblEndereco.setBounds(177, 263, 81, 24);
		pnlDadosPessoais.add(lblEndereco);
		
		jtfNome = new JTextField(funcionarioConsulta.getNome());
		jtfNome.setBackground(new Color(214, 217, 223));
		jtfNome.setEditable(false);
		jtfNome.setBounds(92, 31, 520, 28);
		pnlDadosPessoais.add(jtfNome);
		jtfNome.setColumns(10);
		
		jtfEmail = new JTextField(funcionarioConsulta.getEmail());
		jtfEmail.setEditable(false);
		jtfEmail.setBackground(new Color(214, 217, 223));
		jtfEmail.setBounds(92, 77, 520, 28);
		pnlDadosPessoais.add(jtfEmail);
		jtfEmail.setColumns(10);
		
		jtfTelefone = new JTextField(funcionarioConsulta.getTelefone());
		jtfTelefone.setEditable(false);
		jtfTelefone.setBackground(new Color(214, 217, 223));
		jtfTelefone.setBounds(107, 123, 236, 28);
		pnlDadosPessoais.add(jtfTelefone);
		jtfTelefone.setColumns(10);
		
		jtfRg = new JTextField(funcionarioConsulta.getRg());
		jtfRg.setEditable(false);
		jtfRg.setBackground(new Color(214, 217, 223));
		jtfRg.setBounds(402, 123, 210, 28);
		pnlDadosPessoais.add(jtfRg);
		jtfRg.setColumns(10);
		
		jtfSexo = new JTextField(funcionarioConsulta.getSexo());
		jtfSexo.setEditable(false);
		jtfSexo.setBackground(new Color(214, 217, 223));
		jtfSexo.setBounds(402, 167, 210, 28);
		pnlDadosPessoais.add(jtfSexo);
		jtfSexo.setColumns(10);
		
		jtfCpf = new JTextField(funcionarioConsulta.getCpf());
		jtfCpf.setEditable(false);
		jtfCpf.setBackground(new Color(214, 217, 223));
		jtfCpf.setBounds(92, 167, 251, 28);
		pnlDadosPessoais.add(jtfCpf);
		jtfCpf.setColumns(10);
		
		jtfLogin = new JTextField(funcionarioConsulta.getLogin());
		jtfLogin.setEditable(false);
		jtfLogin.setBackground(new Color(214, 217, 223));
		jtfLogin.setBounds(92, 213, 251, 28);
		pnlDadosPessoais.add(jtfLogin);
		jtfLogin.setColumns(10);
		
		jtfUf = new JTextField(funcionarioConsulta.getUf());
		jtfUf.setEditable(false);
		jtfUf.setBackground(new Color(214, 217, 223));
		jtfUf.setBounds(64, 261, 94, 26);
		pnlDadosPessoais.add(jtfUf);
		jtfUf.setColumns(10);
		
		jtfEndereco = new JTextField(funcionarioConsulta.getEndereco());
		jtfEndereco.setEditable(false);
		jtfEndereco.setBackground(new Color(214, 217, 223));
		jtfEndereco.setBounds(270, 261, 342, 28);
		pnlDadosPessoais.add(jtfEndereco);
		jtfEndereco.setColumns(10);
		
		JLabel lblCidade = new JLabel("Cidade: ");
		lblCidade.setFont(new Font("Arial", Font.BOLD, 14));
		lblCidade.setBounds(25, 305, 57, 24);
		pnlDadosPessoais.add(lblCidade);
		
		jtfCidade = new JTextField(funcionarioConsulta.getCidade());
		jtfCidade.setBackground(new Color(214, 217, 223));
		jtfCidade.setEditable(false);
		jtfCidade.setBounds(92, 303, 200, 26);
		pnlDadosPessoais.add(jtfCidade);
		jtfCidade.setColumns(10);
		
		JLabel lblBairro = new JLabel("Bairro: ");
		lblBairro.setFont(new Font("Arial", Font.BOLD, 14));
		lblBairro.setBounds(304, 305, 57, 24);
		pnlDadosPessoais.add(lblBairro);
		
		jtfBairro = new JTextField(funcionarioConsulta.getBairro());
		jtfBairro.setBackground(new Color(214, 217, 223));
		jtfBairro.setEditable(false);
		jtfBairro.setBounds(373, 301, 239, 28);
		pnlDadosPessoais.add(jtfBairro);
		jtfBairro.setColumns(10);
		
		JLabel lblCep = new JLabel("CEP: ");
		lblCep.setFont(new Font("Arial", Font.BOLD, 14));
		lblCep.setBounds(25, 356, 42, 24);
		pnlDadosPessoais.add(lblCep);
		
		jtfCep = new JTextField(funcionarioConsulta.getCep());
		jtfCep.setBackground(new Color(214, 217, 223));
		jtfCep.setEditable(false);
		jtfCep.setBounds(92, 351, 200, 28);
		pnlDadosPessoais.add(jtfCep);
		jtfCep.setColumns(10);
		
		JLabel lblDataDeNascimento = new JLabel("Data de nascimento: ");
		lblDataDeNascimento.setFont(new Font("Arial", Font.BOLD, 14));
		lblDataDeNascimento.setBounds(304, 351, 146, 24);
		pnlDadosPessoais.add(lblDataDeNascimento);
		
		DateTimeFormatter formatadorDataNascimento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataNascimento = funcionarioConsulta.getDataNascimento().format(formatadorDataNascimento);
		jtfDataNascimento = new JTextField(dataNascimento);
		jtfDataNascimento.setEditable(false);
		jtfDataNascimento.setBackground(new Color(214, 217, 223));
		jtfDataNascimento.setBounds(461, 351, 151, 29);
		pnlDadosPessoais.add(jtfDataNascimento);
		jtfDataNascimento.setColumns(10);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Arial", Font.BOLD, 14));
		lblId.setBounds(510, 24, 29, 24);
		contentPanel.add(lblId);
		{
			jtfId = new JTextField(String.valueOf(funcionarioConsulta.getId()));
			jtfId.setBackground(new Color(214, 217, 223));
			jtfId.setEditable(false);
			jtfId.setBounds(540, 22, 122, 28);
			contentPanel.add(jtfId);
			jtfId.setColumns(10);
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Dados do funcion\u00E1rio", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel.setBounds(22, 505, 645, 132);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNmeroDoDocumento = new JLabel("Número do documento: ");
		lblNmeroDoDocumento.setFont(new Font("Arial", Font.BOLD, 14));
		lblNmeroDoDocumento.setBounds(71, 32, 168, 24);
		panel.add(lblNmeroDoDocumento);
		
		jtfNumeroDocumento = new JTextField(funcionarioConsulta.getNumeroDocumento());
		jtfNumeroDocumento.setEditable(false);
		jtfNumeroDocumento.setBackground(new Color(214, 217, 223));
		jtfNumeroDocumento.setBounds(251, 30, 325, 28);
		panel.add(jtfNumeroDocumento);
		jtfNumeroDocumento.setColumns(10);
		
		JLabel lblNmeroDoUnidade = new JLabel("Número da unidade: ");
		lblNmeroDoUnidade.setFont(new Font("Arial", Font.BOLD, 14));
		lblNmeroDoUnidade.setBounds(71, 78, 168, 24);
		panel.add(lblNmeroDoUnidade);
		
		jtfNumeroUnidade = new JTextField(funcionarioConsulta.getNumeroUnidade());
		jtfNumeroUnidade.setBackground(new Color(214, 217, 223));
		jtfNumeroUnidade.setEditable(false);
		jtfNumeroUnidade.setBounds(251, 76, 325, 28);
		panel.add(jtfNumeroUnidade);
		jtfNumeroUnidade.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		btnOk.setBounds(0, 643, 688, 38);
		contentPanel.add(btnOk);
		
	}
}
