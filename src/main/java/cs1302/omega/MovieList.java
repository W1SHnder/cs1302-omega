package cs1302.omega;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import java.net.MalformedURLException;
import java.io.IOException;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import cs1302.omega.MovieSearch;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A custom component that displays a list of movies 
 * based on a search query and gives the user options
 * to enter a new query or display information on 
 * the various movies. 
 *
 */
public class MovieList extends VBox {

    HBox toolbar;
    Button search;
    Button dispInfo;
    TextField searchBar;
    Text t;
    Alert errorMsg;
    ListView<String> movies;
    String[][] info;
    ObservableList<String> titles;
    HBox options;
    static final String URL = "https://archive.ica.art/sites/default/files/styles/banner-landscape/public/images/WEBno%20image%20%281%29.jpg?itok=Lx57-wSo";
    Image image = new Image(URL, 200, 200, false, false);

    /**
     * The custom component constructor.
     *
     */
    public MovieList() {
        super();

        // instantiates instance variables
        titles = FXCollections.observableArrayList();
        movies = new ListView<String>(titles);
        movies.setMaxSize(500, 300);
        toolbar = new HBox(10);
        HBox options = new HBox(10);
        dispInfo = new Button("Show Info");
        search = new Button("Search");
        options.getChildren().add(dispInfo);
        searchBar = new TextField("godfather");
        t = new Text();
        t.setText("Keywords:");
        errorMsg = new Alert(AlertType.ERROR, "No results for keyword");
        errorMsg.setResizable(true);    
        // adds nodes to the toolbar
        toolbar.getChildren().addAll(t, searchBar, search);

        // ties buttons to event handlers
        search.setOnAction(this::loadMovies);
        dispInfo.setOnAction(this::showInfo);
 
        //fires the neccessary buttons to achieve the application's default state.
        search.fire();

        //Adds everything to the VBox
        this.getChildren().addAll(toolbar, movies, options);
    } // MovieList

    /**
     * A method that loads a new list of movies 
     * based on the contents of the search bar.
     *
     * @param e The Action event from the search button
     *
     */ 
    private void loadMovies(ActionEvent e) {
        runNow(() -> {
            try {
                // finds movies related to search term and fetches info to array
                info = MovieSearch.getInfo(searchBar.getText());
                // disables search button while images are loading
                search.setDisable(true);
                if (info[0][0].equals("error")) {
                    // shows an error dialogue if no results are found
                    Platform.runLater(() -> errorMsg.show());
                } else {
                    updateMovies(); // makes a list of movies if enough are found
                } // if
                search.setDisable(false);
            } catch (MalformedURLException mue) {
                ;
            } catch (IOException ioe) {
                ;
            } // try
        });
    } // loadMovies

    /** 
     * A helper method that loads an image from a URL.
     *
     * @param address The URL
     *
     */
    private void loadImage(String address) {
        image = new Image(address, 200, 200, false, false);
    } // loadVideos

    /**
     * A method that updates the list of movies displayed 
     * in the component's {@code ListView} object.
     *
     */
    private void updateMovies () {
        Platform.runLater(() -> clearList());
        for (int i = 0; i < 10; i ++) {
            if (info[i][0] != null) {
                final int temp = i;
                Platform.runLater(() -> addToList(temp));
            } // if
        } // for

    } // updateMovies

    /**
     * A helper method that clears the list of movies.
     *
     */
    private void clearList() {
        titles.remove(0, titles.size());
    } // clearList

    /**
     * A helper method that adds an item to 
     * the list of movies. 
     *
     * @param n The index of the movie title to add to the list
     *
     */
    private void addToList(int n) {
        titles.addAll(info[n][0]);
    } // addToList

    /**
     * A method that triggers a popup 
     * to appear with information on the 
     * movie selected by the user.
     *
     * @param e The action event from the show info button
     * 
     */   
    private void showInfo(ActionEvent e) {
        ObservableList<Integer> selectedIndices = movies.getSelectionModel().getSelectedIndices();
        int selected = -1;
        for (Integer o: selectedIndices) {
            selected = Integer.valueOf(o);
        } // for
        try {
            Stage panel = new Stage();
            final String tempQuery = info[selected][0];
            try { 
                loadImage(MusicFinder.findImage(tempQuery));
            } catch (MalformedURLException mue) {
                ;
            } catch (IOException ioe) {
                ;
            } // try

            MovieInfo mi =
                new MovieInfo(info[selected][0], info[selected][1], info[selected][2], image);
            Scene panelScene = new Scene(mi);
    
            panel.setMaxWidth(1280);
            panel.setMaxHeight(720);
            panel.setTitle(info[selected][0]);
            panel.setScene(panelScene);
            panel.initOwner(OmegaApp.getParent());
            panel.initModality(Modality.APPLICATION_MODAL);
            panel.show();
        } catch (IndexOutOfBoundsException ioobe) {
            ;
        } catch (NullPointerException npe) {
            ;
        } // try
    } // showInfo

    /**
     * A helper method that creates a new 
     * thread of executions for the param.
     * @param target The runnable to be executed on the Daemon thread
     *
     */
    private static void runNow(Runnable target) {
        Thread t = new Thread(target);
        t.setDaemon(true);
        t.start();
    } // runNow

} // MovieList
