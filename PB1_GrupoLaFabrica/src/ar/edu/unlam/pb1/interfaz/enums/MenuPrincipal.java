package ar.edu.unlam.pb1.interfaz.enums;

public enum MenuPrincipal {
	FABRICAR_RELLENO_Y_ENVASE("Fabricar relleno y envase"), FABRICAR_NUEVO_PRODUCTO("Fabricar nuevo producto"),
	ESTADISTICAS("Ver estadisticas de la fabrica"), PRODUCTOS_DEFECTUOSOS("Ver estado de productos defectuosos"),
	MOSTRAR_PRODUCTOS_POR_TIPO("Mostrar productos por tipo"), SALIR("Salir");

	private String descripcion;

	private MenuPrincipal(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
}