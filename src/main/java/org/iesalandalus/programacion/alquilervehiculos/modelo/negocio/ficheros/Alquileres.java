package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres {
	private List<Alquiler> coleccionAlquileres;

	public Alquileres() {

		coleccionAlquileres = new ArrayList<>();

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
						throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
					}
				}
			} else {
				if (alquiler.getFechaDevolucion().isAfter(fechaAlquiler)
						|| alquiler.getFechaDevolucion().isEqual(fechaAlquiler)) {
					if (alquiler.getCliente().equals(cliente)) {
						throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
					} else {
						if (alquiler.getVehiculo().equals(vehiculo)) {
							throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
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
