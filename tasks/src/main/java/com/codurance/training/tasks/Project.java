package com.codurance.training.tasks;


import java.util.ArrayList;
/**
 * A Project : a task with a list of tasks
 *
 */
public class Project extends Task{

	ArrayList<Task> list;
	
	/**
	 * Parameterized constructors
	 * @param id
	 * @param name
	 */
	public Project(long id, String name) {
		super(id, name, false);
		list = new ArrayList<Task>();
	}

	/**
	 * return the tasks' list of the project
	 * @return
	 */
	public ArrayList<Task> getList() {
		return list;
	}
	
	/**
	 * 
	 * @param nom
	 * @return
	 */
	public Task getByNom(String nom){
		for (Task task : this.getList()) {
			if (task.getDescription().equals(nom)){
				return task;
			}
		}
		return null;
	}

	/**
	 * set the tasks' list of the project
	 * @param list
	 */
	public void setList(ArrayList<Task> list) {
		this.list = list;
	}

	

}

