package pl.mikolajp.androidappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextArg1, editTextArg2;
    private Button buttonAdd, buttonDisplay, buttonClear;
    private TextView resultTextViewName, resultTextViewSum;
    private List<String> entryList = new ArrayList<>();
    private static final String PREF_NAME = "MyPrefs";
    private static final String ENTRY_SET_KEY = "entrySet";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextArg1 = findViewById(R.id.editTextArg1);
        editTextArg2 = findViewById(R.id.editTextArg2);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDisplay = findViewById(R.id.buttonDisplay);
        buttonClear = findViewById(R.id.buttonClear);

        resultTextViewName = findViewById(R.id.name);
        resultTextViewSum = findViewById(R.id.sum);

        loadSavedEntries();
    }

    public void add(View view){
        String name = editTextName.getText().toString();
        Integer arg1 = Integer.parseInt(editTextArg1.getText().toString());
        Integer arg2 = Integer.parseInt(editTextArg2.getText().toString());
        Integer sum = arg1 + arg2;
        resultTextViewName.setText(name);
        resultTextViewSum.setText(sum.toString());
        String entry = "Name = " + name + "; Sum = " + sum;
        entryList.add(entry);
        saveEntries();
    }

    public void displayList(View view) {
        Intent intent = new Intent(this, DisplayListActivity.class);
        intent.putStringArrayListExtra("entryList", (ArrayList<String>) entryList);
        startActivity(intent);
    }

    private void saveEntries() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> entrySet = new HashSet<>(entryList);
        editor.putStringSet(ENTRY_SET_KEY, entrySet);

        editor.apply();
    }

    private void loadSavedEntries() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> entrySet = prefs.getStringSet(ENTRY_SET_KEY, new HashSet<>());

        entryList = new ArrayList<>(entrySet);
    }

    public void clearList(View view) {
        entryList.clear();
        resultTextViewName.setText("none");
        resultTextViewSum.setText("0");
        saveEntries();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationUtils.showNotification(this);
    }

}
