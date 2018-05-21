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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.joda.time.DateTime;

import com.toedter.calendar.JDateChooser;

import model.Distribuidor;
import model.Funcionario;
import model.Paciente;
import model.Vacina;
import model.VacinaAplicada;
import model.DAO.DistribuidorDAO;
import model.DAO.VacinaAplicadaDAO;
import model.DAO.VacinaDAO;

public class JDAplicarVacina extends JDialog {

	//Criação dos objetos
	Vacina vacina = new Vacina();
	VacinaDAO vacinaDAO = new VacinaDAO();
	
	VacinaAplicada vacinaAplicada = new VacinaAplicada();
	VacinaAplicadaDAO vacinaAplicadaDAO = new VacinaAplicadaDAO(); 

	Distribuidor distribuidor = new Distribuidor();
	DistribuidorDAO distribuidorDAO = new DistribuidorDAO();

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfAplicador;
	private JComboBox jcbDistribuidor;
	private JComboBox<String> jcbVacina;
	private JTextField jtfPaciente;
	private JDateChooser jdcDataAplicada;
	private JComboBox jcbDose;
	private JSpinner jspIdVacina;
	private JSpinner jspIdAplicador;
	private JSpinner jspIdPaciente;
	private JSpinner jspIdDistribuidor;

	//	/**
	//	 * Launch the application.
	//	 */
	//	public static void main(String[] args) {
	//		try {
	//			JDAplicarVacina dialog = new JDAplicarVacina();
	//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	//			dialog.setVisible(true);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * Create the dialog.
	 */
	public JDAplicarVacina(Paciente pacienteAplicar, Funcionario aplicador) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				jcbDistribuidor.setSelectedItem(distribuidorDAO.consultar(vacinaDAO.consultar(jcbVacina.getSelectedItem().toString()).getIdDistribuidor()).getNome());
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDAplicarVacina.class.getResource("/imagens/icone.png")));
		setTitle("Aplicar vacina - " + pacienteAplicar.getNome());
		setBounds(100, 100, 576, 550);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel pnlDadosVacinaAplicada = new JPanel();
		pnlDadosVacinaAplicada.setBorder(new TitledBorder(null, "Vacina aplicada", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(59, 59, 59)));
		pnlDadosVacinaAplicada.setBounds(6, 6, 548, 437);
		contentPanel.add(pnlDadosVacinaAplicada);
		pnlDadosVacinaAplicada.setLayout(null);

		jcbVacina = new JComboBox<String>();

		for(String p : vacinaDAO.listarNomesVacinas()) {
			jcbVacina.addItem(p);
		}

		jcbVacina.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				jspIdVacina.setValue(vacinaDAO.consultar(jcbVacina.getSelectedItem().toString().trim()).getIdVacina());
				jcbDistribuidor.setSelectedItem(distribuidorDAO.consultar(vacinaDAO.consultar(jcbVacina.getSelectedItem().toString()).getIdDistribuidor()).getNome());
				jspIdDistribuidor.setValue(distribuidorDAO.consultar(vacinaDAO.consultar(jcbVacina.getSelectedItem().toString()).getIdDistribuidor()).getIdDistribuidor());
			}
		});

		jcbVacina.setBounds(235, 50, 217, 30);
		pnlDadosVacinaAplicada.add(jcbVacina);

		jcbDistribuidor = new JComboBox();
		jcbDistribuidor.setEnabled(false);

		DefaultComboBoxModel<String> modeloDistribuidor = new DefaultComboBoxModel<String>();

		for(String p : distribuidorDAO.listarNomesDistribuidores()) {
			modeloDistribuidor.addElement(p);
			modeloDistribuidor.setSelectedItem(distribuidorDAO.consultar(vacinaDAO.consultar(jcbVacina.getSelectedItem().toString().trim()).getIdDistribuidor()).getNome());
		}
		jcbDistribuidor.setModel(modeloDistribuidor);

		JLabel lblVacina = new JLabel("Vacina / ID: ");
		lblVacina.setFont(new Font("Arial", Font.BOLD, 16));
		lblVacina.setBounds(90, 48, 133, 32);
		pnlDadosVacinaAplicada.add(lblVacina);

		JSeparator separator = new JSeparator();
		separator.setBounds(77, 92, 424, 9);
		pnlDadosVacinaAplicada.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(77, 149, 424, 9);
		pnlDadosVacinaAplicada.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(77, 212, 424, 9);
		pnlDadosVacinaAplicada.add(separator_2);

		JLabel lblAplicador = new JLabel("Aplicador / ID: ");
		lblAplicador.setFont(new Font("Arial", Font.BOLD, 16));
		lblAplicador.setBounds(90, 230, 133, 32);
		pnlDadosVacinaAplicada.add(lblAplicador);

		jtfAplicador = new JTextField(aplicador.getNome());
		jtfAplicador.setEditable(false);
		jtfAplicador.setColumns(10);
		jtfAplicador.setBounds(235, 233, 217, 28);
		pnlDadosVacinaAplicada.add(jtfAplicador);

		jspIdAplicador = new JSpinner();
		jspIdAplicador.setValue(aplicador.getId());
		jspIdAplicador.setEnabled(false);
		jspIdAplicador.setBounds(452, 233, 39, 28);
		pnlDadosVacinaAplicada.add(jspIdAplicador);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(77, 277, 424, 9);
		pnlDadosVacinaAplicada.add(separator_3);

		jdcDataAplicada = new JDateChooser(DateTime.now().toDate());
		jdcDataAplicada.setEnabled(false);
		jdcDataAplicada.setBounds(235, 107, 217, 28);
		pnlDadosVacinaAplicada.add(jdcDataAplicada);

		JLabel lblDataAplicada = new JLabel("Data aplicada:");
		lblDataAplicada.setFont(new Font("Arial", Font.BOLD, 16));
		lblDataAplicada.setBounds(90, 103, 133, 32);
		pnlDadosVacinaAplicada.add(lblDataAplicada);

		jcbDose = new JComboBox();
		jcbDose.setBounds(235, 170, 217, 30);
		pnlDadosVacinaAplicada.add(jcbDose);
		jcbDose.setModel(new DefaultComboBoxModel(new String[] {"1\u00AA dose", "2\u00AA dose", "3\u00AA dose", "Refor\u00E7o"}));

		JLabel lblDose = new JLabel("Dose:");
		lblDose.setBounds(90, 168, 133, 32);
		pnlDadosVacinaAplicada.add(lblDose);
		lblDose.setFont(new Font("Arial", Font.BOLD, 16));

		JLabel lblDistribuidor = new JLabel("Distribuidor / ID: ");
		lblDistribuidor.setFont(new Font("Arial", Font.BOLD, 16));
		lblDistribuidor.setBounds(90, 365, 133, 32);
		pnlDadosVacinaAplicada.add(lblDistribuidor);

		jcbDistribuidor.setBounds(235, 367, 217, 30);
		pnlDadosVacinaAplicada.add(jcbDistribuidor);
		
		JLabel lblPacienteid = new JLabel("Paciente / ID: ");
		lblPacienteid.setFont(new Font("Arial", Font.BOLD, 16));
		lblPacienteid.setBounds(90, 298, 133, 32);
		pnlDadosVacinaAplicada.add(lblPacienteid);
		
		jtfPaciente = new JTextField(pacienteAplicar.getNome());
		jtfPaciente.setEditable(false);
		jtfPaciente.setColumns(10);
		jtfPaciente.setBounds(235, 298, 217, 28);
		pnlDadosVacinaAplicada.add(jtfPaciente);
		
		jspIdPaciente = new JSpinner();
		jspIdPaciente.setValue(pacienteAplicar.getId());
		jspIdPaciente.setEnabled(false);
		jspIdPaciente.setBounds(452, 298, 39, 28);
		pnlDadosVacinaAplicada.add(jspIdPaciente);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(77, 342, 424, 9);
		pnlDadosVacinaAplicada.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setBounds(68, 36, 10, 374);
		pnlDadosVacinaAplicada.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(68, 409, 436, 9);
		pnlDadosVacinaAplicada.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setOrientation(SwingConstants.VERTICAL);
		separator_7.setBounds(503, 36, 10, 374);
		pnlDadosVacinaAplicada.add(separator_7);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(68, 36, 436, 9);
		pnlDadosVacinaAplicada.add(separator_8);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setOrientation(SwingConstants.VERTICAL);
		separator_9.setBounds(223, 36, 10, 374);
		pnlDadosVacinaAplicada.add(separator_9);
		
		jspIdDistribuidor = new JSpinner();
		jspIdDistribuidor.setValue(distribuidorDAO.consultar(vacinaDAO.consultar(jcbVacina.getSelectedItem().toString()).getIdDistribuidor()).getIdDistribuidor());
		jspIdDistribuidor.setEnabled(false);
		jspIdDistribuidor.setBounds(452, 368, 39, 28);
		pnlDadosVacinaAplicada.add(jspIdDistribuidor);
		
		jspIdVacina = new JSpinner();
		jspIdVacina.setValue(vacinaDAO.consultar(jcbVacina.getSelectedItem().toString().trim()).getIdVacina());
		jspIdVacina.setEnabled(false);
		jspIdVacina.setBounds(452, 51, 39, 28);
		pnlDadosVacinaAplicada.add(jspIdVacina);
		
		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(6, 462, 548, 43);
		contentPanel.add(pnlBotoes);
		pnlBotoes.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					vacinaAplicada.setDose(jcbDose.getSelectedItem().toString());
					vacinaAplicada.setIdVacina(Integer.parseInt(jspIdVacina.getValue().toString()));
					vacinaAplicada.setIdDistribuidor(Integer.parseInt(jspIdDistribuidor.getValue().toString()));
					vacinaAplicada.setIdPaciente(Integer.parseInt(jspIdPaciente.getValue().toString()));
					vacinaAplicada.setIdFuncionario(Integer.parseInt(jspIdAplicador.getValue().toString()));
					
					SimpleDateFormat formatadorDataAplicada = new SimpleDateFormat("yyyy-MM-dd");
					String dataAplicada = formatadorDataAplicada.format(jdcDataAplicada.getDate());
					dataAplicada += "T00:00:00";
					vacinaAplicada.setDataAplicada(LocalDateTime.parse(dataAplicada, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
					
					vacinaAplicadaDAO.cadastrar(vacinaAplicada);
					dispose();
				}catch(Exception erro) {
					JOptionPane.showMessageDialog(null, "Erro desconhecido ao registrar dados!\nERRO: " + erro,
							"Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		pnlBotoes.add(btnAplicar);
		
		JButton btnLimparCampos = new JButton("Limpar campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jcbVacina.setSelectedIndex(0);
				jcbDose.setSelectedIndex(0);
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
}
