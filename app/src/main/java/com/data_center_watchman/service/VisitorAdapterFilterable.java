package com.data_center_watchman.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.data_center_watchman.R;
import com.data_center_watchman.model.Visitor;

import java.util.ArrayList;
import java.util.List;

public class VisitorAdapterFilterable extends RecyclerView.Adapter<VisitorAdapterFilterable.VisitorViewHolder>
implements Filterable {

    private List<Visitor> visitorList;
    private List<Visitor> filteredList;
    private VisitorAdapterListener listener;
    private Context mContext;


    public VisitorAdapterFilterable(List<Visitor> visitorList, VisitorAdapterListener listener, Context mContext) {
        this.visitorList = visitorList;
        this.filteredList = visitorList;
        this.listener = listener;
        this.mContext = mContext;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    filteredList = visitorList;

                }else{
                    List<Visitor> filteredVisitor = new ArrayList<>();
                    for(Visitor row : visitorList){
                        if(row.getFullName().toLowerCase().contains(charString.toLowerCase())){
                            filteredVisitor.add(row);
                        }
                    }
                    filteredList = filteredVisitor;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {



            }
        };
    }

    @NonNull
    @Override
    public VisitorAdapterFilterable.VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new VisitorAdapterFilterable.VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorAdapterFilterable.VisitorViewHolder holder, int position) {
        holder.fullName.setText(filteredList.get(position).getFullName());
        holder.idNumber.setText(filteredList.get(position).getIdNumber());
        holder.reason.setText(filteredList.get(position).getReason());
        holder.checkedIn.setText(filteredList.get(position).getTimeIn());
        holder.checkedOut.setText(filteredList.get(position).getTimeOut());
        holder.company.setText(filteredList.get(position).getCompany());
        holder.crqNumber.setText(filteredList.get(position).getCrqNumber());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, idNumber, reason, company, checkedIn,visitorDetail, checkedOut, crqNumber;

        private VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.visitor_Name);
            idNumber = itemView.findViewById(R.id.idnumber);
            reason = itemView.findViewById(R.id.reason_for_visit);
            company = itemView.findViewById(R.id.visitor_type);
            checkedIn = itemView.findViewById(R.id.checkedIn);
            checkedOut = itemView.findViewById(R.id.checkedOut);
            crqNumber = itemView.findViewById(R.id.crqNumber);
            visitorDetail= itemView.findViewById(R.id.visitorDetails);

        }
    }

    public interface VisitorAdapterListener {
        void onVisitorSelected(Visitor visitor);
    }
}
