package ro.utcn.sd.he.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int type;
    @Column(name = "userID")
    private int userID;
    @Column(name = "questionID")
    private Integer questionID;
    @Column(name = "answerID")
    private Integer answerID;
}
