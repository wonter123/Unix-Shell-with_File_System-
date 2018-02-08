package cs131.pa1.filter.sequential;

import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class SequentialREPL {

	public static String currentWorkingDirectory;
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		SequentialCommandBuilder.InitFilterClasses();
		System.out.print(Message.WELCOME);
		Scanner console = new Scanner(System.in);
		
		while (true) {
			System.out.print(Message.NEWCOMMAND);
			
			String cmd = console.nextLine();
			
			if (cmd.equals("")) {
				continue;
			}
			
			List<SequentialFilter> filters = SequentialCommandBuilder.createFiltersFromCommand(cmd);
			if (filters == null) {
				continue;
			}			
			
			for (SequentialFilter filter : filters) {
				filter.process();		
				
				if (!((SequentialFilterExtend)filter).Done) {
					break;
				}
			}
			
			Queue<String> outs = filters.get(filters.size() - 1).output;
			if (outs != null) {
				for (String o:outs) {
					System.out.println(o);
				}
			}
			
			if (cmd.equals("exit")) {
				break;
			}
		}
//      System.out.print(Message.GOODBYE);	
		console.close();
	}

}
