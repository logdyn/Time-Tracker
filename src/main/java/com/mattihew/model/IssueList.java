package com.mattihew.model;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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
    }

    public boolean add(final IssueElement issue)
    {
        issue.getTimeLabel().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            this.issueSelected(issue);
            e.consume();
        });
        this.issueSelected(issue);

        this.container.add(new StackPane(issue.getIssueLabel()), 0, rows);
        this.container.add(new StackPane(issue.getTimeLabel()), 1, rows++);
        return this.issues.add(issue);
    }

    public List<IssueElement> getIssues()
    {
        return Collections.unmodifiableList(this.issues);
    }

    public void clearSelection()
    {
        this.clearSelection(null);
    }

    private void clearSelection(final IssueElement except)
    {
        this.issues.stream()
                .filter(i -> !i.equals(except))
                .forEach(IssueElement::deselect);
    }

    private void issueSelected(final IssueElement issue)
    {
        this.clearSelection();
        issue.select();
    }

    public void clearIssues()
    {
        this.clearSelection();
        this.issues.clear();
        this.container.getChildren().removeIf(n -> n instanceof StackPane);
        this.rows = 0;
    }
}
