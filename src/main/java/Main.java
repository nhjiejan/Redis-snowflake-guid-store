/**
 * Created by NHJIEJAN on 24/01/2017.
 */
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        JedisConnect jedisConnect = new JedisConnect();

        // test data from client as Arraylist
        ArrayList test = new ArrayList();
        test.add("test1");
        test.add("test2");
        test.add("test3");

        // REDIS_host, SNOWFLAKE_HOST, SNOWFLAKE_PORT, [list of fields]
        jedisConnect.getJedisGuid("localhost", "http://localhost", "8182", test);

        }
    }
