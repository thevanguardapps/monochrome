package com.vanguard.monochrome.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vanguard.monochrome.app.utils.Utils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class
login extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener {

    EditText usernameFieldInLogIn,passwordFieldInLogIn;
    Button logInbtn;
    RelativeLayout rlloginActivityLayout;
    ImageView logoInLogIn;
    TextView forgotPassword;


    //Toast Method
    public void alertDisplayer(String title,String message){
        Toast.makeText(getApplicationContext(),title + " " + message,Toast.LENGTH_LONG).show();
    }

    //Implements of View.onKeylistener
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
            logIn(view);
        }
        return false;
    }


    //Implemments of View.onClicklistener
    @Override
    public void onClick(View view) {

        /*
        //Hiding KeyBoard
        if(view.getId()==R.id.logoinLogin || view.getId()==R.id.loginActivityRlayout){
            InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        else */

        //Goto ForgotPassword Activity
        if(view.getId()==R.id.forgotPassword){
            Intent i=new Intent(getApplicationContext(), forgotpassword.class);
            startActivity(i);
        }

        //Cursor Visibility on click on passwordFieldInLogIn
        else if(view.getId()==R.id.passswordLayout){
            passwordFieldInLogIn.setCursorVisible(true);
        }
        //Cursor Visibility on click on usernameFieldInLogIn
        else if(view.getId()==R.id.userLayout){
            usernameFieldInLogIn.setCursorVisible(true);
        }
    }



    //Login Function
    public void logIn(View v){

            final String username=usernameFieldInLogIn.getText().toString();
            final String password=passwordFieldInLogIn.getText().toString();

        if (username.length() == 0 || password.length() == 0)
        {
            Utils.showDialog(this, R.string.err_fields_empty);
        }
        else {
            final ProgressDialog dia=ProgressDialog.show(login.this,null,getString(R.string.alert_wait));
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    dia.dismiss();
                    if (parseUser != null) {
                        userlist.user=parseUser;
                        Log.i("AppInfo","SuccessfulLogIn");

                        //Starting AfterLoginActivity
                        Intent i=new Intent(getApplicationContext(), userlist.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        alertDisplayer("Login failed", e.getMessage()+ "Please retry");
                    }
                }
            });
        }
    }


    //OnCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        rlloginActivityLayout=findViewById(R.id.loginActivityRlayout);
        logInbtn= findViewById(R.id.logInBtn);
        logoInLogIn=findViewById(R.id.groupintenticon);

        usernameFieldInLogIn=findViewById(R.id.usernameInLogIn);
        passwordFieldInLogIn=findViewById(R.id.passwordInLogIn);
        forgotPassword=findViewById(R.id.forgotPassword);

        /*
        rlloginActivityLayout.setOnClickListener(this); //hiding KeyBoard
        logoInLogIn.setOnClickListener(this);  //Hiding KeyBoard
         */

        forgotPassword.setOnClickListener(this);  //starting Forgot Activity
        passwordFieldInLogIn.setOnKeyListener(this);  //Goto login
        passwordFieldInLogIn.setOnClickListener(this);  //Cursor Visibility
        usernameFieldInLogIn.setOnClickListener(this); //Cursor Visibility
        usernameFieldInLogIn.setOnKeyListener(this); //goto password field
    }
}
