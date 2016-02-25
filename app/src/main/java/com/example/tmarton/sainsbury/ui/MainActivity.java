package com.example.tmarton.sainsbury.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.tmarton.sainsbury.R;
import com.example.tmarton.sainsbury.controller.MainActivityController;

/**
 * Created by Tamas Marton.
 */
public class MainActivity extends AppCompatActivity {

    private static final String SAINSBURY_URL = "http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?listView=true&orderBy=FAVOURITES_FIRST&parent_category_rn=12518&top_category=12518&langId=44&beginIndex=0&pageSize=20&catalogId=10137&searchTerm=&categoryId=185749&listId=&storeId=10151&promotionId=%23langId=44&storeId=10151&catalogId=10137&categoryId=185749&parent_category_rn=12518&top_category=12518&pageSize=20&orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hideFilters=true";
    private static final String JAVASCRIPT_INTERFACE = "HTMLOUT";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupBrowser();
    }

    /**
     * setup webview to run the Javascript, and generate the content
     */
    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void setupBrowser() {
        WebView browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new MyJavaScriptInterface(), JAVASCRIPT_INTERFACE);
        browser.setWebViewClient(new WebViewClient() {

            /**
             * @param view
             * @param url
             * @param favicon
             * start showing the progressbar until the page is loading
             */
            @Override
            public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
            }

            /**
             * @param view
             * @param url
             * hide the progressbar, give the html content to the interface
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                progressBar.setVisibility(View.GONE);
            }
        });
        browser.loadUrl(SAINSBURY_URL);
    }

    /**
     * setup toolbar
     */
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Javascript interface to get the html content from the webview
     */
    class MyJavaScriptInterface {

        @JavascriptInterface
        public void processHTML(String html) {

            new MainActivityController(MainActivity.this).createView(html);
        }
    }
}
