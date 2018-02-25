package com.ef.Parser.service;

import com.ef.Parser.model.Log;
import com.ef.Parser.repository.LogRepository;
import com.ef.Parser.util.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<Log> verifyLimit(Arguments params) { return logRepository.verifyLimit(params.getStartDate(), params.getEndDate(), params.getThreshold()); }

    public void saveLogs(List<Log> logs) { logRepository.save(logs); }
}
