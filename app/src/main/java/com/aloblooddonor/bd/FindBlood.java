package com.aloblooddonor.bd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindBlood extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<UserInfo> options;
    FirebaseRecyclerAdapter<UserInfo,SearchViewHolder> adapter;
    ArrayList<UserInfo> arrayList;

    EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_blood);

        searchField = findViewById(R.id.search_field);

        //For Search Field...
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!text.isEmpty()) {
                    search(text);
                }
                else {
                    search("");
                }

                //Making Input Text Uppercase..
                if(!text.equals(text.toUpperCase()))
                {
                    text=text.toUpperCase();
                    searchField.setText(text);
                }

                searchField.setSelection(searchField.getText().length());
            }
        });




        recyclerView = findViewById(R.id.recyclear_view);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        arrayList = new ArrayList<>();

        options = new FirebaseRecyclerOptions.Builder<UserInfo>().setQuery(databaseReference,UserInfo.class).build();

        adapter = new FirebaseRecyclerAdapter<UserInfo, SearchViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull UserInfo model) {
                holder.name_text.setText(model.getName());
                holder.blood_text.setText("Blood Group: " + model.getBlood());
                holder.phone_text.setText("Phone No: " +model.getPhone());
                holder.address_text.setText("Address: " +model.getAddress());
                holder.age_text.setText("Age: " +model.getAge() + " Years");
                holder.weight_text.setText("Weight: " +model.getWeight() + " Kg");
                holder.email_text.setText("Email: " +model.getEmail());
            }

            @NonNull
            @Override
            public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout,viewGroup,false);

                return new SearchViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);




    }



    //Main Search Function...
    private void search(String s) {
        Query query =databaseReference.orderByChild("blood")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren()) {
                        final UserInfo userInfo = dss.getValue(UserInfo.class);
                        arrayList.add(userInfo);
                    }

                    MyAdapter myAdapter = new MyAdapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {


        if (adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.startListening();
        }
    }


}
