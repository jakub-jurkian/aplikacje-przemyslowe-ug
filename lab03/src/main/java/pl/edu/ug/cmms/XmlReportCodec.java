package pl.edu.ug.cmms;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// klasa 'narzędziowa' - odpowiada za ręczną zamianę obiektu raportu na plik XML
// i odczyt pliku XML z powrotem do obiektu Javy.
// zamiast zew. bibl (Jackson) uzywa wbudowanego interfejsu DOM API
public final class XmlReportCodec {
    private XmlReportCodec() {
    }

    public static void write(ReportRecord report, Path file) throws Exception {
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        // tworzy pust dokument XML, dodaje glowny znacznik report i doczepia węzły
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = document.createElement("report");
        document.appendChild(root);
        addText(document, root, "machineCount", report.machineCount());
        addText(document, root, "workOrderCount", report.workOrderCount());
        Element open = document.createElement("openOrders");
        root.appendChild(open);
        report.openOrdersByPriority().forEach((priority, count) -> {
            Element item = document.createElement("priority");
            item.setAttribute("name", priority.name());
            item.setTextContent(String.valueOf(count));
            open.appendChild(item);
        });
        Element ranking = document.createElement("breakdownRanking");
        root.appendChild(ranking);
        report.breakdownRanking().forEach(machine -> {
            Element item = document.createElement("machine");
            item.setAttribute("id", machine.id());
            item.setAttribute("breakdowns", String.valueOf(machine.breakdowns()));
            ranking.appendChild(item);
        });
        Element mttr = document.createElement("mttr");
        root.appendChild(mttr);
        report.mttrByHall().forEach((hall, value) -> {
            Element item = document.createElement("hall");
            item.setAttribute("id", hall);
            item.setTextContent(value);
            mttr.appendChild(item);
        });

        // zamienia drzewo na fizyczny tekst w pliku XML
        var transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(document), new StreamResult(file.toFile()));
    }

    public static ReportRecord read(Path file) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file.toFile());
        Element root = document.getDocumentElement();
        Map<WorkOrder.Priority, Integer> open = new EnumMap<>(WorkOrder.Priority.class);
        var priorityNodes = root.getElementsByTagName("priority");
        for (int i = 0; i < priorityNodes.getLength(); i++) {
            Element item = (Element) priorityNodes.item(i);
            open.put(WorkOrder.Priority.valueOf(item.getAttribute("name")), Integer.parseInt(item.getTextContent()));
        }
        List<ReportRecord.MachineBreakdown> ranking = new ArrayList<>();
        var machineNodes = root.getElementsByTagName("machine");
        for (int i = 0; i < machineNodes.getLength(); i++) {
            Element item = (Element) machineNodes.item(i);
            ranking.add(new ReportRecord.MachineBreakdown(item.getAttribute("id"),
                    Long.parseLong(item.getAttribute("breakdowns"))));
        }
        Map<String, String> mttr = new LinkedHashMap<>();
        var hallNodes = root.getElementsByTagName("hall");
        for (int i = 0; i < hallNodes.getLength(); i++) {
            Element item = (Element) hallNodes.item(i);
            mttr.put(item.getAttribute("id"), item.getTextContent());
        }

        return new ReportRecord(Integer.parseInt(text(root, "machineCount")),
                Integer.parseInt(text(root, "workOrderCount")), open, ranking, mttr);
    }

    private static void addText(Document document, Element parent, String name, int value) {
        Element child = document.createElement(name);
        child.setTextContent(String.valueOf(value));
        parent.appendChild(child);
    }

    private static String text(Element root, String name) {
        return root.getElementsByTagName(name).item(0).getTextContent();
    }
}
