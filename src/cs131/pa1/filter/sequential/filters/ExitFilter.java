package cs131.pa1.filter.sequential.filters;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilterExtend;

public class ExitFilter extends SequentialFilterExtend {

	@Override
	protected String CmdName() {
		return "exit";
	}
	
	@Override
	public void process() {
		if (prev != null) {
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(CmdLine));
			return;
		} else if (next != null) {
			System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(CmdLine));
			return;
		}
		
		System.out.print(Message.GOODBYE.toString());
		
		Done = true;
	}
	
	@Override
	protected String processLine(String line) {		
		return null;
	}

}
