package usc.edu.lugarlang.activities.operator;

import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import usc.edu.lugarlang.R;
import usc.edu.lugarlang.models.Trip;
import java.util.HashMap;
import java.util.Map;

public class EditTripActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String tripId;
    private EditText etPlateNumber, etAssignedDriver, etAssignedConductor, etContactInfo;
    private Spinner spinnerBusCode, spinnerRouteCode;
    private Spinner spinnerDepartureTime, spinnerVehicleAssignment;
    private Spinner spinnerMaintenanceStatus, spinnerTripStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://lugarlangfinal-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = database.getReference("trips");

        tripId = getIntent().getStringExtra("tripId");

        if (tripId == null) {
            Toast.makeText(this, "Trip ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Bind views
        etPlateNumber = findViewById(R.id.etPlateNumber);
        etAssignedDriver = findViewById(R.id.etAssignedDriver);
        etAssignedConductor = findViewById(R.id.etAssignedConductor);
        etContactInfo = findViewById(R.id.etContactInfo);
        spinnerBusCode = findViewById(R.id.spinnerBusCode);
        spinnerRouteCode = findViewById(R.id.spinnerRouteCode);
        spinnerDepartureTime = findViewById(R.id.spinnerDepartureTime);
        spinnerVehicleAssignment = findViewById(R.id.spinnerVehicleAssignment);
        spinnerMaintenanceStatus = findViewById(R.id.spinnerMaintenanceStatus);
        spinnerTripStatus = findViewById(R.id.spinnerTripStatus);

        // Setup spinners
        setupSpinner(spinnerTripStatus,
                new String[]{"Parked", "Departed", "Arrived", "Cancelled"});
        setupSpinner(spinnerDepartureTime,
                new String[]{"06:00", "07:00", "08:00", "09:00", "10:00",
                        "11:00", "12:00", "13:00", "14:00", "15:00"});
        setupSpinner(spinnerVehicleAssignment,
                new String[]{"Bus", "Jeep"});

        // Load existing data
        loadTripData();

        // Submit Changes
        findViewById(R.id.btnSubmitChanges).setOnClickListener(v -> submitChanges());
    }

    private void setupSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void loadTripData() {
        databaseReference.child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Trip trip = snapshot.getValue(Trip.class);
                    if (trip != null) {
                        // Set text fields
                        etPlateNumber.setText(getValueOrDefault(trip.getPlateNumber()));
                        etAssignedDriver.setText(getValueOrDefault(trip.getAssignedDriver()));
                        etAssignedConductor.setText(getValueOrDefault(trip.getAssignedConductor()));
                        etContactInfo.setText(getValueOrDefault(trip.getContactInfo()));

                        // Set spinner selections
                        setSpinnerSelection(spinnerMaintenanceStatus, trip.getMaintenanceStatus());
                        setSpinnerSelection(spinnerTripStatus, trip.getTripStatus());
                        setSpinnerSelection(spinnerDepartureTime, trip.getDepartureTime());
                        setSpinnerSelection(spinnerVehicleAssignment, trip.getVehicleAssignment());
                        setSpinnerSelection(spinnerBusCode, trip.getBusCode());
                        setSpinnerSelection(spinnerRouteCode, trip.getRouteCode());
                    } else {
                        Toast.makeText(EditTripActivity.this, "Error parsing trip data", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(EditTripActivity.this, "Trip not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditTripActivity.this, "Error loading trip: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        if (value == null || value.isEmpty()) return;

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void submitChanges() {
        // Validate inputs
        if (!validateInputs()) {
            return;
        }

        // Create updates map
        Map<String, Object> updates = new HashMap<>();
        updates.put("plateNumber", etPlateNumber.getText().toString().trim());
        updates.put("assignedDriver", etAssignedDriver.getText().toString().trim());
        updates.put("assignedConductor", etAssignedConductor.getText().toString().trim());
        updates.put("contactInfo", etContactInfo.getText().toString().trim());
        updates.put("maintenanceStatus", spinnerMaintenanceStatus.getSelectedItem().toString());
        updates.put("tripStatus", spinnerTripStatus.getSelectedItem().toString());
        updates.put("departureTime", spinnerDepartureTime.getSelectedItem().toString());
        updates.put("vehicleAssignment", spinnerVehicleAssignment.getSelectedItem().toString());

        // Update in Realtime Database
        databaseReference.child(tripId).updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(EditTripActivity.this, "Trip updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditTripActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs() {
        if (etPlateNumber.getText().toString().trim().isEmpty()) {
            etPlateNumber.setError("Plate number is required");
            etPlateNumber.requestFocus();
            return false;
        }

        if (etAssignedDriver.getText().toString().trim().isEmpty()) {
            etAssignedDriver.setError("Driver name is required");
            etAssignedDriver.requestFocus();
            return false;
        }

        return true;
    }

    private String getValueOrDefault(String value) {
        return value != null ? value : "";
    }
}