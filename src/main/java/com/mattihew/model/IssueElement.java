package com.mattihew.model;

import com.mattihew.dialogs.WorkLogDialog;
import com.mattihew.utils.NonNullObservableValue;
import com.mattihew.utils.TimerService;
import javafx.concurrent.ScheduledService;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IssueElement
{
    //JavaFX elements
    private final Labeled lblIssue;

    private final StackPane stkIssue;

    private final Label lblTime;

    private final StackPane stkTime;

    private final MenuItem mnuRemove;

    //Other fields
    private final Issue issue;

    private final ScheduledService<String> service;

    public IssueElement(final Issue issue) throws IOException
    {
        this.issue = issue;

        this.service = new TimerService(this.issue.getTimeTracker());

        final MenuItem showWorkLog = new MenuItem("Show work-log");

        final MenuItem addMillis = new MenuItem("Add Milliseconds");
        this.mnuRemove = new MenuItem("Remove", new Label("×"));
        final ContextMenu contextMenu = new ContextMenu(showWorkLog, new SeparatorMenuItem(), addMillis, this.mnuRemove);

        showWorkLog.setOnAction(e -> {
            try
            {
                WorkLogDialog workLogDialog = new WorkLogDialog(
                        this.issue.getName(),
                        new WorkLog(
                                this.issue.getTimeTracker().getFirstStartTime(),
                                this.issue.getTimeTracker().getDuration(),
                                ""));
                workLogDialog.show();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        });

        addMillis.setOnAction(e -> {
            final TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("please only enter a number.");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(v -> this.issue.getTimeTracker().addMillis(Long.parseLong(v)));
        });

        if (this.issue.getUrl() != null && !this.issue.getUrl().toString().isEmpty())
        {
            this.lblIssue = new Hyperlink(this.issue.getName());
            this.lblIssue.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickLink);
        }
        else
        {
            this.lblIssue = new Label(this.issue.getName());
        }
        this.lblIssue.setFont(new Font(24));
        this.lblIssue.setAlignment(Pos.CENTER);

        this.stkIssue = new StackPane(this.lblIssue);
        this.stkIssue.addEventHandler(MouseEvent.MOUSE_ENTERED, this::hover);
        this.stkIssue.addEventHandler(MouseEvent.MOUSE_EXITED, this::hover);
        this.stkIssue.setOnContextMenuRequested(e -> contextMenu.show(this.stkIssue, e.getScreenX(), e.getScreenY()));

        this.lblTime = new Label();
        this.lblTime.textProperty().bind(new NonNullObservableValue<>(service.lastValueProperty(),"0h 0m 0s"));
        this.lblTime.setFont(new Font(24));
        this.lblTime.setAlignment(Pos.CENTER);

        this.stkTime = new StackPane(this.lblTime);
        this.stkTime.addEventHandler(MouseEvent.MOUSE_ENTERED, this::hover);
        this.stkTime.addEventHandler(MouseEvent.MOUSE_EXITED, this::hover);
        this.stkTime.setOnContextMenuRequested(e -> contextMenu.show(this.stkTime, e.getScreenX(), e.getScreenY()));
    }

    public Node getIssueNode()
    {
        return this.stkIssue;
    }

    public Node getTimeNode()
    {
        return this.stkTime;
    }

    public List<Node> getNodes()
    {
        return Arrays.asList(this.stkIssue, this.stkTime);
    }

    public MenuItem getRemoveMenuItem()
    {
        return this.mnuRemove;
    }

    private void hover(final MouseEvent event)
    {
        for (final Node n : this.getNodes())
        {
            this.setStyleClass(n, "hover", MouseEvent.MOUSE_ENTERED.equals(event.getEventType()));
        }
    }

    private void setStyleClass(final Node node, final String styleClass, final boolean add)
    {
        if (add && !node.getStyleClass().contains(styleClass))
        {
            node.getStyleClass().add(styleClass);
        }
        else if (!add && node.getStyleClass().contains(styleClass))
        {
            node.getStyleClass().remove(styleClass);
        }
    }

    private void clickLink(final MouseEvent event)
    {
        if(event.getButton().equals(MouseButton.PRIMARY)
                && this.issue.getUrl() != null
                && !this.issue.getUrl().toString().isEmpty()
                && Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        {
            try
            {
                Desktop.getDesktop().browse(this.issue.getUrl());
                event.consume();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void select()
    {
        this.getNodes().forEach(n -> this.setStyleClass(n, "active", true));
        this.issue.getTimeTracker().startTimer();
        this.service.restart();
    }

    public void deselect()
    {
        this.getNodes().forEach(n -> this.setStyleClass(n, "active", false));
        this.issue.getTimeTracker().stopTimer();
        this.service.cancel();
    }
}
