package org.elsys.valiolucho.businessmonefy;

public class DateHolder {
    private String from;
    private String to;

    public void setFrom(String from) {
        this.from = from + " 00:00:00";
    }

    public void setTo(String to) {
        this.to = to + " 23:59:59";
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
