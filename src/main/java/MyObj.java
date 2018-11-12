import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MyObj {
    public static final String PAYMENT = "payment";
    public static final String RETURN = "return";
    public static final String WRONG_STATUS = "Wrong status";
    public static final String ID_LESS_THAN_ZERO = "id less than zero";
    public static final String HOW_COULD_THAT_HAPPEN = "how could that happen";
    public static final String DOUBLE_ID = "double id";
    Integer id;
    Integer amount;
    String status;


    MyObj(Integer id, Integer amount, String status) {
        this.id = id;
        this.amount = amount;
        this.status = status;
    }


    public static void main(String[] args) {

        String tst = MyObj.generator(1000000, 10, 10);
        long beforeFirst = System.nanoTime();
        System.out.println(MyObj.value(tst));
        long afterFirst = System.nanoTime();
        long beforeSecond = System.nanoTime();
        System.out.println(Method.value(tst));
        long afterSecond = System.nanoTime();

        long timeFirst = afterFirst - beforeFirst;
        long timeSecond = afterSecond - beforeSecond;
        System.out.println("First " + timeFirst);
        System.out.println("Secon " + timeSecond);

    }

    public static String generator(int numberOfTransactions, int numberOfReturns, int maxAmount) {
        List<MyObj> myObjlist = new LinkedList<>();
        for (int i = 0; i < numberOfReturns; i++) {
            myObjlist.add(new MyObj(i, ThreadLocalRandom.current().nextInt(maxAmount), "return"));
        }
        for (int i = numberOfReturns; i < numberOfTransactions; i++) {
            myObjlist.add(new MyObj(i, ThreadLocalRandom.current().nextInt(maxAmount), "payment"));
        }

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myObjlist);

        return json;
    }

    public static int value(String json) {
        int value = 0;
        Set set = new HashSet<Integer>();
        Type listType = new TypeToken<ArrayList<MyObj>>() {
        }.getType();
        List<MyObj> myobjlist = new Gson().fromJson(json, listType);
        for (MyObj myobj : myobjlist) {
            if (myobj.id < 0) {
                throw new NegativeIdException(ID_LESS_THAN_ZERO);
            }

            if (!set.contains(myobj.id)) {
                set.add(myobj.id);
                switch (myobj.status) {
                    case PAYMENT:
                        value = Math.addExact(value, myobj.amount);
                        break;
                    case RETURN:
                        value = Math.subtractExact(value, myobj.amount);
                        break;
                    default:
                        throw new IllegalArgumentException(WRONG_STATUS);
                }
            } else {
                throw new IllegalArgumentException(DOUBLE_ID);
            }
        }
        if (value < 0) {
            throw new IllegalArgumentException(HOW_COULD_THAT_HAPPEN);
        }
        return value;
    }


}
