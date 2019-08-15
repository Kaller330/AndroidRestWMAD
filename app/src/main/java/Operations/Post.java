package Operations;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import DTO.userDTO;

public class Post {

    InputStream inputStream;
    private String ResponseData;

    public Post(URL url, String jsonStr, StringBuilder builder) throws Exception{

        System.out.println("[JSON SENDER!]: " + jsonStr);
        builder.append(jsonStr + "\n");

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setRequestMethod("POST");

        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");


        out.write(jsonStr);
        out.flush();
        out.close();

        inputStream = new BufferedInputStream(con.getInputStream());
        //ResponseData = convertStreamToString(inputStream);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        builder.setLength(0);


        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append((line + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ResponseData = builder.toString();

        System.out.println("[I'm working]" + builder.toString());

        builder.append("Response code: " + con.getResponseCode() + "\n\n");
        con.disconnect();
    }

    public String getResponseData() {
        return ResponseData;
    }
}
