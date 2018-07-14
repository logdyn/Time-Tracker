package com.mattihew.model;

import java.net.URI;

public class Issue
{
    private final String name;

    private final URI url;

    private final TimeTracker timeTracker;

    public Issue(final String name)
    {
        this(name, null);
    }

    public Issue(final String name, final URI url)
    {
        this.name = name;
        this.url = url;
        this.timeTracker = new TimeTracker();
    }

    public String getName()
    {
        return this.name;
    }

    public URI getUrl()
    {
        return url;
    }

    public TimeTracker getTimeTracker()
    {
        return timeTracker;
    }
}
