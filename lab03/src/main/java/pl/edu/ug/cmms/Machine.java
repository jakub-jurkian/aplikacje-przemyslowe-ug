package pl.edu.ug.cmms;

import java.time.LocalDate;

public record Machine(
        String id,
        String name,
        MachineType machineType,
        String hall,
        LocalDate createdAt
) {

    public enum MachineType {
        CNC_LATHE,
        CONVEYOR,
        MILLING_MACHINE,
        WELDING_ROBOT,
        HYDRAULIC_PRESS,
        COMPRESSOR,
        INJECTION_MOLDER
    }
}
