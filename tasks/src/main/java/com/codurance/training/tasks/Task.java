package com.codurance.training.tasks;

/**
 * 
 * This class manages the Tasks 
 *
 */
public class Task{
    private final long id;
    private final String description;
    private boolean done;
    private String deadline;

    /**
     *  Parameterized constructors
     * @param id
     * @param description
     * @param done
     */
    public Task(long id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = "pas de deadline";
    }
    
    /**
     * Parameterized constructors
     * @param id
     * @param description
     * @param done
     * @param deadline
     */
    public Task(long id, String description, boolean done, String deadline) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
    }

    /**
     * 
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @return
     */
    public boolean isDone() {
        return done;
    }

    /**
     * 
     * @param done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * 
     * @return
     */
	public String getDeadline() {
		return deadline;
	}

	/**
	 * 
	 * @param deadline
	 */
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
}
