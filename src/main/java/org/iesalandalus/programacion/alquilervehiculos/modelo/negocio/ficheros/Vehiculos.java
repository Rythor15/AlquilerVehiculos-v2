package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Vehiculos implements IVehiculos {
	private List<Vehiculo> coleccionVehiculos;
	private static final File FICHERO_VEHICULOS = new File(
			String.format("%s%s%s%n", "datos", File.separator, "vehiculo.xml"));
	private static final String RAIZ = "vehiculos";
	private static final String VEHICULO = "vehiculo";
	private static final String MARCA = "marca";
	private static final String MODELO = "modelo";
	private static final String MATRICULA = "matricula";
	private static final String CILINDRADA = "cilindrada";
	private static final String PLAZAS = "plazas";
	private static final String PMA = "pma";
	private static final String TIPO = "tipo";
	private static final String TURISMO = "turismo";
	private static final String AUTOBUS = "autobus";
	private static final String FURGONETA = "furgoneta";
	private static Vehiculos instancia;

	public Vehiculos() {

		coleccionVehiculos = new ArrayList<>();

	}

	public void comenzar() {

	}

	private void leerDom(Document documentoXml) {

	}

	private Vehiculo getVehiculo(Element elemento) {
		return null;

	}

	public void terminar() {
		
		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_VEHICULOS);

	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for(Vehiculo vehiculo : coleccionVehiculos) {
				
				Element elementoVehiculos = getElemento(documentoXml, vehiculo);
				documentoXml.getDocumentElement().appendChild(elementoVehiculos);
			}
		}
		
		
		return documentoXml;

	}

	private Element getElemento(Document documentoXml, Vehiculo vehiculo) {
		Element elemento = documentoXml.createElement(VEHICULO);
		elemento.setAttribute(MARCA, vehiculo.getMarca());
		elemento.setAttribute(MATRICULA, vehiculo.getMatricula());
		elemento.setAttribute(MODELO, vehiculo.getModelo());

		return elemento;

	}

	static Vehiculos getInstancia() {
		if (instancia == null) {
			instancia = new Vehiculos();
		}
		return instancia;

	}

	@Override
	public List<Vehiculo> get() {
		return new ArrayList<>(coleccionVehiculos);

	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		if (coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		}
		coleccionVehiculos.add(vehiculo);
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {

		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		}
		Vehiculo comprobacionTurismo;
		int indiceLista = coleccionVehiculos.indexOf(vehiculo);
		if (coleccionVehiculos.indexOf(vehiculo) == 0) {
			comprobacionTurismo = coleccionVehiculos.get(indiceLista);
		} else {
			comprobacionTurismo = null;
		}

		return comprobacionTurismo;
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}
		if (!coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		} else {
			coleccionVehiculos.remove(vehiculo);
		}
	}

}
