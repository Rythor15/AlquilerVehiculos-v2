package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;

public class ModeloCascada extends Modelo {

	public ModeloCascada(FactoriaFuenteDatos factoriaFuenteDatos) {
	super(factoriaFuenteDatos);

	}

	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		clientes.insertar(cliente);
	}

	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {

		vehiculos.insertar(vehiculo);
	}

	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}

		Cliente clienteEncontrado = clientes.buscar(alquiler.getCliente());
		Vehiculo vehiculoEncontrado = vehiculos.buscar(alquiler.getVehiculo());
		if (clienteEncontrado == null) {
			throw new OperationNotSupportedException("ERROR: No existe el cliente del alquiler.");
		}
		if (vehiculoEncontrado == null) {
			throw new OperationNotSupportedException("ERROR: No existe el turismo del alquiler.");
		}

		alquileres.insertar(new Alquiler(alquiler));

	}

	public Cliente buscar(Cliente cliente) {

		return clientes.buscar(cliente);

	}

	public Vehiculo buscar(Vehiculo vehiculo) {

		return vehiculos.buscar(vehiculo);

	}

	public Alquiler buscar(Alquiler alquiler) {

		return alquileres.buscar(alquiler);
	}

	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		clientes.modificar(cliente, nombre, telefono);
	}

	public void borrar(Cliente cliente) throws OperationNotSupportedException {

		for (Alquiler alquiler : alquileres.get(cliente)) {
			borrar(alquiler);
		}
		clientes.borrar(cliente);
	}

	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		for (Alquiler alquiler : alquileres.get(vehiculo)) {
			borrar(alquiler);
		}
		vehiculos.borrar(vehiculo);
	}

	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {

		alquileres.borrar(alquiler);

	}

	@Override
	void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		alquileres.devolver(cliente, fechaDevolucion);

	}

	@Override
	void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		alquileres.devolver(vehiculo, fechaDevolucion);

	}

	@Override
	List<Cliente> getListaClientes() {
		List<Cliente> listaCliente = new ArrayList<>();
		for (Cliente cliente : clientes.get()) {
			listaCliente.add(new Cliente(cliente));
		}
		return listaCliente;
	}

	@Override
	List<Vehiculo> getListaVehiculos() {
		List<Vehiculo> listaVehiculo = new ArrayList<>();
		for (Vehiculo vehiculo : vehiculos.get()) {
			listaVehiculo.add(Vehiculo.copiar(vehiculo));
		}
		return  listaVehiculo;
	}

	@Override
	List<Alquiler> getListaAlquileres() {
		List<Alquiler> listaAlquiler = new ArrayList<>();
		for (Alquiler alquiler : alquileres.get()) {
			listaAlquiler.add(new Alquiler(alquiler));
		}
		return listaAlquiler;
	}

	@Override
	List<Alquiler> getListaAlquileres(Cliente cliente) {
		List<Alquiler> listaAlquilerConCliente = new ArrayList<>();
		for (Alquiler alquiler : alquileres.get(cliente)) {
			listaAlquilerConCliente.add(new Alquiler(alquiler));
		}
		return listaAlquilerConCliente;
	}

	@Override
	List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		List<Alquiler> listaAlquilerConTurismo = new ArrayList<>();

		for (Alquiler alquiler : alquileres.get(vehiculo)) {
			listaAlquilerConTurismo.add(new Alquiler(alquiler));
		}
		return listaAlquilerConTurismo;
	}

}
