package com.example.smartgarage.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class VisitFilterDto {
        private String username;

        private String status;
        private LocalDate visitFirstDate;

        private LocalDate visitLastDate;

        private LocalDate visitDate;

        private String sortBy;

        private String sortOrder;

        private boolean showArchived;

        public boolean getShowArchived() {
            return showArchived;
        }
    }
