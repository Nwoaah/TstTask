import com.google.gson.*;
import java.util.HashSet;
import java.util.Set;

public class Method {

    public static final String ID = "id";
    public static final String AMOUNT = "amount";
    public static final String STATUS = "status";
    public static final String EMPTY_JSON = "Empty json";
    public static final String ID_LESS_THAN_ZERO = "id less than zero";
    public static final String NO_ID = "no id";
    public static final String AMOUNT_IS_LESS_THAN_ZERO = "Amount is less than zero";
    public static final String NO_AMOUNT = "no amount";
    public static final String NO_STATUS = "no status";
    public static final String WRONG_STATUS = "Wrong status";
    public static final String PAYMENT = "\"payment\"";
    public static final String RETURN = "\"return\"";
    public static final String DOUBLE_ID = "double id";
    public static final String JSON_IS_NOT_AN_ARRAY = "json is not an array";
    public static final String HOW_COULD_THAT_HAPPEN = "how could that happen";



    public static int value(String json) throws IllegalArgumentException {
        int value = 0;
        int id;
        int val;
        Set set = new HashSet<Integer>();
        if (json.equals("")) {
            throw new IllegalArgumentException(EMPTY_JSON);
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        String status;
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {

                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

                if (jsonObject.has(ID)) {
                    id = Integer.parseInt(String.valueOf(jsonObject.get(ID)));
                    if (id < 0) {
                        throw new NegativeIdException(ID_LESS_THAN_ZERO);
                    }
                } else {
                    throw new IllegalArgumentException(NO_ID);
                }

                if (jsonObject.has(AMOUNT)) {
                    val = Integer.parseInt(String.valueOf(jsonObject.get(AMOUNT))); // проверка на отрицательные значения имеется , на переполнение нет
                    if (val < 0) {
                        throw new IllegalArgumentException(AMOUNT_IS_LESS_THAN_ZERO);
                    }
                } else {
                    throw new IllegalArgumentException(NO_AMOUNT);
                }

                if (jsonObject.has(STATUS)) {
                    status = String.valueOf(jsonObject.get(STATUS)); // тут на статусы внутренняя проверка в свиче
                    //String status = String.valueOf(jsonObject.get("status")).replaceAll("\"", ""); изначально был такой вариант, сам себе баг бы придумал? решил кавычки оставить
                    //  System.out.println(status);
                } else {
                    throw new IllegalArgumentException(NO_STATUS);
                }

                if (!set.contains(id)) {
                    set.add(id);
                    switch (status) {
                        case PAYMENT:
                            value = Math.addExact(value,val);
                            break;
                        case RETURN:
                            value = Math.subtractExact(value,val);
                            break;
                        default:
                            throw new IllegalArgumentException(WRONG_STATUS);
                    }
                } else {
                    throw new IllegalArgumentException(DOUBLE_ID);
                }
            }
        } else {
            throw new IllegalArgumentException(JSON_IS_NOT_AN_ARRAY);
        }
        if (value < 0) {
            throw new IllegalArgumentException(HOW_COULD_THAT_HAPPEN);
        }
        return value;
    }


}
class NegativeIdException extends RuntimeException {
    public NegativeIdException(String s) {
        super(s);
    }
}