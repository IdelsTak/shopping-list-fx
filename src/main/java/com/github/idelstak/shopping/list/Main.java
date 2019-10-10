/*
 Copyright 2019.
 */
package com.github.idelstak.shopping.list;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**

 @author Hiram K <hiram.kamau@outlook.com>
 */
public class Main extends Application {
    

    /**
     @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("ShoppingListFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
