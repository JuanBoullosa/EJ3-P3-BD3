package accesoBD;

public class Examen {

	private String codigo;
	private String materia;
	private String periodo;

	public Examen(String codigo, String materia, String periodo) {
		this.codigo = codigo;
		this.materia = materia;
		this.periodo = periodo;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getMateria() {
		return materia;
	}

	public String getPeriodo() {
		return periodo;
	}

	@Override
	public String toString() {
		return codigo + " - " + materia + " - " + periodo;
	}

}
