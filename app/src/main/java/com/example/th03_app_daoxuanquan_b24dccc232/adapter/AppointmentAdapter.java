package com.example.th03_app_daoxuanquan_b24dccc232.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th03_app_daoxuanquan_b24dccc232.R;
import com.example.th03_app_daoxuanquan_b24dccc232.activity.AppointmentDetailActivity;
import com.example.th03_app_daoxuanquan_b24dccc232.model.Appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private Context context;
    private List<Appointment> appointmentList;

    public AppointmentAdapter(Context context, List<Appointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.tvTitle.setText(appointment.getTitle());
        holder.tvLocation.setText("Địa điểm: " + appointment.getLocation());

        // Format date from yyyy-MM-dd to dd/MM/yyyy
        String displayDate = appointment.getDate();
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Date dateObj = inputFormat.parse(appointment.getDate());
            if (dateObj != null) {
                displayDate = outputFormat.format(dateObj);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvDateTimeBadge.setText(displayDate + " · " + appointment.getTime());

        if (appointment.getReminder() == 1) {
            holder.tvReminderBadge.setText("Có nhắc");
            holder.tvReminderBadge.setBackgroundResource(R.drawable.bg_badge_reminder_on);
            holder.tvReminderBadge.setTextColor(context.getResources().getColor(R.color.badge_reminder_on_text));
        } else {
            holder.tvReminderBadge.setText("Không nhắc");
            holder.tvReminderBadge.setBackgroundResource(R.drawable.bg_badge_reminder_off);
            holder.tvReminderBadge.setTextColor(context.getResources().getColor(R.color.badge_reminder_off_text));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AppointmentDetailActivity.class);
            intent.putExtra("APPOINTMENT_ID", appointment.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList != null ? appointmentList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvLocation;
        TextView tvDateTimeBadge;
        TextView tvReminderBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDateTimeBadge = itemView.findViewById(R.id.tvDateTimeBadge);
            tvReminderBadge = itemView.findViewById(R.id.tvReminderBadge);
        }
    }
}
