package com.krupatek.courier;

import com.krupatek.courier.model.BillGeneration;
import com.krupatek.courier.model.Client;
import com.krupatek.courier.model.Company;
import com.krupatek.courier.repository.CompanyRepository;
import com.krupatek.courier.service.BillingService;
import com.krupatek.courier.service.ClientService;
import com.krupatek.courier.service.InvoiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    Logger log = LogManager.getLogger(Application.class);

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ClientService clientService;

    @Autowired
    BillingService billingService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    void invoiceTest(){
        String invoiceNo = "GST/19-20/788";

        Optional<BillGeneration> billGeneration = billingService.findOne(invoiceNo);

        if(billGeneration.isPresent()){
            Client client = clientService.findAllByClientName(billGeneration.get().getClientName()).get(0);
            Company company = companyRepository.findAll().get(0);
            try {
                invoiceService.generateInvoiceFor(company, client, billGeneration.get(), Locale.getDefault());
            } catch (IOException e) {
                log.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
