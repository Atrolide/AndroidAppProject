package pl.atrolide.androidappproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DisplayActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "MyPrefs";
    private static final String DATA_SET_KEY = "dataSet";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        ListView listView = findViewById(R.id.dataListView);
        Button backButton = findViewById(R.id.backButton);

        ArrayList<String> dataList = getIntent().getStringArrayListExtra("dataList");

        if (dataList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedData = dataList.get(position);
                Toast.makeText(DisplayActivity.this, selectedData, Toast.LENGTH_LONG).show();
            });
        }

        backButton.setOnClickListener(v -> onBackPressed());

        loadSavedData();
    }

    private void loadSavedData() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        Set<String> dataSet = prefs.getStringSet(DATA_SET_KEY, new HashSet<>());

        ArrayList<String> dataList = new ArrayList<>(dataSet);

        // Separate entries for name and sum
        ArrayList<String> formattedDataList = new ArrayList<>(dataList);

        if (!formattedDataList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formattedDataList);
            ListView listView = findViewById(R.id.dataListView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedData = formattedDataList.get(position);
                Toast.makeText(DisplayActivity.this, selectedData, Toast.LENGTH_SHORT).show();
            });
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        NotificationUtils.showToast(this, "Viewing MainActivity");
    }
}
