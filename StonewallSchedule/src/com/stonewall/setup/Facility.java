package com.stonewall.setup;

/**
 * Represents a Facility where games can be played in a sports league.
 * 
 * @author Taylor Smith
 *
 */
public class Facility {
	/**
	 * Number of games that can be played at the Facility during a given week.
	 */
	private int numGames;
	/** The name of the Facility. */
	private String name;

	/**
	 * Sets up a Facility with a given name and number of games.
	 * 
	 * @param name
	 *            Name of the Facility.
	 * @param numGames
	 *            Number of games that can be played at the Facility.
	 * @throws IllegalArgumentException
	 *             If name is empty or number of games is not greater than zero
	 */
	public Facility(String name, int numGames) {
		if (name.trim().isEmpty() || numGames <= 0) {
			throw new IllegalArgumentException();
		} else {
			this.name = name.trim();
			this.numGames = numGames;
		}
	}

	/**
	 * Gets the number of games that can be played at the Facility.
	 * 
	 * @return The number of games that can be played at the Facility.
	 */
	public int getNumGames() {
		return this.numGames;
	}

	/**
	 * Gets the name of the Facility.
	 * 
	 * @return The name of the Facility.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the Facility.
	 * 
	 * @param name
	 *            The new name of the Facility.
	 * @throws IllegalArgumentException
	 *             If the new name is empty.
	 */
	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			this.name = name.trim();
		}
	}

	/**
	 * Sets the number of games that can be played at the Facility.
	 * 
	 * @param numGames
	 *            The number of games that can be played at the Facility.
	 * @throws IllegalArgumentException
	 *             If the number of games is not greater than zero.
	 */
	public void setNumGames(int numGames) {
		if (numGames > 0) {
			this.numGames = numGames;
		} else {
			throw new IllegalArgumentException();
		}
	}
}
