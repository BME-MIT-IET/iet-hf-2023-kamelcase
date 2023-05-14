package softprojlab.model;

import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GameValidator {

    @BeforeAll
    static void setUp() {

    }

    @ParameterizedTest
    @MethodSource("getTestCase")
    void test(String testInput, String testOutput) {
        Game testDummy = new Game();
        
        String mockOutput = "";
        
        assertEquals(testOutput, mockOutput);
    }


}
