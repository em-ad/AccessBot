package com.emad.sattaricoordinator.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.sattaricoordinator.R;
import com.emad.sattaricoordinator.model.ItemClickInterface;
import com.emad.sattaricoordinator.model.RequestItem;

import java.util.ArrayList;

public class MyRequestListAdapter extends RecyclerView.Adapter<MyRequestListAdapter.ViewHolder> {

   private ArrayList<RequestItem> dataSet;
   private ItemClickInterface callback;

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new ViewHolder(LayoutInflater.from(parent.getContext())
              .inflate(R.layout.item_request, parent, false));
   }

   public MyRequestListAdapter(ItemClickInterface callback) {
      this.callback = callback;
   }

   @SuppressLint("NotifyDataSetChanged")
   public void setDataSet(ArrayList<RequestItem> dataSet) {
      this.dataSet = dataSet;
      notifyDataSetChanged();
   }

   @SuppressLint("SetTextI18n")
   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      if(position == -1) return;
      String persianStatus = dataSet.get(position).getStatus().equals("accepted") ? "صادر شده" :
              dataSet.get(position).getStatus().equals("rejected") ? "رد شده" : "در انتظار بررسی";
      String requestText = "وضعیت درخواست: " + persianStatus;
      for (int i = 0; i < dataSet.get(position).getEmployees().size(); i++) {
         requestText = requestText + "\n" + dataSet.get(position).getEmployees().get(i).getName() +
                 " (" + dataSet.get(position).getEmployees().get(i).getNid() + ")";
      }
      holder.tv.setText(requestText);
      holder.itemView.setOnClickListener(view -> callback.requestClicked(dataSet.get(position)));
   }

   @Override
   public int getItemCount() {
      return dataSet == null ? 0 : dataSet.size();
   }

   @SuppressWarnings("InnerClassMayBeStatic")
   public class ViewHolder extends RecyclerView.ViewHolder {

      private final TextView tv;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         tv = itemView.findViewById(R.id.tv);
      }
   }
}
