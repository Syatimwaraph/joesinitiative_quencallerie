package dev.syatimwaraph.quencallerie_mngt_v1.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class InventoryPdfService {

    private List<Product> products;

    public InventoryPdfService(List<Product> products) {
        this.products = products;
    }

    public void export(HttpServletResponse response)
            throws IOException {

        Document document =
                new Document(PageSize.A4);

        PdfWriter.getInstance(document,
                response.getOutputStream());

        document.open();

        // TITLE
        Font titleFont = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                18,
                Color.BLACK
        );

        Paragraph title =
                new Paragraph(
                        "Inventory Requisition Report",
                        titleFont
                );

        title.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(title);

        document.add(new Paragraph(" "));

        // TABLE
        PdfPTable table =
                new PdfPTable(5);

        table.setWidthPercentage(100);

        table.setWidths(new float[]{
                1.5f,
                3.5f,
                2.5f,
                2.0f,
                2.0f
        });

        // HEADER
        addTableHeader(table);

        // DATA
        addRows(table);

        document.add(table);

        document.close();
    }

    private void addTableHeader(PdfPTable table) {

        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.DARK_GRAY);

        cell.setPadding(5);

        Font font =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD
                );

        font.setColor(Color.WHITE);

        String[] headers = {
                "ID",
                "Product",
                "Category",
                "Stock",
                "Alert Level"
        };

        for (String header : headers) {

            cell.setPhrase(new Phrase(header, font));

            table.addCell(cell);
        }
    }

    private void addRows(PdfPTable table) {

        for (Product product : products) {

            table.addCell(
                    String.valueOf(product.getId())
            );

            table.addCell(
                    product.getProductName()
            );

            table.addCell(
                    String.valueOf(product.getProductCategory())
            );

            table.addCell(
                    String.valueOf(product.getStock())
            );

            table.addCell(
                    String.valueOf(product.getAlertStock())
            );
        }
    }
}