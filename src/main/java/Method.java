import com.google.gson.*;
import java.util.HashSet;
import java.util.Set;

public class Method {


    public static int value(String json) throws IllegalArgumentException {
        int value = 0;
        int id;
        int val;
        Set set = new HashSet<Integer>();
        if (json.equals("")) {
            throw new IllegalArgumentException("Empty json");
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        String status;
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            if (jsonArray.size() < 1) {
                throw new IllegalArgumentException("array is empty");
            }
            for (int i = 0; i < jsonArray.size(); i++) {

                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();

                if (jsonObject.has("id")) {
                    id = Integer.parseInt(String.valueOf(jsonObject.get("id")));
                    if (id < 0) {
                        throw new IllegalArgumentException("id less than zero");
                    }
                } else {
                    throw new IllegalArgumentException("no id");
                }

                if (jsonObject.has("amount")) {
                    val = Integer.parseInt(String.valueOf(jsonObject.get("amount"))); // проверка на отрицательные значения имеется , на переполнение нет
                    if (val < 0) {
                        throw new IllegalArgumentException("Amount is less than zero");
                    }
                } else {
                    throw new IllegalArgumentException("no amount");
                }

                if (jsonObject.has("status")) {
                    status = String.valueOf(jsonObject.get("status")); // тут на статусы внутренняя проверка в свиче
                    //String status = String.valueOf(jsonObject.get("status")).replaceAll("\"", ""); изначально был такой вариант, сам себе баг бы придумал? решил кавычки оставить
                    //  System.out.println(status);
                } else {
                    throw new IllegalArgumentException("no status");
                }

                if (!set.contains(id)) {
                    set.add(id);
                    switch (status) {
                        case "\"payment\"":
                            value = Math.addExact(value,val);
                            break;
                        case "\"return\"":
                            value = Math.subtractExact(value,val);
                            break;
                        default:
                            throw new IllegalArgumentException("Wrong status");
                    }
                } else {
                    throw new IllegalArgumentException("double id");
                }
            }
        } else {
            throw new IllegalArgumentException("json is not an array");
        }
        if (value < 0) {
            throw new IllegalArgumentException("how could that happen");
        }
        return value;
    }
}
