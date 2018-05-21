package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import model.LoginTableModel;
import model.DAO.LoginDAO;
import javax.swing.border.TitledBorder;

public class JDLogsDeUsuarios extends JDialog {

	//Criação dos objetos
	LoginDAO loginDAO = new LoginDAO();

	private final JPanel contentPanel = new JPanel();
	private JTable jtbLogins;

	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		try {
	//			JDLogsDeUsuarios dialog = new JDLogsDeUsuarios();
	//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	//			dialog.setVisible(true);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * Create the dialog.
	 */
	public JDLogsDeUsuarios() {
		setTitle("Logs de usu\u00E1rios");
		setBounds(100, 100, 1000, 700);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JScrollPane jspLogs = new JScrollPane();
		TitledBorder bordaLogins = new TitledBorder(null, "Registro de logins", TitledBorder.LEADING, TitledBorder.TOP, null, null);
		bordaLogins.setTitleFont(new Font("Arial", Font.PLAIN, 26));
		jspLogs.setBorder(bordaLogins);
		jspLogs.setAutoscrolls(true);
		jspLogs.setBounds(11, 186, 963, 330);

		jtbLogins = new JTable();
		final LoginTableModel model = new LoginTableModel(loginDAO.listarLogins("", ""));
		
//		int linhas = jtbLogins.getRowCount(), coluna = 4, linha = -1;
//		boolean posse = false;
//		String valor = "01/01/0001 às 00:00:00";
		
		
//		for(int i = 0; i<linhas;i++){  
//			String item = (String) jtbLogins.getValueAt(0, coluna);  
//			if(item.equals(valor)) {
//				posse = true;
//				linha = i;
//			}  
//		}  
//
//		if(posse) {  
//			jtbLogins.setValueAt("Logado", linha, 4);
//			//se tem = true, quer dizer que contém o valor, então você faz aqui  
//			//oque você quer fazer, caso o valor ja exista  
//		}
		
		jtbLogins.setModel(model);
		jspLogs.setViewportView(jtbLogins);
		
		contentPanel.setLayout(null);
		contentPanel.add(jspLogs);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 

		        String str = (String) value;
		        if ("Logado".equals(str)) {
		            c.setForeground(new Color(51, 153, 0));
		            c.setFont(new Font("Arial", Font.BOLD, 13));
		        } else {
		            c.setForeground(Color.BLACK);
		        }
		        return c;
		    }
		};
		
		jtbLogins.getColumnModel().getColumn(4).setCellRenderer(renderer);
	}
}
