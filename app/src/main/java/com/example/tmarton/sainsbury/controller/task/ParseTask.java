package com.example.tmarton.sainsbury.controller.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tmarton.sainsbury.R;
import com.example.tmarton.sainsbury.model.Item;
import com.example.tmarton.sainsbury.model.ItemList;
import com.example.tmarton.sainsbury.ui.MainActivityAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Tamas Marton.
 */
public class ParseTask extends AsyncTask<String, Void, ArrayList<Item>> {

    private float value = 0;

    private final Activity activity;
    private ProgressDialog ringProgressDialog;

    public ParseTask(Activity activity) {
        this.activity = activity;
    }

    /**
     * show progress dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ringProgressDialog = ProgressDialog.show(activity, activity.getString(R.string.progress_dialog_title), activity.getString(R.string.progress_dialog_content), true);
        ringProgressDialog.show();
    }

    /**
     * @param params
     * @return ArrayList<Item>
     * make the html parsing
     */
    @Override
    protected ArrayList<Item> doInBackground(final String... params) {

        Document doc = Jsoup.parse(params[0]);
        Elements elements = doc.select(".productInfo");

        ArrayList<Item> list = new ArrayList<>();
        for (Element element : elements) {
            Item item = new Item();

            item.setName(element.select("h3").text());
            setImageUr(element, item);
            setAdditionalImageUr(element, item);
            setPriceUnit(element, item);
            item.setPricePerMesure(element.select(".pricePerMeasure").text());
            list.add(item);

            sumValue(item);
        }
        new ItemList().setItemsList(list);

        return list;
    }

    /**
     * @param items do things after parser finished, dismiss porgressdialog, setup views
     */
    @Override
    protected void onPostExecute(final ArrayList<Item> items) {
        super.onPostExecute(items);
        setupValueView();
        setupRecyclerView(items);
        ringProgressDialog.dismiss();
    }

    /**
     * @param element
     * @param item    parse unit and price
     */
    private void setPriceUnit(final Element element, final Item item) {
        Element price = element.select(".pricePerUnit").first();
        item.setPrice(price.firstElementSibling().textNodes().get(0).text());
        item.setUnit(element.attr("unit"));
    }

    /**
     * @param element
     * @param item    set image url
     */
    private void setImageUr(final Element element, final Item item) {
        Element image = element.select("img").first();
        item.setImageUrl(image.attr("src"));
    }

    /**
     * @param element
     * @param item    set additional image url
     */
    private void setAdditionalImageUr(final Element element, final Item item) {
        Element additinalElement = element.select(".lastchild a img").first();
        if (additinalElement != null) {
            item.setAdditionalImageUrl(additinalElement.attr("src"));
        }
    }

    /**
     * @param item format price and sum prices
     */
    private void sumValue(final Item item) {
        String formattedValue = item.getPrice().substring(2);
        value = value + Float.valueOf(formattedValue);
    }

    /**
     * setup ValueView
     */
    private void setupValueView() {
        TextView valueView = (TextView) activity.findViewById(R.id.value_header);
        valueView.setText(String.valueOf(value));
        valueView.setVisibility(View.VISIBLE);
    }

    /**
     * @param items setup recyclerView
     */
    private void setupRecyclerView(final ArrayList<Item> items) {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.items_recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MainActivityAdapter mainActivityAdapter = new MainActivityAdapter();
        mainActivityAdapter.setItemsList(items);
        recyclerView.setAdapter(mainActivityAdapter);
    }
}

