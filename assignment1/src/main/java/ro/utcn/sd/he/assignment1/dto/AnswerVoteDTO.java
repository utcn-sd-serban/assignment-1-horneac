package ro.utcn.sd.he.assignment1.dto;

import lombok.Data;
import ro.utcn.sd.he.assignment1.model.Answer;

import java.sql.Timestamp;

@Data
public class AnswerVoteDTO {
    private int id;
    private String author;
    private String text;
    private Timestamp creation_date_time;
    private int questionId;
    private int voteCount;

    public static AnswerVoteDTO ofEntity(Answer answer, int voteCount) {
        AnswerVoteDTO dto = new AnswerVoteDTO();
        dto.setAuthor(answer.getAuthor());
        dto.setCreation_date_time(answer.getCreation_date_time());
        dto.setId(answer.getId());
        dto.setQuestionId(answer.getQuestionId());
        dto.setText(answer.getText());
        dto.setVoteCount(voteCount);
        return dto;
    }
}
