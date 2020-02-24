package com.krupatek.courier;

import com.krupatek.courier.model.AccountCopy;
import com.krupatek.courier.repository.CompanyRepository;
import com.krupatek.courier.service.*;
import com.krupatek.courier.utils.DateUtils;
import com.krupatek.courier.view.ClientBillPrintingForm;
import com.krupatek.courier.view.ClientBillRePrintingForm;
import com.krupatek.courier.view.CustomerBillingDetailsForm;
import com.krupatek.courier.view.SystemSettingsForm;
import com.krupatek.courier.view.accountcopy.AccountCopyEditor;
import com.krupatek.courier.view.accountcopy.AccountCopyForm;
import com.krupatek.courier.view.clientprofile.ClientProfileEditor;
import com.krupatek.courier.view.pod.PODEntryForm;
import com.krupatek.courier.view.rate.RateEntryEditor;
import com.krupatek.courier.view.rate.RateIntEntryEditor;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@Theme(value = Lumo.class, variant = Material.LIGHT)
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {
    @Autowired
    AccountCopyService accountCopyService;

    @Autowired
    ClientService clientService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DestinationService destinationService;

    @Autowired
    RateMasterService rateMasterService;

    @Autowired
    RateIntMasterService rateIntMasterService;

    @Autowired
    CourierService courierService;

    @Autowired
    PlaceGenerationService placeGenerationService;

    @Autowired
    NetworkService networkService;

    @Autowired
    DateUtils dateUtils;

    @Autowired
    PODSummaryService podSummaryService;

    @Autowired
    BillingService billingService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    DailyReportService dailyReportService;

    public MainView(@Autowired MessageBean bean) {
        MenuBar menuBar = new MenuBar();
        Div component = new Div();

        menuBar.setOpenOnHover(true);
        Text selected = new Text("");
        Div message = new Div(new Text("Selected: "), selected);

        MenuItem masters = menuBar.addItem("Masters");
        MenuItem billingDetails = menuBar.addItem("Billing Details");

        SubMenu systemSettingsSubMenu = masters.getSubMenu();
        systemSettingsSubMenu.addItem("System Settings", e -> {
            component.removeAll();
            component.add(new SystemSettingsForm(companyRepository));
        }  );

        masters.getSubMenu().addItem("Client Profile",
                e -> {
                    component.removeAll();
                    component.add(new ClientProfileEditor(clientService));
                });

        MenuItem rateMaster = masters.getSubMenu().addItem("Rate Master");
        rateMaster.getSubMenu().addItem("Rate Entry", e -> {
            component.removeAll();
            component.add(new RateEntryEditor(clientService, courierService, rateMasterService));
        });
        rateMaster.getSubMenu().addItem("Rate International Entry", e -> {
            component.removeAll();
            component.add(new RateIntEntryEditor(clientService, courierService, rateIntMasterService));
        });

        MenuItem accountCopyMenuItem = billingDetails.getSubMenu().addItem("Account Copy");
        accountCopyMenuItem.getSubMenu().addItem("Create Account Copy", e -> {
            component.removeAll();
            component.add(new AccountCopyForm(
                    accountCopyService,
                    clientService,
                    rateMasterService ,
                    rateIntMasterService,
                    placeGenerationService,
                    networkService,
                    dateUtils,
                    new AccountCopy()));
        } );
        accountCopyMenuItem.getSubMenu().addItem("Edit Account Copy", e -> {
            component.removeAll();
            component.add(new AccountCopyEditor(
                    accountCopyService,
                    clientService,
                    rateMasterService,
                    rateIntMasterService,
                    placeGenerationService,
                    networkService,
                    dateUtils));
        } );
        billingDetails.getSubMenu().addItem("Client Bill Printing", e -> {
            component.removeAll();
            component.add(new ClientBillPrintingForm(
                    accountCopyService,
                    clientService,
                    rateMasterService,
                    rateIntMasterService,
                    placeGenerationService,
                    networkService,
                    invoiceService,
                    companyRepository,
                    billingService,
                    dateUtils));

        });
        billingDetails.getSubMenu().addItem("Client Bill Reprinting", e -> {
            component.removeAll();
            component.add(new ClientBillRePrintingForm(
                    billingService,
                    accountCopyService,
                    invoiceService,
                    clientService,
                    companyRepository));
        });
        billingDetails.getSubMenu().addItem("POD Summary / Daily Report", e -> {
            component.removeAll();
            component.add(new CustomerBillingDetailsForm(
                    accountCopyService,
                    clientService,
                    rateMasterService,
                    rateIntMasterService,
                    placeGenerationService,
                    networkService,
                    podSummaryService,
                    dailyReportService,
                    companyRepository,
                    dateUtils));
        } );

        billingDetails.getSubMenu().addItem("POD Entry Form", e -> {
            component.removeAll();
            component.add(new PODEntryForm(accountCopyService, dateUtils));
        });

        add(menuBar);
        add(component);
    }

}
