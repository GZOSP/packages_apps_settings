package com.android.settings.gzosp;

import android.content.Context;
import android.provider.Settings;
import androidx.preference.SwitchPreference;
import android.util.AttributeSet;

public class SystemSettingSwitchPreference extends SwitchPreference {
    public SystemSettingSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SystemSettingSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SystemSettingSwitchPreference(Context context) {
        super(context, null);
    }

    @Override
    protected boolean persistBoolean(boolean value) {
        if (shouldPersist()) {
            if (value == getPersistedBoolean(!value)) {
                // It's already there, so the same as persisting
                return true;
            }
            Settings.System.putInt(getContext().getContentResolver(), getKey(), value ? 1 : 0);
            return true;
        }
        return false;
    }

    @Override
    protected boolean getPersistedBoolean(boolean defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        return Settings.System.getInt(getContext().getContentResolver(),
                getKey(), defaultReturnValue ? 1 : 0) != 0;
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setChecked(Settings.System.getString(getContext().getContentResolver(), getKey()) != null ? getPersistedBoolean(isChecked())
                : (Boolean) defaultValue);
    }
}
