package cs131.pa1.filter.sequential.filters;

import java.io.File;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilterExtend;
import cs131.pa1.filter.sequential.SequentialREPL;

public class CdFilter extends SequentialFilterExtend {

	@Override
	protected String CmdName() {
		return "cd";
	}

	@Override
	public void process() {
		if (prev != null) {
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(CmdLine));
			return;
		} else if (next != null) {
			System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(CmdLine));
			return;
		} else if (Args.length < 2) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(CmdLine));
			return;
		}
		
		String newDir = Args[1];
		if (newDir.equals(".")) {
			return;
		} else if (newDir.equals("..")) {
			int i = SequentialREPL.currentWorkingDirectory.indexOf(Filter.FILE_SEPARATOR);
			int j = SequentialREPL.currentWorkingDirectory.lastIndexOf(Filter.FILE_SEPARATOR);
			
			if (i != j) {
				SequentialREPL.currentWorkingDirectory = SequentialREPL.currentWorkingDirectory.substring(0, j);
			}
			return;
		} 
		
		if (newDir.startsWith(Filter.FILE_SEPARATOR)) {
			int i = SequentialREPL.currentWorkingDirectory.indexOf(Filter.FILE_SEPARATOR);
			
			newDir = SequentialREPL.currentWorkingDirectory.substring(0, i) + newDir;
		} else {
			newDir = SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + newDir;
		}
		
		File dir = new File(newDir);
		if (dir.isDirectory()) {
			SequentialREPL.currentWorkingDirectory = dir.getAbsolutePath();
		} else {
			System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(CmdLine));
		}
		
		Done = true;
	}
	
	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
