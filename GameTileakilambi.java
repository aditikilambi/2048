/*
 * Name: Aditi Kilambi, cse8bfbq
 * Date: December 3rd, 2017
 * File: GameTileakilambi.java
 * Sources of Help: Piazza, Stack Overflow
 * 
 * Creates a custom GameTile class that creates the tiles for the
 * 2048 game.
 * Extends the GameTile class created by the instructors for this course.
 * 
 */


//Imported some classes to resolve issue with Label


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;



/**
  * Class Header
  * 
  * A GameTile is a StackPane that groups together the 
  * visual items needed to display a 2048 tile.
  * 
  * We haven't talked about inheritance much yet, so 
  * the "extends" keyword might be a bit mysterious at this
  * point.  We'll help you through it in this assignment
  * and it will start to make much more sense over the next
  * few weeks.
  */

public class GameTileakilambi extends GameTile {


	//Hashmap that uses tilevalue as the key to access appropriate color
	//Check out the populateColors method that populates the HashMap
	private static HashMap<Integer, Color> colors = 
			new HashMap<Integer, Color>();


	public GameTileakilambi(int tileValue) {

		//calls the empty constructor
		super(); 
		
		// Default Color for Tiles is Yellow
		Color tileColor = Color.rgb(20,20,25);
		
		
		// If the color is a legal value (in the hash map), change it 
		// to blue
		if (colors.containsKey(tileValue)) {
			tileColor = colors.get(tileValue);
		}
		
		
		//Creating our circular tile c:
		Circle tile = new Circle(55, tileColor);
		
		
		//Initializing value label
		Label value = null;
		
		//Setting value label if it is not 0
		if (tileValue != 0){
			value = new Label(Integer.toString(tileValue));
			value.setFont(Font.font
					("Avenir", FontWeight.MEDIUM, FontPosture.REGULAR, 30));	
			value.setTextFill(Color.BLACK);
		}
       
		if (tileValue > 256) {
            value.setTextFill(Color.WHITE); //tile value color
        }

		//Adding tile to StackPane
		this.getChildren().add(tile);
		
		//Only adding tileValue to StackPane if it is not 0
		if (tileValue != 0) {
			this.getChildren().add(value);
		}
	}

	/* Name: populateColors() 
	 *
	 * Purpose: The purpose of this method is to populate the HashMap
	 * with RGB values pertaining to certain tileValues. For example,
	 * the tileValue 2 has an RGB value of (238, 228, 218). Therefore,
	 * if we want to access the color of tileValue 2 from the hashmap,
	 * we would say colors.get(2) and it would return the color object
	 * Color.rgb(238, 228, 218).
	 *
	 *
	 * Parameters: None
	 *
	 * Return: None
	 */
	
	public static void populateColors() {
		colors.put(0,Color.rgb(200, 200, 255, 0.1)); //empty tile
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
	
	public static GameTile makeNewTile(int tileValue) {
		GameTileakilambi newTile = new GameTileakilambi(tileValue);
		return newTile;
	}
	
	
}
