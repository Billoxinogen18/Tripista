package com.iti.intake40.tripista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcommingFragment extends Fragment {

    private RecyclerView upcommingRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Trip> tripList = new ArrayList<>();

    private FireBaseCore core = FireBaseCore.getInstance();
    private static final String TAG = "upcomming";

    public UpcommingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_upcomming, container, false);

        upcommingRecyclerView = rootView.findViewById(R.id.upcommming_rc);
        upcommingRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        upcommingRecyclerView.setLayoutManager(layoutManager);
        adapter = new UpcommingTripAdapter(getContext(), tripList);
        upcommingRecyclerView.setAdapter(adapter);

        List<Trip> recTrips = new ArrayList<>();
        core.getTripsForCurrentUser(new OnTripsLoaded() {
            @Override
            public void onTripsLoaded(List<Trip> trips) {
                tripList.addAll(trips);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onTripsLoaded: " + trips.toString());
            }
        });

        return rootView;
    }

}
