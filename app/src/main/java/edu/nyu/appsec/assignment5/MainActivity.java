package edu.nyu.appsec.assignment5;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.content.Intent;
import android.Manifest;
import android.content.Context;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.net.http.SslError;
import android.support.v4.app.ActivityCompat;

import android.webkit.SslErrorHandler;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.SerializablePermission;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity{
    private static final String SPELL_CHECK_URL = "http://10.0.2.2:8080/";
    private static final String KNOWN_HOST = "10.0.2.2";

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = String.valueOf(request.getUrl());
            String host = Uri.parse(url).getHost();

            if (KNOWN_HOST.equals(host)) {
                return false;
            }

            /* if the URL does not equal the KNOW_URL, we shouldn't just open it in a browser.
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            */
            return true;
        }
    }

    /* Location Service is not needed for the spell check web application.
       Get location data to provide language localization
       Supported languages ar-DZ zh-CN en-US en-IN en-AU fr-FR

    @Override
    public void onLocationChanged(Location location) {
        URL url = null;
        try {
            url = new URL(SPELL_CHECK_URL + "metrics"
                    +"?lat="
                    +location.getLatitude()+"&long=" + location.getLongitude()
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Necessary to implement the LocationListener interface

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView view = new WebView(this);
        view.setWebViewClient(new MyWebViewClient());

        WebSettings settings = view.getSettings();

        settings.setJavaScriptEnabled(true); // JavaScript is needed for button redirection.

        /* The following two lines are not needed, and they are not recommended by android developer reference page.
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        */

        /* Location Service is not needed for the spell check web application.
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        */

        setContentView(view);
        view.loadUrl(SPELL_CHECK_URL + "register");
    }
}
