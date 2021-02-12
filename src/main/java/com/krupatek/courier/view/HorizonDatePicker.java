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
public class HorizonDatePicker{

    private LocalDate currentDate;
    private DateUtils dateUtils;
    private TextField dayTF, monthTF, yearTF;
    private DateChangeListener dateChangeListener;
    private Label dateSeparator1, dateSeparator2;

    public HorizonDatePicker(){

    }

    public void wrap(HorizontalLayout horizontalLayout, LocalDate date, DateUtils dateUtils, NumberUtils numberUtils){

        this.currentDate = date;
        this.dateUtils = dateUtils;

        dateSeparator1 = new Label("-");
        dateSeparator1.setWidth("15%");
        dateSeparator2 = new Label("-");
        dateSeparator2.setWidth("15%");

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
                    if(dateChangeListener != null) {
                        dateChangeListener.changedDate(currentDate);
                    }
                    dayTF.setInvalid(false);
                    monthTF.setInvalid(false);
                    yearTF.setInvalid(false);
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
                    if(dateChangeListener != null) {
                        dateChangeListener.changedDate(currentDate);
                    }
                    dayTF.setInvalid(false);
                    monthTF.setInvalid(false);
                    yearTF.setInvalid(false);
                } else {
                    monthTF.setInvalid(true);
                    monthTF.setErrorMessage("\"Doesn't look like a month");
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
                    if(dateChangeListener != null) {
                        dateChangeListener.changedDate(currentDate);
                    }
                    dayTF.setInvalid(false);
                    monthTF.setInvalid(false);
                    yearTF.setInvalid(false);
                } else {
                    yearTF.setInvalid(true);
                    yearTF.setErrorMessage("Doesn't look like a year");
                }
            }
        });
        yearTF.addBlurListener(e -> {
            boolean validDate = numberUtils.isDecimalNumber(dayTF.getValue());
            boolean validMonth = numberUtils.isDecimalNumber(monthTF.getValue());
            boolean validYear = numberUtils.isDecimalNumber(yearTF.getValue());

            if(validDate && validMonth && validYear) {
                int day = Integer.parseInt(dayTF.getValue()); // Check Range
                validDate = 1 <= day && day <= currentDate.lengthOfMonth();

                int month = Integer.parseInt(monthTF.getValue()); // Check Range
                validMonth = 1 <= month && month <= 12;

                int year = Integer.parseInt(yearTF.getValue()) + 2000; // Check Range
                validYear = 2000 <= year && year <= 2099;
                if(validDate && validMonth && validYear) {
                    currentDate = currentDate.withYear(year).withMonth(month).withDayOfMonth(day);
                    if(dateChangeListener != null) {
                        dateChangeListener.changedDate(currentDate);
                    }
                    dayTF.setInvalid(false);
                    monthTF.setInvalid(false);
                    yearTF.setInvalid(false);
                }
            }

            if(!validDate){
                dayTF.setInvalid(true);
                dayTF.setErrorMessage("Doesn't look like a day of a month");
            }

            if(!validMonth){
                monthTF.setInvalid(true);
                monthTF.setErrorMessage("Doesn't look like a month");
            }

            if(!validYear){
                yearTF.setInvalid(true);
                yearTF.setErrorMessage("Doesn't look like a year");
            }
        });

        horizontalLayout.add(dayTF, dateSeparator1, monthTF, dateSeparator2, yearTF);
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
    public TextField getDayTF() {
        return dayTF;
    }

    public void setDayTF(TextField dayTF) {
        this.dayTF = dayTF;
    }

    public TextField getMonthTF() {
        return monthTF;
    }

    public void setMonthTF(TextField monthTF) {
        this.monthTF = monthTF;
    }

    public TextField getYearTF() {
        return yearTF;
    }

    public void setYearTF(TextField yearTF) {
        this.yearTF = yearTF;
    }

    public Label getDateSeparator1() {
        return dateSeparator1;
    }

    public void setDateSeparator1(Label dateSeparator1) {
        this.dateSeparator1 = dateSeparator1;
    }

    public Label getDateSeparator2() {
        return dateSeparator2;
    }

    public void setDateSeparator2(Label dateSeparator2) {
        this.dateSeparator2 = dateSeparator2;
    }

}
