import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MethodTest {

    @Test
    public void positive1() {  // позитивный тест на работоспособность 1
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

        Assert.assertEquals(Method.value(json), 900, "passed");

    }

    @Test
    public void positive2() {  // позитивный тест на работоспособность 2, увеличим количество и сделаем только сложение
        String json = "[" +
                "{" +
                "\"id\": 12," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 13," +
                "\"amount\": 150," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 14," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 15," +
                "\"amount\": 150," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 16," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 17," +
                "\"amount\": 150," +
                "\"status\": \"payment\"" +
                "}" +
                "]";

        Assert.assertEquals(Method.value(json), 3600, "passed");

    }

    @Test
    public void positive3() {  // позитивный тест на работоспособность 3, из теста 2 повычитаем до 0
        String json = "[" +
                "{" +
                "\"id\": 12," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 13," +
                "\"amount\": 150," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 14," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 15," +
                "\"amount\": 150," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 16," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 17," +
                "\"amount\": 150," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 18," +
                "\"amount\": 1050," +
                "\"status\": \"return\"" +
                "}," +
                "{" +
                "\"id\": 19," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}," +
                "{" +
                "\"id\": 20," +
                "\"amount\": 1050," +
                "\"status\": \"return\"" +
                "}," +
                "{" +
                "\"id\": 21," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}," +
                "{" +
                "\"id\": 22," +
                "\"amount\": 1050," +
                "\"status\": \"return\"" +
                "}," +
                "{" +
                "\"id\": 23," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";

        Assert.assertEquals(Method.value(json), 0, "passed");

    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "no amount")
    public void throwIfcontractBrokenAmount() throws IllegalArgumentException { // sum вместо amount
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

    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Empty json")
    public void throwIfEmpty() throws IllegalArgumentException { // пустая строка
        String json = "";
        Method.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class, JsonParseException.class})
    public void throwIfInvalid() throws IllegalArgumentException, JsonParseException { // не хватает закрывающей ] , отлаливаем инвалиды
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
                "}";
        Method.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "json is not an array")
    public void throwIfNotAnArray() throws IllegalArgumentException { // если json не массив
        String json = "{}";
        Method.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "array is empty")
    public void throwIfAnEmptyArray() throws IllegalArgumentException { // если json пустой массив
        String json = "[]";
        Method.value(json);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void throwIfNull() throws NullPointerException { // NPE
        String json = null;
        Method.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "double id")
    public void throwIfSameIds() throws IllegalArgumentException { // Дублирование ID
        String json = "[" +
                "{" +
                "\"id\": 12," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 12," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "id less than zero")
    public void throwIfIdBelowZero() throws IllegalArgumentException { // отрицательные ID
        String json = "[" +
                "{" +
                "\"id\": -12," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 12," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
    }

    @Test(expectedExceptions = {NumberFormatException.class})
    public void throwIfIdIsString() throws NumberFormatException { // если вместо int в id у нас строка(другой формат, который не кастится к инту)
        String json = "[" +
                "{" +
                "\"id\": dsa," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 12," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
    }

    @Test(expectedExceptions = {NumberFormatException.class})
    public void throwIfAmountisString() throws NumberFormatException { // если в amount формат который не кастится к инту
        String json = "[" +
                "{" +
                "\"id\": 10," +
                "\"amount\": 105dsadf0," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 12," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Wrong status")
    public void throwIfStatusIsNotString() throws IllegalArgumentException { // в силу реализации все не удовлетворяющие "payment" и "return" упадут сюда
        String json = "[" +
                "{" +
                "\"id\": 10," +
                "\"amount\": 1050," +
                "\"status\": \"123\"" +
                "}," +
                "{" +
                "\"id\": 12," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
    }

    @Test(expectedExceptions = {JsonSyntaxException.class})
    public void throwIfNoId() throws JsonSyntaxException { // просто нет id
        String json = "[" +
                "{" +
                "\"id\": ," +
                "\"amount\": 1050," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 12," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
    }

    @Test(expectedExceptions = {JsonSyntaxException.class})
    public void throwIfNoAmount() throws JsonSyntaxException { // просто нет amount
        String json = "[" +
                "{" +
                "\"id\": 10," +
                "\"amount\": ," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 12," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
    }

    @Test(expectedExceptions = {JsonSyntaxException.class})
    public void throwIfNoStatus() throws JsonSyntaxException { // нет status
        String json = "[" +
                "{" +
                "\"id\": 10," +
                "\"amount\": 1050," +
                "\"status\": " +
                "}," +
                "{" +
                "\"id\": 12," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";
        Method.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "how could that happen")
    //Проверка на сумму < 0 решил для рабочего теста выбрасывать исключения, вероятно можно было сделать просто непроходящий проверку тест.
    public void valueIsBelowZero() throws IllegalArgumentException {
        String json = "[" +
                "{" +
                "\"id\": 12," +
                "\"amount\": 1050," +
                "\"status\": \"return\"" +
                "}," +
                "{" +
                "\"id\": 13," +
                "\"amount\": 150," +
                "\"status\": \"return\"" +
                "}" +
                "]";

        Method.value(json);

    }


    @Test(expectedExceptions = {ArithmeticException.class})
    // тест на переполнение общей суммы, по реализации при переполнении инта даст ArithmeticException
    public void overflowValue() throws ArithmeticException {
        String json = "[" +
                "{" +
                "\"id\": 12," +
                "\"amount\": 2147483640," +
                "\"status\": \"payment\"" +
                "}," +
                "{" +
                "\"id\": 13," +
                "\"amount\": 214748340," +
                "\"status\": \"payment\"" +
                "}" +
                "]";

        Method.value(json);

    }

}