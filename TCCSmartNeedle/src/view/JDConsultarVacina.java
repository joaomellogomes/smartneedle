package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.joda.time.DateTime;

import com.toedter.calendar.JDateChooser;

import model.Distribuidor;
import model.Vacina;
import model.DAO.DistribuidorDAO;
import model.DAO.VacinaDAO;

public class JDConsultarVacina extends JDialog {
	
	//Criação dos objetos
	Distribuidor distribuidor = new Distribuidor();
	DistribuidorDAO distribuidorDAO = new DistribuidorDAO();
	
	Vacina vacina = new Vacina();
	VacinaDAO vacinaDAO = new VacinaDAO();

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfIdVacina;
	private JTextField jtfVacina;
	private JTextField jtfFabricante;
	private JTextField jtfLote;
	private JLabel lblIDDistribuidor;
	private JSpinner jsrDisponiveis;
	private JComboBox jcbTipo;
	private JSpinner jsrSolicitacoes;
	private JSpinner jsrAnos;
	private JSpinner jsrMeses;
	private JDateChooser jdcValidade;
	private JTextArea jtaIndicacao;
	private JTextArea jtaContraIndicacao;
	private JTextArea jtaDescricao;
	private JComboBox jcbDistribuidores;

	/**
	 * Create the dialog.
	 */
	public JDConsultarVacina(Vacina vacinaConsulta) {
		setModal(true);
		setTitle("Consultar Vacina(" + vacinaConsulta.getNome() + ")");
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
		
		jtfVacina = new JTextField(vacinaConsulta.getNome());
		jtfVacina.setBounds(92, 31, 252, 28);
		pnlDadosPessoais.add(jtfVacina);
		jtfVacina.setColumns(10);
		
		JLabel lblFabricante = new JLabel("Fabricante: ");
		lblFabricante.setFont(new Font("Arial", Font.BOLD, 14));
		lblFabricante.setBounds(25, 88, 83, 24);
		pnlDadosPessoais.add(lblFabricante);
		
		jtfFabricante = new JTextField(vacinaConsulta.getFabricante());
		jtfFabricante.setColumns(10);
		jtfFabricante.setBackground(UIManager.getColor("ArrowButton.background"));
		jtfFabricante.setBounds(120, 86, 224, 28);
		pnlDadosPessoais.add(jtfFabricante);
		
		JLabel lblLote = new JLabel("Lote:");
		lblLote.setFont(new Font("Arial", Font.BOLD, 14));
		lblLote.setBounds(368, 33, 35, 24);
		pnlDadosPessoais.add(lblLote);
		
		jtfLote = new JTextField(vacinaConsulta.getLote());
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
		jsrDisponiveis.setValue(vacinaConsulta.getDisponivel());
		jsrDisponiveis.setValue(vacinaConsulta.getDisponivel());
		jsrDisponiveis.setBounds(206, 139, 138, 28);
		pnlDadosPessoais.add(jsrDisponiveis);
		
		jsrSolicitacoes = new JSpinner();
		jsrSolicitacoes.setValue(vacinaConsulta.getSolicitacoes());
		jsrSolicitacoes.setBounds(216, 192, 128, 26);
		pnlDadosPessoais.add(jsrSolicitacoes);
		
		JLabel lblValidadeDaVacina = new JLabel("Validade da vacina:");
		lblValidadeDaVacina.setFont(new Font("Arial", Font.BOLD, 14));
		lblValidadeDaVacina.setBounds(25, 250, 138, 24);
		pnlDadosPessoais.add(lblValidadeDaVacina);
		
		jdcValidade = new JDateChooser(new DateTime(vacinaConsulta.getValidade().toString()).toDate());
		jdcValidade.setBounds(175, 250, 169, 28);
		pnlDadosPessoais.add(jdcValidade);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Arial", Font.BOLD, 14));
		lblTipo.setBounds(368, 145, 35, 24);
		pnlDadosPessoais.add(lblTipo);
		
		jcbTipo = new JComboBox();
		
		DefaultComboBoxModel<String> modeloTipo = new DefaultComboBoxModel<String>();
		modeloTipo.setSelectedItem(vacinaConsulta.getTipo());
		jcbTipo.setModel(modeloTipo);
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
		
		jtaIndicacao = new JTextArea(vacinaConsulta.getIndicacao());
		jspIndicacao.setViewportView(jtaIndicacao);
		jtaIndicacao.setLineWrap(true);//ir para a próxima linha
		jtaIndicacao.setWrapStyleWord(true);
		
		JScrollPane jspContraIndicacao = new JScrollPane();
		jspContraIndicacao.setBounds(25, 346, 319, 114);
		pnlDadosPessoais.add(jspContraIndicacao);
		
		jtaContraIndicacao = new JTextArea(vacinaConsulta.getContraIndicacao());
		jspContraIndicacao.setViewportView(jtaContraIndicacao);
		jtaContraIndicacao.setLineWrap(true);
		jtaContraIndicacao.setWrapStyleWord(true);
		jtaContraIndicacao.setWrapStyleWord(true);
		jtaContraIndicacao.setLineWrap(true);
		
		JScrollPane jspDescricao = new JScrollPane();
		jspDescricao.setBounds(368, 346, 252, 114);
		pnlDadosPessoais.add(jspDescricao);
		
		JTextArea jtaDescricao = new JTextArea(vacinaConsulta.getDescricao());
		jspDescricao.setViewportView(jtaDescricao);
		jtaDescricao.setLineWrap(true);
		jtaDescricao.setWrapStyleWord(true);
		jtaDescricao.setWrapStyleWord(true);
		jtaDescricao.setLineWrap(true);
		
		jcbDistribuidores = new JComboBox();
		
		DefaultComboBoxModel<String> modeloDistribuidor = new DefaultComboBoxModel<String>();

		for(String p : distribuidorDAO.listarNomesDistribuidores()) {
			modeloDistribuidor.addElement(p);
			modeloDistribuidor.setSelectedItem(distribuidorDAO.consultar(vacinaDAO.consultar(jtfVacina.getText().trim()).getIdDistribuidor()).getNome());
		}
		
		jcbDistribuidores.setBounds(210, 476, 204, 28);
		jcbDistribuidores.setModel(modeloDistribuidor);
		pnlDadosPessoais.add(jcbDistribuidores);
		
		JLabel lblSelecione = new JLabel("Selecione o distribuidor:");
		lblSelecione.setFont(new Font("Arial", Font.BOLD, 14));
		lblSelecione.setBounds(25, 480, 173, 24);
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
		
		JLabel lblId = new JLabel("ID Vacina:");
		lblId.setFont(new Font("Arial", Font.BOLD, 16));
		lblId.setBounds(460, 24, 78, 24);
		contentPanel.add(lblId);
		{
			jtfIdVacina = new JTextField(String.valueOf(vacinaConsulta.getIdVacina()));
			jtfIdVacina.setFont(new Font("Arial", Font.PLAIN, 14));
			jtfIdVacina.setBounds(540, 22, 122, 28);
			contentPanel.add(jtfIdVacina);
			jtfIdVacina.setColumns(10);
		}
		
		JButton btnOk = new JButton("OK");
		getRootPane().setDefaultButton(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnOk.setBounds(0, 643, 688, 38);
		contentPanel.add(btnOk);
		
		JLabel lblIDDis = new JLabel("ID Distribuidor: ");
		lblIDDis.setFont(new Font("Arial", Font.BOLD, 16));
		lblIDDis.setBounds(22, 24, 120, 24);
		contentPanel.add(lblIDDis);
		
		lblIDDistribuidor = new JLabel("N/D");
		lblIDDistribuidor.setFont(new Font("Arial", Font.BOLD, 16));
		lblIDDistribuidor.setBounds(147, 24, 55, 24);
		contentPanel.add(lblIDDistribuidor);
		
		//Desabilitar campos
		jtfIdVacina.setEditable(false);
		jtaDescricao.setEditable(false);
		jcbDistribuidores.setEnabled(false);
		jtfVacina.setEditable(false);
		jtfFabricante.setEditable(false);
		jtfLote.setEditable(false);
		jsrDisponiveis.setEnabled(false);
		jcbTipo.setEnabled(false);
		jsrSolicitacoes.setEnabled(false);
		jdcValidade.setEnabled(false);
		jsrAnos.setEnabled(false);
		jsrMeses.setEnabled(false);
		jtaIndicacao.setEditable(false);
		jtaContraIndicacao.setEditable(false);
		
	}
	
	public void desabilitarCampos(){
		
		jtfIdVacina.setEditable(false);
		jtaDescricao.setEditable(false);
		jcbDistribuidores.setEnabled(false);
		jtfVacina.setEditable(false);
		jtfFabricante.setEditable(false);
		jtfLote.setEditable(false);
		jsrDisponiveis.setEnabled(false);
		jcbTipo.setEnabled(false);
		jsrSolicitacoes.setEnabled(false);
		jdcValidade.setEnabled(false);
		jsrAnos.setEnabled(false);
		jsrMeses.setEnabled(false);
		jtaIndicacao.setEditable(false);
		jtaContraIndicacao.setEditable(false);

	}
	
}
