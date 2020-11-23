package com.itbeebd.medicare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.itbeebd.medicare.api.allInterfaces.CustomLocation;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int updateTimeInterval = 10000;
    private int LOCATION_PERMISSION_ID = 44;
    private Marker userLocationMarker, busLocationMarker;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng userLocation;
    private Boolean isGpsLocationEnableChecked = false;
    private Dialog gpsEnableDialog;

    private SmartMaterialSpinner searchNearbySpinner;
    private String searchOption = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        searchNearbySpinner = findViewById(R.id.searchNearbySpinnerId);

        initSpinner();
        initializeDialog();
    }

    private void initSpinner() {

        ArrayList<String> nearBy = new ArrayList<>();

        nearBy.add("Hospitals in 5km");
        nearBy.add("Hospitals in 10km");
        nearBy.add("Hospitals in 20km");
        nearBy.add("Hospitals in 50km");

        nearBy.add("Diagnostic Centers in 5km");
        nearBy.add("Diagnostic Centers in 10km");
        nearBy.add("Diagnostic Centers in 20km");
        nearBy.add("Diagnostic Centers in 50km");

        nearBy.add("Blood Bank in 5km");
        nearBy.add("Blood Bank in 10km");
        nearBy.add("Blood Bank in 20km");
        nearBy.add("Blood Bank in 50km");

        searchNearbySpinner.setItem(nearBy);

        searchNearbySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(MapsActivity.this, nearBy.get(position), Toast.LENGTH_SHORT).show();
                searchOption = nearBy.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                searchOption = "";
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.uber_style));

        getLastLocation(this::setMarker);

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         */
    }

    private void setMarker(double latitude, double longitude) {
        mMap.clear();

        LatLngBounds bounds;
        /*
        if (true) {
            bounds = new LatLngBounds.Builder()
                    .include(new LatLng(latitude, longitude))
                  //  .include(new LatLng(bus.lat, bus.lon))
                    .build();

           // busLocationMarker = mMap.addMarker(new MarkerOptions()
                  //  .position(new LatLng(bus.lat, bus.lon))
                   // .title(bus.name)
                   // .icon(bitmapDescriptorFromVector(this, R.drawable.ic_bus_marker)));

          //  if (bus.stoppages != null && !bus.stoppages.isEmpty()) {
              //  for (int i = 0; i < bus.stoppages.size(); i++) {
                  //  mMap.addMarker(new MarkerOptions()
                  //          .position(new LatLng(bus.stoppages.get(i).lat, bus.stoppages.get(i).lon))
                    //        .title(bus.stoppages.get(i).spot_name)
                    //        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_blue_spot)));
              //  }
           // }
        } else {
            bounds = new LatLngBounds.Builder()
                    .include(new LatLng(latitude, longitude))
                    .build();
        }


         */

        bounds = new LatLngBounds.Builder()
                .include(new LatLng(latitude, longitude))
                .build();

        userLocationMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("My location")
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_user_marker)));

        // .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_gps)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nearest_station.getStationGPS(),zoom));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation(final CustomLocation customLocation) {

        if (isLocationPermissionsGiven()) {
            if (isLocationEnabled()) {
                isGpsLocationEnableChecked = false;
                requestNewLocationData(customLocation);
                if (gpsEnableDialog.isShowing())
                    gpsEnableDialog.dismiss();

            } else {
                if (isGpsLocationEnableChecked) {
                    if (!gpsEnableDialog.isShowing())
                        gpsEnableDialog.show();
                    // Toast.makeText(this, "To use this app you have to enable gps service on", Toast.LENGTH_LONG).show();
                    return;
                }
                isGpsLocationEnableChecked = true;

                //Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestLocationPermissions();
        }
    }

    private void initializeDialog() {
        gpsEnableDialog = new Dialog(MapsActivity.this);
        gpsEnableDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gpsEnableDialog.setCancelable(true);
        gpsEnableDialog.setContentView(R.layout.gps_enable_dialog);

        Button dialogButton = gpsEnableDialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(v -> gpsEnableDialog.dismiss());
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(final CustomLocation customLocation) {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // Log.i("request2", "getLastLocation from mLocationCallBacks");
                        Location mLastLocation = locationResult.getLastLocation();
                        userLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        setMarker(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        customLocation.customLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                        //Log.i("controlBottomSheet", "location2 >>> " + mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());

                    }
                },
                Looper.myLooper()
        );

    }

    private boolean isLocationPermissionsGiven() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation(this::setMarker);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (isLocationPermissionsGiven()) {
             getLastLocation(this::setMarker);
           // updateUserLocationAndBusCurrentLocationFromAPI();
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void showNoWifiInternet() {
        final Dialog dialog = new Dialog(MapsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.no_internet);

        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    public void searchNearByByNameAndDistance(View view) {
        if(searchOption.isEmpty()){
            Toast.makeText(this, "Please select an option first", Toast.LENGTH_LONG).show();
            return;
        }
        String name = (searchOption.split("in")[0]).trim();
        String radius = (searchOption.split("in")[1]).trim().replace("km","");
        Toast.makeText(this,name + " >>>>> " + radius, Toast.LENGTH_LONG).show();
    }
}