package ar.edu.unlam.pb1.interfaz;

import java.util.Scanner;

import ar.edu.unlam.pb1.dominio.GestionFabrica;
import ar.edu.unlam.pb1.dominio.Producto;
import ar.edu.unlam.pb1.dominio.enums.TipoProducto;
import ar.edu.unlam.pb1.interfaz.enums.MenuPrincipal;

public class PruebaFabrica {

	public static final String RESET = "\033[0m"; // Text Reset
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE public static final String GREEN_BACKGROUND =
																	// "\033[42m"; // GREEN
	public static final String RED_BRIGHT = "\033[0;91m"; // RED
	public static final String CYAN_BRIGHT = "\033[0;96m"; // CYAN
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN

	/*
	 * EQUIPO LA FABRICA: Gestion de fabrica de Pastas Dentales "Wonka".
	 * 
	 * Silvana Paola Topatigh, Mauricio Chambi Delgado, Lucas Marron, Anna Sol
	 * Manfredi,Castro Leandro.
	 * 
	 * Desarrollamos un producto software que gestiona una fábrica de pasta dental
	 * en la cual un robot automata realiza las tareas (empaquetar,rellenar,taponar
	 * y fabricar las materias primas). Se pueden establecer las características
	 * cómo el sabor,capacidad del envase y su respectivo lote.
	 */

	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		GestionFabrica gestion = new GestionFabrica();
		mostrarPorPantalla(CYAN_BRIGHT + "\n!Bienvenido a la fabrica de Pastas Dentales WONKA!\n" + RESET);
		MenuPrincipal opcion = null;

		inicializarFabrica(gestion);

		do {
			menu();
			opcion = opcionMenuValidada();

			switch (opcion) {
			case FABRICAR_RELLENO_Y_ENVASE:
				fabricarRellenoYEnvase(gestion);
				break;
			case FABRICAR_NUEVO_PRODUCTO:
				crearProducto(gestion);
				break;
			case ESTADISTICAS:
				mostrarEstadisticas(gestion);
				break;
			case PRODUCTOS_DEFECTUOSOS:
				administrarDefectuosos(gestion);
				break;
			case MOSTRAR_PRODUCTOS_POR_TIPO:
				mostrarProductosPorTipo(gestion);
				break;
			case SALIR:
				mostrarPorPantalla(CYAN_BRIGHT + "\n\nGracias por confiar en Wonka.\n!Vuelva Pronto!" + RESET);
				break;
			}
		} while (!opcion.equals(MenuPrincipal.SALIR));

