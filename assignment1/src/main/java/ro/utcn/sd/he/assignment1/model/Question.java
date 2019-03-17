package ro.utcn.sd.he.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private int id;
    private String authot;
    private String title;
    private String text;
    private Timestamp creatiion_date_time;
}
