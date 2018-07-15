package com.mattihew.servers;

import com.mattihew.model.Issue;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class JiraServer implements Server
{
    private final String url;

    private final String name;

    private final String auth;

    private final String issueJQL;

    public JiraServer(final String url, final String name, final String auth, final String issueJQL)
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

    @Override
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

    @Override
    public List<Issue> getIssues()
    {
        try
        {
            final URL statusURL = new URL(this.url + "/rest/api/2/search?jql=" + this.issueJQL.replace(" ", "%20"));
            final HttpURLConnection connection = (HttpURLConnection) statusURL.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + this.auth);
            try (final InputStream stream = connection.getInputStream())
            {
                final JSONObject json = new JSONObject(new JSONTokener(stream));
                return json.getJSONArray("issues").toList().stream()
                        .map(i -> {
                            final String key = ((Map) i).get("key").toString();
                            return new Issue(key, URI.create(this.url + "/browse/" + key));
                        }).collect(Collectors.toList());
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
