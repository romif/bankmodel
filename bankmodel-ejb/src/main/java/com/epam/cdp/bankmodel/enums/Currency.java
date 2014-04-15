package com.epam.cdp.bankmodel.enums;

/**
 * The Class Currency.
 * @author Roman_Konovalov
 *
 */
public enum Currency {

	/**
	 * Euro.
	 */
	EURO(1),

	/**
	 * Dollar.
	 */
	DOLLAR(1.3),

	/**
	 * Rubble.
	 */
	RUBBLE(15000);

	/**
	 * Rate.
	 */
	private double rate;

	/**
	 * Constructor.
	 * @param rate
	 *            the rate
	 */
	Currency(final double rate) {
		this.rate = rate;
	}

	/**
	 * @return rate
	 */
	public double getRate() {
		return rate;
	}

}
