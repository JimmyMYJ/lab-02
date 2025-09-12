package com.example.listycity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private Button addButton, deleteButton;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;


    private int selectedIndex = ListView.INVALID_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityList     = findViewById(R.id.city_list);
        addButton    = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);


        dataList = new ArrayList<>(Arrays.asList(
                "Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin",
                "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"
        ));


        cityAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                dataList
        );
        cityList.setAdapter(cityAdapter);

        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityList.setItemChecked(position, true);
        });

        addButton.setOnClickListener(v -> {
            final EditText input = new EditText(MainActivity.this);
            input.setHint("City name");

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Add City")
                    .setView(input)
                    .setPositiveButton("CONFIRM", (DialogInterface dialog, int which) -> {
                        String name = input.getText().toString().trim();
                            dataList.add(name);
                            cityAdapter.notifyDataSetChanged();
                            cityList.smoothScrollToPosition(dataList.size() - 1);
                            clearSelection();
                    })
                    .setNegativeButton("CANCEL", null)
                    .show();
        });

        deleteButton.setOnClickListener(v -> {
                dataList.remove(selectedIndex);
                cityAdapter.notifyDataSetChanged();
                clearSelection();
        });
    }
    private void clearSelection() {
        cityList.clearChoices();
        selectedIndex = ListView.INVALID_POSITION;
    }
}
