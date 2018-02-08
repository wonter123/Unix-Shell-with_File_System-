package cs131.pa1.filter.sequential.filters;

import java.io.File;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilterExtend;
import cs131.pa1.filter.sequential.SequentialREPL;

public class LsFilter extends SequentialFilterExtend {

	@Override
	protected String CmdName() {
		return "ls";
	}
	
	@Override
	public void process() {
		if (prev != null) {
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(CmdLine));
			return;
		}
		
		File dir = new File(SequentialREPL.currentWorkingDirectory);
		String files[] = dir.list();
		
		for (String file: files) {
			output.add(file);
		}
		
		Done = true;
	}

	@Override
	protected String processLine(String line) { 
		return null;
	}

}
