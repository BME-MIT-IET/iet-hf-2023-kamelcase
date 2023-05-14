package softprojlab.model;

// static JUnit imports

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

// static Mockito imports

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.ArgumentMatchers.anyInt;

// Java imports

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// JUnit imports

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// project imports

import softprojlab.model.character.Virologist;
import softprojlab.model.field.Field;


public class GameValidator {

    // private variables
    
    private Game testDummy;
    private Field testLocation;
    private Virologist testPlayer;
    private int playerUpdateCallbackCounter;
    
    // constant variables
    
    private final int VALID_NUMBER_OF_PLAYERS = 2;
    private final int INVALID_NUMBER_OF_PLAYERS = 0;
    private final String BASIC_USER_INPUT_QUESTION = "hello question?";
    private final ArrayList<String> BASIC_USER_ANSWER_OPTION_LIST = new ArrayList<String>(
                                                                                            List.of(
                                                                                                    "option 1",
                                                                                                    "option 2", 
                                                                                                    "option 3"
                                                                                                    )
                                                                                            );
    private final int VALID_USER_ANSWER_INDEX = 1;
    private final int INVALID_USER_ANSWER_INDEX = -10;
    private final Function<String, Integer> VALID_USER_ANSWER_CALLBACK = (input) -> {
        return this.VALID_USER_ANSWER_INDEX;
    };
    private final Function<String, Integer> INVALID_USER_ANSWER_CALLBACK = (input) -> {
        return this.INVALID_USER_ANSWER_INDEX;
    };
    private final int TEST_UID = 404;
    
    // clean test setup
    
    @BeforeEach
    void setUp() {
        this.testDummy = new Game();
        
        this.testPlayer = mock();
        this.playerUpdateCallbackCounter = 0;
        when(this.testPlayer.update()).then((input) -> {
            ++this.playerUpdateCallbackCounter;
            return input;
        });
        
        this.testLocation = mock();
        when(this.testLocation.getPlayers()).thenReturn(
                                                        new ArrayList<Virologist>(
                                                                                    List.of(this.testPlayer)
                                                                                    )
                                                        );
        doCallRealMethod().when(this.testLocation).setUid(anyInt());
        when(this.testLocation.getUid()).thenCallRealMethod();
    }

    // map generation tests
    
    @Test
    void generateThenStartNoExceptionTest() {
        this.testDummy.generate(this.VALID_NUMBER_OF_PLAYERS, false);
        
        assertDoesNotThrow(this.testDummy::startGame);
    }
    
    @Test
    void startWithoutGenerateThrowsExceptionTest() {
        assertThrows(Exception.class, this.testDummy::startGame);
    }
    
    @Test
    void generateWithInvalidNumberOfPlayersThenStartThrowsExceptionTest() {
        this.testDummy.generate(this.INVALID_NUMBER_OF_PLAYERS, false);
        
        assertThrows(Exception.class, this.testDummy::startGame);
    }
    
    @Test 
    void manualGenerateOneFieldWithOneVirologistThenStartNoExceptionTest() {
        this.testDummy.addField(this.testLocation);
        
        assertDoesNotThrow(this.testDummy::startGame);
        
    }

    // user input tests
    
    @Test
    void askUserMultiQuestionWithValidAnswerTest() {
        Game.questionCallback = this.VALID_USER_ANSWER_CALLBACK;
        
        assertEquals(this.VALID_USER_ANSWER_INDEX, Game.askForUserInput(BASIC_USER_INPUT_QUESTION, BASIC_USER_ANSWER_OPTION_LIST, false));
    }
    
    @Test
    void askUserMultiQuestionWithInvalidAnswerThrowsExceptionTest() {
        Game.questionCallback = this.INVALID_USER_ANSWER_CALLBACK;
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Game.askForUserInput(BASIC_USER_INPUT_QUESTION, BASIC_USER_ANSWER_OPTION_LIST, false);
        });
    }
    
    @Test
    void askUserYesNoQuestionWithValidAnswerTest() {
        Game.questionCallback = this.VALID_USER_ANSWER_CALLBACK;
        
        boolean yesAnswerWithIndexOfOneBooleanValue = true;
        
        assertEquals(yesAnswerWithIndexOfOneBooleanValue, Game.askYesNo(BASIC_USER_INPUT_QUESTION));
    }
    
    @Test
    void askUserYesNoQuestionWithInvalidAnswerThrowsExceptionTest() {
        Game.questionCallback = this.INVALID_USER_ANSWER_CALLBACK;
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Game.askYesNo(BASIC_USER_INPUT_QUESTION);
        });
    }
    
    // UniqueObject tests
    
    @Test
    void addingThenGettingObjectWithUidTest() {
        int assignedUid = this.testDummy.addNewUniqueObject(this.testLocation);
        
        assertEquals(this.testLocation, this.testDummy.findUniqueObject(assignedUid));
    }
    
    @Test
    void gettingObjectWithUidWithoutAddingTest() {
        assertNull(this.testDummy.findUniqueObject(this.TEST_UID));
    }
    
    // round updating test
}
