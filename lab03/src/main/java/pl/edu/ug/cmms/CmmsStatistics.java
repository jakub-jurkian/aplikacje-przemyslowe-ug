package pl.edu.ug.cmms;

import java.time.Duration;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CmmsStatistics {
    private final int top;

    public CmmsStatistics(int top) {
        if (top < 1) {
            throw new IllegalArgumentException("cmms.report.top must be positive");
        }
        this.top = top;
    }

    public ReportRecord createReport() {
        List<Machine> machines = SampleData.machines();
        List<WorkOrder> orders = SampleData.workOrders();

        return new ReportRecord(
                machines.size(),
                orders.size(),
                openOrders(orders),
                breakdownRanking(orders),
                mttrByHall(machines, orders)
        );
    }

    private Map<WorkOrder.Priority, Integer> openOrders(List<WorkOrder> orders) {
        Map<WorkOrder.Priority, Integer> result = new EnumMap<>(WorkOrder.Priority.class);

        for (WorkOrder.Priority priority : WorkOrder.Priority.values()) {
            int count = (int) orders.stream()
                    .filter(order -> order.priority() == priority)
                    .filter(order -> order.status() == WorkOrder.WorkOrderStatus.OPEN
                            || order.status() == WorkOrder.WorkOrderStatus.IN_PROGRESS)
                    .count();

            result.put(priority, count);
        }
        return result;
    }


    private List<ReportRecord.MachineBreakdown> breakdownRanking(List<WorkOrder> orders) {
        return orders.stream()
                .filter(order -> order.type() == WorkOrder.WorkOrderType.BREAKDOWN)
                .filter(order -> order.status() != WorkOrder.WorkOrderStatus.CANCELLED)
                .collect(Collectors.groupingBy(WorkOrder::machineId, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(top)
                .map(entry -> new ReportRecord.MachineBreakdown(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();
    }

    private Map<String, String> mttrByHall(List<Machine> machines, List<WorkOrder> orders) {

        Map<String, String> machineHalls = machines.stream()
                .collect(Collectors.toMap(Machine::id, Machine::hall));

        Map<String, List<WorkOrder>> byHall = orders.stream()
                .filter(order -> order.type() == WorkOrder.WorkOrderType.BREAKDOWN)
                .filter(order -> order.status() == WorkOrder.WorkOrderStatus.DONE)
                .collect(Collectors.groupingBy(order -> machineHalls.get(order.machineId())));

        Map<String, String> result = new TreeMap<>();
        byHall.forEach((hall, hallOrders) -> {
            Duration total = hallOrders.stream()
                    .map(order -> Duration.between(order.reportedAt(), order.closedAt()))
                    .reduce(Duration.ZERO, Duration::plus);
            result.put(hall, formatDuration(total.dividedBy(hallOrders.size())));
        });

        return result;
    }

    private static String formatDuration(Duration duration) {
        return duration.toHours() + " h " + duration.toMinutesPart() + " min";
    }
}
