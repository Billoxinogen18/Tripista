package com.iti.intake40.tripista;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class UpcommingTripAdapter extends RecyclerView.Adapter<UpcommingTripAdapter.ViewHolder> {

    public static int  cancelOneWayTripId;
    public static int cancelRoundWayTripId;
    private final Context context;
    public Trip currentTrip;
    FireBaseCore core = FireBaseCore.getInstance();
    String i;
    private List<Trip> trips;
    private List<Trip> tripList = new ArrayList<>();


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
        int r;
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
                                    Toast.makeText(context, "notes", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.edit:
                                    Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.delete:
                                    Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.cancel:
                                    Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                                    cancelOneWayTripId = core.getTripCancelID(currentTrip);
                                    cancelRoundWayTripId = core.getTripBackCancelID(currentTrip);
                                    if (currentTrip.getType() == Trip.Type.ROUND_TRIP && currentTrip.getStatus() == Trip.Status.UPCOMMING){
                                        cancelAlarm(cancelOneWayTripId);
                                        cancelAlarm(cancelRoundWayTripId);
                                    }else{
                                        cancelAlarm(cancelOneWayTripId);
                                    }

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
    public void cancelAlarm(int id){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}

