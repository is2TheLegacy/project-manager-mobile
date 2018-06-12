package alpha.proyectos.is2.fpuna.py.alpha.service.model;

public class CategoriaProyecto {

	private Short idCategoriaProyecto;
    private String nombre;
	
	public CategoriaProyecto() {
	}
    
    public Short getIdCategoriaProyecto() {
        return idCategoriaProyecto;
    }

    public void setIdCategoriaProyecto(Short idCategoriaProyecto) {
        this.idCategoriaProyecto = idCategoriaProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}