package com.mattihew;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    @FXML
    private VBox issueContainer;

    public static void main(final String[] args)
    {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException
    {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("main.fxml"));
        final Scene scene = new Scene(root, 250, -1);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Time Tracker");
        primaryStage.show();
    }

    public void initialize() throws IOException
    {
        Node issue = FXMLLoader.load(ClassLoader.getSystemResource("issue.fxml"));
        Node issue2 = FXMLLoader.load(ClassLoader.getSystemResource("issue.fxml"));
        Node issue3 = FXMLLoader.load(ClassLoader.getSystemResource("issue.fxml"));
        issueContainer.getChildren().add(issue);
        issueContainer.getChildren().add(issue2);
        issueContainer.getChildren().add(issue3);
    }
}
