package com.codurance.training.tasks;

import java.util.List;


/*
 * Manage the display of tasks and projects
 */
public class view implements Command {

	/**
	 * Show the tasks'list of each project in different ways depending on the user's request
	 */
	@Override
	public List<Project> execute(String commandLine, List<Project> projects) {
		
		switch(commandLine){
		/**
		 * Shows the tasks' list of each project
		 */
			case "by project":
				for(int i=0; i<projects.size(); i++){
		        	Project project = projects.get(i);
		        	//Display the project name
		        	System.out.println(project.getDescription());
		            for (Task task : project.getList()) {
		            	//Display the tasks' list of the selected project
		            	System.out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
		            	if (task instanceof Project){
		            		//Method to display recursively the subtasks (tasks of subprojects)
		            		sousAffichage((Project) task, 1, "project");
		            	}
		            }
		            System.out.println();
		        }
				break;

			/**
			* Shows the tasks' list of each project by deadline
			*/
			case "by deadline":
				for(int i=0; i<projects.size(); i++){
		        	Project project = projects.get(i);
		        	
		        	//Display the project name
		        	System.out.println("Project " + project.getDescription());
		        	//Display the tasks' list of the selected project
		            for (Task task : project.getList()) {
		            	//If the selected task is a project, display the tasks' list of the selected subproject (recursively...)
		            	if (task instanceof Project){
		            		System.out.println("\tSubproject " + task.getId() + " deadline : " + task.getDeadline());
		            		//Method to display recursively the subtasks (tasks of subprojects)
		            		sousAffichage((Project) task, 1, "deadline");
		            	}
		            	else{
		            		System.out.println("\tTask " + task.getId() + " deadline : " + task.getDeadline());
		            	}
		            }
		        }
				break;
		}
		return projects;
	}
	
	/**
	 * Display recursively the subtasks (tasks of subproject)
	 * @param project
	 * @param nbRecur
	 * @param mode
	 */
	public void sousAffichage(Project project, int nbRecur, String mode){
		String tabulation = "";
		for (int i =0; i<nbRecur;i++){
			tabulation += "\t";
		}
		if(mode.equals("project")){
			for (Task task : project.getList()) {
	        	System.out.printf(tabulation +"    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
	        	if (task instanceof Project){
	        		sousAffichage((Project) task, nbRecur+1, mode);
	        	}
	        }
		}
		else if (mode.equals("deadline")){
			for (Task task : project.getList()) {
            	if (task instanceof Project){
            		System.out.println("\tSubproject " + task.getId() + " deadline : " + task.getDeadline());
            		sousAffichage((Project) task, nbRecur+1, mode);
            	}
            	else{
            		System.out.println("\tTask " + task.getId() + " deadline : " + task.getDeadline());
            	}
            }
		}
	}

	/**
	 * The help method (How to use view)
	 */
	public static String HelpString() {
       return 	"  view by :\n"
        		+ "\t-project : shows the tasks' list of each project\n"
        		+ "\t-date : shows the tasks' list by date\n"
        		+ "\t-dead line : shows the tasks' list of each project";
	}

}
