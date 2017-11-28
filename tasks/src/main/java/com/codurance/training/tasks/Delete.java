package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Allow to delete tasks
 *
 */
public class Delete implements Command {

	/**
	 * Method to delete a task (by its id)
	 */
	public List<Project> execute(String commandLine, List<Project> projects) {
		//cast the string in int
		int id = Integer.parseInt(commandLine);
		boolean test1 = false, test2 = false;
		Iterator<Project> iterProj = projects.iterator();
		while(iterProj.hasNext()){
			Project project = iterProj.next();
			Iterator<Task> iterTask = project.getList().iterator();
            while(iterTask.hasNext()){
            	Task task = iterTask.next();
                if (task.getId() == id) {
                	iterTask.remove();
                	test1 = true;
                }
                else if(task instanceof Project){
                	test2 = DeleteInASubproject(id, ((Project) task).getList());
                }
            }
        }
		if ((!test1)&&(!test2)){
			System.out.printf("Could not find a task with an ID of %d.", id);
		}
        return projects;
		
	}

	/**
	 * Method to search recursively the task to delete in a subproject
	 * @param id
	 * @param list
	 * @return
	 */
	private boolean DeleteInASubproject(int id, ArrayList<Task> list) {
		boolean test1 = false, test2 = false;
		Iterator<Task> iterTask = list.iterator();
        while(iterTask.hasNext()){
        	Task task = iterTask.next();
			if (task.getId() == id) {
				iterTask.remove();
				test1 = true;
			}
			else if(task instanceof Project){
				test2 = DeleteInASubproject(id, ((Project) task).getList());
			}
        }
        if((test1==true)||(test2==true)){
        	return true;
        }
        return false;
	}

	/**
	 * The help method (How to use delete)
	 * @return
	 */
	public static String HelpString() {
		String retour = "  delete <task ID> :\n" + "\t-delete the task with the ID <task ID>";
		return retour;
	}

}
