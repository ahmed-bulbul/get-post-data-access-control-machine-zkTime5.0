package db;

import java.io.*;
import java.net.*;
import java.util.List;

public class PostEventRecords {

    // api call post request to send eventRecordList to server
    public static void sendEventRecordList(List<String> eventRecordList) {


        String targetURL = "http://192.168.134.88:9001/hrms_api/test/create";
        String json = "";

        //add org and  operating unit to json
        json = "{\"organization\":\"majesto\",\"eventRecordList\":" + eventRecordList + ",\"ou\":\"majesto-IT\"}";
        System.out.println("Data to be sent: " + targetURL);

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
            //java.net.ConnectException: Connection refused: connect handle this exception
            OutputStreamWriter wr =null;
            try {
                 wr= new OutputStreamWriter(conn.getOutputStream());
            }catch ( Exception e) {
                System.out.println("Connection refused! Please check the server is running");
            }

            if(wr!=null){
                wr.write(json);
                wr.flush();
            }else{
                System.out.println("Connection refused! Try again later");
            }



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
        } catch (ConnectException e) {
            System.out.printf("Exception: %s%n", e);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // check if the request is successful or not and print the response

    }
}
