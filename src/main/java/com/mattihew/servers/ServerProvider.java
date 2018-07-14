package com.mattihew.servers;

import java.net.URI;

public interface ServerProvider
{
    boolean isHandler(final URI url);

    String getServerName(final URI uri);

    boolean isAuthenticated(final URI uri, final String username, final String password);
}
