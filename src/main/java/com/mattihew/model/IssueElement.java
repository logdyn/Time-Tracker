package com.mattihew.model;

import com.mattihew.utils.NonNullObservableValue;
import com.mattihew.utils.TimerService;
import javafx.concurrent.ScheduledService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class IssueElement
{
    @FXML private Hyperlink lblIssue;

    @FXML private Region root;

    @FXML private Label lblTime;

    private final TimeTracker timeTracker;

    private final String name;

    private final URI url;

    private final ScheduledService<String> service;

    public IssueElement(final String issue) throws IOException
    {
        this(issue, null);
    }

    public IssueElement(final String issue, final URI url) throws IOException
    {
        this.name = issue;
        this.url = url;
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
        //this.lblTime.prefWidthProperty().bind(this.root.widthProperty().divide(2));
    }

    @FXML
    private void clickLink() throws IOException
    {
        if(!this.url.toString().isEmpty()
                && Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        {
            Desktop.getDesktop().browse(this.url);
        }
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
}
