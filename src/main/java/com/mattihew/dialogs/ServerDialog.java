package com.mattihew.dialogs;

import com.mattihew.servers.Server;
import com.mattihew.utils.IconController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Base64;

public class ServerDialog extends Dialog<Server>
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

    public ServerDialog() throws IOException
    {
        this(null);
    }

    public ServerDialog(final Server server) throws IOException
    {
        this.setDialogPane(FXMLLoader.load(this.getClass().getResource("/fxml/dialogs/ServerDialog.fxml"), null, null, c -> this)); //NON-NLS
        IconController.setIcon((Stage) this.getDialogPane().getScene().getWindow(), "white"); //NON-NLS
        this.setTitle("Server Dialog");

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

        Platform.runLater(() -> txtUrl.requestFocus());
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> this.updateAuthLabel());
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> this.updateAuthLabel());
        this.setResultConverter(b -> {
            if (b.equals(ButtonType.OK))
            {
                return new Server(txtUrl.getText(), txtName.getText(), genBasicAuth(txtUsername.getText(), txtPassword.getText()), txtJql.getText());
            }
            else
            {
                return null;
            }
        });
    }

    private void updateAuthLabel()
    {
        lblAuth.setText("Auth: " + genBasicAuth(txtUsername.getText(), txtPassword.getText()));
    }

    private static String genBasicAuth(final String username, final String password)
    {
        return Base64.getEncoder().encodeToString((username + ':' + password).getBytes());
    }
}
