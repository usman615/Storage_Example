package com.example.storage_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class internal_storage extends AppCompatActivity {

    EditText internal_input;
    TextView load_data;
    Button internal_save, internal_load;
    static final int READ_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        internal_input = findViewById(R.id.internal_input);
        load_data = findViewById(R.id.load_data);
        internal_save = findViewById(R.id.internal_save);
        internal_load = findViewById(R.id.internal_load);

        internal_save.setOnClickListener(v -> {
            String data = internal_input.getText().toString();
            if(!data.isEmpty()) {
                try {
                    FileOutputStream fileOutputStream = openFileOutput("dataFile.txt", MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    outputStreamWriter.write(data);

                    outputStreamWriter.close();
                    internal_input.setText("");
                    Toast.makeText(this, "Data Successfully Saved", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                internal_input.setError("This field cannot be empty");
            }
        });

        internal_load.setOnClickListener(v -> {
            try {
                FileInputStream fileInputStream = openFileInput("dataFile.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

                char[] buffer = new char[READ_SIZE];
                StringBuilder data = new StringBuilder();
                int bytesRead;

                while ((bytesRead = inputStreamReader.read(buffer)) > 0) {
                    data.append(buffer, 0, bytesRead);
                }

                load_data.setText(data);
                Toast.makeText(this, "Data Loaded Successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}