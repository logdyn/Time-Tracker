package com.mattihew.servers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ServerFactory
{
    private static final List<ServerProvider> serverProviders = new ArrayList<>();

    static
    {
        serverProviders.add(new JiraServerProvider());
    }

    private ServerFactory()
    {
        throw new AssertionError("Constructor should not be called");
    }

    public static boolean isValidServer(final URI uri)
    {
        return serverProviders.stream()
                .anyMatch(provider -> provider.isHandler(uri));
    }

    public static String getDefaultServerName(final URI uri)
    {
        return serverProviders.stream()
                .filter(provider -> provider.isHandler(uri))
                .findFirst()
                .map(provider -> provider.getServerName(uri))
                .orElse(uri.toString());
    }

    public static boolean isAuthValid(final URI uri, final String username, final String password)
    {
        return serverProviders.stream()
                .filter(provider -> provider.isHandler(uri))
                .findFirst()
                .map(provider -> provider.isAuthenticated(uri, username, password))
                .orElse(false);
    }
}
