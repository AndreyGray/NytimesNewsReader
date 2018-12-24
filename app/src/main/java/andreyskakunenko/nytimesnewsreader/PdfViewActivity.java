package andreyskakunenko.nytimesnewsreader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;

import java.io.File;

import andreyskakunenko.nytimesnewsreader.data.FavLab;

public class PdfViewActivity extends AppCompatActivity {
    PDFView mPDFView;
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
    FloatingActionButton delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        mPDFView = findViewById(R.id.pdf_viewer);
        delButton = findViewById(R.id.floating_pdf_del_button);
        if (getIntent() != null) {
            String filename = getIntent().getStringExtra("content");
            File pdfFile = new File(path,filename);

            mPDFView.fromFile(pdfFile)
                    .password(null) //If have password
                    .defaultPage(0) //Open default page(remember this value to open from last time)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true) //Double tap to zoom
                    .onDraw(new OnDrawListener() {
                        @Override
                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                            //Something code
                        }
                    })
                    .onDrawAll(new OnDrawListener() {
                        @Override
                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                            //Something code
                        }
                    })
                    .onPageError(new OnPageErrorListener() {
                        @Override
                        public void onPageError(int page, Throwable t) {
                            Toast.makeText(PdfViewActivity.this, "Error open page"+page, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {
                            // Something code
                        }
                    })
                    .onTap(new OnTapListener() {
                        @Override
                        public boolean onTap(MotionEvent e) {
                            return true;
                        }
                    })
                    .onRender(new OnRenderListener() {
                        @Override
                        public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                            mPDFView.fitToWidth();//Fixed screen size
                        }
                    })
                    .enableAnnotationRendering(true)
                    .invalidPageColor(Color.WHITE)
                    .load();
        }
    }

    public void deleteOnPdfClick(View view) {
        FavLab.get(this).deleteArticleFromFav(getIntent().getStringExtra("id"));
        Toast.makeText(this, "Article deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
