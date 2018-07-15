package mm.kb.com.mondaymorning;

public class ArticleStore {
    public static final int TEXT = 0;
    public static final int IMAGE = 1;
    public static final int TITLE=10;
    public static final int CATEGORY=11;

    public int type;
    public String imageURL = "";
    public String text;

    public ArticleStore(int type, String imageURL, String text) {
        this.type = type;
        this.imageURL += imageURL;
        this.text = text;
    }

    public ArticleStore(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getText() {
        return text;
    }
}
