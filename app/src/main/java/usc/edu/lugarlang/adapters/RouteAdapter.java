package usc.edu.lugarlang.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import usc.edu.lugarlang.R;
import usc.edu.lugarlang.models.Route;
import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private List<Route> routeList;
    private OnRouteClickListener listener;

    public interface OnRouteClickListener {
        void onRouteClick(Route route);
    }

    public RouteAdapter(List<Route> routeList, OnRouteClickListener listener) {
        this.routeList = routeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        Route route = routeList.get(position);
        holder.tvRouteCode.setText("Route: " + route.getRouteCode());
        holder.tvRoutePath.setText(route.getStartPoint() + " → " + route.getEndPoint());
        holder.tvFare.setText("Fare: ₱" + route.getFare());

        if (route.getStatus() != null) {
            holder.tvStatus.setText(route.getStatus());
            if (route.getStatus().equals("Active")) {
                holder.tvStatus.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_green_dark));
            } else {
                holder.tvStatus.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRouteClick(route);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    static class RouteViewHolder extends RecyclerView.ViewHolder {
        TextView tvRouteCode, tvRoutePath, tvFare, tvStatus;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRouteCode = itemView.findViewById(R.id.tvRouteCode);
            tvRoutePath = itemView.findViewById(R.id.tvRoutePath);
            tvFare = itemView.findViewById(R.id.tvFare);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}