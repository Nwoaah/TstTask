import com.google.gson.*;
import java.util.HashSet;
import java.util.Set;

public class Method {



    public static int value(String json) {
        int value = 0;
        Set set = new HashSet<Integer>();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);

        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                try {
                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                    int id = Integer.parseInt(String.valueOf(jsonObject.get("id"))); // Проверку на отрицательные id решил не делать
                    int val = Integer.parseInt(String.valueOf(jsonObject.get("amount"))); // проверка на отрицательные значения имеется , на переполнение нет
                    if (val < 0) {
                        throw new IllegalArgumentException("Amount is less than zero");
                    }
                    String status = String.valueOf(jsonObject.get("status")); // тут на статусы внутренняя проверка в свиче
                    //String status = String.valueOf(jsonObject.get("status")).replaceAll("\"", ""); изначально был такой вариант, сам себе баг бы придумал? решил кавычки оставить
                  //  System.out.println(status);

                    if (!set.contains(id)) {
                        set.add(id);
                        switch (status) {
                            case "\"payment\"":
                                value += val;
                                break;
                            case "\"return\"":
                                value -= val;
                                break;
                            default:
                                throw new IllegalArgumentException("Wrong status");
                        }
                    } else {
                        throw new IllegalArgumentException("double id");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            throw new IllegalArgumentException("json is not an array");
        }

        return value;
    }

}
