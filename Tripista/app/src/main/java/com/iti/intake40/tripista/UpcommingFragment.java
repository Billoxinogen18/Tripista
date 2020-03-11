package com.iti.intake40.tripista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    private FloatingActionButton addButton;

    public UpcommingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_upcomming, container, false);
        //test data
        for (int i = 0; i < 10; i++) {
            Trip t = new Trip("date" + i, "time" + i);
            tripList.add(t);
        }
        upcommingRecyclerView = rootView.findViewById(R.id.upcommming_rc);
        upcommingRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        upcommingRecyclerView.setLayoutManager(layoutManager);
        adapter = new UpcommingTripAdapter(getContext(), tripList);
        upcommingRecyclerView.setAdapter(adapter);


//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getActivity().getSupportFragmentManager()
////                        .beginTransaction()
////                        .replace(R.id.fragment_container, new AddTripFragment())
////                        .addToBackStack("add_trip")
////                        .commit();
//                Intent intent = new Intent(getContext(),AddTripActivity.class);
//                startActivity(intent);
//            }
//        });
        return rootView;
    }

}
