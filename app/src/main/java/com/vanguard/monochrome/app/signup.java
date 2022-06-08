package com.vanguard.monochrome.app;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.vanguard.monochrome.app.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class signup extends AppCompatActivity implements View.OnKeyListener,View.OnClickListener {

    EditText usernameField,passwordField,emailField;
    RelativeLayout rlSignUpActivity;
    Button signUpBtn;
    ImageView logoInSignUp;


    @Override
    public void onClick(View view) {

        /*
        if(view.getId()==R.id.logoInSignUp || view.getId()==R.id.rlsignUpActivity){
            InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        else
        */

        if(view.getId()==R.id.signUpBtn){
            try{
                attemptRegister();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(view.getId()==R.id.email ){
            emailField.setCursorVisible(true);
        }
        else if(view.getId()==R.id.password){
            passwordField.setCursorVisible(true);
        }
        else if(view.getId()==R.id.username){
            usernameField.setCursorVisible(true);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
            attemptRegister();
        }
        return false;
    }

    public void alertDisplayer(String title,String message){
        Toast.makeText(getApplicationContext(),title + " " + message,Toast.LENGTH_LONG).show();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public void attemptRegister(){
        // Reset errors.
        usernameField.setError(null);
        emailField.setError(null);
        passwordField.setError(null);

        // Store values at the time of the login attempt.
        final String username=usernameField.getText().toString();
        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Utils.showDialog(this, "Invalid password");
            focusView = passwordField;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Utils.showDialog(this, "Please enter email");
            focusView = emailField;
            cancel = true;
        } else if (!isEmailValid(email)) {
            Utils.showDialog(this, "Invalid email");
            focusView = emailField;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(signup.this);
            builder.setTitle("Before you continue...");
            builder.setMessage("By using our app, you agree that you are the only one responsible for the content you post in this app.\n\nThe developers of Monochrome are not responsible for any of the user-generated content.");
            builder.setPositiveButton("I agree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    final ProgressDialog dia=ProgressDialog.show(signup.this,null,getString(R.string.alert_wait));

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            dia.dismiss();
                            if (e == null) {
                                final String title = "Hurray!";
                                final String message = "Account created successfully";

                                //Mixpanel userdata
                                MixpanelAPI mixpanel = MixpanelAPI.getInstance(signup.this, "362bdc538a5dae7f6173abd97beb83ef");
                                JSONObject userdata = new JSONObject();
                                try {
                                    userdata.put("Username", username);
                                } catch (JSONException jsonException) {
                                    jsonException.printStackTrace();
                                }
                                try {
                                    userdata.put("Email", email);
                                } catch (JSONException jsonException) {
                                    jsonException.printStackTrace();
                                }
                                mixpanel.track("Account created", userdata);
                                //Mixpanel userdata end

                                alertDisplayer(title, message);

                                Intent i=new Intent(signup.this, login.class);
                                startActivity(i);
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                final String title = "Error. Account creation failed";
                                final String message = "Account could not be created";

                                alertDisplayer(title, message + " :" + e.getMessage());
                            }
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        usernameField=findViewById(R.id.username);
        passwordField=findViewById(R.id.password);
        emailField=findViewById(R.id.email);
        signUpBtn=findViewById(R.id.signUpBtn);
        rlSignUpActivity=findViewById(R.id.rlsignUpActivity);
        logoInSignUp=findViewById(R.id.logoInSignUp);

        /*
        logoInSignUp.setOnClickListener(this);
         */
        signUpBtn.setOnClickListener(this);
        /*
        rlSignUpActivity.setOnClickListener(this);
         */
        usernameField.setOnClickListener(this);
        emailField.setOnClickListener(this);
        usernameField.setOnClickListener(this);
        usernameField.setOnKeyListener(this);
        emailField.setOnKeyListener(this);
        passwordField.setOnKeyListener(this);

    }
}
