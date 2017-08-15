package com.ssd.a00818881.tinyarcade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ContactFragment extends Fragment implements View.OnClickListener {

    public int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact,container, false);
        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        Context n = view.getContext().getApplicationContext();
        Intent intent = new Intent(n, ViewContactActivity.class);
        intent.putExtra("id",this.id);
        view.getContext().startActivity(intent);
    }
}
