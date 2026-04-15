package usc.edu.lugarlang.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import usc.edu.lugarlang.R;
import usc.edu.lugarlang.adapters.RouteAdapter;
import usc.edu.lugarlang.models.Route;
import java.util.ArrayList;
import java.util.List;

public class RouteManagementActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView rvRoutes;
    private RouteAdapter routeAdapter;
    private List<Route> routeList = new ArrayList<>();
    private ValueEventListener routesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_management);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://lugarlangfinal-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = database.getReference("routes");

        // Add button → Adding New Routes screen
        findViewById(R.id.btnAdd).setOnClickListener(v ->
                startActivity(new Intent(this, AddingNewRoutesActivity.class))
        );

        // Bottom nav
        findViewById(R.id.navDashboard).setOnClickListener(v ->
                startActivity(new Intent(this, OperatorDashboardActivity.class))
        );

//        findViewById(R.id.navSettings).setOnClickListener(v ->
//                 startActivity(new Intent(this, OperatorSettingsActivity.class))
//        );

        // Setup RecyclerView
        rvRoutes = findViewById(R.id.rvRoutes);
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter - temporarily disable click handler
        routeAdapter = new RouteAdapter(routeList, route -> {
            // TODO: Create RouteDetailsActivity and uncomment this
            // Intent intent = new Intent(this, RouteDetailsActivity.class);
            // intent.putExtra("routeId", route.getRouteId());
            // startActivity(intent);

            // Temporary: Show toast instead
            Toast.makeText(this, "Route: " + route.getRouteCode(), Toast.LENGTH_SHORT).show();
        });
        rvRoutes.setAdapter(routeAdapter);

        // Load routes from Firebase
        loadRoutes();
    }

    private void loadRoutes() {
        routesListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                routeList.clear();
                for (DataSnapshot routeSnapshot : snapshot.getChildren()) {
                    Route route = routeSnapshot.getValue(Route.class);
                    if (route != null) {
                        route.setRouteId(routeSnapshot.getKey());
                        routeList.add(route);
                    }
                }
                routeAdapter.notifyDataSetChanged();

                if (routeList.isEmpty()) {
                    Toast.makeText(RouteManagementActivity.this,
                            "No routes found. Add a new route.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RouteManagementActivity.this,
                        "Error loading routes: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the listener to prevent memory leaks
        if (routesListener != null) {
            databaseReference.removeEventListener(routesListener);
        }
    }
}