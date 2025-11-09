package ar.edu.unlam.pb1.dominio;

import ar.edu.unlam.pb1.dominio.enums.TipoProducto;

public class Producto {

	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String RESET = "\033[0m"; // Text Reset

	private int capacidadDelEnvase;
	private TipoProducto tipoProducto;
	private int lote;// 001 y 002

	public Producto(int capacidad, TipoProducto tipoProducto, int lote) {
		this.capacidadDelEnvase = capacidad;
		this.tipoProducto = tipoProducto;
		this.lote = lote;

	}

	public int getLote() {
		return lote;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public int getCapacidad() {
		return capacidadDelEnvase;
	}

	public void setCapacidad(int capacidad) {
		this.capacidadDelEnvase = capacidad;
	}

	public String toString() {
		return YELLOW_BOLD_BRIGHT + "\n== Informacion Pasta Dental ==" + RESET + "\nCapacidad: " + capacidadDelEnvase
				+ " g" + "\nTipo: " + tipoProducto + "\nLote: " + lote;
	}

}