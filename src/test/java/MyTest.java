import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTest {

    @Test
    public void testAddition() {
        int result = 1 + 2;
        assertEquals(3, result);
    }
}