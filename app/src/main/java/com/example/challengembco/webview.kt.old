import android.webkit.WebView
import android.content.Context

class VulnerableWebView(context: Context) {
    private val webView: WebView = WebView(context)

    fun loadUserInput(userInput: String) {
        webView.loadUrl(userInput) // detectable por "Android Webview debugging enabled"
    }
}