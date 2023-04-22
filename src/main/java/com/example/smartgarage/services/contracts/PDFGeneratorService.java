package com.example.smartgarage.services.contracts;

import com.example.smartgarage.models.entities.Visit;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PDFGeneratorService {

    void export(HttpServletResponse response, Visit visit) throws IOException;


}
