/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class MyConverter {



    public static GregorianCalendar calcTime(Date time, Date date) {
        GregorianCalendar gcTime = new GregorianCalendar();
        gcTime.setTimeInMillis(time.getTime());
        GregorianCalendar gcDate = new GregorianCalendar();
        gcDate.setTimeInMillis(date.getTime());

        GregorianCalendar newTime = (GregorianCalendar) gcTime.clone();
        newTime.set(Calendar.DATE, gcDate.get(Calendar.DATE));
        newTime.set(Calendar.MONTH, gcDate.get(Calendar.MONTH));
        newTime.set(Calendar.YEAR, gcDate.get(Calendar.YEAR));
        return newTime;

    }

    public static String getTime(GregorianCalendar gc) {
        String dateString = String.format("%02d", gc.get(Calendar.DATE))
                + "/" + String.format("%02d", (gc.get(Calendar.MONTH) + 1)) + "/"
                + String.format("%02d", gc.get(Calendar.YEAR)) + "    "
                + String.format("%02d", gc.get(Calendar.HOUR_OF_DAY)) + ":"
                + String.format("%02d", gc.get(Calendar.MINUTE)) + ":"
                + String.format("%02d", gc.get(Calendar.SECOND));
        return dateString;

    }

    public static String displayTime(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        String timeString = String.format("%02d", gc.get(Calendar.HOUR_OF_DAY)) + ":"
                + String.format("%02d", gc.get(Calendar.MINUTE)) + ":"
                + String.format("%02d", gc.get(Calendar.SECOND));
        return timeString;

    }

    public static String displayTime(GregorianCalendar gc) {
        String timeString = String.format("%02d", gc.get(Calendar.HOUR_OF_DAY)) + ":"
                + String.format("%02d", gc.get(Calendar.MINUTE)) + ":"
                + String.format("%02d", gc.get(Calendar.SECOND));
        return timeString;

    }


    public static String displayDate(GregorianCalendar gc) {
        String dateString = String.format("%02d", gc.get(Calendar.DATE))
                + "/" + String.format("%02d", (gc.get(Calendar.MONTH) + 1)) + "/"
                + String.format("%02d", gc.get(Calendar.YEAR));
        return dateString;

    }

    public static int getHours(GregorianCalendar g1, GregorianCalendar g2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;
        boolean validate = false;
        if (g2.after(g1)) {
            validate = true;
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        //g1.clear(Calendar.YEAR);
        // g1.clear(Calendar.MONTH);
        //g1.clear(Calendar.DATE);
        //g1.clear(Calendar.HOUR);
        g1.clear(Calendar.MINUTE);
        g1.clear(Calendar.SECOND);
        //g2.clear(Calendar.YEAR);
        // g2.clear(Calendar.MONTH);
        //g2.clear(Calendar.DATE);
        //g2.clear(Calendar.HOUR);
        g2.clear(Calendar.MINUTE);
        g2.clear(Calendar.SECOND);
        while (gc1.before(gc2)) {
            gc1.add(Calendar.HOUR, 1);
            if (validate) {
                elapsed++;
            } else {
                elapsed--;
            }
        }
        return elapsed;
    }

    public static int getDays(GregorianCalendar g1, GregorianCalendar g2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;
        boolean validate = false;
        if (g2.after(g1)) {
            validate = true;
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        //g1.clear(Calendar.YEAR);
        // g1.clear(Calendar.MONTH);
        //g1.clear(Calendar.DATE);
        g1.clear(Calendar.HOUR);
        g1.clear(Calendar.MINUTE);
        g1.clear(Calendar.SECOND);
        //g2.clear(Calendar.YEAR);
        // g2.clear(Calendar.MONTH);
        //g2.clear(Calendar.DATE);
        g2.clear(Calendar.HOUR);
        g2.clear(Calendar.MINUTE);
        g2.clear(Calendar.SECOND);
        while (gc1.before(gc2)) {
            gc1.add(Calendar.DATE, 1);
            if (validate) {
                elapsed++;
            } else {
                elapsed--;
            }
        }
        return elapsed;
    }

    public static GregorianCalendar convertDateToGregorian(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        return gc;
    }

    public static int requestIntegerValue(String requestMsg, String invalidMsg,boolean allowEmpty) {
        Scanner sc = new Scanner(System.in);
        String quantity = "";
        do {
            System.out.print(requestMsg);
            quantity = sc.nextLine();
            if(allowEmpty && quantity.isEmpty()){
                return -1;
            }
            if (!quantity.matches("^[0-9]+$")) {
                System.out.println(invalidMsg);
            }
        } while (!quantity.matches("^[0-9]+$"));
        return Integer.parseInt(quantity);
    }

    public static GregorianCalendar readStringCalendar(String requestMsg,String invalidMsg,boolean allowEmpty){
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        GregorianCalendar gc = new GregorianCalendar();  
        do {
            System.out.print(requestMsg);
            String startDateString = sc.nextLine();
            if(allowEmpty && startDateString.isEmpty()){
                return null;
            }
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            Date startDate;
            try {
                startDate = df.parse(startDateString);
                gc.setTimeInMillis(startDate.getTime());
                valid = true;
            } catch (ParseException e) {
              
                    System.out.println(invalidMsg);
            }
        } while (!valid );
        return gc;
    }
    
    public static boolean validateOption(String msg) {
        Scanner sc = new Scanner(System.in);
        for (;;) {
            System.out.print(msg);
            String option = sc.nextLine().toLowerCase();
            switch (option) {
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println("Invalid option, please enter again!!");
            }
        }
    }

    public static String requestGender() {
        Scanner sc = new Scanner(System.in);
        String gender = "";
        String genderOption="";
        do {
            System.out.print("Please enter the gender(M/F):");
            gender = sc.nextLine().toLowerCase();
            switch (gender) {
                case "m":
                    genderOption = "Male";
                    break;
                case "f":
                    genderOption =  "Female";
                    break;
                default:
                    System.out.println("Invalid gender please enter again!");
            }
        } while (!"m".equals(gender) && !"f".equals(gender));
        return genderOption;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //GregorianCalendar gc = MyConverter.readStringCalendar("Please enter your date:", "Invalid date, please try again!!;", true);
        System.out.println("End");
       
        
    }

}
