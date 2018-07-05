package com.mattihew.utils;

import com.mattihew.model.TimeTracker;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class TimerService extends ScheduledService<String>
{
    private final TimeTracker timeTracker;

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
                return DurationFormatUtils.formatDuration(timeTracker.getDuration(), "HH'h' mm'm' ss's'", false);
            }
        };
    }
}
