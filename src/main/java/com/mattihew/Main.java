package com.mattihew;

import com.mattihew.model.IssueElement;
import com.mattihew.model.IssueList;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application
{
    @FXML private VBox issueContainer;

    private IssueList issueList;

    public static void main(final String[] args)
    {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException
    {
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("main.fxml"));
        final Scene scene = new Scene(root, 300, -1);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Time Tracker");
        primaryStage.show();
    }

    @FXML
    private void initialize() throws IOException
    {
        this.issueList = new IssueList(issueContainer.getChildren());
    }

    @FXML
    private void addNewIssue() throws IOException
    {
        TextInputDialog dialog = new TextInputDialog("issue name");
        dialog.setTitle("New Issue name");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter issue name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(n -> {
            try
            {
                this.issueList.add(new IssueElement(n));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });

    }
}
