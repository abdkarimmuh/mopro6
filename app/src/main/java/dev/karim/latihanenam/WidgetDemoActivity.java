package dev.karim.latihanenam;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by Karim on 11/2/2017.
 */

public class WidgetDemoActivity extends AppCompatActivity {
    private final static String PRESH_NAME = "Widget Me";
    private int mAppWidgetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_demo);
        Button simpan = (Button) findViewById(R.id.btn_save);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb1 = (RadioButton)findViewById(R.id.rbtn_hijau);
                RadioButton rb2 = (RadioButton)findViewById(R.id.rbtn_kuning);
                RadioButton rb3 = (RadioButton)findViewById(R.id.rbtn_merah);
                int color = Color.WHITE;
                if(rb1.isChecked()){
                    color = Color.GREEN;
                } else if (rb2.isChecked()){
                    color = Color.YELLOW;
                } else if (rb3.isChecked()){
                    color = Color.RED;
                }
                SharedPreferences.Editor prefs = getApplicationContext().getSharedPreferences(PRESH_NAME, 0).edit();
                prefs.putInt("warna_teks", color);
                prefs.apply();

                ComponentName thisAppWidget = new ComponentName(getApplicationContext().getPackageName(), MyWidgetProvider.class.getName());
                Intent updateIntent = new Intent(getApplicationContext(), MyWidgetProvider.class);
                int[] appWidgetIds = AppWidgetManager.getInstance(getApplicationContext()).getAppWidgetIds(thisAppWidget);
                updateIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                getApplicationContext().sendBroadcast(updateIntent);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }
}
