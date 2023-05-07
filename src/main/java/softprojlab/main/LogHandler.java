package softprojlab.main;

// Java imports
import java.util.Stack;

// Project imports


/**
 * Class facilitating logging output onto the standard output.
 * @author pfemeter.marton
 *
 */
public class LogHandler {
	// Private Attributes
	
	/**
	 * The number of times this.indentSize should be applied before a log entry.
	 */
	private static int indentLevel = 0;
	
	/**
	 * The number of spaces each indentation should consist of.
	 */
	private final static int indentSize = 4;
	
	/**
	 * Stack for tracking function calls.
	 */
	private static Stack<String> functionCalls = new Stack<String>();

	// Public Attributes
	
	
	
	// Private Methods

	/**
	 * Writes the current level of indentation to the standard output.
	 */
	private static void indent() {
		for (int levelIterator = 0; levelIterator < LogHandler.indentLevel; ++levelIterator) {
			for (int spaceIterator = 0; spaceIterator < LogHandler.indentSize; ++spaceIterator) {
				//System.out.print(' ');
			}
		}
	}
	
	/**
	 * Creates a log with the specified parameters, and adds/removes the log from the stack.
	 * @param caller The name of the class calling the function.
	 * @param function The name of the function to log.
	 * @param isReturning Whether the function is returning.
	 * @return The log created.
	 */
	private static String createLog(String caller, String function, Boolean isReturning) {
		//String logText = ( caller + '.' + function );
		return "";// LogHandler.createLog(logText, isReturning);
	}
	
	/**
	 * Creates a log with the specified parameters, and adds/removes the log from the stack.
	 * @param stackEntry The name of the class and the function to log.
	 * @param isReturning Whether the function is returning.
	 * @return The log created.
	 */
	private static String createLog(String stackEntry, Boolean isReturning) {
		//String logText = stackEntry;
	//	if (isReturning) {
		//	LogHandler.functionCalls.pop();
	//		logText += " returned";
	//	} else {
	//		LogHandler.functionCalls.push(logText);
	//		logText += " called";
	//	}
	//	logText += '\n';
		
		return "";
	}
	
	// Public Methods
	
	/**
	 * Writes a log to the standard output, incrementing LogHandler.indentLevel beforehand.
	 * @param caller The name of the class calling the function.
	 * @param function The name of the function to log.
	 */
	public static void logFunctionCall(String caller, String function) {
		//++LogHandler.indentLevel;
		
	//	LogHandler.indent();
	//	String log = LogHandler.createLog(caller, function, false);
		
		//System.out.print(log);
	}
	
	/**
	 * Decrements LogHandler.indentLevel.
	 */
	public static void decrementIndentation() {
		if (LogHandler.indentLevel > 0) {
		//	LogHandler.indent();
		//	String log = LogHandler.createLog(LogHandler.functionCalls.peek(), true);
			
			//System.out.print(log);
			
		//	--LogHandler.indentLevel;
		}
	}
}
