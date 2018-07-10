package com.mattihew;

import com.mattihew.dialogs.CreateIssueDialog;
import com.mattihew.dialogs.ExceptionAlert;
import com.mattihew.model.IssueElement;
import com.mattihew.model.IssueList;
import com.mattihew.utils.IconController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application
{
    @FXML private GridPane issueContainer;

    private IssueList issueList;

    public static void main(final String[] args)
    {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException
    {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> new ExceptionAlert(e).show());

        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/main.fxml"), null, null, c -> this);
        root.getStylesheets().add("fxml/main.css");
        final Scene scene = new Scene(root, 300, -1);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Time Tracker");
        primaryStage.show();
        IconController.setIcon(primaryStage, "white");

        final ScheduledService<Void> reminder = new ScheduledService<Void>()
        {
            @Override
            protected Task<Void> createTask()
            {
                return new Task<Void>()
                {
                    @Override
                    protected Void call()
                    {
                        Platform.runLater(() -> {
                            primaryStage.toFront();
                            //issueList.clearSelection();
                        });
                        return null;
                    }
                };
            }
        };
        reminder.setOnFailed(e ->e.getSource().getException().printStackTrace());
        reminder.setPeriod(Duration.minutes(30));
        //reminder.start();
    }

    @FXML
    private void initialize()
    {
        this.issueList = new IssueList(issueContainer);
    }

    @FXML
    private void addNewIssue() throws IOException
    {
        CreateIssueDialog dialog = new CreateIssueDialog();
        Optional<IssueElement> result = dialog.showAndWait();
        result.ifPresent(this.issueList::add);
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
