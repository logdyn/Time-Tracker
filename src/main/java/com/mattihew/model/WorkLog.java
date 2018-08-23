package com.mattihew.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkLog
{
    private TimeTracker timeTracker;
    private StringProperty comment;

    public WorkLog()
    {
        this(-1, 0, "");
    }

    public WorkLog(final long startTime, final long duration, final String comment)
    {
        this.timeTracker = new TimeTracker(startTime, duration);
        this.comment = new SimpleStringProperty(this, "comment", comment); //NON-NLS
    }

    public TimeTracker getTimeTracker()
    {
        return this.timeTracker;
    }

    public long getStartTime()
    {
        return this.timeTracker.getFirstStartTime();
    }

    public long getDuration()
    {
        return this.timeTracker.getDuration();
    }

    public StringProperty commentProperty() { return this.comment; }
    public String getComment() { return this.comment.get(); }
    public void setComment(final String comment) { this.comment.set(comment); }
}
