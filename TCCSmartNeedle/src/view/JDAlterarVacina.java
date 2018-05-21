package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.joda.time.DateTime;

import com.toedter.calendar.JDateChooser;

import model.Administrador;
import model.Distribuidor;
import model.Vacina;
import model.DAO.AdministradorDAO;
import model.DAO.DistribuidorDAO;
import model.DAO.VacinaDAO;
import java.awt.event.WindowFocusListener;
import java.text.SimpleDateFormat;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class JDAlterarVacina extends JDialog {

	//Criação dos objetos
	Vacina vacina = new Vacina();
	VacinaDAO vacinaDAO = new VacinaDAO();

	AdministradorDAO admDAO = new AdministradorDAO();

	Distribuidor distribduidor = new Distribuidor();
	DistribuidorDAO distribuidorDAO = new DistribuidorDAO();

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfVacina;
	private JTextField jtfFabricante;
	private JTextField jtfLote;
	private JSpinner jsrDisponiveis;
	private JComboBox jcbTipo;
	private JComboBox jcbDistribuidores;
	private JSpinner jsrSolicitacoes;
	private JSpinner jsrAnos;
	private JSpinner jsrMeses;
	private JDateChooser jdcValidade;
	private JTextArea jtaIndicacao;
	private JTextArea jtaContraIndicacao;
	private JTextArea jtaDescricao;
	private JTextField jtfID;
	private JButton btnOk;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JLabel lblIDDistribuidor;

	/**
	 * Create the dialog.
	 */
	public JDAlterarVacina(Vacina vacinaAlterada, Administrador admLogado) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {

				desabilitarCampos();

			}
		});
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
		setModal(true);
		setTitle("Cadastrar vacina");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AWAdministrador.class.getResource("/imagens/icone.png")));
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

		jtfVacina = new JTextField(vacinaAlterada.getNome());
		jtfVacina.setBackground(new Color(214, 217, 223));
		jtfVacina.setBounds(92, 31, 252, 28);
		pnlDadosPessoais.add(jtfVacina);
		jtfVacina.setColumns(10);

		JLabel lblFabricante = new JLabel("Fabricante: ");
		lblFabricante.setFont(new Font("Arial", Font.BOLD, 14));
		lblFabricante.setBounds(25, 88, 83, 24);
		pnlDadosPessoais.add(lblFabricante);

		jtfFabricante = new JTextField(vacinaAlterada.getFabricante());
		jtfFabricante.setColumns(10);
		jtfFabricante.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfFabricante.setBounds(120, 86, 224, 28);
		pnlDadosPessoais.add(jtfFabricante);

		JLabel lblLote = new JLabel("Lote:");
		lblLote.setFont(new Font("Arial", Font.BOLD, 14));
		lblLote.setBounds(368, 33, 35, 24);
		pnlDadosPessoais.add(lblLote);

		jtfLote = new JTextField(vacinaAlterada.getLote());
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
		jsrDisponiveis.setValue(vacinaAlterada.getDisponivel());
		jsrDisponiveis.setBounds(206, 139, 138, 28);
		pnlDadosPessoais.add(jsrDisponiveis);

		jsrSolicitacoes = new JSpinner();
		jsrSolicitacoes.setValue(vacinaAlterada.getSolicitacoes());
		jsrSolicitacoes.setBounds(216, 192, 128, 26);
		pnlDadosPessoais.add(jsrSolicitacoes);

		JLabel lblValidadeDaVacina = new JLabel("Validade da vacina:");
		lblValidadeDaVacina.setFont(new Font("Arial", Font.BOLD, 14));
		lblValidadeDaVacina.setBounds(25, 250, 138, 24);
		pnlDadosPessoais.add(lblValidadeDaVacina);

		jdcValidade = new JDateChooser(new DateTime(vacinaAlterada.getValidade().toString()).toDate());
		jdcValidade.setBounds(175, 250, 169, 28);
		pnlDadosPessoais.add(jdcValidade);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
		lblTipo.setBounds(368, 145, 35, 24);
		pnlDadosPessoais.add(lblTipo);

		jcbTipo = new JComboBox();
		jcbTipo.setModel(new DefaultComboBoxModel(new String[] {"Aplicada", "Gota"}));

		if(vacinaAlterada.getTipo().equals("Aplicada")) {
			jcbTipo.setSelectedIndex(0);
		}else if(vacinaAlterada.getTipo().equals("Gota")) {
			jcbTipo.setSelectedIndex(1);
		}

		jcbTipo.setBounds(415, 140, 204, 28);
		pnlDadosPessoais.add(jcbTipo);

		JLabel lblIndicao = new JLabel("Indica\u00E7\u00E3o:");
		lblIndicao.setFont(new Font("Arial", Font.BOLD, 14));
		lblIndicao.setBounds(368, 194, 76, 24);
		pnlDadosPessoais.add(lblIndicao);

		JLabel lblContraindicao = new JLabel("Contra-indica\u00E7\u00E3o:");
		lblContraindicao.setFont(new Font("Arial", Font.BOLD, 14));
		lblContraindicao.setBounds(25, 310, 128, 24);
		pnlDadosPessoais.add(lblContraindicao);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setFont(new Font("Arial", Font.BOLD, 14));
		lblDescrio.setBounds(368, 310, 76, 24);
		pnlDadosPessoais.add(lblDescrio);

		JScrollPane jspIndicacao = new JScrollPane();
		jspIndicacao.setBounds(368, 230, 252, 78);
		pnlDadosPessoais.add(jspIndicacao);

		jtaIndicacao = new JTextArea(vacinaAlterada.getIndicacao());
		jspIndicacao.setViewportView(jtaIndicacao);
		jtaIndicacao.setLineWrap(true);//ir para a próxima linha
		jtaIndicacao.setWrapStyleWord(true);

		JScrollPane jspContraIndicacao = new JScrollPane();
		jspContraIndicacao.setBounds(25, 346, 319, 114);
		pnlDadosPessoais.add(jspContraIndicacao);

		jtaContraIndicacao = new JTextArea(vacinaAlterada.getContraIndicacao());
		jspContraIndicacao.setViewportView(jtaContraIndicacao);
		jtaContraIndicacao.setLineWrap(true);
		jtaContraIndicacao.setWrapStyleWord(true);
		jtaContraIndicacao.setWrapStyleWord(true);
		jtaContraIndicacao.setLineWrap(true);

		JScrollPane jspDescricao = new JScrollPane();
		jspDescricao.setBounds(368, 346, 252, 114);
		pnlDadosPessoais.add(jspDescricao);

		jtaDescricao = new JTextArea(vacinaAlterada.getDescricao());
		jspDescricao.setViewportView(jtaDescricao);
		jtaDescricao.setLineWrap(true);
		jtaDescricao.setWrapStyleWord(true);
		jtaDescricao.setWrapStyleWord(true);
		jtaDescricao.setLineWrap(true);

		jcbDistribuidores = new JComboBox();

		DefaultComboBoxModel<String> modeloDistribuidor = new DefaultComboBoxModel<String>();

		for(String p : distribuidorDAO.listarNomesDistribuidores()) {
			modeloDistribuidor.addElement(p);
		}
		jcbDistribuidores.setModel(modeloDistribuidor);
		jcbDistribuidores.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itmEvt) {

				String nomeDistribuidor = jcbDistribuidores.getSelectedItem().toString();

				Distribuidor umDistribuidor = distribuidorDAO.consultar(nomeDistribuidor);
				lblIDDistribuidor.setText(String.valueOf(umDistribuidor.getIdDistribuidor()));

			}
		});
		jcbDistribuidores.setBounds(210, 474, 204, 28);
		pnlDadosPessoais.add(jcbDistribuidores);

		JLabel lblSelecione = new JLabel("Selecione o distribuidor:");
		lblSelecione.setFont(new Font("Arial", Font.BOLD, 14));
		lblSelecione.setBounds(25, 478, 173, 24);
		pnlDadosPessoais.add(lblSelecione);

		jsrAnos = new JSpinner();
		jsrAnos.setBounds(368, 84, 120, 28);
		pnlDadosPessoais.add(jsrAnos);

		JLabel lblAnos = new JLabel("Anos:");
		lblAnos.setFont(new Font("Arial", Font.BOLD, 14));
		lblAnos.setBounds(368, 65, 46, 24);
		pnlDadosPessoais.add(lblAnos);

		JLabel lblMeses = new JLabel("Meses:");
		lblMeses.setFont(new Font("Arial", Font.BOLD, 14));
		lblMeses.setBounds(500, 65, 51, 24);
		pnlDadosPessoais.add(lblMeses);

		jsrMeses = new JSpinner();
		jsrMeses.setBounds(499, 84, 120, 28);
		pnlDadosPessoais.add(jsrMeses);

		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(6, 642, 676, 33);
		contentPanel.add(pnlBotoes);
		pnlBotoes.setLayout(new GridLayout(0, 3, 0, 0));

		btnOk = new JButton("OK");
		getRootPane().setDefaultButton(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

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

						opcao = JOptionPane.showOptionDialog(JDAlterarVacina.this, new Object[]{lblSenhaa, jpfSenhaConfirmacao}, "Digite sua senha para concluir",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,  botoes, botoes[0]);

						senhaDigitada = new String(jpfSenhaConfirmacao.getPassword());

						if(opcao == JOptionPane.OK_OPTION){

							final Administrador admAutenticado = admDAO.autenticar(admLogado.getLogin(), senhaDigitada);

							if(admAutenticado.getSenha().equals(senhaDigitada)){

								try{
									vacina.setIdVacina(Integer.parseInt(jtfID.getText()));
									vacina.setNome(jtfVacina.getText());
									vacina.setLote(jtfLote.getText());
									vacina.setFabricante(jtfFabricante.getText());
									
									SimpleDateFormat formatadorDataValidade = new SimpleDateFormat("yyyy-MM-dd");
									String dataAplicada = formatadorDataValidade.format(jdcValidade.getDate());
									dataAplicada += "T00:00:00";
									vacina.setValidade(LocalDateTime.parse(dataAplicada, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
									
									vacina.setDisponivel(Integer.parseInt(jsrDisponiveis.getValue().toString()));
									vacina.setTipo(jcbTipo.getSelectedItem().toString());
									vacina.setSolicitacoes(Integer.parseInt(jsrSolicitacoes.getValue().toString()));
									vacina.setAnos(Integer.parseInt(jsrAnos.getValue().toString()));
									vacina.setMeses(Integer.parseInt(jsrMeses.getValue().toString()));
									vacina.setIndicacao(jtaIndicacao.getText());
									vacina.setContraIndicacao(jtaContraIndicacao.getText());
									vacina.setDescricao(jtaDescricao.getText());
									vacina.setIdDistribuidor(Integer.parseInt(lblIDDistribuidor.getText()));

									vacinaDAO.alterar(vacina);
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
		btnOk.setEnabled(false);
		pnlBotoes.add(btnOk);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

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

		JLabel lblId = new JLabel("ID Vacina:");
		lblId.setFont(new Font("Arial", Font.BOLD, 16));
		lblId.setBounds(455, 23, 78, 24);
		contentPanel.add(lblId);

		jtfID = new JTextField(String.valueOf(vacinaAlterada.getIdVacina()));
		jtfID.setFont(new Font("Arial", Font.PLAIN, 14));
		jtfID.setEditable(false);
		jtfID.setColumns(10);
		jtfID.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfID.setBounds(545, 22, 122, 28);
		contentPanel.add(jtfID);

		JLabel lblIdD = new JLabel("ID Distribuidor: ");
		lblIdD.setFont(new Font("Arial", Font.BOLD, 16));
		lblIdD.setBounds(35, 23, 120, 24);
		contentPanel.add(lblIdD);

		lblIDDistribuidor = new JLabel("N/D");
		lblIDDistribuidor.setFont(new Font("Arial", Font.BOLD, 16));
		lblIDDistribuidor.setBounds(160, 23, 55, 24);
		contentPanel.add(lblIDDistribuidor);
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

			jtfVacina.setEditable(true);
			jtfLote.setEditable(true);
			jtfFabricante.setEditable(true);
			jsrDisponiveis.setEnabled(true);
			jsrSolicitacoes.setEnabled(true);
			jcbTipo.setEnabled(true);
			jsrAnos.setEnabled(true);
			jsrMeses.setEnabled(true);
			jtaIndicacao.setEditable(true);
			jtaContraIndicacao.setEditable(true);
			jtaDescricao.setEditable(true);
			jdcValidade.setEnabled(true);
			jcbDistribuidores.setEnabled(true);

		}

		public void desabilitarCampos(){

			jtfVacina.setEditable(false);
			jtfLote.setEditable(false);
			jtfFabricante.setEditable(false);
			jsrDisponiveis.setEnabled(false);
			jsrSolicitacoes.setEnabled(false);
			jcbTipo.setEnabled(false);
			jsrAnos.setEnabled(false);
			jsrMeses.setEnabled(false);
			jtaIndicacao.setEditable(false);
			jtaContraIndicacao.setEditable(false);
			jtaDescricao.setEditable(false);
			jdcValidade.setEnabled(false);
			jcbDistribuidores.setEnabled(false);

		}

		public void limparCampos(){

			jtfVacina.setText(null);
			jtfLote.setText("");
			jtfFabricante.setText("");
			jsrDisponiveis.setValue(0);
			jsrSolicitacoes.setValue(0);
			jcbTipo.setSelectedIndex(0);
			jsrAnos.setValue(0);
			jsrMeses.setValue(0);
			jtaIndicacao.setText("");
			jtaContraIndicacao.setText("");
			jtaDescricao.setText("");
			jdcValidade.setDate(DateTime.now().toDate());

		}

		public void clarearCampos(){

			jtfVacina.setBackground(new Color(255, 255, 255));
			jtfLote.setBackground(new Color(255, 255, 255));
			jtfFabricante.setBackground(new Color(255, 255, 255));
			jsrDisponiveis.setBackground(new Color(255, 255, 255));
			jsrSolicitacoes.setBackground(new Color(255, 255, 255));
			jcbTipo.setBackground(new Color(255, 255, 255));
			jsrAnos.setBackground(new Color(255, 255, 255));
			jsrAnos.setBackground(new Color(255, 255, 255));
			jtaIndicacao.setBackground(new Color(255, 255, 255));
			jtaContraIndicacao.setBackground(new Color(255, 255, 255));
			jtaDescricao.setBackground(new Color(255, 255, 255));

		}

		public void escurecerCampos(){

			jtfVacina.setBackground(new Color(214, 217, 223));
			jtfLote.setBackground(new Color(214, 217, 223));
			jtfFabricante.setBackground(new Color(214, 217, 223));
			jsrDisponiveis.setBackground(new Color(214, 217, 223));
			jsrSolicitacoes.setBackground(new Color(214, 217, 223));
			jcbTipo.setBackground(new Color(214, 217, 223));
			jsrAnos.setBackground(new Color(214, 217, 223));
			jsrMeses.setBackground(new Color(214, 217, 223));
			jtaIndicacao.setBackground(new Color(214, 217, 223));
			jtaContraIndicacao.setBackground(new Color(214, 217, 223));
			jtaDescricao.setBackground(new Color(214, 217, 223));

		}
	}
