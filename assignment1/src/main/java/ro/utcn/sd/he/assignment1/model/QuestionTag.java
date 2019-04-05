package ro.utcn.sd.he.assignment1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuestionTag {
    @Id
    @Column(name = "questionID")
    private int questionID;
    @Column(name = "tagID")
    private int tagID;
}
