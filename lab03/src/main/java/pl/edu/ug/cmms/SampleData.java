package pl.edu.ug.cmms;

import static pl.edu.ug.cmms.Machine.MachineType.*;
import static pl.edu.ug.cmms.WorkOrder.Priority.*;
import static pl.edu.ug.cmms.WorkOrder.WorkOrderStatus.*;
import static pl.edu.ug.cmms.WorkOrder.WorkOrderType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import pl.edu.ug.cmms.WorkOrder.Priority;
import pl.edu.ug.cmms.WorkOrder.WorkOrderStatus;
import pl.edu.ug.cmms.WorkOrder.WorkOrderType;

/**
 * Starting data of the plant: 9 machines in halls A-C and 45 work orders (2026-03-02 2026-09-19).
 */
public final class SampleData {

    private SampleData() {
    }

    public static List<Machine> machines() {
        return List.of(
                new Machine("M-01", "Tokarka CNC 1", CNC_LATHE, "A", LocalDate.parse("2019-03-02")),
                new Machine("M-02", "Przenośnik taśmowy 1", CONVEYOR, "A", LocalDate.parse("2023-06-24")),
                new Machine("M-03", "Frezarka 1", MILLING_MACHINE, "A", LocalDate.parse("2014-04-20")),
                new Machine("M-04", "Robot spawalniczy 1", WELDING_ROBOT, "B", LocalDate.parse("2021-11-22")),
                new Machine("M-05", "Wtryskarka 1", INJECTION_MOLDER, "B", LocalDate.parse("2024-11-03")),
                new Machine("M-06", "Prasa hydrauliczna 1", HYDRAULIC_PRESS, "B", LocalDate.parse("2019-10-19")),
                new Machine("M-07", "Sprężarka 1", COMPRESSOR, "C", LocalDate.parse("2022-12-30")),
                new Machine("M-08", "Tokarka CNC 2", CNC_LATHE, "C", LocalDate.parse("2018-06-11")),
                new Machine("M-09", "Robot spawalniczy 2", WELDING_ROBOT, "C", LocalDate.parse("2013-09-17"))
        );
    }

