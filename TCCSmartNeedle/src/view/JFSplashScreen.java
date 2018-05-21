package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

public class JFSplashScreen extends JFrame {

	private JFrame mover;
	private static JLabel label1;          //label responsável por conter a imagem
	private ImageIcon imagem;              //imagem que será mostrada no label
	public JProgressBar progressBar = new JProgressBar();
	JLabel lblPorcentagem = new JLabel("");
	private ImageIcon iconeJanela = new ImageIcon(getClass().getResource("/imagens/icone.png"));
	
	//Variáveis para mover a SplashScreen
	int x = 0; int y = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFSplashScreen frame = new JFSplashScreen();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Construtor contendo a função responsável por carregar os dados da janela (layout e imagem)
	public JFSplashScreen() {
		
		setIconImage(iconeJanela.getImage());
		setResizable(false);
		//definindo o tipo de fechamento, o tamanho, tirando a barra de títulos, deixando no centro, definindo um icone e mostrando a janela
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setUndecorated(true);
	    setLocationRelativeTo(null);
		//Ativa o progressbar quando a janela estive ativa
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				//timer que incrementa a barra em 2s
				final Timer t = new Timer(20, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//para cada rodada do timer seta 1
						progressBar.setValue(progressBar.getValue() + 2);					
	
						for (int i = 0; i <= 100; i++) {
							
							lblPorcentagem.setText(String.valueOf(progressBar.getValue() + "%"));
						}
						
						pararProgressBar(500);
						pararProgressBar(350);
						
						if (progressBar.getValue() == 100) {
							//quando o pregressbar chegar em 100% para o timer
							((Timer) e.getSource()).stop();
							
						}
					}
				});
				t.start();
			}
		});

		//definindo o layout como nulo
		getContentPane().setLayout(null);
		setBounds(0,0,700,300); 

		//setando a imagem de splash
		imagem = new ImageIcon(getClass().getResource("/imagens/logo.png"));
		progressBar.setBackground(new Color(255, 255, 255));
		progressBar.setForeground(new Color(153, 255, 255));
		UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
		lblPorcentagem.setForeground(new Color(0, 51, 255));
		lblPorcentagem.setFont(new Font("Arial Black", Font.BOLD, 24));
		
		lblPorcentagem.setBounds(317, 232, 87, 37);
		getContentPane().add(lblPorcentagem);
		//    UIManager.put("ProgressBar.selectionBackground", )

		//adicionando a barra de progresso e mudando o tamanho
		progressBar.setBounds(0, 280, 700, 20);
		getContentPane().add(progressBar);

		//adicionando a imagem no label e mudando o tamanho
		label1 = new JLabel(imagem);
		label1.setBounds(0,0,700,300);
		
		//adicionando componentes na janela
		getContentPane().add(label1);
		
//		try{
//			PlasticLookAndFeel.setPlasticTheme(new DarkStar() {
//			});
//			try{
//				
//				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//			}catch(InstantiationException erro){
//				Logger.getLogger(JFSplashScreen.class.getName()).log(Level.SEVERE, null, erro);
//			}catch(IllegalAccessException erro){
//				Logger.getLogger(JFSplashScreen.class.getName()).log(Level.SEVERE, null, erro);
//			}catch(UnsupportedLookAndFeelException erro){
//				Logger.getLogger(JFSplashScreen.class.getName()).log(Level.SEVERE, null, erro);
//			}
//		}catch(ClassNotFoundException erro){
//			JOptionPane.showMessageDialog(null, "Classe não encontrada", "Classe não encontrada!",
//					JOptionPane.ERROR_MESSAGE);
//		}
//		SwingUtilities.updateComponentTreeUI(this);
//		this.setBackground(SystemColor.darkGray);

	}

	//função ao fechar a splash
	public void dispose(){
		super.dispose();
	}
	
	public void pararProgressBar(int tempo){
		int max = 99;
		int min = 5;
		int randomNum = min + (int)(Math.random() * (max - min));
		
		try {
			if( (randomNum % 2) == 1){
				randomNum = randomNum + 1;
				if(progressBar.getValue() == randomNum){
					Thread.sleep(tempo);
				}
			}
			
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar!", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}
}
