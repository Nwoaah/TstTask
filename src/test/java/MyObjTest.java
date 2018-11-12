import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyObjTest {


    @Test
    public void positive() {
        String tst = "[" +
                "{\"id\":0,\"amount\":1,\"status\":\"return\"}," +
                "{\"id\":1,\"amount\":9,\"status\":\"return\"}," +
                "{\"id\":2,\"amount\":2,\"status\":\"return\"}," +
                "{\"id\":3,\"amount\":1,\"status\":\"return\"}," +
                "{\"id\":4,\"amount\":0,\"status\":\"return\"}," +
                "{\"id\":5,\"amount\":9,\"status\":\"payment\"}," +
                "{\"id\":6,\"amount\":7,\"status\":\"payment\"}," +
                "{\"id\":7,\"amount\":8,\"status\":\"payment\"}," +
                "{\"id\":8,\"amount\":1,\"status\":\"payment\"}," +
                "{\"id\":9,\"amount\":9,\"status\":\"payment\"}]\n";

        Assert.assertEquals(MyObj.value(tst), 21, "passed");
    }

    @Test
    public void positive1() {
        // позитивный тест на работоспособность 1
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

        Assert.assertEquals(MyObj.value(json), 900, "passed");

    }

    @Test
    public void positive2() {
        // позитивный тест на работоспособность 2, увеличим количество и сделаем только сложение
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

        Assert.assertEquals(MyObj.value(json), 3600, "passed");

    }

    @Test
    public void positive3() {
        // позитивный тест на работоспособность 3, из теста 2 повычитаем до 0
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

        Assert.assertEquals(MyObj.value(json), 0, "passed");

    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void throwIfcontractBrokenAmount() throws NullPointerException {
        // sum вместо amount
        // падает с NPE, в Method падала c IllegalArgumentException
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

        MyObj.value(json);

    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void throwIfEmpty() throws NullPointerException {
        // пустая строка
        // тут падает с NPE, в Method падает с illegalargumentException
        String json = "";
        MyObj.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class, JsonParseException.class})
    public void throwIfInvalid() throws IllegalArgumentException, JsonParseException {
        // не хватает закрывающей ] , отлаливаем инвалиды, ниже закомменчены 3 теста, по сути просто невалидный json, пришел к выводу, что можно обойтись одним этим, но не уверен, поэтому не стер
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
                "}";
        MyObj.value(json);
    }

    //    @Test(expectedExceptions = {JsonSyntaxException.class})
//    public void throwIfNoId() throws JsonSyntaxException { // просто нет id
//        String json = "[" +
//                "{" +
//                "\"id\": ," +
//                "\"amount\": 1050," +
//                "\"status\": \"payment\"" +
//                "}," +
//                "{" +
//                "\"id\": 12," +
//                "\"amount\": 150," +
//                "\"status\": \"return\"" +
//                "}" +
//                "]";
//        MyObj.value(json);
//    }
//
//    @Test(expectedExceptions = {JsonSyntaxException.class})
//    public void throwIfNoAmount() throws JsonSyntaxException { // просто нет amount
//        String json = "[" +
//                "{" +
//                "\"id\": 10," +
//                "\"amount\": ," +
//                "\"status\": \"payment\"" +
//                "}," +
//                "{" +
//                "\"id\": 12," +
//                "\"amount\": 150," +
//                "\"status\": \"return\"" +
//                "}" +
//                "]";
//        MyObj.value(json);
//    }

//    @Test(expectedExceptions = {JsonSyntaxException.class})
//    public void throwIfNoStatus() throws JsonSyntaxException { // нет status
//        String json = "[" +
//                "{" +
//                "\"id\": 10," +
//                "\"amount\": 1050," +
//                "\"status\": " +
//                "}," +
//                "{" +
//                "\"id\": 12," +
//                "\"amount\": 150," +
//                "\"status\": \"return\"" +
//                "}" +
//                "]";
//        MyObj.value(json);
//    }

    @Test(expectedExceptions = {JsonSyntaxException.class})
    public void throwIfNotAnArray() throws JsonSyntaxException {
        // если json не массив
        // в method падает с IllegalArgumentException
        String json = "{}";
        MyObj.value(json);
    }

    // пришел к выводу, что тест лишний, так как с точки зрения бизнеса транзакций может и не быть, однако не указано, в каком виде тогда будет json, поэтому предположил, что он будет просто пустым массивом,тогда value = 0
//    @Test(expectedExceptions = {IllegalArgumentException.class},
//            expectedExceptionsMessageRegExp = "array is empty")
//    public void throwIfAnEmptyArray() throws IllegalArgumentException {
//        // если json пустой массив
//        String json = "[]";
//        MyObj.value(json);
//    }

    @Test(expectedExceptions = NullPointerException.class)
    public void throwIfNull() throws NullPointerException {
        // NPE
        String json = null;
        MyObj.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "double id")
    public void throwIfSameIds() throws IllegalArgumentException {
        // Дублирование ID
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
        MyObj.value(json);
    }

    @Test(expectedExceptions = {NegativeIdException.class},
            expectedExceptionsMessageRegExp = "id less than zero")
    public void throwIfIdBelowZero() throws NegativeIdException {
        // отрицательные ID
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
        MyObj.value(json);
    }

    @Test(expectedExceptions = {JsonSyntaxException.class})
    public void throwIfIdIsString() throws JsonSyntaxException {
        // если вместо int в id у нас строка(другой формат, который не кастится к инту)
        // в Method падает с NumberFormatException, тут ошибка на этапе десереализации
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
        MyObj.value(json);
    }

    @Test(expectedExceptions = {JsonParseException.class})
    public void throwIfAmountisString() throws JsonParseException {
        // если в amount формат который не кастится к инту
        // этот тест у Method падает с NumberFormatException, но тут уже при десериализации выдает другую ошибку
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
        MyObj.value(json);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Wrong status")
    public void throwIfStatusIsNotString() throws IllegalArgumentException {
        // в силу реализации все не удовлетворяющие "payment" и "return" упадут сюда
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
        MyObj.value(json);
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

        MyObj.value(json);

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

        MyObj.value(json);

    }

    @Test(expectedExceptions = {NumberFormatException.class})
    // если в качестве значения переданы id : null или amount : null, в силу реализации упадет в numberformatexception, если status : null, то кинет illegalargumentexception, решил оставить один тест.
    public void throwIfNullOnID() throws NumberFormatException {
        String json = "[{\"id\":1,\"amount\":" + null + ",\"status\":\"payment\"}," +
                "{\"id\":2,\"amount\":10,\"status\":\"return\"}," +
                "{\"id\":3,\"amount\":10,\"status\":\"payment\"}," +
                "{\"id\":4,\"amount\":10,\"status\":\"payment\"}]";

        Method.value(json);

    }

}
