package ro.utcn.sd.he.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.sd.he.assignment1.dto.QuestionDTO;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String author;
    private String title;
    private String text;
    @Column(name = "creation_date_time")
    private Timestamp creation_date_time;

    public static Question ofEntity(QuestionDTO dto) {
        return new Question(
                dto.getId(), dto.getAuthor(), dto.getTitle(), dto.getText(), dto.getCreation_date_time()
        );
    }
}
