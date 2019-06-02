package ro.utcn.sd.he.assignment1.dto;

import lombok.Data;
import ro.utcn.sd.he.assignment1.model.Question;

import java.sql.Timestamp;

@Data
public class QuestionVoteDTO {
    private int id;
    private String author;
    private String title;
    private String text;
    private Timestamp creation_date_time;
    private int voteCount;

    public static QuestionVoteDTO ofEntity(Question question, int voteCount) {
        QuestionVoteDTO dto = new QuestionVoteDTO();
        dto.setAuthor(question.getAuthor());
        dto.setCreation_date_time(question.getCreation_date_time());
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setTitle(question.getTitle());
        dto.setVoteCount(voteCount);
        return dto;
    }
}
