package io.openjob.server.alarm.channel;

import io.openjob.server.repository.entity.AlertRule;

import java.util.Locale;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public abstract class AbstractChannel implements AlarmChannel {

    /**
     * Get locale
     *
     * @param alertRule alertRule
     * @return Locale
     */
    protected Locale getLocale(AlertRule alertRule) {
        String locale = alertRule.getLocale();
        String[] splitLocale = locale.split("_");
        if (splitLocale.length != 2) {
            return Locale.US;
        }

        return new Locale(splitLocale[0], splitLocale[1]);
    }
}
