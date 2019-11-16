package de.karasuma.client;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;

import java.io.*;

public class Client {

    public static void main(String[] args) {
        
        String url = readUrlFromFile();

        while (true) {
            System.out.println(sendRequestToServer(url));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static String readUrlFromFile() {
        File file = new File("conf/url.json");

        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line = bufferedReader.readLine();
                StringBuilder builder = new StringBuilder();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                return jsonObject.getString("url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "http://localhost:8080";
    }

    private static String sendRequestToServer(String url) {
        try {
            HttpResponse<String> response = Unirest.get(url)
                    .asString();

            if (response.isSuccess()) {
                return response.getBody();
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "Could not connect to " + url;
    }

}
