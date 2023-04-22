package com.example.smartgarage.services;

import com.example.smartgarage.models.entities.Visit;
import com.example.smartgarage.services.contracts.PDFGeneratorService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {
    public void export(HttpServletResponse response, Visit visit) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Font fontDetails = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontDetails.setSize(12);

        Font bottomText = FontFactory.getFont(FontFactory.TIMES_ITALIC);
        fontDetails.setSize(8);

        Paragraph paragraph = new Paragraph("Invoice № "+visit.getId().toString(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph paragraph2 = new Paragraph("Details: ", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph username = new Paragraph("Customer: "+visit.getVehicle().getUser().getUsername());
        Paragraph vehicle = new Paragraph("Vehicle: " + "\n" +
                "Brand: " + visit.getVehicle().getCarModelId().getBrand().getBrandName() + "\n" +
                "Model: " + visit.getVehicle().getCarModelId().getModelName() + "\n" +
                "Licence plate: " + visit.getVehicle().getLicensePlate());
        Paragraph services = new Paragraph("Services: " + "\n" +
                visit.displayServices());
        Paragraph lineSeparator = new Paragraph("================================================================"+"\n");
        Paragraph sum = new Paragraph("Total amount: "+ visit.displaySum()+" BGN.");
        Paragraph space = new Paragraph("\n"+
                "\n"+
                "\n"+
                "\n"+
                "\n"+
                "\n"+
                "\n"+
                "\n"+
                "\n");
        Paragraph bottom = new Paragraph("\"Thank you for choosing us! " +"\n" +
                "We are committed to providing our customers with the highest level of service and expertise, " +"\n" +
                "and we take great pride in being the best car service around. " +"\n" +
                "If you have any questions or concerns, please don't hesitate to contact us. " +"\n" +
                "We appreciate your business and look forward to serving you again soon.");
        bottom.setAlignment(Element.ALIGN_BOTTOM | Element.ALIGN_RIGHT);
        bottom.setIndentationRight(50);

        document.add(paragraph);
        document.add(paragraph2);
        document.add(username);
        document.add(vehicle);
        document.add(services);
        document.add(lineSeparator);
        document.add(sum);
        document.add(space);
        document.add(bottom);

        document.close();
    }
}