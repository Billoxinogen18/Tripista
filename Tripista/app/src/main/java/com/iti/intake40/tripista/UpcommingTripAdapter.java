package com.iti.intake40.tripista;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.iti.intake40.tripista.map.ShowMap;
import com.iti.intake40.tripista.note.AddNote;
import com.iti.intake40.tripista.trip.AddTripActivity;
import com.iti.intake40.tripista.trip.ShowNotes;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class UpcommingTripAdapter extends RecyclerView.Adapter<UpcommingTripAdapter.ViewHolder> {

    public static int cancelOneWayTripId;
    public static int cancelRoundWayTripId;
    private static final String TAG = "adapter";
    private final Context context;
    public Trip currentTrip;
    private FireBaseCore core = FireBaseCore.getInstance();
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
        Log.d(TAG, "onBindViewHolder: " + currentTrip.toString());
        holder.tripDate.setText(currentTrip.getDate());
        holder.tripTime.setText(currentTrip.getTime());
        holder.tripTitle.setText(currentTrip.getTitle());
        holder.tripStatus.setText(currentTrip.getStatus().toString());
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
        int r;
        private TextView tripDate;
        private TextView tripTime;
        private TextView tripTitle;
        private TextView tripStatus;
        private TextView startPoint;
        private TextView endPoint;
        private TextView distance;
        private ConstraintLayout rootLayout;
        private ImageButton optionsButton,notes;
        private Button startTrip;


        ViewHolder(final View itemView) {
            super(itemView);

            tripDate = itemView.findViewById(R.id.txt_trip_date);
            tripTime = itemView.findViewById(R.id.txt_trip_time);
            tripTitle = itemView.findViewById(R.id.txt_trip_title);
            tripStatus = itemView.findViewById(R.id.txt_upcommingTrip_status);
            startPoint = itemView.findViewById(R.id.txt_start_pont);
            endPoint = itemView.findViewById(R.id.txt_end_point);
            distance = itemView.findViewById(R.id.txt_distance);
            rootLayout = itemView.findViewById(R.id.trip_row);
            optionsButton = itemView.findViewById(R.id.textViewOptions);
            startTrip = itemView.findViewById(R.id.start_trip);
            notes = itemView.findViewById(R.id.notes_icon);
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
                                    Intent addNote = new Intent(context, AddNote.class);
                                    String tripId = trips.get(getAdapterPosition()).getTripId();
                                    addNote.putExtra("ID", tripId);
                                    context.startActivity(addNote);
                                    break;
                                case R.id.edit:
                                    Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                                    gotoEditTrip(getAdapterPosition());
                                    break;
                                case R.id.delete:
                                    deleteTrip(getAdapterPosition());
                                    break;
                                case R.id.cancel:
                                    Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                                    cancelOneWayTripId = core.getTripCancelID(currentTrip);
                                    cancelRoundWayTripId = core.getTripBackCancelID(currentTrip);
                                    if (currentTrip.getType() == Trip.Type.ROUND_TRIP && currentTrip.getStatus() == Trip.Status.UPCOMMING) {
                                        cancelAlarm(cancelOneWayTripId);
                                        cancelAlarm(cancelRoundWayTripId);
                                    } else {
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
            startTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = trips.get(getAdapterPosition()).getTripId();
                    core.changeStateOfTrip("DONE", id);
                    Intent goMap = new Intent(context, ShowMap.class);
                    goMap.putExtra("id", id);
                    context.startActivity(goMap);
                }
            });
            notes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = trips.get(getAdapterPosition()).getTripId();
                    Intent showNotes = new Intent(context, ShowNotes.class);
                    showNotes.putExtra("id", id);
                    context.startActivity(showNotes);

                }
            });
        }
    }

    public void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
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

    private void gotoEditTrip(final int tripPos) {
        Trip updatedTrip = trips.get(tripPos);
        Intent editIntent = new Intent(context, AddTripActivity.class);
        //get trip values and send it to the edit activity
        editIntent.putExtra(IntentKeys.ID, updatedTrip.getTripId());
        editIntent.putExtra(IntentKeys.TITLE, updatedTrip.getTitle());
        editIntent.putExtra(IntentKeys.DATE, updatedTrip.getDate());
        editIntent.putExtra(IntentKeys.TIME, updatedTrip.getTime());
        editIntent.putExtra(IntentKeys.START_POINT, updatedTrip.getStartPoint());
        editIntent.putExtra(IntentKeys.END_POINT, updatedTrip.getEndPoint());
        editIntent.putExtra(IntentKeys.BACK_DATE, updatedTrip.getBackDate());
        editIntent.putExtra(IntentKeys.BACK_TIME, updatedTrip.getBackTime());
        editIntent.putExtra(IntentKeys.BACK_START_POINT, updatedTrip.getBackStartPoint());
        editIntent.putExtra(IntentKeys.BACK_END_POINT, updatedTrip.getBackEndPoint());
        editIntent.putExtra(IntentKeys.STATUS, updatedTrip.getStatus());
        editIntent.putExtra(IntentKeys.TYPE, updatedTrip.getType().toString());
        context.startActivity(editIntent);
    }

    public class IntentKeys {
        public static final String ID = "tripId";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String START_POINT = "startPoint";
        public static final String END_POINT = "endPoint";
        public static final String BACK_DATE = "back_Date";
        public static final String BACK_TIME = "back_Time";
        public static final String BACK_START_POINT = "back_startPoint";
        public static final String BACK_END_POINT = "back_endPoint";
        public static final String STATUS = "status";
        public static final String TYPE = "type";
    }
}

