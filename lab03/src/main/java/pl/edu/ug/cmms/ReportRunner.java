package pl.edu.ug.cmms;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.nio.file.Path;

public class ReportRunner implements ApplicationRunner {
    private final CmmsStatistics statistics;
    private final Path reportFile;

    public ReportRunner(CmmsStatistics statistics, String reportFile) {
        this.statistics = statistics;
        this.reportFile = Path.of(reportFile);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ReportRecord report = statistics.createReport();
        System.out.println("Machines: " + report.machineCount());
        System.out.println("Work orders: " + report.workOrderCount());
        report.openOrdersByPriority().forEach((priority, count) ->
                System.out.println(priority + ": " + count));
        report.breakdownRanking().forEach(machine ->
                System.out.println(machine.id() + " (" + machine.breakdowns() + ")"));
        report.mttrByHall().forEach((hall, mttr) -> System.out.println(hall + " " + mttr));
        XmlReportCodec.write(report, reportFile);
    }
}
