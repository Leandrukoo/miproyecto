package ar.edu.unlam.pb1.dominio;

public class Robot {

	// atributos
	private int velocidad;
	private String modelo;
	private String tipoDeTarea;
	public static int cantidadPasta = 0;
	public static int cantidadEnvases = 0;
	public static int cantidadProductoFinal = 0;

	// constructor

	public Robot(String modelo, String tarea) {
		this.velocidad = 10;
		this.modelo = modelo;
		this.tipoDeTarea = tarea;

	}

	public static int getCantidadPasta() {
		return cantidadPasta;
	}

	public String getTipoDeTarea() {
		return tipoDeTarea;
	}

	public void fabricarRelleno(int cantidad) {
		Robot.cantidadPasta += cantidad;

	}

	public void fabricarEnvase(int cantidad) {
		Robot.cantidadEnvases += cantidad;

	}

	public void fabricarProducto(int cantidadRelleno) {
		Robot.cantidadProductoFinal += 1;
		Robot.cantidadEnvases -= 1;
		Robot.cantidadPasta -= cantidadRelleno;
	}

	public String mostrarEstado() {
		return "\n=== Características del " + this.modelo + " ===\n" + "Velocidad de operación: " + this.velocidad
				+ " unidades/minuto\n";

	}
}
