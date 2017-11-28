package com.codurance.training.tasks;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class manages and runs the application
 *
 */
public final class Application implements Runnable {
    private static final String QUIT = "quit";
    
    //List of project with a list of their tasks
    private List<Project> tasks = new ArrayList<Project>();
    
    //List of commands
    private static Map<String, Command> ListCommands = new HashMap<String, Command>();
    
    /**
     * Add of the commands in the Map
     */
    public static void InitiateListCommands() {
		ListCommands.put("delete", new Delete());
		ListCommands.put("add", new Add());
		ListCommands.put("check", new Check());
		ListCommands.put("today", new Today());
		ListCommands.put("help", new Help());
		ListCommands.put("deadline", new Deadline());
		ListCommands.put("view", new view());
		ListCommands.put("attach", new Attach());
	}

    private final BufferedReader in;
    private final PrintWriter out;

    public static void main(String[] args) throws Exception {
    	InitiateListCommands();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new Application(in, out).run();
    }

    /**
     * Parameterized constructors
     * @param reader
     * @param writer
     */
    public Application(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    /**
     * This method runs the program
     */
    public void run() {
    	
    	//Show the available commands
    	ListCommands.get("help").execute("random", tasks);
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            execute(command);
        }
    }

    /**
     * This method calls the different commands and executes them
     * @param commandLine
     * @throws ArrayIndexOutOfBoundsException
     */
    private void execute(String commandLine) throws ArrayIndexOutOfBoundsException {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        String arguments;
        if (command.length()==commandLine.length()){
        	arguments="chaine pour passer le try/catch";
        }
        else{
        	arguments = commandRest[1];
        }
        try{

        	tasks = ListCommands.get(command).execute(arguments, tasks);
        }
        catch(Exception e){
        	System.out.println(e);
        	error(command);
        }
    }

    /**
     * Dislays an error when the command is not recognized
     * @param command
     */
    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }
}
