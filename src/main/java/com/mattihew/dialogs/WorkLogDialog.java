package com.mattihew.dialogs;

import com.mattihew.model.WorkLog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;

public class WorkLogDialog extends Dialog<WorkLog>
{
    @FXML private TextField txtDuration;

    @FXML private TextField txtStartTime;

    private final WorkLog worklog;

    public WorkLogDialog(final String title, final WorkLog workLog) throws IOException
    {
        this.setTitle(title);
        this.worklog = workLog;

        this.setDialogPane(FXMLLoader.load(
                this.getClass().getResource("/fxml/dialogs/workLogDialog.fxml"),
                null,
                null,
                c -> this));
        this.setResultConverter(b -> this.worklog);
    }

    @FXML
    private void initialize()
    {
        this.txtDuration.setText(DurationFormatUtils.formatDuration(this.worklog.getDuration(), "HH'h' mm'm'"));
        this.txtStartTime.setText(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(this.worklog.getStartTime()));
    }
}
