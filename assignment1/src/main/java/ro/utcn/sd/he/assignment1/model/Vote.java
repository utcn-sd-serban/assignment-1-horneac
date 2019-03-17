package ro.utcn.sd.he.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private int id;
    private int type;
    private int userID;
    private int questionID;
    private int answerID;
}
