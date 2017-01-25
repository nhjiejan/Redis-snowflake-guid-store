/**
 * Created by NHJIEJAN on 06/12/2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Snowflakecurl {

    private String host;
    private String port;

    public Snowflakecurl(String host, String port) throws MalformedURLException {
        this.host = host;
        this.port = port;
    }

    public String getGuid() throws MalformedURLException {
        Integer worker_id = 1;
        URL url = new URL(host + ":" + port + "/worker/" + worker_id.toString());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null; ) {
                String guid = line.split(":")[1];
                return guid.substring(0, guid.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

