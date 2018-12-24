package andreyskakunenko.nytimesnewsreader.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import andreyskakunenko.nytimesnewsreader.Model.Article;

import static andreyskakunenko.nytimesnewsreader.data.FavContract.*;

public class ArticleCursorWrapper extends CursorWrapper {

    public ArticleCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Article getArticle(){

        String uuidString = getString(getColumnIndex(FavTable.Cols.UUID));
        String addDate = getString(getColumnIndex(FavTable.Cols.ADDDATE));
        String title = getString(getColumnIndex(FavTable.Cols.TITLE));
        String author = getString(getColumnIndex(FavTable.Cols.AUTHOR));
        String section = getString(getColumnIndex(FavTable.Cols.SECTION));
        String date = getString(getColumnIndex(FavTable.Cols.DATE));
        String pictUrl = getString(getColumnIndex(FavTable.Cols.PICURL));
        String webUrl = getString(getColumnIndex(FavTable.Cols.WEBURL));
        String content = getString(getColumnIndex(FavTable.Cols.CONTENT));

        Article article = new Article(UUID.fromString(uuidString));
        article.setDate(addDate);
        article.setTitle(title);
        article.setAuthor(author);
        article.setSection(section);
        article.setDate(date);
        article.setPicURL(pictUrl);
        article.setWebURL(webUrl);
        article.setContent(content);

        return article;
    }
}
