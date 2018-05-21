package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.joda.time.DateTime;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.toedter.calendar.JDateChooser;

import model.Administrador;
import model.Distribuidor;
import model.Vacina;
import model.DAO.AdministradorDAO;
import model.DAO.DistribuidorDAO;
import model.DAO.VacinaDAO;

public class JDCadastrarVacina extends JDialog {

	//Criação dos objetos
	Vacina vacina = new Vacina();
	VacinaDAO vacinaDAO = new VacinaDAO();

	AdministradorDAO admDAO = new AdministradorDAO();

	Distribuidor distribuidor = new Distribuidor();
	DistribuidorDAO distribuidorDAO = new DistribuidorDAO(); 

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfVacina;
	private JTextField jtfFabricante;
	private JTextField jtfLote;
	private JSpinner jsrDisponiveis;
	private JSpinner jsrAnos;
	private JSpinner jsrMeses;
	private JComboBox jcbTipo;
	private JComboBox jcbDistribuidores;
	private JSpinner jsrSolicitacoes;
	private JDateChooser jdcValidade;
	private JTextArea jtaIndicacao;
	private JTextArea jtaContraIndicacao;
	private JTextArea jtaDescricao;
	private JLabel lblIDDistribuidor;


	/**
	 * Create the dialog.
	 */
	public JDCadastrarVacina(Administrador admLogado) {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {

				String nomeDistribuidor = jcbDistribuidores.getSelectedItem().toString();

				Distribuidor umDistribuidor = distribuidorDAO.consultar(nomeDistribuidor);
				lblIDDistribuidor.setText(String.valueOf(umDistribuidor.getIdDistribuidor()));

			}
			public void windowLostFocus(WindowEvent arg0) {
				String nomeDistribuidor = jcbDistribuidores.getSelectedItem().toString();

				Distribuidor umDistribuidor = distribuidorDAO.consultar(nomeDistribuidor);
				lblIDDistribuidor.setText(String.valueOf(umDistribuidor.getIdDistribuidor()));
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(AWAdministrador.class.getResource("/imagens/icone.png")));
		setModal(true);
		setTitle("Cadastrar vacina");
		setResizable(false);
		setBounds(100, 100, 694, 710);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel pnlDadosPessoais = new JPanel();
		pnlDadosPessoais.setBorder(new TitledBorder(null, "Dados da vacina", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		pnlDadosPessoais.setBounds(22, 52, 645, 540);
		contentPanel.add(pnlDadosPessoais);
		pnlDadosPessoais.setLayout(null);

		JLabel lblVacina = new JLabel("Vacina:");
		lblVacina.setFont(new Font("Arial", Font.BOLD, 14));
		lblVacina.setBounds(25, 33, 55, 24);
		pnlDadosPessoais.add(lblVacina);

		jtfVacina = new JTextField();
		//Tudo que for inserido no JTextField fica maiúsculo
		jtfVacina.setDocument( new PlainDocument()
		{
			@Override
			public void insertString( int offs, String str, AttributeSet a )
							throws BadLocationException
			{
				super.insertString( offs, str.toUpperCase(), a );
			}
		} );
		
		jtfVacina.setBackground(Color.WHITE);
		jtfVacina.setBounds(92, 31, 252, 28);
		pnlDadosPessoais.add(jtfVacina);
		jtfVacina.setColumns(10);

		JLabel lblFabricante = new JLabel("Fabricante: ");
		lblFabricante.setFont(new Font("Arial", Font.BOLD, 14));
		lblFabricante.setBounds(25, 88, 83, 24);
		pnlDadosPessoais.add(lblFabricante);

		jtfFabricante = new JTextField();
		jtfFabricante.setColumns(10);
		jtfFabricante.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfFabricante.setBounds(120, 86, 224, 28);
		pnlDadosPessoais.add(jtfFabricante);

		JLabel lblLote = new JLabel("Lote:");
		lblLote.setFont(new Font("Arial", Font.BOLD, 14));
		lblLote.setBounds(368, 33, 35, 24);
		pnlDadosPessoais.add(lblLote);

		jtfLote = new JTextField();
		jtfLote.setColumns(10);
		jtfLote.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfLote.setBounds(415, 31, 204, 28);
		pnlDadosPessoais.add(jtfLote);

		JLabel lblVacinasDisponiveis = new JLabel("Vacinas dispon\u00EDvies: ");
		lblVacinasDisponiveis.setFont(new Font("Arial", Font.BOLD, 14));
		lblVacinasDisponiveis.setBounds(25, 141, 148, 24);
		pnlDadosPessoais.add(lblVacinasDisponiveis);

		JLabel lblSolicitacoes = new JLabel("Solicita\u00E7\u00F5es de vacinas:");
		lblSolicitacoes.setFont(new Font("Arial", Font.BOLD, 14));
		lblSolicitacoes.setBounds(25, 194, 169, 24);
		pnlDadosPessoais.add(lblSolicitacoes);

		jsrDisponiveis = new JSpinner();
		jsrDisponiveis.setBounds(206, 139, 138, 28);
		pnlDadosPessoais.add(jsrDisponiveis);

		jsrSolicitacoes = new JSpinner();
		jsrSolicitacoes.setBounds(216, 192, 128, 26);
		pnlDadosPessoais.add(jsrSolicitacoes);

		JLabel lblValidadeDaVacina = new JLabel("Validade da vacina:");
		lblValidadeDaVacina.setFont(new Font("Arial", Font.BOLD, 14));
		lblValidadeDaVacina.setBounds(25, 250, 138, 24);
		pnlDadosPessoais.add(lblValidadeDaVacina);

		jdcValidade = new JDateChooser();
		jdcValidade.setBounds(175, 250, 169, 28);
		pnlDadosPessoais.add(jdcValidade);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
		lblTipo.setBounds(368, 143, 35, 24);
		pnlDadosPessoais.add(lblTipo);

		jcbTipo = new JComboBox();
		jcbTipo.setModel(new DefaultComboBoxModel(new String[] {"Aplicada", "Gota"}));
		jcbTipo.setBounds(415, 141, 204, 28);
		pnlDadosPessoais.add(jcbTipo);

		JLabel lblIndicao = new JLabel("Indica\u00E7\u00E3o:");
		lblIndicao.setFont(new Font("Arial", Font.BOLD, 14));
		lblIndicao.setBounds(368, 207, 76, 24);
		pnlDadosPessoais.add(lblIndicao);

		JLabel lblContraindicao = new JLabel("Contra-indica\u00E7\u00E3o:");
		lblContraindicao.setFont(new Font("Arial", Font.BOLD, 14));
		lblContraindicao.setBounds(25, 320, 128, 24);
		pnlDadosPessoais.add(lblContraindicao);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setFont(new Font("Arial", Font.BOLD, 14));
		lblDescrio.setBounds(368, 320, 76, 24);
		pnlDadosPessoais.add(lblDescrio);

		JScrollPane jspIndicacao = new JScrollPane();
		jspIndicacao.setBounds(368, 230, 252, 78);
		pnlDadosPessoais.add(jspIndicacao);

		jtaIndicacao = new JTextArea();
		jspIndicacao.setViewportView(jtaIndicacao);
		jtaIndicacao.setLineWrap(true);//ir para a próxima linha
		jtaIndicacao.setWrapStyleWord(true);

		JScrollPane jspContraIndicacao = new JScrollPane();
		jspContraIndicacao.setBounds(25, 346, 319, 114);
		pnlDadosPessoais.add(jspContraIndicacao);

		jtaContraIndicacao = new JTextArea();
		jspContraIndicacao.setViewportView(jtaContraIndicacao);
		jtaContraIndicacao.setLineWrap(true);
		jtaContraIndicacao.setWrapStyleWord(true);

		JScrollPane jspDescricao = new JScrollPane();
		jspDescricao.setBounds(368, 346, 252, 114);
		pnlDadosPessoais.add(jspDescricao);

		jtaDescricao = new JTextArea();
		jspDescricao.setViewportView(jtaDescricao);
		jtaDescricao.setLineWrap(true);
		jtaDescricao.setWrapStyleWord(true);
		jtaDescricao.setWrapStyleWord(true);
		jtaDescricao.setLineWrap(true);

		JLabel label = new JLabel("Selecione o distribuidor:");
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setBounds(25, 489, 173, 24);
		pnlDadosPessoais.add(label);

		jcbDistribuidores = new JComboBox();
		jcbDistribuidores.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String nomeDistribuidor = jcbDistribuidores.getSelectedItem().toString();

				Distribuidor umDistribuidor = distribuidorDAO.consultar(nomeDistribuidor);
				lblIDDistribuidor.setText(String.valueOf(umDistribuidor.getIdDistribuidor()));
			}
		});
		DefaultComboBoxModel<String> modeloDistribuidor = new DefaultComboBoxModel<String>();

