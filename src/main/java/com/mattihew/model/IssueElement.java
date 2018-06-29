package com.mattihew.model;

import com.mattihew.utils.NonNullObservableValue;
import com.mattihew.utils.TimerService;
import javafx.concurrent.ScheduledService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.io.IOException;

public class IssueElement
{
    @FXML private Hyperlink lblIssue;

    @FXML private Region root;

    @FXML private Label lblTime;

    private final TimeTracker timeTracker;

    private final String name;

    private final ScheduledService<String> service;

    public IssueElement(final String issue) throws IOException
    {
        this.name = issue;
        this.timeTracker = new TimeTracker();

        FXMLLoader.load(ClassLoader.getSystemResource("issue.fxml"), null, null, c -> this);
        this.root.getStylesheets().add("issue.css");
        this.service = new TimerService(this.timeTracker);
        this.lblTime.textProperty().bind(new NonNullObservableValue<>(service.lastValueProperty(),"0h 0m 0s"));
    }

    @FXML
    private void initialize()
    {
        this.lblIssue.setText(this.name);
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
