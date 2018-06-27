package com.mattihew.model;

import com.mattihew.ExecutionUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Future;

public class Issue
{
    private static Future<?> timerFuture;

    @FXML private Hyperlink lblIssue;

    @FXML private Region root;

    @FXML private Label lblTime;

    private final TimeTracker timeTracker;

    private final String issue;

    public Issue(final String issue) throws IOException
    {
        this.issue = issue;
        this.timeTracker = new TimeTracker();

        FXMLLoader.load(ClassLoader.getSystemResource("issue.fxml"), null, null, c -> this);
        this.root.getStylesheets().add("issue.css");
    }

    @FXML
    private void initialize()
    {
        this.lblIssue.setText(this.issue);
        this.lblIssue.prefWidthProperty().bind(this.root.widthProperty().divide(2));
    }

    public Region getRoot()
    {
        return this.root;
    }

    public void select()
    {
        this.root.getStyleClass().add("active");
        this.timeTracker.startTimer();

        if (Issue.timerFuture != null)
        {
            Issue.timerFuture.cancel(true);
        }

        Issue.timerFuture = ExecutionUtils.scheduleAtFixedRate(() -> Platform.runLater(() -> {
            LocalTime date = LocalTime.ofSecondOfDay(Issue.this.timeTracker.getDuration()/1000);
            String text = DateTimeFormatter.ISO_LOCAL_TIME.format(date);
            Issue.this.lblTime.setText(text);
        }), 1000);
    }

    public void deselect()
    {
        this.root.getStyleClass().remove("active");
        this.timeTracker.stopTimer();
    }

    @FXML
    public void click(final MouseEvent event)
    {
        this.select();
    }
}
