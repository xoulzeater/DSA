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

    public static String getTime(GregorianCalendar gc) {
        String dateString = String.format("%02d", gc.get(Calendar.DATE))
                + "/" + String.format("%02d", (gc.get(Calendar.MONTH) + 1)) + "/"
                + String.format("%02d", gc.get(Calendar.YEAR)) + "    "
                + String.format("%02d", gc.get(Calendar.HOUR_OF_DAY)) + ":"
                + String.format("%02d", gc.get(Calendar.MINUTE)) + ":"
                + String.format("%02d", gc.get(Calendar.SECOND));
        return dateString;

    }

    public static String displayDate(GregorianCalendar gc) {
        String dateString = String.format("%02d", gc.get(Calendar.DATE))
                + "/" + String.format("%02d", (gc.get(Calendar.MONTH) + 1)) + "/"
                + String.format("%02d", gc.get(Calendar.YEAR));
        return dateString;

    }

    public static int getYears(GregorianCalendar g1, GregorianCalendar g2) {
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
            gc1.add(Calendar.YEAR, 1);
            if (validate) {
                elapsed++;
            } else {
                elapsed--;
            }
        }
        return elapsed;
    }

    public static long getDaysTimeDiff() {
        String dateStop = "01/14/2012 09:29:58";
        String dateStart = "01/15/2012 10:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
            return diffSeconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static GregorianCalendar convertDateToGregorian(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        return gc;
    }

    public static int requestIntegerValue(String requestMsg, String invalidMsg, boolean allowEmpty) {
        Scanner sc = new Scanner(System.in);
        String quantity = "";
        do {
            System.out.print(requestMsg);
            quantity = sc.nextLine();
            if (allowEmpty && quantity.isEmpty()) {
                return -1;
            }
            if (!quantity.matches("^[0-9]+$")) {
                System.out.println(invalidMsg);
            }
        } while (!quantity.matches("^[0-9]+$"));
        return Integer.parseInt(quantity);
    }

    public static GregorianCalendar readStringCalendar(String requestMsg, String invalidMsg, boolean allowEmpty,String dateFormat) {
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        GregorianCalendar gc = new GregorianCalendar();
        do {
            System.out.print(requestMsg);
            String startDateString = sc.nextLine();
            if (allowEmpty && startDateString.isEmpty()) {
                return null;
            }
            DateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            Date startDate;
            try {
                startDate = df.parse(startDateString);
                gc.setTimeInMillis(startDate.getTime());
                valid = true;
            } catch (ParseException e) {

                System.out.println(invalidMsg);
            }
        } while (!valid);
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
        String genderOption = "";
        do {
            System.out.print("Please enter the gender(M/F):");
            gender = sc.nextLine().toLowerCase();
            switch (gender) {
                case "m":
                    genderOption = "Male";
                    break;
                case "f":
                    genderOption = "Female";
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
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.HOUR_OF_DAY,gc.get(Calendar.HOUR_OF_DAY)+1);
        //int c = MyConverter.getHours(gc, new GregorianCalendar());
       // System.out.println(c);

    }

}
