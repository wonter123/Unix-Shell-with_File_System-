package cs131.pa1.filter.sequential.filters;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilterExtend;

public class GrepFilter extends SequentialFilterExtend {

	@Override
	protected String CmdName() {
		return "grep";
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

		super.process();
		
		Done = true;
	}

	@Override
	protected String processLine(String line) {
		
		if (line.contains(Args[1])) {
			return line;
		}		
		return null;
	}

}
