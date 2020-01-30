package com.example.todoredo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
//import android.os.FileUtils;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    List<String> itemsa;
    Button btnAdder;
    EditText editTextAer;
    RecyclerView rvItemser;
    ItemsAdapter itemsAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdder = findViewById(R.id.btnAddS);
        editTextAer = findViewById(R.id.editTextA);
        rvItemser = findViewById(R.id.rvitems);



        loadItems();

        ItemsAdapter.OnLongClickListener OnLongClickListenerC = new ItemsAdapter.OnLongClickListener(){

            @Override
            public void onItemLongClick(int position) {
                itemsa.remove(position);
                itemsAdapters.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was Removed!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        itemsAdapters = new ItemsAdapter(itemsa, OnLongClickListenerC);
        rvItemser.setAdapter(itemsAdapters);
        rvItemser.setLayoutManager(new LinearLayoutManager(this));

        btnAdder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String toDoItem = editTextAer.getText().toString();
                        itemsa.add(toDoItem);
                        itemsAdapters.notifyItemInserted(itemsa.size()-1);
                        editTextAer.setText("");
                        Toast.makeText(getApplicationContext(),"Item was Added!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try{
            itemsa = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch(IOException e){
            Log.e("MainActivity", "Error Reading Items", e);
            itemsa = new ArrayList<>();
        }
    }

    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), itemsa);
        } catch(IOException e){
            Log.e("MainActivity", "Error Reading Items", e);
        }
    }

}
