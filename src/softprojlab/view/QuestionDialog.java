package softprojlab.view;

// Java Imports
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

import softprojlab.view.viewHelpers.PopupHandler;

// Project Imports


/**
 * 
 */
public class QuestionDialog extends ListItemManager {
	
	// Static Attributes
	
	/**
	 * Unique ID for Serialization 
	 */
	private static final long serialVersionUID = 4443158612739839597L;

	/**
	 * Indicates when the user has answered the current question.
	 */
	private static Semaphore result = new Semaphore(0);	
	
	// Private Attributes 
	
    /**
     * Reference to the single MainWindow instance.
     */
	private MainWindow mainWindow;
	
	// Public Attributes
	
	/**
	 * 
	 */
	public boolean isVisible;
	
	// Constructors
	
	/**
	 * Default constructor.
	 * @param root Reference to the single MainWindow instance.
	 */
	public QuestionDialog(MainWindow root) {
		super(false);
		this.mainWindow = root;
	}
	
	// Private Methods
	
	/**
	 * Callback function used to wait in a background thread for the user's response
	 * @return this.selectedIndex, the user's response.
	 */
	private Integer applyQuestion() {
		try {
			QuestionDialog.result.acquire();
		} catch (InterruptedException e1) {
			System.err.println("QuestionDialog.askQuestion methodinterrupted");
			e1.printStackTrace();
		}
		
		return this.selectedIndex;
	}
	
	/**
	 * Configures this component for asking a new question. Does not update the UI.
	 * @param message Message to show the user. Its first row should be the question we want to present, then the possible responses, each in a new row.
	 */
	private void setupQuestion(String message) {
		this.selectedIndex = -1;
		String[] options = message.split("\n");
		if (options.length <= 1) {
			throw new IllegalArgumentException("Ask a question with at least 1 choice");
		}
		
		String question = options[0];
		String[] realOptionList = new String[ (options.length - 1) ];
		for (int i = 1; i < options.length; ++i) {
			realOptionList[i-1] = options[i];
		}
		
		this.setOptions(question, realOptionList);
	}
	
	// Public Methods
	
	/**
	 * Return the default width of this component. Used for correctly being rendered in MainWindow.
	 * @return The default width of this component
	 */
	public int getDefaultWidth() {
		return 500;
	}
	
	/**
	 * Return the default height of this component. Used for correctly being rendered in MainWindow.
	 * @return The default height of this component
	 */
	public int getDefaultHeight() {
		return 300;
	}
	
	/**
	 * Asks a question from the user using a popup window, and starts a background thread for waiting for the response.
	 * @param message Message to show the user. Its first row should be the question we want to present, then the possible responses, each in a new row.
	 * @param doneCallback The function that should be called once the user responded to the question. Its only parameter is the index of the option selected.
	 * @return
	 */
	public void askQuestion(String message, Consumer<Integer> doneCallback) {
		this.setupQuestion(message);
		
		PopupHandler callbackThread = new PopupHandler(this::applyQuestion, doneCallback);
		callbackThread.execute();
		
		this.isVisible = true;
		this.mainWindow.invalidate();
	}
	
	/**
	 * Asks a question from the user using a popup window. Waiting for the response is synchronous (i.e. this method blocks execution until the user responds).
	 * @param message Message to show the user. Its first row should be the question we want to present, then the possible responses, each in a new row.
	 * @return this.selectedIndex, the user's response.
	 */
	public int askQuestion(String message) {
		this.setupQuestion(message);
		
		this.isVisible = true;
		this.mainWindow.invalidate();
		
		return this.applyQuestion();
	}
	
	/**
	 * Called when the user clicks on an already selected list item.
	 * This essentially means that the user has chosen an option
	 */
	public void onListItemClicked() {
		QuestionDialog.result.release();
		
		this.isVisible = false;
		this.mainWindow.invalidate();
	}

}
