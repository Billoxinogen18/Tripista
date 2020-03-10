package com.iti.intake40.tripista;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
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

import com.iti.intake40.tripista.core.model.Trip;

import java.util.List;

public class UpcommingTripAdapter extends RecyclerView.Adapter<UpcommingTripAdapter.ViewHolder> {

    private final Context context;
    private List<Trip> trips;
    private Trip currentTrip;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
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
                                    break;
                            }
                            return false;
                        }
                    });
                    menu.show();
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        //ADD AN ONMENUITEM LISTENER TO EXECUTE COMMANDS ONCLICK OF CONTEXT MENU TASK
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1:
                        //Do stuff
                        break;

                    case 2:
                        //Do stuff

                        break;
                }
                return true;
            }
        };


    }
}

