package com.example.easyteamup.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyteamup.DatabaseHelper;
import com.example.easyteamup.EventDetail;
import com.example.easyteamup.R;
import com.example.easyteamup.classes.Event;
import com.example.easyteamup.classes.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment {

    DatabaseHelper db;
    GoogleMap gm;

    List<Event> events;

    private static final int DEFAULT_ZOOM = 15;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gm = googleMap;
            db = new DatabaseHelper(getActivity());
            events = db.getAllActivePublicEvents();

            LatLng loc_latLong = null;

            for(Event e : events)
            {
                String loc_string = e.getEvtLocation();
                Location loc = new Gson().fromJson(loc_string, Location.class);

                loc_latLong = new LatLng(loc.getLatitude(), loc.getLongitude());
                Marker marker = googleMap.addMarker(new MarkerOptions().position(loc_latLong).title(e.getEvtName()));
                marker.setTag(e);
            }

            if(loc_latLong != null)
            {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc_latLong, DEFAULT_ZOOM));
            }

            gm.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    Event e = (Event)marker.getTag();
                    String hostEmail = db.getUserEmail(e.getHostId());
                    e.setHostEmail(hostEmail);
                    String e_string = new Gson().toJson(e);

                    Intent intent = new Intent(getActivity(), EventDetail.class);
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra("eventInfo", e_string);
                    startActivity(intent);
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}