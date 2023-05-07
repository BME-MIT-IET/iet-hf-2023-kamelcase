package softprojlab.model.character;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class VirologistValidator {
    
    @BeforeAll
    static void setUp() {

    }

    @ParameterizedTest
    @MethodSource("getTestCase")
    void test(String testInput, String testOutput) {
        Virologist testDummy = new Virologist(null);
        
        String mockOutput = "";
        
        assertEquals(testOutput, mockOutput);
    }


}