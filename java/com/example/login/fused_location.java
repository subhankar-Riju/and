package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

public class fused_location extends AppCompatActivity implements LocationListener {
    private TextView latituteField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    String dist,state;
    DatabaseAccess databaseAccess;
    Button back;


    String s;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fused_location);
        latituteField = (TextView) findViewById(R.id.textView14);
        longitudeField = (TextView) findViewById(R.id.textView15);
        //n
        final int PERMISSION_SEND_SMS = 123;

        back=(Button)findViewById(R.id.button2);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        //n
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);
        }
        //n
        String number="9749454770";
        String msg="hello";


        try {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,msg,null,null);
            Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Some fiels is Empty",Toast.LENGTH_LONG).show();
        }


        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);

        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
        //new

        if(s.equals("LOW")) {

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, msg, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Some fiels is Empty", Toast.LENGTH_LONG).show();
            }
        }


    }

    /* Request updates at startup */
    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat =  (location.getLatitude());
        Double lng =  (location.getLongitude());
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        }
        catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }

        Address address = addresses.get(0);
        String addressDetails = address.getFeatureName() + "\n" + address.getThoroughfare() + "\n" +
                "Locality: " + address.getLocality() + "\n" + "district: " + address.getSubAdminArea() + "\n" +
                "State: " + address.getAdminArea() + "\n" + "Country: " + address.getCountryName() + "\n" +
                "Postal Code: " + address.getPostalCode() + "\n";
        state=address.getAdminArea();
        dist=address.getSubAdminArea();




        latituteField.setText(addressDetails);

        check_location(state,dist);
    }

    public  void check_location(String state,String dist){
        s=new String();
        databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        Cursor cursor=databaseAccess.getAddress(state.toUpperCase(),dist.toUpperCase(),"Total Crime");
        while(cursor.moveToNext()){
            s= (cursor.getString(16));

        }
        longitudeField.setText(s +" crime prone zone");
//
        Toast.makeText(getApplicationContext(),"Hello"+s,Toast.LENGTH_SHORT).show();



    }

    private void call_change() {
        Toast.makeText(getApplicationContext(),"Hey"+s,Toast.LENGTH_SHORT).show();
        if(s.equals("LOW")){
            Toast.makeText(getApplicationContext(),"thank you",Toast.LENGTH_SHORT).show();

           Intent i=new Intent(fused_location.this,MainActivity.class);
            startActivity(i);
//            finish();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}
