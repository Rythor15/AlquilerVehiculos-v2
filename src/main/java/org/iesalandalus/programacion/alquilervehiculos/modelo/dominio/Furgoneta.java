package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Furgoneta extends Vehiculo {

	private static final int FACTOR_PLAZAS = 1;
	private static final int FACTOR_PMA = 100;

	private int plazas;
	private int pma;

	public Furgoneta(String marca, String modelo, int pma, int plazas, String matricula) {
		super(marca, modelo, matricula);
		setPma(pma);
		setPlazas(plazas);
	}

	public Furgoneta(Furgoneta furgoneta) {
		super(furgoneta);

		plazas = furgoneta.getPlazas();
		pma = furgoneta.getPma();

	}

	public int getPlazas() {
		return plazas;
	}

	private void setPlazas(int plazas) {

		if (plazas <= 1 || plazas > 9) {
			throw new IllegalArgumentException("ERROR: Las plazas no son correctas.");
		}
		this.plazas = plazas;
	}

	public int getPma() {
		return pma;
	}

	private void setPma(int pma) {
		if (pma <= 100 || pma > 10000) {
			throw new IllegalArgumentException("ERROR: El PMA no es correcto.");
		}
		this.pma = pma;
	}

	@Override
	public int getFactorPrecio() {
		return pma / FACTOR_PMA + plazas * FACTOR_PLAZAS;
	}

	@Override
	public String toString() {
		return String.format("%s %s (%s kg, %s plazas) - %s", marca, modelo, pma, plazas, matricula);
	}

}
