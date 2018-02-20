package com.example.vanijais.project3;

import android.app.Activity;
import android.app.Fragment;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.TextView;


/**
 * Created by vanijais on 10/26/17.
 */

public class WebFragment extends Fragment {
    private static final String TAG = "QuotesFragment";

    //WebView mWebView = null;
    private int CurrIdx = -1;
    private int webArrLen;
    private WebView mWebView;
    int a;


    int getShownIndex() {
        return CurrIdx;
    }

    // Show the Quote string at position newIndex
    void showQuoteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= webArrLen)
            return;
        CurrIdx = newIndex;
        mWebView =(WebView)getActivity().findViewById(R.id.web);
        WebSettings websettings = mWebView.getSettings();
        websettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(FragmentActivity.webArray[CurrIdx]);


        //mWebView.setWebViewClient(FragmentActivity.webArray[CurrIdx]);
    }

    @Override
        public void onAttach(Activity activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");



            // Inflate the layout defined in quote_fragment.xml
            // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.webview,
            container,false);
        }


    // Set up some information about the mQuoteView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        mWebView =(WebView)getActivity().findViewById(R.id.web);
        webArrLen = FragmentActivity.webArray.length;
        showQuoteAtIndex(CurrIdx);


    }

    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
        super.onDestroyView();
    }

}


