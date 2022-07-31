package db;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class DbConnection {

    //ucanaccess db connection from here
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        getConnection();

        //send  record  every 10 minutes to server
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(6000);
                        sendEventRecordList(getEventRecordList());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();





    }

    public static void getConnection() {

        String user = "";
        String pass = "";
        String databaseURL = "jdbc:ucanaccess://C:/Program Files (x86)/ZKTime5.0/att2000.mdb";

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection(databaseURL, user, pass);
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed");
        }
    }

    public static List<String> getEventRecordList() {

        String user = "";
        String pass = "";
        String databaseURL = "jdbc:ucanaccess://C:/Program Files (x86)/ZKTime5.0/att2000.mdb";

        List<String> eventRecordList= new ArrayList<>();


        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection(databaseURL, user, pass);
            LocalDate date = LocalDate.now();
            LocalDate prevDate = date.minusDays(1);
            LocalDate prevDate2 = date.minusDays(2);

            //String  sql = "SELECT * FROM checkinout"; checkinout.checktime,userinfo.Badgenumber
            String sql = "SELECT * FROM checkinout,userinfo where userinfo.userid=checkinout.userid and DateValue([checktime])<='" + date + "'" + "and DateValue([checktime])>='" + prevDate2 + "'";
            String getAllCheckInOut = "SELECT * FROM checkinout";

            //write a sql to fetch all checkinout and userinfo where userId = checkinout.userId
            String sql2 = "SELECT * FROM checkinout,userinfo where userinfo.userid=checkinout.userid";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql2);

            HashMap<String, String> eventRecord = new HashMap<>();


            while (result.next()) {

                Time ariseTime = result.getTime("checktime");
                Date ariseDate = result.getDate("checktime");

                eventRecord.put("ariseTime", ariseTime.toString());
                eventRecord.put("ariseDate", ariseDate.toString());
                eventRecord.put("userId", result.getString("userid"));
                eventRecord.put("card", result.getString("Badgenumber"));

                eventRecordList.add(new Gson().toJson(eventRecord));

            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();

        }
        return eventRecordList;

    }

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
