package com.stonewall.backendmanager;

import com.stonewall.setup.*;
import java.util.ArrayList;

/**
 * Manages the schedule on the back-end of the application.
 * 
 * @author Taylor Smith
 *
 */
public class ScheduleManager {
	/** A list of the divisions in the league. */
	private ArrayList<Division> divisions;
	/** A list of the facilities available to the league. */
	private ArrayList<Facility> facilities;
	/** Number of games that each team plays during the season. */
	private int numGamesSeason;
	/** Number of teams that plays at each facility during one game. */
	private int numTeamsFacility;

	/**
	 * Sets up a schedule with a given number of games per season for each team
	 * and a given number of teams at each facility.
	 * 
	 * @param numGamesSeason
	 *            The number of games each team plays per season.
	 * @param numTeamsFacility
	 *            The number of teams that plays at each facility during one
	 *            game.
	 * @throws IllegalArgumentException
	 *             If the number of games or number of teams at a facility is
	 *             not greater than zero.
	 */
	public ScheduleManager(int numGamesSeason, int numTeamsFacility) {
		if (numGamesSeason > 0 && numTeamsFacility > 0) {
			this.numGamesSeason = numGamesSeason;
			this.numTeamsFacility = numTeamsFacility;
		} else {
			throw new IllegalArgumentException();
		}
		divisions = new ArrayList<Division>();
		facilities = new ArrayList<Facility>();
	}

