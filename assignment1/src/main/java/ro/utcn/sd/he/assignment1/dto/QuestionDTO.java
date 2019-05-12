package ro.utcn.sd.he.assignment1.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuestionDTO {
    private int id;
    private String author;
    private String title;
    private String text;
    private Timestamp creation_date_time;
    private String tags;


}
