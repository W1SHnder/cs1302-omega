package cs1302.omega;

import cs1302.game.DemoGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import cs1302.omega.MovieSearch;
import java.io.FileNotFoundException;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import cs1302.omega.MovieList;

/**
 * The OmegaApp will create a list of the top 10 results for movies based on a search term 
 * entered a labeled search box.
 * If a movie is selected, and the "Show Info" button is clicked, a popup window will appear
 * containing infomation on the movie from the TMDB API, and a poster for the movie found
 * in the iTunes API
 * If no results are found for a given query, a dialogue box with an error message will appear.
 * If no poster is found for a given movie, a default image will display in it's place.
 * The program can be exited with the "exit" option in the "File" menu at the top of the window. 
 * 
 */
public class OmegaApp extends Application {

    static Stage parentStage;

    /**
     * Start method for the {@code OmegaApp}.
     *  
     * @param stage The primary {@code Stage} object of the application
     */
    public void start(Stage stage) {
        parentStage = stage; // reference to main stage
        VBox view = new VBox();
        MovieList movieList = new MovieList();
        // creates the menu
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        menuBar.getMenus().add(file);
        file.getItems().add(exit);
        exit.setOnAction(e -> System.exit(0));
        // adds nodes to VBox
        view.getChildren().add(menuBar);
        view.getChildren().add(movieList);
        Scene scene = new Scene(view, 500, 475);
        // sets stage preferences
        stage.setMaxWidth(1280);
        stage.setMaxHeight(720);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    } // start

    /**
     * A method that returns a reference to the 
     * application's parent stage.
     *
     * @return Stage The parent stage. 
     */
    public static Stage getParent() {
        return parentStage;
    } // getParent
    
} // OmegaApp
