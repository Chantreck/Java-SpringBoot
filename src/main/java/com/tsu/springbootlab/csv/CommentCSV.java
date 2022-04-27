package com.tsu.springbootlab.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.util.Date;

@Data
public class CommentCSV {
    @CsvBindByPosition(position = 0)
    @CsvDate("dd.MM.yyyy")
    private Date createdAt;

    @CsvBindByPosition(position = 1)
    @CsvDate("dd.MM.yyyy")
    private Date editedAt;

    @CsvBindByPosition(position = 2)
    private Integer userId;

    @CsvBindByPosition(position = 3)
    private String tasks;

    @CsvBindByPosition(position = 4)
    private String text;
}
