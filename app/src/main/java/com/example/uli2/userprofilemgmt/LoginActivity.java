package com.example.uli2.userprofilemgmt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.uli2.userprofilemgmt.Persistence.AppDatabase;
import com.example.uli2.userprofilemgmt.Persistence.MonthlyPie;
import com.example.uli2.userprofilemgmt.Persistence.Users;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.AsyncResponse;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements AsyncResponse {
    Button loginBtn;
    AppDatabase database;
    int userscount;
    ProgressBar progressBar;
    EditText textUname;
    EditText textPass;
    String entered_username, entered_password;
    String username;
    Users user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        loginBtn = (Button) findViewById(R.id.loginBtn);
        textUname = (EditText) findViewById(R.id.textUname);
//        textPass = (EditText) findViewById(R.id.textPass);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_username = textUname.getText().toString();
//                entered_password = textPass.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                database = AppDatabase.getDatabase(getApplicationContext());
                userscount = database.usersModel().getCount();
                if(userscount <= 0 && !Objects.equals(entered_username, "")) {
                    Singleton.getInstance().setDelegate(LoginActivity.this);
                    Singleton.getInstance().setCurrentUser();
                } else {
                    String queryName = '%'+ entered_username + '%';
                    List<Users> currentUsers = database.usersModel().getUsers(queryName);

                    if(currentUsers.size() == 1) {
                        Toast status = Toast.makeText(LoginActivity.this, "Login successful", Toast
                                .LENGTH_SHORT);
                        status.show();

                        //set as current user
                        long currentUsersID = currentUsers.get(0).id;
                        database.usersModel().setLoggedInUser(currentUsersID);
                        Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
                        startActivity(intent);
                    } else {
                        if(entered_username != null) {
                            Toast status = Toast.makeText(LoginActivity.this, "Login failed, " +
                                    "please try " +
                                    "again", Toast
                                    .LENGTH_LONG);
                            status.show();

                        }
                    }
                }

            }
        });



    }

    @Override
    public void processFinish(String output) {
        progressBar.setVisibility(View.VISIBLE);
        List<List<String>> PagingUsers = Singleton.getInstance().hashMap.get("PU");

        int numSize = PagingUsers.size();
        int objCount = PagingUsers.get(0).size();
        for(int i = 0; i < objCount; i++) {
            Users build = Users.builder()
                    .setId(i)
                    .setuserName(PagingUsers.get(0).get(i))
                    .setuserDisplayName(PagingUsers.get(1).get(i))
                    .setloggedIn(false)
                    .build();
            database.usersModel().addUsers(build);
        }

        List<Users> currentUsers = database.usersModel().getUsers(entered_username);

        if(currentUsers.size() > 0 && entered_username != null) {
            user = currentUsers.get(0);
            user.loggedIn = true;
            Toast status = Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT);

            final Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500); // As I am using LENGTH_LONG in Toast
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            status.show();
            thread.start();

        } else {
            if(entered_username != null) {
                Toast status = Toast.makeText(this, "Login failed, please try again", Toast
                        .LENGTH_LONG);
                status.show();
            }
        }

    }

    @Override
    public Context getDelegateContext() {
        return this;
    }

}
