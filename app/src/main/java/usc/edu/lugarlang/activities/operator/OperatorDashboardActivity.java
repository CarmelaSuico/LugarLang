package usc.edu.lugarlang.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
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
import usc.edu.lugarlang.adapters.TripAdapter;
import usc.edu.lugarlang.models.Operator;
import usc.edu.lugarlang.models.Trip;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperatorDashboardActivity extends AppCompatActivity {

    private RecyclerView rvTrips;
    private TripAdapter tripAdapter;
    private final List<Trip> tripList = new ArrayList<>();
    private DatabaseReference tripsReference;
    private ValueEventListener tripsListener;
    private final Handler pollingHandler = new Handler();
    private static final int POLL_INTERVAL = 7000; // 7 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_dashboard);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://lugarlangfinal-default-rtdb.asia-southeast1.firebasedatabase.app/");
        tripsReference = database.getReference("trips");

        rvTrips = findViewById(R.id.rvTrips);
        rvTrips.setLayoutManager(new LinearLayoutManager(this));
        tripAdapter = new TripAdapter(tripList, trip -> {
            // Navigate to Bus Details on "More Details" click
            Intent intent = new Intent(this, BusDetailsActivity.class);
            intent.putExtra("tripId", trip.getTripId());
            startActivity(intent);
        });
        rvTrips.setAdapter(tripAdapter);

        // Add Trip button
        findViewById(R.id.btnAddTrip).setOnClickListener(v ->
                startActivity(new Intent(this, AddTripActivity.class))
        );

        // Bottom nav
        findViewById(R.id.navRouteManagement).setOnClickListener(v ->
                startActivity(new Intent(this, RouteManagementActivity.class))
        );
//        findViewById(R.id.navSettings).setOnClickListener(v ->
//                startActivity(new Intent(this, OperatorSettingsActivity.class))
//        );

        startPolling();
    }

    private void startPolling() {
        pollingHandler.post(new Runnable() {
            @Override
            public void run() {
                fetchTrips();
                pollingHandler.postDelayed(this, POLL_INTERVAL);
            }
        });
    }

    private void fetchTrips() {
        tripsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tripList.clear();
                tripList.size();
                int oldSize = 0;

                for (DataSnapshot tripSnapshot : snapshot.getChildren()) {
                    Trip trip = tripSnapshot.getValue(Trip.class);
                    if (trip != null) {
                        trip.setTripId(tripSnapshot.getKey());
                        tripList.add(trip);
                    }
                }

                // More efficient update - notify entire list changed
                if (!tripList.isEmpty()) {
                    tripAdapter.notifyDataSetChanged();
                } else {
                    tripAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
                error.toException().printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pollingHandler.removeCallbacksAndMessages(null);
        // Remove listener if you were using a persistent one
        if (tripsListener != null) {
            tripsReference.removeEventListener(tripsListener);
        }
    }

    public static class OperatorSettingsActivity extends AppCompatActivity {

        private DatabaseReference databaseReference;
        private String operatorId;
        private EditText etName, etEmail, etPhoneNumber, etFranchiseNumber;
        private Button btnSave, btnBack;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_operator_settings);

            // Initialize Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://lugarlangfinal-default-rtdb.asia-southeast1.firebasedatabase.app/");
            databaseReference = database.getReference("operators");

            // Get operator ID from intent or use default
            operatorId = getIntent().getStringExtra("operatorId");
            if (operatorId == null) {
                operatorId = "default_operator"; // You can change this
            }

            // Bind views
            etName = findViewById(R.id.etName);
            etEmail = findViewById(R.id.etEmail);
            etPhoneNumber = findViewById(R.id.etPhoneNumber);
            etFranchiseNumber = findViewById(R.id.etFranchiseNumber);
            btnSave = findViewById(R.id.btnSave);
            btnBack = findViewById(R.id.btnBack);

            // Back button
            btnBack.setOnClickListener(v -> finish());

            // Save button
            btnSave.setOnClickListener(v -> saveSettings());

            // Load existing settings
            loadSettings();
        }

        private void loadSettings() {
            databaseReference.child(operatorId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Operator operator = snapshot.getValue(Operator.class);
                        if (operator != null) {
                            etName.setText(getValueOrDefault(operator.getName()));
                            etEmail.setText(getValueOrDefault(operator.getEmail()));
                            etPhoneNumber.setText(getValueOrDefault(operator.getPhoneNumber()));
                            etFranchiseNumber.setText(getValueOrDefault(operator.getFranchiseNumber()));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(OperatorSettingsActivity.this,
                            "Error loading settings: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void saveSettings() {
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", etName.getText().toString().trim());
            updates.put("email", etEmail.getText().toString().trim());
            updates.put("phoneNumber", etPhoneNumber.getText().toString().trim());
            updates.put("franchiseNumber", etFranchiseNumber.getText().toString().trim());

            databaseReference.child(operatorId).updateChildren(updates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(OperatorSettingsActivity.this,
                                "Settings saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(OperatorSettingsActivity.this,
                                "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private String getValueOrDefault(String value) {
            return value != null ? value : "";
        }
    }
}