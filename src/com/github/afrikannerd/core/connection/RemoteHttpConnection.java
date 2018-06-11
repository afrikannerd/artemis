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

        for (String singlePayload : payload) {
            String[] payloadSegment = singlePayload.split(";");
            for (String singleSegment : payloadSegment) {
                params.put(singleSegment.split(":")[0], singleSegment.split(":")[1]);
            }
        }
    }

    public String post() throws MalformedURLException, IOException {
        if (!getParams().equalsIgnoreCase("") && !getParams().isEmpty()) {
            this.buildURL();
            if (data.startsWith("&")) {
                data = data.replaceFirst("&", "");
            }
            url = new URL(path);
            StringBuffer incoming;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(postConnection(data, url)))) {
                incoming = new StringBuffer();
                link.getResponseCode();
                String line;
                while ((line = reader.readLine()) != null) {
                    incoming.append(line.concat("\n"));
                }
            }
            return incoming.toString();
        } else {
            return null;
        }

    }

    private void buildURL(){
        String key = "";
        String val = "";
        for (Map.Entry<String, String> param : params.entrySet()) {
            key = param.getKey();
            val = param.getValue();
            switch(method){
                case "GET":
                    this.data = this.data.concat(String.format("%s=%s&", key, val));
                    break;
                default:
                    this.data = this.data.concat(String.format("&%s=%s", key, val));
                    break;
            }
        }
    }
    public String get() throws MalformedURLException, IOException {
        method = "GET";
        if (!path.endsWith("/")) {
            path = path.concat("/?");
        } else {
            path = path.concat("?");
        }
        this.buildURL();
        data = data.substring(0, data.length() - 1);
        url = new URL(path.concat(data));
        link = (HttpURLConnection) url.openConnection();
        link.setRequestMethod(method);
        int responseCode = link.getResponseCode();
        StringBuffer response = new StringBuffer();
        String inputLine;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(link.getInputStream()))) {
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine.concat("\n"));
                }
            }
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
    
    public String getMethod() {

        return this.method;
    }

    public void setUserAgent(String agent) {
        this.userAgent = agent;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public static void main(String... args) throws InterruptedException {
       
        try {
            String [] payload =
            {
                "username:amolosteve;",
                "password:banter",
                
            };
            String url = "https://calebamolo10.000webhostapp.com/test/login.php";
            RemoteHttpConnection obj = new RemoteHttpConnection(payload,url);
            //obj.setMethod("GET");
            
            
            
            System.out.println(obj.get());
            System.out.println(obj.method);
            //System.out.println(obj.link.toString().split(":", 2)[1]);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
