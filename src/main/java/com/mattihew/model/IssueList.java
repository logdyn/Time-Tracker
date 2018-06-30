package com.mattihew.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IssueList
{
    private final List<IssueElement> issues = new ArrayList<>();
    private final ObservableList<Node> nodes;

    public IssueList(final ObservableList<Node> nodes)
    {
        this.nodes = nodes;
    }

    public boolean add(final IssueElement issue)
    {
        issue.getRoot().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.issueSelected(issue));
        this.issueSelected(issue);
        return this.nodes.add(issue.getRoot())
        && this.issues.add(issue);
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
        clearSelection();
        issue.select();
    }
}
