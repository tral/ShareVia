package ru.perm.trubnikov.sharevia;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    Button enableGPSBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        enableGPSBtn = (Button) findViewById(R.id.button);


        enableGPSBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //share("com.maskyn.fileeditor");
                share("");
            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void share(String namePckg) {

        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {

                if (info.activityInfo.packageName.toLowerCase().equalsIgnoreCase(namePckg) || namePckg.equalsIgnoreCase("")) {

                    Intent targeted = new Intent(android.content.Intent.ACTION_SEND);
                    targeted.setType("text/plain"); // put here your mime type

                    //   if (info.activityInfo.packageName.toLowerCase().contains(nameApp) ||
                    //           info.activityInfo.name.toLowerCase().contains(nameApp)) {

                    //  if ((info.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){}else{
                    targeted.putExtra(Intent.EXTRA_TEXT, "My body of post/email");
                    targeted.putExtra(Intent.EXTRA_SUBJECT, "My subject");

                    //Log.d("gps",info.activityInfo.applicationInfo.loadLabel(getPackageManager()) +" - " + info.activityInfo.packageName.toLowerCase() + " " + info.activityInfo.name.toLowerCase());
                    Log.d("gps", info.activityInfo.loadLabel(getPackageManager()) + " - " + info.activityInfo.packageName.toLowerCase() + " " + info.activityInfo.name.toLowerCase());

                    targeted.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                    targeted.setPackage(info.activityInfo.packageName);
                    targeted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CharSequence label = info.loadLabel(getPackageManager());
                    Intent extraIntents = new LabeledIntent(targeted, info.activityInfo.packageName, label, info.icon);

                    targetedShareIntents.add(extraIntents);
                }
                //                  targetedShareIntents.add(targeted);
                // }
                //   }
            }

            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
            startActivity(chooserIntent);
        }
    }


}
