package com.summer.school.retrofitcourse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.summer.school.retrofitcourse.model.EmployeeData;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {

    private List<EmployeeData> employeeList;
    private OnItemLongClickListener longClickListener;

    public MyListAdapter(List<EmployeeData> employeeList, OnItemLongClickListener longClickListener) {
        this.employeeList = employeeList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.employeeIdTextView.setText(String.valueOf(employeeList.get(position).getId()));
        myViewHolder.employeeNameTextView.setText(employeeList.get(position).getEmployeeName());
        myViewHolder.employeeSalaryTextView.setText(String.valueOf(employeeList.get(position).getEmployeeSalary()));
        myViewHolder.employeeAgeTextView.setText(String.valueOf(employeeList.get(position).getEmployeeAge()));

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return longClickListener.onItemLongClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView employeeIdTextView;
        public TextView employeeNameTextView;
        public TextView employeeSalaryTextView;
        public TextView employeeAgeTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            employeeIdTextView = itemView.findViewById(R.id.employeeId);
            employeeNameTextView = itemView.findViewById(R.id.employeeName);
            employeeSalaryTextView = itemView.findViewById(R.id.employeeSalary);
            employeeAgeTextView = itemView.findViewById(R.id.employeeAge);
        }
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(int position);
    }
}
