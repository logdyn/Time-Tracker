package com.mattihew.servers;

import com.mattihew.model.Issue;

import java.util.List;

public interface Server
{
    public String getName();

    public List<Issue> getIssues();
}
