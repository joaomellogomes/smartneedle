package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.swing.JButton;
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
import model.Funcionario;
import model.DAO.AdministradorDAO;
import model.DAO.FuncionarioDAO;
import java.awt.FlowLayout;

public class JDAlterarFuncionario extends JDialog {

	//Criação dos objetos
	AdministradorDAO admDAO = new AdministradorDAO();
	Funcionario funcionario = new Funcionario();
	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

	//String para receber o nome do componente que deve ser habilitado para edição
	String nomeComponente = "";

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfId;
	private JPasswordField jpfSenha;
	private JTextField jtfNome;
	private JTextField jtfEmail;
	private JTextField jtfSexo;
	private JTextField jtfLogin;
	private JTextField jtfUf;
	private JTextField jtfEndereco;
	private JTextField jtfCidade;
	private JTextField jtfBairro;
	private JTextField jtfNumeroDocumento;
	private JTextField jtfNumeroUnidade;
	private JButton btnOk;
	private JButton btnCancelar;
	private JButton btnEditar;
	private JFormattedTextField ftfTelefone;
	private JFormattedTextField ftfRg;
	private JFormattedTextField ftfCpf;
	private JFormattedTextField ftfCep;
	private JDateChooser jdcDataNascimento;
	private JButton btnLimparCampos;

