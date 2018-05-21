package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.joda.time.DateTime;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;

import model.Administrador;
import model.Distribuidor;
import model.Email;
import model.Vacina;

public class JDEnviarEmail extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfEnderecoServidor = new JTextField();
	private JTextField jtfPorta =  new JTextField();;
	private JComboBox jcbProvedor = new JComboBox();;
	private JTextArea jtaMensagem = new JTextArea();;

	File[] anexo;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			JDEnviarEmail dialog = new JDEnviarEmail(new Vacina(6,60,20,2,2,4,"Nome","Descricao","fabricante","lote", "indicaocao","contra,", "tipo", new DateTime(DateTime.now())), 
//					new Distribuidor(1, "Nomedis", "cnpj", "email", "tel", "uf", "endereco", "cidade", "bairro", "cep"), 
//					new Administrador(1, "nomeAdm", "emailAdm", "tel", "rg", "cpf", "sexo", "login", "senha", "uf", "endereco", "cidade", "bairro", "cep", new DateTime(DateTime.now())));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//			dialog.setLocationRelativeTo(null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private JTextField jtfRemetente;
	private JPasswordField jpfSenha;
	private JTextField jtfDestinatario;
	private JTextField jtfAnexo;
	private JTextField jtfNome;
	private JTextField jtfAssunto;
	/**
	 * Create the dialog.
	 */
	public JDEnviarEmail(Vacina vacinaSolicitada, Distribuidor distribuidorSolicitado, Administrador administrador) {
		setResizable(false);
		setTitle("Solicitar " + vacinaSolicitada.getNome() + " para " + distribuidorSolicitado.getNome());
		setBounds(100, 100, 739, 700);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDEnviarEmail.class.getResource("/imagens/icone.png")));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel pnlConfiguracoes = new JPanel();
		pnlConfiguracoes.setBorder(new TitledBorder(null, "Configura\u00E7\u00F5es de envio", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		pnlConfiguracoes.setBounds(114, 6, 520, 149);
		contentPanel.add(pnlConfiguracoes);
		pnlConfiguracoes.setLayout(null);

		jcbProvedor.setFont(new Font("Arial", Font.PLAIN, 13));
		jcbProvedor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				//Mudar porta e endereço do servidor
				String provedor = jcbProvedor.getSelectedItem().toString();

				if(provedor.equals("Gmail")) {
					jtfEnderecoServidor.setText("smtp.gmail.com");
					jtfPorta.setText("465");
				}else if(provedor.equals("OUTLOOK / HOTMAIL / LIVE")) {
					jtfEnderecoServidor.setText("smtp-mail.outlook.com");
					jtfPorta.setText("587");
				}else if(provedor.equals("UOL")) {
					jtfEnderecoServidor.setText("smtps.uol.com.br");
					jtfPorta.setText("587");
				}else if(provedor.equals("BOL")) {
					jtfEnderecoServidor.setText("smtps.bol.com.br");
					jtfPorta.setText("587");
				}else if(provedor.equals("YAHOO")) {
					jtfEnderecoServidor.setText("smtp.mail.yahoo.com.br");
					jtfPorta.setText("25");
				}else if(provedor.equals("CLICK21")) {
					jtfEnderecoServidor.setText("smtp.click21.com.br");
					jtfPorta.setText("25");
				}else if(provedor.equals("IG")) {
					jtfEnderecoServidor.setText("smtp.ig.com.br");
					jtfPorta.setText("587");
				}else if(provedor.equals("BRTURBO")) {
					jtfEnderecoServidor.setText("smtp.brturbo.com.br");
					jtfPorta.setText("587");
				}else if(provedor.equals("GLOBO.COM")) {
					jtfEnderecoServidor.setText("smtp.globo.com");
					jtfPorta.setText("25");
				}else if(provedor.equals("OI VELOX")) {
					jtfEnderecoServidor.setText("smtp.oi.com.br");
					jtfPorta.setText("25");
				}else if(provedor.equals("CULTURA.COM.BR")) {
					jtfEnderecoServidor.setText("smtp.cultura.com.br");
					jtfPorta.setText("25");
				}

			}
		});
		jcbProvedor.setModel(new DefaultComboBoxModel(new String[] {"Gmail", "OUTLOOK / HOTMAIL / LIVE", "UOL", "BOL", "YAHOO", "CLICK21", "IG", "BRTURBO", "GLOBO.COM", "OI VELOX", "CULTURA.COM.BR"}));
		jcbProvedor.setBounds(283, 24, 200, 28);
		pnlConfiguracoes.add(jcbProvedor);

		jtfEnderecoServidor.setText("smtp.gmail.com");
		jtfEnderecoServidor.setEditable(false);
		jtfEnderecoServidor.setBounds(202, 62, 281, 28);
		pnlConfiguracoes.add(jtfEnderecoServidor);
		jtfEnderecoServidor.setColumns(10);

		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setFont(new Font("Arial", Font.BOLD, 14));
		lblPorta.setBounds(25, 113, 49, 24);
		pnlConfiguracoes.add(lblPorta);

		JLabel lblEndereoDoServidor = new JLabel("Endere\u00E7o do servidor:");
		lblEndereoDoServidor.setFont(new Font("Arial", Font.BOLD, 14));
		lblEndereoDoServidor.setBounds(25, 64, 165, 24);
		pnlConfiguracoes.add(lblEndereoDoServidor);

		JLabel lblSelecioneSeuProvedor = new JLabel("Selecione seu provedor de e-mail:");
		lblSelecioneSeuProvedor.setFont(new Font("Arial", Font.BOLD, 14));
		lblSelecioneSeuProvedor.setBounds(25, 26, 246, 24);
		pnlConfiguracoes.add(lblSelecioneSeuProvedor);

		jtfPorta.setText("465");
		jtfPorta.setEditable(false);
		jtfPorta.setBounds(86, 111, 200, 28);
		pnlConfiguracoes.add(jtfPorta);
		jtfPorta.setColumns(10);

		JPanel pnlInformacoesEnvio = new JPanel();
		pnlInformacoesEnvio.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de envio", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		pnlInformacoesEnvio.setBounds(17, 160, 701, 453);
		contentPanel.add(pnlInformacoesEnvio);
		pnlInformacoesEnvio.setLayout(null);

		JLabel lblRemetente = new JLabel("Remetente:");
		lblRemetente.setFont(new Font("Arial", Font.BOLD, 14));
		lblRemetente.setBounds(29, 20, 86, 24);
		pnlInformacoesEnvio.add(lblRemetente);

		jtfRemetente = new JTextField(administrador.getEmail());
		jtfRemetente.setBounds(127, 18, 248, 28);
		pnlInformacoesEnvio.add(jtfRemetente);
		jtfRemetente.setColumns(10);

		JLabel lblSenhaDeUsurio = new JLabel("Senha de usu\u00E1rio:");
		lblSenhaDeUsurio.setFont(new Font("Arial", Font.BOLD, 14));
		lblSenhaDeUsurio.setBounds(29, 85, 127, 24);
		pnlInformacoesEnvio.add(lblSenhaDeUsurio);

		jpfSenha = new JPasswordField();
		jpfSenha.setBounds(169, 83, 336, 28);
		pnlInformacoesEnvio.add(jpfSenha);

		JLabel lblDestinatrio = new JLabel("Destinat\u00E1rio:");
		lblDestinatrio.setFont(new Font("Arial", Font.BOLD, 14));
		lblDestinatrio.setBounds(29, 137, 94, 24);
		pnlInformacoesEnvio.add(lblDestinatrio);

		jtfDestinatario = new JTextField(distribuidorSolicitado.getEmail());
		jtfDestinatario.setBounds(169, 135, 336, 28);
		pnlInformacoesEnvio.add(jtfDestinatario);
		jtfDestinatario.setColumns(10);

		JButton btnAnexo = new JButton("Anexo");
		btnAnexo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser file = new JFileChooser();
				file.setDialogTitle("Selecione arquivos para anexo do e-mail");
				file.setMultiSelectionEnabled(true);
				file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				int i = file.showOpenDialog(null);
				if (i == 1) {
					jtfAnexo.setText("");
					anexo = null;
				} else {
					anexo = file.getSelectedFiles();

					String SomaAnexo1 = "";
					String SomaAnexo2 = "";
					for (File enderec : anexo) {
						jtfAnexo.setText(enderec.getPath());
						SomaAnexo1 = jtfAnexo.getText();
						SomaAnexo2 = SomaAnexo2 + SomaAnexo1 + ";";
						jtfAnexo.setText(SomaAnexo2);

					}
				}

			}
		});
		btnAnexo.setToolTipText("Inserir arquivos");
		btnAnexo.setBounds(29, 222, 90, 28);
		pnlInformacoesEnvio.add(btnAnexo);

		jtfAnexo = new JTextField();
		jtfAnexo.setEditable(false);
		jtfAnexo.setBounds(120, 222, 385, 28);
		pnlInformacoesEnvio.add(jtfAnexo);
		jtfAnexo.setColumns(10);

		JLabel lblMensagem = new JLabel("Mensagem:");
		lblMensagem.setFont(new Font("Arial", Font.BOLD, 14));
		lblMensagem.setBounds(29, 262, 86, 24);
		pnlInformacoesEnvio.add(lblMensagem);

		JScrollPane jspMensagem = new JScrollPane();
		jspMensagem.setBounds(28, 287, 535, 138);
		pnlInformacoesEnvio.add(jspMensagem);

		String mensagemSolicitacao = "Abaixo seguem informações da vacina solicitada.\n\n" + "  Vacina solicitada: " + vacinaSolicitada.getNome() + "\n" + "	* Fabricante: " + vacinaSolicitada.getFabricante() +
				"\n	* Disponíveis: " + vacinaSolicitada.getDisponivel() + "\n	* Solicitações: " + vacinaSolicitada.getSolicitacoes() + "\n	* Indicação: " 
				+ vacinaSolicitada.getIndicacao() + "\n	* Contra indicação: " + vacinaSolicitada.getContraIndicacao() + "\n	* Tipo: " + vacinaSolicitada.getTipo() +
				"\n	* Descrição: " + vacinaSolicitada.getDescricao();

		jtaMensagem.setText(mensagemSolicitacao);
		jtaMensagem.setLineWrap(true);
		jtaMensagem.setWrapStyleWord(true);

		jspMensagem.setViewportView(jtaMensagem);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				jtfAnexo.setText("");
				anexo = null;

			}
		});
		btnExcluir.setToolTipText("Remover anexo");
		btnExcluir.setBounds(507, 222, 90, 28);
		pnlInformacoesEnvio.add(btnExcluir);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 14));
		lblNome.setBounds(387, 20, 51, 24);
		pnlInformacoesEnvio.add(lblNome);

		jtfNome = new JTextField(administrador.getNome());
		jtfNome.setBounds(450, 18, 214, 28);
		pnlInformacoesEnvio.add(jtfNome);
		jtfNome.setColumns(10);

		JLabel lblAssunto = new JLabel("Assunto:");
		lblAssunto.setFont(new Font("Arial", Font.BOLD, 14));
		lblAssunto.setBounds(29, 186, 76, 24);
		pnlInformacoesEnvio.add(lblAssunto);

		jtfAssunto = new JTextField();
		jtfAssunto.setBounds(120, 182, 385, 28);
		pnlInformacoesEnvio.add(jtfAssunto);
		jtfAssunto.setColumns(10);

		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(0, 621, 733, 50);
		contentPanel.add(pnlBotoes);
		pnlBotoes.setLayout(new GridLayout(0, 3, 0, 0));

		JButton btnEnviarEmail = new JButton("Enviar e-mail");
		btnEnviarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					Email email = new Email();
					email.setAnexo(anexo);
					email.setProvedorEmail(jcbProvedor.getSelectedItem().toString());
					email.setEnderecoSmtp(jtfEnderecoServidor.getText());		
					email.setPorta(jtfPorta.getText());
					email.setRemetente(jtfRemetente.getText());
					email.setNome(jtfNome.getText());
					email.setSenha(String.valueOf(jpfSenha.getPassword()));
					email.setDestinatario(jtfDestinatario.getText());
					email.setAssunto(jtfAssunto.getText());
					email.setMensagem(jtaMensagem.getText());

					JDProgressBar progresso = new JDProgressBar(email);
					progresso.setModal(true);
					progresso.setLocationRelativeTo(null);
					progresso.setVisible(true);

					//					email.start();

				}catch(Exception erro) {
					JOptionPane.showMessageDialog(null, "Erro capturado: \n" + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		pnlBotoes.add(btnEnviarEmail);

		JButton btnLimparCampos = new JButton("Limpar campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				jtfRemetente.setText("");
				jpfSenha.setText("");
				jtfDestinatario.setText("");
				jtfAnexo.setText("");
				jtaMensagem.setText("");
				jtfAssunto.setText("");
				jtfNome.setText("");
				anexo = null;
				jcbProvedor.setSelectedIndex(0);
				jtfRemetente.grabFocus();

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
		this.setBackground(SystemColor.darkGray);

	}

	public void EnviarEmail() {

		try {

			final String usuario = jtfRemetente.getText();
			final String senha = String.valueOf(jpfSenha.getPassword());

			//config. do gmail
			Properties mailProps = new Properties();
			mailProps.put("mail.transport.protocol", "smtp");
			mailProps.put("mail.smtp.starttls.enable", "true");
			mailProps.put("mail.smtp.host", jtfEnderecoServidor.getText());
			mailProps.put("mail.smtp.auth", "true");
			mailProps.put("mail.smtp.user", usuario);
			mailProps.put("mail.debug", "true");
			mailProps.put("mail.smtp.port", jtfPorta.getText());
			mailProps.put("mail.smtp.socketFactory.port", jtfPorta.getText());
			mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			mailProps.put("mail.smtp.socketFactory.fallback", "false");

			//eh necessario autenticar
			Session mailSession = Session.getInstance(mailProps, new Authenticator() {

				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(usuario, senha);
				}
			});
			mailSession.setDebug(false);

			//config. da mensagem
			Message mailMessage = new MimeMessage(mailSession);

			//remetente
			mailMessage.setFrom(new InternetAddress(jtfRemetente.getText(), jtfNome.getText()));

			//destinatario
			mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(jtfDestinatario.getText()));

			//mensagem que vai no corpo do email
			MimeBodyPart mbpMensagem = new MimeBodyPart();
			mbpMensagem.setText(jtaMensagem.getText());

			//	partes do email
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbpMensagem);

			String Endereco_Anexo = "";
			if (anexo != null) { // se tiver alguma coisa anexada ela inicializar o comando abaixo
				for (File element : anexo) {

					Endereco_Anexo = element.getPath();
					String imagem = Endereco_Anexo;
					File Arquivo = new File(imagem);
					//setando o anexo
					MimeBodyPart mbpAnexo = new MimeBodyPart();
					mbpAnexo.setDataHandler(new DataHandler(new FileDataSource(Arquivo)));
					mbpAnexo.setFileName(Arquivo.getName());
					mp.addBodyPart(mbpAnexo);
				}
			}

			//assunto do email
			mailMessage.setSubject(jtfAssunto.getText());

			//seleciona o conteudo 
			mailMessage.setContent(mp);

			//envia o email
			Transport.send(mailMessage);
			JOptionPane.showMessageDialog(null, "Email Enviado com Sucesso", "E-mail", JOptionPane.INFORMATION_MESSAGE);

		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível enviar a mensagem\nERRO: " + e.getMessage() + 
					"\nClasse do erro: " + e.getClass(), "Erro ao enviar E-mail", JOptionPane.ERROR_MESSAGE);
		}

	}
}












