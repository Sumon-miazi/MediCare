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
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
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
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.api.SearchApi;
import com.itbeebd.medicare.api.allInterfaces.CustomLocation;
import com.itbeebd.medicare.diagnosticCenter.OrderTestActivity;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Hospital;
import com.mikhaellopez.circularimageview.CircularImageView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private int updateTimeInterval = 10000;
    private int LOCATION_PERMISSION_ID = 44;
    private Marker userLocationMarker, initialLocationMarker;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng userLocation, initialDestinationLocation;
    private Boolean isGpsLocationEnableChecked = false;
    private Dialog gpsEnableDialog;

    // search layout
    private ConstraintLayout searchLayout;

    // diagnostic center view
    private CardView diagnosticCardView;
    private CircularImageView diagnosticImage;
    private ImageView diagnosticCloseIcon;
    private TextView diagnosticName;
    private TextView diagnosticAddress;
    private TextView diagnosticPhone;
    private TextView allServicesTxt;
    private Button seeAllServicesBtn, orderTest;
    private ExpandableLayout expandable_layout;

    private ArrayList<Hospital> hospitals;
    private ArrayList<DiagnosticCenter> diagnosticCenters;
    private ArrayList<BloodBank> bloodBanks;
    private String name = "";
    private SmartMaterialSpinner searchNearbySpinner;
    private String searchOption = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        searchLayout = findViewById(R.id.searchLayoutId);
        searchNearbySpinner = findViewById(R.id.searchNearbySpinnerId);

        if (getIntent().hasExtra("lat") && getIntent().hasExtra("long")) {
            initialDestinationLocation = new LatLng(getIntent().getDoubleExtra("lat", 0), getIntent().getDoubleExtra("long", 0));
        }


        initDiagnosticView();

        initMap();
        initSpinner();
        initializeDialog();
    }

    private void initDiagnosticView() {
        diagnosticCardView = findViewById(R.id.diagnosticCardView);
        diagnosticImage = findViewById(R.id.main_userImageViewId);
        diagnosticCloseIcon = findViewById(R.id.closeIconId);
        diagnosticName = findViewById(R.id.textView5);
        diagnosticAddress = findViewById(R.id.textView4);
        diagnosticPhone = findViewById(R.id.textView6);
        seeAllServicesBtn = findViewById(R.id.seeAllServicesBtnId);
        orderTest = findViewById(R.id.requestReportBtnId);
        expandable_layout = findViewById(R.id.expandable_layout);
        allServicesTxt = findViewById(R.id.allServicesTxtId);

        diagnosticCloseIcon.setVisibility(View.VISIBLE);
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        mMap.setOnMarkerClickListener(marker -> {
            System.out.println("mmmmmmmmm >>>> " + marker.getTitle());
            marker.showInfoWindow();
            setUpDataOnMarkerClicked(marker.getTitle());
            return true;
        });
    }

    private void setMarker(double latitude, double longitude) {

        mMap.clear();

        LatLngBounds bounds;
        if (initialDestinationLocation != null) {
            bounds = new LatLngBounds.Builder()
                    .include(new LatLng(latitude, longitude))
                    .include(initialDestinationLocation)
                    .build();

            initialLocationMarker = mMap.addMarker(new MarkerOptions()
                    .position(initialDestinationLocation)
                    .title("Result")
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_user_marker)));
            initialLocationMarker.showInfoWindow();
        }
        else {
            bounds = new LatLngBounds.Builder()
                    .include(new LatLng(latitude, longitude))
                    .build();
        }

        addUserMarker(latitude, longitude);

        if(initialDestinationLocation != null) mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
        else mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12));
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
                        //setMarker(mLastLocation.getLatitude(), mLastLocation.getLongitude());
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
           // getLastLocation(this::setMarker);
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
        if (searchOption.isEmpty()) {
            Toast.makeText(this, "Please select an option first", Toast.LENGTH_LONG).show();
            return;
        }
        name = (searchOption.split("in")[0]).toLowerCase().trim();
        String radius = (searchOption.split("in")[1]).trim().replace("km", "").toLowerCase();
        // Toast.makeText(this,name + " >>>>> " + radius, Toast.LENGTH_LONG).show();

        new SearchApi().getNearByNameAndDistance(name,
                Integer.parseInt(radius),
                (hospitals, diagnosticCenters, bloodBanks, message) -> {

                    if (name.equals("diagnostic centers") && diagnosticCenters != null && !diagnosticCenters.isEmpty()) {
                        this.diagnosticCenters = diagnosticCenters;
                        setMarkersOnMap();
                    }

                });
    }
    private void setMarkersOnMap(){

        mMap.clear();
        initialDestinationLocation = null;

        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        if(userLocation != null) bounds.include(userLocation);

        getLastLocation((latitude, longitude) -> {
            bounds.include(new LatLng(latitude, longitude));
            addUserMarker(latitude, longitude);
        });

        switch (name) {
            case ApiUrls.HOSPITAL:
                for (int i = 0; i < this.hospitals.size(); i++) {
                    bounds.include(this.hospitals.get(i).getLocation());
                    addMarker(
                            this.hospitals.get(i).getName(),
                            this.hospitals.get(i).getLocation(),
                            R.drawable.ic_user_marker
                    );
                }
                break;
            case ApiUrls.DIAGNOSTIC:
                for (int i = 0; i < this.diagnosticCenters.size(); i++) {
                    bounds.include(this.diagnosticCenters.get(i).getLocation());
                    addMarker(
                            this.diagnosticCenters.get(i).getName(),
                            this.diagnosticCenters.get(i).getLocation(),
                            R.drawable.ic_user_marker
                    );
                }
                break;
            case ApiUrls.BLOODBANK:
                for (int i = 0; i < this.bloodBanks.size(); i++) {
                    bounds.include(this.bloodBanks.get(i).getLocation());
                    addMarker(
                            this.bloodBanks.get(i).getName(),
                            this.bloodBanks.get(i).getLocation(),
                            R.drawable.ic_user_marker
                    );
                }
                break;
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 200));
    }

    private void addMarker(String name, LatLng location, int drawable){
        System.out.println(">>>>>>>. addMarker");
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(name)
                .icon(bitmapDescriptorFromVector(this, drawable)));
    }

    private void addUserMarker(double latitude, double longitude){
        System.out.println(">>>>>>>. addUserMarker");

         mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("My location")
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_user_marker)));


    }

    private void setUpDataOnMarkerClicked(String title){
        System.out.println(">>>>>>>. setUpDataOnMarkerClicked " + title);
        if(title.equals("My location")) {
            System.out.println(">>>>>>>>>>>>>else ");
            searchLayout.setVisibility(View.VISIBLE);
            diagnosticCardView.setVisibility(View.GONE);
        }
        else if(name.equals(ApiUrls.HOSPITAL)){
            System.out.println(">>>>>>>>>>>>>hospital ");
            for (int i = 0; i < this.hospitals.size(); i++) {
                Hospital hospital = this.hospitals.get(i);
                if(hospital.getName().equals(title)){
                    searchLayout.setVisibility(View.GONE);
                    setHospitalData(hospital);
                    break;
                }
            }
        }
        else if(name.equals(ApiUrls.DIAGNOSTIC)){
            System.out.println(">>>>>>>>>>>>>dc ");
            for (int i = 0; i < this.diagnosticCenters.size(); i++) {
                DiagnosticCenter diagnosticCenter = this.diagnosticCenters.get(i);
                if(diagnosticCenter.getName().equals(title)){
                    searchLayout.setVisibility(View.GONE);
                    setDiagnosticCenterData(diagnosticCenter);
                    break;
                }
            }
        }
        else if(name.equals(ApiUrls.BLOODBANK)){
            System.out.println(">>>>>>>>>>>>>bb ");
            for (int i = 0; i < this.bloodBanks.size(); i++) {
                BloodBank bloodBank = this.bloodBanks.get(i);
                if(bloodBank.getName().equals(title)){
                    searchLayout.setVisibility(View.GONE);
                    setBloodBankData(bloodBank);
                    break;
                }
            }
        }
    }

    private void setHospitalData(Hospital hospitalData) {

    }

    private void setDiagnosticCenterData(DiagnosticCenter diagnosticCenterData) {
        System.out.println(">>>>>>>. diagnosticCardView visible");
        diagnosticCardView.setVisibility(View.VISIBLE);
        diagnosticName.setText(diagnosticCenterData.getName());
        diagnosticAddress.setText(diagnosticCenterData.getAddress());
        diagnosticPhone.setText(diagnosticCenterData.getPhone());
        allServicesTxt.setText(Html.fromHtml(diagnosticCenterData.getServices()));

        if (diagnosticCenterData.getImage() != null) {
            Glide.with(this)
                    .load(ApiUrls.BASE_IMAGE_URL + diagnosticCenterData.getImage())
                    .into(diagnosticImage);
        }

        seeAllServicesBtn.setOnClickListener(view -> {
            expandable_layout.toggle();
        });

        orderTest.setOnClickListener(view -> {
            Intent intent = new Intent(this, OrderTestActivity.class);
            intent.putExtra("diagnosticCenterId", diagnosticCenterData.getDiagnosticId());
            startActivity(intent);
        });

        diagnosticCloseIcon.setOnClickListener(view -> {
            diagnosticCardView.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
        });
    }

    private void setBloodBankData(BloodBank bloodBankData) {

    }
}