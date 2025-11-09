package ar.edu.unlam.pb1.dominio.enums;

public enum TipoProducto {
	MENTA("Menta"), FRUTILLA("Frutilla"), SANDIA("Sandia"), CEREZA("Cereza");

	private String eleccion;

	private TipoProducto(String eleccion) {
		this.eleccion = eleccion;
	}

	public String getEleccion() {
		return eleccion;
	}
}
