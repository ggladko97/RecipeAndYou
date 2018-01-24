package pl.rzeszow.wsiz.zhekabandit97.recipe4u;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void kozyrTest() {
//        Integer randomFlat = new Random().nextInt(3*9*4);


        Map<Integer, Integer> answer1 = ExampleUnitTest.calculateFlat(3);
        Map<Integer, Integer> answer2 = ExampleUnitTest.calculateFlat(54);
        Map<Integer, Integer> answer3 = ExampleUnitTest.calculateFlat(88);
        Map<Integer, Integer> answer4 = ExampleUnitTest.calculateFlat(100);

        System.out.println("Result 1: \n" + answer1 + "\n");
        System.out.println("Result 2: \n" + answer2 + "\n");
        System.out.println("Result 3: \n" + answer3 + "\n");
        System.out.println("Result 4: \n" + answer4 + "\n");


        assertNotNull(answer4);
    }

    public static Map<Integer, Integer> calculateFlat(int flat) {
        for (int i = 0; i < 98; i += 27) {

            if (flat < i) {
                for (int j = i; j < i+27; j+=3) {
                    if (flat == j) {
                        //key = padik, value = stage
                        Map<Integer, Integer> result = new HashMap<>();
                        result.put(i, j);
                        return result;
                    }
                }
            }
        }

        //not found (out of the bound)
        return null;
    }
}