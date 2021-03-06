package com.vanguard.monochrome.app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.bugsnag.android.Bugsnag;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class home extends AppCompatActivity {


    private TextView privacy;
    private TextView disclaimer;

    public void gotoSignUpActivity(View view){
        Intent i=new Intent(getApplicationContext(), signup.class);
        startActivity(i);
    }


    public void gotoLoginActivity(View view){
        Intent i=new Intent(getApplicationContext(), login.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, "362bdc538a5dae7f6173abd97beb83ef");
        Bugsnag.start(this);

        privacy = (TextView) findViewById(R.id.privacy);
        String privacytext = "Privacy Policy";
        SpannableString content = new SpannableString(privacytext);
        content.setSpan(new UnderlineSpan(), 0 , privacytext.length(), 0);
        privacy.setText(content);
        privacy.setTextColor(Color.BLUE);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                builder.setTitle("Privacy Policy");
                builder.setMessage("At Monochrome, one of our main priorities is the privacy of our visitors. This Privacy Policy document contains types of information that is collected and recorded by Monochrome and how we use it.\n" +
                        " \n" +
                        "If you have additional questions or require more information about our Privacy Policy, do not hesitate to contact us at thevanguardapps@protonmail.com\n" +
                        " \n" +
                        "Log Files\n" +
                        "Monochrome follows a standard procedure of using log files. These files log visitors when they use app. The information collected by log files include internet protocol (IP) addresses, Internet Service Provider (ISP), date and time stamp, device name, operating system version, the configuration of the app. The purpose of the information is for analyzing trends, administering the app, tracking users' movement on the app, and gathering demographic information.\n" +
                        " \n" +
                        "Third Party Services\n" +
                        "The app does use third-party services that may collect information used to identify you. This includes:\n" +
                        " \n" +
                        "??? Back4App\n" +
                        "??? Mixpanel\n" +
                        "??? Bugsnag\n" +
                        " \n" +
                        "Children's Information\n" +
                        "Another part of our priority is adding protection for children while using the internet. We encourage parents and guardians to observe, participate in, and/or monitor and guide their online activity.\n" +
                        " \n" +
                        "Monochrome does not knowingly collect any Personal Identifiable Information from children under the age of 13. If you think that your child provided this kind of information on our App, we strongly encourage you to contact us immediately and we will do our best efforts to promptly remove such information from our records.\n" +
                        " \n" +
                        "Consent\n" +
                        "By using our app, you hereby consent to our Privacy Policy.\n");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });

        disclaimer = (TextView) findViewById(R.id.disclaimer);
        String disclaimertext = "Disclaimer";
        SpannableString content1 = new SpannableString(disclaimertext);
        content1.setSpan(new UnderlineSpan(), 0 , disclaimertext.length(), 0);
        disclaimer.setText(content1);
        disclaimer.setTextColor(Color.BLUE);
        disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                builder.setTitle("Disclaimer");
                builder.setMessage("We are doing our best to prepare the content of this app. However, Monochrome cannot warranty the expressions and suggestions of the contents, as well as its accuracy. In addition, to the extent permitted by the law, Monochrome shall not be responsible for any losses and/or damages due to the usage of the information on our app. \n" +
                        "\n" +
                        "By using our app, you hereby consent to our disclaimer and agree to its terms.\n" +
                        "\n" +
                        "Any links contained in our app may lead to external sites are provided for convenience only. Any information or statements that appeared in these sites or app are not sponsored, endorsed, or otherwise approved by Monochrome. For these external sites, Monochrome cannot be held liable for the availability of, or the content located on or through it. Plus, any losses or damages occurred from using these contents or the internet generally.\n" +
                        "As long as the app and the information and services on the app are provided free of charge, we will not be liable for any loss or damage of any nature.\n" +
                        "\n" +
                        "By using our app, you also agree that you are the only one responsible for the content you post in this app. The developers of Monochrome are not responsible for any of the user-generated content.");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });

        if(ParseUser.getCurrentUser()!=null){
            Intent i=new Intent(home.this, userlist.class);
            startActivity(i);
            finish();
        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }
}