    public static List<WorkOrder> workOrders() {
        return List.of(
                order("WO-0001", "M-06", PREVENTIVE, NORMAL, DONE, "2026-03-02T15:45", "2026-03-02T23:05"),
                order("WO-0002", "M-04", PREVENTIVE, NORMAL, DONE, "2026-03-06T06:45", "2026-03-06T11:45"),
                order("WO-0003", "M-06", PREVENTIVE, LOW, DONE, "2026-03-11T17:45", "2026-03-11T21:20"),
                order("WO-0004", "M-09", INSPECTION, LOW, DONE, "2026-03-15T17:30", "2026-03-15T19:20"),
                order("WO-0005", "M-09", PREVENTIVE, NORMAL, DONE, "2026-03-20T20:45", "2026-03-21T01:10"),
                order("WO-0006", "M-06", BREAKDOWN, CRITICAL, CANCELLED, "2026-03-24T19:30", null),
                order("WO-0007", "M-04", BREAKDOWN, CRITICAL, DONE, "2026-03-29T08:45", "2026-03-29T15:25"),
                order("WO-0008", "M-07", PREVENTIVE, LOW, DONE, "2026-04-02T18:45", "2026-04-02T21:45"),
                order("WO-0009", "M-07", BREAKDOWN, HIGH, DONE, "2026-04-07T21:45", "2026-04-08T06:45"),
                order("WO-0010", "M-02", BREAKDOWN, HIGH, DONE, "2026-04-11T09:30", "2026-04-12T03:35"),
                order("WO-0011", "M-08", BREAKDOWN, HIGH, DONE, "2026-04-16T19:30", "2026-04-17T13:00"),
                order("WO-0012", "M-09", BREAKDOWN, NORMAL, DONE, "2026-04-20T21:30", "2026-04-22T11:30"),
                order("WO-0013", "M-09", BREAKDOWN, CRITICAL, DONE, "2026-04-25T18:30", "2026-04-25T23:20"),
                order("WO-0014", "M-04", BREAKDOWN, HIGH, DONE, "2026-04-29T14:15", "2026-04-29T21:15"),
                order("WO-0015", "M-05", PREVENTIVE, LOW, DONE, "2026-05-04T19:15", "2026-05-04T22:00"),
                order("WO-0016", "M-06", BREAKDOWN, CRITICAL, DONE, "2026-05-08T21:15", "2026-05-09T01:05"),
                order("WO-0017", "M-05", BREAKDOWN, NORMAL, DONE, "2026-05-13T06:45", "2026-05-14T22:45"),
                order("WO-0018", "M-06", BREAKDOWN, NORMAL, DONE, "2026-05-17T13:00", "2026-05-18T15:00"),
                order("WO-0019", "M-07", INSPECTION, NORMAL, DONE, "2026-05-22T10:00", "2026-05-22T12:40"),
                order("WO-0020", "M-06", BREAKDOWN, HIGH, DONE, "2026-05-26T07:30", "2026-05-26T19:30"),
                order("WO-0021", "M-08", BREAKDOWN, NORMAL, DONE, "2026-05-31T09:15", "2026-06-01T14:05"),
                order("WO-0022", "M-03", PREVENTIVE, NORMAL, DONE, "2026-06-04T19:30", "2026-06-05T01:45"),
                order("WO-0023", "M-04", PREVENTIVE, NORMAL, DONE, "2026-06-09T21:00", "2026-06-10T03:00"),
                order("WO-0024", "M-05", INSPECTION, LOW, DONE, "2026-06-13T10:30", "2026-06-13T11:45"),
                order("WO-0025", "M-03", INSPECTION, NORMAL, DONE, "2026-06-18T21:15", "2026-06-18T23:15"),
                order("WO-0026", "M-05", BREAKDOWN, HIGH, DONE, "2026-06-22T13:15", "2026-06-22T23:35"),
                order("WO-0027", "M-04", BREAKDOWN, CRITICAL, DONE, "2026-06-27T09:45", "2026-06-27T12:15"),
                order("WO-0028", "M-03", BREAKDOWN, NORMAL, DONE, "2026-07-01T20:30", "2026-07-02T16:45"),
                order("WO-0029", "M-01", PREVENTIVE, NORMAL, DONE, "2026-07-06T21:00", "2026-07-07T01:00"),
                order("WO-0030", "M-01", BREAKDOWN, CRITICAL, DONE, "2026-07-10T14:00", "2026-07-10T17:15"),
                order("WO-0031", "M-01", PREVENTIVE, NORMAL, CANCELLED, "2026-07-15T18:45", null),
                order("WO-0032", "M-01", PREVENTIVE, LOW, DONE, "2026-07-19T15:45", "2026-07-19T17:55"),
                order("WO-0033", "M-08", PREVENTIVE, NORMAL, DONE, "2026-07-24T21:15", "2026-07-25T02:05"),
                order("WO-0034", "M-08", PREVENTIVE, NORMAL, DONE, "2026-07-28T18:45", "2026-07-29T00:25"),
                order("WO-0035", "M-08", BREAKDOWN, CRITICAL, DONE, "2026-08-02T19:45", "2026-08-02T22:05"),
                order("WO-0036", "M-04", BREAKDOWN, HIGH, DONE, "2026-08-06T12:15", "2026-08-07T09:15"),
                order("WO-0037", "M-06", INSPECTION, NORMAL, DONE, "2026-08-11T15:15", "2026-08-11T17:40"),
                order("WO-0038", "M-02", INSPECTION, LOW, DONE, "2026-08-15T14:30", "2026-08-15T16:00"),
                order("WO-0039", "M-02", PREVENTIVE, LOW, DONE, "2026-08-20T06:15", "2026-08-20T09:45"),
                order("WO-0040", "M-05", BREAKDOWN, CRITICAL, DONE, "2026-08-24T07:00", "2026-08-24T08:45"),
                order("WO-0041", "M-05", PREVENTIVE, LOW, OPEN, "2026-09-11T08:00", null),
                order("WO-0042", "M-04", BREAKDOWN, HIGH, IN_PROGRESS, "2026-09-15T08:00", null),
                order("WO-0043", "M-02", BREAKDOWN, HIGH, OPEN, "2026-09-17T10:15", null),
                order("WO-0044", "M-03", BREAKDOWN, NORMAL, OPEN, "2026-09-18T22:30", null),
                order("WO-0045", "M-08", BREAKDOWN, HIGH, OPEN, "2026-09-19T02:45", null)
        );
    }

    private static WorkOrder order(String id, String machineId, WorkOrderType type, Priority priority,
                                   WorkOrderStatus status, String reportedAt, String closedAt) {
        return new WorkOrder(id, machineId, type, priority, status, LocalDateTime.parse(reportedAt),
                closedAt == null ? null : LocalDateTime.parse(closedAt));
    }
}