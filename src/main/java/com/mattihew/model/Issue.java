package com.mattihew.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.List;

public class Issue
{
    @FXML
    private Hyperlink lblIssue;

    @FXML
    private Region root;

    private TimeTracker timeTracker;


    private final String issue;

    public Issue(final String issue) throws IOException
    {
        this.issue = issue;
        this.timeTracker = new TimeTracker();

        FXMLLoader.load(ClassLoader.getSystemResource("issue.fxml"), null, null, c -> this);
        this.root.getStylesheets().add("issue.css");
    }

    public void initialize()
    {
        this.lblIssue.setText(this.issue);
        this.lblIssue.prefWidthProperty().bind(this.root.widthProperty().divide(2));
    }

    public Node getRoot()
    {
        return this.root;
    }

    @FXML
    public void click(MouseEvent e)
    {
        final List<String> classes = this.root.getStyleClass();
        final String styleClass = "active";

        if (classes.contains(styleClass))
        {
            classes.remove(styleClass);
        }
        else
        {
            classes.add(styleClass);
        }

    }
}
