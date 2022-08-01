package db;


import java.sql.*;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;


public class DbConnection {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        getConnection();
        t.start();

    }

    // thread function
    public static Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    // 5 minutes every time
                    Thread.sleep(30000);
                    String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss a"));
                    System.out.println("Thread is running... Current time is: " + formattedDate);

                    PostEventRecords.sendEventRecordList(GetEventRecords.getEventRecordList());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });



    public static void getConnection() {

        String user = "";
        String pass = "";
        String databaseURL = "jdbc:ucanaccess://C:/Program Files (x86)/ZKTime5.0/att2000.mdb";
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection connection = DriverManager.getConnection(databaseURL, user, pass);
            if(connection != null) {
                System.out.println("Successfully connected to the access database");
            }
        } catch (Exception e) {
            System.out.println("Connection Failed");
        }
    }





}
