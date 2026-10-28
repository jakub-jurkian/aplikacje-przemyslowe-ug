package pl.edu.ug.cmms;

import java.time.LocalDateTime;
import java.util.Objects;

public record WorkOrder(
        String id,
        String machineId,
        WorkOrderType type,
        Priority priority,
        WorkOrderStatus status,
        LocalDateTime reportedAt,
        LocalDateTime closedAt
) {
    public enum WorkOrderType {
        PREVENTIVE,
        INSPECTION,
        BREAKDOWN
    }

    public enum Priority {
        CRITICAL,
        HIGH,
        NORMAL,
        LOW
    }

    public enum WorkOrderStatus {
        CANCELLED,
        DONE,
        IN_PROGRESS,
        OPEN
    }

    public WorkOrder {
        Objects.requireNonNull(id, "id cannot be null.");
        Objects.requireNonNull(status, "status cannot be null.");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be blank.");
        }
        if (status == WorkOrderStatus.DONE && closedAt == null) {
            throw new IllegalArgumentException("Closed date must exist for DONE order.");
        }
        if (closedAt != null && closedAt.isBefore(reportedAt)) {
            throw new IllegalArgumentException("Closed date cannot be before reported date.");
        }
    }
}
