package com.mattihew.model;

import com.mattihew.utils.IconController;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IssueList
{
    private final List<IssueElement> issues = new ArrayList<>();
    private final GridPane container;

    private int rows = 0;

    public IssueList(final GridPane nodes)
    {
        this.container = nodes;
        this.container.setOnDragOver(e -> {
            if (e.getDragboard().hasString())
            {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });
        this.container.setOnDragDropped(e -> {
            try
            {
                if (e.getDragboard().hasUrl())
                {
                    URI uri = new URI(e.getDragboard().getUrl());
                    this.add(new IssueElement(new Issue(
                            uri.getPath().substring(uri.getPath().lastIndexOf('/')+1),
                            uri)));
                    e.setDropCompleted(true);
                }
                else if (e.getDragboard().hasString())
                {
                    this.add(new IssueElement(new Issue(e.getDragboard().getString())));
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

    public boolean add(final IssueElement issue)
    {
        issue.getRemoveMenuItem().addEventHandler(ActionEvent.ACTION, e -> {
            this.removeIssue(issue);
            e.consume();
        });

        issue.getNodes().forEach(n -> n.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton().equals(MouseButton.PRIMARY))
            {
                this.issueSelected(issue);
                e.consume();
            }
        }));
        this.issueSelected(issue);

        this.container.add(issue.getIssueNode(), 0, rows);
        this.container.add(issue.getTimeNode(), 1, rows++);
        issue.getTimeNode().toBack();
        issue.getIssueNode().toBack();
        return this.issues.add(issue);
    }

    public List<IssueElement> getIssues()
    {
        return Collections.unmodifiableList(this.issues);
    }

    public void removeIssue(final IssueElement issue)
    {
        issue.deselect();
        this.container.getChildren().removeIf(i -> i.equals(issue.getIssueNode()) || i.equals(issue.getTimeNode()));
    }

    public void clearSelection()
    {
        this.clearSelection(null);
        IconController.setIcon((Stage) this.container.getScene().getWindow(), "red");
    }

    private void clearSelection(final IssueElement except)
    {
        this.issues.stream()
                .filter(i -> !i.equals(except))
                .forEach(IssueElement::deselect);
    }

    private void issueSelected(final IssueElement issue)
    {
        this.clearSelection(issue);
        issue.select();
        IconController.setIcon((Stage) this.container.getScene().getWindow(), "green");
    }

    public void clearIssues()
    {
        this.clearSelection();
        this.issues.clear();
        this.container.getChildren().removeIf(n -> n instanceof StackPane);
        this.rows = 0;
    }
}
