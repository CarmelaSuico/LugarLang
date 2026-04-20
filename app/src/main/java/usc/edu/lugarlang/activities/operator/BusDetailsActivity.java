package usc.edu.lugarlang.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import usc.edu.lugarlang.R;
import usc.edu.lugarlang.models.Trip;

public class BusDetailsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String tripId;
    private ValueEventListener tripListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://lugarlangfinal-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = database.getReference("trips");

        tripId = getIntent().getStringExtra("tripId");

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnEdit).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditTripActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        });

        loadTripDetails();
    }

    private void loadTripDetails() {
        if (tripId == null) {
            Toast.makeText(this, "Trip ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tripListener = databaseReference.child(tripId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Trip trip = snapshot.getValue(Trip.class);
                    if (trip != null) {
                        displayTripDetails(trip);
                    } else {
                        Toast.makeText(BusDetailsActivity.this, "Error parsing trip data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BusDetailsActivity.this, "Trip not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BusDetailsActivity.this, "Error loading trip: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayTripDetails(Trip trip) {
        ((TextView) findViewById(R.id.tvTripId)).setText(tripId);
        ((TextView) findViewById(R.id.tvBusCode)).setText("Bus Code: " + getValueOrDefault(trip.getBusCode()));
        ((TextView) findViewById(R.id.tvPlateNumber)).setText("Plate Number: " + getValueOrDefault(trip.getPlateNumber()));
        ((TextView) findViewById(R.id.tvFranchise)).setText("Franchise: " + getValueOrDefault(trip.getFranchise()));
        ((TextView) findViewById(R.id.tvTerminalOrigin)).setText("Terminal Location (Origin): " + getValueOrDefault(trip.getTerminalOrigin()));
        ((TextView) findViewById(R.id.tvDestination)).setText("Destination: " + getValueOrDefault(trip.getDestination()));
        ((TextView) findViewById(R.id.tvDepartureTime)).setText("Assigned Departure Time: " + getValueOrDefault(trip.getDepartureTime()));
        ((TextView) findViewById(R.id.tvETA)).setText("Estimated Arrival Time: " + getValueOrDefault(trip.getEstimatedArrivalTime()));
        ((TextView) findViewById(R.id.tvDriver)).setText("Assigned Driver: " + getValueOrDefault(trip.getAssignedDriver()));
        ((TextView) findViewById(R.id.tvConductor)).setText("Assigned Conductor: " + getValueOrDefault(trip.getAssignedConductor()));
        ((TextView) findViewById(R.id.tvContact)).setText("Contact Info: " + getValueOrDefault(trip.getContactInfo()));
        ((TextView) findViewById(R.id.tvMaintenanceStatus)).setText("Maintenance Status: " + getValueOrDefault(trip.getMaintenanceStatus()));
        ((TextView) findViewById(R.id.tvTripStatus)).setText("Trip Status: " + getValueOrDefault(trip.getTripStatus()));
    }

    private String getValueOrDefault(String value) {
        return value != null && !value.isEmpty() ? value : "Not specified";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the listener to prevent memory leaks
        if (tripListener != null && tripId != null) {
            databaseReference.child(tripId).removeEventListener(tripListener);
        }
    }
}