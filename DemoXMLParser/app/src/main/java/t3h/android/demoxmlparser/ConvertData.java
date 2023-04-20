package t3h.android.demoxmlparser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ConvertData {
    private String getElementTagName, result;

    public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Entry> entryList = new ArrayList<>();

        // kiểm tra có phải thẻ mở hay không và mở thẻ feed
        parser.require(XmlPullParser.START_TAG, null, "feed");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            getElementTagName = parser.getName();
            if ("entry".equals(getElementTagName)) {
                entryList.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entryList;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }

    }

    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        Entry entry = new Entry();
        String title = null;
        String author = null;
        String link = null;
        // mở thẻ entry
        parser.require(XmlPullParser.START_TAG, null, "entry");

        while (parser.next() != XmlPullParser.END_TAG) {
            // nếu không phải thẻ mở thì bỏ qua
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            // nếu là thẻ mở thì tiến hành lấy data
            getElementTagName = parser.getName();
            if ("title".equals(getElementTagName)) {
                title = readTitle(parser);
            } else if ("author".equals(getElementTagName)) {
                author = readAuthor(parser);
            } else if ("link".equals(getElementTagName)) {
                link = readLink(parser);
            } else {
                skip(parser);
            }
        }
        entry.setTitle(title);
        entry.setAuthor(author);
        entry.setLink(link);
        return entry;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "link");
        String relType = parser.getAttributeValue(null, "rel");
        if ("alternate".equals(relType)) {
            result = parser.getAttributeValue(null, "href");
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, null, "link");
        return result;
    }

    private String readAuthor(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "author");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            getElementTagName = parser.getName();
            if ("name".equals(getElementTagName)) {
                result = getAuthorName(parser);
            } else {
                skip(parser);
            }
        }
        parser.require(XmlPullParser.END_TAG, null, "author");
        return result;
    }

    private String getAuthorName(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "name");
        result = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "name");
        return result;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "title");
        result = readText(parser);
        // kiểm tra có phải thẻ đóng không và đóng thẻ title
        parser.require(XmlPullParser.END_TAG, null, "title");
        return result;
    }

    private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
        // kiểm tra thành phần tiếp theo có phải là TEXT hay không
        if (parser.next() == XmlPullParser.TEXT) {
            // lấy text (content)
            result = parser.getText();
            // di chuyển con trỏ đến tag tiếp theo (tag đóng)
            parser.nextTag();
        }
        return result;
    }

}
