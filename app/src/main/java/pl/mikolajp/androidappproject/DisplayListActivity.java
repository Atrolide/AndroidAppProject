package pl.mikolajp.androidappproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DisplayListActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String ENTRY_SET_KEY = "entrySet";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        ListView listView = findViewById(R.id.listView);

        ArrayList<String> entryList = getIntent().getStringArrayListExtra("entryList");

        if (entryList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entryList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedEntry = entryList.get(position);

                    Toast.makeText(DisplayListActivity.this, selectedEntry, Toast.LENGTH_SHORT).show();
                }
            });
        }
        loadSavedEntries();
    }

    private void loadSavedEntries() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> entrySet = prefs.getStringSet(ENTRY_SET_KEY, new HashSet<>());

        ArrayList<String> entryList = new ArrayList<>(entrySet);

        if (!entryList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entryList);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedEntry = entryList.get(position);

                    Toast.makeText(DisplayListActivity.this, selectedEntry, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationUtils.showNotification(this);
    }



}
