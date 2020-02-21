package com.krupatek.courier.service;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.model.Company;
import com.krupatek.courier.utils.NumberUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class DailyReportService {
    @Autowired
    NumberUtils numberUtils;

    Logger log = LogManager.getLogger(DailyReportService.class);
    private static final String logo_path = "/jasper/images/stackextend-logo.png";
    private final String daily_report_template_path = "/jasper/Daily-Report.jrxml";

    public File generateInvoiceFor(Company company, List<AccountCopy> accountCopies, String grossTotal,  Locale locale) throws IOException {

        File pdfFile = File.createTempFile("my-daily-report", ".pdf");

        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load the invoice jrxml template.
            final JasperReport report = loadTemplate();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(company, grossTotal, locale);

            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(accountCopies);

            // Render the PDF file
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);

            log.info("PDF file : "+pdfFile.getAbsolutePath());
        }
        catch (final Exception e)
        {
            log.error(String.format("An error occured during PDF creation: %s", e));
        }
        return pdfFile;
    }
    // Load invoice jrxml template
    private JasperReport loadTemplate() throws JRException {

        log.info(String.format("Invoice template path : %s", daily_report_template_path));

        final InputStream reportInputStream = getClass().getResourceAsStream(daily_report_template_path);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private Map<String, Object> parameters(Company company, String grossTotal, Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();

//        parameters.put("logo", getClass().getResourceAsStream(logo_path));
        parameters.put("company",  company);
        parameters.put("grossTotal",  grossTotal);
        parameters.put("numberUtils",numberUtils);
        parameters.put("REPORT_LOCALE", locale);

        return parameters;
    }
}
