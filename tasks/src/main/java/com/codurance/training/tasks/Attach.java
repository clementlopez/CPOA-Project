package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Attach a task to an other project 
 *
 */
public class Attach implements Command {

	@Override
	public List<Project> execute(String commandLine, List<Project> projects) {

		//Retrieve the arguments (nomProjet and id String)
		String[] subcommandRest = commandLine.split(" ", 2);
		String nomprojet = subcommandRest[0];
        String idString = subcommandRest[1];
        
        // Convert the string (id) to an integer
        int id = Integer.parseInt(idString);
        Task t = null;
        
        //Run through the projects
        for(Project project : projects){
        	//Run through the tasks' of the selected project
        	for (Task task : project.getList()) {
        		//If the task has the needed id, we keep it
        		if (task.getId() == id) {
        			t = task;
        		}
        		//else we search recursively if we can found it
        		else if (task instanceof Project){
        			t = getTaskInASubproject(nomprojet, id, (Project) task);
        		}
        		if(t!=null){
        			break;
        		}
        	}
        	if(t!=null){
    			break;
    		}
        }
        if (t!=null){
        	Project projectTasks = null;
        	//Seek the project to attach the task
        	for(Project project : projects){
        		//If the project has the good name, we keep it
        		if (project.getDescription().equals(nomprojet)){
        			projectTasks = project;
        			projectTasks.getList().add(t);
        		}
        		//else we search recursively if we can found it
        		else if (project instanceof Project){
        			projectTasks = AttachInASubproject(nomprojet, id, project.getList(), t);
        		}
        		if(projectTasks!=null){
        			return projects;
        		}
            }
        	//if the project does not exist :
        	if (projectTasks == null) {
                System.out.printf("Could not find a project with the name \"%s\".", nomprojet);
                System.out.println();
            }
    		return projects;
        }
        //if the task does not exist:
        else{
        	System.out.printf("Could not find a task with an ID of %d.", id);
            System.out.println();
        }
		return projects;
	}
	
	/**
	 * Search recursively the task in a subproject
	 * @param nomprojet
	 * @param id
	 * @param project
	 * @return
	 */
	private Task getTaskInASubproject(String nomprojet, int id, Project project) {
    	Task t=null;
		for (Task task : project.getList()) {
			//If the task has the needed id, we keep it
    		if (task.getId() == id) {
    			t = task;
    			return t;
    		}
    		//else we search recursively if we can found it
    		else if (task instanceof Project){
    			t = getTaskInASubproject(nomprojet, id, (Project) task);
    			if (t!=null){
    				return t;
    			}
    		}
    	}
		return null;
	}

	/**
	 * Search recursively the project to which the task is to be attached (and attach the task to the project if we find it)
	 * @param nomprojet
	 * @param id
	 * @param Tasks
	 * @param t
	 * @return
	 */
	private Project AttachInASubproject(String nomprojet, int id, ArrayList<Task> Tasks, Task t) {
		Project projectTasks = null;
		for (Task task : Tasks){
			if (task instanceof Project){
				//If the project has the good name, we keep it
	    		if (task.getDescription().equals(nomprojet)){
	    			projectTasks = (Project) task;
	    			projectTasks.getList().add(t);
	    		}
	    		//else we search recursively if we can found it
	    		else {
	    			projectTasks = AttachInASubproject(nomprojet, id, ((Project) task).getList(), t);
	    		}
			}
    		return projectTasks;
        }
		return null;
	}

	/**
	 * The help method (How to use attach)
	 * @return
	 */
	public static String HelpString() {
	       return 	"  attach <project name> <task ID> :\n"
	        		+ "\t-attach a task to the project named <project name>\n";
		}

}
