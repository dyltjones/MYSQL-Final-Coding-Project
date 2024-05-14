package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;



/**
 * This class is a menu-driven application that accepts user input from the console. It then
 * performs CRUD operations on the project tables.
 */

public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;

	
	// @formatter:off
			private List<String> operations = List.of(
					"1) Add a project",
					"2) List projects",
					"3) Select a project",
					"4) Update project details",
					"5) Delete a project"
					);
			// @formatter:on
	
	/**
	 * Entry point for application
	 * @param args Unused.
	 */
	

	public static void main(String[] args) {
		
		new ProjectsApp().processUserSelections();

	}
/**
 * This method prints the operations, gets a user menu selection, and performs the requested
 * operation. It repeats until the user request that the application terminated. 
 */
	
	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {
		try {
			int selection = getUserSelection();
			
			switch(selection) {
			case -1:
				done = exitMenu();
				break;
			
			case 1:
				createProject();
				break;
			
			case 2:
				listProjects();
				break;
			
			case 3:
				selectProject();
				break;
			
			case 4:
			updateProjectDetails();
			break;
			
			case 5:
				deleteProject();
				break;
			
			default:
				System.out.println("\n" + selection + " is not a valid selection. Try again.");
				break;
			}
		}
		catch(Exception e ) {
			System.out.println("\nError: " + e + " Try again." );
		}
		}
	}
	/**
	 * Gather user input for a project row then call the project service to create the row.
	 */
	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
				
	}
	
	/**
	 * Gets the user's input from the console and converts it to a BigDecimal.
	 * 
	 * @param prompt The promps to display on the console
	 * @return a BigDecimal value if successful.
	 * @throws Dbexception Thrown if an error occurs converting the number to a BigDecimal
	 */
	
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
			}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
	}
	}
	/**
	 * Called when the user wants to exit the application. It prints a message and returns to terminated the app
	 * @return
	 */
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}
	
	/** 
	 * prints the available menu selections. It then gets the user's menu selection 
	 * from the console and converts it to an int.
	 * 
	 * @return
	 */
	private int getUserSelection() {
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? -1 : input;
	}
	/**
	 * prints a prompt on the console and then gets user's input from the console. 
	 * then coverts the input into an int. 
	 * @param prompt
	 * @return
	 */
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
	}
	/**
	 *  Prints a prompt on the console and then gets the  users
	 *  input from the console. If the user enters nothing {@code null} is returned.  
	 *  Othersie, the trimmed input is returned.
	 * @param prompt
	 * @return
	 */
	private String getStringInput(String prompt) {
	System.out.println(prompt + ": ");
	String input = scanner.nextLine();
	
	return input.isBlank() ? null : input.trim();
	}
	// Print the menu selections, one per line. 

	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a project Id to select a project");
		
		curProject = null;
		
		curProject = projectService.fetchProjectById(projectId);	
	}
	
	/**
	 *  List all projects currently stored in the database
	 */
	private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		
		System.out.println("\nProjects: ");
		
		projects.forEach(project -> System.out.println("   " + project.getProjectId() + ": " + project.getProjectName()));
	}
	
	/** Displays the available operations and the current project if selected
	 * 
	 */
	private void printOperations() {
		System.out.println("\nThese aer the available selections. Press the Enter key to quit:");
		
		/* With Lambda expression */
		operations.forEach(line -> System.out.println("  " + line));
		
		/* With enhanced for loop */
		// for(String line : operations) {
		// System.out.println(" " + line;
		// }
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		}
		else {
			System.out.println("\nYou are working with project: " + curProject);
		}
	}
	
	/**
	 * Deletes a selected project after confirming the project ID
	 */
	private void deleteProject() {
		listProjects();
		
		Integer projectId = getIntInput("Enter the ID of the project to delete");
		
		projectService.deleteProject(projectId);
		System.out.println("Project " + projectId + " was deleted successfully.");
		
		if(Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
			curProject = null;
		}
	}
	
	/**
	 *  allows the user to update the details of the current project 
	 */
	private void updateProjectDetails() { 
		if(Objects.isNull(curProject)) {
			System.out.println("\nPlease select a project.");
			return;
		}
		// get new values for project displays current values 
		
		String projectName = 
				getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
		
		BigDecimal estimatedHours = 
				getDecimalInput("Enter the estimated hours + [" + curProject.getEstimatedHours() + "]");
		
		BigDecimal actualHours = 
				getDecimalInput("Enter the actual hours + [" + curProject.getActualHours() + "]");
		
		Integer difficulty = 
				getIntInput("Enter the project difficulty (1-5 [" + curProject.getDifficulty() + "]");
		
		String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
		
		Project project = new Project();
		
		// greate a new project instance and updated attribues
		project.setProjectId(curProject.getProjectId());
		project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
		
		project.setEstimatedHours(
				Objects.isNull(estimatedHours)? curProject.getEstimatedHours() : estimatedHours);
		
		project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
		project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
		project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
		
		// update project details in the database 
		projectService.modifyProjectDetails(project);
		
		// fetch the refreshed  and current project details
		curProject = projectService.fetchProjectById(curProject.getProjectId());
		
		
		
		
	}
	

}




