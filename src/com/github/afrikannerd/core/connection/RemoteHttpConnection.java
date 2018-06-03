/*
 * Copyright (C) 2018 afrikannerd <https://github.com/afrikannerd>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.afrikannerd.core.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author afrikannerd <https://github.com/afrikannerd>
 * @version "0.1"
 */
public class RemoteHttpConnection {

    private volatile String path = "";
    private volatile String method = "POST";
    private String type = "application/x-www-form-urlencoded";
    private volatile String data = "";
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0";
    private volatile URL url;
    private HttpURLConnection link;
    private static Map<String, String> params;

    public RemoteHttpConnection(String[] payload, String path) {
        params = new HashMap<>();
        this.path = path;

        for (int x = 0; x < payload.length; x++) {
            String[] payloadSegment = payload[x].split(";");
            for (int k = 0; k < payloadSegment.length; k++) {
                params.put(payloadSegment[k].split(":")[0], payloadSegment[k].split(":")[1]);
            }
        }
    }

    public String post() throws MalformedURLException, IOException {
        if (!getParams().equalsIgnoreCase("") && !getParams().isEmpty()) {
            String key = "";
            String val = "";
            for (Map.Entry<String, String> param : params.entrySet()) {
                key = param.getKey();
                val = param.getValue();
                this.data = this.data.concat(String.format("&%s=%s", key, val));
            }
            if (data.startsWith("&")) {
                data = data.replaceFirst("&", "");
            }
            url = new URL(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(postConnection(data, url)));
            StringBuffer incoming = new StringBuffer();
            link.getResponseCode();
            String line;
            while ((line = reader.readLine()) != null) {
                incoming.append(line.concat("\n"));
            }
            reader.close();
            return incoming.toString();
        } else {
            return null;
        }

    }

    public String get() throws MalformedURLException, IOException {
        if (!path.endsWith("/")) {
            path = path.concat("/?");
        } else {
            path = path.concat("?");
        }
        String key = "";
        String val = "";
        for (Map.Entry<String, String> param : params.entrySet()) {
            key = param.getKey();
            val = param.getValue();
            data = data.concat(String.format("%s=%s&", key, val));
        }
        data = data.substring(0, data.length() - 1);
        url = new URL(path.concat(data));
        link = (HttpURLConnection) url.openConnection();
        link.setRequestMethod("GET");
        int responseCode = link.getResponseCode();
        StringBuffer response = new StringBuffer();
        String inputLine;
        if (responseCode == link.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(link.getInputStream()));
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine.concat("\n"));
            }
            reader.close();
            return response.toString();
        } else {
            return null;
        }

    }

    private static String getParams() {
        return params.toString();
    }

    public String getQueryString() {

        return this.data;
    }

    public void setUserAgent(String agent) {
        this.userAgent = agent;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public static void main(String... args) {
       
        try {
            String [] payload =
            {
                "username:amolosteve;",
                "password:banter",
                
            };
            String url = "https://calebamolo10.000webhostapp.com/test/login.php";
            System.out.println(new RemoteHttpConnection(payload,url).post());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private InputStream postConnection(String data, URL url) throws IOException {
        byte[] outbound = data.getBytes();
        link = (HttpURLConnection) url.openConnection();
        link.setRequestMethod("POST");
        link.addRequestProperty("User-Agent", this.userAgent);
        link.setDoOutput(true);
        link.addRequestProperty("Content-Type", type);
        link.connect();
        OutputStream os = link.getOutputStream();
        os.write(outbound);
        return link.getInputStream();
    }

}
