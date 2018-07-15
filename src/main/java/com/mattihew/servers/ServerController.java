package com.mattihew.servers;

import com.mattihew.dialogs.ServerDialog;
import com.mattihew.model.IssueElement;
import com.mattihew.model.IssueList;
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

    private IssueList issueList;

    private List<Server> servers = new ArrayList<>();

    public ServerController(final Menu mnuServer, final IssueList issueList)
    {
        this.mnuServer = mnuServer;
        this.issueList = issueList;

        final MenuItem addServer = new MenuItem("Add", new Label("➕"));
        this.mnuServer.getItems().add(addServer);
        addServer.setOnAction(e -> this.addNewServer());
    }

    private void addNewServer()
    {
        try
        {
            final Optional<Server> newServer = new ServerDialog().showAndWait();
            newServer.ifPresent(jiraServer -> {
                if (servers.isEmpty())
                {
                    this.mnuServer.getItems().add(new SeparatorMenuItem());
                }
                servers.add(jiraServer);

                final MenuItem edit = new MenuItem("Edit");
                final MenuItem remove = new MenuItem("Remove");
                final Menu menu = new Menu(jiraServer.getName());
                menu.setOnShowing(event -> {
                    menu.getItems().clear();
                    menu.getItems().addAll(edit, remove, new SeparatorMenuItem());
                    menu.getItems().addAll(jiraServer.getIssues().stream().map(issue -> {
                        final MenuItem item = new MenuItem(issue.getName());
                        item.setOnAction(e -> {
                            try
                            {
                                issueList.add(new IssueElement(issue));
                            }
                            catch (IOException e1)
                            {
                                e1.printStackTrace();
                            }
                        });
                        return item;
                    }).collect(Collectors.toList()));
                });

                menu.getItems().addAll(edit, remove, new SeparatorMenuItem());
                this.mnuServer.getItems().add(menu);
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
