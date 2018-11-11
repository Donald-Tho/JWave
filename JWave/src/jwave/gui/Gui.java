package jwave.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Gui extends Application{
	
	Button button;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("JWave 1.0");
		Scene scene = new Scene(new VBox(), 960, 480);
		
		MenuBar menuBar = new MenuBar();
		
		Menu menuFile = new Menu("File");
		
		MenuItem menuFileNewProject = new MenuItem("New JWave Project");
		menuFileNewProject.setOnAction(e -> browseForNewProject(stage));
		menuFile.getItems().add(menuFileNewProject);
		
		
		Menu menuEdit = new Menu("Edit");
		Menu menuEffects = new Menu("Effects");
		Menu menuPreferences = new Menu("Preferences");
		menuBar.getMenus().addAll(menuFile,menuEdit,menuEffects,menuPreferences);
		
		((VBox) scene.getRoot()).getChildren().addAll(menuBar);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void browseForNewProject(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Audio File");
		fileChooser.showOpenDialog(stage);
	}
}
