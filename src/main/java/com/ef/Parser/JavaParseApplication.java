package com.ef.Parser;

import com.ef.Parser.model.Comment;
import com.ef.Parser.model.Log;
import com.ef.Parser.service.CommentService;
import com.ef.Parser.service.LogService;
import com.ef.Parser.util.Arguments;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
public class JavaParseApplication implements ApplicationRunner {
    @Autowired
    LogService logService;

    @Autowired
    CommentService commentService;

	public static void main(String[] args) {
	    if(args.length < 3) {
            System.out.println(">>>>> Invalid command.");
            return;
        }
        SpringApplication.run(JavaParseApplication.class, args);
	}

	@Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Arguments arguments = new Arguments(applicationArguments);

        if (arguments.getAccessLog() != null) {
            try {
                logService.saveLogs(loadFile(arguments.getAccessLog()));
                System.out.println(">>>>> Success");
            } catch (Exception e) {
                System.out.println(">>>>> Error: " + e.getMessage());
            }
        }

        if (arguments.verifyCommand()) {
            List<Log> logs = logService.verifyLimit(arguments);
            if(logs.isEmpty()){
                System.out.println(">>>>> No match criteria.");
                return;
            }

            List<Comment> comments = commentService.saveComments(arguments, logs);
            for (Comment comment : comments) System.out.println(">>>>> " + comment.getComment());
        } else System.out.println(">>>>> Invalid command.");
    }

    public List<Log> loadFile(String file) throws ParseException, IOException {
        List<Log> logs = new ArrayList<>();
        Reader reader = new FileReader(file);
        Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.withDelimiter('|').parse(reader);

        for (CSVRecord csvRecord : csvRecords) {
            Log log = new Log();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

            String dateString = csvRecord.get(0);
            Date date = dateFormat.parse(dateString);

            log.setDate(date);
            log.setIp(csvRecord.get(1));
            log.setRequest(csvRecord.get(2));
            log.setStatus(csvRecord.get(3));
            log.setUserAgent(csvRecord.get(4));

            logs.add(log);
        }

        return logs;
    }
}
