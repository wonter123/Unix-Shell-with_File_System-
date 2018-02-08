package cs131.pa1.filter.sequential.filters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilterExtend;
import cs131.pa1.filter.sequential.SequentialREPL;

public class HeadFilter extends SequentialFilterExtend {

	@Override
	protected String CmdName() { 
		return "head";
	}
	
	@Override
	public void process() {
		if (prev != null) {
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(CmdLine));
			return;
		} else if (Args.length < 2) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(CmdLine));
			return;
		} else if (Args.length > 3) {
			System.out.print(Message.INVALID_PARAMETER.with_parameter(CmdLine));
			return;
		} else if (Args.length == 2 && Args[1].startsWith("-")) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(CmdLine));
			return;
		}
		
		int lines = 10;
		String path = Args[1];
						
		if (Args.length == 3) {
			if (Args[1].startsWith("-")) {
				try {
					lines = Integer.parseInt(Args[1].substring(1));
				} catch (NumberFormatException e) {
					System.out.print(Message.INVALID_PARAMETER.with_parameter(CmdLine));
					return;					
				}				
			} else {
				System.out.print(Message.INVALID_PARAMETER.with_parameter(CmdLine));
				return;
			}
			
			path = Args[2];
		}
		
		if (path.startsWith(Filter.FILE_SEPARATOR)) {
			int i = SequentialREPL.currentWorkingDirectory.indexOf(Filter.FILE_SEPARATOR);
			
			path = SequentialREPL.currentWorkingDirectory.substring(0, i) + path;
		} else {
			path = SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + path;
		}
				
		try {
			Scanner scan = new Scanner(new File(path));

			while (scan.hasNextLine() && lines > 0) {
				output.add(scan.nextLine());
				lines--;
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			System.out.print(Message.FILE_NOT_FOUND.with_parameter(CmdLine));
			return;
		}
		
		Done = true;
	}

	@Override
	protected String processLine(String line) {
		return null;
	}

}
