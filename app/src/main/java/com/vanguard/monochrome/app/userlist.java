package com.vanguard.monochrome.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.vanguard.monochrome.app.utils.Const;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class userlist extends Activity {

    private ArrayList<ParseUser> uList;

    public static ParseUser user;
    private LinearLayout grouplayout;
    private ImageView menu;
    private TextView grouptxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        user=ParseUser.getCurrentUser();
        updateUserStatus(true);

        grouptxt = findViewById(R.id.grouptxt);
        grouptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(userlist.this, groups.class);
                startActivity(i);
            }
        });

        grouplayout =  findViewById(R.id.grouplayout);
        grouplayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(userlist.this, groups.class);
                startActivity(i);
            }
        });

        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(userlist.this, menu);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Privacy Policy")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(userlist.this);
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
                                    "• Back4App\n" +
                                    "• Mixpanel\n" +
                                    "• Bugsnag\n" +
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
                        else {
                            if (item.getTitle().equals("Donate")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(userlist.this);
                                builder.setTitle("Donate Monochrome");
                                builder.setMessage("We require funds for the development and maintenance of Monochrome app and its servers. If you can, please donate us using the Bitcoin address given below: \n" +
                                        "BTC: 1QKAxDaUfbWSQkqUExoMSEWPBiiN1Uvbkm");
                                builder.setPositiveButton("Copy BTC address", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "1QKAxDaUfbWSQkqUExoMSEWPBiiN1Uvbkm"));
                                        Toast.makeText(userlist.this, "BTC address copied!", Toast.LENGTH_LONG).show();
                                    }
                                });
                                builder.setNegativeButton("Cancel", null);
                                builder.show();
                            }
                            else {
                                if (item.getTitle().equals("Logout")) {
                                    updateUserStatus(false);
                                    ParseUser.getCurrentUser().logOut();
                                    Intent i=new Intent(userlist.this, home.class);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    if (item.getTitle().equals("Get Source Code")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(userlist.this);
                                        builder.setTitle("Get Source Code");
                                        builder.setMessage("Monochrome is an open source project. Get the source code and help us to improve Monochrome and be a part of Monochrome's success.");
                                        builder.setPositiveButton("Get Source Code", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/thevanguardapps/monochrome")));
                                            }
                                        });
                                        builder.setNegativeButton("Cancel", null);
                                        builder.show();
                                    }
                                    else {
                                        if (item.getTitle().equals("Disclaimer")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(userlist.this);
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
                                        else {

                                        }
                                    }
                                }
                            }
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateUserStatus(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserList();
    }

    private void updateUserStatus(boolean online){
        user.put("online",online);
        user.saveEventually();
    }

    private void loadUserList(){
        final ProgressDialog dia = ProgressDialog.show(this,null,getString(R.string.alert_loading));
        ParseUser.getQuery().whereNotEqualTo("username",user.getUsername())
                .findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        dia.dismiss();
                        if(e==null){
                            if(objects.size()>0){
                                uList = new ArrayList<ParseUser>(objects);
                                ListView list =findViewById(R.id.listinUserList);
                                list.setAdapter(new UserAdapter());
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                                        //startActivity(new Intent(UserList.this,Chat.class).putExtra(Const.EXTRA_DATA),uList.get(pos).getUsername());
                                        Intent i=new Intent(userlist.this, chat.class);
                                        i.putExtra(Const.EXTRA_DATA,uList.get(pos).getUsername());
                                        startActivity(i);
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),e.getMessage().substring(e.getMessage().indexOf(" ")),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

    }

    private class UserAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return uList.size();
        }

        @Override
        public ParseUser getItem(int i) {
            return uList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int pos, View v, ViewGroup arg2) {
            if(v==null)
                v = getLayoutInflater().inflate(R.layout.chat_item,null);
            ParseUser c=getItem(pos);
            TextView lbl=(TextView) v;
            lbl.setText(c.getUsername());
            lbl.setCompoundDrawablesWithIntrinsicBounds(c.getBoolean("online") ? R.drawable.ic_action_online : R.drawable.ic_action_offline,0,R.drawable.ic_action_arrow,0);

            return v;
        }
    }

}
