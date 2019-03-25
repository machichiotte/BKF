package com.whitedev.bkf.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_documentation.*
import android.widget.ProgressBar
import com.whitedev.bkf.MainActivity
import com.whitedev.bkf.R

class DocumentationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_documentation, container, false)
    }

    companion object {

        fun newInstance(): DocumentationFragment {
            return DocumentationFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setActionBarTitle(getString(R.string.documentation))

        prepareWebview()
    }

    private var progressBar: ProgressBar? = null
    var mywebview: WebView? = null

    private fun prepareWebview() {
        mywebview = wv_doc
        progressBar = pb_doc

        pb_doc.visibility = View.VISIBLE

        mywebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url)
                progressBar!!.visibility = View.GONE
            }
        }


        //TODO appel api au lancement de l'app
        mywebview!!.loadUrl("http://www.whitedev.fr/")
    }
}