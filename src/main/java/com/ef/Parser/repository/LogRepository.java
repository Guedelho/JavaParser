package com.ef.Parser.repository;

import com.ef.Parser.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    @Query("SELECT l FROM log l WHERE l.date BETWEEN :startDate AND :endDate GROUP BY l.ip HAVING COUNT(l.ip) > :threshold")
    List<Log> verifyLimit(@Param("startDate") Date startDate,
                          @Param("endDate") Date endDate,
                          @Param("threshold") Long threshold);
}
