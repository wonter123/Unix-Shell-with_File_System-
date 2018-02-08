package cs131.pa1.filter.sequential;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.filters.RedirFilter;

public class SequentialCommandBuilder {
	
	private static String[] filterClassNames = {
		"cs131.pa1.filter.sequential.filters.ExitFilter",
		
		"cs131.pa1.filter.sequential.filters.PwdFilter",
		"cs131.pa1.filter.sequential.filters.CdFilter",
		"cs131.pa1.filter.sequential.filters.LsFilter",
		
		"cs131.pa1.filter.sequential.filters.HeadFilter",
		"cs131.pa1.filter.sequential.filters.GrepFilter",
		"cs131.pa1.filter.sequential.filters.WcFilter",
	};
	
	private static ArrayList<SequentialFilterExtend> filterClasses;
	
	public static void InitFilterClasses() {
		filterClasses = new ArrayList<SequentialFilterExtend>();
		for (String filterClassName : filterClassNames) {
			try {
				Class<?> clazz = Class.forName(filterClassName);
				// Class array to store every filters
				filterClasses.add((SequentialFilterExtend)clazz.getConstructor().newInstance());
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				System.err.println("Filter Class Not Found " + filterClassName);
			}
			
		}
	}
	
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		List<SequentialFilter> filters = new ArrayList<SequentialFilter>();
		
		//pre process command input, replace extra whitespace
		command = command.trim();
		command = command.replaceAll(" +", " ");
		
		SequentialFilter finalFilter = determineFinalFilter(command);
		command = adjustCommandToRemoveFinalFilter(command);
		
		if (!command.equals("")) {
			String[] cmds = command.split("\\|");
			for (String cmd: cmds){
				cmd = cmd.trim();
				
				SequentialFilter filter = constructFilterFromSubCommand(cmd);
				
				if (filter == null) {
					System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(cmd));
					return null;
				}
				
				filters.add(filter);
			}
		}
		
		if (finalFilter != null) {
			filters.add(finalFilter);
		}
		
		linkFilters(filters);
		
		return filters;
	}
	
	private static SequentialFilter determineFinalFilter(String command){
		int i = command.lastIndexOf(">");
		if (i != -1) {
			String cmd = command.substring(i, command.length());
			String[] args = cmd.split(" ");
			SequentialFilterExtend filter = new RedirFilter();
			filter.Init(cmd, args);
			
			return filter;
		}
		
		return null;
	}
	
	// Judge have > or not 
	private static String adjustCommandToRemoveFinalFilter(String command){
		int i = command.lastIndexOf(">");
		if (i != -1) {			
			return command.substring(0, i).trim();
		}
		
		return command;
//		String c = command.split("\\|")[command.split("\\|").length-1];
//		if (c.split(" ")[c.split(" ").length-2].equals(">")) {
//			SequentialFilter k = constructFilterFromSubCommand(c.substring(0, c.lastIndexOf(">")-1));
//			list.add(k);
//			String redir = c.substring(c.lastIndexOf(">")+2);
//			j = Redirection(redir);
//		} else {
//			j = constructFilterFromSubCommand(i);
	}
	
	private static SequentialFilter constructFilterFromSubCommand(String cmd){
		
		for (SequentialFilterExtend filterClass : filterClasses) {
			try {
				if (filterClass.CheckCommand(cmd)) {
					String[] args = cmd.split(" ");
					SequentialFilterExtend filter = (SequentialFilterExtend)filterClass.getClass().getConstructor().newInstance();
					filter.Init(cmd, args);
					return filter;
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException |InstantiationException e) {
				assert(false);
			}
		}
		
		return null;
	}

	private static void linkFilters(List<SequentialFilter> filters){
		for (int i = 0; i < filters.size() - 1; i++) {
			filters.get(i).setNextFilter(filters.get(i + 1));
		}
//		    if(filters.size() > 1){
//			SequentialFilter first = filters.get(0);
//			first.setNextFilter(filters.get(1));
//			for(int i = 1; i < filters.size() - 2; i++){
//				filters.get(i).setPrevFilter(filters.get(i-1));
//				filters.get(i).setNextFilter(filters.get(i+1));
//			}
//			SequentialFilter last = filters.get(filters.size()-1);
//			last.setPrevFilter(filters.get(filters.size() - 2));
//		}
	}
}
