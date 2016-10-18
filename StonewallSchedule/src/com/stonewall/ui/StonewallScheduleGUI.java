package com.stonewall.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import com.stonewall.backendmanager.*;
import com.stonewall.setup.*;

public class StonewallScheduleGUI extends JFrame {
	private ScheduleManager manager;
	private Division currentDiv;
	private int index;

	// start face
	private JButton startButton = new JButton("Start");
	private JButton exitButton = new JButton("Exit");
	private JButton credits = new JButton("Credits");
	private JPanel logoPanel = new JPanel();
	private JPanel buttonStartPanel = new JPanel();

	// Credits Face
	private JLabel programmer = new JLabel("Programmer:");
	private JLabel myName = new JLabel("Taylor Smith");
	private JLabel version = new JLabel("Version:");
	private JLabel versionNum = new JLabel("1.0");
	private JLabel origin = new JLabel("Program out of Stonewall Sports Raleigh");
	private JLabel email = new JLabel("Email:");
	private JLabel emailAddress = new JLabel("stonewallschedule@gmail.com");
	private JButton backToStart = new JButton("Back");
	private JPanel creditsPanel = new JPanel();
	private JPanel backPanel = new JPanel();

	// main screen face
	private String[] divNames = {};
	private JComboBox divNamesBox = new JComboBox(divNames);
	private JButton editDivButton = new JButton();
	private JButton addDivButton = new JButton();
	private int numGames = 1;
	private JLabel numGamesLabel = new JLabel("Number of Games:");
	private JTextField numGamesField = new JTextField(10);
	private JButton editNumGames = new JButton("Edit");
	private JButton goToFacility = new JButton("Facility Menu");
	private JButton schedule = new JButton("Schedule");
	private JPanel editDivPanel = new JPanel();
	private JPanel numGamesPanel = new JPanel();
	private JPanel schedulePanel = new JPanel();

	// add division face
	private JLabel divNameLabel = new JLabel("Name:");
	private JTextField divNameEdit = new JTextField(15);
	private JLabel numTeamsDivLabel = new JLabel("Maximum Number of Teams:");
	private JTextField numTeamsEdit = new JTextField(5);
	private JButton okayAddDiv = new JButton("Add");
	private JButton backFromAdd = new JButton("Back");

	// edit division face
	private JLabel divisionNameLabel = new JLabel("Division Name:");
	private JTextField divisionNameEdit = new JTextField(15);
	private JButton divisionNameEditButton = new JButton("Edit");
	private JButton addTeamButton = new JButton("Add Team");
	private JButton removeTeamButton = new JButton("Remove Team");
	private JTextField numTeamsDivEdit = new JTextField(5);
	private JButton editNumTeams = new JButton("Edit");
	private JButton backFromEdit = new JButton("Back");

	private JList teamNames;
	private JScrollPane scrollTeamNames;

	private JPanel divNamePanel = new JPanel();
	private JPanel teamsPanel = new JPanel();
	private JPanel numTeamsPanel = new JPanel();

	// add team face
	private JLabel teamNameLabel = new JLabel("Team Name");
	private JTextField editTeamName = new JTextField(15);
	private JButton okayAddTeamButton = new JButton("Add");
	private JButton backFromAddTeam = new JButton("Back");

	// facility menu
	private JButton addFacilityButton = new JButton("Add Facility");
	private JButton removeFacilityButton = new JButton("Remove Facility");
	private JButton backFromAddFacility = new JButton("Back");

	private JList facilityNames;
	private JScrollPane scrollFacilityNames;

	private JPanel facilityButtons = new JPanel();

	// add facility face
	private JLabel facilityNameLabel = new JLabel("Facility Name:");
	private JLabel facilityGames = new JLabel("Number of Games Per Week:");
	private JTextField editFacilityName = new JTextField(15);
	private JTextField editFacilityGames = new JTextField(5);
	private JButton okayAddFacilityButton = new JButton("Add");
	private JButton backFromAddFacilityScreen = new JButton("Back");

	// schedule display face
	private ArrayList<JLabel> weekLabels;
	private ArrayList<JList> gamesList;
	private ArrayList<JScrollPane> scrollGamesList;
	private JButton backFromSchedule = new JButton("Back");
	private JButton scheduleAgain = new JButton("Reschedule");
	private JButton export = new JButton("Export");
	private JScrollPane scheduleWindow;
	private Container scheduleFrame = new Container();

	// menu bar
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem saveItem;
	private JMenuItem loadItem;

	// set up images
	private ImageIcon pencilIcon;
	private ImageIcon plusIcon;
	private ImageIcon backIcon;
	private ImageIcon minusIcon;
	private ImageIcon logoIcon;

	private void setUpImages() {
		BufferedImage pencil = null;
		BufferedImage plus = null;
		BufferedImage back = null;
		BufferedImage minus = null;
		BufferedImage logo = null;
		try {
			pencil = ImageIO.read(getClass().getResource("/com/stonewall/ui/pencilicon2.png"));
			plus = ImageIO.read(getClass().getResource("/com/stonewall/ui/plus.png"));
			back = ImageIO.read(getClass().getResource("/com/stonewall/ui/back.png"));
			minus = ImageIO.read(getClass().getResource("/com/stonewall/ui/redminus.png"));
			logo = ImageIO.read(getClass().getResource("/com/stonewall/ui/stonewall.PNG"));
		} catch (IOException exc) {

		}
		pencilIcon = new ImageIcon(pencil);
		plusIcon = new ImageIcon(plus);
		backIcon = new ImageIcon(back);
		minusIcon = new ImageIcon(minus);
		logoIcon = new ImageIcon(logo);
	}

