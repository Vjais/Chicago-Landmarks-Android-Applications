package com.example.vanijais.project3;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.ActionBar;

import com.example.vanijais.project3.TitleFragment.ListSelectionListener;

public class FragmentActivity extends ActionBarActivity implements ListSelectionListener {
    public static String[] titleArray;
    public static String[] webArray;


    private final WebFragment webFragment = new WebFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout titleFrameLayout, webFrameLayout;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "FragmentActivity";
    private static final String TOAST_INTENT = "com.example.vanijais.project3app2";
    private static final String PERMISSION = "com.example.vanijais.MyPermission" ;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 246;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");

        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles and web
        titleArray = getResources().getStringArray(R.array.Titles);
        webArray = getResources().getStringArray(R.array.web);

        setContentView(R.layout.activity_fragment);

        Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);


        // Get references to the TitleFragment and to the webFragment
        titleFrameLayout = (FrameLayout) findViewById(R.id.titlecontainer);
        webFrameLayout = (FrameLayout) findViewById(R.id.webcontainer);


        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Add the TitleFragment to the layout

        fragmentTransaction.replace(R.id.titlecontainer,
                new TitleFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {

        // Determine whether the webFragment has been added
        if (!webFragment.isAdded()) {

            // Make the TitleFragment occupy the entire layout
            titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            webFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else if (webFragment.isAdded() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            // Make the TitleLayout take 1/3 of the layout's width
            titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));

            // Make the QuoteLayout take 2/3's of the layout's width
            webFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));
        } else if (webFragment.isAdded() && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Make the TitleLayout take 1/3 of the layout's width
            titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));

            // Make the QuoteLayout take 2/3's of the layout's width
            webFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                    MATCH_PARENT));
        }


    }

    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {
        Log.d(TAG,"index is " + index);
        Log.d(TAG, webFragment.isAdded() + "");

        // If the WebFragment has not been added, add it now
        if (!webFragment.isAdded()) {


            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the WebFragment to the layout
            fragmentTransaction.add(R.id.webcontainer,
                    webFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (webFragment.getShownIndex() != index) {

            // Tell the WebFragment to show the webpage at position index
            webFragment.showQuoteAtIndex(index);

        }
    }
    //Create Optionmenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.my_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                //Log.i("aa",aa);
                Toast.makeText(FragmentActivity.this, "option1", Toast.LENGTH_SHORT).show();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                return true;
            case R.id.option2:
                Toast.makeText(FragmentActivity.this, "option2", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(FragmentActivity.this, "com.example.vanijais.MyPermission")
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(FragmentActivity.this, new String[]{"com.example.vanijais.MyPermission"},
                            REQUEST_CODE_ASK_PERMISSIONS);;

                    return true;
                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(TOAST_INTENT);

                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                    sendOrderedBroadcast(intent,PERMISSION);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {
                    // Permission Granted
                    Intent intent = new Intent();
                    intent.setAction(TOAST_INTENT);

                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                    sendOrderedBroadcast(intent,PERMISSION);
                } else {

                    // Permission Denied
                    Toast.makeText(FragmentActivity.this, "Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        /*if (item.getItemId() == R.id.option1) {
            Toast.makeText(FragmentActivity.this, "I'm out", Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }
        if (item.getItemId() == R.id.option2) {
            Toast.makeText(FragmentActivity.this, "Find my Brother", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onCreateOptionsMenu((Menu) item);*/
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

}