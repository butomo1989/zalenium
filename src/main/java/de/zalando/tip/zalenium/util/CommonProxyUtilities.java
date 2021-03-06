package de.zalando.tip.zalenium.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonProxyUtilities {

    private static final Logger LOG = Logger.getLogger(CommonProxyUtilities.class.getName());

    /*
        Reading a JSON with DockerSelenium capabilities from a given URL
        http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
     */
    public JsonElement readJSONFromUrl(String url) {
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            is.close();
            return new JsonParser().parse(jsonText);
        } catch (Exception e) {
            LOG.log(Level.FINE, e.toString(), e);
        }
        return null;
    }

    public JsonElement readJSONFromFile(String fileName) {
        try(FileReader fr = new FileReader(new File(currentLocalPath(), fileName))) {
            BufferedReader rd = new BufferedReader(fr);
            String jsonText = readAll(rd);
            return new JsonParser().parse(jsonText);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
        return null;
    }

    public String currentLocalPath() {
        try {
            File jarLocation = new File(CommonProxyUtilities.class.getProtectionDomain().getCodeSource()
                    .getLocation().toURI().getPath());
            return jarLocation.getParent();
        } catch (URISyntaxException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
        return null;
    }

    private static String readAll(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