		for(String p : distribuidorDAO.listarNomesDistribuidores()) {
			modeloDistribuidor.addElement(p);
		}

		jcbDistribuidores.setModel(modeloDistribuidor);
		jcbDistribuidores.setBounds(210, 485, 204, 28);
		pnlDadosPessoais.add(jcbDistribuidores);

		JLabel lblAnos = new JLabel("Anos:");
		lblAnos.setFont(new Font("Arial", Font.BOLD, 14));
		lblAnos.setBounds(368, 67, 46, 24);
		pnlDadosPessoais.add(lblAnos);

		JLabel lblMeses = new JLabel("Meses:");
		lblMeses.setFont(new Font("Arial", Font.BOLD, 14));
		lblMeses.setBounds(500, 67, 51, 24);
		pnlDadosPessoais.add(lblMeses);

		jsrAnos = new JSpinner();
		jsrAnos.setBounds(368, 86, 120, 28);
		pnlDadosPessoais.add(jsrAnos);

		jsrMeses = new JSpinner();
		jsrMeses.setBounds(499, 86, 120, 28);
		pnlDadosPessoais.add(jsrMeses);

		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(0, 648, 688, 33);
		contentPanel.add(pnlBotoes);
		pnlBotoes.setLayout(new GridLayout(0, 3, 0, 0));

