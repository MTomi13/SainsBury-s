package com.example.tmarton.sainsbury;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.tmarton.sainsbury.model.Item;
import com.example.tmarton.sainsbury.model.ItemList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Tamas Marton.
 */
public class ParseTask extends AsyncTask<String, Void, ArrayList<Item>> {

    private final Activity activity;
    private ProgressDialog ringProgressDialog;

    public ParseTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ringProgressDialog = ProgressDialog.show(activity, "Please wait ...", "Parsing datas ...", true);
        ringProgressDialog.show();
    }

    @Override
    protected ArrayList<Item> doInBackground(final String... params) {

        Document doc = Jsoup.parse(params[0]);

        Elements elements = doc.select(".productInfo");

        ArrayList<Item> list = new ArrayList<>();
        for (Element element :elements) {
            Item item = new Item();

            item.setName(element.select("h3").text());
            setImageUr(element, item);
            item.setPrice(element.select(".pricePerUnit").text());
            item.setPricePerMesure(element.select(".pricePerMeasure").text());
            list.add(item);
        }
        new ItemList().setItemsList(list);

        return list;
    }

    @Override
    protected void onPostExecute(final ArrayList<Item> items) {
        super.onPostExecute(items);

        ringProgressDialog.dismiss();
    }

    private void setImageUr(final Element element, final Item item) {
        Element image = element.select("img").first();
        item.setImageUrl(image.attr("src"));
    }
}

