package com.learnadroid.myfirstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main4Activity extends AppCompatActivity {

    EditText regname, regemail, regpass;
    Button regsubmit;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        regname = (EditText) findViewById(R.id.rname);
        regemail = (EditText) findViewById(R.id.remail);
        regpass = (EditText) findViewById(R.id.rpass);
        regsubmit = (Button) findViewById(R.id.rsubmit);

        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(this);

        regsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doregister doreg = new Doregister();
                doreg.execute();
            }
        });
    }

        public class Doregister extends AsyncTask<String, String, String> {

            String namestr = regname.getText().toString();
            String emailstr = regemail.getText().toString();
            String passstr = regpass.getText().toString();

            String z = "";
            boolean isSuccess = false;

            @Override
            protected void onPreExecute() {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                MainActivity.user_id = emailstr;
                if (namestr.trim().equals("") || emailstr.trim().equals("") || passstr.trim().equals(""))
                    z = "Please enter all fields....";
                else {
                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {
                            z = "Please check your internet connection";
                        } else {
                            String query = "insert into user values('" + namestr + "','" + emailstr + "','" + passstr + "')";

                            Statement stmt = con.createStatement();
                            stmt.executeUpdate(query);

                            z = "Welcome "+namestr;
                            isSuccess = true;
                            MainActivity.user_name=namestr;
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        z = "Exceptions" + ex;
                    }
                }
                return z;
            }

            @Override
            protected void onPostExecute(String s) {

                Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
                if (isSuccess) {
                    Intent mIntent =new Intent(Main4Activity.this, HomeActivity.class);
                    mIntent.putExtra("name",regname.getText().toString());
                    startActivity(mIntent);
                }
                progressDialog.hide();
            }
        }
}