	private void setUpMenuBar() {
		if (loadItem != null) {
			if (loadItem.getActionListeners().length > 0) {
				loadItem.removeActionListener(loadItem.getActionListeners()[0]);
			}
		}
		if (saveItem != null) {
			if (saveItem.getActionListeners().length > 0) {
				saveItem.removeActionListener(saveItem.getActionListeners()[0]);
			}
		}
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		saveItem = new JMenuItem("Save");
		loadItem = new JMenuItem("Load");
		loadItem.addActionListener(new LoadAction());
		saveItem.addActionListener(new SaveAction());

		menu.add(saveItem);
		menu.add(loadItem);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	public StonewallScheduleGUI() {
		setUpImages();
		this.setUpStartFace();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		this.setTitle("Stonewall Sports Scheduling");
		manager = new ScheduleManager(numGames, 2);
		currentDiv = null;

	}

	private void setUpStartFace() {
		if (startButton.getActionListeners().length > 0) {
			startButton.removeActionListener(startButton.getActionListeners()[0]);
		}
		if (exitButton.getActionListeners().length > 0) {
			exitButton.removeActionListener(exitButton.getActionListeners()[0]);
		}
		if (credits.getActionListeners().length > 0) {
			credits.removeActionListener(credits.getActionListeners()[0]);
		}

		// set up the photo
		logoPanel.removeAll();
		logoPanel.setLayout(new FlowLayout());
		JLabel logoShow = new JLabel();
		logoShow.setIcon(logoIcon);
		logoPanel.add(logoShow);

		// set up the bottom
		buttonStartPanel.removeAll();
		buttonStartPanel.setLayout(new FlowLayout());
		buttonStartPanel.add(startButton);
		startButton.addActionListener(new StartAction());
		buttonStartPanel.add(exitButton);
		exitButton.addActionListener(new ExitAction());
		buttonStartPanel.add(credits);
		credits.addActionListener(new CreditsAction());

		Container c = getContentPane();
		c.removeAll();

		c.setLayout(new BorderLayout());
		c.add(logoPanel, BorderLayout.NORTH);
		c.add(buttonStartPanel, BorderLayout.SOUTH);

		this.setResizable(false);
		this.pack();
		this.validate();
		this.repaint();
	}

	private void setUpCreditsFace() {
		if (backToStart.getActionListeners().length > 0) {
			backToStart.removeActionListener(backToStart.getActionListeners()[0]);
		}

		// set up the top panel
		creditsPanel.removeAll();
		creditsPanel.setLayout(new GridLayout(3, 2, 0, 0));

		creditsPanel.add(programmer);
		creditsPanel.add(myName);
		creditsPanel.add(version);
		creditsPanel.add(versionNum);
		creditsPanel.add(email);
		creditsPanel.add(emailAddress);

		// set up back panel
		backPanel.removeAll();
		backPanel.setLayout(new FlowLayout());
		backPanel.add(backToStart);
		backToStart.addActionListener(new BackFromCreditsAction());
		backToStart.setText("");
		backToStart.setIcon(backIcon);

		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BorderLayout());
		c.add(creditsPanel, BorderLayout.NORTH);
		c.add(backPanel, BorderLayout.SOUTH);

		this.pack();
		this.validate();
		this.repaint();

	}

	private void setUpMainFace() {
		if (this.editDivButton.getActionListeners().length > 0) {
			editDivButton.removeActionListener(editDivButton.getActionListeners()[0]);
		}
		if (this.addDivButton.getActionListeners().length > 0) {
			addDivButton.removeActionListener(addDivButton.getActionListeners()[0]);
		}
		if (this.editNumGames.getActionListeners().length > 0) {
			editNumGames.removeActionListener(editNumGames.getActionListeners()[0]);
		}
		if (this.schedule.getActionListeners().length > 0) {
			schedule.removeActionListener(schedule.getActionListeners()[0]);
		}
		if (this.numGamesField.getActionListeners().length > 0) {
			numGamesField.removeActionListener(numGamesField.getActionListeners()[0]);
		}
		if (this.editNumGames.getActionListeners().length > 0) {
			editNumGames.removeActionListener(editNumGames.getActionListeners()[0]);
		}
		if (this.schedule.getActionListeners().length > 0) {
			schedule.removeActionListener(schedule.getActionListeners()[0]);
		}
		if (this.goToFacility.getActionListeners().length > 0) {
			goToFacility.removeActionListener(goToFacility.getActionListeners()[0]);
		}

		setUpMenuBar();
		// set up editDivPanel
		this.editDivPanel.removeAll();
		editDivPanel.setLayout(new FlowLayout());
		int currentSpot = -1;
		divNames = new String[manager.getNumDivisions()];
		for (int d = 0; d < divNames.length; d++) {
			divNames[d] = manager.getDivision(d).getName();
		}

		if (divNames.length < 1) {
			editDivButton.setEnabled(false);
			saveItem.setEnabled(false);
		} else {
			currentSpot = divNamesBox.getSelectedIndex();
			editDivButton.setEnabled(true);
			saveItem.setEnabled(true);
		}
		divNamesBox = new JComboBox(divNames);
		if (currentSpot != -1) {
			divNamesBox.setSelectedIndex(currentSpot);
		}
		addDivButton.addActionListener(new AddDivAction());
		editDivButton.addActionListener(new EditDivAction());
		// editDivButton.setText("");
		addDivButton.setIcon(plusIcon);
		editDivButton.setIcon(pencilIcon);
		editDivPanel.add(divNamesBox);
		editDivPanel.add(editDivButton);
		editDivPanel.add(addDivButton);

		// set up num games panel
		this.numGamesPanel.removeAll();
		numGamesPanel.setLayout(new FlowLayout());
		numGamesPanel.add(numGamesLabel);
		numGamesPanel.add(this.numGamesField);
		numGamesPanel.add(this.editNumGames);

		numGamesField.setText("" + numGames);
		numGamesField.setEditable(false);
		editNumGames.addActionListener(new EditNumGamesAction());
		numGamesField.addActionListener(new EditNumGamesAction());
		editNumGames.setText("");
		editNumGames.setIcon(pencilIcon);

		// set up schedule panel
		this.schedulePanel.removeAll();
		schedulePanel.setLayout(new FlowLayout());
		schedulePanel.add(goToFacility);
		schedulePanel.add(schedule);

		if (divNames.length < 1) {
			schedule.setEnabled(false);
		} else {
			schedule.setEnabled(true);
		}
		goToFacility.addActionListener(new GoToFacilityAction());
		schedule.addActionListener(new ScheduleAction());

		Container c = getContentPane();
		c.removeAll();

		c.setLayout(new BorderLayout());
		c.add(editDivPanel, BorderLayout.NORTH);
		c.add(numGamesPanel, BorderLayout.CENTER);
		c.add(schedulePanel, BorderLayout.SOUTH);

		this.setResizable(false);
		this.pack();
		this.validate();
		this.repaint();
	}

