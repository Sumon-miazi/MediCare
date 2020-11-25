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
    private final int LOCATION_PERMISSION_ID = 1976;
    private Marker userLocationMarker;
    private LatLng userLocation, initialDestinationLocation;
    private Boolean isGpsLocationEnableChecked = false;
    private Dialog gpsEnableDialog;

    // search layout
    private ConstraintLayout searchLayout;

    // hospital center view
    private CardView hospitalInfoLayout;
    private CircularImageView hospitalImageView;
    private TextView hospitalName;
    private TextView hospitalAddress;

    // diagnostic center view
    private CardView diagnosticCardView;
    private CircularImageView diagnosticImage;
    private TextView diagnosticName;
    private TextView diagnosticAddress;
    private TextView diagnosticPhone;
    private TextView allServicesTxt;
    private Button orderTest;
    private ExpandableLayout expandable_layout;

    // blood bank view
    private CardView bbCardView;
    private CircularImageView bbImage;
    private TextView bbName;
    private TextView bbAddress;
    private TextView bbPhone;
    private Button bbCallBtn;

    private ArrayList<Hospital> hospitals;
    private ArrayList<DiagnosticCenter> diagnosticCenters;
    private ArrayList<BloodBank> bloodBanks;
    private String selectedNearbyCategoryName = "";
    private String name = "";
    private SmartMaterialSpinner searchNearbySpinner;
    private String searchOption = "";
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        searchLayout = findViewById(R.id.searchLayoutId);
        searchNearbySpinner = findViewById(R.id.searchNearbySpinnerId);

        if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {

            initialDestinationLocation = new LatLng(getIntent().getDoubleExtra("latitude", 0),
                    getIntent().getDoubleExtra("longitude", 0));
            title = getIntent().getStringExtra("title");
        }


        initHospitalView();
        initDiagnosticView();
        initBloodBankView();

        initMap();
        initSpinner();
        initializeDialog();
    }

    private void initHospitalView() {
        hospitalInfoLayout = findViewById(R.id.hospitalInfoLayoutId);
        hospitalImageView = findViewById(R.id.hospitalImageViewId);
        ImageView hospitalCloseIcon = findViewById(R.id.hospitalMapIconId);
        hospitalCloseIcon.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_baseline_close_24));

        hospitalName = findViewById(R.id.hospitalNameTxtViewId);
        hospitalAddress = findViewById(R.id.hospitalAddressTxtViewId);

        hospitalCloseIcon.setVisibility(View.VISIBLE);
        hospitalCloseIcon.setOnClickListener(this::closeIconClicked);
    }

    private void initDiagnosticView() {
        diagnosticCardView = findViewById(R.id.diagnosticCardView);
        diagnosticImage = findViewById(R.id.main_userImageViewId);
        ImageView diagnosticCloseIcon = findViewById(R.id.closeIconId);
        diagnosticName = findViewById(R.id.textView5);
        diagnosticAddress = findViewById(R.id.textView4);
        diagnosticPhone = findViewById(R.id.textView6);
        Button seeAllServicesBtn = findViewById(R.id.seeAllServicesBtnId);
        orderTest = findViewById(R.id.requestReportBtnId);
        expandable_layout = findViewById(R.id.expandable_layout);
        allServicesTxt = findViewById(R.id.allServicesTxtId);

        diagnosticCloseIcon.setVisibility(View.VISIBLE);
        diagnosticCloseIcon.setOnClickListener(this::closeIconClicked);
        seeAllServicesBtn.setOnClickListener(view -> expandable_layout.toggle());
    }

    private void initBloodBankView() {
        bbCardView = findViewById(R.id.bloodBankLayoutId);
        bbImage = findViewById(R.id.bloodBankImageViewId);
        ImageView bbCloseIcon = findViewById(R.id.bbCloseIconId);
        bbName = findViewById(R.id.bloodBankNameTxtViewId);
        bbAddress = findViewById(R.id.bloodBankAddressTxtViewId);
        bbPhone = findViewById(R.id.bloodBankPhoneTxtViewId);
        bbCallBtn = findViewById(R.id.bbCallBtn);
        ImageView bbLocationBtn = findViewById(R.id.bbLocationBtn);
        bbLocationBtn.setVisibility(View.GONE);

        bbCloseIcon.setVisibility(View.VISIBLE);
        bbCloseIcon.setOnClickListener(this::closeIconClicked);

    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initSpinner() {

        ArrayList<String> nearBy = new ArrayList<>();

        nearBy.add(ApiUrls.HOSPITAL + " in 5km");
        nearBy.add(ApiUrls.HOSPITAL + " in 10km");
        nearBy.add(ApiUrls.HOSPITAL + " in 20km");
        nearBy.add(ApiUrls.HOSPITAL + " in 50km");

        nearBy.add(ApiUrls.DIAGNOSTIC + " in 5km");
        nearBy.add(ApiUrls.DIAGNOSTIC + " in 10km");
        nearBy.add(ApiUrls.DIAGNOSTIC + " in 20km");
        nearBy.add(ApiUrls.DIAGNOSTIC + " in 50km");

        nearBy.add(ApiUrls.BLOODBANK + " in 5km");
        nearBy.add(ApiUrls.BLOODBANK + " in 10km");
        nearBy.add(ApiUrls.BLOODBANK + " in 20km");
        nearBy.add(ApiUrls.BLOODBANK + " in 50km");

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

        if(!name.isEmpty()){
            setMarkersOnMap();
            return;
        }

        mMap.clear();

        LatLngBounds bounds;
        if (initialDestinationLocation != null) {
            bounds = new LatLngBounds.Builder()
                    .include(new LatLng(latitude, longitude))
                    .include(initialDestinationLocation)
                    .build();

            Marker initialLocationMarker = mMap.addMarker(new MarkerOptions()
                    .position(initialDestinationLocation)
                    .title(title)
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_common_gps_marker)));
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

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
        if (searchOption.isEmpty()) {
            Toast.makeText(this, "Please select an option first", Toast.LENGTH_LONG).show();
            return;
        }
        selectedNearbyCategoryName = (searchOption.split("in")[0]).toLowerCase().trim();
        String radius = (searchOption.split("in")[1]).trim().replace("km", "").toLowerCase();
        // Toast.makeText(this,name + " >>>>> " + radius, Toast.LENGTH_LONG).show();

        new SearchApi().getNearby(userLocation, selectedNearbyCategoryName, Integer.parseInt(radius),
                (hospitals, diagnosticCenters, bloodBanks, message) -> {

                    if (selectedNearbyCategoryName.equals(ApiUrls.HOSPITAL) && hospitals != null && !hospitals.isEmpty()) {
                        this.name = this.selectedNearbyCategoryName;
                        this.hospitals = hospitals;
                        setMarkersOnMap();
                    }
                    else if (selectedNearbyCategoryName.equals(ApiUrls.DIAGNOSTIC) && diagnosticCenters != null && !diagnosticCenters.isEmpty()) {
                        this.name = this.selectedNearbyCategoryName;
                        this.diagnosticCenters = diagnosticCenters;
                        setMarkersOnMap();
                    }
                    else if (selectedNearbyCategoryName.equals(ApiUrls.BLOODBANK) && bloodBanks != null && !bloodBanks.isEmpty()) {
                        this.name = this.selectedNearbyCategoryName;
                        this.bloodBanks = bloodBanks;
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
        System.out.println("<<<<<<<< name" + name);
        System.out.println("<<<<<<<< apiule" + ApiUrls.HOSPITAL);
        System.out.println("<<<<<<<< apiule" + ApiUrls.DIAGNOSTIC);
        System.out.println("<<<<<<<< apiule" + ApiUrls.BLOODBANK);
        switch (name) {
            case ApiUrls.HOSPITAL:
                for (int i = 0; i < this.hospitals.size(); i++) {
                    bounds.include(this.hospitals.get(i).getLocation());
                    addMarker(
                            this.hospitals.get(i).getName(),
                            this.hospitals.get(i).getLocation(),
                            R.drawable.ic_hospital_gps_marker
                    );
                }
                break;
            case ApiUrls.DIAGNOSTIC:
                for (int i = 0; i < this.diagnosticCenters.size(); i++) {
                    bounds.include(this.diagnosticCenters.get(i).getLocation());
                    addMarker(
                            this.diagnosticCenters.get(i).getName(),
                            this.diagnosticCenters.get(i).getLocation(),
                            R.drawable.ic_diagnostic_gps_marker
                    );
                }
                break;
            case ApiUrls.BLOODBANK:
                for (int i = 0; i < this.bloodBanks.size(); i++) {
                    bounds.include(this.bloodBanks.get(i).getLocation());
                    addMarker(
                            this.bloodBanks.get(i).getName(),
                            this.bloodBanks.get(i).getLocation(),
                            R.drawable.ic_blood_bank_gps_marker
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
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_user_gps_marker)));


    }

    private void setUpDataOnMarkerClicked(String title){
        System.out.println(">>>>>>>. setUpDataOnMarkerClicked " + title);
        if(title.equals("My location")) {
            closeIconClicked(null);
        }

        else if(name.equals(ApiUrls.HOSPITAL) && this.hospitals != null){
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
        else if(name.equals(ApiUrls.DIAGNOSTIC) && this.diagnosticCenters != null){
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
        else if(name.equals(ApiUrls.BLOODBANK) && this.bloodBanks != null){
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
        hospitalInfoLayout.setVisibility(View.VISIBLE);
        hospitalName.setText(hospitalData.getName());
        hospitalAddress.setText(hospitalData.getAddress());
        if (hospitalData.getImage() != null) {
            Glide.with(this)
                    .load(ApiUrls.BASE_IMAGE_URL + hospitalData.getImage())
                    .into(hospitalImageView);
        }
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

        orderTest.setOnClickListener(view -> {
            Intent intent = new Intent(this, OrderTestActivity.class);
            intent.putExtra("diagnosticCenterId", diagnosticCenterData.getDiagnosticId());
            startActivity(intent);
        });
    }

    private void setBloodBankData(BloodBank bloodBankData) {
        bbCardView.setVisibility(View.VISIBLE);
        bbName.setText(bloodBankData.getName());
        bbAddress.setText(bloodBankData.getAddress());
        bbPhone.setText(bloodBankData.getPhone());

        if (bloodBankData.getImage() != null) {
            Glide.with(this)
                    .load(ApiUrls.BASE_IMAGE_URL + bloodBankData.getImage())
                    .into(bbImage);
        }

        bbCallBtn.setOnClickListener(view -> {
            Toast.makeText(this, "called button clicked " + bloodBankData.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    private void closeIconClicked(View v){
        hospitalInfoLayout.setVisibility(View.GONE);
        diagnosticCardView.setVisibility(View.GONE);
        bbCardView.setVisibility(View.GONE);
        searchLayout.setVisibility(View.VISIBLE);
    }
}