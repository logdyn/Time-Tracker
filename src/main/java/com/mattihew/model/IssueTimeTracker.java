package com.mattihew.model;

public class IssueTimeTracker
{
    private final String issue;
    private final long firstStartTime;
    private long currentStartTime;
    private long duration;

    public IssueTimeTracker(final String issue, final long startTime)
    {
        this.issue = issue;
        this.firstStartTime = startTime;
        this.currentStartTime = -1;
        this.duration = 0;
    }

    public String getIssue()
    {
        return this.issue;
    }

    public long getFirstStartTime()
    {
        return this.firstStartTime;
    }

    public long startTimer()
    {
        return this.currentStartTime = System.currentTimeMillis();
    }

    public boolean isStopped()
    {
        return this.currentStartTime == -1;
    }

    public long stopTimer()
    {
        this.duration += this.getCurrentDuration();
        this.currentStartTime = -1;
        return this.duration;
    }

    private long getCurrentDuration()
    {
        return this.isStopped() ? 0L : (System.currentTimeMillis() - this.currentStartTime);
    }

    public long getDuration()
    {
        return this.getDuration(true);
    }

    public long getDuration(final boolean includeCurrent)
    {
        long result = this.duration;
        if(includeCurrent)
        {
            result += this.getCurrentDuration();
        }
        return result;
    }
}
