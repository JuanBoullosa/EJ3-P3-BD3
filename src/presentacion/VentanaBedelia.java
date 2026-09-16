package presentacion;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import accesoBD.AccesoBD;
import accesoBD.Examen;
import accesoBD.PersistenciaException;
import accesoBD.Resultado;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaBedelia extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JList<String> list;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBedelia frame = new VentanaBedelia();
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
	public VentanaBedelia() {
		setTitle("Ingresar resultado de examen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Por favor, selecione el examen:");
		lblNewLabel.setBounds(10, 53, 179, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Cedula");
		lblNewLabel_1.setBounds(330, 80, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Calificacion");
		lblNewLabel_2.setBounds(330, 120, 79, 14);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(433, 77, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(433, 117, 86, 20);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Ingresar Resultado");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				/* aquí va el código que...
		1. obtiene el string del examen seleccionado por el usuario.
		2. extrae el substring correspondiente al código del examen.
		3. valida que la cédula tenga el formato adecuado.
		4. valida que la calificación sea un número entre 0 y 12.
		*/
		Connection con = null;
		try
		{
			/* obtengo driver, url, user, password de un archivo de
			configuracion */
			Properties p = new Properties();
			p.load(new FileInputStream("src/config.properties"));
			String driver = p.getProperty("driver");
			String url = p.getProperty("url") + "Bedelia";
			String user = p.getProperty("user");
			String password = p.getProperty("password");
			/* cargo el driver */
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			AccesoBD acceso = new AccesoBD();

			// 1. obtengo el string del examen seleccionado por el usuario
			String seleccionado = list.getSelectedValue();
			if (seleccionado == null)
			{
				JOptionPane.showMessageDialog(VentanaBedelia.this, "Debe seleccionar un examen de la lista.");
				return;
			}

			// 2. extraigo el substring correspondiente al codigo del examen
			String codigo = seleccionado.substring(0, seleccionado.indexOf("-"));

			// 3. valido que la cedula tenga el formato adecuado (7 u 8 digitos)
			String cedulaTexto = textField.getText().trim();
			if (!cedulaTexto.matches("\\d{7,8}"))
			{
				JOptionPane.showMessageDialog(VentanaBedelia.this, "La cedula debe tener 7 u 8 digitos numericos.");
				return;
			}
			int cedula = Integer.parseInt(cedulaTexto);

			// 4. valido que la calificacion sea un numero entre 0 y 12
			int calificacion;
			try
			{
				calificacion = Integer.parseInt(textField_1.getText().trim());
			}
			catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(VentanaBedelia.this, "La calificacion debe ser un numero.");
				return;
			}
			if (calificacion < 0 || calificacion > 12)
			{
				JOptionPane.showMessageDialog(VentanaBedelia.this, "La calificacion debe estar entre 0 y 12.");
				return;
			}

			Resultado resu = new Resultado(codigo, cedula, calificacion);
			acceso.ingresarResultado(con, resu);
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
		btnNewButton.setBounds(332, 172, 187, 23);
		contentPane.add(btnNewButton);
		
		list = new JList<String>();
		list.setBounds(10, 79, 310, 136);
		contentPane.add(list);

		Connection con = null;
		try
		{
			/* obtengo driver, url, user, password de un archivo de
			configuracion */
			Properties p = new Properties();
			p.load(new FileInputStream("src/config.properties"));
			String driver = p.getProperty("driver");
			String url = p.getProperty("url") + "Bedelia";
			String user = p.getProperty("user");
			String password = p.getProperty("password");
			/* cargo el driver */
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			AccesoBD acceso = new AccesoBD();
			List <Examen> lista = acceso.listaExamenes(con);

			//Recorro la lista de examenes y la voy agregando al listado,
			//el usuario posteriormente elegira uno de ellos
			LinkedList<String> datosListado = new LinkedList<String>();
			Iterator <Examen> iter = lista.iterator();
			while(iter.hasNext())
			{
				Examen exa = iter.next();
				String cod = exa.getCodigo();
				String mat = exa.getMateria();
				String per = exa.getPeriodo();
				String datos = cod + "-" + mat + "-" + per;
				datosListado.add(datos);
			}
			list.setListData(datosListado.toArray(new String[0]));

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

}
