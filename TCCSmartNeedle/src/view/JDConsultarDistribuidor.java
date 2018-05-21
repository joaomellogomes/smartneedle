package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import model.Distribuidor;
import javax.swing.JFormattedTextField;

public class JDConsultarDistribuidor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfDistribuidor;
	private JTextField jtfNome;
	private JTextField jtfEmail;
	private JTextField jtfEndereco;
	private JFormattedTextField ftfCnpj;
	private JTextField jtfCidade;
	private JTextField jtfBairro;
	private JComboBox jcbUf;
	private JFormattedTextField ftfTelefone;
	private JFormattedTextField ftfCep;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			JDConsultarDistribuidor dialog = new JDConsultarDistribuidor(null);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public JDConsultarDistribuidor(Distribuidor distribuidorConsulta) {
		setModal(true);
		setTitle("Consultar Distribuidor - " + distribuidorConsulta.getNome());
		setIconImage(Toolkit.getDefaultToolkit().getImage(AWAdministrador.class.getResource("/imagens/icone.png")));
		setResizable(false);
		setBounds(100, 100, 694, 560);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel pnlDadosPessoais = new JPanel();
		pnlDadosPessoais.setBorder(new TitledBorder(null, "Dados do distribuidor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		pnlDadosPessoais.setBounds(22, 55, 645, 411);
		contentPanel.add(pnlDadosPessoais);
		pnlDadosPessoais.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 16));
		lblNome.setBounds(41, 57, 61, 24);
		pnlDadosPessoais.add(lblNome);
		
		jtfNome = new JTextField(distribuidorConsulta.getNome());
		jtfNome.setEditable(false);
		jtfNome.setColumns(10);
		jtfNome.setBounds(114, 56, 252, 28);
		pnlDadosPessoais.add(jtfNome);
		
		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setFont(new Font("Arial", Font.BOLD, 16));
		lblCnpj.setBounds(390, 57, 55, 24);
		pnlDadosPessoais.add(lblCnpj);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblEmail.setBounds(41, 119, 61, 24);
		pnlDadosPessoais.add(lblEmail);
		
		jtfEmail = new JTextField(distribuidorConsulta.getEmail());
		jtfEmail.setEditable(false);
		jtfEmail.setColumns(10);
		jtfEmail.setBounds(114, 118, 252, 28);
		pnlDadosPessoais.add(jtfEmail);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Arial", Font.BOLD, 16));
		lblTelefone.setBounds(41, 185, 82, 24);
		pnlDadosPessoais.add(lblTelefone);
		
		JLabel lblUf = new JLabel("UF:");
		lblUf.setFont(new Font("Arial", Font.BOLD, 16));
		lblUf.setBounds(390, 119, 55, 24);
		pnlDadosPessoais.add(lblUf);
		
		jcbUf = new JComboBox();
		jcbUf.setEnabled(false);
		
		DefaultComboBoxModel<String> modeloTipo = new DefaultComboBoxModel<String>();
		modeloTipo.setSelectedItem(distribuidorConsulta.getUf());
		jcbUf.setModel(modeloTipo);
		jcbUf.setBounds(457, 118, 167, 30);
		pnlDadosPessoais.add(jcbUf);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setFont(new Font("Arial", Font.BOLD, 16));
		lblEndereo.setBounds(41, 243, 82, 24);
		pnlDadosPessoais.add(lblEndereo);
		
		jtfEndereco = new JTextField(distribuidorConsulta.getEndereco());
		jtfEndereco.setEditable(false);
		jtfEndereco.setColumns(10);
		jtfEndereco.setBounds(135, 242, 488, 28);
		pnlDadosPessoais.add(jtfEndereco);
		
		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Arial", Font.BOLD, 16));
		lblCep.setBounds(390, 185, 40, 24);
		pnlDadosPessoais.add(lblCep);
		
		ftfCnpj = new JFormattedTextField();
		ftfCnpj.setEditable(false);
		MaskFormatter mascara = null;
		try {
			mascara = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: " + erro, "Erro", JOptionPane.ERROR_MESSAGE);
		}

		mascara.install(ftfCnpj);
		
		ftfCnpj.setText(distribuidorConsulta.getCpnj());
		ftfCnpj.setBounds(457, 56, 167, 28);
		pnlDadosPessoais.add(ftfCnpj);
		
		ftfCep = new JFormattedTextField();
		ftfCep.setEditable(false);
		
		try {
			mascara = new MaskFormatter("##-######");
		}catch(ParseException erro) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido: " + erro, "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		ftfCep.setText(distribuidorConsulta.getCep());
		ftfCep.setBounds(457, 184, 167, 28);
		pnlDadosPessoais.add(ftfCep);
		
		ftfTelefone = new JFormattedTextField();
		ftfTelefone.setEditable(false);
		ftfTelefone.setText(distribuidorConsulta.getTelefone());
		ftfTelefone.setBounds(135, 184, 231, 28);
		pnlDadosPessoais.add(ftfTelefone);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Arial", Font.BOLD, 16));
		lblCidade.setBounds(41, 304, 82, 24);
		pnlDadosPessoais.add(lblCidade);
		
		jtfCidade = new JTextField(distribuidorConsulta.getCidade());
		jtfCidade.setEditable(false);
		jtfCidade.setColumns(10);
		jtfCidade.setBounds(135, 303, 489, 28);
		pnlDadosPessoais.add(jtfCidade);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Arial", Font.BOLD, 16));
		lblBairro.setBounds(41, 359, 82, 24);
		pnlDadosPessoais.add(lblBairro);
		
		jtfBairro = new JTextField(distribuidorConsulta.getBairro());
		jtfBairro.setEditable(false);
		jtfBairro.setColumns(10);
		jtfBairro.setBounds(135, 358, 489, 28);
		pnlDadosPessoais.add(jtfBairro);
		
		JLabel lblIdDistribuidor = new JLabel("ID Distribuidor:");
		lblIdDistribuidor.setFont(new Font("Arial", Font.BOLD, 16));
		lblIdDistribuidor.setBounds(417, 19, 116, 24);
		contentPanel.add(lblIdDistribuidor);
		
		jtfDistribuidor = new JTextField(String.valueOf(distribuidorConsulta.getIdDistribuidor()));
		jtfDistribuidor.setFont(new Font("Arial", Font.PLAIN, 14));
		jtfDistribuidor.setEditable(false);
		jtfDistribuidor.setColumns(10);
		jtfDistribuidor.setBounds(545, 15, 122, 28);
		contentPanel.add(jtfDistribuidor);
		
		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button.setBounds(0, 493, 688, 38);
		contentPanel.add(button);
		
	}
}
