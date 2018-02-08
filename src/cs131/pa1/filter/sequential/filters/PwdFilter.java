package cs131.pa1.filter.sequential.filters;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilterExtend;
import cs131.pa1.filter.sequential.SequentialREPL;

public class PwdFilter extends SequentialFilterExtend {

	@Override
	protected String CmdName() {
		return "pwd";
	}
	
	@Override
	public void process() {
		if (prev != null) {
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(CmdLine));
			return;
		}
		
		output.add(SequentialREPL.currentWorkingDirectory);
		
		Done = true;
	}

	@Override
	protected String processLine(String line) {
		return null;
	}

}
