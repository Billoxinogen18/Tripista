package com.iti.intake40.tripista;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;

import java.util.List;

public class UpcommingTripAdapter extends RecyclerView.Adapter<UpcommingTripAdapter.ViewHolder> {

    private final Context context;
    private List<Trip> trips;
    private Trip currentTrip;
    FireBaseCore core = FireBaseCore.getInstance();

    public UpcommingTripAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.trips = trips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_single_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.currentTrip = this.trips.get(position);
        holder.tripDate.setText(currentTrip.getDate());
        holder.tripTime.setText(currentTrip.getTime());
        holder.tripTitle.setText(currentTrip.getTitle());
       // holder.tripType.setText(currentTrip.getType().toString());
        holder.startPoint.setText(currentTrip.getStartPoint());
        holder.endPoint.setText(currentTrip.getEndPoint());

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, currentTrip.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tripDate;
        private TextView tripTime;
        private TextView tripTitle;
        private TextView tripType;
        private TextView startPoint;
        private TextView endPoint;
        private TextView distance;
        private ConstraintLayout rootLayout;
        private ImageButton optionsButton;

        ViewHolder(final View itemView) {
            super(itemView);

            tripDate = itemView.findViewById(R.id.txt_trip_date);
            tripTime = itemView.findViewById(R.id.txt_trip_time);
            tripTitle = itemView.findViewById(R.id.txt_trip_title);
            tripType = itemView.findViewById(R.id.txt_trip_type);
            startPoint = itemView.findViewById(R.id.txt_start_pont);
            endPoint = itemView.findViewById(R.id.txt_end_point);
            distance = itemView.findViewById(R.id.txt_distance);
            rootLayout = itemView.findViewById(R.id.trip_row);
            optionsButton = itemView.findViewById(R.id.textViewOptions);

            optionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(context, optionsButton);
                    menu.inflate(R.menu.trip_menu);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.notes:
                                    Toast.makeText(context, "notes" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.edit:
                                    Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.delete:
                                    deleteTrip(getAdapterPosition());
                                    break;
                                case R.id.cancel:
                                    Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            return false;
                        }
                    });
                    //show the menu
                    menu.show();
                }
            });
        }

    }

    private void deleteTrip(final int tripPos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Delete Trip")
                .setMessage("Are you sure you want to delete this trip?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tripId = trips.get(tripPos).getTripId();
                        core.deleteTrip(tripId, context);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

