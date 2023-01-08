package com.example.espcammonitoring

import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webView.webViewClient = WebViewClientMy()

        // this will load the url of the website
        webView.loadUrl("https://192.168.1.5:81/stream")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)

//        val myIntent = Intent(this@MainActivity, GalleryActivity::class.java)

//        this@MainActivity.startActivity(myIntent)

        val button: Button = findViewById(R.id.gallery)
        button.setOnClickListener {
            val myIntent = Intent(this@MainActivity, GalleryActivity::class.java)
            this@MainActivity.startActivity(myIntent)
        }
    }


    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack())
            webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}

class WebViewClientMy : WebViewClient() {
    @Override
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError?) {
        handler.proceed() // Ignore SSL certificate errors
    }
}