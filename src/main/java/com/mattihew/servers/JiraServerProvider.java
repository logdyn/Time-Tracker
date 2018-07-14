package com.mattihew.servers;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Base64;

public class JiraServerProvider implements ServerProvider
{
    private static final String STATUS_PATH = "/rest/api/2/serverInfo"; //NON-NLS

    private static final String SERVER_TITLE_KEY = "serverTitle"; //NON-NLS

    private static final String USER_PATH = "/rest/api/2/myself"; //NON-NLS

    private static final String USER_ACTIVE_KEY = "active"; //NON-NLS

    public JiraServerProvider()
    {

    }

    @Override
    public boolean isHandler(final URI uri)
    {
        if (uri.getScheme() == null || uri.getHost() == null)
        {
            return false;
        }
        try
        {
            final URL statusURL = new URL(uri.getScheme(), uri.getHost(), uri.getPort(), STATUS_PATH);
            try (final InputStream stream = statusURL.openStream())
            {
                final JSONObject json = new JSONObject(new JSONTokener(stream));
                return json.has(SERVER_TITLE_KEY);
            }
            catch (final IOException e)
            {
                //NOOP
            }
        }
        catch (final MalformedURLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getServerName(final URI uri)
    {
        if (uri.getScheme() == null || uri.getHost() == null)
        {
            return uri.toString();
        }
        try
        {
            final URL statusURL = new URL(uri.getScheme(), uri.getHost(), uri.getPort(), STATUS_PATH);
            try (final InputStream stream = statusURL.openStream())
            {
                final JSONObject json = new JSONObject(new JSONTokener(stream));
                return json.optString(SERVER_TITLE_KEY, uri.toString());
            }
            catch (final IOException e)
            {
                //NOOP
            }
        }
        catch (final MalformedURLException e)
        {
            e.printStackTrace();
        }
        return uri.toString();
    }

    @Override
    public boolean isAuthenticated(final URI uri, final String username, final String password)
    {
        if (uri.getScheme() == null || uri.getHost() == null)
        {
            return false;
        }
        try
        {
            final URL statusURL = new URL(uri.getScheme(), uri.getHost(), uri.getPort(), USER_PATH);
            final HttpURLConnection connection = (HttpURLConnection) statusURL.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ':' + password).getBytes()));
            try (final InputStream stream = connection.getInputStream())
            {
                final JSONObject json = new JSONObject(new JSONTokener(stream));
                return json.optBoolean(USER_ACTIVE_KEY);
            }
            catch (final IOException e)
            {
                //NOOP
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
