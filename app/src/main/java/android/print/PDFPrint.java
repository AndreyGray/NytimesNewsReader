package android.print;

import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;

public class PDFPrint {

    private static final String TAG = PDFPrint.class.getSimpleName();
    private final PrintAttributes printAttributes;

    public PDFPrint(PrintAttributes printAttributes) {
        this.printAttributes = printAttributes;
    }

    public void print(final PrintDocumentAdapter printAdapter, final File path, final String fileName) {
        printAdapter.onLayout(
                null,
                printAttributes,
                null,
                new PrintDocumentAdapter.LayoutResultCallback() {
            @Override
            public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
                printAdapter.onWrite(
                        new PageRange[]{PageRange.ALL_PAGES},
                        getOutputFile(path, fileName),
                        new CancellationSignal(),
                        new PrintDocumentAdapter.WriteResultCallback() {
                    @Override
                    public void onWriteFinished(PageRange[] pages) {
                        super.onWriteFinished(pages);
                    }
                });
            }
        }, null);
    }

    private ParcelFileDescriptor getOutputFile(File path, String fileName) {
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open ParcelFileDescriptor", e);
        }
        return null;
    }
}
