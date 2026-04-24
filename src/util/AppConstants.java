package util;

import java.util.List;

public final class AppConstants {
    public static final String APP_NAME = "SmartHome Controller";

    public static final double DEFAULT_TEMP = 22.0;
    public static final int MIN_TEMP = 16;
    public static final int MAX_TEMP = 30;

    public static final List<String> DEFAULT_ROOM_NAMES = List.of("Living Room", "Bedroom", "Kitchen");

    public static final String DEVICE_ADDED = "DEVICE_ADDED";
    public static final String DEVICE_STATUS_CHANGED = "DEVICE_STATUS_CHANGED";
    public static final String TEMP_CHANGED = "TEMP_CHANGED";

    private AppConstants() {
    }
}
