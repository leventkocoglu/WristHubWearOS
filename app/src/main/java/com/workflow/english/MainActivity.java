package com.workflow.english;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.net.Uri;
public class MainActivity extends Activity {
 private WebView web;
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  web = new WebView(this); setContentView(web);
  web.getSettings().setJavaScriptEnabled(true);
  web.getSettings().setDomStorageEnabled(true);
  web.getSettings().setAllowFileAccess(false);
  web.setWebViewClient(new WebViewClient() {
   @Override public boolean shouldOverrideUrlLoading(WebView view, android.webkit.WebResourceRequest request) {
    Uri uri=request.getUrl();
    if ("https".equals(uri.getScheme()) && "app.local".equals(uri.getHost())) return false;
    if ("https".equals(uri.getScheme())) { try { startActivity(new Intent(Intent.ACTION_VIEW,uri)); } catch (Exception ignored) {} }
    return true;
   }
   @Override public android.webkit.WebResourceResponse shouldInterceptRequest(WebView view, android.webkit.WebResourceRequest request) {
    Uri uri=request.getUrl();
    if ("https".equals(uri.getScheme()) && "app.local".equals(uri.getHost()) && "/index.html".equals(uri.getPath())) {
     try { return new android.webkit.WebResourceResponse("text/html","UTF-8",getAssets().open("index.html")); } catch (java.io.IOException e) { return null; }
    }
    return new android.webkit.WebResourceResponse("text/plain","UTF-8",new java.io.ByteArrayInputStream(new byte[0]));
   }
  });
  web.loadUrl("https://app.local/index.html");
 }
 @Override public void onBackPressed() { if(web.canGoBack()) web.goBack(); else super.onBackPressed(); }
 @Override protected void onDestroy() { web.destroy(); super.onDestroy(); }
}
