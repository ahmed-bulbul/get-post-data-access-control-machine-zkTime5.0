package db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class PostEventRecords {

    // api call post request to send eventRecordList to server
    public static void sendEventRecordList(List<String> eventRecordList) {

        String targetURL = "http://192.168.134.88:9001/hrms_api/test/create";
        String json = "";

        //add org and  operating unit to json
        json = "{\"organization\":\"majesto\",\"eventRecordList\":" + eventRecordList + ",\"ou\":\"majesto-IT\"}";
        System.out.println(json);

        // ConnectException  handling


        try {
            // Construct data
            String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
            data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");

            // Send data
            URL url = new URL(targetURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json);
            wr.flush();

            // dfd

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                // Process line...
                System.out.println(line);
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // check if the request is successful or not and print the response

    }
}
