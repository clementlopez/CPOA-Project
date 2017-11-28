package com.codurance.training.tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Display all of the tasks which the deadline is today
 * 
 */
public class Today implements Command {

	/**
	 * 
	 */
	@Override
	public List<Project> execute(String commandLine, List<Project> projects) {
		//Collect of the today's date and formatting
		String format = "dd/MM/yy";
        SimpleDateFormat formater = new java.text.SimpleDateFormat(format); 
        Date date = new java.util.Date();
        int recursivite = 1;
        for(int i=0; i<projects.size(); i++){
         	Project project = projects.get(i);
            for (Task task : project.getList()) {
            	//If the deadline is today
                if (task.getDeadline().equals(formater.format(date))) {
                	//Display of the date
                	System.out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
                	if(task instanceof Project){
                		//If the task is a subproject we display its tasks recursively
                		DisplayAllTasksInSubproject(((Project) task).getList(), recursivite);
                	}
                }
                //else, if the task is a subproject we search recursively if we can find if some of its tasks have a today's deadline
                else if (task instanceof Project){
                	DisplayRecursif((Project) task, recursivite, formater, date);
                }
            }
        }
        return projects;
	}
	
	/**
	 * Display all the tasks of a today's deadline subproject
	 * @param list
	 * @param recursivite
	 */
	private void DisplayAllTasksInSubproject(ArrayList<Task> list, int recursivite) {
		String tabulation ="";
		for (int i=0; i<recursivite;i++){
			tabulation+="\t";
		}
		recursivite++;
		for(Task task : list){
			System.out.printf(tabulation + "    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
			if(task instanceof Project){
        		DisplayAllTasksInSubproject(((Project) task).getList(), recursivite);
        	}
		}
	}

	/**
	 * Method to search recursively the today's deadline tasks in a subproject
	 * @param project
	 * @param recursivite
	 * @param formater
	 * @param date
	 */
	public void DisplayRecursif(Project project, int recursivite, SimpleDateFormat formater, Date date){
		String tabulation ="";
		for (int i=0; i<recursivite;i++){
			tabulation+="\t";
		}
		recursivite++;
		for (Task task : project.getList()) {
			//If the deadline is today
            if (task.getDeadline().equals(formater.format(date))) {
            	//Display of the date
            	System.out.printf(tabulation + "    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            	if(task instanceof Project){
            		DisplayAllTasksInSubproject(((Project) task).getList(), recursivite);
            	}
            }
            else if (task instanceof Project){
            	DisplayRecursif((Project) task, recursivite, formater, date);
            }
        }
	}

	/**
	 * The help method (How to use today)
	 * @return
	 */
	public static String HelpString() {
		String retour = "  today :\n" + "\t-displays all of the tasks which the deadline is today";
		return retour;
	}

}
