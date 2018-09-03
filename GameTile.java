import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.HashMap;

public class GameTile extends StackPane {

    //Hashmap that uses tilevalue as the key to access appropriate color
    //Check out the populateColors method that populates the HashMap
    static HashMap<Integer, Color> colors;

    public GameTile() {
        if (colors == null) {
            populateColors();
        }
    }

    public GameTile(int tileValue) {
        Rectangle tileBG = new Rectangle();
        tileBG.setWidth(100);
        tileBG.setHeight(100);

        Text value = new Text();

        if (tileValue == 0) value.setText("");
        else value.setText("" + tileValue);

        value.setFont(Font.font("Helvetica", FontWeight.BOLD, 25));
        if (tileValue > 256) {
            value.setFill(Color.WHITE); //tile value color
        }

        if (colors == null) {
            populateColors();
        }
        tileBG.setFill(((Color) colors.get(tileValue)));

        this.getChildren().add(tileBG);
        this.getChildren().add(value);
    }

    /**
     * Factory function to produce a new GameTile object.
     *
     * @param tileValue
     * @return
     */
    public static GameTile makeNewTile(int tileValue) {
        return new GameTile(tileValue);
    }

    /* Name: populateColors()
     *
     * Purpose: The purpose of this method is to populate the HashMap
     * with RGB values pertaining to certain tileValues. For example,
     * the tileValue 2 has an RGB value of (238, 228, 218). Therefore,
     * if we want to access the color of tileValue 2 from the hashmap,
     * we would say colors.get(2) and it would return the color object
     * Color.rgb(238, 228, 218)
     *
     * Parameters: None
     *
     * Return: None
     */
    public static void populateColors() {
        colors = new HashMap<>();
        colors.put(0, Color.rgb(238, 228, 218, 0.35)); //empty tile
        colors.put(2, Color.rgb(238, 228, 218));
        colors.put(4, Color.rgb(237, 224, 200));
        colors.put(8, Color.rgb(242, 177, 121));
        colors.put(16, Color.rgb(245, 149, 99));
        colors.put(32, Color.rgb(246, 124, 95));
        colors.put(64, Color.rgb(246, 94, 59));
        colors.put(128, Color.rgb(237, 207, 114));
        colors.put(256, Color.rgb(237, 204, 97));
        colors.put(512, Color.rgb(237, 200, 80));
        colors.put(1024, Color.rgb(237, 197, 63));
        colors.put(2048, Color.rgb(237, 194, 46));
        colors.put(4096, Color.rgb(237, 194, 46));
        colors.put(8192, Color.rgb(237, 194, 46));
    }

}
