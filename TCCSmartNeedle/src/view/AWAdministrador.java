package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;

import model.Administrador;
import model.Distribuidor;
import model.Funcionario;
import model.FuncionarioTableModel;
import model.Login;
import model.Vacina;
import model.DAO.AdministradorDAO;
import model.DAO.ConnectionFactory;
import model.DAO.DistribuidorDAO;
import model.DAO.FuncionarioDAO;
import model.DAO.LoginDAO;
import model.DAO.VacinaDAO;

public class AWAdministrador {

	//Criação dos objetos
	Administrador administrador = new Administrador();
	AdministradorDAO administradorDAO = new AdministradorDAO();

	Login login = new Login();
	LoginDAO loginDAO = new LoginDAO();

	Funcionario funcionario = new Funcionario();
	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

	Vacina vacina = new Vacina();
	VacinaDAO vacinaDAO = new VacinaDAO();

	Distribuidor distribuidor = new Distribuidor();
	DistribuidorDAO distribuidorDAO = new DistribuidorDAO();

	public JFrame frmAdministrador;
	
	private JTable jtbFuncionarios;
	private JTextField jtfFiltrar;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPasswordField jpfSenhaAntiga;
	private JPasswordField jpfNovaSenha;
	private JPasswordField jpfConfirmar;
	private JPasswordField jpfSenhaConfirmacao;
	private JLabel lblStatusSenha;
	private JButton btnAlterarSenha;
	private JList jlsVacinas;
	private JList jlsDistribuidores;
	private JLabel lblDisponivel;
	private JLabel lblSolicitacoes;
	private JLabel lblFalta;
	private JLabel lblDescricao;

