package accesoBD;

public class Consultas {

	public String listarExamenes()
	{
		String query = "SELECT * FROM examenes"; 
		return query;
	}
	
	public String insertarResultados()
	{
		String insert = "INSERT INTO resultados (cedula,codigo,calificacion) VALUES(?,?,?)";
		return insert;
	}
	public String listarResultados()
	{
		String query = "SELECT * FROM resultados WHERE cedula = ?"; 
		return query;
	}
	
}
