package com.workflow.english;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.net.Uri;
public class MainActivity extends Activity {
 private WebView web;
 private android.speech.tts.TextToSpeech tts;
 private boolean speechReady=false;
 public class SpeechBridge { @android.webkit.JavascriptInterface public void copy(String text) { runOnUiThread(() -> { android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE); cm.setPrimaryClip(android.content.ClipData.newPlainText("WorkFlow backup",text)); }); } @android.webkit.JavascriptInterface public void speak(String text) { runOnUiThread(() -> { if(speechReady) tts.speak(text,android.speech.tts.TextToSpeech.QUEUE_FLUSH,null,"workflow"); else android.widget.Toast.makeText(MainActivity.this,"İngilizce ses hazır değil. Telefonun metin okuma ayarlarını kontrol et.",android.widget.Toast.LENGTH_LONG).show(); }); } }
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  tts=new android.speech.tts.TextToSpeech(this,status -> { if(status==android.speech.tts.TextToSpeech.SUCCESS) { int result=tts.setLanguage(java.util.Locale.UK); speechReady=result>=0; tts.setSpeechRate(0.86f); } });
  web = new WebView(this); setContentView(web);
  web.addJavascriptInterface(new SpeechBridge(),"NativeSpeech");
  web.setOnApplyWindowInsetsListener((view,insets) -> { view.setPadding(insets.getSystemWindowInsetLeft(),insets.getSystemWindowInsetTop(),insets.getSystemWindowInsetRight(),insets.getSystemWindowInsetBottom()); return insets; });
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
 @Override public void onBackPressed() { web.evaluateJavascript("(function(){if(typeof route!=='undefined' && route!=='home'){home();return true;}return false;})()", value -> { if(!"true".equals(value)) finish(); }); }
 @Override protected void onDestroy() { if(tts!=null){tts.stop();tts.shutdown();} web.removeJavascriptInterface("NativeSpeech"); web.destroy(); super.onDestroy(); }
}
