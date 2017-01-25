/**
 * Created by NHJIEJAN on 24/01/2017.
 */
import redis.clients.jedis.Jedis;
import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.ArrayList;



public class JedisConnect {

    private String redisHost;
    private String snowflakeHost;
    private String snowflakePort;
    private String redisGuid;


    public String getJedisGuid(String redisHost, String snowflakeHost, String snowflakePort, ArrayList line) {

        this.redisHost = redisHost;
        this.snowflakeHost = snowflakeHost;
        this.snowflakePort = snowflakePort;

        //Connecting to Redis server on localhost
        JedisPool pool = new JedisPool(new JedisPoolConfig(), redisHost);
        System.out.println("[INFO] Connection to server sucessfully");
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            {
                try {

                    String preHashLine = String.join("|", line);
                    System.out.println("[INFO] preHashline :: " + preHashLine);

                    // create hash
                    String rowHash = DigestUtils.sha1Hex(preHashLine);

                    System.out.println("[INFO] pre submit check :: "+ line + " :: " + rowHash);
                    System.out.println("[INFO] Key Exists in Redis :: " + jedis.exists(rowHash));

                    // boolean to check if hash key already exists in redis
                    Boolean rowHashExist = jedis.exists(rowHash);
                    if (rowHashExist) {
                        System.out.println("[INFO] getting guid from redis");
                        System.out.println("[INFO] Stored string in redis:: " + jedis.get(rowHash));
                        String redisGuid = jedis.get(rowHash);
                    } else {
                        // get guid form snowflake
                        System.out.println("[INFO] getting guid from snowflake");
                        Snowflakecurl snowflakecurl = new Snowflakecurl(snowflakeHost, snowflakePort);
                        String redisGuid = snowflakecurl.getGuid();
                        jedis.set(rowHash, redisGuid);

                    }

                    return redisGuid;
                    // }
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

        return redisGuid;
    }
}
