package com.mattihew.dialogs;

import com.mattihew.model.IssueElement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class CreateIssueDialog extends Dialog<IssueElement>
{
    @FXML private TextField txtIssueName;

    @FXML private TextField txtIssueUrl;

    public CreateIssueDialog() throws IOException
    {
        this.setTitle("Add Issue");
        this.setHeaderText("Add a new Issue");

        final Parent parent = FXMLLoader.load(
                ClassLoader.getSystemResource("fxml/dialogs/createIssueDialog.fxml"),
                null,
                null,
                c -> this);
        this.getDialogPane().setContent(parent);

        final URL imageURL = this.getClass().getResource("/com/sun/javafx/scene/control/skin/modena/dialog-confirm.png");
        if (imageURL != null)
        {
            this.setGraphic(new ImageView(imageURL.toString()));
        }

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Node btnOk = this.getDialogPane().lookupButton(ButtonType.OK);

        btnOk.disableProperty().bind(this.txtIssueName.textProperty().isEmpty());

        Platform.runLater(() -> this.txtIssueName.requestFocus());

        this.setResultConverter(b -> {
            try
            {
                return b == ButtonType.OK ? new IssueElement(txtIssueName.getText(), new URI(txtIssueUrl.getText())) : null;
            }
            catch (final IOException | URISyntaxException e)
            {
                e.printStackTrace();
                return null;
            }
        });
    }

    @FXML
    private void initialize()
    {
        this.txtIssueUrl.setOnDragOver(e -> {
            if (e.getDragboard().hasUrl())
            {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });
        this.txtIssueUrl.setOnDragDropped(e -> {
            if (e.getDragboard().hasUrl())
            {
                this.txtIssueUrl.setText(e.getDragboard().getUrl());
                e.setDropCompleted(true);
            }
            else
            {
                e.setDropCompleted(false);
            }
            e.consume();
        });
    }
}