		teclado.close();

	}

	private static void inicializarFabrica(GestionFabrica gestion) {
		boolean inicializada = false;
		do {
			String respuesta = ingresarString("\nDesea inicializar la fabrica? (SI / NO):");
			if (respuesta.equalsIgnoreCase("Si")) {
				gestion.inicializarFabrica();
				inicializada = true;
			} else {
				mostrarPorPantalla("\nLa fabrica no comenzara a funcionar hasta que ingrese 'SI'.");
			}
		} while (!inicializada);

	}

	private static void fabricarRellenoYEnvase(GestionFabrica gestion) {
		int cantidad = ingresarEntero(WHITE_BOLD_BRIGHT + "\nIngrese la cantidad de relleno a fabricar (g): " + RESET);
		mostrarPorPantalla(gestion.fabricarRelleno(cantidad));
		cantidad = ingresarEntero(WHITE_BOLD_BRIGHT + "\nIngrese la cantidad de envases a fabricar: " + RESET);
		mostrarPorPantalla(gestion.fabricarEnvase(cantidad));
	}

	private static void crearProducto(GestionFabrica gestion) {
		int lote = 0;
		int capacidad = 0;

		if (gestion.comprobarMateriaPrima()) {

			mostrarPorPantalla(YELLOW_BOLD_BRIGHT + "\n=== Fabricacion de Pastas dentales ===\n" + RESET);
			TipoProducto tipoProducto = ingresarTipoDePastaValidada();

			do {
				capacidad = ingresarEntero(WHITE_BOLD_BRIGHT + "\nIngrese capacidad (g):" + RESET);

				if (capacidad <= gestion.obtenerCantidadRelleno()) {
					mostrarPorPantalla(GREEN_BOLD_BRIGHT + "\n¡Creado con exito!" + RESET);
				} else {
					mostrarError("Error excede la cantidad disponible.");
					mostrarPorPantalla("Cantidad disponible: " + gestion.obtenerCantidadRelleno());
				}

			} while (capacidad > gestion.obtenerCantidadRelleno());

			lote = ingresarLoteValidado();
			mostrarPorPantalla(gestion.fabricarProductoValido(capacidad, tipoProducto, lote));

		} else {
			mostrarPorPantalla(
					RED_BRIGHT + "\nNo se dispone del material necesario para fabricar el producto.\n" + RESET);
			fabricarRellenoYEnvase(gestion);
		}
	}

	private static void mostrarEstadisticas(GestionFabrica gestion) {
		gestion.promediosPorTipo();
		mostrarPorPantalla(WHITE_BOLD_BRIGHT + "\nEl porcentaje de productos de cada sabor es: " + RESET + "\nMenta: %"
				+ gestion.getPromedioMenta() + "\nFrutilla: %" + gestion.getPromedioFrutilla() + "\nSandia: %"
				+ gestion.getPromedioSandia() + "\nCereza: %" + gestion.getPromedioCereza());
	}

	private static void administrarDefectuosos(GestionFabrica gestion) {
		String eleccion;
		if (gestion.getProductosDefectuosos() <= 0) {
			mostrarPorPantalla(WHITE_BOLD_BRIGHT + "\n¡No se fabricaron productos defectuosos!" + RESET);
		} else {
			mostrarPorPantalla("\nActualmente hay " + RED_BRIGHT + gestion.getProductosDefectuosos() + RESET
					+ " productos defectuosos.");
			do {
				eleccion = ingresarString("\n¿Desea eliminar los productos defectuosos? (SI / NO): ");
				switch (eleccion) {
				case "SI":
					mostrarPorPantalla("\n¡Productos eliminados con exito!");
					gestion.setProductosDefectuosos(0);
					break;
				case "NO":
					mostrarPorPantalla("\nProductos defectuosos conservados en la cantidad actual.");
					break;

				default:
					mostrarError(RED_BRIGHT + "\nPor favor, ingrese una opcion valida." + RESET);
					break;
				}
			} while (!eleccion.equalsIgnoreCase("SI") && !eleccion.equalsIgnoreCase("NO"));
		}
	}

	private static void mostrarProductosPorTipo(GestionFabrica gestion) {
		TipoProducto producto = ingresarTipoDePastaValidada();
		mostrarProductos(gestion.obtenerProductosPorTipo(producto));

	}

	private static int ingresarLoteValidado() {
		int lote;
		do {
			lote = ingresarEntero(WHITE_BOLD_BRIGHT + "\nIngrese lote 001 o 002: " + RESET);
		} while (lote != 001 && lote != 002);
		return lote;
	}

	private static void mostrarError(String string) {
		System.err.println(string);
	}

	private static void mostrarPorPantalla(String mensaje) {
		System.out.println(mensaje);
	}

	private static String ingresarString(String string) {
		System.out.println(string);
		return teclado.next().trim().toUpperCase();
	}

	private static int ingresarEntero(String mensaje) {
		mostrarPorPantalla(mensaje);
		return teclado.nextInt();
	}

	private static void menu() {
		mostrarPorPantalla("\n--------------\n" + YELLOW_BOLD_BRIGHT + "\nMenu Principal" + RESET
				+ "\n¿Que desea realizar?" + RESET);
		for (int i = 0; i < MenuPrincipal.values().length; i++) {
			mostrarPorPantalla((i + 1) + ". " + MenuPrincipal.values()[i].getDescripcion());
		}
	}

	private static MenuPrincipal opcionMenuValidada() {
		int opcion = 0;
		do {
			opcion = ingresarEntero(WHITE_BOLD_BRIGHT + "\nIngrese la opcion deseada: " + RESET);
		} while (opcion < 0 || opcion > MenuPrincipal.values().length);
		return MenuPrincipal.values()[opcion - 1];
	}

	private static TipoProducto ingresarTipoDePastaValidada() {

		mostrarPastasDentales();

		int opcion = 0;
		do {
			opcion = ingresarEntero(WHITE_BOLD_BRIGHT + "\nIngrese la opcion deseada: " + RESET);

		} while (opcion <= 0 || opcion > TipoProducto.values().length);

		return TipoProducto.values()[opcion - 1];

	}

	private static void mostrarPastasDentales() {
		mostrarPorPantalla(WHITE_BOLD_BRIGHT + "\nElija tipo de pasta:" + RESET);
		for (int i = 0; i < TipoProducto.values().length; i++) {
			mostrarPorPantalla((i + 1) + ". " + TipoProducto.values()[i].getEleccion());
		}
	}

	public boolean validarEleccion(int eleccion) {
		boolean estado = false;
		if (eleccion > 0 && eleccion < TipoProducto.values().length) {
			estado = true;
		}
		return estado;
	}

	private static void mostrarProductos(Producto[] pastasDentales) {

		for (int i = 0; i < pastasDentales.length; i++) {
			if (pastasDentales[i] != null) {
				mostrarPorPantalla(pastasDentales[i].toString());
			}
		}

	}
}
