import org.testng.Assert;
import org.testng.annotations.Test;


public class MethodTest {


    @Test
    public void testValue1() {
        String json = "[" +
                "{" +
                "\"id\": 12," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 13," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";

        Assert.assertEquals(Method.value(json), 900,"passed");
    }

    @Test
    public void testValue2() {
        String json = "[" +
                "{" +
                "\"id\": 12," +
                "\"sum\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 13," +
                "\"sum\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
        Assert.fail();
    }

    @Test
    public void testValue3() {
        String json = "[" +
                "{" +
                "\"id\": 12," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 13," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
    }
}