	/**
	 * Create the dialog.
	 */
	public JDAlterarFuncionario(Funcionario funcionarioAlterado, Administrador admLogado) {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDAlterarFuncionario.class.getResource("/imagens/icone.png")));
		setTitle("Alterar Funcionário (" + funcionarioAlterado.getLogin() + ")");
		setResizable(false);
		setBounds(100, 100, 696, 720);
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

		jpfSenha = new JPasswordField(funcionarioAlterado.getSenha());
		jpfSenha.setName("Senha");
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

		jtfNome = new JTextField(funcionarioAlterado.getNome());
		jtfNome.setName("Nome");
		jtfNome.setBackground(new Color(214, 217, 223));
		jtfNome.setEditable(false);
		jtfNome.setBounds(92, 31, 520, 28);
		pnlDadosPessoais.add(jtfNome);
		jtfNome.setColumns(10);

		jtfEmail = new JTextField(funcionarioAlterado.getEmail());
		jtfEmail.setName("E-mail");
		jtfEmail.setEditable(false);
		jtfEmail.setBackground(new Color(214, 217, 223));
		jtfEmail.setBounds(92, 77, 520, 28);
		pnlDadosPessoais.add(jtfEmail);
		jtfEmail.setColumns(10);

		jtfSexo = new JTextField(funcionarioAlterado.getSexo());
		jtfSexo.setName("Sexo");
		jtfSexo.setEditable(false);
		jtfSexo.setBackground(new Color(214, 217, 223));
		jtfSexo.setBounds(402, 167, 210, 28);
		pnlDadosPessoais.add(jtfSexo);
		jtfSexo.setColumns(10);

		jtfLogin = new JTextField(funcionarioAlterado.getLogin());
		jtfLogin.setName("Login");
		jtfLogin.setEditable(false);
		jtfLogin.setBackground(new Color(214, 217, 223));
		jtfLogin.setBounds(92, 213, 251, 28);
		pnlDadosPessoais.add(jtfLogin);
		jtfLogin.setColumns(10);

		jtfUf = new JTextField(funcionarioAlterado.getUf());
		jtfUf.setName("UF");
		jtfUf.setEditable(false);
		jtfUf.setBackground(new Color(214, 217, 223));
		jtfUf.setBounds(64, 261, 94, 26);
		pnlDadosPessoais.add(jtfUf);
		jtfUf.setColumns(10);

		jtfEndereco = new JTextField(funcionarioAlterado.getEndereco());
		jtfEndereco.setName("Endere\u00E7o");
		jtfEndereco.setEditable(false);
		jtfEndereco.setBackground(new Color(214, 217, 223));
		jtfEndereco.setBounds(270, 261, 342, 28);
		pnlDadosPessoais.add(jtfEndereco);
		jtfEndereco.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade: ");
		lblCidade.setFont(new Font("Arial", Font.BOLD, 14));
		lblCidade.setBounds(25, 305, 57, 24);
		pnlDadosPessoais.add(lblCidade);

		jtfCidade = new JTextField(funcionarioAlterado.getCidade());
		jtfCidade.setName("Cidade");
		jtfCidade.setBackground(new Color(214, 217, 223));
		jtfCidade.setEditable(false);
		jtfCidade.setBounds(92, 303, 200, 26);
		pnlDadosPessoais.add(jtfCidade);
		jtfCidade.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro: ");
		lblBairro.setFont(new Font("Arial", Font.BOLD, 14));
		lblBairro.setBounds(304, 305, 57, 24);
		pnlDadosPessoais.add(lblBairro);

		jtfBairro = new JTextField(funcionarioAlterado.getBairro());
		jtfBairro.setName("Bairro");
		jtfBairro.setBackground(new Color(214, 217, 223));
		jtfBairro.setEditable(false);
		jtfBairro.setBounds(373, 301, 239, 28);
		pnlDadosPessoais.add(jtfBairro);
		jtfBairro.setColumns(10);

		JLabel lblCep = new JLabel("CEP: ");
		lblCep.setFont(new Font("Arial", Font.BOLD, 14));
		lblCep.setBounds(25, 356, 42, 24);
		pnlDadosPessoais.add(lblCep);

		JLabel lblDataDeNascimento = new JLabel("Data de nascimento: ");
		lblDataDeNascimento.setFont(new Font("Arial", Font.BOLD, 14));
		lblDataDeNascimento.setBounds(304, 356, 146, 24);
		pnlDadosPessoais.add(lblDataDeNascimento);

		ftfTelefone = new JFormattedTextField();
		ftfTelefone.setName("Telefone");
		MaskFormatter mascara = null;
		try {
			mascara = new MaskFormatter("(##)####-####");

		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e);
		}

		mascara.install(ftfTelefone);
		ftfTelefone.setBackground(new Color(214, 217, 223));
		ftfTelefone.setText(funcionarioAlterado.getTelefone());
		ftfTelefone.setEditable(false);
		ftfTelefone.setBounds(105, 125, 240, 26);
		pnlDadosPessoais.add(ftfTelefone);


		ftfRg = new JFormattedTextField();
		ftfRg.setName("RG");
		ftfRg.setEditable(false);

		try{
			mascara = new MaskFormatter("##.###.###-#");

		}catch(ParseException erro){
			JOptionPane.showMessageDialog(null, erro);
		}


		mascara.install(ftfRg);
		ftfRg.setBackground(new Color(214, 217, 223));
		ftfRg.setText(funcionarioAlterado.getRg());
		ftfRg.setBounds(400, 125, 210, 24);
		pnlDadosPessoais.add(ftfRg);

		ftfCpf = new JFormattedTextField();
		ftfCpf.setName("CPF");
		ftfCpf.setEditable(false);

		try{
			mascara = new MaskFormatter("###.###.###-##");
		}catch(ParseException erro){
			JOptionPane.showMessageDialog(null, "Erro ao inserir mascara no campo \n" + erro,
					"Erro na máscara!", JOptionPane.ERROR_MESSAGE);
		}

		mascara.install(ftfCpf);
		ftfCpf.setText(funcionarioAlterado.getCpf());
		ftfCpf.setBackground(new Color(214, 217, 223));
		ftfCpf.setBounds(92, 169, 253, 26);
		pnlDadosPessoais.add(ftfCpf);

		ftfCep = new JFormattedTextField();
		ftfCep.setName("CEP");
		ftfCep.setEditable(false);

		try{
			mascara = new MaskFormatter("#####-###");
		}catch(ParseException erro){
			JOptionPane.showMessageDialog(null, "Erro ao inserir mascara no campo \n" + erro,
					"Erro na máscara!", JOptionPane.ERROR_MESSAGE);
		}

		mascara.install(ftfCep);
		ftfCep.setBackground(new Color(214, 217, 223));
		ftfCep.setText(funcionarioAlterado.getCep());
		ftfCep.setBounds(89, 355, 203, 28);
		pnlDadosPessoais.add(ftfCep);

		jdcDataNascimento = new JDateChooser(new DateTime(funcionarioAlterado.getDataNascimento().toString()).toDate());
		jdcDataNascimento.setName("Data de nascimento");
		jdcDataNascimento.setEnabled(false);
		jdcDataNascimento.setBackground(new Color(214, 217, 223));
		jdcDataNascimento.setBounds(460, 356, 152, 27);
		pnlDadosPessoais.add(jdcDataNascimento);

		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Arial", Font.BOLD, 14));
		lblId.setBounds(510, 24, 29, 24);
		contentPanel.add(lblId);
		{
			jtfId = new JTextField(String.valueOf(funcionarioAlterado.getId()));
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

		jtfNumeroDocumento = new JTextField(funcionarioAlterado.getNumeroDocumento());
		jtfNumeroDocumento.setName("N\u00FAmero do documento");
		jtfNumeroDocumento.setEditable(false);
		jtfNumeroDocumento.setBackground(new Color(214, 217, 223));
		jtfNumeroDocumento.setBounds(251, 30, 325, 28);
		panel.add(jtfNumeroDocumento);
		jtfNumeroDocumento.setColumns(10);

		JLabel lblNmeroDoUnidade = new JLabel("Número da unidade: ");
		lblNmeroDoUnidade.setFont(new Font("Arial", Font.BOLD, 14));
		lblNmeroDoUnidade.setBounds(71, 78, 168, 24);
		panel.add(lblNmeroDoUnidade);

		jtfNumeroUnidade = new JTextField(funcionarioAlterado.getNumeroUnidade());
		jtfNumeroUnidade.setName("N\u00FAmero da unidade");
		jtfNumeroUnidade.setBackground(new Color(214, 217, 223));
		jtfNumeroUnidade.setEditable(false);
		jtfNumeroUnidade.setBounds(251, 76, 325, 28);
		panel.add(jtfNumeroUnidade);
		jtfNumeroUnidade.setColumns(10);

		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(0, 648, 688, 33);
		contentPanel.add(pnlBotoes);

		btnOk = new JButton("OK");
		getRootPane().setDefaultButton(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object[] botoes = {"Sim", "Cancelar"};
				String senhaDigitada = "";

				int opcao = JOptionPane.showOptionDialog(null, "Deseja continuar esta alteração?", "Alterar", 
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

					botoes[0] = "Alterar";
					botoes[1] = "Cancelar";

					opcao = JOptionPane.showOptionDialog(JDAlterarFuncionario.this, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[0]);

					senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

					if(opcao == JOptionPane.OK_OPTION){

						final Administrador admAutenticado = admDAO.autenticar(admLogado.getLogin(), senhaDigitada);

							if(admAutenticado.getSenha().equals(senhaDigitada)){
	
								try{
									funcionario.setId(Integer.parseInt(jtfId.getText()));
									funcionario.setNome(jtfNome.getText());
									funcionario.setEmail(jtfEmail.getText());
									funcionario.setTelefone(ftfTelefone.getText());
									funcionario.setRg(ftfRg.getText());
									funcionario.setCpf(ftfCpf.getText());
									funcionario.setSexo(jtfSexo.getText());
									funcionario.setLogin(jtfLogin.getText());
									funcionario.setSenha(jpfSenha.getPassword().toString());
									funcionario.setUf(jtfUf.getText());
									funcionario.setEndereco(jtfEndereco.getText());
									funcionario.setCidade(jtfCidade.getText());
									funcionario.setBairro(jtfBairro.getText());
									funcionario.setCep(ftfCep.getText());
	
									SimpleDateFormat formatadorDataNascimento = new SimpleDateFormat("yyyy-MM-dd");
									
									String dataNascimento = formatadorDataNascimento.format(jdcDataNascimento.getDate());
									dataNascimento += "T00:00:00.000";
									funcionario.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	
									funcionario.setNumeroDocumento(jtfNumeroDocumento.getText());
									funcionario.setNumeroUnidade(jtfNumeroUnidade.getText());
	
									funcionarioDAO.alterar(funcionario);
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
		pnlBotoes.setLayout(new GridLayout(0, 4, 0, 0));
		btnOk.setEnabled(false);
		pnlBotoes.add(btnOk);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				escurecerCampos();
				desabilitarBotoes();
				desabilitarCampos();

			}
		});
		btnCancelar.setEnabled(false);
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
		
		btnLimparCampos = new JButton("Limpar campos");
		btnLimparCampos.setEnabled(false);
		pnlBotoes.add(btnLimparCampos);

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
		jtfSexo.setEditable(true);
		jtfLogin.setEditable(true);
		jtfUf.setEditable(true);
		jtfEndereco.setEditable(true);
		jtfCidade.setEditable(true);
		jtfBairro.setEditable(true);
		jtfNumeroDocumento.setEditable(true);
		jtfNumeroUnidade.setEditable(true);
		ftfTelefone.setEditable(true);
		ftfRg.setEditable(true);
		ftfCpf.setEditable(true);
		ftfCep.setEditable(true);
		jdcDataNascimento.setEnabled(true);
		jtfNome.grabFocus();

	}

	public void desabilitarCampos(){

		jpfSenha.setEditable(false);
		jtfNome.setEditable(false);
		jtfEmail.setEditable(false);
		jtfSexo.setEditable(false);
		jtfLogin.setEditable(false);
		jtfUf.setEditable(false);
		jtfEndereco.setEditable(false);
		jtfCidade.setEditable(false);
		jtfBairro.setEditable(false);
		jtfNumeroDocumento.setEditable(false);
		jtfNumeroUnidade.setEditable(false);
		ftfTelefone.setEditable(false);
		ftfRg.setEditable(false);
		ftfCpf.setEditable(false);
		ftfCep.setEditable(false);
		jdcDataNascimento.setEnabled(false);

	}

	public void limparCampos(){

		jpfSenha.setText(null);
		jtfNome.setText("");
		jtfEmail.setText("");
		jtfSexo.setText("");
		jtfLogin.setText("");
		jtfUf.setText("");
		jtfEndereco.setText("");
		jtfCidade.setText("");
		jtfBairro.setText("");
		jtfNumeroDocumento.setText("");
		jtfNumeroUnidade.setText("");
		ftfTelefone.setText("");
		ftfRg.setText("");
		ftfCpf.setText("");
		ftfCep.setText("");
		jdcDataNascimento.setDate(null);

	}

	public void clarearCampos(){

		jpfSenha.setBackground(new Color(255, 255, 255));
		jtfNome.setBackground(new Color(255, 255, 255));
		jtfEmail.setBackground(new Color(255, 255, 255));
		jtfSexo.setBackground(new Color(255, 255, 255));
		jtfLogin.setBackground(new Color(255, 255, 255));
		jtfUf.setBackground(new Color(255, 255, 255));
		jtfEndereco.setBackground(new Color(255, 255, 255));
		jtfCidade.setBackground(new Color(255, 255, 255));
		jtfBairro.setBackground(new Color(255, 255, 255));
		jtfNumeroDocumento.setBackground(new Color(255, 255, 255));
		jtfNumeroUnidade.setBackground(new Color(255, 255, 255));
		ftfTelefone.setBackground(new Color(255, 255, 255));
		ftfRg.setBackground(new Color(255, 255, 255));
		ftfCpf.setBackground(new Color(255, 255, 255));
		ftfCep.setBackground(new Color(255, 255, 255));
		jdcDataNascimento.setBackground(new Color(255, 255, 255));

	}

	public void escurecerCampos(){

		jpfSenha.setBackground(new Color(214, 217, 223));
		jtfNome.setBackground(new Color(214, 217, 223));
		jtfEmail.setBackground(new Color(214, 217, 223));
		jtfSexo.setBackground(new Color(214, 217, 223));
		jtfLogin.setBackground(new Color(214, 217, 223));
		jtfUf.setBackground(new Color(214, 217, 223));
		jtfEndereco.setBackground(new Color(214, 217, 223));
		jtfCidade.setBackground(new Color(214, 217, 223));
		jtfBairro.setBackground(new Color(214, 217, 223));
		jtfNumeroDocumento.setBackground(new Color(214, 217, 223));
		jtfNumeroUnidade.setBackground(new Color(214, 217, 223));
		ftfTelefone.setBackground(new Color(214, 217, 223));
		ftfRg.setBackground(new Color(214, 217, 223));
		ftfCpf.setBackground(new Color(214, 217, 223));
		ftfCep.setBackground(new Color(214, 217, 223));
		jdcDataNascimento.setBackground(new Color(214, 217, 223));

	}

	public boolean verficarCampos(){
		
		Component components[] = getContentPane().getComponents();
		
		//Para cada componente
		for(int i = 0; i < components.length; i++){
			
			//Verifica se é um JTextField, JPasswordField, JDateChooser
			if(components[i]  instanceof JTextField){
				//Verifica se está preenchido
				if(((JTextField) components[i]).getText().isEmpty()){
					
					JOptionPane.showMessageDialog(null, "Preencha todos os campos!\n+"
							+ ((JTextField) components[i]).getName(), "Erro ao alterar", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
			
		}
		
		return false;

	}

}
