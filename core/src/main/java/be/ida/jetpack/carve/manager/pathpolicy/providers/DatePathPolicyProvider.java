package be.ida.jetpack.carve.manager.pathpolicy.providers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Persists the model under a date and timestamp based path using the following yyyy/MM/dd/HH/mm
 *
 */
public class DatePathPolicyProvider implements PathPolicyProvider {

    private static final String DATE_FORMAT = "yyyy/MM/dd/HH/mm";

    @Override
    public String apply(String id) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String formatDateTime = now.format(formatter);
        return formatDateTime + SEPERATOR + id;
    }
}
