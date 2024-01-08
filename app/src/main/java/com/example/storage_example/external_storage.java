package com.example.storage_example;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class external_storage extends AppCompatActivity {

    EditText external_input;
    TextView load_data;
    Button external_save, external_load;
    String fileName = "ExternalData.txt";
    String filePath = "MyFiles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        external_input = findViewById(R.id.external_input);
        load_data = findViewById(R.id.load_data);
        external_save = findViewById(R.id.external_save);
        external_load = findViewById(R.id.external_load);

        if (!isExternalStorageAvailable()) {
            external_save.setEnabled(false);
            external_load.setEnabled(false);
        }
    }

    private boolean isExternalStorageAvailable() {
        String externalStorage = Environment.getExternalStorageState();
        return externalStorage.equals(Environment.MEDIA_MOUNTED);
    }

    public void saveData(View view) {
        String fileData = external_input.getText().toString().trim();
        if(!fileData.isEmpty()) {
                File externalFile = new File(getExternalFilesDir(filePath), fileName);
                try (FileWriter fileWriter = new FileWriter(externalFile)) {
                    fileWriter.write(fileData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                external_input.setText("");
                Toast.makeText(this, "File Saved on SD Card", Toast.LENGTH_SHORT).show();
        } else {
            external_input.setError("This field cannot be empty");
        }
    }

    public void loadData(View view) {
        File externalFile = new File(getExternalFilesDir(filePath), fileName);
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(externalFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            load_data.setText(stringBuilder.toString().trim());
        }
    }

}
