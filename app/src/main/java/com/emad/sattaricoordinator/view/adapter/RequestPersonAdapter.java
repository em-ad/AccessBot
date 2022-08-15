package com.emad.sattaricoordinator.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.Employee;
import com.emad.sattaricoordinator.model.RequestItem;

import java.util.ArrayList;

public class RequestPersonAdapter extends RecyclerView.Adapter<RequestPersonAdapter.ViewHolder> {

   ArrayList<Employee> dataSet;

   public RequestPersonAdapter(ArrayList<Employee> dataSet) {
      this.dataSet = dataSet;
   }

   @NonNull
   @Override
   public RequestPersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false));
   }

   @Override
   public void onBindViewHolder(@NonNull RequestPersonAdapter.ViewHolder holder, int position) {
      holder.tv.setText(dataSet.get(position).getName() + "(" + dataSet.get(position).getNid() + ")");
   }

   public void setDataSet(ArrayList<Employee> dataSet) {
      this.dataSet = dataSet;
      notifyDataSetChanged();
   }

   @Override
   public int getItemCount() {
      return dataSet == null ? 0 : dataSet.size();
   }

   public class ViewHolder extends RecyclerView.ViewHolder {

      private TextView tv;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         tv = itemView.findViewById(R.id.tv);
      }
   }
}
