package com.example.th03_app_daoxuanquan_b24dccc232.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.th03_app_daoxuanquan_b24dccc232.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng users
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL)");

        // Tạo bảng appointments
        db.execSQL("CREATE TABLE appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "location TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "time TEXT NOT NULL, " +
                "reminder INTEGER NOT NULL)");

        // Seed data: Users
        db.execSQL("INSERT INTO users (username, password) VALUES ('admin', '123456')");

        // Seed data: Appointments
        db.execSQL("INSERT INTO appointments (title, location, date, time, reminder) " +
                "VALUES ('Họp nhóm đồ án', 'Phòng B203', '2026-10-25', '09:00', 1)");
        db.execSQL("INSERT INTO appointments (title, location, date, time, reminder) " +
                "VALUES ('Khám răng', 'Nha khoa Minh Tâm', '2026-10-26', '14:30', 1)");
        db.execSQL("INSERT INTO appointments (title, location, date, time, reminder) " +
                "VALUES ('Đi cà phê với bạn', 'Highland Coffee', '2026-10-27', '19:00', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS appointments");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // 1. checkLogin
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    // 2. insertUser
    public void insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        db.insert("users", null, values);
    }

    // 3. insertAppointment
    public long insertAppointment(Appointment a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", a.getTitle());
        values.put("location", a.getLocation());
        values.put("date", a.getDate());
        values.put("time", a.getTime());
        values.put("reminder", a.getReminder());
        return db.insert("appointments", null, values);
    }

    // 4. getAllAppointments
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM appointments ORDER BY date ASC, time ASC", null);
            if (cursor.moveToFirst()) {
                do {
                    Appointment a = new Appointment(
                            cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("title")),
                            cursor.getString(cursor.getColumnIndexOrThrow("location")),
                            cursor.getString(cursor.getColumnIndexOrThrow("date")),
                            cursor.getString(cursor.getColumnIndexOrThrow("time")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("reminder"))
                    );
                    list.add(a);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    // 5. getAppointmentById
    public Appointment getAppointmentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM appointments WHERE id = ?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                return new Appointment(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("title")),
                        cursor.getString(cursor.getColumnIndexOrThrow("location")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("time")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("reminder"))
                );
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    // 6. updateReminder
    public void updateReminder(int id, boolean on) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reminder", on ? 1 : 0);
        db.update("appointments", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // 7. deleteAppointment
    public void deleteAppointment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("appointments", "id = ?", new String[]{String.valueOf(id)});
    }

    // 8. countAll
    public int countAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM appointments", null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return 0;
    }

    // 9. countReminders
    public int countReminders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM appointments WHERE reminder = 1", null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return 0;
    }
}
