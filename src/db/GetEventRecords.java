package db;

import com.google.gson.Gson;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetEventRecords {

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
}
