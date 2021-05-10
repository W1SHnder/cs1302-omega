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
 * A class designed to find a list of 10 movies based on a search query.
 *
 */
public class MovieSearch {

    /**
     * A helper method that generates a URL to query the TMDB api.
     *
     * @param query The search term
     * @return String the URL
     *
     */
    private static String getUrl(String query) {
        Scanner term = new Scanner(query);
        String searchURL = "https://api.themoviedb.org/3/search/movie?api_key=afd6de92c4433483899c49ec2ebbdc8c&language=en-US&query=";
        // parses the search term and adds it to the URL
        while (term.hasNext()) {
            searchURL += term.next() + "%20";
        } // while
        searchURL = searchURL.substring(0, searchURL.length() - 3);
        return searchURL;
    } // getUrl

    /**
     * A method that finda a list of 10 movies based on a search query.
     *
     * @param query The search term
     * @return String[][] The list of movies
     *
     */
    public static String[][] getInfo(String query) throws MalformedURLException, IOException {
        // converts a URL query to JSON elements
        String url = getUrl(query);
        JsonElement je = Tools.getJson(url);
        String[][] info = new String[10][3];
        boolean resultFound = false;
        for (int i = 0; i < 10; i ++) {
            boolean isUnique = true;
            try {
                for (int j = 0; j < info.length; j ++) {
                    if (Tools.get(je, "results", i, "title").getAsString().equals(info[j][0])) {
                        isUnique = false;
                    } // if
                } // for
                if (Tools.get(je, "results", i, "title") != null && isUnique) {
                    info[i][0] = Tools.get(je, "results", i, "title").getAsString();
                    resultFound = true;
                } // if
                if (Tools.get(je, "results", i, "overview") != null && isUnique) {
                    info[i][1] = Tools.get(je, "results", i, "overview").getAsString();
                } else {
                    info[i][1] = "No overview located";
                } // if
                if (Tools.get(je, "results", i, "release_date") != null && isUnique) {
                    info[i][2] =
                        Tools.get(je, "results", i, "release_date").getAsString();
                } else {
                    info[i][2] = "not found";
                } // if
            } catch (IndexOutOfBoundsException ioobe) {
                break;
            } // try
        } // for
        // passes an error if no results are located
        if (!resultFound) {
            info = new String[1][1];
            info[0][0] = "error";
        } // if
        return info;
    } // getInfo

} // MovieSearch
