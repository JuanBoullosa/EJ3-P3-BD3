package accesoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;



public class AccesoBD {

	public List<Examen> listaExamenes (Connection con) throws SQLException
	{
		//defino como una lista
		List<Examen> lista = new LinkedList<>();

		try
		{
			//ejecuto la consola
			Consultas cons = new Consultas ();
			String query = cons.listarExamenes();
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			//recorro el resulset y cargo la lista de examenes
			while(rs.next())
			{
				String codigo = rs.getString("codigo");
				String materia = rs.getString("materia");
				String periodo = rs.getString("periodo");

				Examen exa = new Examen (codigo,materia,periodo);
				lista.add(exa);
			}
			rs.close();
			pstmt.close();

		}
		catch( SQLException e)
		{
			e.printStackTrace();

		}
		return lista;
	}


	public void ingresarResultado (Connection con, Resultado resu) throws SQLException
	{
		try
		{
			//ejecuto la consutla
			Consultas cons = new Consultas();
			String query = cons.insertarResultados();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, resu.getCedula());
			pstmt.setString(2,resu.getCodigo());
			pstmt.setInt(3, resu.getCalificacion());
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public List<Resultado> listarResultados(Connection con, int cedula)	throws SQLException
	{
		List<Resultado> lista = new LinkedList();
		try 
		{
			Consultas cons = new Consultas();
			String query = cons.listarResultados();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, cedula); //aca pongo como que la segunda columna sea solo de cedula! que pasan como parametro
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				String codigo = rs.getString("codigo");
				int ced = rs.getInt("cedula");
				int calificacion = rs.getInt("calificacion");
				Resultado resu = new Resultado(codigo, ced, calificacion);
				lista.add(resu);
			}
			rs.close();
			pstmt.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return lista;
		
	}



}
