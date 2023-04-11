package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Alquileres implements IAlquileres {

	private List<Alquiler> coleccionAlquileres;
	private static final File FICHERO_ALQUILERES = new File(
			String.format("%s%s%s%n", "datos", File.separator, "alquileres.xml"));
	private static final DateTimeFormatter FORMATO_FECHA = null;
	private static final String RAIZ = "alquileres";
	private static final String ALQUILER = "alquiler";
	private static final String CLIENTE = "cliente";
	private static final String VEHICULO = "vehiculo";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";
	private static Alquileres instancia;

	public Alquileres() {

		coleccionAlquileres = new ArrayList<>();

	}

	public void comenzar() {

	}

	private void leerDom(Document documentoXml) {

	}

	private Alquiler getAlquiler(Element elemento) {
		return null;

	}

	public void terminar() {

		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_ALQUILERES);
		
	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for(Alquiler alquiler : coleccionAlquileres) {
				
				Element elementoAlquileres = getElemento(documentoXml, alquiler);
				documentoXml.getDocumentElement().appendChild(elementoAlquileres);
			}
		}
		
		
		return documentoXml;

	}

	private Element getElemento(Document documentoXml, Alquiler alquiler) {
		Element elemento = documentoXml.createElement(ALQUILER);
		elemento.setAttribute(CLIENTE, alquiler.getCliente().getDni());
		elemento.setAttribute(FECHA_ALQUILER, alquiler.getFechaAlquiler().format(FORMATO_FECHA));
		elemento.setAttribute(FECHA_DEVOLUCION, alquiler.getFechaDevolucion().format(FORMATO_FECHA));
		elemento.setAttribute(VEHICULO, alquiler.getVehiculo().getMatricula());
		return elemento;

	}

	static Alquileres getInstancia() {
		if (instancia == null) {
			instancia = new Alquileres();
		}
		return instancia;

	}

	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);

	}

	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> listaCliente = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				listaCliente.add(alquiler);
			}
		}
		return listaCliente;

	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {
		List<Alquiler> listavehiculo = new ArrayList<>();

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				listavehiculo.add(alquiler);
			}
		}
		return listavehiculo;

	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {

		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getFechaDevolucion() == null) {
				if (alquiler.getCliente().equals(cliente)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				} else {
					if (alquiler.getVehiculo().equals(vehiculo)) {
						throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
					}
				}
			} else {
				if (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler)) {
					if (alquiler.getCliente().equals(cliente)) {
						throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
					} else {
						if (alquiler.getVehiculo().equals(vehiculo)) {
							throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
						}
					}
				}
			}
		}

	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		Alquiler alquilerDeCliente = getAlquilerAbierto(cliente);
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		if (alquilerDeCliente == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		coleccionAlquileres.get(coleccionAlquileres.indexOf(alquilerDeCliente)).devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {

		Alquiler alquilerAbierto = null;
		for (Iterator<Alquiler> iterador = coleccionAlquileres.iterator(); iterador.hasNext()
				&& alquilerAbierto == null;) {

			Alquiler alquiler = iterador.next();
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto = alquiler;
			}
		}

		return alquilerAbierto;

	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {

		Alquiler alquilerDeVehiculo = getAlquilerAbierto(vehiculo);
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		if (alquilerDeVehiculo == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		coleccionAlquileres.get(coleccionAlquileres.indexOf(alquilerDeVehiculo)).devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {

		Alquiler alquilerAbierto = null;
		for (Iterator<Alquiler> iterador = coleccionAlquileres.iterator(); iterador.hasNext()
				&& alquilerAbierto == null;) {

			Alquiler alquiler = iterador.next();
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				alquilerAbierto = alquiler;
			}
		}

		return alquilerAbierto;

	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {

		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		Alquiler comprobacionAlquiler;
		int indiceLista = coleccionAlquileres.indexOf(alquiler);
		if (coleccionAlquileres.indexOf(alquiler) == 0) {
			comprobacionAlquiler = coleccionAlquileres.get(indiceLista);
		} else {
			comprobacionAlquiler = null;
		}

		return comprobacionAlquiler;
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!coleccionAlquileres.contains(alquiler)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		} else {
			coleccionAlquileres.remove(alquiler);
		}
	}

}
