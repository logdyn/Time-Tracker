package com.mattihew;

import java.util.concurrent.*;

public class ExecutionUtils
{
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private ExecutionUtils()
    {
        throw new AssertionError();
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(final Runnable runnable, final long period)
    {
        return ExecutionUtils.executorService.scheduleAtFixedRate(runnable, period, period, TimeUnit.MILLISECONDS);
    }

    static void shutdown()
    {
        ExecutionUtils.executorService.shutdown();
    }
}
