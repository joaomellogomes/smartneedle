package view;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;

import model.Email;
import java.awt.event.MouseMotionAdapter;
import java.awt.Cursor;

public class JDProgressBar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	int x, y;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		File[] anexo = null;
//		try {
//			JDProgressBar dialog = new JDProgressBar(new Email("Gmail", "smtp.gmail.com","465","joaomellogomes@gmail.com", "Joao", "N1nteressa","joaomellogomes@gmail.com","assunto","mensagem", anexo));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//			dialog.setLocationRelativeTo(null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public JDProgressBar(Email emailEnviar) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDEnviarEmail.class.getResource("/imagens/icone.png")));
		setUndecorated(true);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 0, 0);
		getContentPane().add(contentPanel);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		CustomPanel cpnProgresso = new CustomPanel();
		cpnProgresso.setBounds(72, 30, 275, 251);
		getContentPane().add(cpnProgresso);
		cpnProgresso.setLayout(null);
		
		JLabel lblMensagemProgresso = new JLabel("");
		lblMensagemProgresso.setFont(new Font("Arial", Font.PLAIN, 20));
		lblMensagemProgresso.setBorder(new TitledBorder(null, "Progresso:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblMensagemProgresso.setBounds(6, 307, 417, 61);
		getContentPane().add(lblMensagemProgresso);
		
		JPanel pnlPai = new JPanel();
		pnlPai.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		pnlPai.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				
				setLocation(getLocation().x + evt.getX() - x, getLocation().y + evt.getY() - y);
				
			}
		});
		pnlPai.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				
				x = evt.getX();
				y = evt.getY();
				
			}
		});
		pnlPai.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlPai.setBounds(0, 0, 445, 427);
		getContentPane().add(pnlPai);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				Email email = new Email();
				try {

					Thread.sleep(1200);

					//Criação do objeto do tipo Email somente para verificação do progresso do envio
					//					Email email = new Email();
					emailEnviar.start();

					new Thread(new Runnable() {
						@Override
						public void run() {

//							for(int num = 0; num <= 100; num++) {
//								try {
//									cpnProgresso.UpdateProgressBar(num);
//									cpnProgresso.repaint();
//									Thread.sleep(50);
//									email.setProgresso(email.getProgresso() + 1);
//								} catch (InterruptedException erro) {
//									JOptionPane.showMessageDialog(null, "Um erro inesperado ocorreu: \n" + erro , "Erro desconhecido!", JOptionPane.ERROR);
//								}
//							}

							int num = 0;
							while(num <= 100) {
								
								int prec = num;
								
								num = emailEnviar.getProgresso();
								
//								if(prec == num) {
//									dispose();
//									break;
//								}
								
								for(int i = num; i <= 100; i++) {
									cpnProgresso.UpdateProgressBar(emailEnviar.getProgresso());
									cpnProgresso.repaint();
									lblMensagemProgresso.setText(emailEnviar.getMensagemProgresso());
									
									try {
										Thread.sleep(50);
									}catch(Exception erro) {
										JOptionPane.showMessageDialog(null, "Erro: \n" + erro ,
												"Erro verificar progresso!", JOptionPane.ERROR_MESSAGE);
									}
									
								}
								
								cpnProgresso.UpdateProgressBar(emailEnviar.getProgresso());
								cpnProgresso.repaint();
								
								if(num == 100) {
									JOptionPane.showMessageDialog(null, "Email Enviado com Sucesso", "E-mail", JOptionPane.INFORMATION_MESSAGE);
									dispose();
									break;
								}
							}

						}
					}).start();
					
				}catch(Exception erro) {
					JOptionPane.showMessageDialog(null, "Um erro ocorreu: \n" + erro, "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
					dispose();
				}

			}
		});
		setTitle("ProgressBar Circular JDialog Email");
		setBounds(100, 100, 445, 427);

		try{
			PlasticLookAndFeel.setPlasticTheme(new DarkStar() {
			});
			try{

				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			}catch(InstantiationException erro){
				Logger.getLogger(JDProgressBar.class.getName()).log(Level.SEVERE, null, erro);
			}catch(IllegalAccessException erro){
				Logger.getLogger(JDProgressBar.class.getName()).log(Level.SEVERE, null, erro);
			}catch(UnsupportedLookAndFeelException erro){
				Logger.getLogger(JDProgressBar.class.getName()).log(Level.SEVERE, null, erro);
			}
		}catch(ClassNotFoundException erro){
			JOptionPane.showMessageDialog(null, "Classe não encontrada", "Classe não encontrada!",
					JOptionPane.ERROR_MESSAGE);
		}
		SwingUtilities.updateComponentTreeUI(this);
		this.setBackground(SystemColor.darkGray);

	}
}
