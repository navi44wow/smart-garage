package com.example.smartgarage.models.enums;

import com.example.smartgarage.models.entities.VisitStatus;

public enum StatusCode {

    NOT_STARTED(new VisitStatus(1L, "Not Started")),
    IN_PROGRESS(new VisitStatus(2L, "In Progress")),
    COMPLETED(new VisitStatus(3L, "Completed"));
    private final VisitStatus status;

    StatusCode(VisitStatus status) {
        this.status = status;
    }

    public VisitStatus getStatus() {
        return status;
    }
}
