
package com.example.myfirst.assignment.ui.aboutus;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.sujan.break_o_last.R;
import com.example.myfirst.assignment.R;
//import com.sujan.break_o_last.models.LatLong;
import com.example.myfirst.assignment.models.LatLong;
import java.util.ArrayList;
import java.util.List;

public class AboutusFragment extends Fragment  implements OnMapReadyCallback {

    private GoogleMap mMap;
    MapView mapView;
    View mView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView=inflater.inflate(R.layout.fragment_aboutus, container, false );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        return  mView;

    }

    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView=mView.findViewById(R.id.map);
        if(mapView != null){
           mapView.onCreate(null);
           mapView.onResume();
           mapView.getMapAsync(this);


        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        mMap=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        List<LatLong> latLongList = new ArrayList<>();
        latLongList.add(new LatLong(27.706195, 85.3300396, "Katyani Dillibazar"));
        latLongList.add(new LatLong(27.709095, 85.3312232, "Sunsine Dillibazar"));

        for (int i = 0; i < latLongList.size(); i++) {


            googleMap.addMarker(new MarkerOptions().position(new LatLng(latLongList.get(i).getLat(), latLongList.get(i).getLon())). title("Break-O-Last Dillibazar").snippet("I hope you to be here"));
            CameraPosition hotel = CameraPosition.builder().target(new LatLng(latLongList.get(i).getLat(), latLongList.get(i).getLon())).zoom(16).bearing(0).tilt(90).build();



//            googleMap.addMarker(new MarkerOptions().position(new LatLng(latLongList.get(i).getLat(),
//                    latLongList.get(i).getLon())).title(latLongList.get(i).getMarker()).snippet("I hope you to be here"));
//            CameraPosition hotel = CameraPosition.builder().target(new LatLng(latLongList.get(i).getLat(), latLongList.get(i).getLon())).zoom(16).bearing(0).tilt(90).build();

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(hotel));
            googleMap.getUiSettings().setZoomControlsEnabled(true);

        }

    }
}
