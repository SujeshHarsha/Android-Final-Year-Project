package com.example.myfirst.assignment.ui.home;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirst.assignment.R;
import com.example.myfirst.assignment.adapters.HotelAdapter;
import com.example.myfirst.assignment.bll.HotelBll;
import com.example.myfirst.assignment.models.Hotel;
import com.example.myfirst.assignment.strictMode.StrictModeClass;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static List<Hotel> hotelList = new ArrayList<>();
    HorizontalScrollView horizontalScrollView;
    CheckBox check1,check2,check3;

    private RecyclerView recycler_view, recycler_view_horizontal;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        horizontalScrollView=root.findViewById(R.id.horizontalScrollView);
        check1=root.findViewById(R.id.check1);
        check3=root.findViewById(R.id.check3);
        check2=root.findViewById(R.id.check2);


        recycler_view = root.findViewById(R.id.recycler_view);
        recycler_view_horizontal = root.findViewById(R.id.recycler_view_horizontal);

        StrictModeClass.StrictMode();
        HotelBll hotelBll = new HotelBll();
        hotelBll.getHotel();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/200);

        HotelAdapter hotelAdapter = new HotelAdapter(getActivity(), hotelList);
        recycler_view.setAdapter(hotelAdapter);
        recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), columns));

        HotelAdapter hotelAdapter1 = new HotelAdapter(getActivity(), hotelList);
        recycler_view_horizontal.setAdapter(hotelAdapter1);
        recycler_view_horizontal.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        horizontalScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(View.FOCUS_RIGHT);

            }
        },5000);

        return root;
    }
}