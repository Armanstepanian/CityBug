package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class fragment1 extends Fragment {
    Button button ;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main2,container,false);
        button = (Button)root.findViewById(R.id.fcbButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Toast.makeText(getContext(),"asssss",Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);

    }
}
