package usc.edu.lugarlang.activities.operator;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import usc.edu.lugarlang.R;
import usc.edu.lugarlang.models.Trip;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddTripActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText etStartPoint, etEndPoint, etPlateNumber;
    private EditText etAssignedDriver, etAssignedConductor, etContactInfo;
    private EditText etVehicleAssignment;
    private Spinner spinnerRouteCode, spinnerBusCode;
    private Spinner spinnerDepartureTime, spinnerMaintenanceStatus, spinnerTripStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://lugarlangfinal-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = database.getReference("trips");

        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Bind views
        etStartPoint = findViewById(R.id.etStartPoint);
        etEndPoint = findViewById(R.id.etEndPoint);
        etPlateNumber = findViewById(R.id.etPlateNumber);
        etAssignedDriver = findViewById(R.id.etAssignedDriver);
        etAssignedConductor = findViewById(R.id.etAssignedConductor);
        etContactInfo = findViewById(R.id.etContactInfo);
        etVehicleAssignment = findViewById(R.id.etVehicleAssignment);
        spinnerRouteCode = findViewById(R.id.spinnerRouteCode);
        spinnerBusCode = findViewById(R.id.spinnerBusCode);
        spinnerDepartureTime = findViewById(R.id.spinnerDepartureTime);
        spinnerTripStatus = findViewById(R.id.spinnerTripStatus);

        // Populate spinners
        setupSpinner(spinnerTripStatus,
                new String[]{"Parked", "Departed", "Arrived", "Cancelled"});
        setupSpinner(spinnerDepartureTime,
                new String[]{"06:00", "07:00", "08:00", "09:00", "10:00",
                        "11:00", "12:00", "13:00", "14:00", "15:00"});

        // Add Trip button
        Button btnAddTrip = findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(v -> saveTrip());
    }

    private void setupSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void saveTrip() {
        // Validate inputs
        if (!validateInputs()) {
            return;
        }

        // Generate a unique ID for the trip
        String tripId = databaseReference.push().getKey();
        if (tripId == null) {
            tripId = UUID.randomUUID().toString();
        }

        // Create Trip object
        Trip trip = new Trip();
        trip.setTripId(tripId);
        trip.setStartPoint(etStartPoint.getText().toString().trim());
        trip.setEndPoint(etEndPoint.getText().toString().trim());
        trip.setPlateNumber(etPlateNumber.getText().toString().trim());
        trip.setAssignedDriver(etAssignedDriver.getText().toString().trim());
        trip.setAssignedConductor(etAssignedConductor.getText().toString().trim());
        trip.setContactInfo(etContactInfo.getText().toString().trim());
        trip.setVehicleAssignment(etVehicleAssignment.getText().toString().trim());
        trip.setTripStatus(spinnerTripStatus.getSelectedItem().toString());
        trip.setDepartureTime(spinnerDepartureTime.getSelectedItem().toString());

        // Save to Realtime Database
        databaseReference.child(tripId).setValue(trip, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(AddTripActivity.this, "Trip added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddTripActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs() {
        if (etStartPoint.getText().toString().trim().isEmpty()) {
            etStartPoint.setError("Start point is required");
            etStartPoint.requestFocus();
            return false;
        }

        if (etEndPoint.getText().toString().trim().isEmpty()) {
            etEndPoint.setError("End point is required");
            etEndPoint.requestFocus();
            return false;
        }

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
}