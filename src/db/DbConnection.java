package db;


import java.sql.*;


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
                    Thread.sleep(300000);
                    System.out.println("Thread is running");
                    //if connection refuse 3 times, break the loop
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
                System.out.println("Connected to ucanaccess db");
            }
        } catch (Exception e) {
            System.out.println("Connection Failed");
        }
    }





}
