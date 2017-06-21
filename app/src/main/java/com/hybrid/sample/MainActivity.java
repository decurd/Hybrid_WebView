package com.hybrid.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private String mCurrentUrl;

    private final static String MAIN_URL = "http://m.naver.com/";

    // BACK 2번 클릭 시 종료 핸들러. 플래그
    private Handler mHandler = new Handler();
    private boolean mFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //스플래시
        startActivity(new Intent(this, SplashActivity.class));

        //웹뷰 설정
        mWebView = (WebView) findViewById(R.id.web);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.setInitialScale(100);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClientClass());
        mWebView.loadUrl(MAIN_URL);
    }


    private class WebViewClientClass extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String overrideUrl) {
            view.loadUrl(overrideUrl);
            return true;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (mWebView.getOriginalUrl().equalsIgnoreCase(MAIN_URL)) {
                if (!mFlag) {
                    Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();    // 종료안내 toast 를 출력
                    mFlag = true;
                    mHandler.sendEmptyMessageDelayed(0, 2000);    // 2000ms 만큼 딜레이
                    return false;
                } else {
                    // 앱 종료 code
                    moveTaskToBack(true);
                    finish();
                    android.os.Process.killProcess(Process.myPid());
                }
            } else {
                // 뒤로 가기 실행
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
            }
        }
        return true;
    }
}
