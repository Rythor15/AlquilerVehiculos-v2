package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.Alquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.Clientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.Vehiculos;

public abstract class Modelo {

	protected IClientes clientes;
	protected IVehiculos vehiculos;
	protected IAlquileres alquileres;
	protected IFuenteDatos fuenteDatos;

	protected Modelo(FactoriaFuenteDatos factoriaFuenteDatos) {

	}

	protected IClientes getClientes() {
		return clientes;

	}

	protected IVehiculos getVehiculos() {
		return vehiculos;

	}

	protected IAlquileres getAlquileres() {
		return alquileres;

	}

	protected void setFuenteDatos(IFuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

	public void comenzar() {

		clientes = fuenteDatos.crearClientes();
		vehiculos = fuenteDatos.crearVehiculos();
		alquileres = fuenteDatos.crearAlquileres();

	}

	public void terminar() {

		System.out.print("El modelo ha terminado ^^");

	}

	abstract void insertar(Cliente cliente) throws OperationNotSupportedException;

	abstract void insertar(Vehiculo vehiculo) throws OperationNotSupportedException;

	abstract void insertar(Alquiler alquiler) throws OperationNotSupportedException;

	abstract Cliente buscar(Cliente cliente);

	abstract Vehiculo buscar(Vehiculo vehiculo);

	abstract Alquiler buscar(Alquiler alquiler);

	abstract void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException;

	abstract void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException;

	abstract void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException;

	abstract void borrar(Cliente cliente) throws OperationNotSupportedException;

	abstract void borrar(Vehiculo vehiculo) throws OperationNotSupportedException;

	abstract void borrar(Alquiler alquiler) throws OperationNotSupportedException;

	abstract List<Cliente> getListaClientes();

	abstract List<Vehiculo> getListaVehiculos();

	abstract List<Alquiler> getListaAlquileres();

	abstract List<Alquiler> getListaAlquileres(Cliente cliente);

	abstract List<Alquiler> getListaAlquileres(Vehiculo vehiculo);

}
