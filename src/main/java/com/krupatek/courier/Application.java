package com.krupatek.courier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

//    Logger log = LogManager.getLogger(Application.class);
//
//    @Autowired
//    CompanyRepository companyRepository;
//
//    @Autowired
//    InvoiceService invoiceService;
//
//    @Autowired
//    ClientService clientService;
//
//    @Autowired
//    BillingService billingService;
//
//    @Autowired
//    AccountCopyService accountCopyService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @PostConstruct
//    void invoiceTest(){
//        String invoiceNo = "GST/19-20/788";
//
//        Optional<BillGeneration> billGeneration = billingService.findOne(invoiceNo);
//        List<AccountCopy> accountCopies = accountCopyService.findAllByBillNo(invoiceNo+" ");
//
//        if(billGeneration.isPresent()){
//            Client client = clientService.findAllByClientName(billGeneration.get().getClientName()).get(0);
//            Company company = companyRepository.findAll().get(0);
//            try {
//                invoiceService.generateInvoiceFor(company, client, billGeneration.get(), accountCopies, Locale.getDefault());
//            } catch (IOException e) {
//                log.info(e.getMessage());
//                e.printStackTrace();
//            }
//        }
//    }


}
