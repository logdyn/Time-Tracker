package com.mattihew.model;

public class WorkLog
{
    private long startTime;
    private long duration;
    private String comment;

    public WorkLog(final long startTime, final long duration, final String comment)
    {
        this.startTime = startTime;
        this.duration = duration;
        this.comment = comment;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public long getDuration()
    {
        return duration;
    }

    public String getComment()
    {
        return comment;
    }
}
