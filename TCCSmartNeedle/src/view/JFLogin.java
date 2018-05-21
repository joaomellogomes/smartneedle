package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DarkStar;

import model.Administrador;
import model.Funcionario;
import model.Login;
import model.DAO.AdministradorDAO;
import model.DAO.FuncionarioDAO;
import model.DAO.LoginDAO;

public class JFLogin extends JFrame {
	
	//Criação dos objetos
	AdministradorDAO admDAO = new AdministradorDAO();
	FuncionarioDAO funDAO = new FuncionarioDAO();
	LoginDAO loginDAO = new LoginDAO();

	private final JPanel contentPanel = new JPanel();
	private JTextField jtfLogin;
	private JPasswordField jpfSenha;
	private JComboBox jcbTipo = new JComboBox();
	
	int x, y;

	/**
	 * Create the dialog.
	 */
	public JFLogin() {
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JFLogin.class.getResource("/imagens/icone.png")));
		setResizable(false);
		setBounds(100, 100, 467, 412);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setForeground(new Color(102, 102, 102));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel pnlTitulo = new JPanel();
		pnlTitulo.setBorder(UIManager.getBorder("List.cellNoFocusBorder"));
		pnlTitulo.setBackground(new Color(51, 204, 204));
		pnlTitulo.setBounds(0, 0, 467, 72);
		contentPanel.add(pnlTitulo);
		pnlTitulo.setLayout(null);
		
		JLabel lblLogin = new JLabel("login Smart Needle");
		lblLogin.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				
				setLocation(getLocation().x + evt.getX() - x, getLocation().y + evt.getY() - y);
				
			}
		});
		lblLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				
				x = evt.getX();
				y = evt.getY();
				
			}
		});
		lblLogin.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		lblLogin.setFont(new Font("Segoe Print", Font.BOLD, 40));
		lblLogin.setForeground(new Color(255, 255, 255));
		lblLogin.setBounds(39, 6, 395, 60);
		pnlTitulo.add(lblLogin);
		
		//Adicionar imagem na label de tamanha 64x64
		ImageIcon usuario = new ImageIcon(JFLogin.class.getResource("/imagens/usuarioLogin.png"));
		
		Image user = usuario.getImage();
		Image us = user.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH);
		
		JLabel lblUsuario = new JLabel();
		lblUsuario.setIcon(new ImageIcon(us));
		
		lblUsuario.setBounds(37, 93, 76, 72);
		contentPanel.add(lblUsuario);
		
		//Adicionar imagem na label de tamanha 64x64
		ImageIcon senha = new ImageIcon(JFLogin.class.getResource("/imagens/senhaLogin.png"));
		Image password = senha.getImage();
		Image sen = password.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH);
				
		JLabel lblSenha = new JLabel();
		lblSenha.setIcon(new ImageIcon(sen));
		lblSenha.setBounds(37, 191, 76, 72);
		contentPanel.add(lblSenha);
		
		JPanel pnlUsuario = new JPanel();
		pnlUsuario.setBackground(Color.WHITE);
		pnlUsuario.setBounds(108, 111, 341, 44);
		contentPanel.add(pnlUsuario);
		pnlUsuario.setLayout(new GridLayout(0, 1, 0, 0));
		
		jtfLogin = new JTextField();
		jtfLogin.setFont(new Font("Arial", Font.PLAIN, 24));
		jtfLogin.setBorder(null);
		pnlUsuario.add(jtfLogin);
		jtfLogin.setColumns(10);
		
		JPanel pnlSenha = new JPanel();
		pnlSenha.setBackground(Color.WHITE);
		pnlSenha.setBounds(108, 211, 341, 44);
		contentPanel.add(pnlSenha);
		
		final JCheckBox jcbExibir = new JCheckBox("Exibir senha");
		jcbExibir.setFont(new Font("Segoe Print", Font.BOLD, 13));
		jcbExibir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				jpfSenha.grabFocus();
				if(jpfSenha.getEchoChar() == '*'){
					jpfSenha.setFont(new Font("SansSerif", Font.PLAIN, 25));
					jpfSenha.setForeground(Color.BLACK);
					jpfSenha.setEchoChar((char) 0);
				}else{
					jpfSenha.setFont(new Font("SansSerif", Font.BOLD, 36));
					jpfSenha.setForeground(new Color(51, 102, 102));
					jpfSenha.setEchoChar('*');
				}
				
			}
		});

		jcbExibir.setBounds(312, 267, 136, 28);
		contentPanel.add(jcbExibir);
		pnlSenha.setLayout(new GridLayout(0, 1, 0, 0));
		
		jpfSenha = new JPasswordField();
		jpfSenha.setForeground(new Color(51, 102, 102));
		jpfSenha.setFont(new Font("SansSerif", Font.BOLD, 36));
		jpfSenha.setBorder(null);
		pnlSenha.add(jpfSenha);
		
		JLabel lblTipo = new JLabel("Tipo: ");
		lblTipo.setForeground(new Color(0, 0, 0));
		lblTipo.setFont(new Font("Segoe Print", Font.BOLD, 15));
		lblTipo.setBounds(183, 309, 50, 30);
		contentPanel.add(lblTipo);
		
		jcbTipo.setFont(new Font("Arial", Font.BOLD, 12));
		jcbTipo.setModel(new DefaultComboBoxModel(new String[] {"Funcion\u00E1rio", "Administrador"}));
		jcbTipo.setBounds(247, 309, 202, 32);
		contentPanel.add(jcbTipo);
	
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			
			JButton btnEntrar = new JButton("Entrar");
			btnEntrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					//Armazena os valores lidos do formulário em variáveis (Login, Senha e Tipo de Pessoa)
					String loginUsuario = jtfLogin.getText();
					String senha = String.valueOf(jpfSenha.getPassword());
					String tipo = String.valueOf(jcbTipo.getSelectedItem());
					
					Login loginParcial = new Login(0, tipo, loginUsuario, LocalDateTime.now(), null, null);
					
					if(tipo.equals("Administrador")){
						
						final Administrador admAutenticado = admDAO.autenticar(loginUsuario, senha);
						
						if(!admAutenticado.getNome().equals("")){
							
							final Login loginCompleto = loginDAO.inserirLogin(loginParcial);
							
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										AWAdministrador window = new AWAdministrador(admAutenticado, loginCompleto);
										window.frmAdministrador.setVisible(true);
									} catch (Exception erro) {
										JOptionPane.showMessageDialog(null, "Não foi possível logar\nERRO: " + erro,
												"Erro ao logar", JOptionPane.ERROR_MESSAGE);
									}
								}
							});
							
							dispose();
						}else{
							JOptionPane.showMessageDialog(null, "Verifique se o login e/ou senha estão corretos!",
									"Login e/ou Senha inválido(s)!", JOptionPane.ERROR_MESSAGE);
							jtfLogin.grabFocus();
							jpfSenha.setText(null);//Limpar o campo de senha
						}
						
					}else if(tipo.equals("Funcionário")){
						
						final Funcionario funAutenticado = funDAO.autenticar(loginUsuario, senha);
						
						if(!funAutenticado.getNome().equals("")){
							
							final Login loginCompleto = loginDAO.inserirLogin(loginParcial);
							
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										AWFuncionario window = new AWFuncionario(funAutenticado, loginCompleto);
										window.frmFuncionario.setLocationRelativeTo(null);
										window.frmFuncionario.setVisible(true);
									} catch (Exception erro) {
										JOptionPane.showMessageDialog(null, "Não foi possível logar\nERRO: " + erro,
												"Erro ao logar", JOptionPane.ERROR_MESSAGE);
									}
								}
							});
							
							dispose();
						}else{
							JOptionPane.showMessageDialog(null, "Verifique se o login e/ou senha estão corretos!",
									"Login e/ou Senha inválido(s)!", JOptionPane.ERROR_MESSAGE);
							jtfLogin.grabFocus();
							jpfSenha.setText(null);//Limpar o campo de senha
						}
						
					}
					
				}
			});
			getRootPane().setDefaultButton(btnEntrar);
			btnEntrar.setFont(new Font("Segoe Print", Font.BOLD, 20));
			btnEntrar.setBackground(SystemColor.textHighlight);
			buttonPane.add(btnEntrar);
			
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					try {
						Thread.sleep(500);
						System.exit(0);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro ao encerrar!", "Erro desconhecido!", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			});
			btnCancelar.setFont(new Font("Segoe Print", Font.BOLD, 20));
			btnCancelar.setBackground(new Color(204, 204, 255));
			buttonPane.add(btnCancelar);
		}
		
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
}
