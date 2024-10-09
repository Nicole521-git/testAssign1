package org.example;

import java.util.Date;

class mydatetime {
    String year;
    String month;
    String day;
    String datetime;
    Date date;
    String week;

    mydatetime() {
        date = new Date();
        year = this.getyear();
        month = this.getmonth();
        day = this.getday();
        datetime = this.getdatetime();
    }

    String getyear() {
        String temp;
        int index;
        index = this.date.toString().lastIndexOf(" ");
        temp = this.date.toString().substring(index, date.toString().length());
        return temp;
    }

    String getmonth() {
        String temp;
        temp = this.date.toString().substring(4, 7);
        if (temp.equals("Feb"))
            return "2";
        else if (temp.equals("Apr"))
            return "4";
        else if (temp.equals("May"))
            return "5";
        else if (temp.equals("Jun"))
            return "6";
        else if (temp.equals("Jul"))
            return "7";
        else if (temp.equals("Aug"))
            return "8";
        else if (temp.equals("Sep"))
            return "9";
        else if (temp.equals("Oct"))
            return "10";
        else if (temp.equals("Nov"))
            return "11";
        else if (temp.equals("Dec"))
            return "12";
        else
            return temp;

    }

    String getday() {
        String temp;
        temp = date.toString().substring(8, 10);
        return temp;

    }

    String getdatetime() {
        String temp;
        temp = date.toString().substring(11, 19);
        return temp;
    }

}
