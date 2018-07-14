package com.mattihew.servers;

import com.mattihew.dialogs.ServerDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class ServerController
{
    private Menu mnuServer;

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
            new ServerDialog().showAndWait();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
