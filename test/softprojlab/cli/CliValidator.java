package softprojlab.cli;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CliValidator {

    @BeforeAll
    static void setUp() {

    }

    @ParameterizedTest
    @MethodSource("getTestCase")
    void test(String testInput, String testOutput) {
        CLI testDummy = new CLI();
        
        String mockOutput = "";
        
        assertEquals(testOutput, mockOutput);
    }


}
