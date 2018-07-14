package com.mattihew.servers;

import com.mattihew.dialogs.ServerDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServerController
{
    private Menu mnuServer;

    private List<JiraServer> servers = new ArrayList<>();

    public ServerController(final Menu mnuServer)
    {
        this.mnuServer = mnuServer;

        final MenuItem addServer = new MenuItem("Add", new Label("➕"));
        this.mnuServer.getItems().add(addServer);
        addServer.setOnAction(e -> this.addNewServer());
    }

    private void addNewServer()
    {
        try
        {
            final Optional<JiraServer> newServer = new ServerDialog().showAndWait();
            newServer.ifPresent(jiraServer -> {
                if (servers.isEmpty())
                {
                    this.mnuServer.getItems().add(new SeparatorMenuItem());
                }
                servers.add(jiraServer);

                final MenuItem remove = new MenuItem("Remove");
                final Menu menu = new Menu(jiraServer.getName());
                menu.setOnShowing(event -> {
                    menu.getItems().clear();
                    menu.getItems().addAll(remove, new SeparatorMenuItem());
                    menu.getItems().addAll(jiraServer.getIssues().stream().map(issue -> new MenuItem(issue.getName())).collect(Collectors.toList()));
                });

                menu.getItems().addAll(remove, new SeparatorMenuItem());
                this.mnuServer.getItems().add(menu);
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
