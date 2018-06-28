package com.mattihew.model;

import com.mattihew.utils.NonNullObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Issue
{
    @FXML private Hyperlink lblIssue;

    @FXML private Region root;

    @FXML private Label lblTime;

    private final TimeTracker timeTracker;

    private final String issue;

    private final ScheduledService<String> service;

    public Issue(final String issue) throws IOException
    {
        this.issue = issue;
        this.timeTracker = new TimeTracker();

        FXMLLoader.load(ClassLoader.getSystemResource("issue.fxml"), null, null, c -> this);
        this.root.getStylesheets().add("issue.css");
        this.service = new ScheduledService<String>()
        {
            @Override
            protected Task<String> createTask()
            {
                return new Task<String>()
                {
                    @Override
                    protected String call()
                    {
                        LocalTime date = LocalTime.ofSecondOfDay(Issue.this.timeTracker.getDuration()/1000);
                        return DateTimeFormatter.ISO_LOCAL_TIME.format(date);
                    }
                };
            }
        };
        this.service.setPeriod(Duration.millis(1000));
        this.lblTime.textProperty().bind(new NonNullObservableValue<>(service.lastValueProperty(),"00:00:00"));
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
        this.service.restart();
    }

    public void deselect()
    {
        this.root.getStyleClass().remove("active");
        this.timeTracker.stopTimer();
        this.service.cancel();
    }

    @FXML
    public void click(final MouseEvent event)
    {
        this.select();
    }
}
