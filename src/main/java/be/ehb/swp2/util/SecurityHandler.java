package be.ehb.swp2.util;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;

/**
 * Created by arnaudcoel on 13/12/15.
 */
public class SecurityHandler {

    /**
     * Method to protect out input against evil people
     *
     * @param input Unescaped
     * @return Escaped tags
     */

    public static String stripTags(String input) {
        if (input == null)
            return input;

        return new HtmlToPlainText().getPlainText(Jsoup.parse(input));
    }

}
