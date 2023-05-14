package softprojlab.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameValidator {
    
    private Game testDummy;
    private final int VALID_NUMBER_OF_PLAYERS = 2;
    private final int INVALID_NUMBER_OF_PLAYERS = 0;
    
    @BeforeEach
    void setUp() {
        this.testDummy = new Game();
    }

    @Test
    void generateThenStartNoExceptionTest() {
        this.testDummy.generate(this.VALID_NUMBER_OF_PLAYERS, false);
        
        assertDoesNotThrow(this.testDummy::startGame);
    }
    
    @Test
    void startWithoutGenerateThrowsExceptionTest() {
        assertThrows(Exception.class, this.testDummy::startGame);
    }

}
