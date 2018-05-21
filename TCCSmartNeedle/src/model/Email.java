package model;

import java.io.File;
import java.util.Properties;

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
import javax.swing.JOptionPane;

public class Email extends Thread{

	//Atributos
	private String provedorEmail;
	private String enderecoSmtp;
	private String porta;
	private String remetente;
	private String nome;
	private String senha;
	private String destinatario;
	private String assunto;
	private String mensagem;
	private File[] anexo = {null};

	//Construtores
	public Email() {
		this.provedorEmail = "";
		this.enderecoSmtp = "";
		this.porta = "";
		this.remetente = "";
		this.nome = "";
		this.senha = "";
		this.destinatario = "";
		this.assunto = "";
		this.mensagem = "";
		anexo = null;
	}

	public Email(String provedorEmail, String enderecoSmtp, String porta, String remetente, String nome, String senha,
			String destinatario, String assunto, String mensagem, File[] anexo) {
		this.provedorEmail = provedorEmail;
		this.enderecoSmtp = enderecoSmtp;
		this.porta = porta;
		this.remetente = remetente;
		this.nome = nome;
		this.senha = senha;
		this.destinatario = destinatario;
		this.assunto = assunto;
		this.mensagem = mensagem;
		this.anexo = anexo;
	}

	//M�todos de acesso
	public String getProvedorEmail() {
		return provedorEmail;
	}

	public void setProvedorEmail(String provedorEmail) {
		this.provedorEmail = provedorEmail;
	}

	public String getEnderecoSmtp() {
		return enderecoSmtp;
	}

	public void setEnderecoSmtp(String enderecoSmtp) {
		this.enderecoSmtp = enderecoSmtp;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public File[] getAnexo() {
		return anexo;
	}

	public void setAnexo(File[] anexo) {
		this.anexo = anexo;
	}

	//Vari�vel de progresso do Envio de Email
	int progresso = 0;
	String mensagemProgresso = "";
	
	//M�todos de acesso para verificar o progresso do envio;
	public int getProgresso() {
		return progresso;
	}
	
	public void setProgresso(int progresso) {
		this.progresso = progresso;
	}
	
	public String getMensagemProgresso() {
		return mensagemProgresso;
	}
	
	public void setProgresso(String mensagemProgresso) {
		this.mensagemProgresso = mensagemProgresso;
	}

	//public void enviarEmail() equivale a este m�todo
	@Override
	public void run() {

		try {
			progresso = 1;Thread.sleep(200);
			final String usuario = remetente;	progresso += 3;	mensagemProgresso = "Lendo usu�rio."; ;Thread.sleep(200);	
			final String senha = this.senha;	progresso += 3;	mensagemProgresso = "Lendo senha."; Thread.sleep(200);	

			//config. do gmail
			Properties mailProps = new Properties();progresso += 3;	mensagemProgresso = "Verificando propriedades.";	Thread.sleep(200); 
			mailProps.put("mail.transport.protocol", "smtp");progresso += 3;Thread.sleep(200);
			mailProps.put("mail.smtp.starttls.enable", "true");progresso += 3;Thread.sleep(200);
			mailProps.put("mail.smtp.host", enderecoSmtp);progresso += 3;Thread.sleep(200);
			mailProps.put("mail.smtp.auth", "true");progresso += 3;Thread.sleep(200);
			mailProps.put("mail.smtp.user", usuario);progresso += 3;Thread.sleep(200);
			mailProps.put("mail.debug", "true");progresso += 3;Thread.sleep(200);
			mailProps.put("mail.smtp.port", porta);progresso += 3;Thread.sleep(200);
			mailProps.put("mail.smtp.socketFactory.port", porta);progresso += 3;Thread.sleep(200);
			mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");progresso += 3;Thread.sleep(200);
			mailProps.put("mail.smtp.socketFactory.fallback", "false");progresso += 3;Thread.sleep(200);


			//eh necessario autenticar
			Session mailSession = Session.getInstance(mailProps, new Authenticator() {

				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(usuario, senha);
				}
			});progresso += 3;	mensagemProgresso = "Autenticando."; Thread.sleep(200);	
			mailSession.setDebug(false);progresso += 3;Thread.sleep(200);

			//config. da mensagem
			Message mailMessage = new MimeMessage(mailSession);progresso += 3;	mensagemProgresso = "Verificando mensagem.";	Thread.sleep(200);	

			//remetente
			mailMessage.setFrom(new InternetAddress(remetente, nome));	mensagemProgresso = "Verificando remetente.";	progresso += 3;	Thread.sleep(200);

			//destinatario
			mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));	mensagemProgresso = "Verificando destinat�rio.";	progresso += 3;	Thread.sleep(200);	

			//mensagem que vai no corpo do email
			MimeBodyPart mbpMensagem = new MimeBodyPart();	progresso += 3;	mensagemProgresso = "Criando corpo da mensagem.";	Thread.sleep(200);	
			mbpMensagem.setText(mensagem);	progresso += 3;	mensagemProgresso = "Inserindo mensagem.";	Thread.sleep(200);	

			//	partes do email
			Multipart mp = new MimeMultipart();	progresso += 3;	mensagemProgresso = "Adicionando partes do e-mail.";	Thread.sleep(200);	
			mp.addBodyPart(mbpMensagem);	progresso += 3;	Thread.sleep(200);

			String Endereco_Anexo = "";	mensagemProgresso = "Procurando anexo.";	progresso += 3;	Thread.sleep(100);	
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
					
					mensagemProgresso = "Inserindo anexo.";
				}
			}progresso += 3;Thread.sleep(150);

			//assunto do email
			mailMessage.setSubject(assunto);	mensagemProgresso = "Inserindo assunto.";	progresso += 3;	Thread.sleep(200); 

			//seleciona o conteudo 
			mailMessage.setContent(mp);	progresso += 3;	 mensagemProgresso = "Selecionando conte�do..";	Thread.sleep(175);

			mensagemProgresso = "Enviando mensagem...";
			//envia o email
			Transport.send(mailMessage);	progresso = 100;	Thread.sleep(100);
//			JOptionPane.showMessageDialog(null, "Email Enviado com Sucesso", "E-mail", JOptionPane.INFORMATION_MESSAGE);

		}catch (Exception e) {
//			JDProgressBar frame = new JDProgressBar(this);
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel enviar a mensagem\nERRO: " + e.getMessage() + 
					"\nClasse do erro: " + e.getClass(), "Erro ao enviar E-mail", JOptionPane.ERROR_MESSAGE);
		}

	}

}