	/**
	 * Adds a division to the league with a given name and number of teams.
	 * 
	 * @param name
	 *            The name of the division.
	 * @param numTeams
	 *            The number of teams in the division.
	 * @throws IllegalArgumentException
	 *             If the division name or number of teams given is invalid.
	 */
	public void addDivision(String name, int numTeams) {
		try {
			divisions.add(new Division(name, numTeams));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets the division at the given index.
	 * 
	 * @param divisionToGet
	 *            The index of the division to get.
	 * @return The division at the given index.
	 * @throws IllegalArgumentException
	 *             If the position is out of bounds.
	 */
	public Division getDivision(int divisionToGet) {
		if (divisionToGet >= 0 && divisionToGet < divisions.size()) {
			return divisions.get(divisionToGet);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds a facility with a given name and number of games.
	 * 
	 * @param name
	 *            The name of the facility.
	 * @param numGames
	 *            The number of games that can be played at that facility.
	 * @throws IllegalArgumentException
	 *             If the name or number of games is invalid.
	 */
	public void addFacility(String name, int numGames) {
		try {
			facilities.add(new Facility(name, numGames));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets the facility at the given index.
	 * 
	 * @param facilityToGet
	 *            The index of the facility to get.
	 * @return The facility at the given index.
	 * @throws IllegalArgumentException
	 *             If the index is not acceptable.
	 */
	public Facility getFacility(int facilityToGet) {
		if (facilityToGet >= 0 && facilityToGet < facilities.size()) {
			return facilities.get(facilityToGet);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Removes the facility at a given index.
	 * 
	 * @param facilityToRemove
	 *            The index of the facility to remove.
	 * @throws IllegalArgumentException
	 *             If the index given is not acceptable.
	 */
	public void removeFacility(int facilityToRemove) {
		if (facilityToRemove >= 0 && facilityToRemove < facilities.size()) {
			facilities.remove(facilityToRemove);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Removes the division at a given index.
	 * 
	 * @param divisionToRemove
	 *            The index of the division to remove.
	 * @throws IllegalArgumentException
	 *             if the index given is not acceptable.
	 */
	public void removeDivision(int divisionToRemove) {
		if (divisionToRemove >= 0 && divisionToRemove < divisions.size()) {
			divisions.remove(divisionToRemove);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets the number of games in a season.
	 * 
	 * @return The number of games in a season.
	 */
	public int getNumGamesSeason() {
		return this.numGamesSeason;
	}

	/**
	 * Sets the number of games in a season.
	 * 
	 * @param numGamesSeason
	 *            The new number of games in a season.
	 * @throws IllegalArgumentException
	 *             If the number of games is not greater than zero.
	 */
	public void setNumGamesSeason(int numGamesSeason) {
		if (numGamesSeason > 0) {
			this.numGamesSeason = numGamesSeason;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets the number of teams that plays at each facility.
	 * 
	 * @return The number of teams that plays at each facility.
	 */
	public int getNumTeamsFacility() {
		return this.numTeamsFacility;
	}

	/**
	 * Sets the number of teams that plays at each facility.
	 * 
	 * @param numTeamsFacility
	 *            The new number of teams that plays at each facility.
	 * @throws IllegalArgumentException
	 *             If the number of teams given is not greater than zero.
	 */
	public void setNumTeamsFacility(int numTeamsFacility) {
		if (numTeamsFacility > 0) {
			this.numTeamsFacility = numTeamsFacility;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns true if all the divisions in the league have the same number of
	 * teams.
	 * 
	 * @return True if all the divisions have the same number of teams.
	 */
	public boolean allDivisionsSameSize() {
		int size = divisions.get(0).getNumTeams();
		for (int i = 0; i < divisions.size(); i++) {
			if (divisions.get(i).getNumTeams() != size) {
				return false;
			}
		}
		return true;
	}

	private int calculateNumWeeks(int numGames, int numTeams) {
		int n = (int) Math.floor(((double) numGames) / (numTeams - 1));
		if (n < 1) {
			n = 1;
		}

		int numWeeks = n * numTeams;
		if ((numGames % (numTeams - 1)) != 0) {
			numWeeks += (numGames % (numTeams - 1)) + 1;
		}

		return numWeeks;
	}

	public int countByesSoFar(Division d, int weeks) {
		return d.countByesSoFar(weeks);
	}

	public boolean checkDivSize() {
		int size = divisions.get(0).getNumTeams();
		for (int div = 0; div < divisions.size(); div++) {
			int sizeComp = Math.abs(size - divisions.get(div).getNumTeams());
			if (sizeComp > 2) {
				return false;
			}
		}
		return true;
	}

	public void equalizeDivisions(int size) {
		int count = 0;
		for (int div = 0; div < divisions.size(); div++) {
			if (divisions.get(div).getNumTeams() < size) {
				int diff = size - divisions.get(div).getNumTeams();
				divisions.get(div).setNumTeams(size);
				for (int team = 0; team < diff; team++) {
					divisions.get(div).addTeam("Bye" + (++count), numGamesSeason);
				}
			}
		}
	}

	public int getNumDivisions() {
		return divisions.size();
	}

	public int getNumFacilities() {
		return facilities.size();
	}

	public String[] facilityNames() {
		String[] names = new String[facilities.size()];
		for (int i = 0; i < facilities.size(); i++) {
			names[i] = facilities.get(i).getName();
		}
		return names;
	}

	public int maxSize() {
		int maxSize = -1;
		for (int div = 0; div < divisions.size(); div++) {
			if (divisions.get(div).getNumTeams() > maxSize) {
				maxSize = divisions.get(div).getNumTeams();
			}
		}
		return maxSize;
	}

	private int totalTeams() {
		int count = 0;
		for (int div = 0; div < divisions.size(); div++) {
			for (int t = 0; t < divisions.get(div).getNumTeams(); t++) {
				count++;
			}
		}
		return count;
	}

	private void scheduleEvenDivisionSeason(Division d) {
		int numTeams = d.getNumTeams();
		int maxTimesPlayed = (int) Math.ceil(((double) numGamesSeason) / (numTeams - 1));

		int count2 = 0;
		int countFail = 0;
		for (int week = 0; week < numGamesSeason; week++) {
			for (int team = 0; team < numTeams; team++) {
				Team current = d.getTeam(team);
				if (!current.isWeekOccupied(week)) {
					boolean b = true;
					int count = 0;

					// go until it can successfully add a team
					while (b) {
						int randTeamNum = (int) (Math.random() * numTeams);

						// handle if there is no possible outcome for a
						// team
						count++;
						if (count > numTeams * 2) {
							// start the week over
							d.wipeWeek(week);
							team = -1;
							if (week > 0) {
								week--;
							}
							b = false;
							count2++;
							if (count2 > numGamesSeason * 2) {
								d.wipeAll();
								System.out.println(++countFail);
								week = 0;
								team = -1;
								count2 = 0;
							}
						} else if (randTeamNum != team) {
							Team randTeam = d.getTeam(randTeamNum);
							if (!randTeam.isWeekOccupied(week) && current.occurencesOf(randTeam) < maxTimesPlayed
									&& !current.getName().equals(randTeam.getName())) {
								current.addToSchedule(randTeam, week);
								randTeam.addToSchedule(current, week);
								b = false;
							}
						}
					}
				}
			}
		}
	}

	private void scheduleOddDivisionSeason(Division d) {
		if (numGamesSeason % 2 == 1) {
			throw new IllegalArgumentException("Can't have odd number of teams and odd number of games");
		}

		int numTeams = d.getNumTeams();
		int maxTimesPlayed = (int) Math.ceil(((double) numGamesSeason) / (numTeams - 1));

		// the number of teams in the division is odd
		System.out.println("Max number of times played: " + maxTimesPlayed);

		d.setNumTeams(numTeams + 1);

		int numTimesPlayBye = maxTimesPlayed;
		if (numTimesPlayBye != 1) {
			numTimesPlayBye--;
		}

		int numWeeks = this.calculateNumWeeks(numGamesSeason, numTeams);

		d.addTeam("Bye", numWeeks);

		for (int team = 0; team < numTeams; team++) {
			d.getTeam(team).setNumGames(numWeeks);
		}

		System.out.println("Number of Times Play Bye: " + numTimesPlayBye + "\n");
		System.out.println("Number of Weeks: " + numWeeks);

		int count2 = 0;
		int countFail = 0;
		for (int week = 0; week < numWeeks; week++) {
			for (int team = 0; team < numTeams; team++) {
				Team current = d.getTeam(team);
				if (!current.isWeekOccupied(week)) {
					boolean b = true;
					int count = 0;
					while (b) {
						int randTeamNum = (int) (Math.random() * (numTeams + 1));
						count++;
						if (count > (numTeams + 1) * 2) {
							d.wipeWeek(week);
							team = -1;
							if (week > 0) {
								week--;
							}
							b = false;
							count2++;
							if (count2 > (numTeams + 1)) {
								count2 = 0;
								d.wipeAll();
								System.out.println(++countFail);
								week = 0;
								team = -1;
							}
						} else if (randTeamNum != team) {
							Team randTeam = d.getTeam(randTeamNum);
							if (randTeam.getName().equals("Bye") && current.occurencesOf(randTeam) < numTimesPlayBye) {
								current.addToSchedule(randTeam, week);
								// don't schedule games for the bye
								// "team"
								b = false;
							} else
								if (!randTeam.isWeekOccupied(week) && current.occurencesOf(randTeam) < maxTimesPlayed) {
								current.addToSchedule(randTeam, week);
								randTeam.addToSchedule(current, week);
								b = false;
							}
						}
					}
				}
			}
		}
		d.removeByes();
	}

	private void scheduleEvenDivisionGames(Division d, int numGames) {
		int numTeams = d.getNumTeams();
		int maxTimesPlayed = (int) Math.ceil(((double) numGames) / (numTeams - 1));

		int count2 = 0;
		int countFail = 0;
		for (int week = 0; week < numGames; week++) {
			for (int team = 0; team < numTeams; team++) {
				Team current = d.getTeam(team);
				if (!current.isWeekOccupied(week)) {
					boolean b = true;
					int count = 0;

					// go until it can successfully add a team
					while (b) {
						int randTeamNum = (int) (Math.random() * numTeams);

						// handle if there is no possible outcome for a
						// team
						count++;
						if (count > numTeams * 2) {
							// start the week over
							d.wipeWeek(week);
							team = -1;
							if (week > 0) {
								week--;
							}
							b = false;
							count2++;
							if (count2 > numGames * 2) {
								d.wipeAll();
								System.out.println(++countFail);
								week = 0;
								team = -1;
								count2 = 0;
							}
						} else if (randTeamNum != team) {
							Team randTeam = d.getTeam(randTeamNum);
							if (!randTeam.isWeekOccupied(week) && current.occurencesOf(randTeam) < maxTimesPlayed
									&& !current.getName().equals(randTeam.getName())) {
								current.addToSchedule(randTeam, week);
								randTeam.addToSchedule(current, week);
								b = false;
							}
						}
					}
				}
			}
		}
	}

	private void scheduleOtherGamesNoBye(int numGames, int start) {
		for (int div = 0; div < divisions.size(); div++) {
			Division d = divisions.get(div);

			int numTeams = d.getNumTeams();
			int countOthers = 0;
			for (int division = 0; division < divisions.size(); division++) {
				if (division != div) {
					for (int team = 0; team < divisions.get(division).getNumTeams(); team++) {
						countOthers++;
					}
				}
			}
			// countOthers should now be equal to the number of teams in other
			// divisions

			int maxTimesPlayed = (int) Math.ceil(((double) numGames) / (countOthers));

			int maxTimesPlayBye = (numGamesSeason - (maxSize() - 2)) / totalTeams();
			if (maxTimesPlayBye == 0) {
				maxTimesPlayBye = 1;
			}

			int count2 = 0;
			int countFail = 0;

			// go through each week for each division
			for (int week = start; week < numGames + start; week++) {
				// go for each team in each division
				for (int team = 0; team < numTeams; team++) {
					Team current = d.getTeam(team);
					if (!current.isWeekOccupied(week)) {
						boolean b = true;
						int count = 0;

						// go until it can successfully add
						// a team
						while (b) {
							int randDivNum = (int) (Math.random() * divisions.size());
							Division randDiv = divisions.get(randDivNum);

							if (div != randDivNum) {
								int randDivNumTeams = randDiv.getNumTeams();
								int randTeamNum = (int) (Math.random() * randDivNumTeams);

								// handle if there is no
								// possible outcome for a
								// team
								count++;
								if (count > numTeams * 2) {
									// start the week over
									for (int division = 0; division < divisions.size(); division++) {
										for (int t = 0; t < divisions.get(division).getNumTeams(); t++) {
											if (divisions.get(division).getTeam(t).isBye(week)) {
												divisions.get(division).getTeam(t).removeBye();
											}
										}
										divisions.get(division).wipeWeek(week);
									}
									div = -1;
									team = -1;
									if (week > start) {
										week--;
									}
									b = false;

									count2++;
									if (count2 > numGamesSeason * 2) {
										for (int division = 0; division < divisions.size(); division++) {
											divisions.get(division).wipeWeeks(start, start + numGames - 1);
											for (int t = 0; t < divisions.get(division).getNumTeams(); t++) {
												divisions.get(division).getTeam(t).removeByes();
											}
										}
										System.out.println("Had to wipe all");
										System.out.println(++countFail);
										week = start;
										team = -1;
										count2 = 0;
									}
								} else {
									Team randTeam = randDiv.getTeam(randTeamNum);
									if (randTeam.getName().contains("Bye")) {
										if (current.getByes() < maxTimesPlayBye) {
											if (!randTeam.isWeekOccupied(week)
													&& !current.getName().equals(randTeam.getName())) {
												current.addBye();
												current.addToSchedule(randTeam, week);
												randTeam.addToSchedule(current, week);
												b = false;
											}
										}
									} else if (!randTeam.isWeekOccupied(week)
											&& current.occurencesOf(randTeam) < maxTimesPlayed
											&& !current.getName().equals(randTeam.getName())) {
										current.addToSchedule(randTeam, week);
										randTeam.addToSchedule(current, week);
										b = false;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void scheduleOddDivisionGames(Division d, int numGames) {
		if (numGames % 2 == 1) {
			throw new IllegalArgumentException("Can't have odd number of teams and odd number of games");
		}

		int numTeams = d.getNumTeams();
		int maxTimesPlayed = (int) Math.ceil(((double) numGames) / (numTeams - 1));

		// the number of teams in the division is odd

		d.setNumTeams(numTeams + 1);

		int numTimesPlayBye = maxTimesPlayed;
		if (numTimesPlayBye != 1) {
			numTimesPlayBye--;
		}

		int numWeeks = this.calculateNumWeeks(numGames, numTeams);

		d.addTeam("Bye", numWeeks);

		for (int team = 0; team < numTeams; team++) {
			if (numGamesSeason < numTeams) {
				d.getTeam(team).setNumGames(numWeeks);
			} else {
				d.getTeam(team).setNumGames(numGamesSeason + (numWeeks - numGames));
			}
		}

		int count2 = 0;
		int countFail = 0;
		for (int week = 0; week < numWeeks; week++) {
			for (int team = 0; team < numTeams; team++) {
				Team current = d.getTeam(team);
				if (!current.isWeekOccupied(week)) {
					boolean b = true;
					int count = 0;
					while (b) {
						int randTeamNum = (int) (Math.random() * (numTeams + 1));
						count++;
						if (count > (numTeams + 1) * 2) {
							d.wipeWeek(week);
							team = -1;
							if (week > 0) {
								week--;
							}
							b = false;
							count2++;
							if (count2 > (numTeams + 1)) {
								count2 = 0;
								d.wipeAll();
								// System.out.println(++countFail);
								week = 0;
								team = -1;
							}
						} else if (randTeamNum != team) {
							Team randTeam = d.getTeam(randTeamNum);
							if (randTeam.getName().equals("Bye")) {
								if (current.occurencesOf(randTeam) < numTimesPlayBye) {
									current.addToSchedule(randTeam, week);
									// don't schedule games for the bye
									// "team"
									b = false;
								}
							} else
								if (!randTeam.isWeekOccupied(week) && current.occurencesOf(randTeam) < maxTimesPlayed) {
								current.addToSchedule(randTeam, week);
								randTeam.addToSchedule(current, week);
								b = false;
							}
						}
					}
				}
			}
		}
		d.removeByes();
	}

	private void scheduleOtherGamesByeSameSize(int numGames, int start) {
		if (numGames % 2 == 1) {
			throw new IllegalArgumentException("Can't have odd number of teams and odd number of games");
		}

		// first have to calculate the number of weeks that this will take
		int numTeamsAll = 0;
		for (int div = 0; div < divisions.size(); div++) {
			numTeamsAll += divisions.get(div).getNumTeams();
		}
		int numWeeks = this.calculateNumWeeks(numGamesSeason - (divisions.get(0).getNumTeams() - 1), numTeamsAll);

		numWeeks = numGames + 1;

		// set all the team's schedules to be the correct length

		for (int div = 0; div < divisions.size(); div++) {
			Division d = divisions.get(div);
			int numTeams = d.getNumTeams();
			for (int t = 0; t < numTeams; t++) {
				Team team = d.getTeam(t);
				team.setNumGames(numTeams + numWeeks);
			}
		}

		// calculate byes so far
		int byesSoFar = divisions.get(0).countByesSoFar(numWeeks);

		// method already assumes that the divisions are the same size
		int numTeams = divisions.get(0).getNumTeams();
		int countOthers = numTeams * (divisions.size() - 1);

		int maxTimesPlayed = (int) Math.ceil(((double) numGames) / (countOthers));

		int numTimesPlayByeAfter = maxTimesPlayed;

		int totalTimesPlayBye = numTimesPlayByeAfter + byesSoFar;
		System.out.println("Weeks: " + numWeeks + "\nByes: " + totalTimesPlayBye);

		this.addDivision("Bye Division", 1);
		divisions.get(divisions.size() - 1).addTeam("Bye", numWeeks);
		int count2 = 0;
		int countFail = 0;
		// don't want to try to schedule the bye division
		for (int div = 0; div < divisions.size() - 1; div++) {
			Division d = divisions.get(div);

			// go through each week for each division
			for (int week = start; week < numWeeks + start; week++) {
				// go for each team in each division
				for (int team = 0; team < numTeams; team++) {
					Team current = d.getTeam(team);
					if (!current.isWeekOccupied(week)) {
						boolean b = true;
						int count = 0;

						// go until it can successfully add
						// a team
						while (b) {
							int randDivNum = (int) (Math.random() * divisions.size());
							Division randDiv = divisions.get(randDivNum);

							if (div != randDivNum) {
								int randDivNumTeams = randDiv.getNumTeams();
								int randTeamNum = (int) (Math.random() * randDivNumTeams);

								// handle if there is no
								// possible outcome for a
								// team
								count++;
								if (count > numTeams * 2) {
									// start the week over
									// System.out.println("Wiped week " + week);

									for (int division = 0; division < divisions.size(); division++) {
										divisions.get(division).wipeWeek(week);
									}
									div = -1;
									team = -1;
									if (week > start) {
										week--;
									}
									b = false;

									count2++;
									if (count2 > numGamesSeason * 2) {
										for (int division = 0; division < divisions.size(); division++) {
											divisions.get(division).wipeWeeks(start, start + numWeeks - 1);
										}
										System.out.println(++countFail);
										week = start;
										team = -1;
										count2 = 0;
									}
								} else {
									Team randTeam = randDiv.getTeam(randTeamNum);
									if (randTeam.getName().equals("Bye")) {
										if (current.occurencesOf(randTeam) < totalTimesPlayBye) {
											current.addToSchedule(randTeam, week);
											// don't schedule games for the bye
											// "team"
											b = false;
										}
									} else if (!randTeam.isWeekOccupied(week)
											&& current.occurencesOf(randTeam) < maxTimesPlayed
											&& !current.getName().equals(randTeam.getName())) {
										current.addToSchedule(randTeam, week);
										randTeam.addToSchedule(current, week);
										b = false;
									}
								}
							}
						}
					}
				}
			}
		}
		this.removeDivision(divisions.size() - 1);
	}

	/**
	 * Schedules the league.
	 */
	public void schedule() {
		for (int div = 0; div < divisions.size(); div++) {
			divisions.get(div).wipeAll();
		}

		// if the number of divisions in the league is equal to 1
		if (divisions.size() == 1) {
			int numTeams = divisions.get(0).getNumTeams();
			if (numTeams % 2 == 0) {
				scheduleEvenDivisionSeason(divisions.get(0));
			} else {
				scheduleOddDivisionSeason(divisions.get(0));
			}
			for (int i = 0; i < numTeams; i++) {
				System.out.println(divisions.get(0).getTeam(i).getName());
				divisions.get(0).getTeam(i).printSchedule();
			}
		} else {
			// there is more than one division ...
			if (this.allDivisionsSameSize()) {
				int numTeamsInOne = divisions.get(0).getNumTeams();
				int numTeamsInAll = numTeamsInOne * divisions.size();

				if (numTeamsInOne % 2 == 0) {
					// if the number of games is less than the number of teams
					// in one division, only play divisional games
					if (numGamesSeason < numTeamsInOne) {
						for (int i = 0; i < divisions.size(); i++) {
							scheduleEvenDivisionGames(divisions.get(i), numGamesSeason);
						}
					} else {
						// if the number of games in the season is less than the
						// total number of games, only play teams outside the
						// division once
						for (int i = 0; i < divisions.size(); i++) {
							scheduleEvenDivisionGames(divisions.get(i), numTeamsInOne - 1);
						}
						scheduleOtherGamesNoBye(numGamesSeason - (numTeamsInOne - 1), numTeamsInOne - 1);
					}
				} else {
					if (numGamesSeason < numTeamsInOne) {
						for (int i = 0; i < divisions.size(); i++) {
							scheduleOddDivisionGames(divisions.get(i), numGamesSeason);
						}
					} else {
						if (divisions.size() % 2 == 0) {
							// the total number of teams is even
							for (int div = 0; div < divisions.size(); div++) {
								scheduleOddDivisionGames(divisions.get(div), numTeamsInOne - 1);
							}
							int numWeeks = this.calculateNumWeeks(numTeamsInOne - 1, numTeamsInOne);
							this.scheduleOtherGamesNoBye(numGamesSeason - (numTeamsInOne - 1), numWeeks);
						} else {
							// the total number of teams is odd
							for (int div = 0; div < divisions.size(); div++) {
								scheduleOddDivisionGames(divisions.get(div), numTeamsInOne - 1);
							}
							int numWeeks = this.calculateNumWeeks(numTeamsInOne - 1, numTeamsInOne);
							System.out.println("Divisional play complete");
							this.scheduleOtherGamesByeSameSize(numGamesSeason - (numTeamsInOne - 1), numWeeks);
						}
					}
				}
			} else {
				// the divisions are not all the same size
				if (this.checkDivSize()) {
					int maxSize = -1;
					for (int div = 0; div < divisions.size(); div++) {
						if (divisions.get(div).getNumTeams() > maxSize) {
							maxSize = divisions.get(div).getNumTeams();
						}
					}

					boolean b = true;
					int countFail = 0;
					while (b) {
						b = false;
						System.out.println("Run: " + (++countFail));
						if (numGamesSeason < maxSize) {
							for (int div = 0; div < divisions.size(); div++) {
								if (divisions.get(div).getNumTeams() % 2 == 0) {
									this.scheduleEvenDivisionSeason(divisions.get(div));
								} else {
									this.scheduleOddDivisionSeason(divisions.get(div));
								}
							}
						} else {
							this.equalizeDivisions(maxSize);
							for (int div = 0; div < divisions.size(); div++) {
								if (maxSize % 2 == 0) {
									this.scheduleEvenDivisionGames(divisions.get(div), maxSize - 1);
								} else {
									this.scheduleOddDivisionGames(divisions.get(div), maxSize - 1);
								}
							}

							int totalSize = maxSize * divisions.size();
							if (totalSize % 2 == 0) {
								int start = 0;
								if (maxSize % 2 == 0) {
									start = maxSize - 1;
								} else {
									start = maxSize;
								}
								for (int div = 0; div < divisions.size(); div++) {
									for (int t = 0; t < divisions.get(div).getNumTeams(); t++) {
										if (maxSize % 2 == 1) {
											divisions.get(div).getTeam(t)
													.setNumGames((maxSize) + (numGamesSeason - (maxSize - 2)));
										} else {
											divisions.get(div).getTeam(t)
													.setNumGames((maxSize - 1) + (numGamesSeason - (maxSize - 2)));
										}
									}
								}
								this.scheduleOtherGamesNoBye(numGamesSeason - (maxSize - 2), start);
							} else {
								int start = 0;
								if (maxSize % 2 == 0) {
									start = maxSize - 1;
								} else {
									start = maxSize;
								}
								for (int div = 0; div < divisions.size(); div++) {
									for (int t = 0; t < divisions.get(div).getNumTeams(); t++) {
										if (maxSize % 2 == 1) {
											divisions.get(div).getTeam(t)
													.setNumGames((maxSize) + (numGamesSeason - (maxSize - 2)));
										} else {
											divisions.get(div).getTeam(t)
													.setNumGames((maxSize - 1) + (numGamesSeason - (maxSize - 2)));
										}
									}
								}
								this.scheduleOtherGamesByeSameSize(numGamesSeason - (maxSize - 2), start);
							}
						}

						for (int div = 0; div < divisions.size(); div++) {
							for (int team = 0; team < divisions.get(div).getNumTeams(); team++) {
								if (divisions.size() < 2) {
									if (divisions.get(div).getTeam(team).scheduledGames() < numGamesSeason) {
										b = true;
									}
								} else {
									if (divisions.get(div).getTeam(team).scheduledGames() < numGamesSeason - 1) {
										b = true;
									}
								}
							}
						}

						if (b) {
							for (int div = 0; div < divisions.size(); div++) {
								divisions.get(div).wipeAll();
							}
						} else {
							for (int div = 0; div < divisions.size(); div++) {
								for (int t = 0; t < divisions.get(div).getNumTeams(); t++) {
									if (divisions.get(div).getTeam(t).getName().contains("Bye")) {
										int num = divisions.get(div).getNumTeams();
										divisions.get(div).removeTeam(t);
										divisions.get(div).setNumTeams(num - 1);
										t--;
									}
								}
							}
						}
					}

				} else {
					throw new IllegalArgumentException("Divisions have to be within 2 of one another");
				}
			}

			boolean b = true;
			/*
			 * for (int division = 0; division < divisions.size(); division++) {
			 * if (divisions.get(division).doesTeamHaveNull()) { b = false; } }
			 */
			if (b) {
				for (int division = 0; division < divisions.size(); division++) {
					System.out.println("Division name: " + divisions.get(division).getName());
					System.out.println("------------------------");
					for (int team = 0; team < divisions.get(division).getNumTeams(); team++) {
						System.out.println("Team " + divisions.get(division).getTeam(team).getName());
						System.out.println("-----------------");
						divisions.get(division).getTeam(team).printSchedule();
					}
				}
			} else {
				System.out.println("FAIL");
			}
		}
	}

	public static void main(String[] args) {
		ScheduleManager man = new ScheduleManager(8, 2);
		man.addDivision("Division 1", 5);
		man.addDivision("Division 2", 4);
		man.addDivision("Division 3", 4);
		man.addDivision("Division 4", 4);
		Division d = man.getDivision(0);
		Division d2 = man.getDivision(1);
		Division d3 = man.getDivision(2);
		Division d4 = man.getDivision(3);

		d.addTeam("Tar Heels", 8);
		d.addTeam("Bearcats", 8);
		d.addTeam("Paladins", 8);
		d.addTeam("Demon Deacons", 8);
		d.addTeam("Revolution", 8);

		d2.addTeam("Seahawks", 8);
		d2.addTeam("Wolfpack", 8);
		d2.addTeam("Phoenix", 8);
		d2.addTeam("Hurricanes", 8);

		d3.addTeam("Eagles", 8);
		d3.addTeam("Cocks", 8);
		d3.addTeam("Wildcats", 8);
		d3.addTeam("Twins", 8);

		d4.addTeam("Jazz", 8);
		d4.addTeam("Patriots", 8);
		d4.addTeam("Arsenal", 8);
		d4.addTeam("Red Sox", 8);

		man.schedule();
	}
}
