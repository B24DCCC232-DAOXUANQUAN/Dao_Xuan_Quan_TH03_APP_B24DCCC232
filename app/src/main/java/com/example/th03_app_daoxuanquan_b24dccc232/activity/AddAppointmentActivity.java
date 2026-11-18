package com.example.th03_app_daoxuanquan_b24dccc232.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.th03_app_daoxuanquan_b24dccc232.R;
import com.example.th03_app_daoxuanquan_b24dccc232.database.DatabaseHelper;
import com.example.th03_app_daoxuanquan_b24dccc232.model.Appointment;

import java.util.Calendar;
import java.util.Locale;

public class AddAppointmentActivity extends AppCompatActivity {

    private EditText etTitle, etLocation;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private SwitchCompat switchReminder;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        dbHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        etTitle = findViewById(R.id.etTitle);
        etLocation = findViewById(R.id.etLocation);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        switchReminder = findViewById(R.id.switchReminder);
        btnSave = findViewById(R.id.btnSave);

        timePicker.setIs24HourView(true);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (title.isEmpty()) {
                etTitle.setError("Không được để trống");
                etTitle.requestFocus();
                return;
            }
            if (location.isEmpty()) {
                etLocation.setError("Không được để trống");
                etLocation.requestFocus();
                return;
            }

            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            Calendar selectedCal = Calendar.getInstance();
            selectedCal.set(year, month, day, hour, minute, 0);
            
            if (selectedCal.getTimeInMillis() <= System.currentTimeMillis()) {
                Toast.makeText(this, "Thời gian hẹn phải ở trong tương lai!", Toast.LENGTH_SHORT).show();
                return;
            }

            String dateStr = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day);
            String timeStr = String.format(Locale.US, "%02d:%02d", hour, minute);
            int reminder = switchReminder.isChecked() ? 1 : 0;

            Appointment appointment = new Appointment(title, location, dateStr, timeStr, reminder);
            dbHelper.insertAppointment(appointment);

            Toast.makeText(this, "Đã lưu lịch hẹn", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
