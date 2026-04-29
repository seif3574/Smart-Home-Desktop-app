package service;

import model.hub.DeviceEvent;
import util.EventBus;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public final class NotificationService {
    private static final int MAX_NOTIFICATIONS = 5;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final LinkedList<String> recentNotifications = new LinkedList<>();
    private final EventBus eventBus;

    public NotificationService() {
        this(EventBus.getInstance());
    }

    public NotificationService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void notify(String message) {
        if (message == null || message.isBlank()) {
            return;
        }
        if (recentNotifications.size() == MAX_NOTIFICATIONS) {
            recentNotifications.removeFirst();
        }
        recentNotifications.addLast(FORMATTER.format(java.time.LocalDateTime.now()) + " - " + message);
    }

    public List<String> getRecentNotifications() {
        return List.copyOf(recentNotifications);
    }

    public void publish(String eventType, Object payload) {
        eventBus.publish(new DeviceEvent("notification", eventType, payload, java.time.LocalDateTime.now()));
    }
}
