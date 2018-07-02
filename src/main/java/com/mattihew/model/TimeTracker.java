package com.mattihew.model;

public class TimeTracker
{
    private long firstStartTime;
    private long duration;
    private long currentStartTime;

    public TimeTracker()
    {
        this(-1, 0);
    }

    public TimeTracker(final long firstStartTime, final long duration)
    {
        this.firstStartTime = firstStartTime;
        this.duration = duration;
        this.currentStartTime = -1;
    }

    public long getFirstStartTime()
    {
        return this.firstStartTime;
    }

    public long startTimer()
    {
        this.duration += this.getCurrentDuration();
        this.currentStartTime = System.currentTimeMillis();
        if (this.firstStartTime == -1)
        {
            this.firstStartTime = this.currentStartTime;
        }
        return this.currentStartTime;
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
        return this.isStopped() ? 0 : (System.currentTimeMillis() - this.currentStartTime);
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

    public void subtractMillis(final long millis)
    {
        this.currentStartTime += millis;
    }

    public void addMillis(final long millis)
    {
        this.currentStartTime -= millis;
    }
}
