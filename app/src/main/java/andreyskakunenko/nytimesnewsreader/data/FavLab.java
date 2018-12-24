package andreyskakunenko.nytimesnewsreader.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import andreyskakunenko.nytimesnewsreader.Model.Article;

import static andreyskakunenko.nytimesnewsreader.data.FavContract.*;

public class FavLab {
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
    private static FavLab sFavLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static FavLab get(Context context) {
            if (sFavLab == null) {
                sFavLab = new FavLab(context);
            }
            return sFavLab;
    }

    private FavLab(Context context) {
            mContext = context.getApplicationContext();
            mDatabase = new FavDBHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Article article) {
        ContentValues values = new ContentValues();
        values.put(FavTable.Cols.UUID, article.getId().toString());
        values.put(FavTable.Cols.ADDDATE,article.getAddDate().toString());
        values.put(FavTable.Cols.TITLE, article.getTitle());
        values.put(FavTable.Cols.AUTHOR, article.getAuthor());
        values.put(FavTable.Cols.SECTION,article.getSection());
        values.put(FavTable.Cols.DATE, article.getDate());
        values.put(FavTable.Cols.PICURL, article.getPicURL());
        values.put(FavTable.Cols.WEBURL,article.getWebURL());
        values.put(FavTable.Cols.CONTENT, article.getContent());
        return values;
    }

    public void addArticle(Article article) {
        ContentValues values = getContentValues(article);
        mDatabase.insert(FavTable.TABLE_NAME, null,values);
    }

    public List<Article> getArticles() {
            List<Article> articles = new ArrayList<>();
            ArticleCursorWrapper cursor = queryArticle(null,null);
            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    articles.add(cursor.getArticle());
                    cursor.moveToNext();
                }
            }finally {
                cursor.close();
            }
            return articles;
    }

    public Article getArticl(String id) {
        ArticleCursorWrapper cursor = queryArticle(
                FavTable.Cols.UUID + " =?",
                new String[]{id}
        );
        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getArticle();
        }finally {
            cursor.close();
        }
    }

    private ArticleCursorWrapper queryArticle(String whereClause, String[] whereArgs) {
            Cursor cursor = mDatabase.query(
                    FavTable.TABLE_NAME,
                    null, // columns - с null выбираются все столбцы
                    whereClause,
                    whereArgs,
                    null, // groupBy
                    null, // having
                    null // orderBy
            );
            return new ArticleCursorWrapper(cursor);
    }

    public void deleteArticleFromFav(String id){
        Article article = getArticl(id);
        mDatabase.delete(FavTable.TABLE_NAME,
                FavTable.Cols.UUID+" =?",
                new String[]{id});
        if(!article.getContent().isEmpty()) {
            File filename = new File(path, article.getContent());

            if(filename.exists()){
                filename.delete();
        }
        }
    }

    public boolean isExistInFav(String title){
        ArticleCursorWrapper cursor = queryArticle(
                FavTable.Cols.TITLE + " =?",
                new String[]{title}
        );
        try{
            if (cursor.getCount() == 0){
                return false;
            }
            cursor.moveToFirst();
            return true;
        }finally {
            cursor.close();
        }

    }

}
