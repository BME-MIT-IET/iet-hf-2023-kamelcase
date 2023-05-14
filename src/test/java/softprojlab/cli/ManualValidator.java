package softprojlab.cli;

// JUnit static imports

import static org.junit.jupiter.api.Assertions.*;

// Java imports

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

// JUnit imports

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ManualValidator {
    
    static ArrayList<String> input;
    static ArrayList<String> output;
    
    static Path directory;

    @BeforeAll
    static void setUp() {
        ManualValidator.input = new ArrayList<String>();
        ManualValidator.output = new ArrayList<String>();
        
        ManualValidator.directory = Path.of("./testfiles/");
        
        ManualValidator.append("01_StealFromParalyzedThenKillWithAxe.txt");
    }
    
    static void append(String input) {
        
        ManualValidator.input.add( ("in/" + input) );
        ManualValidator.output.add( ("out/" + input) );
    }
    
    static Stream<Arguments> getTestCase() {
        ArrayList<Arguments> result = new ArrayList<Arguments>();
        
        for (int i = 0; i < ManualValidator.input.size(); ++i) {
            String localInputPath = ManualValidator.directory.resolve(ManualValidator.input.get(i)).toAbsolutePath().toString();
            String localOutputPath = ManualValidator.directory.resolve(ManualValidator.output.get(i)).toAbsolutePath().toString();
            
            String localOutput = "";
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader(localOutputPath));
                String line;
                while ((line = reader.readLine()) != null) {
                    localOutput += line + '\n';
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("An error occurred during reading output file.");
                e.printStackTrace();
            }
            
            result.add(Arguments.of(localInputPath, localOutput));
        }
        
        return Stream.of(result.toArray(new Arguments[result.size()]));
    }

    @ParameterizedTest
    @MethodSource("getTestCase")
    void test(String testInput, String testOutput) {
        CLI testDummy = new CLI();
        testDummy.doExit = false;
        
        // System.out.println("\n---------\nrunning test case: " + testInput + '\n');
        
        testDummy.loadHistory(testInput);
        
        String result = testDummy.handleShowCommandLocal("show game");
        
        assertEquals(testOutput, result);
    }


}
