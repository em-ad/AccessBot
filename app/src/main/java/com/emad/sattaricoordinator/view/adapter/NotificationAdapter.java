package com.emad.sattaricoordinator.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.NotificationModel;

import java.util.ArrayList;

import ir.hamsaa.persiandatepicker.date.PersianDateImpl;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    ArrayList<NotificationModel> dataSet;
    long lastSeen;

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.tvDescription.setText(dataSet.get(position).getDescription());
        PersianDateImpl impl = new PersianDateImpl();
        impl.setDate(dataSet.get(position).getCreateDate());
        holder.tvDate.setText(impl.getPersianLongDate());
        if (dataSet.get(position).getCreateDate() > lastSeen)
            holder.tvTitle.setText(dataSet.get(position).getTitle() + " (پیام جدید)");
        else holder.tvTitle.setText(dataSet.get(position).getTitle() + " (خوانده شده)");

    }

    public void setDataSet(ArrayList<NotificationModel> dataSet, long lastSeen) {
        this.dataSet = dataSet;
        this.lastSeen = lastSeen;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
