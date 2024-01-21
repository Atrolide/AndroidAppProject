package pl.atrolide.androidappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText inputName, inputX, inputY;
    private TextView nameTextView, sumTextView;
    private List<String> dataList = new ArrayList<>();
    private static final String PREFERENCES_NAME = "JungleDiff";
    private static final String DATA_SET_KEY = "FF15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.inputName);
        inputX = findViewById(R.id.inputX);
        inputY = findViewById(R.id.inputY);

        nameTextView = findViewById(R.id.nameTextView);
        sumTextView = findViewById(R.id.sumTextView);

        loadSavedData();
    }

    @SuppressLint("DefaultLocale")
    public void addData(View view) {
        String name = inputName.getText().toString();

        Integer x = Integer.parseInt(inputX.getText().toString());
        Integer y = Integer.parseInt(inputY.getText().toString());

        int sum = x + y;

        nameTextView.setText(name);
        sumTextView.setText(String.format("%d", sum));
        String currentDate = getCurrentDate();

        dataList.add(String.format("Name: %s\nSum: %d\nDate: %s", name, sum, currentDate));

        saveData();
    }

    public void displayData(View view) {
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putStringArrayListExtra("dataList", new ArrayList<>(dataList));
        startActivity(intent);
    }

    private void saveData() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> dataSet = new HashSet<>(dataList);
        editor.putStringSet(DATA_SET_KEY, dataSet);

        NotificationUtils.showToast(this, "*** New record saved ***");

        editor.apply();
    }

    private void loadSavedData() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        Set<String> dataSet = prefs.getStringSet(DATA_SET_KEY, new HashSet<>());

        dataList = new ArrayList<>(dataSet);
    }

    public void clearData(View view) {
        dataList.clear();
        nameTextView.setText("n/a");
        sumTextView.setText("n/a");
        NotificationUtils.showToast(this, "*** Records data cleared ***");
        saveData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationUtils.showToast(this, "*** Viewing DisplayActivity ***");
    }

    private String getCurrentDate() {
        // Get the current date and time
        Date currentDate = Calendar.getInstance().getTime();
        // Format the date as desired (you can adjust the format)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(currentDate);
    }
}
