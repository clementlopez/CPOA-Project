package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *  This class manages the creation of new tasks or projects
 *
 */
public class Add implements Command {

	private long lastId = 0;
	boolean test;
	
	/**
	 * Create a (main) Project
	 * Or create a Task and add it to a Project or a subproject
	 * Or create a (sub)Project and add it to a Project or a subproject
	 */
	@Override
	public List<Project> execute(String commandLine, List<Project> projects) {
		
		// Split the command
		String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        
        // if the parameter is project
        if (subcommand.equals("project")) {
        	String name = subcommandRest[1];
        	/*
        	 * Create a new project named "name" with an empty list of tasks
        	 */
        	projects.add(new Project(nextId(), name));
        	
        	
        }
        // if the parameter is task
        else if (subcommand.equals("task")) {
        	/*
        	 * Add a task to the tasks' list of a project with its description
        	 */
            String[] projectTask = subcommandRest[1].split(" ", 2);
            String parent = projectTask[0]; /*parent project*/
        	String description = projectTask[1]; /*the name of the subproject*/
            ArrayList<Task> projectTasks = null;
            for(Project project : projects){
            	projectTasks = project.getList();
        		if (project.getDescription().equals(parent)){
        			test = true;
        			// Add a new task in the project
        			projectTasks.add(new Task(nextId(), description, false));
        		}
        		else if (project instanceof Project){
        			projectTasks = AddInASubproject(parent, description, projectTasks, "task");
        		}
        		if (test){
        			return projects;
        		}
        	}
            
            // if the project is not found
            if (projectTasks == null) {
                System.out.printf("Could not find a project with the name \"%s\".", projectTask[0]);
                System.out.println();
                return projects;
            }
        }
        // if the parameter is subproject
        else if (subcommand.equals("subproject")) {
        	/*
        	 * Add a subproject to the tasks' list of a project
        	 */
        	
        	String[] decoupe = subcommandRest[1].split(" ", 2);
        	
        	//parent project
        	String parent = decoupe[0];
        	
        	//the name of the subproject
        	String name = decoupe[1];
        	
            ArrayList<Task> projectTasks = null;
            test = false;
            for(Project project : projects){
            	projectTasks = project.getList();
        		if (project.getDescription().equals(parent)){
        			test = true;
        			projectTasks.add(new Project(nextId(), name));
        		}
        		else if (project instanceof Project){
        			projectTasks = AddInASubproject(parent, name, projectTasks, "subproject");
        		}
        		if (test){
        			project.setList(projectTasks);
        			return projects;
        		}
        	}
            System.out.printf("Could not find a project with the name \"%s\".", parent);
            System.out.println();
            return projects;
        }
        //if it's a wrong parameter
        else{
        	System.out.println("Error, the command add "+subcommand+" does not exist.");
        }
        return projects;
	}
	
	/**
	 * Method used to determine the id to give to the new Task (uniqueness)
	 * @return lastId
	 */
	private long nextId() {
        return ++lastId;
    }
	
	/**
	 * The help method (How to use add)
	 * @return
	 */
	public static String HelpString() {
		
		String retour = "  add project <project name> :\n"
						+"\t-create a new project named <project name> with an empty list of tasks\n"
						+ "  add task <project name> <task description> :\n"
						+"\t-add a task to the tasks' list of the project <project name> with a description\n"
						+ "  add subproject <project name> <subproject name> :\n"
						+"\t-add a project to the tasks' list of the project <project name>";
		return retour;
	}
	
	/**
	 * Method used to search recursively the project in which to place the task or subproject
	 * @param parent
	 * @param name
	 * @param projectTasks
	 * @param mode
	 * @return
	 */
	public ArrayList<Task> AddInASubproject(String parent, String name, ArrayList<Task> projectTasks, String mode){
		this.test = false;
		ArrayList<Task> subProject = new ArrayList<Task>();
		for(Task task : projectTasks){
			if(task instanceof Project){
				subProject = ((Project) task).getList();
	    		if (task.getDescription().equals(parent)){
	    			
	    			this.test = true;
	    			if(mode=="subproject"){
	    				subProject.add(new Project(nextId(), name));
	    			}
	    			else{
	    				subProject.add(new Task(nextId(), name, false));
	    			}
	    		}
	    		else{    			
	    			subProject = AddInASubproject(parent, name, subProject, mode);
	    		}
			}
    		
    		if (test){
    			((Project) task).setList(subProject);
    			return projectTasks;
    		}
    	}
		return projectTasks;
		
	}

}
