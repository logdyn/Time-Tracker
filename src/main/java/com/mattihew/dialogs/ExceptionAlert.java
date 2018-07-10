package com.mattihew.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionAlert extends Alert
{
    private static final int WIDTH = 55;

    public ExceptionAlert(final Throwable t)
    {
        super(AlertType.ERROR, null, ButtonType.OK);
        this.initStyle(StageStyle.DECORATED);
        this.setTitle("Something went wrong.");

        if (t != null)
        {
            this.setHeaderText(t.getMessage());

            final StringWriter sw = new StringWriter(t.getStackTrace().length * WIDTH);
            final PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);

            final Label label = new Label("The Exception stacktrace was:");
            final TextArea textArea = new TextArea(sw.toString());
            label.setLabelFor(textArea);
            textArea.setPrefColumnCount(WIDTH);
            textArea.setEditable(false);

            this.getDialogPane().setExpandableContent(new VBox(label, textArea));
        }
    }
}
