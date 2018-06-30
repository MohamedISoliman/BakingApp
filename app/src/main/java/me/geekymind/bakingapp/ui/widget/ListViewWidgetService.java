package me.geekymind.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import me.geekymind.bakingapp.R;
import me.geekymind.bakingapp.data.entity.Ingredient;

/**
 * Created by Mohamed Ibrahim on 6/30/18.
 */
public class ListViewWidgetService extends RemoteViewsService {

    public static final String KEY_INGREDIENTS_LIST = "INGREDIENTS_LIST";


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Bundle bundle = intent.getBundleExtra(KEY_INGREDIENTS_LIST);
        return new AppWidgetListView(this.getApplicationContext(),
                bundle.getParcelableArrayList(KEY_INGREDIENTS_LIST));
    }

    public class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {

        private List<Ingredient> dataList;
        private Context context;

        public AppWidgetListView(Context applicationContext, List<Ingredient> dataList) {
            this.context = applicationContext;
            this.dataList = dataList;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list_item);
            Ingredient item = dataList.get(position);
            String line =
                    item.getIngredientDescription() + "- " + String.format("%s:%s ", item.getQuantity(),
                            item.getMeasure());
            views.setTextViewText(R.id.widget_ingredient_name, line);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return new RemoteViews(context.getPackageName(), R.layout.loading_view);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