		JButton btnOk = new JButton("OK");
		getRootPane().setDefaultButton(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Vacina verificarVacina = vacinaDAO.consultar(jtfVacina.getText().trim());

				if(verificarVacina.getNome().equals("") /*|| 
						verificarVacina.getNome().equals(jtfVacina.getText().trim()) && !verificarVacina.getLote().equals(jtfLote.getText().trim())*/) {

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

						opcao = JOptionPane.showOptionDialog(JDCadastrarVacina.this, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir o cadastro",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[0]);

						senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

						if(opcao == JOptionPane.OK_OPTION){

							final Administrador admAutenticado = admDAO.autenticar(admLogado.getLogin(), senhaDigitada);

							if(admAutenticado.getSenha().equals(senhaDigitada)){

								try{
									vacina.setNome(jtfVacina.getText());
									vacina.setFabricante(jtfFabricante.getText());
									vacina.setDisponivel(Integer.parseInt(jsrDisponiveis.getValue().toString()));
									vacina.setSolicitacoes(Integer.parseInt(jsrSolicitacoes.getValue().toString()));
									vacina.setDescricao(jtaDescricao.getText());
									vacina.setLote(jtfLote.getText());
									vacina.setIndicacao(jtaIndicacao.getText());
									vacina.setContraIndicacao(jtaContraIndicacao.getText());
									vacina.setTipo(jcbTipo.getSelectedItem().toString());
									
									SimpleDateFormat formatadorDataValidade = new SimpleDateFormat("yyyy-MM-dd");
									String dataAplicada = formatadorDataValidade.format(jdcValidade.getDate());
									dataAplicada += "T00:00:00";
									vacina.setValidade(LocalDateTime.parse(dataAplicada, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
									
									vacina.setAnos(Integer.parseInt(jsrAnos.getValue().toString()));
									vacina.setMeses(Integer.parseInt(jsrMeses.getValue().toString()));
									vacina.setIdDistribuidor(Integer.parseInt(lblIDDistribuidor.getText()));

									vacinaDAO.inserir(vacina);
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

				}else {
					JOptionPane.showMessageDialog(null, "Esta vacina já foi cadastrada!" /*com este valor de lote!*/
							+ "\nSe quiser atualizar alguma vacina tente alterá-la no menu de vacinas.", 
							"Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		pnlBotoes.add(btnOk);

		JButton btnLimparCampos = new JButton("Limpar campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				jtfVacina.setText("");
				jtfLote.setText("");
				jtfFabricante.setText("");
				jsrAnos.setValue(0);
				jsrMeses.setValue(0);
				jsrDisponiveis.setValue(0);
				jcbTipo.setSelectedIndex(0);
				jsrSolicitacoes.setValue(0);
				jdcValidade.setDate(null);
				jtaIndicacao.setText("");
				jtaContraIndicacao.setText("");
				jtaDescricao.setText("");
				jtfVacina.grabFocus();
				jcbDistribuidores.setSelectedIndex(0);

			}
		});
		pnlBotoes.add(btnLimparCampos);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		pnlBotoes.add(btnCancelar);

		JLabel lblId = new JLabel("ID Distribuidor: ");
		lblId.setFont(new Font("Arial", Font.BOLD, 16));
		lblId.setBounds(469, 15, 120, 24);
		contentPanel.add(lblId);

		lblIDDistribuidor = new JLabel("N/D");
		lblIDDistribuidor.setFont(new Font("Arial", Font.BOLD, 16));
		lblIDDistribuidor.setBounds(612, 15, 55, 24);
		contentPanel.add(lblIDDistribuidor);
		
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
		setBackground(SystemColor.darkGray);

	}
}
