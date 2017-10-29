package ch34;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.SQLException;

import ch34.FindUserTables;

public class FindUserTablesUI extends Application {
	/**
	 * Public 'Stuff' for our UI/Program.
	 */
	public			FindUserTablesUI(){};
	public	static	String[]			mainString			= new String[4];
	/**
	 * Private 'Stuff' for our UI/Program.
	 */
	private			DoubleProperty		paddingWidth		= new SimpleDoubleProperty(15d);
	private			DoubleProperty		paddingLength		= new SimpleDoubleProperty(paddingWidth.get());
	private			DoubleProperty		masterWidth			= new SimpleDoubleProperty(600d);
	private			DoubleProperty		masterHeight		= new SimpleDoubleProperty(800d);
	private			DoubleProperty		textAreaWidth		= new SimpleDoubleProperty(((double) (masterWidth.get()  / 8) * 5));
	private			DoubleProperty		textAreaHeight		= new SimpleDoubleProperty(((double) (masterHeight.get() / 16) * 15));
	private			DoubleProperty		navBarWidth			= new SimpleDoubleProperty(masterWidth.get() - textAreaWidth.get());
	private			DoubleProperty		buttonNormalHeight	= new SimpleDoubleProperty(20d);
	private			DoubleProperty		buttonExitHeight	= new SimpleDoubleProperty(buttonNormalHeight.get() + 10d);
	private			DoubleProperty		buttonExitWidth		= new SimpleDoubleProperty(navBarWidth.get() / 2d);
	private			IntegerProperty		numAccordionPanes	= new SimpleIntegerProperty(3);
	private			StringProperty		textFontName		= new SimpleStringProperty("Tahoma");
	private			StringProperty		buttonFontName		= new SimpleStringProperty(textFontName.get());
	private			DoubleProperty		buttonFontSize		= new SimpleDoubleProperty(11d);
	private			DoubleProperty		buttonExitFontSize	= new SimpleDoubleProperty(buttonFontSize.get() + 3d);
	private			BackgroundFill		backFill			= new BackgroundFill(backColor, CornerRadii.EMPTY, Insets.EMPTY);
	private			Background			background			= new Background(backFill);
	private			BackgroundFill		backAccentFill		= new BackgroundFill(accentColor, CornerRadii.EMPTY, Insets.EMPTY);
	private			Background			backgroundAccent	= new Background(backAccentFill);
	private	static	Color				backColor			= Color.LIGHTGRAY;
	private static	Color				accentColor			= Color.DODGERBLUE;
	private static	Color				shadowColor			= Color.DARKGRAY;
	private	static	FontWeight			textFontWeight		= FontWeight.BLACK;
	private	static	FontPosture			textFontPosture		= FontPosture.ITALIC;
	private	static	FontWeight			buttonFontWeight	= textFontWeight;
	private	static	FontPosture			buttonFontPosture	= textFontPosture;
	
	@Override
	public void start(Stage mainStage) throws Exception {
		mainStage.setResizable	(false);
		mainStage.setTitle		("CineHub alpha v0.0.52");
		
		Button buttonExit		= makeButton("Exit", buttonExitWidth.get(), buttonExitHeight.get(), buttonFontName.get(), buttonFontWeight, buttonFontPosture, buttonExitFontSize.get());
		buttonExit.setTranslateX(masterWidth.get() - buttonExitWidth.get() - paddingWidth.get());
		
	    // Create and register the Exit button handler
	    buttonExit.setOnAction(new EventHandler<ActionEvent>() {
	      @Override // Override the handle method
	      public void handle(ActionEvent e) { Platform.exit(); }
	    });

	    Accordion optionsAccordion = makeAccordion(numAccordionPanes.get());
	    for(int i = 0; i < numAccordionPanes.get(); i++){
	    	switch(i){
	    	case 0:
		    	optionsAccordion.getPanes().get(i).setText("Tables Available");
		    	break;
	    	case 1:
	    		optionsAccordion.getPanes().get(i).setText("Functions Available");
		    	break;
	    	case 2:
	    		optionsAccordion.getPanes().get(i).setText("Procedures Available");
		    	break;
	    	}
	    	VBox newVbox = new VBox();
	    	for(int j = 0; j < mainString[i+1].substring(0, mainString[i+1].lastIndexOf("\n")).split("\n").length; j++){
	    		Button newButton = makeButton(mainString[i+1].substring(0, mainString[i+1].lastIndexOf("\n")).split("\n")[j], navBarWidth.get(),	buttonNormalHeight.get(),		buttonFontName.get(),	buttonFontWeight,	buttonFontPosture,	buttonFontSize.get());
	    		newButton.setAlignment(Pos.BASELINE_LEFT);
	    		newVbox.getChildren().add(newButton);
	    	}
	    	newVbox.setBackground(backgroundAccent);
	    	optionsAccordion.getPanes().get(i).setContent(newVbox);
	    }
	    optionsAccordion.setEffect(new DropShadow());
	    VBox buttons		= new VBox(optionsAccordion);
	    buttons.setEffect	(new DropShadow(5d, 3d, 3d, shadowColor));
	    
	    buttons.setSpacing	(paddingLength.get());
	    buttons.setPadding	(new Insets(paddingLength.get(), paddingWidth.get(), paddingLength.get(), paddingWidth.get()));
	    Pane freeSpace		= new Pane();
	    freeSpace.setPrefWidth(textAreaWidth.get());
	    freeSpace.setPrefHeight(textAreaHeight.get());
	    
		HBox root			= new HBox(freeSpace, buttons);
		root.setBackground(background);
		root.setMinHeight(textAreaHeight.get());
		
		VBox outerRoot		= new VBox(root, buttonExit);
		outerRoot.setBackground(background);
		
		mainStage.setScene	(new Scene(outerRoot, masterWidth.get(), masterHeight.get()));
		
		mainStage.show();
	}
	
	public static Button		makeButton(String buttonText, double width, double height, String textFont, FontWeight fontWeight, FontPosture fontPosture, double textSize){
		Font newTextFont			= Font.font(textFont, fontWeight, fontPosture, textSize);
		Button newButton 			= new Button();
		newButton.setMaxWidth		(width);
		newButton.setPrefWidth		(width);
		newButton.setMaxHeight		(height);
		newButton.setPrefHeight		(height);
		newButton.setText			(buttonText);
		newButton.setFont			(newTextFont);
		return newButton;
	}

	public static TextArea		makeText(String text, double width, double height, String textFont, FontWeight fontWeight, FontPosture fontPosture, double fontSize){
		Font newTextFont			= Font.font(textFont, fontWeight, fontPosture, fontSize);
		TextArea newText			= new TextArea();
		newText.setFocusTraversable	(false);
		newText.setEditable			(false);
		newText.setMaxWidth			(width);
		newText.setMaxHeight		(height);
		newText.setText				(text);
		newText.setFont				(newTextFont);
		newText.setVisible			(false);
		return newText;
	}

	public static TitledPane	makeTitledPane(String paneTitle, Node innerObject){
		return new TitledPane(paneTitle, innerObject);
	}
	
	public static Accordion		makeAccordion(){
		return new Accordion();
	}
	
	public static Accordion		makeAccordion(int numPanes){
		Accordion newAccordion		= makeAccordion();
		for(int i = 0; i < numPanes; i++) { newAccordion.getPanes().add(makeTitledPane("" + i, null)); }
		return newAccordion;
	}
	
	public static void 			main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
		mainString 	= FindUserTables.main(args);
		launch		(args);
		return;
	}
}
