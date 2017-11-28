package com.codurance.training.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Add a deadline to a task
 * 
 */
public class Deadline implements Command {

	/**
	 * The help method (How to use deadline)
	 * @return
	 */
	public static String HelpString() {
		return  "  deadline <task ID> <dd/MM/yy> :\n"
        		+ "\t-set a deadline to a task";
	}

	/**
	 * Method to give the deadline to the right task
	 */
	@Override
	public List<Project> execute(String commandLine, List<Project> projects) {
    	
		//Split the command
   	 	String[] subcommandRest = commandLine.split(" ", 2); 
   	 
   	 	//Cast the String to an integer
        int id = Integer.parseInt(subcommandRest[0]);
        
        //We check if the date is in the right format
        String format = "dd/MM/yy";
		SimpleDateFormat df = new SimpleDateFormat(format);
		boolean pass = false;
		try {
            df.parse(subcommandRest[1]);
            pass = true;
        } catch (ParseException ex) {
            System.out.println("Error! The correct date format is: " + format);
        }
		
		if(pass){
			//We add the deadline to the right task (it is sought recursively if necessary)
	        for(int i=0; i<projects.size(); i++){
	        	Project project = projects.get(i);
	           for (Task task : project.getList()) {
	                if (task.getId() == id) {
	                    task.setDeadline(subcommandRest[1]);
	                    return projects;
	                }
	                else if(task instanceof Project){
	                	DeadlineInSubproject(((Project) task).getList(), id, subcommandRest[1]);
	                }
	            }
	        }
		}
		return projects;
	}

	/**
	 * Recursive method to give the deadline to the right task
	 * @param list
	 * @param id
	 * @param deadline
	 */
	private void DeadlineInSubproject(ArrayList<Task> list, int id, String deadline) {
		for (Task task : list) {
            if (task.getId() == id) {
                task.setDeadline(deadline);
                return;
            }
            else if(task instanceof Project){
            	DeadlineInSubproject(((Project) task).getList(), id, deadline);
            }
        }
	}

}
