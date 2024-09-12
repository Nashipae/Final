package com.data_center_watchman.service;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.data_center_watchman.R;
import com.data_center_watchman.model.Visitor;
import com.data_center_watchman.ui.CheckinView;

import java.util.List;

public class CheckInAdapter extends RecyclerView.Adapter<CheckInAdapter.CheckInViewHolder> {
    private List<Visitor> checkinList;
    private Context mContext;

    public CheckInAdapter (Context context, List<Visitor> checkinList) {
        this.mContext = context;
        this.checkinList = checkinList;
    }

    @NonNull
    @Override
    public CheckInAdapter.CheckInViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new CheckInViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInAdapter.CheckInViewHolder holder, final int position) {
        holder.fullName.setText(checkinList.get(position).getFullName());
        holder.idNumber.setText(checkinList.get(position).getIdNumber());
        holder.reason.setText(checkinList.get(position).getReason());
        holder.checkedIn.setText(checkinList.get(position).getTimeIn());
        holder.company.setText(checkinList.get(position).getCompany());
        holder.crqNumber.setText(checkinList.get(position).getCrqNumber());
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        holder.visitorDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CheckinView.class);
                intent.putExtra("fullName",checkinList.get(position).getFullName());
                intent.putExtra("idNumber", checkinList.get(position).getIdNumber());
                intent.putExtra("reason", checkinList.get(position).getReason());
                intent.putExtra("checkedIn", checkinList.get(position).getTimeIn());
                intent.putExtra("company", checkinList.get(position).getCompany());
                intent.putExtra("phoneNumber", checkinList.get(position).getPhonenumber());
                intent.putExtra("crqNumber", checkinList.get(position).getCrqNumber());
                intent.putExtra("location", checkinList.get(position).getLocation());
                intent.putExtra("timeOut", checkinList.get(position).getTimeOut());
                intent.putExtra("id", checkinList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkinList.size();
    }
    public class CheckInViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, idNumber, reason, company, checkedIn,visitorDetail, checkedOut, crqNumber;

        private CheckInViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.visitor_Name);
            idNumber = itemView.findViewById(R.id.idnumber);
            reason = itemView.findViewById(R.id.reason_for_visit);
            company = itemView.findViewById(R.id.visitor_type);
            crqNumber = itemView.findViewById(R.id.crqNumber);
            checkedIn = itemView.findViewById(R.id.checkedIn);
            checkedOut = itemView.findViewById(R.id.checkedOut);
            visitorDetail= itemView.findViewById(R.id.visitorDetails);

        }

    }
}
