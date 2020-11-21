package com.itbeebd.medicare.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Appointment {

    private int patient_id;
    private int doctor_id;
    private int doctor_chamber_id;
    private int hospital_id;
    private Date appointmentDateAndTime;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int status;

    // these will hold the doctor info
    private String name;
    private String hospitalName;
    private String image;
    private String degree;
    private String address;

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getDoctor_chamber_id() {
        return doctor_chamber_id;
    }

    public void setDoctor_chamber_id(int doctor_chamber_id) {
        this.doctor_chamber_id = doctor_chamber_id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDateTime(LocalDate localDate, String time){
        this.year = localDate.getYear();
        this.month = localDate.getMonthValue();
        this.day = localDate.getDayOfMonth();

        String[] t = time.split(":");
        this.hour = Integer.parseInt(t[0]);
        this.minute = Integer.parseInt(t[1]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public static Date getDate(int year, int month, int day, int hour, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month + 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    public Date getAppointmentDateAndTime() {
        return appointmentDateAndTime;
    }

    public void setAppointmentDateAndTime(String  appointmentDateAndTime) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            this.appointmentDateAndTime =fmt.parse(appointmentDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
            this.appointmentDateAndTime = null;
        }
    }

    public  ArrayList<String> getDateFormat(){
        Calendar now = Calendar.getInstance();

        ArrayList<String> dateAndTime = new ArrayList<>();

        String hours = (String) DateFormat.format("HH", appointmentDateAndTime);
        String minutes = (String) DateFormat.format("mm", appointmentDateAndTime);
        String dayOfTheWeek = (String) DateFormat.format("EEEE", appointmentDateAndTime); // Thursday
        String day          = (String) DateFormat.format("dd",   appointmentDateAndTime); // 20
        String monthString  = (String) DateFormat.format("MMMM",  appointmentDateAndTime); // June
        String monthNumber  = (String) DateFormat.format("MM",   appointmentDateAndTime); // 06
        String year         = (String) DateFormat.format("yyyy", appointmentDateAndTime); // 2013

        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH) + 1;
        int nowDay = now.get(Calendar.DAY_OF_MONTH);


       // if(Integer.parseInt(year) == nowYear){
            System.out.println(">>>>>>> monthNumber " + monthNumber);
            System.out.println(">>>>>>> nowMonth " + nowMonth);
            System.out.println(">>>>>>> day " + day);
            System.out.println(">>>>>>> nowDay " + nowDay);
            System.out.println(">>>>>>> hours " + hours);
            System.out.println(">>>>>>> minutes " + minutes);

            if(Integer.parseInt(monthNumber) == nowMonth){
                if(Integer.parseInt(day) == nowDay) dateAndTime.add("Today");
                else if(Integer.parseInt(day) == (nowDay+1)) dateAndTime.add("Tomorrow") ;
                else if(Integer.parseInt(day) == (nowDay-1)) dateAndTime.add("Yesterday") ;
                else dateAndTime.add(monthString + " " + day);
            }
            else {
                dateAndTime.add(monthString + " " + day);
            }

            if(Integer.parseInt(hours) >= 12){
                dateAndTime.add((Integer.parseInt(hours)-12) + ":" + minutes + "pm");
            }
            else {
                dateAndTime.add(Integer.parseInt(hours) + ":" + minutes + "am");
            }
      //  }
        return dateAndTime;
        /*
        else if(Integer.parseInt(year) > nowYear){

        }
        else if(Integer.parseInt(year) < nowYear){

        }

         */
    }

    public  String getAppointmentDateAndTimeString(){
        Calendar now = Calendar.getInstance();

        String dateAndTime = "";

        String hours = (String) DateFormat.format("HH", appointmentDateAndTime);
        String minutes = (String) DateFormat.format("mm", appointmentDateAndTime);
        String dayOfTheWeek = (String) DateFormat.format("EEEE", appointmentDateAndTime); // Thursday
        String day          = (String) DateFormat.format("dd",   appointmentDateAndTime); // 20
        String monthString  = (String) DateFormat.format("MMM",  appointmentDateAndTime); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   appointmentDateAndTime); // 06
        String year         = (String) DateFormat.format("yyyy", appointmentDateAndTime); // 2013

        int nowMonth = now.get(Calendar.MONTH) + 1;
        int nowDay = now.get(Calendar.DAY_OF_MONTH);

        if(Integer.parseInt(monthNumber) == nowMonth){
            if(Integer.parseInt(day) == nowDay) dateAndTime += "Today";
            else if(Integer.parseInt(day) == (nowDay+1)) dateAndTime += "Tomorrow" ;
            else if(Integer.parseInt(day) == (nowDay-1)) dateAndTime += "Yesterday" ;
            else dateAndTime += day +" "+ monthString + ", " + year;
        }
        else {
            dateAndTime += day +" "+ monthString + ", " + year;
        }

        if(Integer.parseInt(hours) >= 12){
            dateAndTime += " " + (Integer.parseInt(hours)-12) + ":" + minutes + "pm";
        }
        else {
            dateAndTime +=  " " + hours + ":" + minutes + "am";
        }
        //  }
        return dateAndTime;
        /*
        else if(Integer.parseInt(year) > nowYear){

        }
        else if(Integer.parseInt(year) < nowYear){

        }

         */
    }
}
