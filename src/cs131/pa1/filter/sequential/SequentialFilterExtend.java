package cs131.pa1.filter.sequential;

import java.util.LinkedList;

public abstract class SequentialFilterExtend extends SequentialFilter {

	protected abstract String CmdName();
	
	protected String CmdLine;
	protected String[] Args;
	
	public boolean Done;
	
	public SequentialFilterExtend() {
		output = new LinkedList<>();
		
		Done = false;
	}
	
	public void Init(String cmdline, String[] args) {
		this.CmdLine = cmdline;
		this.Args = args;
	}
	
	public boolean CheckCommand(String cmd) {
		String[] args = cmd.split(" ");
					
		return args[0].equals(CmdName());
	}

}
