package usc.edu.lugarlang.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import usc.edu.lugarlang.R;
import usc.edu.lugarlang.models.Trip;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> tripList;
    private OnTripClickListener listener;

    public interface OnTripClickListener {
        void onMoreDetailsClick(Trip trip);
    }

    public TripAdapter(List<Trip> tripList, OnTripClickListener listener) {
        this.tripList = tripList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);

        holder.tvRoute.setText(trip.getStartPoint() + " → " + trip.getEndPoint());
        holder.tvPlateNumber.setText("Plate: " + trip.getPlateNumber());
        holder.tvDriver.setText("Driver: " + trip.getAssignedDriver());
        holder.tvDepartureTime.setText("Departure: " + trip.getDepartureTime());
        holder.tvStatus.setText(trip.getTripStatus());

        // Set status color
        switch (trip.getTripStatus()) {
            case "Parked":
                holder.tvStatus.setTextColor(holder.itemView.getContext().getColor(R.color.text_dark));
                break;
            case "Departed":
                holder.tvStatus.setTextColor(holder.itemView.getContext().getColor(R.color.accent));
                break;
            case "Arrived":
                holder.tvStatus.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_green_dark));
                break;
            case "Cancelled":
                holder.tvStatus.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
                break;
        }

        holder.btnMoreDetails.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMoreDetailsClick(trip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoute, tvPlateNumber, tvDriver, tvDepartureTime, tvStatus;
        Button btnMoreDetails;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvPlateNumber = itemView.findViewById(R.id.tvPlateNumber);
            tvDriver = itemView.findViewById(R.id.tvDriver);
            tvDepartureTime = itemView.findViewById(R.id.tvDepartureTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnMoreDetails = itemView.findViewById(R.id.btnMoreDetails);
        }
    }
}