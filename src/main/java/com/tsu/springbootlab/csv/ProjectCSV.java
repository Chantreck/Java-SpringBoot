package com.tsu.springbootlab.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectCSV {
    @CsvBindByPosition(position = 0)
    @CsvDate("dd.MM.yyyy")
    private Date createdAt;

    @CsvBindByPosition(position = 1)
    @CsvDate("dd.MM.yyyy")
    private Date editedAt;

    @CsvBindByPosition(position = 2)
    private String name;

    @CsvBindByPosition(position = 3)
    private String description;
}
