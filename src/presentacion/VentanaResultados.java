package presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import accesoBD.AccesoBD;
import accesoBD.Resultado;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class VentanaResultados extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaResultados frame = new VentanaResultados();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaResultados() {
		setTitle("Resultados de examenes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cedula:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 26, 79, 22);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(64, 28, 119, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {

				Connection con = null;
				try
				{
					//Leo la cedula del JTextField
					int cedula = Integer.parseInt(textField.getText());
					
					//Creo la conexion
					Properties p = new Properties();
					p.load(new FileInputStream("src/config.properties"));
					String driver = p.getProperty("driver");
					String url = p.getProperty("url") + "Bedelia";
					String user = p.getProperty("user");
					String password = p.getProperty("password");
					/* cargo el driver */
					Class.forName(driver);
					con = DriverManager.getConnection(url, user, password);
					
					//Creo AccesoBD
					AccesoBD acceso = new AccesoBD();
					
					//Obtengo mi lista
					List<Resultado> lista = acceso.listarResultados(con, cedula);
					
					// Creo las columnas de la tabla
		            String[] columnas = {
		                "Codigo",
		                "Cedula",
		                "Calificacion"
		            };
		            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		            
		            
		            // Recorro tu lista
		            for (Resultado r : lista) {

		                Object[] fila = {
		                    r.getCodigo(),
		                    r.getCedula(),
		                    r.getCalificacion()
		                };

		                modelo.addRow(fila);
		            }

		            // Muestro el modelo en la JTable
		            table.setModel(modelo);

		 
					
				}
				catch (NumberFormatException e) {
				    JOptionPane.showMessageDialog(null, "La cedula debe ser un numero");
				}
				catch (FileNotFoundException e)
				{
				/* si no encuentra el archivo de configuracion */
				e.printStackTrace();
				}
				catch (IOException e)
				{
				/* si hay problema al leer el archivo de configuracion */
				e.printStackTrace();
				}
				catch (SQLException e)
				{
				/* si hay algun problema vinculado al DBMS o la BD */
				e.printStackTrace();
				}
				catch (ClassNotFoundException e)
				{
				/* si no se puede hallar la clase correspondiente al driver */
				e.printStackTrace();
				}
				finally
				{
					try
					{
						/* en cualquier caso, cierro la conexion */
						if (con != null)
							con.close();
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}
				}

				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(229, 27, 89, 23);
		contentPane.add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 82, 312, 151);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
