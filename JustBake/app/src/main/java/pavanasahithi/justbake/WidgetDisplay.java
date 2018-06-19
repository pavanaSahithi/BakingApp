package pavanasahithi.justbake;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Lenovo on 05-06-2018.
 */

public class WidgetDisplay extends AppCompatActivity {
    public static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    String names[];
    int length;

    public static String getTheResponse(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new WidgetData().execute();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        names = savedInstanceState.getStringArray("names");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("names", names);
    }

    private void createWidget(String s) {
        String ingredients = convert(s);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.my_widget);
        ComponentName thisWidget = new ComponentName(this, MyWidget.class);
        remoteViews.setViewVisibility(R.id.id_widget_tv, View.GONE);
        remoteViews.setViewVisibility(R.id.id_ingredients, View.VISIBLE);
        remoteViews.setTextViewText(R.id.id_ingredients, ingredients);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    private String convert(String s) {
        String data = "";
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); ) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ing = jsonObject.optString("ingredient", null);
                String measure = jsonObject.optString("measure", null);
                float qty = (float) jsonObject.optDouble("quantity", 0.0);
                data = data + (++i) + ". " + ing + " " + qty + " " + measure + "\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public class WidgetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = null;
            try {
                response = getTheResponse(new URL(BASE_URL));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONArray jsonArray;
            final String[] ing;
            try {
                jsonArray = new JSONArray(s);
                length = jsonArray.length();
                names = new String[length];
                ing = new String[length];
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ing[i] = jsonObject.getJSONArray("ingredients").toString();
                    names[i] = jsonObject.getString("name");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(WidgetDisplay.this);
                builder.setTitle(R.string.chooserecipe);
                builder.setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                createWidget(ing[0]);
                                finish();
                                break;
                            case 1:
                                createWidget(ing[1]);
                                finish();
                                break;
                            case 2:
                                createWidget(ing[2]);
                                finish();
                                break;
                            case 3:
                                createWidget(ing[3]);
                                finish();
                                break;

                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
