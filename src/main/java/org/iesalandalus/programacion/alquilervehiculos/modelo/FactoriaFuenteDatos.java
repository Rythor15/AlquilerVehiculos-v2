package org.iesalandalus.programacion.alquilervehiculos.modelo;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.FuenteDatosFicheros;

public enum FactoriaFuenteDatos {

	MEMORIA {
		@Override
		public IFuenteDatos crear() {
			return new FuenteDatosFicheros();
		}
	};
	
	public abstract IFuenteDatos crear();
	
}
