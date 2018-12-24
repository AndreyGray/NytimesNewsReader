package andreyskakunenko.nytimesnewsreader;

import android.app.AlertDialog;
import android.os.Environment;
import android.print.PDFPrint;
import android.print.PrintAttributes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import java.io.File;

import andreyskakunenko.nytimesnewsreader.Model.Article;
import andreyskakunenko.nytimesnewsreader.data.FavLab;
import dmax.dialog.SpotsDialog;

public class DetailActivity extends AppCompatActivity {
    WebView webView;
    AlertDialog dialog;
    FloatingActionButton buttonFav, buttonFavDelete;
    Article mArticle;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        buttonFav = findViewById(R.id.floating_favorite_button);
        buttonFavDelete = findViewById(R.id.floating_fav_del_button);
        dialog = new SpotsDialog.Builder().setContext(this).build();
        dialog.show();
        //WebView
        webView = findViewById(R.id.webView);
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

        if(getIntent() != null)
        {
            if(getIntent().getStringExtra("title") !=null
                    &&FavLab.get(this).isExistInFav(getIntent().getStringExtra("title")))
            {
                buttonFav.hide();
            }
            if(getIntent().getStringExtra("id")!=null
                    && !getIntent().getStringExtra("id").isEmpty())
            {
                buttonFav.hide();

            }
            else
                {
                    buttonFavDelete.hide();
                }

            if(!getIntent().getStringExtra("webURL").isEmpty())
                    webView.loadUrl(getIntent().getStringExtra("webURL"));

        }
    }

    public void favoriteOnClick(View view)  {
        if(getIntent() != null) {
            if (!getIntent().getStringExtra("title").isEmpty()) {
                mArticle = new Article(
                        getIntent().getStringExtra("title"),
                        getIntent().getStringExtra("author"),
                        getIntent().getStringExtra("section"),
                        getIntent().getStringExtra("date"),
                        getIntent().getStringExtra("pictureUrl"),
                        getIntent().getStringExtra("webURL"),
                        "TO DO TO DO Extracted article"
                );
                id = mArticle.getId().toString().substring(0,4);
                mArticle.setContent("output_" + id + ".pdf");
                createWebPrintJob(webView);
                FavLab.get(this).addArticle(mArticle);
                Toast.makeText(this, "Article added to favorites", Toast.LENGTH_SHORT).show();
                buttonFav.hide();
            }

        }

    }

    public void deleteOnClick(View view) {
        FavLab.get(this).deleteArticleFromFav(getIntent().getStringExtra("id"));
        Toast.makeText(this, "Article deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void createWebPrintJob(WebView webView) {
        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
        PDFPrint pdfPrint = new PDFPrint(attributes);
        pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, "output_" + id + ".pdf");
    }

}