	private void setUpAddDivFace() {
		if (okayAddDiv.getActionListeners().length > 0) {
			okayAddDiv.removeActionListener(okayAddDiv.getActionListeners()[0]);
		}
		if (backFromAdd.getActionListeners().length > 0) {
			backFromAdd.removeActionListener(backFromAdd.getActionListeners()[0]);
		}
		if (numTeamsEdit.getActionListeners().length > 0) {
			numTeamsEdit.removeActionListener(numTeamsEdit.getActionListeners()[0]);
		}
		if (divNameEdit.getActionListeners().length > 0) {
			divNameEdit.removeActionListener(divNameEdit.getActionListeners()[0]);
		}

		Container c = getContentPane();
		c.removeAll();

		this.setJMenuBar(null);
		c.setLayout(new FlowLayout());
		c.add(divNameLabel);
		c.add(divNameEdit);
		c.add(numTeamsDivLabel);
		c.add(numTeamsEdit);
		c.add(okayAddDiv);
		c.add(backFromAdd);

		divNameEdit.setText("");
		numTeamsEdit.setText("");
		okayAddDiv.addActionListener(new OkayAddDivAction());
		backFromAdd.addActionListener(new BackFromAddAction());
		numTeamsEdit.addActionListener(new OkayAddDivAction());
		divNameEdit.addActionListener(new OkayAddDivAction());
		okayAddDiv.setText("");
		okayAddDiv.setIcon(plusIcon);
		backFromAdd.setText("");
		backFromAdd.setIcon(backIcon);

		this.setResizable(false);
		this.pack();
		this.validate();
		this.repaint();
	}

