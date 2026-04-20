package usc.edu.lugarlang.activities.operator;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import usc.edu.lugarlang.R;

public class AddingNewRoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_new_routes);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnAddRoute).setOnClickListener(v -> {
            // TODO: Navigate to Add Route form
            // startActivity(new Intent(this, AddRouteActivity.class));
        });

        findViewById(R.id.btnAddBusJeep).setOnClickListener(v -> {
            startActivity(new Intent(this, AddTripActivity.class));
        });

        findViewById(R.id.btnEditBusJeep).setOnClickListener(v -> {
            startActivity(new Intent(this, OperatorDashboardActivity.class));
        });
    }
}
