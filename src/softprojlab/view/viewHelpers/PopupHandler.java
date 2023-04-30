package softprojlab.view.viewHelpers;

// Java Imports
import javax.swing.SwingWorker;

import java.util.function.Consumer;
import java.util.function.Supplier;

// Project Imports


/**
 * Class for handling waiting in a background thread for the user's response to a question.
 * Usage: instantiate this class, then call its execute function. Instantiation parameters can be found in the constructors documentation.
 * @author pfemeter.marton
 */
public class PopupHandler extends SwingWorker<Integer, Object> {

	/**
	 * The source where we are going to get the response from.
	 */
	private Supplier<Integer> questionCallback;
	
	/**
	 * The function to call once the user responds.
	 */
	private Consumer<Integer> doneCallback;

	/**
	 * Default constructor. PopupHandler.execute() must be called afterwards.
	 * @param doCallback The function that will supply the result from the user.
	 * @param doneCallback The function to call once the user responds (with the user's response as its parameter).
	 */
	public PopupHandler(Supplier<Integer> doCallback, Consumer<Integer> doneCallback) {
		this.questionCallback = doCallback;
		this.doneCallback = doneCallback;
	}

	/**
	 * Executes this.questionCallback in a background thread.
	 */
	protected Integer doInBackground() throws Exception {
		return this.questionCallback.get();
	}
	
	/**
	 * Calls this.doneCallback on the main Swing thread (Swing EDT).
	 */
	protected void done() {
        try {
        	this.doneCallback.accept(this.get());
        } catch (Exception ignore) {
        	System.err.println("Error after being done in SwingWorker: " + ignore);
        }
	}

}