	private void setUpEditDivFace() {
		index = divNamesBox.getSelectedIndex();
		Division d = manager.getDivision(index);
		currentDiv = d;

		if (this.divisionNameEdit.getActionListeners().length > 0) {
			divisionNameEdit.removeActionListener(divisionNameEdit.getActionListeners()[0]);
		}
		if (this.addTeamButton.getActionListeners().length > 0) {
			addTeamButton.removeActionListener(addTeamButton.getActionListeners()[0]);
		}
		if (this.removeTeamButton.getActionListeners().length > 0) {
			removeTeamButton.removeActionListener(removeTeamButton.getActionListeners()[0]);
		}
		if (this.numTeamsDivEdit.getActionListeners().length > 0) {
			numTeamsDivEdit.removeActionListener(numTeamsDivEdit.getActionListeners()[0]);
		}
		if (this.editNumTeams.getActionListeners().length > 0) {
			editNumTeams.removeActionListener(editNumTeams.getActionListeners()[0]);
		}
		if (this.backFromEdit.getActionListeners().length > 0) {
			backFromEdit.removeActionListener(backFromEdit.getActionListeners()[0]);
		}
		if (this.divisionNameEditButton.getActionListeners().length > 0) {
			divisionNameEditButton.removeActionListener(divisionNameEditButton.getActionListeners()[0]);
		}

		// set up the top panel
		divNamePanel.removeAll();
		divNamePanel.setLayout(new FlowLayout());
		divisionNameEdit.addActionListener(new EditDivNameAction());
		divisionNameEditButton.addActionListener(new EditDivNameAction());
		divNamePanel.add(divisionNameLabel);
		divNamePanel.add(divisionNameEdit);
		divNamePanel.add(divisionNameEditButton);
		divNamePanel.add(backFromEdit);

		divisionNameEdit.setText(d.getName());
		divisionNameEdit.setEditable(false);
		divisionNameEditButton.setText("");
		divisionNameEditButton.setIcon(pencilIcon);
		backFromEdit.setText("");
		backFromEdit.setIcon(backIcon);

		// set up the scroll pane
		String[] teams = d.getListTeams();
		teamNames = new JList(teams);
		scrollTeamNames = new JScrollPane(teamNames);

		// set up the central panel
		teamsPanel.removeAll();
		teamsPanel.setLayout(new FlowLayout());
		teamsPanel.add(scrollTeamNames);
		teamsPanel.add(addTeamButton);
		teamsPanel.add(removeTeamButton);

		addTeamButton.addActionListener(new GoToAddTeamAction());
		removeTeamButton.addActionListener(new RemoveTeamAction());
		addTeamButton.setText("");
		addTeamButton.setIcon(plusIcon);
		removeTeamButton.setText("");
		removeTeamButton.setIcon(minusIcon);

		if (currentDiv.numberOfTeams() == currentDiv.getNumTeams()) {
			addTeamButton.setEnabled(false);
		} else {
			addTeamButton.setEnabled(true);
		}
		if (teams.length == 0) {
			removeTeamButton.setEnabled(false);
		} else {
			removeTeamButton.setEnabled(true);
		}

		// set up bottom panel
		numTeamsPanel.removeAll();
		numTeamsPanel.setLayout(new FlowLayout());
		numTeamsPanel.add(numTeamsDivLabel);
		numTeamsPanel.add(numTeamsDivEdit);
		numTeamsPanel.add(editNumTeams);

		numTeamsDivEdit.addActionListener(new EditNumTeamsAction());
		editNumTeams.addActionListener(new EditNumTeamsAction());
		numTeamsDivEdit.setText("" + d.getNumTeams());
		numTeamsDivEdit.setEditable(false);
		backFromEdit.addActionListener(new BackFromEditAction());
		editNumTeams.setText("");
		editNumTeams.setIcon(pencilIcon);

		Container c = getContentPane();
		c.removeAll();

		this.setJMenuBar(null);
		c.setLayout(new BorderLayout());
		c.add(divNamePanel, BorderLayout.NORTH);
		c.add(teamsPanel, BorderLayout.CENTER);
		c.add(numTeamsPanel, BorderLayout.SOUTH);

		this.pack();
		this.validate();
		this.repaint();
	}

	private void setUpAddTeamFace() {
		if (this.okayAddTeamButton.getActionListeners().length > 0) {
			okayAddTeamButton.removeActionListener(okayAddTeamButton.getActionListeners()[0]);
		}
		if (this.backFromAddTeam.getActionListeners().length > 0) {
			backFromAddTeam.removeActionListener(backFromAddTeam.getActionListeners()[0]);
		}
		if (this.editTeamName.getActionListeners().length > 0) {
			editTeamName.removeActionListener(editTeamName.getActionListeners()[0]);
		}

		// populate the scroll pane
		String[] names = manager.facilityNames();
		facilityNames = new JList(names);
		scrollFacilityNames = new JScrollPane(facilityNames);

		Container c = getContentPane();
		c.removeAll();

		c.setLayout(new FlowLayout());
		c.add(teamNameLabel);
		c.add(editTeamName);
		c.add(okayAddTeamButton);
		c.add(backFromAddTeam);

		editTeamName.setText("");
		editTeamName.addActionListener(new AddTeamAction());
		okayAddTeamButton.addActionListener(new AddTeamAction());
		backFromAddTeam.addActionListener(new BackFromAddTeamAction());

		okayAddTeamButton.setText("");
		okayAddTeamButton.setIcon(plusIcon);
		backFromAddTeam.setText("");
		backFromAddTeam.setIcon(backIcon);

		this.pack();
		this.validate();
		this.repaint();
	}

	private void setUpFacilityFace() {
		if (addFacilityButton.getActionListeners().length > 0) {
			addFacilityButton.removeActionListener(addFacilityButton.getActionListeners()[0]);
		}
		if (removeFacilityButton.getActionListeners().length > 0) {
			removeFacilityButton.removeActionListener(removeFacilityButton.getActionListeners()[0]);
		}
		if (backFromAddFacility.getActionListeners().length > 0) {
			backFromAddFacility.removeActionListener(backFromAddFacility.getActionListeners()[0]);
		}

		// populate the scroll pane
		String[] names = manager.facilityNames();
		for (int fac = 0; fac < names.length; fac++) {
			int games = manager.getFacility(fac).getNumGames();
			if (games > 1) {
				names[fac] += ", " + games + " games per week";
			} else {
				names[fac] += ", " + games + " game per week";
			}
		}
		facilityNames = new JList(names);
		scrollFacilityNames = new JScrollPane(facilityNames);

		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BorderLayout());

		this.setJMenuBar(null);
		c.add(scrollFacilityNames, BorderLayout.NORTH);

		facilityButtons.removeAll();
		facilityButtons.add(addFacilityButton);
		facilityButtons.add(removeFacilityButton);
		facilityButtons.add(backFromAddFacility);

		addFacilityButton.addActionListener(new GoToAddFacilityAction());
		removeFacilityButton.addActionListener(new RemoveFacilityAction());
		backFromAddFacility.addActionListener(new BackFromAddFacilityAction());
		addFacilityButton.setText("");
		addFacilityButton.setIcon(plusIcon);
		removeFacilityButton.setText("");
		removeFacilityButton.setIcon(minusIcon);
		backFromAddFacility.setText("");
		backFromAddFacility.setIcon(backIcon);

