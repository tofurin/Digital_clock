package com.example.digitalclock;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Handler;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private Handler handler = new Handler();
    private Runnable updateWidgetRunnable;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setInt(R.id.Widget,"setBackgroundResource",R.drawable.widget_background);
            updateWidgetRunnable = new Runnable(){
                @Override
                public void run(){
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String currenttime = sdf.format(new Date());

                    views.setTextViewText(R.id.Widget_text,currenttime);

                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views);

                    handler.postDelayed(updateWidgetRunnable, 100);
                }
            };

            handler.post(updateWidgetRunnable);


        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}