package jwave.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import jwave.core.JWave;
import jwave.core.TrackList;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NormalizeGui extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Normalize");
		Scene scene = new Scene(new VBox(), 960, 480);
      stage.setScene(scene);
		stage.show();
	}	
		
}
