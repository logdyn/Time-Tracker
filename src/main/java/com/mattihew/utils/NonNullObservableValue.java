package com.mattihew.utils;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class NonNullObservableValue<T> implements ObservableValue<T>
{
    private ObservableValue<T> value;
    private T nullValue;

    public NonNullObservableValue(final ObservableValue<T> value, final T nullValue)
    {
        this.value = value;
        this.nullValue = nullValue;
    }

    @Override
    public void addListener(final ChangeListener<? super T> listener)
    {
        this.value.addListener(listener);
    }

    @Override
    public void removeListener(final ChangeListener<? super T> listener)
    {
        this.value.removeListener(listener);
    }

    @Override
    public T getValue()
    {
        return this.value.getValue() == null ? this.nullValue : this.value.getValue();
    }

    @Override
    public void addListener(final InvalidationListener listener)
    {
        this.value.addListener(listener);
    }

    @Override
    public void removeListener(final InvalidationListener listener)
    {
        this.value.removeListener(listener);
    }
}