	/**
	 * Create the application.
	 */
	public AWAdministrador(Administrador administrador, Login login) {
		initialize(administrador, login);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	int controleListaVacinas = 0, controleListaDistribuidores = 0;
	private void initialize(Administrador administrador, final Login usuarioLogado) {
		frmAdministrador = new JFrame();
		frmAdministrador.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				//jtbFuncionarios
				FuncionarioTableModel model = new FuncionarioTableModel(funcionarioDAO.listarFuncionarios("", ""));
				model.fireTableDataChanged();
				jtbFuncionarios.setModel(model);

				//jlsVacinas
				DefaultListModel modeloVacinas = new DefaultListModel();

				for(String p : vacinaDAO.listarNomesVacinas()){
					modeloVacinas.addElement(p);
				}
				
				jlsVacinas.setModel(modeloVacinas);
				jlsVacinas.setSelectedIndex(controleListaVacinas);
				
				//jlsDistribuidores
				DefaultListModel modeloDistribuidores = new DefaultListModel();

				for(String p : distribuidorDAO.listarNomesDistribuidores()){
					modeloDistribuidores.addElement(p);
				}
				
				jlsDistribuidores.setModel(modeloDistribuidores);
				jlsDistribuidores.setSelectedIndex(controleListaDistribuidores);
			}
			public void windowLostFocus(WindowEvent arg0) {
				//jtbFuncionarios
				FuncionarioTableModel model = new FuncionarioTableModel(funcionarioDAO.listarFuncionarios("", ""));
				model.fireTableDataChanged();
				jtbFuncionarios.setModel(model);

				//jlsVacinas
				DefaultListModel modeloVacinas = new DefaultListModel();

				for(String p : vacinaDAO.listarNomesVacinas()){
					modeloVacinas.addElement(p);
				}
				jlsVacinas.setModel(modeloVacinas);
				
				//jlsDistribuidores
				DefaultListModel modeloDistribuidores = new DefaultListModel();
				
				for(String p : distribuidorDAO.listarNomesDistribuidores()) {
					modeloDistribuidores.addElement(p);
				}
				jlsDistribuidores.setModel(modeloDistribuidores);
			}
		});

		frmAdministrador.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				sair(usuarioLogado);
			}
			@Override
			public void windowOpened(WindowEvent e) {
				jlsVacinas.setSelectedIndex(0);
				jlsDistribuidores.setSelectedIndex(0);
			}
		});

		//Definir tamanho da tela como padrão (proporcional ao visor), impedir que o 
		//tamanho seja alterado e desativar o botão Maximizar
		Insets in = Toolkit.getDefaultToolkit().getScreenInsets(frmAdministrador.getGraphicsConfiguration());
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = d.width - (in.left + in.top);
		int height = d.height - (in.top + in.bottom);
		frmAdministrador.setLocationRelativeTo(null);
		frmAdministrador.setSize(width, height);
		frmAdministrador.setResizable(false);
		frmAdministrador.addComponentListener(new ComponentAdapter() {

			public void componentMoved(ComponentEvent evt){
				frmAdministrador.setEnabled(false);
				frmAdministrador.setEnabled(true);
			}

		});

		frmAdministrador.setTitle("Administrador - " + administrador.getLogin());
		frmAdministrador.setIconImage(Toolkit.getDefaultToolkit().getImage(AWAdministrador.class.getResource("/imagens/icone.png")));
		frmAdministrador.setBounds(0, 0, width, height);
		frmAdministrador.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmAdministrador.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		final JPanel pnlPai = new JPanel();	

		pnlPai.setBounds(321, 20, 1019, 664);

		JButton btnVacinas = new JButton("Vacinas");
		btnVacinas.setToolTipText("Gerenciar vacinas");
		btnVacinas.setBounds(104, 20, 109, 127);

		ImageIcon vacinaa = new ImageIcon(AWAdministrador.class.getResource("/imagens/vacinaAdministrador.png"));
		Image vacinaFinal = vacinaa.getImage();
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



		btnVacinas.setBackground(new Color(153, 204, 204));
		btnVacinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				CardLayout c = (CardLayout)(pnlPai.getLayout());
				c.show(pnlPai, "telaUm");

			}
		});

		JButton btnFuncionarios = new JButton("Funcion\u00E1rios");
		btnFuncionarios.setToolTipText("Gerenciar funcionários");
		btnFuncionarios.setBounds(104, 159, 109, 127);

		ImageIcon funcionario = new ImageIcon(AWAdministrador.class.getResource("/imagens/funcionarioAdministrador.png"));
		Image funcionarioFinal = funcionario.getImage();
		funcionarioFinal = funcionarioFinal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		btnFuncionarios.setIcon(new ImageIcon(funcionarioFinal));

		funcionarioFinal = funcionarioFinal.getScaledInstance(108, 108, Image.SCALE_SMOOTH);
		btnFuncionarios.setRolloverIcon(new ImageIcon(funcionarioFinal));

		funcionarioFinal = funcionarioFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnFuncionarios.setPressedIcon(new ImageIcon(funcionarioFinal));

		btnFuncionarios.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnFuncionarios.setVerticalAlignment(SwingConstants.BOTTOM);
		btnFuncionarios.setIconTextGap(-3);
		btnFuncionarios.setHorizontalTextPosition(SwingConstants.CENTER);
		btnFuncionarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFuncionarios.setContentAreaFilled(false);
		btnFuncionarios.setBorderPainted(false);
		btnFuncionarios.setBorder(null);

		btnFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CardLayout c = (CardLayout)(pnlPai.getLayout());
				c.show(pnlPai, "telaDois");

			}
		});

		JButton btnDistribuidores = new JButton("Distribuidores");
		btnDistribuidores.setToolTipText("Gerenciar distribuidores");
		btnDistribuidores.setBounds(104, 298, 109, 127);

		ImageIcon distribuidores = new ImageIcon(AWAdministrador.class.getResource("/imagens/distribuidoresAdministrador.png"));
		Image distribuidoresFinal = distribuidores.getImage();
		distribuidoresFinal = distribuidoresFinal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		btnDistribuidores.setIcon(new ImageIcon(distribuidoresFinal));

		distribuidoresFinal = distribuidoresFinal.getScaledInstance(108, 108, Image.SCALE_SMOOTH);
		btnDistribuidores.setRolloverIcon(new ImageIcon(distribuidoresFinal));

		distribuidoresFinal = distribuidoresFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnDistribuidores.setPressedIcon(new ImageIcon(distribuidoresFinal));

		btnDistribuidores.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDistribuidores.setVerticalAlignment(SwingConstants.BOTTOM);
		btnDistribuidores.setIconTextGap(-3);
		btnDistribuidores.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDistribuidores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDistribuidores.setContentAreaFilled(false);
		btnDistribuidores.setBorderPainted(false);
		btnDistribuidores.setBorder(null);

		btnDistribuidores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CardLayout c = (CardLayout)(pnlPai.getLayout());
				c.show(pnlPai, "telaTres");

			}
		});

		JButton btnConfiguracoes = new JButton("Configura\u00E7\u00F5es do software");
		btnConfiguracoes.setBounds(104, 437, 109, 127);

		ImageIcon configuracoes = new ImageIcon(AWAdministrador.class.getResource("/imagens/configuracoesAdministrador.png"));
		Image configuracoesFinal = configuracoes.getImage();
		configuracoesFinal = configuracoesFinal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		btnConfiguracoes.setIcon(new ImageIcon(configuracoesFinal));

		configuracoesFinal = configuracoesFinal.getScaledInstance(108, 108, Image.SCALE_SMOOTH);
		btnConfiguracoes.setRolloverIcon(new ImageIcon(configuracoesFinal));

		configuracoesFinal = configuracoesFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnConfiguracoes.setPressedIcon(new ImageIcon(configuracoesFinal));

		btnConfiguracoes.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnConfiguracoes.setVerticalAlignment(SwingConstants.BOTTOM);
		btnConfiguracoes.setIconTextGap(-3);
		btnConfiguracoes.setHorizontalTextPosition(SwingConstants.CENTER);
		btnConfiguracoes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfiguracoes.setContentAreaFilled(false);
		btnConfiguracoes.setBorderPainted(false);
		btnConfiguracoes.setBorder(null);

		btnConfiguracoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CardLayout c = (CardLayout)(pnlPai.getLayout());
				c.show(pnlPai, "telaQuatro");

				//O botão de configurações chamará o layout de configurações, o qual terá opções como alterar a senha do 
				//administrador que está logado, e futuramente poderá ser incluídas configurações referentes aos envios de emails
				//e envios de sms e outras opções por exemplo de entrar em contato com os desenvolvedores para dúvidas e assistência
				//técnica etc.

				jpfSenhaAntiga.setText("");
				jpfNovaSenha.setText("");
				jpfConfirmar.setText("");
				lblStatusSenha.setText("");
				btnAlterarSenha.setEnabled(false);
			}
		});
		pnlPai.setLayout(new CardLayout(0, 0));

		JPanel pnlA = new JPanel();

		TitledBorder bordaA = new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Vacinas", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 128, 128));
		bordaA.setTitleFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlA.setBorder(bordaA);

		pnlA.setBackground(new Color(214, 217, 223));
		pnlPai.add(pnlA, "telaUm");
		
		JScrollPane jspVacinas = new JScrollPane();
		jspVacinas.setBounds(53, 92, 251, 437);
		jspVacinas.setBorder(new TitledBorder(null, "Vacinas", TitledBorder.LEFT, TitledBorder.TOP, null, null));

		JButton btnCadastrarVacina = new JButton("Cadastrar");
		btnCadastrarVacina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				cadastrarVacina(administrador);

			}
		});
		btnCadastrarVacina.setBounds(264, 588, 243, 47);

		JButton btnAlterarVacina = new JButton("Alterar");
		btnAlterarVacina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				alterarVacina(administrador);

			}
		});
		btnAlterarVacina.setBounds(746, 588, 246, 47);

		JButton btnConsultarVacina = new JButton("Consultar");
		btnConsultarVacina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(jlsVacinas.getSelectedIndex() == -1){//getSelectedIndex() retorna -1 se não houver nenhuma opção selecionada
					JOptionPane.showMessageDialog(null, "Selecione uma Vacina para consulta!", "Consultar", JOptionPane.WARNING_MESSAGE);
				}else{

					try{
						JDConsultarVacina consultarVacina = new JDConsultarVacina(vacina);
						consultarVacina.setLocationRelativeTo(null);
						consultarVacina.setModal(true);
						consultarVacina.setVisible(true);
					}catch(Exception erro){
						JOptionPane.showMessageDialog(null, "Erro desconhecido ao abrir janela! \nERRO: " + erro,
								"Erro ao abrir janela!", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnConsultarVacina.setBounds(24, 588, 243, 47);

		JButton btnExcluirVacina = new JButton("Excluir");
		btnExcluirVacina.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnExcluirVacina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				excluirVacina(administrador);

			}
		});
		btnExcluirVacina.setBounds(504, 588, 245, 47);

		JLabel lblVacinasDisponveis = new JLabel("Dispon\u00EDveis:");
		lblVacinasDisponveis.setBounds(492, 59, 102, 40);
		lblVacinasDisponveis.setFont(new Font("Arial", Font.BOLD, 16));

		JLabel lblVacinasEmFalta = new JLabel("Em falta:");
		lblVacinasEmFalta.setBounds(733, 184, 102, 40);
		lblVacinasEmFalta.setFont(new Font("Arial", Font.BOLD, 16));

		lblDisponivel = new JLabel("");
		lblDisponivel.setBounds(606, 59, 90, 40);
		lblDisponivel.setFont(new Font("Arial", Font.PLAIN, 26));
		lblDisponivel.setForeground(new Color(50, 205, 50));

		lblSolicitacoes = new JLabel("");
		lblSolicitacoes.setBounds(847, 59, 90, 40);
		lblSolicitacoes.setFont(new Font("Arial", Font.PLAIN, 26));
		lblSolicitacoes.setForeground(Color.RED);

		jlsVacinas = new JList();
		jlsVacinas.setSelectedIndex(0);
		jlsVacinas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {

				if (evt.getClickCount() == 2) {
					if(jlsVacinas.getSelectedIndex() == -1){//getSelectedIndex() retorna -1 se não houver nenhuma opção selecionada
						JOptionPane.showMessageDialog(null, "Selecione uma Vacina para consulta!", "Consultar", JOptionPane.WARNING_MESSAGE);
					}else{

						try{
							JDConsultarVacina consultarVacina = new JDConsultarVacina(vacina);
							consultarVacina.setLocationRelativeTo(null);
							consultarVacina.setModal(true);
							consultarVacina.setVisible(true);
						}catch(Exception erro){
							JOptionPane.showMessageDialog(null, "Erro desconhecido ao abrir janela! \nERRO: " + erro,
									"Erro ao abrir janela!", JOptionPane.ERROR_MESSAGE);
						}
					}
				}

			}
		});
		jlsVacinas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				Vacina vacinaSelecionada = new Vacina();
				vacinaSelecionada = vacinaDAO.consultar(jlsVacinas.getSelectedValue().toString());
				vacina = vacinaSelecionada;
				
				lblDisponivel.setText(String.valueOf(vacinaSelecionada.getDisponivel()));
				lblSolicitacoes.setText(String.valueOf(vacinaSelecionada.getSolicitacoes()));

				int faltaVacinas = 0;
				faltaVacinas = vacinaSelecionada.getDisponivel() - vacinaSelecionada.getSolicitacoes();

				if(faltaVacinas < 0){
					faltaVacinas = faltaVacinas * -1;
					lblVacinasEmFalta.setText("Em falta:");
					lblFalta.setForeground(Color.RED);
				}else{
					lblVacinasEmFalta.setText("Restantes:");
					lblFalta.setForeground(new Color(50, 205, 50));
				}

				lblFalta.setText(String.valueOf(faltaVacinas));
				String descricao = vacinaSelecionada.getDescricao();

				if(descricao.length() > 140) {
					descricao = descricao.substring(0, 140) + "...";
				}

				lblDescricao.setText("<html><p align=\"left\">" + descricao + "</p></html>");
				
				controleListaVacinas = jlsVacinas.getSelectedIndex();
				
			}
		});
		jlsVacinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListModel modelo = new DefaultListModel();

		for(String p : vacinaDAO.listarNomesVacinas()){
			modelo.addElement(p);
			jlsVacinas.setModel(modelo);
		}

		jspVacinas.setViewportView(jlsVacinas);
		jlsVacinas.setBorder(null);
		pnlA.setLayout(null);
		pnlA.add(btnCadastrarVacina);
		pnlA.add(btnAlterarVacina);
		pnlA.add(btnConsultarVacina);
		pnlA.add(btnExcluirVacina);
		pnlA.add(jspVacinas);
		pnlA.add(lblVacinasDisponveis);
		pnlA.add(lblDisponivel);
		pnlA.add(lblSolicitacoes);
		pnlA.add(lblVacinasEmFalta);

		JLabel lblSolicitacoesVacinas = new JLabel("Solicita\u00E7\u00F5es:");
		lblSolicitacoesVacinas.setFont(new Font("Arial", Font.BOLD, 16));
		lblSolicitacoesVacinas.setBounds(733, 59, 102, 40);
		pnlA.add(lblSolicitacoesVacinas);

		lblFalta = new JLabel("");
		lblFalta.setForeground(new Color(50, 205, 50));
		lblFalta.setFont(new Font("Arial", Font.PLAIN, 26));
		lblFalta.setBounds(847, 184, 90, 40);
		pnlA.add(lblFalta);

		lblDescricao = new JLabel("");
		lblDescricao.setBorder(new TitledBorder(null, "Descri\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblDescricao.setBackground(Color.WHITE);
		lblDescricao.setBounds(316, 184, 278, 114);
		pnlA.add(lblDescricao);

		JButton btnSolicitarVacina = new JButton("Solicitar vacina");
		btnSolicitarVacina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(jlsVacinas.getSelectedIndex() != -1) {
					
					String vacinaSolicitar = jlsVacinas.getSelectedValue().toString(),
					distribuidorSelecionado = "";
					
					JList jlsconferirDistribuidor = new JList();
					JScrollPane jspConferirDistribuidores = new JScrollPane();
					jspConferirDistribuidores.setBounds(0, 0, 200, 100);
					jspConferirDistribuidores.setBorder(new TitledBorder(null, "Distribuidores", TitledBorder.CENTER, TitledBorder.TOP, null, null));
					jspConferirDistribuidores.setViewportView(jlsconferirDistribuidor);
					
					//jlsDistribuidores
					DefaultListModel modeloConferir = new DefaultListModel();

					for(String p : distribuidorDAO.listarNomesDistribuidores()){
						modeloConferir.addElement(p);
					}
					
					jlsconferirDistribuidor.setModel(modeloConferir);
					
					Object[] botoes = {"Selecionar", "Cancelar"};
					
					JLabel lblSelecionarDistribuidor = new JLabel("<html>Selecione o distribuidor para solicitar a vacina:<br><b>"
							+ vacinaSolicitar + "</b></html>");
					lblSelecionarDistribuidor.setFont(new Font("Arial", Font.BOLD, 14));
					
					int op = JOptionPane.showOptionDialog(null, new Object[]{lblSelecionarDistribuidor, jspConferirDistribuidores}, 
							"Solicitar vacina",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,botoes, botoes[0]);
					
					distribuidorSelecionado = jlsconferirDistribuidor.getSelectedValue().toString();
					
					if(op == JOptionPane.OK_OPTION) {
						
						//Solicitar vacina selecionada por email
						Distribuidor distribuidorEscolhido = distribuidorDAO.consultar(distribuidorSelecionado);
						Vacina vacinaSolicitada = vacinaDAO.consultar(vacinaSolicitar);
						
						try {
							JDEnviarEmail dialog = new JDEnviarEmail(vacinaSolicitada, distribuidorEscolhido, administrador);
							dialog.setModal(true);
							dialog.setLocationRelativeTo(null);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
							dialog.setLocationRelativeTo(null);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Selecione uma Vacina!",
							"Aviso", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnSolicitarVacina.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnSolicitarVacina.setBounds(726, 264, 158, 170);

		//Média
		ImageIcon solicitarVacina = new ImageIcon(AWAdministrador.class.getResource("/imagens/solicitarVacinaMedia.png"));
		Image solicitarVacinaFinal = solicitarVacina.getImage();
		btnSolicitarVacina.setIcon(new ImageIcon(solicitarVacinaFinal));

		//Grande
		solicitarVacina = new ImageIcon(AWAdministrador.class.getResource("/imagens/solicitarVacinaGrande.png"));
		solicitarVacinaFinal = solicitarVacina.getImage();
		btnSolicitarVacina.setRolloverIcon(new ImageIcon(solicitarVacinaFinal));

		//Pequena
		solicitarVacina = new ImageIcon(AWAdministrador.class.getResource("/imagens/solicitarVacinaPequena.png"));
		solicitarVacinaFinal = solicitarVacina.getImage();
		btnSolicitarVacina.setPressedIcon(new ImageIcon(solicitarVacinaFinal));

		btnSolicitarVacina.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSolicitarVacina.setVerticalAlignment(SwingConstants.BOTTOM);
		btnSolicitarVacina.setIconTextGap(2);
		btnSolicitarVacina.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSolicitarVacina.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSolicitarVacina.setContentAreaFilled(false);
		btnSolicitarVacina.setBorderPainted(false);
		btnSolicitarVacina.setBorder(null);

		pnlA.add(btnSolicitarVacina);

		JPanel pnlB = new JPanel();

		TitledBorder bordaB = new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Funcionarios", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 99, 71));
		bordaB.setTitleFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlB.setBorder(bordaB);
		
		pnlB.setBackground(new Color(214, 217, 223));
		pnlPai.add(pnlB, "telaDois");
		pnlB.setLayout(null);

		JScrollPane jspFuncionarios = new JScrollPane();

		TitledBorder borda = new TitledBorder(null, "Quadro de Funcion\u00E1rios", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 139, 139));
		borda.setTitleFont(new Font("Segoe Print", Font.PLAIN, 20));
		jspFuncionarios.setBorder(borda);

		jspFuncionarios.setBounds(24, 140, 968, 436);
		pnlB.add(jspFuncionarios);

		jtbFuncionarios = new JTable();
		jtbFuncionarios.addMouseListener(new MouseAdapter() {  
			@Override  
			public void mousePressed(MouseEvent e) {    
				if (e.getButton() == MouseEvent.BUTTON3) { 

					int col = jtbFuncionarios.columnAtPoint(e.getPoint());  
					int row = jtbFuncionarios.rowAtPoint(e.getPoint());  
					if (col != -1 && row != -1) {  
						jtbFuncionarios.setColumnSelectionInterval(col, col);  
						jtbFuncionarios.setRowSelectionInterval(row, row);  
					}  
				}  
			}  
		});

		jtbFuncionarios.setShowVerticalLines(true);
		jtbFuncionarios.getTableHeader().setReorderingAllowed(false);
		jtbFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jspFuncionarios.setViewportView(jtbFuncionarios);

		//Criação do model FuncionarioTableModel
		final FuncionarioTableModel model = new FuncionarioTableModel(funcionarioDAO.listarFuncionarios("", ""));

		//Atribuição do model a tabela
		jtbFuncionarios.setModel(model);

		final JPopupMenu jpmOperacoes = new JPopupMenu();
		addPopup(jtbFuncionarios, jpmOperacoes);

		JMenuItem jmiConsultar = new JMenuItem("Consultar");
		jmiConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				consultarFuncionario();

			}
		});
		jpmOperacoes.add(jmiConsultar);

		JSeparator separator = new JSeparator();
		jpmOperacoes.add(separator);

		JMenuItem jmiCadastrar = new JMenuItem("Cadastrar");
		jmiCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				cadastrarFuncionario(administrador);

			}
		});
		jpmOperacoes.add(jmiCadastrar);

		JSeparator separator_1 = new JSeparator();
		jpmOperacoes.add(separator_1);

		JMenuItem jmiExcluir = new JMenuItem("Excluir");
		jmiExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				excluirFuncionario(administrador);

			}
		});
		jpmOperacoes.add(jmiExcluir);

		JSeparator separator_2 = new JSeparator();
		jpmOperacoes.add(separator_2);

		JMenuItem jmiAlterar = new JMenuItem("Alterar");
		jmiAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				alterarFuncionario(administrador);

			}
		});
		jpmOperacoes.add(jmiAlterar);

		JButton btnConsultarFuncionario = new JButton("Consultar");
		btnConsultarFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				consultarFuncionario();

			}
		});
		btnConsultarFuncionario.setBounds(24, 588, 243, 47);
		pnlB.add(btnConsultarFuncionario);

		JButton btnCadastrarFuncionario = new JButton("Cadastrar");
		btnCadastrarFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				cadastrarFuncionario(administrador);

			}
		});
		btnCadastrarFuncionario.setBounds(264, 588, 243, 47);
		pnlB.add(btnCadastrarFuncionario);

		JButton btnExcluirFuncionario = new JButton("Excluir");
		btnExcluirFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				excluirFuncionario(administrador);

			}
		});
		btnExcluirFuncionario.setBounds(504, 588, 245, 47);
		pnlB.add(btnExcluirFuncionario);

		JButton btnAlterarFuncionario = new JButton("Alterar");
		btnAlterarFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				alterarFuncionario(administrador);

			}
		});
		btnAlterarFuncionario.setBounds(746, 588, 246, 47);
		pnlB.add(btnAlterarFuncionario);

		JLabel lblFiltrarPor = new JLabel("Filtrar por: ");
		lblFiltrarPor.setBounds(24, 67, 72, 16);
		pnlB.add(lblFiltrarPor);

		JRadioButton jrbNome = new JRadioButton("Nome");
		jrbNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtfFiltrar.grabFocus();
			}
		});
		jrbNome.setSelected(true);
		buttonGroup.add(jrbNome);
		jrbNome.setBounds(108, 66, 72, 18);
		pnlB.add(jrbNome);

		JRadioButton jrbCpf = new JRadioButton("CPF");
		jrbCpf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtfFiltrar.grabFocus();
			}
		});
		buttonGroup.add(jrbCpf);
		jrbCpf.setBounds(192, 66, 72, 18);
		pnlB.add(jrbCpf);

		jtfFiltrar = new JTextField();
		jtfFiltrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {

				String opcaoFiltro = "";

				if(jrbNome.isSelected()){
					opcaoFiltro = "nome";
				}else if(jrbCpf.isSelected()){
					opcaoFiltro = "cpf";
				}

				//Filtra os registros apresentados de acordo com a inserção de dados no jtfFiltrar
				if(jtfFiltrar.getText().equals("")){

					model.setListaFuncionario(funcionarioDAO.listarFuncionarios("", ""));

				}else{

					model.setListaFuncionario(funcionarioDAO.listarFuncionarios(jtfFiltrar.getText(), opcaoFiltro));

				}
				jtbFuncionarios.setModel(model);

			}
		});
		jtfFiltrar.setBounds(24, 96, 483, 28);
		pnlB.add(jtfFiltrar);
		jtfFiltrar.setColumns(10);

		JButton btnOrdenanarPorNome = new JButton("Ordenanar por nome");
		btnOrdenanarPorNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.ordenarNome();
				jtbFuncionarios.setModel(model);

			}
		});
		btnOrdenanarPorNome.setBounds(618, 92, 181, 36);
		pnlB.add(btnOrdenanarPorNome);

		JButton btnOrdenarPorLogin = new JButton("Ordenar por login");
		btnOrdenarPorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				model.ordenarLogin();
				jtbFuncionarios.setModel(model);

			}
		});
		btnOrdenarPorLogin.setBounds(811, 92, 181, 36);
		pnlB.add(btnOrdenarPorLogin);

		JPanel pnlC = new JPanel();

		TitledBorder bordaC = new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Distribuidores", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(46, 139, 87));
		bordaC.setTitleFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlC.setBorder(bordaC);

		pnlC.setBackground(new Color(214, 217, 223));
		pnlPai.add(pnlC, "telaTres");
		pnlC.setLayout(null);

		JScrollPane jspDistribuidores = new JScrollPane();
		jspDistribuidores.setBorder(new TitledBorder(null, "Distribuidores", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		jspDistribuidores.setBounds(53, 92, 251, 437);
		pnlC.add(jspDistribuidores);

		jlsDistribuidores = new JList();
		jlsDistribuidores.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				Distribuidor distribuidorSelecionado = new Distribuidor();
				distribuidorSelecionado = distribuidorDAO.consultar(jlsDistribuidores.getSelectedValue().toString());
				distribuidor = distribuidorSelecionado;
				
				controleListaDistribuidores = jlsDistribuidores.getSelectedIndex();
			}
		});
		jlsDistribuidores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jspDistribuidores.setViewportView(jlsDistribuidores);
		jlsDistribuidores.setBorder(null);

		JButton btnContato = new JButton("Entre em contato");

		//Média
		ImageIcon emailMedio = new ImageIcon(AWAdministrador.class.getResource("/imagens/emailMedio.png"));
		Image emailMedioFinal = emailMedio.getImage();
		btnContato.setIcon(new ImageIcon(emailMedioFinal));
		
		//Grande
		ImageIcon emailGrande = new ImageIcon(AWAdministrador.class.getResource("/imagens/emailGrande.png"));
		Image emailGrandeFinal = emailGrande.getImage();
		btnContato.setRolloverIcon(new ImageIcon(emailGrandeFinal));
		
		//Pequena
		ImageIcon emailPequeno = new ImageIcon(AWAdministrador.class.getResource("/imagens/emailPequeno.png"));
		Image emailPequenoFinal = emailPequeno.getImage();
		btnContato.setPressedIcon(new ImageIcon(emailPequenoFinal));

		btnContato.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnContato.setVerticalAlignment(SwingConstants.BOTTOM);
		btnContato.setIconTextGap(-3);
		btnContato.setHorizontalTextPosition(SwingConstants.CENTER);
		btnContato.setContentAreaFilled(false);
		btnContato.setBorderPainted(false);
		btnContato.setBorder(null);

		btnContato.setToolTipText("<html><body bgcolor = 'white'><b><font size = '4' color = 'rgb(0, 138, 138)'>Solicite uma vacina não existente no sistema<br>ou entre em contato com o distribuidor.</font></b></body></html>");
		btnContato.setBounds(556, 75, 158, 145);
		pnlC.add(btnContato);
		
		JButton btnConsultarDistribuidor = new JButton("Consultar");
		btnConsultarDistribuidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				if(jlsDistribuidores.getSelectedIndex() == -1){//getSelectedIndex() retorna -1 se não houver nenhuma opção selecionada
					JOptionPane.showMessageDialog(null, "Selecione um distribuidor para consulta!", "Consultar", JOptionPane.WARNING_MESSAGE);
				}else{

					try {
						
						distribuidor = distribuidorDAO.consultar(jlsDistribuidores.getSelectedValue().toString());
								
						JDConsultarDistribuidor dialog = new JDConsultarDistribuidor(distribuidor);
						dialog.setModal(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		btnConsultarDistribuidor.setBounds(24, 588, 243, 47);
		pnlC.add(btnConsultarDistribuidor);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				excluirDistribuidor(administrador);
				
			}
		});
		btnExcluir.setBounds(504, 588, 245, 47);
		pnlC.add(btnExcluir);
		
		JButton btnCadastrarDistribuidor = new JButton("Cadastrar");
		btnCadastrarDistribuidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cadastrarDistribuidor(administrador);
			}
		});
		btnCadastrarDistribuidor.setBounds(264, 588, 243, 47);
		pnlC.add(btnCadastrarDistribuidor);
		
		JButton btnAlterarDistribuidor = new JButton("Alterar");
		btnAlterarDistribuidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				alterarDistribuidor(administrador);
				
			}
		});
		btnAlterarDistribuidor.setBounds(746, 588, 246, 47);
		pnlC.add(btnAlterarDistribuidor);

		JPanel pnlD = new JPanel();

		TitledBorder bordaD = new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Configurações", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 140, 0));
		bordaD.setTitleFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlD.setBorder(bordaD);

		pnlD.setBackground(new Color(214, 217, 223));
		pnlPai.add(pnlD, "telaQuatro");
		pnlD.setLayout(null);

		JLabel lblNewLabel = new JLabel("Alterar senha: ");
		lblNewLabel.setBounds(56, 62, 257, 41);
		lblNewLabel.setFont(new Font("Segoe Print", Font.BOLD, 34));
		pnlD.add(lblNewLabel);

		JLabel lblSenhaAntiga = new JLabel("Senha antiga: ");
		lblSenhaAntiga.setFont(new Font("Segoe Print", Font.BOLD, 20));
		lblSenhaAntiga.setBounds(56, 147, 158, 28);
		pnlD.add(lblSenhaAntiga);

		btnAlterarSenha = new JButton("Alterar");
		btnAlterarSenha.setEnabled(false);
		btnAlterarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String senhaAntiga = String.valueOf(jpfSenhaAntiga.getPassword()),
						novaSenha = String.valueOf(jpfNovaSenha.getPassword()),
						confirmarSenha = String.valueOf(jpfConfirmar.getPassword());

				if(senhaAntiga.equals(administrador.getSenha()) && novaSenha.equals(confirmarSenha)){

					administrador.setSenha(confirmarSenha.toString());
					administradorDAO.alterar(administrador, "Senha");

				}

			}
		});
		btnAlterarSenha.setBounds(56, 516, 158, 41);
		pnlD.add(btnAlterarSenha);

		jpfSenhaAntiga = new JPasswordField();
		jpfSenhaAntiga.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {

				conferirStatusSenha(administrador.getSenha());

			}
		});
		jpfSenhaAntiga.setMargin(new Insets(5, 0, 0, 0));
		jpfSenhaAntiga.setFont(new Font("SansSerif", Font.BOLD, 20));
		jpfSenhaAntiga.setBounds(56, 187, 257, 35);
		pnlD.add(jpfSenhaAntiga);

		JLabel lblNovaSenha = new JLabel("Nova senha: ");
		lblNovaSenha.setFont(new Font("Segoe Print", Font.BOLD, 20));
		lblNovaSenha.setBounds(56, 249, 158, 28);
		pnlD.add(lblNovaSenha);

		lblStatusSenha = new JLabel("");
		lblStatusSenha.setFont(new Font("Arial", Font.BOLD, 14));
		lblStatusSenha.setForeground(Color.RED);
		lblStatusSenha.setBounds(56, 447, 315, 60);
		pnlD.add(lblStatusSenha);

		jpfNovaSenha = new JPasswordField();
		jpfNovaSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {

				conferirStatusSenha(administrador.getSenha());

			}
		});
		jpfNovaSenha.setMargin(new Insets(5, 0, 0, 0));
		jpfNovaSenha.setFont(new Font("SansSerif", Font.BOLD, 20));
		jpfNovaSenha.setBounds(56, 289, 257, 35);
		pnlD.add(jpfNovaSenha);

		JLabel lblConfirmarNovaSenha = new JLabel("Confirmar nova senha: ");
		lblConfirmarNovaSenha.setFont(new Font("Segoe Print", Font.BOLD, 20));
		lblConfirmarNovaSenha.setBounds(56, 358, 257, 28);
		pnlD.add(lblConfirmarNovaSenha);

		jpfConfirmar = new JPasswordField();
		jpfConfirmar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {

				conferirStatusSenha(administrador.getSenha());

			}
		});
		jpfConfirmar.setMargin(new Insets(5, 0, 0, 0));
		jpfConfirmar.setFont(new Font("SansSerif", Font.BOLD, 20));
		jpfConfirmar.setBounds(56, 398, 257, 35);
		pnlD.add(jpfConfirmar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				jpfSenhaAntiga.setText("");
				jpfNovaSenha.setText("");
				jpfConfirmar.setText("");

			}
		});
		btnCancelar.setBounds(226, 516, 158, 41);
		pnlD.add(btnCancelar);

		JButton btnEmailConfig = new JButton("E-mail");
		btnEmailConfig.setBounds(600, 58, 158, 145);

		//Média
		btnEmailConfig.setIcon(new ImageIcon(emailMedioFinal));

		//Grande
		btnEmailConfig.setRolloverIcon(new ImageIcon(emailGrandeFinal));

		//Pequena
		btnEmailConfig.setPressedIcon(new ImageIcon(emailPequenoFinal));

		btnEmailConfig.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEmailConfig.setVerticalAlignment(SwingConstants.BOTTOM);
		btnEmailConfig.setIconTextGap(-3);
		btnEmailConfig.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEmailConfig.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEmailConfig.setContentAreaFilled(false);
		btnEmailConfig.setBorderPainted(false);
		btnEmailConfig.setBorder(null);

		pnlD.add(btnEmailConfig);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(455, 62, 16, 495);
		pnlD.add(separator_3);
		
		JButton btnLogins = new JButton("Logins de usu\u00E1rios");
		btnLogins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JDLogsDeUsuarios logins = new JDLogsDeUsuarios();
				logins.setModal(true);
				logins.setLocationRelativeTo(null);
				logins.setVisible(true);
				
			}
		});
		btnLogins.setBounds(600, 222, 147, 129);
		
		ImageIcon logins = new ImageIcon(AWAdministrador.class.getResource("/imagens/logins.png"));
		Image loginsFinal = logins.getImage();
		loginsFinal = loginsFinal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		btnLogins.setIcon(new ImageIcon(loginsFinal));

		loginsFinal = loginsFinal.getScaledInstance(108, 108, Image.SCALE_SMOOTH);
		btnLogins.setRolloverIcon(new ImageIcon(loginsFinal));

		loginsFinal = loginsFinal.getScaledInstance(84, 84, Image.SCALE_SMOOTH);
		btnLogins.setPressedIcon(new ImageIcon(loginsFinal));

		btnLogins.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLogins.setVerticalAlignment(SwingConstants.BOTTOM);
		btnLogins.setIconTextGap(-3);
		btnLogins.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogins.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogins.setContentAreaFilled(false);
		btnLogins.setBorderPainted(false);
		btnLogins.setBorder(null);
		
		pnlD.add(btnLogins);

		JButton btnSair = new JButton("Sair");
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

		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sair(usuarioLogado);
			}
		});

		frmAdministrador.getContentPane().setLayout(null);
		frmAdministrador.getContentPane().add(btnConfiguracoes);
		frmAdministrador.getContentPane().add(btnSair);
		frmAdministrador.getContentPane().add(btnFuncionarios);
		frmAdministrador.getContentPane().add(btnDistribuidores);
		frmAdministrador.getContentPane().add(btnVacinas);
		frmAdministrador.getContentPane().add(pnlPai);

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
		SwingUtilities.updateComponentTreeUI(frmAdministrador);
		frmAdministrador.setBackground(SystemColor.darkGray);
	}

	public void consultarFuncionario(){

		if(jtbFuncionarios.getSelectedRowCount() == 1){

			Funcionario umFuncionario = new Funcionario();

			FuncionarioTableModel model = (FuncionarioTableModel) jtbFuncionarios.getModel();

			umFuncionario = model.getFuncionario(jtbFuncionarios.getSelectedRow());

			try {
				JDConsultarFuncionario dialog = new JDConsultarFuncionario(funcionarioDAO.consultar(umFuncionario));
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}


		}else{
			JOptionPane.showMessageDialog(null, "Selecione um funcionário!", "Aviso", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void cadastrarFuncionario(Administrador administrador){

		try {
			JDCadastrarFuncionario cadastroFuncionario = new JDCadastrarFuncionario(administrador);
			cadastroFuncionario.setModal(true);
			cadastroFuncionario.setLocationRelativeTo(null);
			cadastroFuncionario.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			cadastroFuncionario.setVisible(true);
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: \n" + erro, "Erro ao abrir a janela!", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void alterarFuncionario(Administrador administrador){

		if(jtbFuncionarios.getSelectedRowCount() == 1){

			Funcionario umFuncionario = new Funcionario();

			FuncionarioTableModel model = (FuncionarioTableModel) jtbFuncionarios.getModel();

			umFuncionario = model.getFuncionario(jtbFuncionarios.getSelectedRow());

			try {
				JDAlterarFuncionario dialog = new JDAlterarFuncionario(funcionarioDAO.consultar(umFuncionario), administrador);
				dialog.setModal(true);
				dialog.setLocationRelativeTo(null);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception erro) {
				erro.printStackTrace();
			}


		}else{
			JOptionPane.showMessageDialog(null, "Selecione um funcionário!", "Aviso", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void excluirFuncionario(Administrador administrador){

		if(jtbFuncionarios.getSelectedRowCount() == 1){
			Funcionario umFuncionario = new Funcionario();

			FuncionarioTableModel model = (FuncionarioTableModel) jtbFuncionarios.getModel();

			umFuncionario = model.getFuncionario(jtbFuncionarios.getSelectedRow());

			Object[] botoes = {"Sim", "Não"};
			String senhaDigitada = "";

			int opcao = JOptionPane.showOptionDialog(frmAdministrador, "Deseja realmente excluir este funcionário?", "Excluir", 
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

				opcao = JOptionPane.showOptionDialog(frmAdministrador, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[1]);

				senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

				if(opcao == JOptionPane.OK_OPTION){

					if(administrador.getSenha().equals(senhaDigitada)){

						funcionarioDAO.excluir(umFuncionario);

					}else{
						JOptionPane.showMessageDialog(null, "Senha inválida! Verifique sua senha e \n"
								+ "tente novamente.", "Senha incorreta", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}

		}else{
			JOptionPane.showMessageDialog(null, "Selecione um funcionário!",
					"Aviso", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void cadastrarVacina(Administrador administrador){

		try{

			JDCadastrarVacina cadastroVacina =  new JDCadastrarVacina(administrador);
			cadastroVacina.setModal(true);
			cadastroVacina.setLocationRelativeTo(null);
			cadastroVacina.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			cadastroVacina.setVisible(true);

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: \n" + erro, "Erro ao abrir a janela!", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void excluirVacina(Administrador administrador){

		if(jlsVacinas.getSelectedIndex() != -1){
			Vacina umaVacina= new Vacina();

			umaVacina = vacinaDAO.consultar(jlsVacinas.getSelectedValue().toString());

			Object[] botoes = {"Sim", "Não"};
			String senhaDigitada = "";

			int opcao = JOptionPane.showOptionDialog(frmAdministrador, "Deseja realmente excluir esta Vacina?", "Excluir", 
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

				opcao = JOptionPane.showOptionDialog(frmAdministrador, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[1]);

				senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

				if(opcao == JOptionPane.OK_OPTION){

					if(administrador.getSenha().equals(senhaDigitada)){

						vacinaDAO.excluir(umaVacina);

					}else{
						JOptionPane.showMessageDialog(null, "Senha inválida! Verifique sua senha e \n"
								+ "tente novamente.", "Senha incorreta", JOptionPane.ERROR_MESSAGE);
					}
				}

			}

		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma Vacina!",
					"Aviso", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void alterarVacina(Administrador administrador){

		if(jlsVacinas.getSelectedIndex() != -1){

			Vacina vacinaAlterar = new Vacina();

			vacinaAlterar = vacinaDAO.consultar(jlsVacinas.getSelectedValue().toString());

			try {
				JDAlterarVacina dialogVacina = new JDAlterarVacina(vacinaAlterar, administrador);
				dialogVacina.setModal(true);
				dialogVacina.setLocationRelativeTo(null);
				dialogVacina.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogVacina.setVisible(true);
			} catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao abrir janela! \nERRO: " + erro,
						"Erro ao abrir janela!", JOptionPane.ERROR_MESSAGE);
			}

		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma Vacina para alterar!", "Alterar", JOptionPane.WARNING_MESSAGE);
		}

	}
	
	public void cadastrarDistribuidor(Administrador administrador) {
		
		try {
			JDCadastrarDistribuidor cadastroDistribuidor = new JDCadastrarDistribuidor(administrador);
			cadastroDistribuidor.setModal(true);
			cadastroDistribuidor.setLocationRelativeTo(null);
			cadastroDistribuidor.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			cadastroDistribuidor.setVisible(true);
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: \n" + erro, "Erro ao abrir a janela!", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void excluirDistribuidor(Administrador administrador) {
		
		if(jlsDistribuidores.getSelectedIndex() != -1){
			Distribuidor umDistribuidor = new Distribuidor();

			umDistribuidor = distribuidorDAO.consultar(jlsDistribuidores.getSelectedValue().toString());

			Object[] botoes = {"Sim", "Não"};
			String senhaDigitada = "";

			int opcao = JOptionPane.showOptionDialog(frmAdministrador, "Deseja realmente excluir este Distribuidor?", "Excluir", 
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

				opcao = JOptionPane.showOptionDialog(frmAdministrador, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[1]);

				senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

				if(opcao == JOptionPane.OK_OPTION){

					if(administrador.getSenha().equals(senhaDigitada)){

						distribuidorDAO.excluir(umDistribuidor);

					}else{
						JOptionPane.showMessageDialog(null, "Senha inválida! Verifique sua senha e \n"
								+ "tente novamente.", "Senha incorreta", JOptionPane.ERROR_MESSAGE);
					}
				}

			}

		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma Vacina!",
					"Aviso", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void alterarDistribuidor(Administrador admLogado) {
		
		if(jlsDistribuidores.getSelectedIndex() != -1){

			Distribuidor distribuidorAlterar = distribuidorDAO.consultar(jlsDistribuidores.getSelectedValue().toString());

			try {
				JDAlterarDistribuidor dialogDistribuidor = new JDAlterarDistribuidor(distribuidorAlterar, admLogado);
				dialogDistribuidor.setModal(true);
				dialogDistribuidor.setLocationRelativeTo(null);
				dialogDistribuidor.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogDistribuidor.setVisible(true);
			} catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "Erro desconhecido ao abrir janela! \nERRO: " + erro,
						"Erro ao abrir janela!", JOptionPane.ERROR_MESSAGE);
			}

		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma Vacina para alterar!", "Alterar", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void sair(Login usuarioLogado) {
		
		Object[] opcoes = {"Encerrar operações", "Fazer logout", "Cancelar"};

		int opcao = JOptionPane.showOptionDialog(frmAdministrador, "O que deseja fazer?", "Sair", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[2]);

		if(opcao == JOptionPane.YES_OPTION){
			loginDAO.atualizarLogin(usuarioLogado, "dataLogoutTempoLogado");
			System.exit(0);
		}else if(opcao == JOptionPane.NO_OPTION){

			loginDAO.atualizarLogin(usuarioLogado, "dataLogoutTempoLogado");

			frmAdministrador.dispose();

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

	public void conferirStatusSenha(String senhaAdm){

		String senhaAdministrador = senhaAdm,

				campoSenhaAntiga = String.valueOf(jpfSenhaAntiga.getPassword()),

				novaSenha = String.valueOf(jpfNovaSenha.getPassword()),

				confirmarSenha = String.valueOf(jpfConfirmar.getPassword());

		if(campoSenhaAntiga.equals(senhaAdministrador) && !novaSenha.equals("") && novaSenha.equals(confirmarSenha)){//Senhas corretas
			lblStatusSenha.setText("");
			btnAlterarSenha.setEnabled(true);
		}else if(campoSenhaAntiga.equals("") && novaSenha.equals("") && confirmarSenha.equals("") || (campoSenhaAntiga.equals("")  &&
				novaSenha.equals(confirmarSenha)) && !novaSenha.equals("")){//Todos campos em branco ou as duas senhas iguais
			btnAlterarSenha.setEnabled(false);
			lblStatusSenha.setText("");
		}else if((campoSenhaAntiga.equals("") || campoSenhaAntiga.equals(senhaAdministrador)) &&
				novaSenha.equals("") && !confirmarSenha.equals("")){//Senha antiga nula ou igual senhaAdm e nova senha nula e confirmar não nulo
			btnAlterarSenha.setEnabled(false);
			lblStatusSenha.setText("* Nova senha inválida!");
		}else if((campoSenhaAntiga.equals("") || campoSenhaAntiga.equals(senhaAdministrador)) && !novaSenha.equals("") 
				&& !novaSenha.equals(confirmarSenha)){//Senha igual senhaAdm ou nula e novaSenha não nula e confirmarSenha diferente da nova senha
			btnAlterarSenha.setEnabled(false);
			lblStatusSenha.setText("* Confirmação de senha incorreta!");
		}else if((campoSenhaAntiga.equals("") || campoSenhaAntiga.equals(senhaAdministrador)) && novaSenha.equals("") && 
				confirmarSenha.equals("")){//Somente senha antiga correta ou sem algum texto
			btnAlterarSenha.setEnabled(false);
			lblStatusSenha.setText("");
		}else if(!campoSenhaAntiga.equals(senhaAdministrador) && !campoSenhaAntiga.equals("") && novaSenha.equals("") && 
				!confirmarSenha.equals("")){//SenhaAdm diferente senhaAdmini. e não nula e novaSenha nula e confirmarSenha não nula
			btnAlterarSenha.setEnabled(false);
			lblStatusSenha.setText("<html>* Senha de administrador incorreta!<br>* Nova senha inválida!</html>");
		}else if(!campoSenhaAntiga.equals(senhaAdministrador) && !campoSenhaAntiga.equals("") && confirmarSenha.equals("") && 
				!novaSenha.equals("")){//SenhaAdm diferente senhaAdmini. e não nula e confirmarSenha nula e novaSenha não nula
			btnAlterarSenha.setEnabled(false);
			lblStatusSenha.setText("<html>* Senha de administrador incorreta!<br>* Confirmação de senha incorreta!</html>");
		}else if(novaSenha.equals(confirmarSenha) && !campoSenhaAntiga.equals(senhaAdministrador)){//Somente senhaAntiga incorreta
			btnAlterarSenha.setEnabled(false);
			lblStatusSenha.setText("* Senha de administrador incorreta!");
		}else if(!campoSenhaAntiga.equals("") && !campoSenhaAntiga.equals(senhaAdministrador) && !novaSenha.equals("") &&
				!novaSenha.equals(confirmarSenha)){

		}else if((!campoSenhaAntiga.equals("") && !campoSenhaAntiga.equals(senhaAdministrador)) &&
				!novaSenha.equals("") && !novaSenha.equals(confirmarSenha)){//SenhaAdm incorreta e senhas não coincidem
			btnAlterarSenha.setEnabled(false);
			lblStatusSenha.setText("<html>* Senha de administrador incorreta!<br>* Confirmação de senha incorreta!</html>");
		}

		if((campoSenhaAntiga.equals("") || !campoSenhaAntiga.equals(senhaAdministrador)) || novaSenha.equals("") || confirmarSenha.equals("")
				|| !novaSenha.equals(confirmarSenha)){
			btnAlterarSenha.setEnabled(false);
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
