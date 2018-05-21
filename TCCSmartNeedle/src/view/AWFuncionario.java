package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;

import model.Funcionario;
import model.FuncionarioTableModel;
import model.Login;
import model.Paciente;
import model.PacienteTableModel;
import model.DAO.ConnectionFactory;
import model.DAO.LoginDAO;
import model.DAO.PacienteDAO;
import java.awt.event.WindowFocusListener;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class AWFuncionario {

	//Criação dos objetos
	Login login = new Login();
	LoginDAO loginDAO = new LoginDAO();

	Paciente paciente = new Paciente();
	PacienteDAO pacienteDAO = new PacienteDAO();

	public JFrame frmFuncionario;
	private JTextField jtfFiltrar;
	private JTable jtbPacientes;
	private JRadioButton jrbNome;
	private JRadioButton jrbCpf;
	private JPasswordField jpfSenhaConfirmacao;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	//	/**
	//	 * Launch the application.
	//	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					AWFuncionario window = new AWFuncionario();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public AWFuncionario(Funcionario funcionario, Login login) {
		initialize(funcionario, login);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Funcionario funcionario, Login usuarioLogado) {
		frmFuncionario = new JFrame();
		frmFuncionario.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				//jtbPacientes
				PacienteTableModel model = new PacienteTableModel(pacienteDAO.listarPacientes("", ""));
				model.fireTableDataChanged();
				jtbPacientes.setModel(model);
			}
			public void windowLostFocus(WindowEvent e) {
				//jtbPacientes
				PacienteTableModel model = new PacienteTableModel(pacienteDAO.listarPacientes("", ""));
				model.fireTableDataChanged();
				jtbPacientes.setModel(model);
			}
		});
		frmFuncionario.setTitle("Funcionário - " + funcionario.getLogin());
		frmFuncionario.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				sair(usuarioLogado);
			}
		});
		frmFuncionario.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				frmFuncionario.setEnabled(false);
				frmFuncionario.setEnabled(true);
			}
		});
		frmFuncionario.setBounds(100, 100, 450, 300);
		frmFuncionario.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//Definir tamanho da tela como padrão (proporcional ao visor), impedir que o 
		//tamanho seja alterado e desativar o botão Maximizar
		Insets in = Toolkit.getDefaultToolkit().getScreenInsets(frmFuncionario.getGraphicsConfiguration());
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = d.width - (in.left + in.top);
		int height = d.height - (in.top + in.bottom);
		frmFuncionario.setLocationRelativeTo(null);
		frmFuncionario.setSize(width, height);
		frmFuncionario.setResizable(false);
		frmFuncionario.setIconImage(Toolkit.getDefaultToolkit().getImage(AWAdministrador.class.getResource("/imagens/icone.png")));

		//Criação do model PacienteTableModel
		final PacienteTableModel model = new PacienteTableModel(pacienteDAO.listarPacientes("", ""));

		//Panel Pai
		final JPanel pnlPai = new JPanel();	
		pnlPai.setLayout(new CardLayout(0, 0));
		pnlPai.setBounds(321, 20, 1019, 664);

		//Panel A
		JPanel pnlA = new JPanel();

		TitledBorder bordaA = new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Pacientes", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 128, 128));
		bordaA.setTitleFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlA.setBorder(bordaA);

		pnlA.setBackground(new Color(214, 217, 223));		
		//Panel B
		JPanel pnlB = new JPanel();

		TitledBorder bordaB = new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Vacinas", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 99, 71));
		bordaB.setTitleFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlB.setBorder(bordaB);

		pnlB.setBackground(new Color(214, 217, 223));
		pnlB.setLayout(null);

		//Panel C
		JPanel pnlC = new JPanel();

		TitledBorder bordaC = new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Estoque", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(46, 139, 87));
		bordaC.setTitleFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlC.setBorder(bordaC);
		pnlC.setBackground(new Color(214, 217, 223));
		//		
		pnlC.setLayout(null);

		//Panel D
		JPanel pnlD = new JPanel();

		TitledBorder bordaD = new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Relatório", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 140, 0));
		bordaD.setTitleFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlD.setBorder(bordaD);

		pnlD.setBackground(new Color(214, 217, 223));
		pnlD.setLayout(null);

		pnlPai.add(pnlA, "telaUm");
		pnlA.setLayout(null);

		JScrollPane jspPacientes = new JScrollPane();
		jspPacientes.setBounds(24, 140, 968, 436);

		TitledBorder borda = new TitledBorder(null, "Quadro de pacientes", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 139, 139));
		borda.setTitleFont(new Font("Segoe Print", Font.PLAIN, 20));

		jtfFiltrar = new JTextField();
		jtfFiltrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String opcaoFiltro = "";

				if(jrbNome.isSelected()){
					opcaoFiltro = "nome";
				}else if(jrbCpf.isSelected()){
					opcaoFiltro = "cpf";
				}

				//Filtra os registros apresentados de acordo com a inserção de dados no jtfFiltrar
				if(jtfFiltrar.getText().equals("")){

					model.setListaPaciente(pacienteDAO.listarPacientes("", ""));

				}else{

					model.setListaPaciente(pacienteDAO.listarPacientes(jtfFiltrar.getText(), opcaoFiltro));

				}
				jtbPacientes.setModel(model);
			}
		});
		jtfFiltrar.setColumns(10);
		jtfFiltrar.setBounds(24, 96, 483, 28);
		pnlA.add(jtfFiltrar);
		jspPacientes.setBorder(borda);

		pnlA.add(jspPacientes);

		jtbPacientes = new JTable();		
		jtbPacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtbPacientes.getTableHeader().setReorderingAllowed(false);
		jspPacientes.setViewportView(jtbPacientes);

		//Atribuição do model a tabela
		jtbPacientes.setModel(model);
		
		JPopupMenu jpmOperacoes = new JPopupMenu();
		addPopup(jtbPacientes, jpmOperacoes);
		
		JMenuItem jmiAplicarVacina = new JMenuItem("Aplicar Vacina");
		jmiAplicarVacina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarVacina(funcionario);
			}
		});
		jpmOperacoes.add(jmiAplicarVacina);
		
		JMenuItem jmiConsultar = new JMenuItem("Consultar");
		jmiConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarPaciente();
			}
		});
		jpmOperacoes.add(jmiConsultar);
		
		JMenuItem jmiCadastrar = new JMenuItem("Cadastrar");
		jmiCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarPaciente(funcionario);
			}
		});
		jpmOperacoes.add(jmiCadastrar);
		
		JMenuItem jmiExcluir = new JMenuItem("Excluir");
		jmiExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirPaciente(funcionario);
			}
		});
		jpmOperacoes.add(jmiExcluir);
		
		JMenuItem jmiAlterar = new JMenuItem("Alterar");
		jmiAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarPaciente(funcionario);
			}
		});
		jpmOperacoes.add(jmiAlterar);

		JLabel lblFiltrarPor = new JLabel("Filtrar por: ");
		lblFiltrarPor.setBounds(24, 67, 72, 16);
		pnlA.add(lblFiltrarPor);

		jrbNome = new JRadioButton("Nome");
		jrbNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtfFiltrar.grabFocus();
			}
		});
		buttonGroup.add(jrbNome);
		jrbNome.setSelected(true);
		jrbNome.setBounds(108, 66, 72, 18);
		pnlA.add(jrbNome);

		jrbCpf = new JRadioButton("CPF");
		jrbCpf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtfFiltrar.grabFocus();
			}
		});
		buttonGroup.add(jrbCpf);
		jrbCpf.setBounds(192, 66, 72, 18);
		pnlA.add(jrbCpf);

		JButton btnOrdenarPorLogin = new JButton("Ordenar por login");
		btnOrdenarPorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.ordenarLogin();
				jtbPacientes.setModel(model);
			}
		});
		btnOrdenarPorLogin.setBounds(811, 92, 181, 36);
		pnlA.add(btnOrdenarPorLogin);

		JButton btnOrdenarPorNome = new JButton("Ordenanar por nome");
		btnOrdenarPorNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.ordenarNome();
				jtbPacientes.setModel(model);
			}
		});
		btnOrdenarPorNome.setBounds(811, 42, 181, 36);
		pnlA.add(btnOrdenarPorNome);

		JButton btnConsultarPaciente = new JButton("Consultar");
		btnConsultarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarPaciente();
			}
		});
		btnConsultarPaciente.setBounds(24, 588, 243, 47);
		pnlA.add(btnConsultarPaciente);

		JButton btnCadastrarPaciente = new JButton("Cadastrar");
		btnCadastrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarPaciente(funcionario);
			}
		});
		btnCadastrarPaciente.setBounds(264, 588, 243, 47);
		pnlA.add(btnCadastrarPaciente);

		JButton btnExcluirPaciente = new JButton("Excluir");
		btnExcluirPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirPaciente(funcionario);
			}
		});
		btnExcluirPaciente.setBounds(504, 588, 245, 47);
		pnlA.add(btnExcluirPaciente);

		JButton btnAlterarPaciente = new JButton("Alterar");
		btnAlterarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarPaciente(funcionario);
			}
		});
		btnAlterarPaciente.setBounds(746, 588, 246, 47);
		pnlA.add(btnAlterarPaciente);
		
		JButton btnAplicarVacina = new JButton("Aplicar Vacina");
		btnAplicarVacina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarVacina(funcionario);
			}
		});
		
		ImageIcon AplicarVacina = new ImageIcon(AWAdministrador.class.getResource("/imagens/aplicarVacina.png"));
		Image AplicarVacinaFinal = AplicarVacina.getImage();
		AplicarVacinaFinal = AplicarVacinaFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnAplicarVacina.setIcon(new ImageIcon(AplicarVacinaFinal));

		AplicarVacinaFinal = AplicarVacinaFinal.getScaledInstance(86, 86, Image.SCALE_SMOOTH);
		btnAplicarVacina.setRolloverIcon(new ImageIcon(AplicarVacinaFinal));

		AplicarVacinaFinal = AplicarVacinaFinal.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		btnAplicarVacina.setPressedIcon(new ImageIcon(AplicarVacinaFinal));

		btnAplicarVacina.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAplicarVacina.setVerticalAlignment(SwingConstants.BOTTOM);
		btnAplicarVacina.setIconTextGap(-3);
		btnAplicarVacina.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAplicarVacina.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAplicarVacina.setContentAreaFilled(false);
		btnAplicarVacina.setBorderPainted(false);
		btnAplicarVacina.setBorder(null);
		
		btnAplicarVacina.setBounds(673, 42, 126, 103);
		pnlA.add(btnAplicarVacina);
		pnlPai.add(pnlB, "telaDois");
		pnlPai.add(pnlC, "telaTres");
		pnlPai.add(pnlD, "telaQuatro");

		frmFuncionario.setBackground(SystemColor.darkGray);
		frmFuncionario.getContentPane().setLayout(null);

		JButton btnPacientes = new JButton("Pacientes");
		btnPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)(pnlPai.getLayout());
				c.show(pnlPai, "telaUm");
			}
		});

		btnPacientes.setToolTipText("Gerenciar pacientes");
		btnPacientes.setBounds(104, 20, 109, 127);

		ImageIcon pacienteMedio = new ImageIcon(AWAdministrador.class.getResource("/imagens/pacienteMedio.png"));
		Image pacienteFinal = pacienteMedio.getImage();
		btnPacientes.setIcon(new ImageIcon(pacienteFinal));

		ImageIcon pacienteGrande = new ImageIcon(AWAdministrador.class.getResource("/imagens/pacienteGrande.png"));
		btnPacientes.setRolloverIcon(new ImageIcon(pacienteGrande.getImage()));

		ImageIcon pacientePequeno = new ImageIcon(AWAdministrador.class.getResource("/imagens/pacientePequeno.png"));
		btnPacientes.setPressedIcon(new ImageIcon(pacientePequeno.getImage()));

		btnPacientes.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPacientes.setVerticalAlignment(SwingConstants.BOTTOM);
		btnPacientes.setIconTextGap(-3);
		btnPacientes.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPacientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPacientes.setContentAreaFilled(false);
		btnPacientes.setBorderPainted(false);
		btnPacientes.setBorder(null);

		frmFuncionario.getContentPane().add(btnPacientes);

		JButton btnVacinas = new JButton("Vacinas");
		btnVacinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)(pnlPai.getLayout());
				c.show(pnlPai, "telaDois");
			}
		});
		btnVacinas.setToolTipText("Gerenciar funcionários");
		btnVacinas.setBounds(104, 159, 109, 127);

		ImageIcon vacina = new ImageIcon(AWAdministrador.class.getResource("/imagens/vacinaAdministrador.png"));
		Image vacinaFinal = vacina.getImage();
		vacinaFinal = vacinaFinal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		btnVacinas.setIcon(new ImageIcon(vacinaFinal));

		vacinaFinal = vacinaFinal.getScaledInstance(108, 108, Image.SCALE_SMOOTH);
		btnVacinas.setRolloverIcon(new ImageIcon(vacinaFinal));

		vacinaFinal = vacinaFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnVacinas.setPressedIcon(new ImageIcon(vacinaFinal));

		btnVacinas.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVacinas.setVerticalAlignment(SwingConstants.BOTTOM);
		btnVacinas.setIconTextGap(-3);
		btnVacinas.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVacinas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVacinas.setContentAreaFilled(false);
		btnVacinas.setBorderPainted(false);
		btnVacinas.setBorder(null);

		frmFuncionario.getContentPane().add(btnVacinas);

		JButton btnEstoque = new JButton("Estoque");
		btnEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)(pnlPai.getLayout());
				c.show(pnlPai, "telaTres");
			}
		});
		btnEstoque.setToolTipText("Gerenciar distribuidores");
		btnEstoque.setBounds(104, 298, 109, 127);

		ImageIcon estoqueIcon = new ImageIcon(AWAdministrador.class.getResource("/imagens/estoque.png"));
		Image estoqueIconFinal = estoqueIcon.getImage();
		estoqueIconFinal = estoqueIconFinal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		btnEstoque.setIcon(new ImageIcon(estoqueIconFinal));

		estoqueIconFinal = estoqueIconFinal.getScaledInstance(108, 108, Image.SCALE_SMOOTH);
		btnEstoque.setRolloverIcon(new ImageIcon(estoqueIconFinal));

		estoqueIconFinal = estoqueIconFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnEstoque.setPressedIcon(new ImageIcon(estoqueIconFinal));

		btnEstoque.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEstoque.setVerticalAlignment(SwingConstants.BOTTOM);
		btnEstoque.setIconTextGap(-3);
		btnEstoque.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEstoque.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEstoque.setContentAreaFilled(false);
		btnEstoque.setBorderPainted(false);
		btnEstoque.setBorder(null);

		frmFuncionario.getContentPane().add(btnEstoque);

		JButton btnRelatorio = new JButton("Relat\u00F3rio");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c = (CardLayout)(pnlPai.getLayout());
				c.show(pnlPai, "telaQuatro");
			}
		});
		btnRelatorio.setBounds(104, 437, 109, 127);

		ImageIcon configuracoes = new ImageIcon(AWAdministrador.class.getResource("/imagens/relatorio.png"));
		Image configuracoesFinal = configuracoes.getImage();
		configuracoesFinal = configuracoesFinal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		btnRelatorio.setIcon(new ImageIcon(configuracoesFinal));

		configuracoesFinal = configuracoesFinal.getScaledInstance(108, 108, Image.SCALE_SMOOTH);
		btnRelatorio.setRolloverIcon(new ImageIcon(configuracoesFinal));

		configuracoesFinal = configuracoesFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnRelatorio.setPressedIcon(new ImageIcon(configuracoesFinal));

		btnRelatorio.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRelatorio.setVerticalAlignment(SwingConstants.BOTTOM);
		btnRelatorio.setIconTextGap(-3);
		btnRelatorio.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setContentAreaFilled(false);
		btnRelatorio.setBorderPainted(false);
		btnRelatorio.setBorder(null);

		frmFuncionario.getContentPane().add(btnRelatorio);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair(usuarioLogado);
			}
		});
		btnSair.setToolTipText("Sair/Logout");
		btnSair.setBounds(104, 571, 109, 122);

		ImageIcon sair = new ImageIcon(AWAdministrador.class.getResource("/imagens/sair.png"));
		Image sairFinal = sair.getImage();
		sairFinal = sairFinal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		btnSair.setIcon(new ImageIcon(sairFinal));

		sairFinal = sairFinal.getScaledInstance(104, 104, Image.SCALE_SMOOTH);
		btnSair.setRolloverIcon(new ImageIcon(sairFinal));

		sairFinal = sairFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnSair.setPressedIcon(new ImageIcon(sairFinal));

		btnSair.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSair.setVerticalAlignment(SwingConstants.BOTTOM);
		btnSair.setIconTextGap(-3);
		btnSair.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSair.setContentAreaFilled(false);
		btnSair.setBorderPainted(false);
		btnSair.setBorder(null);

		frmFuncionario.getContentPane().add(btnSair);
		frmFuncionario.getContentPane().add(pnlPai);

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
		SwingUtilities.updateComponentTreeUI(frmFuncionario);

	}
	
	public void consultarPaciente() {
		if(jtbPacientes.getSelectedRowCount() == 1) {
			
			Paciente umPaciente = new Paciente();
			
			PacienteTableModel model = (PacienteTableModel) jtbPacientes.getModel();
			
			umPaciente = model.getPaciente(jtbPacientes.getSelectedRow());
			
			try {
				JDConsultarPaciente dialog = new JDConsultarPaciente(pacienteDAO.consultar(umPaciente));
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "Não foi possível abrir a janela!\n"
						+ "Erro insesperado causado por: \n" + erro, "Erro ao abrir janela", JOptionPane.ERROR_MESSAGE);
			}
			
		}else{
			JOptionPane.showMessageDialog(null, "Selecione um paciente!", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void cadastrarPaciente(Funcionario funcionario) {
		try {
			JDCadastrarPaciente dialog = new JDCadastrarPaciente(funcionario);
			dialog.setModal(true);
			dialog.setLocationRelativeTo(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}catch(Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir janela!\nERRO: " + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void excluirPaciente(Funcionario funcionario) {
		
		if(jtbPacientes.getSelectedRowCount() == 1){
			Paciente umPaciente = new Paciente();

			PacienteTableModel model = (PacienteTableModel) jtbPacientes.getModel();

			umPaciente = model.getPaciente(jtbPacientes.getSelectedRow());

			Object[] botoes = {"Sim", "Não"};
			String senhaDigitada = "";

			int opcao = JOptionPane.showOptionDialog(frmFuncionario, "Deseja realmente excluir este paciente?", "Excluir", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoes, botoes[1]);

			if(opcao == JOptionPane.YES_OPTION){

				jpfSenhaConfirmacao = new JPasswordField();
				jpfSenhaConfirmacao.setBackground(new Color(255, 255, 255));
				jpfSenhaConfirmacao.setEditable(true);
				jpfSenhaConfirmacao.setBounds(412, 213, 200, 26);
				jpfSenhaConfirmacao.setBounds(0, 0, 200, 30);
				jpfSenhaConfirmacao.setVisible(true);
				jpfSenhaConfirmacao.setFont(new Font("Arial", Font.PLAIN, 11));
				jpfSenhaConfirmacao.requestFocus();

				JLabel lblSenhaa = new JLabel("Senha");
				lblSenhaa.setFont(new Font("Arial", Font.BOLD, 12));

				botoes[0] = "Excluir";
				botoes[1] = "Cancelar";

				opcao = JOptionPane.showOptionDialog(frmFuncionario, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[1]);

				senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

				if(opcao == JOptionPane.OK_OPTION){

					if(funcionario.getSenha().equals(senhaDigitada)){

						pacienteDAO.excluir(umPaciente);

					}else{
						JOptionPane.showMessageDialog(null, "Senha inválida! Verifique sua senha e \n"
								+ "tente novamente.", "Senha incorreta", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}

		}else{
			JOptionPane.showMessageDialog(null, "Selecione um paciente!",
					"Aviso", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void alterarPaciente(Funcionario funcionario) {
		
		if(jtbPacientes.getSelectedRowCount() == 1){

			Paciente umPaciente = new Paciente();

			PacienteTableModel model = (PacienteTableModel) jtbPacientes.getModel();

			umPaciente = model.getPaciente(jtbPacientes.getSelectedRow());

			try {
				JDAlterarPaciente dialog = new JDAlterarPaciente(pacienteDAO.consultar(umPaciente), funcionario);
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao carregar dados!\nERRO: " + erro,
						"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
			}


		}else{
			JOptionPane.showMessageDialog(null, "Selecione um funcionário!", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void aplicarVacina(Funcionario aplicador){
		if(jtbPacientes.getSelectedRowCount() ==  1) {
			//Abrir tela para aplicar Vacina
			
			try {
				Paciente pacienteAplicar = new Paciente();
				
				PacienteTableModel model = (PacienteTableModel) jtbPacientes.getModel();
				
				pacienteAplicar = model.getPaciente(jtbPacientes.getSelectedRow());
				
				JDAplicarVacina dialog = new JDAplicarVacina(pacienteAplicar, aplicador);
				dialog.setModal(true);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}catch(Exception erro) {
				JOptionPane.showMessageDialog(null, "Não foi possível aplicar a vacina!\nERRO: " + erro,
						"Erro ao verificar dados!", JOptionPane.ERROR_MESSAGE);
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "Selecione um paciente", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void sair(Login usuarioLogado) {

		Object[] opcoes = {"Encerrar operações", "Fazer logout", "Cancelar"};

		int opcao = JOptionPane.showOptionDialog(frmFuncionario, "O que deseja fazer?", "Sair", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[2]);

		if(opcao == JOptionPane.YES_OPTION){
			loginDAO.atualizarLogin(usuarioLogado, "dataLogoutTempoLogado");
			System.exit(0);
		}else if(opcao == JOptionPane.NO_OPTION){

			loginDAO.atualizarLogin(usuarioLogado, "dataLogoutTempoLogado");

			frmFuncionario.dispose();

			Connection con = null;

			try{
				con = ConnectionFactory.getConnection();
				//Fecha a conexão
				con.close();
			}catch(Exception erro){
				JOptionPane.showMessageDialog(null, "Erro imprevisto não "
						+ "capturado! \nERRO: \n" + erro, "Erro não capturado!", JOptionPane.ERROR_MESSAGE);
			}

			JFLogin dialog = new JFLogin();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException erro) {
				JOptionPane.showMessageDialog(null, "Erro ao reabrir!", "Erro!", JOptionPane.ERROR_MESSAGE);
			}

			try {
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}if(opcao == JOptionPane.CANCEL_OPTION){

		}

	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
