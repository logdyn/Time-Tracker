package com.mattihew;

import com.mattihew.dialogs.CreateIssueDialog;
import com.mattihew.model.IssueElement;
import com.mattihew.model.IssueList;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/main.fxml"));
        final Scene scene = new Scene(root, 300, -1);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Time Tracker");
        primaryStage.show();
    }

    @FXML
    private void initialize()
    {
        this.issueList = new IssueList(issueContainer.getChildren());
        this.issueContainer.setOnDragOver(e -> {
            if (e.getDragboard().hasString())
            {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });
        this.issueContainer.setOnDragDropped(e -> {
            try
            {
                if (e.getDragboard().hasUrl())
                {
                    URI uri = new URI(e.getDragboard().getUrl());
                    this.issueList.add(new IssueElement(
                            uri.getPath().substring(uri.getPath().lastIndexOf('/')+1),
                            uri));
                    e.setDropCompleted(true);
                }
                else if (e.getDragboard().hasString())
                {
                    this.issueList.add(new IssueElement(e.getDragboard().getString()));
                    e.setDropCompleted(true);
                }
                else
                {
                    e.setDropCompleted(false);
                }
            }
            catch (final IOException | URISyntaxException ex)
            {
                ex.printStackTrace();
            }

            e.consume();
        });
    }

    @FXML
    private void addNewIssue() throws IOException
    {
        CreateIssueDialog dialog = new CreateIssueDialog();
        Optional<IssueElement> result = dialog.showAndWait();
        result.ifPresent(i -> this.issueList.add(i));
    }

    @FXML
    private void stopTracking()
    {
        this.issueList.clearSelection();
    }

    @FXML
    private void clearIssues()
    {
        this.issueList.clearIssues();
    }
}
