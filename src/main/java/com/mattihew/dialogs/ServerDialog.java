package com.mattihew.dialogs;

import com.mattihew.servers.JiraServer;
import com.mattihew.servers.ServerFactory;
import com.mattihew.utils.IconController;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ServerDialog extends Dialog<JiraServer>
{
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUrl;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblAuth;

    @FXML
    private TextField txtJql;

    private final List<Node> nodes;

    private Service<String> nameGetter;

    public ServerDialog() throws IOException
    {
        this(null);
    }

    public ServerDialog(final JiraServer server) throws IOException
    {
        this.setDialogPane(FXMLLoader.load(this.getClass().getResource("/fxml/dialogs/ServerDialog.fxml"), null, null, c -> this)); //NON-NLS
        IconController.setIcon((Stage) this.getDialogPane().getScene().getWindow(), "white"); //NON-NLS
        this.setTitle("Server Dialog");

        nodes = Arrays.asList(txtName, txtUsername, txtPassword, txtJql, this.getDialogPane().lookupButton(ButtonType.OK));
        nodes.forEach(n -> n.setDisable(true));

        if (server != null)
        {
            final String[] auth = new String(Base64.getDecoder().decode(server.getAuth())).split(":");
            this.txtUrl.setText(server.getUrl());
            this.txtName.setText(server.getName());
            this.txtUsername.setText(auth[0]);
            this.txtPassword.setPromptText(auth[1]);
            this.lblAuth.setText("Auth: " + server.getAuth());
            this.txtJql.setText(server.getIssueJQL());
        }
        else
        {
            this.txtJql.setText("assignee = currentUser() AND resolution = Unresolved");
        }

        Platform.runLater(() -> txtUrl.requestFocus());
        txtUrl.textProperty().addListener((observable, oldValue, newValue) -> onURLChanged(oldValue, newValue));
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> this.updateAuthLabel());
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> this.updateAuthLabel());

        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try
            {
                if (!ServerFactory.isAuthValid(new URI(txtUrl.getText()), txtUsername.getText(), txtPassword.getText()))
                {
                    event.consume();
                    new Alert(Alert.AlertType.WARNING, "Username or password is incorrect").showAndWait();
                }
            }
            catch (final URISyntaxException e)
            {
                e.printStackTrace();
            }
        });
        this.setResultConverter(b -> {
            if (b.equals(ButtonType.OK))
            {
                return new JiraServer(txtUrl.getText(), txtName.getText(), genBasicAuth(txtUsername.getText(), txtPassword.getText()), txtJql.getText());
            }
            return null;
        });
    }

    @FXML
    private void initialize()
    {

    }

    private void updateAuthLabel()
    {
        lblAuth.setText("Auth: " + genBasicAuth(txtUsername.getText(), txtPassword.getText()));
    }

    private static String genBasicAuth(final String username, final String password)
    {
        return Base64.getEncoder().encodeToString((username + ':' + password).getBytes());
    }

    private void onURLChanged(final String oldValue, final String newValue)
    {
        if (nameGetter != null)
        {
            nameGetter.cancel();
        }
        nameGetter = new Service<String>()
        {
            @Override
            protected Task<String> createTask()
            {
                return new Task<String>()
                {
                    @Override
                    protected String call() throws URISyntaxException
                    {
                        final URI uri = new URI(newValue);
                        final boolean isServer = ServerFactory.isValidServer(uri);
                        return isServer && !isCancelled() ? ServerFactory.getDefaultServerName(uri) : null;
                    }
                };
            }
        };
        nameGetter.setOnSucceeded(event -> {
            nodes.forEach(n -> n.setDisable(nameGetter.getValue() == null));
            txtName.setText(nameGetter.getValue());
        });
        nameGetter.start();
    }
}
