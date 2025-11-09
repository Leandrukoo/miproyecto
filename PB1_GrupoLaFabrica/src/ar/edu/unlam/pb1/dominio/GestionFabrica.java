package ar.edu.unlam.pb1.dominio;

import java.util.Arrays;

import ar.edu.unlam.pb1.dominio.enums.TipoProducto;

public class GestionFabrica {

	public static final String RESET = "\033[0m"; // Text Reset
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
	public static final String RED_BRIGHT = "\033[0;91m"; // RED
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN

	private boolean inicializado = false;
	private final int MAXIMO_PRODUCTOS = 10;
	private Producto[] productos = new Producto[MAXIMO_PRODUCTOS];
	private int[] cantidadDeCadaProducto = new int[4];
	private Robot rellenador, taponador, empaquetador;
	private boolean hayMateriaPrima;
	private int productosDefectuosos;
	private double promedioMenta, promedioFrutilla, promedioSandia, promedioCereza;

	public String inicializarFabrica() {
		String mensaje;
		if (!this.inicializado) {
			mensaje = GREEN_BOLD_BRIGHT + "\nFabrica inicializada" + RESET;
			this.rellenador = new Robot("Rellenador", "Rellenar");
			this.taponador = new Robot("Taponador", "Tapar");
			this.empaquetador = new Robot("Empaquetador", "Empaquetar");
			this.inicializado = true;
			this.productosDefectuosos = 0;
			this.promedioCereza = 0;
			this.promedioFrutilla = 0;
			this.promedioSandia = 0;
			this.promedioMenta = 0;
		} else {
			mensaje = RED_BRIGHT + "\nLa fabrica ya fue inicializada." + RESET;
		}
		return mensaje;
	}

	public String fabricarRelleno(int cantidad) {
		String mensaje = RED_BRIGHT + "\nNo se pudo fabricar el relleno.\n" + RESET;
		if (this.inicializado) {
			this.rellenador.fabricarRelleno(cantidad);
			mensaje = GREEN_BOLD_BRIGHT + "\n¡Relleno fabricado con exito!\n" + RESET;
		}
		return mensaje;
	}

	public int obtenerCantidadRelleno() {
		return Robot.getCantidadPasta();
	}

	public String fabricarEnvase(int cantidad) {
		String mensaje = RED_BRIGHT + "\nNo se pudo fabricar el envase.\n";
		if (this.inicializado) {
			this.taponador.fabricarEnvase(cantidad);
			mensaje = GREEN_BOLD_BRIGHT + "\n¡Envase fabricado con exito!\n" + RESET;
		}
		return mensaje;
	}

	private Producto crearProducto(int capacidad, TipoProducto tipoProducto, int lote) {

		Producto pastaDental = null;
		if (this.inicializado) {
			pastaDental = new Producto(capacidad, tipoProducto, lote);
			this.empaquetador.fabricarProducto(capacidad);
		}

		return pastaDental;
	}

	private void agregarProducto(int capacidad, TipoProducto tipoProducto, int lote) {
		Producto pastaDental = crearProducto(capacidad, tipoProducto, lote);
		boolean agregado = false;
		int indice = 0;

		while (indice < this.productos.length && !agregado) {
			if (this.productos[indice] == null) {
				this.productos[indice] = pastaDental;
				agregado = true;

			}
			indice++;
		}
	}

	public String fabricarProductoValido(int capacidad, TipoProducto tipoProducto, int lote) {
		String mensaje = "";
		if (!this.inicializado) {
			mensaje = RED_BRIGHT + "\nNo se pudo fabricar el producto, inicialice la fabrica primero." + RESET;

		} else if (!this.comprobarMateriaPrima()) {
			mensaje = RED_BRIGHT + "\nNo se dispone del material necesario para fabricar el producto.\n" + RESET;
		} else if (!this.encontrarProductoDefectuoso()) {
			mensaje = GREEN_BOLD_BRIGHT + "\n¡Producto agregado correctamente!" + RESET;
			this.agregarProducto(capacidad, tipoProducto, lote);
		} else {
			mensaje = RED_BRIGHT + "\n¡El producto ha salido defectuoso!" + RESET;
			this.productosDefectuosos++;
		}
		return mensaje;
	}

	public boolean comprobarMateriaPrima() {
		if (Robot.cantidadPasta <= 0 || Robot.cantidadEnvases <= 0) {
			this.hayMateriaPrima = false;
		} else {
			this.hayMateriaPrima = true;
		}
		return this.hayMateriaPrima;
	}

	public void promediosPorTipo() {
		int total = 0;
		double m = 0;
		double f = 0;
		double s = 0;
		double c = 0;
		for (int i = 0; i < this.productos.length; i++) {
			if (this.productos[i] != null) {
				total++;
			}
		}
		for (int j = 0; j < total; j++) {
			switch (this.productos[j].getTipoProducto()) {
			case MENTA:
				m++;
				this.promedioMenta = (m / total) * 100;
				break;
			case FRUTILLA:
				f++;
				this.promedioFrutilla = (f / total) * 100;
				break;
			case SANDIA:
				s++;
				this.promedioSandia = (s / total) * 100;
				break;
			case CEREZA:
				c++;
				this.promedioCereza = (c / total) * 100;
				break;
			}
		}
	}

	private boolean encontrarProductoDefectuoso() {
		int posicionMax = 10, posicionMin = 1;
		boolean esDefectuoso = false;
		int numeroAleatorio = (int) ((Math.random() * posicionMax) + posicionMin);

		if (numeroAleatorio <= 2) {
			esDefectuoso = true;
		}
		return esDefectuoso;
	}

	private Producto[] ordenarProductosPorLoteAscendente(Producto[] productos) {

		Producto auxiliar = null;

		for (int i = 0; i < productos.length; i++) {
			for (int j = 0; j < productos.length - (i + 1); j++) {
				if (productos[j] != null && productos[j + 1] != null
						&& productos[j].getLote() > productos[j + 1].getLote()) {
					auxiliar = productos[j];
					productos[j] = productos[j + 1];
					productos[j + 1] = auxiliar;
				}
			}
		}

		return productos;
	}

	public Producto[] obtenerProductosPorTipo(TipoProducto producto) {
		Producto[] pastaCiertoTipo = new Producto[this.productos.length];
		int posicion = 0;
		for (int i = 0; i < this.productos.length; i++) {
			if (this.productos[i] != null && this.productos[i].getTipoProducto().equals(producto)) {
				pastaCiertoTipo[posicion++] = this.productos[i];
			}
		}
		return ordenarProductosPorLoteAscendente(pastaCiertoTipo);
	}

	public boolean getInicializado() {
		return inicializado;
	}

	public boolean getHayMateriaPrima() {
		return hayMateriaPrima;
	}

	public int getProductosDefectuosos() {
		return productosDefectuosos;
	}

	public void setProductosDefectuosos(int productosDefectuosos) {
		this.productosDefectuosos = productosDefectuosos;
	}

	public double getPromedioMenta() {
		return promedioMenta;
	}

	public double getPromedioFrutilla() {
		return promedioFrutilla;
	}

	public double getPromedioSandia() {
		return promedioSandia;
	}

	public double getPromedioCereza() {
		return promedioCereza;
	}

	public String toString() {
		return "GestionFabrica [inicializado=" + inicializado + ", MAXIMO_PRODUCTOS=" + MAXIMO_PRODUCTOS
				+ ", productos=" + Arrays.toString(productos) + ", cantidadDeCadaProducto="
				+ Arrays.toString(cantidadDeCadaProducto);
	}

}
