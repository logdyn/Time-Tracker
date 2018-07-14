package com.mattihew.servers;

import java.util.Base64;

public class Server
{
    private final String url;

    private final String name;

    private final String auth;

    private final String issueJQL;

    public Server(final String url, final String name, final String auth, final String issueJQL)
    {
        this.url = url;
        this.name = name;
        this.auth = auth;
        this.issueJQL = issueJQL;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getName()
    {
        return this.name;
    }

    public String getAuth()
    {
        return this.auth;
    }

    public String getIssueJQL()
    {
        return this.issueJQL;
    }
}
