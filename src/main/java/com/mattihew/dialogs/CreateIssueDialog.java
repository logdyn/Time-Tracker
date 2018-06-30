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

import java.io.IOException;

public class CreateIssueDialog extends Dialog<IssueElement>
{
    @FXML private TextField txtIssueName;

    public CreateIssueDialog() throws IOException
    {
        this.setTitle("Add Issue");
        this.setHeaderText("Add a new Issue");

        final Parent parent = FXMLLoader.load(
                ClassLoader.getSystemResource("dialogs/createIssueDialog.fxml"),
                null,
                null,
                c -> this);
        this.getDialogPane().setContent(parent);

        this.setGraphic(new ImageView(
                this.getClass().getResource("/com/sun/javafx/scene/control/skin/modena/dialog-confirm.png").toString()));

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Node btnOk = this.getDialogPane().lookupButton(ButtonType.OK);

        btnOk.disableProperty().bind(this.txtIssueName.textProperty().isEmpty());

        Platform.runLater(() -> this.txtIssueName.requestFocus());

        this.setResultConverter(b -> {
            try
            {
                return b == ButtonType.OK ? new IssueElement(txtIssueName.getText()) : null;
            }
            catch (final IOException e)
            {
                e.printStackTrace();
                return null;
            }
        });
    }
}
