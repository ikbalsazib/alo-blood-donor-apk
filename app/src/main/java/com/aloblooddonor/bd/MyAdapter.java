package com.aloblooddonor.bd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    public Context c;
    public ArrayList<UserInfo> arrayList;
    public MyAdapter(Context c, ArrayList<UserInfo> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout,viewGroup,false);
        return new MyAdapterViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder myAdapterViewHolder, int i) {
        UserInfo userInfo = arrayList.get(i);

        //Information..
        myAdapterViewHolder.name_text.setText(userInfo.getName());
        myAdapterViewHolder.blood_text.setText("Blood Group: " + userInfo.getBlood());
        myAdapterViewHolder.phone_text.setText("Phone No: " + userInfo.getPhone());
        myAdapterViewHolder.address_text.setText("Address: " + userInfo.getAddress());
        myAdapterViewHolder.age_text.setText("Age: " + userInfo.getAge() + " Years");
        myAdapterViewHolder.weight_text.setText("Weight: " + userInfo.getWeight() + " Kg");
        myAdapterViewHolder.email_text.setText("Email: " +userInfo.getEmail());
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView name_text, blood_text, phone_text, address_text, age_text, weight_text, email_text;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            name_text = itemView.findViewById(R.id.name_text);
            blood_text = itemView.findViewById(R.id.blood_text);
            phone_text = itemView.findViewById(R.id.phone_text);
            address_text = itemView.findViewById(R.id.address_text);
            age_text = itemView.findViewById(R.id.age_text);
            weight_text = itemView.findViewById(R.id.weight_text);
            email_text = itemView.findViewById(R.id.email_text);
        }
    }
}
