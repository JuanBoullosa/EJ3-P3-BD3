package accesoBD;

public class Resultado {

    private int cedula;
    private String codigo;
    private int calificacion;

    //constructor
    public Resultado(String codigo, int cedula, int calificacion){
        this.cedula=cedula;
        this.codigo=codigo;
        this.calificacion=calificacion;
    }
       
    public int getCedula()
    {
        return cedula;
    }
    public String getCodigo()
    {
        return codigo;
    }
    public int getCalificacion()
    {
        return calificacion;
    }

}


