package cs1302.omega;


import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import cs1302.omega.MovieList;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;


/**
 * A javafx custom component that displays 
 * information on a movie passed to it.
 *
 */
public class MovieInfo extends VBox {

    ImageView imgView;
    Text title;
    Label overview;
    Label titleLabel;
    Label overviewLabel;
    Label date;
    Label dateLabel;
    HBox dateRow;
    HBox titleRow;
    VBox overviewBox;

    /**
     * The custom component constructor.
     *
     * @param titleParam The title of the movie
     * @param overviewParam A description of the movie
     * @param releaseDate The release date of the movie
     * @param poster A cover image of the movie
     *
     */
    public MovieInfo(String titleParam, String overviewParam, String releaseDate, Image poster) {
        super();

        ImageView image = new ImageView(poster);
        title = new Text(titleParam);
        overview = new Label(overviewParam);
        titleLabel = new Label("Title: ");
        overviewLabel = new Label("Overview:");
        date = new Label(releaseDate);
        dateLabel = new Label("Release Date: ");
        dateRow = new HBox(10);
        dateRow.getChildren().addAll(dateLabel, date);
        Font font = Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 12);
        titleLabel.setFont(font);
        overviewLabel.setFont(font);
        dateLabel.setFont(font);
        overview.setWrapText(true);
        overview.setTextAlignment(TextAlignment.JUSTIFY);
        overview.setMaxWidth(400);
        titleRow = new HBox(10);
        overviewBox = new VBox();
        overviewBox.getChildren().addAll(overviewLabel, overview);
        titleRow.getChildren().addAll(titleLabel, title);

        this.setSpacing(5);
        this.getChildren().addAll(image, titleRow, overviewBox, dateRow);

    } // MovieInfo
    
} // MovieInfo
