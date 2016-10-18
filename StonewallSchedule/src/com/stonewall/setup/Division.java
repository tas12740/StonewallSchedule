package com.stonewall.setup;

import java.util.ArrayList;

/**
 * Represents a Division in a sports league.
 * 
 * @author Taylor Smith
 *
 */
public class Division {
	/** List of the teams in the Division, in no particular order. */
	private ArrayList<Team> teams;
	/** Number of the teams possible in the Division. */
	private int numTeams;
	/** Name of the Division. */
	private String name;

	/**
	 * Sets up a Division with a particular name and number of teams.
	 * 
	 * @param name
	 *            Name of the Division.
	 * @param numTeams
	 *            Number of teams in the Division.
	 * @throws IllegalArgumentException
	 *             If the given name is empty or the number of teams is not a
	 *             positive number.
	 */
	public Division(String name, int numTeams) {
		if (name.trim().isEmpty() || numTeams <= 0) {
			throw new IllegalArgumentException();
		} else {
			this.name = name;
			this.numTeams = numTeams;
		}
		teams = new ArrayList<Team>();
	}

	/**
	 * Gets the name of the Division.
	 * 
	 * @return The name of the Division.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the number of teams in the Division.
	 * 
	 * @return The number of teams in the Division.
	 */
	public int getNumTeams() {
		return this.numTeams;
	}

	/**
	 * Sets the name of the Division.
	 * 
	 * @param name
	 *            The new name of the Division.
	 * @throws IllegalArgumentException
	 *             If the name of the Division given is empty.
	 */
	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			this.name = name;
		}
	}

	/**
	 * Sets the number of teams in the Division.
	 * 
	 * @param numTeams
	 *            The new number of teams in the Division.
	 * @throws IllegalArgumentException
	 *             If the new number of teams is invalid.
	 */
	public void setNumTeams(int numTeams) {
		if (numTeams >= teams.size()) {
			this.numTeams = numTeams;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds a new team to the Division with a given name.
	 * 
	 * @param name
	 *            The name of the new team.
	 * @throws IllegalArgumentException
	 *             If the team has a blank name or if there is no space for the
	 *             team in the Division.
	 */
	public void addTeam(String name, int numGames) {
		if (teams.size() < numTeams) {
			try {
				teams.add(new Team(name, numGames));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Wipes a given week for all the teams.
	 * 
	 * @param week
	 *            The week to wipe.
	 */
	public void wipeWeek(int week) {
		for (int i = 0; i < teams.size(); i++) {
			teams.get(i).wipeWeek(week);
		}
	}

	/**
	 * Wipe the schedule of every team in the Division.
	 */
	public void wipeAll() {
		for (int i = 0; i < teams.size(); i++) {
			teams.get(i).wipeAll();
		}
	}

	/**
	 * Wipes weeks from the schedule of every team in the Division.
	 * 
	 * @param start
	 *            The start week to wipe.
	 * @param finish
	 *            The end week to wipe.
	 */
	public void wipeWeeks(int start, int finish) {
		for (int team = 0; team < teams.size(); team++) {
			for (int i = start; i <= finish; i++) {
				teams.get(team).wipeWeek(i);
			}
		}
	}

	/**
	 * Returns true if the schedule has failed.
	 * 
	 * @return True if any team in the division has a null week.
	 */
	public boolean doesTeamHaveNull() {
		for (int team = 0; team < teams.size(); team++) {
			Team t = teams.get(team);
			if (t.hasNull()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets a particular team in the Division.
	 * 
	 * @param teamToGet
	 *            The index of the team to get.
	 * @return The wanted team.
	 * @throws IllegalArgumentException
	 *             If the index of the team given is not acceptable.
	 */
	public Team getTeam(int teamToGet) {
		if (teamToGet >= 0 && teamToGet < teams.size()) {
			return teams.get(teamToGet);
		} else {
			System.out.println("Team to get: " + teamToGet);
			System.out.println("Size of division: " + teams.size());
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Removes a team from the Division.
	 * 
	 * @param teamToRemove
	 *            The index of the team to remove.
	 * @throws IllegalArgumentException
	 *             If the index of the team given is not acceptable.
	 */
	public void removeTeam(int teamToRemove) {
		if (teamToRemove >= 0 && teamToRemove < teams.size()) {
			teams.remove(teamToRemove);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the number of teams in the Division.
	 * 
	 * @return The number of teams in the Division.
	 */
	public int numberOfTeams() {
		return teams.size();
	}

	/**
	 * Removes all the bye "teams" from the Division.
	 */
	public void removeByes() {
		for (int i = 0; i < teams.size(); i++) {
			if (teams.get(i).getName().equals("Bye")) {
				teams.remove(i);
				numTeams--;
			}
		}
	}

	public int countByesSoFar(int weeks) {
		return teams.get(0).countByesSoFar(weeks);
	}

	public String[] getListTeams() {
		int size = teams.size();
		String[] list = new String[size];
		for (int i = 0; i < list.length; i++) {
			list[i] = teams.get(i).getName();
		}
		return list;
	}

	public ArrayList<String> game(int week) {
		ArrayList<String> games = new ArrayList<String>();
		for (int team = 0; team < numTeams; team++) {
			games.add(teams.get(team).game(week));
		}
		return games;
	}
}
