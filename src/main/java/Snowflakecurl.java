
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Created by NHJIEJAN on 06/12/2016.
 */
public class Snowflakecurl {

    private String host;
    private String port;

    //= "http://localhost"
    //  = "8182";
    public Snowflakecurl(String host, String port) throws MalformedURLException {
        this.host = host;
        this.port = port;

    }

    public String getGuid() throws MalformedURLException {
        Integer worker_id = 1;
        URL url = new URL(host + ":" + port + "/worker/" + worker_id.toString());

        //URL url = new URL("http://localhost:8182/worker/1");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null; ) {
                //System.out.println(line);
                String guid = line.split(":")[1];
                return guid.substring(0, guid.length() - 1);
                //System.out.println((Long.parseLong(guid.substring(0,guid.length()-1))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

