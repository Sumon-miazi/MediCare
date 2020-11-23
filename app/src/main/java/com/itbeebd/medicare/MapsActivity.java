package com.itbeebd.medicare;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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