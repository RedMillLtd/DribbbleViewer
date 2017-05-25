package uk.co.redmill.viewabbble.login

/**
 * Created by dave on 5/22/17.
 */

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_dribbble.*

import uk.co.redmill.viewabbble.base.BaseActivity
import uk.co.redmill.viewabbble.BuildConfig
import uk.co.redmill.viewabbble.main.MainActivity
import uk.co.redmill.viewabbble.R

class LoginActivity : BaseActivity(), LoginContract.View {

    private val mPresenter: LoginPresenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_dribbble)
        setupWebView()
    }

    private fun setupWebView() {
        mWebView.clearCache(true)
        mWebView.getSettings().setJavaScriptEnabled(true)
        mWebView.getSettings().setSupportZoom(false)
        mWebView.getSettings().setBuiltInZoomControls(false)
        mWebView.setWebViewClient(OAuthWebViewClient())
        mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE)
                } else {
                    if (View.INVISIBLE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE)
                    }
                    progressBar.setProgress(newProgress)
                }
                super.onProgressChanged(view, newProgress)
            }
        })
        mWebView.loadUrl(String.format(AUTHORIZE_URL, BuildConfig.CLIENT_ID))
    }

    override fun loginSuccess() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun loginFailure() {
        Toast.makeText(activity, "sign in failed , please try again .", Toast.LENGTH_SHORT).show()
        finish()
    }

    internal inner class OAuthWebViewClient : WebViewClient() {
        private var index = 0

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            view.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if (url.contains(BuildConfig.REDIRECT_URI + "?code=") && index == 0) {
                index++
                val uri = Uri.parse(url)
                val code = uri.getQueryParameter("code")
                if (!TextUtils.isEmpty(code)) {
                    mPresenter.loadToken(code)
                }
            }
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
        }
    }

    companion object {

        private val TAG = "LoginActivity"

        val AUTHORIZE_URL = "https://dribbble.com/oauth/authorize?client_id=%s"
    }

}