package cs131.pa1.filter.sequential.filters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilterExtend;
import cs131.pa1.filter.sequential.SequentialREPL;

public class RedirFilter extends SequentialFilterExtend {

	private FileWriter fileWriter;
	
	@Override
	protected String CmdName() {
		return ">";
	}
	
	@Override
	public void process() {

		if (input == null) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter(CmdLine));
			return;
		}
		
		if (Args.length < 2) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(CmdLine));
			return;
		}
				
		if (CmdLine.contains("|")) {			
			System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(CmdLine.split("\\|")[0].trim()));
			return;
		}
		
		String path = Args[1];
	
		if (path.startsWith(Filter.FILE_SEPARATOR)) {
			int i = SequentialREPL.currentWorkingDirectory.indexOf(Filter.FILE_SEPARATOR);
			
			path = SequentialREPL.currentWorkingDirectory.substring(0, i) + path;
		} else {
			path = SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + path;
		}
				
		try {
			fileWriter = new FileWriter(new File(path));
			
			super.process();		
			
			fileWriter.close();
			
		} catch (IOException e) {
			System.out.print(Message.INVALID_PARAMETER.with_parameter(CmdLine));
			return;
		}		
		
		Done = true;
	}

	@Override
	protected String processLine(String line) {
		
		try {
			fileWriter.write(line + "\n");
		} catch (IOException e) {
			
		}
		
		return null;
	}

}
