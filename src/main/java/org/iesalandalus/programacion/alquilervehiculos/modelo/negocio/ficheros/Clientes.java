package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Clientes implements IClientes {

	private List<Cliente> coleccionClientes;
	private static final File FICHERO_CLIENTES = new File(
			String.format("%s%s%s%n", "datos", File.separator, "clientes.xml"));
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String TELEFONO = "telefono";
	private static Clientes instancia;

	private Clientes() {

		coleccionClientes = new ArrayList<>();

	}

	static Clientes getInstancia() {
		if (instancia == null) {
			instancia = new Clientes();
		}
		return instancia;

	}

	public void comenzar() {

	}

	private void leerDom(Document documentoXml) {

	}

	private Cliente getCliente(Element elemento) {
		return null;

	}

	public void terminar() {
		
		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_CLIENTES);

	}

	private Document crearDom() {
		
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for(Cliente cliente : coleccionClientes) {
				
				Element elementoClientes = getElemento(documentoXml, cliente);
				documentoXml.getDocumentElement().appendChild(elementoClientes);
			}
		}
		
		
		return documentoXml;

	}

	private Element getElemento(Document documentoXml, Cliente cliente) {

		Element elemento = documentoXml.createElement(CLIENTE);
		elemento.setAttribute(DNI, cliente.getDni());
		elemento.setAttribute(NOMBRE, cliente.getNombre());
		elemento.setAttribute(TELEFONO, cliente.getTelefono());

		return elemento;
	}

	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);

	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}
		coleccionClientes.add(cliente);
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		int indiceLista = coleccionClientes.indexOf(cliente);
		Cliente comprobacionCliente;
		if (coleccionClientes.indexOf(cliente) == 0) {
			comprobacionCliente = coleccionClientes.get(indiceLista);
		} else {
			comprobacionCliente = null;
		}

		return comprobacionCliente;
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (!coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		} else {
			coleccionClientes.remove(cliente);
		}
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		if (!coleccionClientes.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
		if (nombre != null && !nombre.isBlank()) {
			cliente.setNombre(nombre);
		}
		if (telefono != null && !telefono.isBlank()) {
			cliente.setTelefono(telefono);
		}
	}

}
