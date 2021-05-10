package cs1302.omega;

import cs1302.api.Tools;
import java.util.Scanner;
import java.net.URL;
import java.io.InputStreamReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.net.MalformedURLException;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

/**
 * A class designed to find a single image from the itunes API
 * based on a search keyword.
 *
 */
public class MusicFinder {

    /**
     * A static helper method to find a url 
     * to query the iTunes API.
     *
     * @param query The search term
     * @return String The URL
     *
     */
    private static String getUrl(String query) {
        Scanner term = new Scanner(query);
        String searchURL = "https://itunes.apple.com/search?term=";
        // parses the search term and adds it to the URL
        while (term.hasNext()) {
            searchURL += term.next() + "+";
        } // while
        searchURL = searchURL.substring(0, searchURL.length() - 1);
        searchURL += "&limit=50&media=movie";
        return searchURL;
    } // getUrl

    /**
     * A static method that finds a single image based on a serch term.
     *
     * @param query The search term
     * @return String the address of the image
     */
    public static String findImage(String query) throws MalformedURLException, IOException {
        //converts a URL query to JSON elements
        String url = getUrl(query);
        JsonElement je = Tools.getJson(url);
        String out = "";
        boolean foundVid = false;
        for (int i = 0; i < 5; i ++) {
            try {
                if (Tools.get(je, "results", i, "artworkUrl100") != null) {
                    out = Tools.get(je, "results", i, "artworkUrl100").getAsString();
                    foundVid = true;
                    break;
                } // if
            } catch (IndexOutOfBoundsException ioobe) {
                break;
            } // try
        } // for
        // returns "error" if no trailer is found
        if (!foundVid) {
            out = "error";
        } // if
        return out;
    } // findVideos
} // MusicFinder
