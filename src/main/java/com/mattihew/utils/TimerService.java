package com.mattihew.utils;

import com.mattihew.model.TimeTracker;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class TimerService extends ScheduledService<String>
{
    private enum Format{
        DAYS("dd'd' HH'h' mm'm'"),
        SECS("HH'h' mm'm' ss's'");

        private String value;

        Format(final String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return this.value;
        }
    }

    private final TimeTracker timeTracker;
    private Format format = Format.SECS;

    public TimerService(final TimeTracker timeTracker)
    {
        this.timeTracker = timeTracker;
        this.setPeriod(Duration.seconds(1));
    }

    @Override
    protected Task<String> createTask()
    {
        return new Task<String>()
        {
            @Override
            protected String call()
            {
                return DurationFormatUtils.formatDuration(timeTracker.getDuration(), format.toString(), false);
            }
        };
    }

    @Override
    protected void succeeded()
    {
        super.succeeded();
        if (this.format == Format.SECS && this.timeTracker.getDuration() > DateUtils.MILLIS_PER_DAY)
        {
            format = Format.DAYS;
            this.setPeriod(Duration.minutes(1));
        }
        else if(this.format == Format.DAYS && this.timeTracker.getDuration() < DateUtils.MILLIS_PER_DAY)
        {
            format = Format.SECS;
            this.setPeriod(Duration.seconds(1));
        }
    }
}