		c.add(facilityButtons, BorderLayout.SOUTH);

		if (names.length == 0) {
			removeFacilityButton.setEnabled(false);
		} else {
			removeFacilityButton.setEnabled(true);
		}

		this.pack();
		this.validate();
		this.repaint();
	}

	private void setUpAddFacilityFace() {
		if (this.editFacilityGames.getActionListeners().length > 0) {
			this.editFacilityGames.removeActionListener(this.editFacilityGames.getActionListeners()[0]);
		}
		if (this.editFacilityName.getActionListeners().length > 0) {
			this.editFacilityName.removeActionListener(this.editFacilityName.getActionListeners()[0]);
		}
		if (this.okayAddFacilityButton.getActionListeners().length > 0) {
			this.okayAddFacilityButton.removeActionListener(this.okayAddFacilityButton.getActionListeners()[0]);
		}
		if (backFromAddFacilityScreen.getActionListeners().length > 0) {
			this.backFromAddFacilityScreen.removeActionListener(this.backFromAddFacilityScreen.getActionListeners()[0]);
		}

		Container c = getContentPane();
		c.removeAll();

		c.setLayout(new FlowLayout());
		c.add(facilityNameLabel);
		c.add(editFacilityName);
		c.add(facilityGames);
		c.add(editFacilityGames);
		c.add(okayAddFacilityButton);
		c.add(backFromAddFacilityScreen);

		editFacilityName.setText("");
		editFacilityGames.setText("");
		editFacilityName.addActionListener(new AddFacilityAction());
		editFacilityGames.addActionListener(new AddFacilityAction());
		okayAddFacilityButton.addActionListener(new AddFacilityAction());
		backFromAddFacilityScreen.addActionListener(new GoToFacilityAction());

		okayAddFacilityButton.setText("");
		okayAddFacilityButton.setIcon(plusIcon);
		backFromAddFacilityScreen.setText("");
		backFromAddFacilityScreen.setIcon(backIcon);

		this.pack();
		this.validate();
		this.repaint();
	}

	private void setUpShowScheduleFace() {
		if (scheduleAgain.getActionListeners().length > 0) {
			scheduleAgain.removeActionListener(scheduleAgain.getActionListeners()[0]);
		}
		if (backFromSchedule.getActionListeners().length > 0) {
			backFromSchedule.removeActionListener(backFromSchedule.getActionListeners()[0]);
		}
		if (export.getActionListeners().length > 0) {
			export.removeActionListener(export.getActionListeners()[0]);
		}

		scheduleFrame.removeAll();

		int numWeeks = manager.getDivision(0).getTeam(0).scheduleLength();
		int numTeams = 0;
		for (int div = 0; div < manager.getNumDivisions(); div++) {
			numTeams += manager.getDivision(div).getNumTeams();
		}

		scheduleFrame.setLayout(new GridLayout((numWeeks), 2));

		weekLabels = new ArrayList<JLabel>();
		gamesList = new ArrayList<JList>();
		scrollGamesList = new ArrayList<JScrollPane>();

		for (int i = 0; i < numWeeks; i++) {
			weekLabels.add(new JLabel("Week " + (i + 1)));
			ArrayList<String> games = new ArrayList<String>();
			for (int div = 0; div < manager.getNumDivisions(); div++) {
				games.addAll(manager.getDivision(div).game(i));
			}
			// sort the arraylist
			ArrayList<String> soFar = new ArrayList<String>();
			for (int g = 0; g < games.size(); g++) {
				String current = games.get(g);
				if (current.contains("bye")) {
					String[] words = current.split(" is");
					String team = words[0];
					if (!soFar.contains(team)) {
						soFar.add(team);
						games.add(games.remove(g));
						g--;
					}
				} else {
					String[] words = current.split(" vs ");
					String first = words[0];
					String last = words[words.length - 1];
					if (soFar.contains(first) || soFar.contains(last)) {
						games.remove(g);
						g--;
					} else {
						soFar.add(first);
						soFar.add(last);
					}
				}
			}
			// assign facilities
			int[] facilityGames = new int[manager.getNumFacilities()];
			int countFac = 0;
			for (int g = 0; g < games.size(); g++) {
				if (!games.get(g).contains("bye")) {
					if (facilityGames[countFac] > manager.getFacility(countFac).getNumGames()) {
						countFac++;
					}
					if (countFac > manager.getNumFacilities()) {
						countFac = 0;
					}
					facilityGames[countFac] = facilityGames[countFac] + 1;
					String current = games.get(g);
					current += ", " + manager.getFacility(countFac).getName();
					games.set(g, current);
					countFac++;
					if (countFac == manager.getNumFacilities()) {
						countFac = 0;
					}
				}
			}

			String[] gamesArray = new String[games.size()];
			for (int k = 0; k < games.size(); k++) {
				gamesArray[k] = games.get(k);
			}
			gamesList.add(new JList(gamesArray));
			scrollGamesList.add(new JScrollPane(gamesList.get(i)));
			scrollGamesList.get(i).setMinimumSize(new Dimension(300, 100));
			scheduleFrame.add(weekLabels.get(i));
			scheduleFrame.add(scrollGamesList.get(i));
		}

		JPanel lower = new JPanel();
		lower.setLayout(new FlowLayout());
		lower.add(export);
		lower.add(scheduleAgain);
		lower.add(backFromSchedule);

		export.addActionListener(new SaveToExcel());
		scheduleAgain.addActionListener(new ScheduleAction());
		backFromSchedule.addActionListener(new BackFromAddAction());

		scheduleWindow = new JScrollPane(scheduleFrame);
		Dimension dim = new Dimension(800, 400);
		scheduleWindow.setPreferredSize(dim);

		Container c = getContentPane();
		c.removeAll();

		c.setLayout(new BorderLayout());

		this.setJMenuBar(null);
		c.add(scheduleWindow, BorderLayout.NORTH);
		c.add(lower, BorderLayout.SOUTH);

		this.pack();
		this.validate();
		this.repaint();
	}

	private void readFile(File file) {
		Scanner scan = null;
		try {
			for (int div = 0; div < manager.getNumDivisions(); div++) {
				manager.removeDivision(div);
				div--;
			}
			for (int fac = 0; fac < manager.getNumFacilities(); fac++) {
				manager.removeFacility(fac);
				fac--;
			}
			scan = new Scanner(file);
			int count = 0;
			while (scan.hasNext()) {
				String line = scan.nextLine();
				String[] words = line.split(", ");
				if (words[0].equals("D")) {
					if (words.length > 2) {
						manager.addDivision(words[1], words.length - 2);
						for (int t = 2; t < words.length; t++) {
							manager.getDivision(count).addTeam(words[t], 1);
						}
						count++;
					} else {
						manager.addDivision(words[1], 2);
					}
				} else if (words[0].equals("F")) {
					try {
						manager.addFacility(words[1], Integer.parseInt(words[2]));
					} catch (NumberFormatException err) {
						throw new IllegalArgumentException();
					}
				} else if (words[0].equals("N")) {
					try {
						manager.setNumGamesSeason(Integer.parseInt(words[1]));
						numGames = Integer.parseInt(words[1]);
						for (int div = 0; div < manager.getNumDivisions(); div++) {
							for (int t = 0; t < manager.getDivision(div).getNumTeams(); t++) {
								manager.getDivision(div).getTeam(t).setNumGames(numGames);
							}
						}
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException();
					}
				} else {
					throw new IllegalArgumentException();
				}
			}
		} catch (FileNotFoundException e) {

		}
	}

	private String getFileName() {
		JFileChooser fc = new JFileChooser("./");
		int returnVal = fc.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			// Error or user canceled, either way no file name.
			throw new IllegalStateException();
		}
		File gameFile = fc.getSelectedFile();
		return gameFile.getAbsolutePath();
	}

	public static void main(String[] args) {
		new StonewallScheduleGUI();
	}

	private class StartAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(startButton)) {
				setUpMainFace();
			}
		}
	}

	private class ExitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(exitButton)) {
				System.exit(0);
			}
		}
	}

	private class CreditsAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(credits)) {
				setUpCreditsFace();
			}
		}
	}

	private class BackFromCreditsAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(backToStart)) {
				setUpStartFace();
			}
		}
	}

	private class AddDivAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addDivButton)) {
				setUpAddDivFace();
			}
		}
	}

	private class BackFromAddAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(backFromAdd) || e.getSource().equals(backFromSchedule)) {
				setUpMainFace();
			}
		}
	}

	private class OkayAddDivAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(okayAddDiv) || e.getSource().equals(numTeamsEdit)
					|| e.getSource().equals(divNameEdit)) {
				if (divNameEdit.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "The name of the division cannot be empty");
				} else if (numTeamsEdit.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "The number of teams cannot be empty");
				} else {
					String name = divNameEdit.getText().trim();
					boolean b = true;
					for (int div = 0; div < manager.getNumDivisions(); div++) {
						if (manager.getDivision(div).getName().equals(name)) {
							b = false;
						}
					}
					if (b) {
						try {
							int numTeams = Integer.parseInt(numTeamsEdit.getText().trim());
							try {
								manager.addDivision(name, numTeams);
								divNames = Arrays.copyOf(divNames, divNames.length + 1);
								divNames[divNames.length - 1] = name;
								setUpMainFace();
							} catch (IllegalArgumentException err) {
								JOptionPane.showMessageDialog(getContentPane(),
										"The number of games cannot be less than or equal to 0");
							}
						} catch (NumberFormatException error) {
							JOptionPane.showMessageDialog(getContentPane(),
									"The text you entered in the number of teams is not a number");
						}
					} else {
						JOptionPane.showMessageDialog(getContentPane(),
								"There already exists a division with that name");
					}
				}
			}
		}
	}

	private class EditNumGamesAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(editNumGames) || e.getSource().equals(numGamesField)) {
				if (!numGamesField.isEditable()) {
					numGamesField.setEditable(true);
				} else {
					try {
						int numGamesNew = Integer.parseInt(numGamesField.getText().trim());
						try {
							manager.setNumGamesSeason(numGamesNew);
							numGamesField.setEditable(false);
							numGames = numGamesNew;
							for (int div = 0; div < manager.getNumDivisions(); div++) {
								Division d = manager.getDivision(div);
								for (int team = 0; team < d.numberOfTeams(); team++) {
									Team t = d.getTeam(team);
									t.setNumGames(numGamesNew);
								}
							}

						} catch (IllegalArgumentException err) {
							JOptionPane.showMessageDialog(getContentPane(),
									"The number of games cannot be less than or equal to zero");
						}
					} catch (NumberFormatException error) {
						JOptionPane.showMessageDialog(getContentPane(),
								"The text you entered in the number of games is not a number");
					}
				}
			}
		}
	}

	private class EditDivAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(editDivButton)) {
				setUpEditDivFace();
			}
		}
	}

	private class EditDivNameAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(divisionNameEdit) || e.getSource().equals(divisionNameEditButton)) {
				if (!divisionNameEdit.isEditable()) {
					divisionNameEdit.setEditable(true);
					validate();
					repaint();
				} else {
					try {
						currentDiv.setName(divisionNameEdit.getText().trim());
						divisionNameEdit.setEditable(false);
						divNames[index] = divisionNameEdit.getText().trim();
						validate();
						repaint();
					} catch (IllegalArgumentException err) {
						JOptionPane.showMessageDialog(getContentPane(), "The name cannot be empty");
					}
				}
			}
		}
	}

	private class BackFromEditAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(backFromEdit)) {
				setUpMainFace();
			}
		}
	}

	private class EditNumTeamsAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(editNumTeams) || e.getSource().equals(numTeamsDivEdit)) {
				if (!numTeamsDivEdit.isEditable()) {
					numTeamsDivEdit.setEditable(true);
					validate();
					repaint();
				} else {
					try {
						int teams = Integer.parseInt(numTeamsDivEdit.getText());
						if (teams <= 0) {
							JOptionPane.showMessageDialog(getContentPane(),
									"The number you enter cannot be less than or equal to zero.");
						} else {
							try {
								currentDiv.setNumTeams(teams);
								numTeamsDivEdit.setEditable(false);
								if (currentDiv.numberOfTeams() == currentDiv.getNumTeams()) {
									addTeamButton.setEnabled(false);
								} else {
									addTeamButton.setEnabled(true);
								}
								validate();
								repaint();
							} catch (IllegalArgumentException err) {
								JOptionPane.showMessageDialog(getContentPane(),
										"The maximum number of teams must be greater than or equal to the current number of teams.");
							}
						}
					} catch (NumberFormatException error) {
						JOptionPane.showMessageDialog(getContentPane(), "The text you entered is not a number");
					}
				}
			}
		}
	}

	private class GoToAddTeamAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addTeamButton)) {
				setUpAddTeamFace();
			}
		}
	}

	private class AddTeamAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(okayAddTeamButton) || e.getSource().equals(editTeamName)) {
				String name = editTeamName.getText().trim();
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(getContentPane(), "The team name cannot be empty");
				} else {
					boolean b = true;
					for (int div = 0; div < manager.getNumDivisions(); div++) {
						Division d = manager.getDivision(div);
						for (int t = 0; t < d.numberOfTeams(); t++) {
							if (name.equals(d.getTeam(t).getName())) {
								b = false;
							}
						}
					}
					if (b) {
						currentDiv.addTeam(name, numGames);
						setUpEditDivFace();
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "There already exists a team with that name");
					}
				}
			}
		}
	}

	private class BackFromAddTeamAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(backFromAddTeam)) {
				setUpEditDivFace();
			}
		}
	}

	private class RemoveTeamAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(removeTeamButton)) {
				if (!teamNames.isSelectionEmpty()) {
					int index = teamNames.getSelectedIndex();
					currentDiv.removeTeam(index);

					setUpEditDivFace();
				}
			}
		}
	}

	private class ScheduleAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(schedule) || e.getSource().equals(scheduleAgain)) {
				int numDivs = manager.getNumDivisions();
				if (numDivs == 0) {
					JOptionPane.showMessageDialog(getContentPane(), "You haven't entered any divisions yet.");
				} else {
					if (!manager.allDivisionsSameSize()) {
						JOptionPane.showMessageDialog(getContentPane(),
								"WARNING: Unbalanced divisions may result in all teams not playing the same number of games");
					}
					boolean b = true;
					for (int div = 0; div < numDivs; div++) {
						Division d = manager.getDivision(div);
						if (d.getNumTeams() != d.numberOfTeams()) {
							b = false;
						}
					}

					if (b) {
						int games = 0;
						for (int fac = 0; fac < manager.getNumFacilities(); fac++) {
							games += manager.getFacility(fac).getNumGames();
						}
						int totalTeams = 0;
						for (int div = 0; div < manager.getNumDivisions(); div++) {
							totalTeams += manager.getDivision(div).getNumTeams();
						}
						if (games >= totalTeams / 2) {
							manager.schedule();
							setUpShowScheduleFace();
						} else {
							JOptionPane.showMessageDialog(getContentPane(), "There are not enough facilities");
						}
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "You haven't filled all the divisions yet.");
					}
				}
			}
		}
	}

	private class GoToFacilityAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(goToFacility) || e.getSource().equals(backFromAddFacilityScreen)) {
				setUpFacilityFace();
			}
		}
	}

	private class BackFromAddFacilityAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(backFromAddFacility)) {
				setUpMainFace();
			}
		}
	}

	private class GoToAddFacilityAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addFacilityButton)) {
				setUpAddFacilityFace();
			}
		}
	}

	private class AddFacilityAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(editFacilityGames) || e.getSource().equals(editFacilityName)
					|| e.getSource().equals(okayAddFacilityButton)) {
				String name = editFacilityName.getText().trim();
				String games = editFacilityGames.getText().trim();
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(getContentPane(), "The name cannot be empty");
				} else if (games.isEmpty()) {
					JOptionPane.showMessageDialog(getContentPane(), "The number of games cannot be empty");
				} else {
					try {
						int numGames = Integer.parseInt(games);
						boolean b = true;
						for (int fac = 0; fac < manager.getNumFacilities(); fac++) {
							if (name.equals(manager.getFacility(fac).getName())) {
								b = false;
							}
						}
						if (b) {
							try {
								manager.addFacility(name, numGames);
								setUpFacilityFace();
							} catch (Exception error) {
								JOptionPane.showMessageDialog(getContentPane(),
										"The number of games cannot be less than or equal to zero");
							}
						} else {
							JOptionPane.showMessageDialog(getContentPane(),
									"There already exists a facility of that name");
						}
					} catch (NumberFormatException err) {
						JOptionPane.showMessageDialog(getContentPane(),
								"The text you entered for the number of games is not a number");
					}
				}
			}
		}
	}

	private class RemoveFacilityAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(removeFacilityButton)) {
				if (!facilityNames.isSelectionEmpty()) {
					int index = facilityNames.getSelectedIndex();
					manager.removeFacility(index);
					setUpFacilityFace();
				}
			}
		}
	}

	private class LoadAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(loadItem)) {
				try {
					readFile(new File(getFileName()));
					setUpMainFace();
				} catch (IllegalArgumentException exp) {
					JOptionPane.showMessageDialog(getContentPane(),
							"Unable to load file or file is not of the correct format.");
				} catch (IllegalStateException exp) {
					// Don't do anything - user canceled (or error)
				}
			}
		}
	}

	private class SaveAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(saveItem)) {
				try {
					FileWriter write = new FileWriter(getFileName(), false);
					PrintWriter w = new PrintWriter(write);
					for (int d = 0; d < manager.getNumDivisions(); d++) {
						w.print("D, ");
						if (manager.getDivision(d).numberOfTeams() > 0) {
							w.print(manager.getDivision(d).getName() + ", ");
							for (int t = 0; t < manager.getDivision(d).numberOfTeams(); t++) {
								if (t != manager.getDivision(d).numberOfTeams() - 1) {
									w.print(manager.getDivision(d).getTeam(t).getName() + ", ");
								} else {
									w.print(manager.getDivision(d).getTeam(t).getName());
								}
							}
						} else {
							w.println(manager.getDivision(d).getName());
						}
						w.print("\n");
					}
					for (int f = 0; f < manager.getNumFacilities(); f++) {
						w.print("F, ");
						w.print(manager.getFacility(f).getName() + ", ");
						w.print(manager.getFacility(f).getNumGames());
						w.print("\n");
					}
					w.print("N, " + numGames);
					w.close();
				} catch (Exception err) {

				}
			}
		}
	}

	private class SaveToExcel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				FileWriter write = new FileWriter(getFileName() + ".xls", false);
				PrintWriter w = new PrintWriter(write);
				// print the division names
				for (int div = 0; div < manager.getNumDivisions(); div++) {
					if (div != manager.getNumDivisions() - 1) {
						w.print(manager.getDivision(div).getName() + "\t");
					} else {
						w.print(manager.getDivision(div).getName());
					}
				}
				w.print("\n");
				// print the team names
				for (int row = 0; row < manager.maxSize(); row++) {
					for (int div = 0; div < manager.getNumDivisions(); div++) {
						if (row < manager.getDivision(div).getNumTeams()) {
							if (div != manager.getNumDivisions() - 1) {
								w.print(manager.getDivision(div).getTeam(row).getName() + "\t");
							} else {
								w.print(manager.getDivision(div).getTeam(row).getName());
							}
						} else {
							if (div != manager.getNumDivisions() - 1) {
								w.print(" " + "\t");
							} else {
								w.print(" ");
							}
						}
					}
					w.print("\n");
				}
				// print the schedule
				w.print("\n");
				w.println("Schedule by week:");
				int weeks = manager.getDivision(0).getTeam(0).scheduleLength();
				for (int week = 0; week < gamesList.size(); week++) {
					w.print("Week " + (week + 1) + "\n");
					ListModel model = gamesList.get(week).getModel();
					for (int game = 0; game < model.getSize(); game++) {
						String current = (String) model.getElementAt(game);
						if (current.contains("bye")) {
							String[] words = current.split(" is on a ");
							w.print(words[0] + "\tis on a\tbye");
						} else {
							String[] words = current.split(" vs ");
							String[] fac = words[1].split(", ");
							w.print(words[0] + "\tvs\t" + fac[0] + "\t" + fac[1]);
						}
						w.print("\n");
					}
				}
				w.print("\n");
				w.println("Schedule by team:");
				for (int div = 0; div < manager.getNumDivisions(); div++) {
					w.println(manager.getDivision(div).getName() + ":");
					for (int team = 0; team < manager.getDivision(div).getNumTeams(); team++) {
						w.println(manager.getDivision(div).getTeam(team).getName() + ":");
						for (int game = 0; game < manager.getDivision(div).getTeam(team).scheduleLength(); game++) {
							String week = manager.getDivision(div).getTeam(team).game(game);
							if (week.contains("bye")) {
								w.println("Week " + (game + 1) + ": Bye");
							} else {
								String[] words = week.split(" vs ");
								w.println("Week " + (game + 1) + ": " + words[1]);
							}
						}
					}
					w.println();
				}
				w.close();
			} catch (Exception err) {

			}
		}
	}
}
