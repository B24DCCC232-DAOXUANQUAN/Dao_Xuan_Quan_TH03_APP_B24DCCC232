package com.example.th03_app_daoxuanquan_b24dccc232.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.th03_app_daoxuanquan_b24dccc232.R;
import com.example.th03_app_daoxuanquan_b24dccc232.database.DatabaseHelper;
import com.example.th03_app_daoxuanquan_b24dccc232.model.Appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppointmentDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvLocation, tvDateTime, tvReminderStatus;
    private SwitchCompat switchReminder;
    private Button btnBack, btnDelete;
    private DatabaseHelper dbHelper;
    private int appointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        appointmentId = getIntent().getIntExtra("APPOINTMENT_ID", -1);
        if (appointmentId == -1) {
            finish();
            return;
        }

        dbHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        tvTitle = findViewById(R.id.tvTitle);
        tvLocation = findViewById(R.id.tvLocation);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvReminderStatus = findViewById(R.id.tvReminderStatus);
        switchReminder = findViewById(R.id.switchReminder);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        loadData();

        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dbHelper.updateReminder(appointmentId, isChecked);
            tvReminderStatus.setText(isChecked ? "Đang bật" : "Đang tắt");
        });

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteAppointment(appointmentId);
            Toast.makeText(this, "Đã xóa lịch hẹn", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadData() {
        Appointment a = dbHelper.getAppointmentById(appointmentId);
        if (a != null) {
            tvTitle.setText(a.getTitle());
            tvLocation.setText(a.getLocation());

            String displayDate = a.getDate();
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                Date dateObj = inputFormat.parse(a.getDate());
                if (dateObj != null) {
                    displayDate = outputFormat.format(dateObj);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvDateTime.setText(displayDate + " · " + a.getTime());

            boolean isReminderOn = a.getReminder() == 1;
            switchReminder.setChecked(isReminderOn);
            tvReminderStatus.setText(isReminderOn ? "Đang bật" : "Đang tắt");
        }
    }
}
