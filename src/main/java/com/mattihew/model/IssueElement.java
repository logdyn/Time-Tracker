package com.mattihew.model;

import com.mattihew.utils.NonNullObservableValue;
import com.mattihew.utils.TimerService;
import javafx.concurrent.ScheduledService;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class IssueElement
{
    private Hyperlink lblIssue;

    private Label lblTime;

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

        this.lblIssue = new Hyperlink(name);
        this.lblIssue.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickLink);
        this.lblIssue.setFont(new Font(24));
        this.lblIssue.setAlignment(Pos.CENTER);

        this.timeTracker = new TimeTracker();
        this.service = new TimerService(this.timeTracker);

        this.lblTime = new Label();
        this.lblTime.textProperty().bind(new NonNullObservableValue<>(service.lastValueProperty(),"0h 0m 0s"));
        this.lblTime.setFont(new Font(24));
        this.lblTime.setAlignment(Pos.CENTER);
    }

    public Hyperlink getIssueLabel()
    {
        return this.lblIssue;
    }

    public Label getTimeLabel()
    {
        return this.lblTime;
    }

    private void clickLink(final MouseEvent event)
    {
        if(this.url != null
                && !this.url.toString().isEmpty()
                && Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        {
            try
            {
                Desktop.getDesktop().browse(this.url);
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
        this.timeTracker.startTimer();
        this.service.restart();
    }

    public void deselect()
    {
        this.timeTracker.stopTimer();
        this.service.cancel();
    }
}
