package com.example.th03_app_daoxuanquan_b24dccc232.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th03_app_daoxuanquan_b24dccc232.R;
import com.example.th03_app_daoxuanquan_b24dccc232.adapter.AppointmentAdapter;
import com.example.th03_app_daoxuanquan_b24dccc232.database.DatabaseHelper;
import com.example.th03_app_daoxuanquan_b24dccc232.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAppointments;
    private TextView tvTotalReminders;
    private RecyclerView rvAppointments;
    private TextView tvEmpty;
    private Button btnAdd;
    private DatabaseHelper dbHelper;
    private AppointmentAdapter adapter;
    private List<Appointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        tvTotalAppointments = findViewById(R.id.tvTotalAppointments);
        tvTotalReminders = findViewById(R.id.tvTotalReminders);
        rvAppointments = findViewById(R.id.rvAppointments);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnAdd = findViewById(R.id.btnAdd);

        rvAppointments.setLayoutManager(new LinearLayoutManager(this));
        appointmentList = new ArrayList<>();
        adapter = new AppointmentAdapter(this, appointmentList);
        rvAppointments.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAppointmentActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        int total = dbHelper.countAll();
        int reminders = dbHelper.countReminders();

        tvTotalAppointments.setText(String.valueOf(total));
        tvTotalReminders.setText(String.valueOf(reminders));

        appointmentList.clear();
        appointmentList.addAll(dbHelper.getAllAppointments());
        adapter.notifyDataSetChanged();

        if (appointmentList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvAppointments.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvAppointments.setVisibility(View.VISIBLE);
        }
    }
}
