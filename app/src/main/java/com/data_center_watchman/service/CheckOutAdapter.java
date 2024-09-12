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
import com.data_center_watchman.ui.CheckoutView;

import java.util.List;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder> {
    private List<Visitor> checkOutList;
    private Context mContext;

    public CheckOutAdapter (Context context, List<Visitor> checkOutList) {
        this.mContext = context;
        this.checkOutList = checkOutList;
    }

    @NonNull
    @Override
    public CheckOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new CheckOutAdapter.CheckOutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutViewHolder holder, final int position) {
        holder.fullName.setText(checkOutList.get(position).getFullName());
        holder.idNumber.setText(checkOutList.get(position).getIdNumber());
        holder.reason.setText(checkOutList.get(position).getReason());
        holder.checkedIn.setText(checkOutList.get(position).getTimeIn());
        holder.company.setText(checkOutList.get(position).getCompany());
        holder.crqNumber.setText(checkOutList.get(position).getCrqNumber());
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        holder.visitorDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CheckoutView.class);
                intent.putExtra("fullName",checkOutList.get(position).getFullName());
                intent.putExtra("idNumber", checkOutList.get(position).getIdNumber());
                intent.putExtra("reason", checkOutList.get(position).getReason());
                intent.putExtra("checkedIn", checkOutList.get(position).getTimeIn());
                intent.putExtra("company", checkOutList.get(position).getCompany());
                intent.putExtra("phoneNumber", checkOutList.get(position).getPhonenumber());
                intent.putExtra("crqNumber", checkOutList.get(position).getCrqNumber());
                intent.putExtra("location", checkOutList.get(position).getLocation());
                intent.putExtra("timeOut", checkOutList.get(position).getTimeOut());
                intent.putExtra("id", checkOutList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return checkOutList.size();
    }

    public class CheckOutViewHolder extends RecyclerView.ViewHolder{

        TextView fullName, idNumber, reason, company, checkedIn,visitorDetail, checkedOut,crqNumber;

        private CheckOutViewHolder(@NonNull View itemView) {
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
