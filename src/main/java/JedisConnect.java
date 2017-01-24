/**
 * Created by NHJIEJAN on 24/01/2017.
 */
import redis.clients.jedis.Jedis;
import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JedisConnect {
    public static void main(String[] args) {
        //Connecting to Redis server on localhost
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        System.out.println("Connection to server sucessfully");
        //check whether server is running or not
        //System.out.println("Server is running: " + pool.);

        Jedis jedis = null;
        try {
            jedis = pool.getResource();

        String line;
        InputStream fis = null;
        try {
            fis = new FileInputStream("C:\\Users\\nhjiejan\\IdeaProjects\\redis-examples\\data\\sampleset.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        {
            try {
                while ((line = br.readLine()) != null) {
                    // turn row into list
                    List<String> lineSplit = new ArrayList<String>(Arrays.asList(line.split(",")));
                    //String guid = lineSplit.get(0);
                    String col1 = lineSplit.get(0);
                    String col2 = lineSplit.get(1);
                    String col3 = lineSplit.get(2);
                    String rowHash = DigestUtils.sha1Hex(col1 + "|" + col2 + "|" + col3);

                    System.out.println("pre submit check");
                    System.out.println("================");
                    System.out.println(col1 + " :: " + col2 + " :: " + col3 + " :: " + rowHash);
                    System.out.println("================");

                    System.out.println("check if key exists");
                    System.out.println("================");
                    System.out.println("Key Exists:: "+ jedis.exists(rowHash));
                    Boolean rowHashExist = jedis.exists(rowHash);

                    if (rowHashExist) {
                        System.out.println("get guid");
                        //jedis.get(rowHash);
                        System.out.println("Stored string in redis:: "+ jedis.get(rowHash));
                    } else {
                        // get guid form snowflaeke
                        Snowflakecurl snowflakecurl = new Snowflakecurl("http://localhost", "8182");
                        String guid = snowflakecurl.getGuid();
                        //System.out.println(test);
                        jedis.set(rowHash, guid);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        // close connection to pool
        pool.destroy();
    }
}