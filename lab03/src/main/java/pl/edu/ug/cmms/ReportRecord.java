package pl.edu.ug.cmms;

import java.util.List;
import java.util.Map;

public record ReportRecord(
        int machineCount,
        int workOrderCount,
        Map<WorkOrder.Priority, Integer> openOrdersByPriority,
        List<MachineBreakdown> breakdownRanking,
        Map<String, String> mttrByHall) {

    public record MachineBreakdown(String id, long breakdowns) {
    }
}
