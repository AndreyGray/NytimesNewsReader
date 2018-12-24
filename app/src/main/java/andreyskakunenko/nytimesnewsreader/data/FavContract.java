package andreyskakunenko.nytimesnewsreader.data;

public class FavContract {
    public final static class FavTable{
        public static final String TABLE_NAME  = "favorites";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String ADDDATE = "adddate";
            public static final String TITLE = "title";
            public static final String AUTHOR = "author";
            public static final String SECTION = "section";
            public static final String DATE = "datepub";
            public static final String PICURL = "picture";
            public static final String WEBURL = "weburl";
            public static final String CONTENT = "content";
        }
    }
}
