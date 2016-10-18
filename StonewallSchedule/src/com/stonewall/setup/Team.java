package com.stonewall.setup;

import java.util.Arrays;

/**
 * Represents a Team in a sports league.
 * 
 * @author Taylor Smith
 *
 */
public class Team {
	/** The name of the Team. */
	private String name;
	/** Represents the schedule of Teams that this team will play. */
	private Team[] schedule;
	/** The number of games the Team plays in the season. */
	private int numGames;

	private int byes = 0;

	/**
	 * Sets up a Team with a given name.
	 * 
	 * @param name
	 *            The name of the Team.
	 * @param numGames
	 *            The number of games the Team plays in the season.
	 * @throws IllegalArgumentException*
	 *             If the name is empty or if the number of games is not greater
	 *             than zero.
	 */

	public Team(String name, int numGames) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			this.name = name;
		}
		if (numGames > 0) {
			schedule = new Team[numGames];
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets the Team's name.
	 * 
	 * @return The Team's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the Team.
	 * 
	 * @param name
	 *            The new name of the Team.
	 * @throws IllegalArgumentException
	 *             If the name is empty.
	 */
	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			this.name = name;
		}
	}

	/**
	 * Sets the number of games in the season for a team.
	 * 
	 * @param numGames
	 *            The new number of games in the season for a team.
	 */
	public void setNumGames(int numGames) {
		if (numGames > 0) {
			schedule = Arrays.copyOf(schedule, numGames);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds a team to the schedule.
	 * 
	 * @param t
	 *            The team to add to the schedule.
	 * @param pos
	 *            The position to add the Team in the schedule.
	 * @throws IllegalArgumentException
	 *             if position is out of bounds or if there is a team in the
	 *             spot already.
	 */
	public void addToSchedule(Team t, int pos) {
		if (pos < 0 || pos >= schedule.length) {
			throw new IllegalArgumentException();
		} else if (schedule[pos] != null) {
			System.out.println(this.getName() + " added " + t.getName() + " at position " + pos);
			throw new IllegalArgumentException();
		} else {
			schedule[pos] = t;
		}
	}

	/**
	 * Returns the number of times a particular Team is in another Team's
	 * schedule.
	 * 
	 * @param t
	 *            The team that is being looked for.
	 * @return The number of times that team is in the schedule for another
	 *         Team.
	 */
	public int occurencesOf(Team t) {
		int occurrences = 0;
		for (int i = 0; i < schedule.length; i++) {
			if (schedule[i] != null) {
				if (schedule[i].getName().equals(t.getName())) {
					occurrences++;
				}
			}
		}
		return occurrences;
	}

	/**
	 * Returns true if the week in the schedule is occupied.
	 * 
	 * @param pos
	 *            The week in the schedule to look at.
	 * @return If the week is occupied.
	 */
	public boolean isWeekOccupied(int pos) {
		try {
			return (schedule[pos] != null);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Name: " + getName());
			System.out.println("Schedule size: " + schedule.length);
			System.out.println("Position: " + pos);
			return (schedule[pos] != null);
		}
	}

	/**
	 * Prints the team's schedule.
	 */
	public void printSchedule() {
		for (int i = 0; i < schedule.length; i++) {
			if (schedule[i] != null) {
				System.out.println("Week " + (i + 1) + ": " + schedule[i].getName());
			} else {
				System.out.println("ERROR");
			}
		}
	}

	/**
	 * Returns true if the team has a null in the schedule. Used to check if the
	 * scheduling process has failed.
	 * 
	 * @return True if the team has an empty week in the schedule.
	 */
	public boolean hasNull() {
		for (int week = 0; week < schedule.length; week++) {
			if (schedule[week] == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the current size of the schedule for the team.
	 * 
	 * @return The current size of the schedule.
	 */
	public int scheduleSize() {
		int count = 0;
		for (int i = 0; i < schedule.length; i++) {
			if (schedule[i] != null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Wipes the week of a given team.
	 * 
	 * @param week
	 *            The week to wipe.
	 */
	public void wipeWeek(int week) {
		if (week >= 0 && week < schedule.length) {
			schedule[week] = null;
		}
	}

	/**
	 * Wipes the entire schedule for a given Team.
	 */
	public void wipeAll() {
		for (int i = 0; i < schedule.length; i++) {
			wipeWeek(i);
		}
	}

	public int countByesSoFar(int weeks) {
		int count = 0;
		for (int week = 0; week < weeks; week++) {
			if (schedule[week] != null) {
				if (schedule[week].getName().equals("Bye")) {
					count++;
				}
			}
		}
		return count;
	}

	public int scheduleLength() {
		return schedule.length;
	}

	public String game(int week) {
		if (schedule[week] == null) {
			return "ERROR";
		} else {
			if (schedule[week].getName().contains("Bye")) {
				return getName() + " is on a bye";
			} else {
				return getName() + " vs " + schedule[week].getName();
			}
		}
	}

	public int scheduledGames() {
		int count = 0;
		for (int t = 0; t < schedule.length; t++) {
			Team team = schedule[t];
			if (team != null) {
				if (!team.getName().contains("Bye")) {
					count++;
				}
			}
		}
		return count;
	}

	public void addBye() {
		byes++;
	}

	public int getByes() {
		return byes;
	}

	public void removeByes() {
		byes = 0;
	}

	public void removeBye() {
		byes--;
	}

	public boolean isBye(int week) {
		if (schedule[week] != null) {
			return (schedule[week].getName().contains("Bye"));
		} else {
			return false;
		}
	}

	/**
	 * Hashcode method.
	 * 
	 * @return Haschode of this object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Compares two Team's names.
	 * 
	 * @param The
	 *            other team to compare.
	 * @return If the two teams are equal in name.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Team)) {
			return false;
		}
		Team other = (Team) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
