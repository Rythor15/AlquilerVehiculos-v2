package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.naming.OperationNotSupportedException;
import javax.swing.text.Document;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;

public class Clientes implements IClientes {

	private List<Cliente> coleccionCliente;
	private static final File FICHERO_CLIENTES = null;
	private static final String RAIZ = "";
	private static final String CLIENTE = "";
	private static final String NOMBRE = "";
	private static final String DNI = "";
	private static final String TELEFONO = "";
	private static Clientes instancia;

	private Clientes() {

		coleccionCliente = new ArrayList<>();

	}

	protected Clientes getInstancia() {
		return null;

	}

	public void comenzar() {

	}

	private void leerDom(Document documentoXml) {

	}

	private Cliente getCliente(Element elemento) {
		return null;

	}

	public void terminar() {

	}

	private Document crearDom() {
		return null;

	}

	private Element getElemento(Document documentoXml, Cliente cliente) {
		return null;

	}

	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionCliente);

	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (coleccionCliente.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}
		coleccionCliente.add(cliente);
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		int indiceLista = coleccionCliente.indexOf(cliente);
		Cliente comprobacionCliente;
		if (coleccionCliente.indexOf(cliente) == 0) {
			comprobacionCliente = coleccionCliente.get(indiceLista);
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
		if (!coleccionCliente.contains(cliente)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		} else {
			coleccionCliente.remove(cliente);
		}
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		if (!coleccionCliente.contains(cliente)) {
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
