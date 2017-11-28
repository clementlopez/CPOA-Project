package com.codurance.training.tasks;

import java.util.List;

/**
 * Displays commands' list
 */
public class Help implements Command {
	
	/**
	 * Display the help (how to use each methods or one in particular)
	 */
	@Override
	public List<Project> execute(String commandLine, List<Project> projects) {
		String[] subcommandRest = commandLine.split(" ", 2);
		switch(subcommandRest[0]){
		case "add":
			System.out.println(Add.HelpString());
			break;
		case "check":
			System.out.println(Check.HelpString());
			break;
		case "delete":
			System.out.println(Delete.HelpString());
			break;
		case "view":
			System.out.println(view.HelpString());
			break;
		case "deadline":
			System.out.println(Deadline.HelpString());
			break;
		case "today":
			System.out.println(Today.HelpString());
			break;
		case "attach":
			System.out.println(Attach.HelpString());
			break;
		default:
			System.out.println(Add.HelpString()+"\n"
					+view.HelpString()+"\n"
					+Check.HelpString()+"\n"
					+Deadline.HelpString()+"\n"
					+Today.HelpString()+"\n"
					+Delete.HelpString()+"\n"
					+Attach.HelpString()+"\n"
					+Help.HelpString()+"\n"
					+"  quit :\n"
				    +"\t-close the application");
			break;
		}
		return projects;
	}
	
	/**
	 * The help method (How to use help)
	 * @return
	 */
	public static String HelpString() {
		String retour = "  help : \n\t-displays the commands' list";
		return retour;
	}

}
