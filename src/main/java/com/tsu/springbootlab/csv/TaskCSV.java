package com.tsu.springbootlab.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.util.Date;

@Data
public class TaskCSV {
    @CsvBindByPosition(position = 0)
    @CsvDate("dd.MM.yyyy")
    private Date createdAt;

    @CsvBindByPosition(position = 1)
    @CsvDate("dd.MM.yyyy")
    private Date editedAt;

    @CsvBindByPosition(position = 2)
    private String title;

    @CsvBindByPosition(position = 3)
    private String description;

    @CsvBindByPosition(position = 4)
    private Integer creatorId;

    @CsvBindByPosition(position = 5)
    private Integer performerId;

    @CsvBindByPosition(position = 6)
    private String priority;

    @CsvBindByPosition(position = 7)
    private Integer projectId;

    @CsvBindByPosition(position = 8)
    private Integer taskETA;
}
