package Package;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class main {

	public static void main(String[] args) {
		
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
			
		
			
			
			
			
			 /* finalmente, cierro la conexión */
			 con.close();

			
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
