package com.mattihew.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IssueList
{
    private final List<IssueElement> issues = new ArrayList<>();
    private final ObservableList<Node> nodes;

    private int selectedIndex = -1;

    public IssueList(final ObservableList<Node> nodes)
    {
        this.nodes = nodes;
    }

    public boolean add(final IssueElement issue)
    {
        return this.nodes.add(issue.getRoot())
        && this.issues.add(issue);
    }

    public List<IssueElement> getIssues()
    {
        return Collections.unmodifiableList(this.issues);
    }

    public void clearSelection()
    {
        selectedIndex = -1;
        for (final IssueElement issue : this.issues)
        {
            issue.deselect();
        }
    }
}
