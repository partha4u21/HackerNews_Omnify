package com.piper.hackernews;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by parthamurmu on 09/09/17.
 */

@SuppressLint("JavascriptInterface")
public class ArticleDetailFragment extends Fragment {
    private WebView webView;
    private ProgressDialog progDailog;

    public ArticleDetailFragment newInstance(String url) {
        ArticleDetailFragment frag = new ArticleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_detail, container, false);

        progDailog = ProgressDialog.show(getActivity(), "Loading", "Please wait...", true);
        progDailog.setCancelable(false);

        webView = view.findViewById(R.id.webview);
        webView.addJavascriptInterface(getActivity(), "android");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        showArticle();
        return view;
    }

    private void showArticle() {
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });


        webView.loadUrl(getArguments().getString("url"));

    }
}