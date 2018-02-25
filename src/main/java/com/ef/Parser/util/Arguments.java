package com.ef.Parser.util;


import lombok.Data;
import org.springframework.boot.ApplicationArguments;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
public class Arguments {
    private ApplicationArguments applicationArguments;

    private Long threshold;
    private String accessLog;
    private Date startDate;
    private Date endDate;

    public Arguments(ApplicationArguments applicationArguments) throws ParseException {
        this.applicationArguments = applicationArguments;

        if (applicationArguments.containsOption("accesslog")) this.accessLog = applicationArguments.getOptionValues("accesslog").get(0);
        if (applicationArguments.containsOption("threshold")) this.threshold = new Long(applicationArguments.getOptionValues("threshold").get(0));

        if (applicationArguments.containsOption("startDate")) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
            String date = applicationArguments.getOptionValues("startDate").get(0);

            this.startDate = formatter.parse(date);
            this.endDate = startDate;
        }

        if (applicationArguments.containsOption("duration")) {
            String duration = applicationArguments.getOptionValues("duration").get(0);

            if (duration.equals("hourly")) this.endDate = new Date(this.startDate.getTime() + TimeUnit.HOURS.toMillis(1));
            else if (duration.equals("daily")) this.endDate = new Date(this.startDate.getTime() + TimeUnit.DAYS.toMillis(1));
        }
    }

    public boolean verifyCommand() {
        if (this.applicationArguments.containsOption("threshold"))
            if (this.applicationArguments.containsOption("startDate"))
                if (this.applicationArguments.containsOption("duration")) return true;
        return false;
    }
}
