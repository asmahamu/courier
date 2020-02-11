package com.krupatek.courier.service;

import com.krupatek.courier.model.BillGeneration;
import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.Company;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class InvoiceService {
    Logger log = LogManager.getLogger(InvoiceService.class);
    private static final String logo_path = "/jasper/images/stackextend-logo.png";
    private final String invoice_template_path = "/jasper/invoice.jrxml";

    public void generateInvoiceFor(Company company, Client client, BillGeneration billGeneration, Locale locale) throws IOException {

        File pdfFile = File.createTempFile("my-invoice", ".pdf");

        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load the invoice jrxml template.
            final JasperReport report = loadTemplate();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(company, client, billGeneration, locale);

            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));

            // Render the PDF file
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);

            log.info("PDF file : "+pdfFile.getAbsolutePath());
        }
        catch (final Exception e)
        {
            log.error(String.format("An error occured during PDF creation: %s", e));
        }
    }
    // Load invoice jrxml template
    private JasperReport loadTemplate() throws JRException {

        log.info(String.format("Invoice template path : %s", invoice_template_path));

        final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template_path);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private Map<String, Object> parameters(Company company, Client client, BillGeneration billGeneration, Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();

//        parameters.put("logo", getClass().getResourceAsStream(logo_path));
        parameters.put("company",  company);
        parameters.put("client",  client);
        parameters.put("billGeneration",  billGeneration);
        parameters.put("REPORT_LOCALE", locale);

        return parameters;
    }
}
