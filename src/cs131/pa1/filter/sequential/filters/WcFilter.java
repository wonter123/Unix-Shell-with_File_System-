package cs131.pa1.filter.sequential.filters;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilterExtend;

public class WcFilter extends SequentialFilterExtend {

	private int lines;
	private int words;
	private int chars;
	
	@Override
	protected String CmdName() {
		return "wc";
	}
	
	@Override
	public void process() {
		
		if (input == null) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter(CmdLine));
			return;
		}
		
		lines = 0;
		words = 0;
		chars = 0;

		super.process();
		
		output.add(lines + " " + words + " " + chars);
		
		Done = true;
	}

	@Override
	protected String processLine(String line) {
		lines++;
		words += line.split(" ").length;
		chars += line.length();
		return null;
	}

}
