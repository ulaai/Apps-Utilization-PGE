package com.example.uli2.userprofilemgmt;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by uli on 27/07/17.
 */

public class Singleton {
    private static  Singleton mInstance = null;

    private  String mString;
    ConnectionClass mConnection;

    Connection con = null;
    Statement stmt = null;
    String db = "jdbc:jtds:sqlserver://ptmpgesqlsvrdev.pertamina.com:1433/UserProfileManagement";
    String uname = "sa";
    String pass = "sqlserver2012PGE";

    public List<List<String>> results = new ArrayList<List<String>>();
    public ArrayList<String> input = new ArrayList<>();

    public List<List<String>> MonthlyTotalUtilization = new ArrayList<>();
    public List<List<String>> MonthlyAppUtilization = new ArrayList<>();
    public List<List<String>> MonthlyTopApplication = new ArrayList<>();
    public List<List<String>> MonthlyTopUser = new ArrayList<>();



    private Singleton() {
        mString = "Hello";
        mConnection = new ConnectionClass();

    }

    public static Singleton getInstance() {
        if(mInstance == null) {
            synchronized (Singleton.class) {
                if(mInstance == null) {
                    mInstance = new Singleton();
                }
            }
        }
        return mInstance;
    }

    public void newSingleton() {
        mInstance = new Singleton();
        mConnection = new ConnectionClass();

    }

    private class ConnectionClass extends AsyncTask<String, String, String> {
        public AsyncResponse delegate = null;
        String z="";
        int y = 0;
        @Override
        protected String doInBackground(String... query) {
            try{
                String q = query[0];
                List<String> attributeNames = new ArrayList<String>();
                for(int i = 1; i < query.length; i++) {
                    attributeNames.add(query[i]);
                }
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                con = DriverManager.getConnection(db, uname, pass);
                if(con == null) {
                    z = "Cannot connect. Check your internet access!";
                }
                else {
                    results = new ArrayList<List<String>>();
                    for(int j = 0; j < attributeNames.size(); j++) {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(q);
                        input = new ArrayList<>();

                        while (rs.next()) {
                            String value = rs.getString(attributeNames.get(j));

                            input.add(""+value);
                        }
                        results.add(input);
                        if(j == attributeNames.size()-1) {
                            rs.close();
                            con.close();
                        }
                    }

                }
            }
            catch (Exception e) {
                e.getMessage();
            }
            return z;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            delegate.processFinish(result);
        }
    }

    public void setDelegate(AsyncResponse output) {
        mConnection.delegate = output;
    }
    public String getString() {
        return this.mString;
    }

    public void setString(String value) {
        mString = value;
    }

    public void setMonthlyTotalUtilization() {
        mConnection.execute("exec dbo.stp_GetMonthlyTotalUtilization '2017-06-01'", "Label",
                "Value");
    }

    public void getApplicationActivity() {
        mConnection.execute("exec dbo.stp_GetListMonthlyApplicationUtilization '2017-06-01'",
                "Label", "Value", "Value2", "Value3");

    }

    public void getTopMonthlyApplication() {
        mConnection.execute("exec dbo.stp_GetListTopMonthlyApplicationAccess '2017-06-01'", "Label",
                "Value");
    }

    public void getTopMonthlyUser() {
        mConnection.execute("exec dbo.stp_GetListTopMonthlyUserAccess '2017-06-01'", "Label",
                "Value");
    }

}
