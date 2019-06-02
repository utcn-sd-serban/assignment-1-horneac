package ro.utcn.sd.he.assignment1.dto;

import lombok.Data;
import ro.utcn.sd.he.assignment1.model.Question;

import java.sql.Timestamp;

@Data
public class QuestionDTO {
    private int id;
    private String author;
    private String title;
    private String text;
    private Timestamp creation_date_time;
    private String tags;

    public static QuestionDTO ofEntity(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setAuthor(question.getAuthor());
        dto.setCreation_date_time(question.getCreation_date_time());
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setTitle(question.getTitle());
        dto.setTags("");
        return dto;
    }
}
