package com.iti.intake40.tripista;

import android.os.Bundle;
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
public class HistoryFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Trip> tripList = new ArrayList<>();
    private FireBaseCore core = FireBaseCore.getInstance();

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        historyRecyclerView = rootView.findViewById(R.id.history_rc);
        historyRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(layoutManager);
        adapter = new UpcommingTripAdapter(getContext(), tripList);
        historyRecyclerView.setAdapter(adapter);

        core.getHistoryTripsForCurrentUser(new OnTripsLoaded() {
            @Override
            public void onTripsLoaded(List<Trip> trips) {
                tripList.addAll(trips);
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }
}
