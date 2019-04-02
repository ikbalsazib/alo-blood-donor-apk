package com.aloblooddonor.bd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SearchViewHolder extends RecyclerView .ViewHolder {
   public TextView name_text, blood_text, phone_text, address_text, age_text, weight_text, email_text;


    public SearchViewHolder(@NonNull View itemView) {
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
