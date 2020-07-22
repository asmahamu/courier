package com.krupatek.courier.view;

import com.krupatek.courier.utils.DateUtils;
import com.krupatek.courier.utils.NumberUtils;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.time.LocalDate;

@Tag("object")
public class HorizonDatePicker extends HorizontalLayout implements Focusable<TextField>{

    private LocalDate currentDate;
    private DateUtils dateUtils;
    private TextField dayTF, monthTF, yearTF;
    private DateChangeListener dateChangeListener;

    public HorizonDatePicker(LocalDate date, DateUtils dateUtils, NumberUtils numberUtils){
        setWidth("100%");

        this.currentDate = date;
        this.dateUtils = dateUtils;

        Label dateSeperator1 = new Label("-");
        dateSeperator1.setWidth("15%");
        Label dateSeperator2 = new Label("-");
        dateSeperator2.setWidth("15%");

        dayTF = new TextField("Day : ");
        dayTF.setValueChangeMode(ValueChangeMode.LAZY);
        dayTF.setWidth("22%");
        dayTF.setAutoselect(true);
        dayTF.setValue(String.valueOf(currentDate.getDayOfMonth()));
        dayTF.addValueChangeListener(e -> {
            boolean validDate = numberUtils.isDecimalNumber(e.getValue());
            if(validDate) {
                int value = Integer.parseInt(e.getValue()); // Check Range
                validDate = 1 <= value && value <= currentDate.lengthOfMonth();
                if(validDate) {
                    currentDate = currentDate.withDayOfMonth(value);
                    dateChangeListener.changedDate(currentDate);
                    dayTF.setInvalid(false);
                } else {
                    dayTF.setInvalid(true);
                    dayTF.setErrorMessage("Doesn't look like a day of a month");
                }
            }
        });


        monthTF = new TextField("Month : ");
        monthTF.setWidth("24%");
        monthTF.setAutoselect(true);
        monthTF.setValue(String.valueOf(currentDate.getMonthValue()));
        monthTF.setValueChangeMode(ValueChangeMode.LAZY);
        monthTF.addValueChangeListener(e -> {
            boolean validMonth = numberUtils.isDecimalNumber(e.getValue());
            if(validMonth) {
                int value = Integer.parseInt(e.getValue()); // Check Range
                validMonth = 1 <= value && value <= 12;
                if(validMonth) {
                    currentDate = currentDate.withMonth(value);
                    dateChangeListener.changedDate(currentDate);
                    monthTF.setInvalid(false);
                } else {
                    monthTF.setInvalid(true);
                    monthTF.setErrorMessage("Doesn't look like a month");
                }
            }
        });

        yearTF = new TextField("Year : ");
        yearTF.setWidth("24%");
        yearTF.setAutoselect(true);
        yearTF.setValue(String.format("%02d", (currentDate.getYear() - 2000)));
        yearTF.setValueChangeMode(ValueChangeMode.LAZY);
        yearTF.addValueChangeListener(e -> {
            boolean validYear = numberUtils.isDecimalNumber(e.getValue());
            if(validYear) {
                int value = Integer.parseInt(e.getValue()) + 2000; // Check Range
                validYear = 2000 <= value && value <= 2099;
                if(validYear) {
                    currentDate = currentDate.withYear(value);
                    dateChangeListener.changedDate(currentDate);
                    yearTF.setInvalid(false);
                } else {
                    yearTF.setInvalid(true);
                    yearTF.setErrorMessage("Doesn't look like a year");
                }
            }
        });

        add(dayTF, dateSeperator1, monthTF, dateSeperator2, yearTF);
    }

    @Override
    public void focus() {
        dayTF.focus();
    }

    public interface  DateChangeListener{
        void changedDate(LocalDate date);
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }
    public DateChangeListener getDateChangeListener() {
        return dateChangeListener;
    }

    public void setDateChangeListener(DateChangeListener dateChangeListener) {
        this.dateChangeListener = dateChangeListener;
        this.dateChangeListener.changedDate(currentDate);
    }


}
