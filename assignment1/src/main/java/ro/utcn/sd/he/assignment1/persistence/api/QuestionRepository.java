package ro.utcn.sd.he.assignment1.persistence.api;

import ro.utcn.sd.he.assignment1.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    //TODO sa facem in REPO filtrarea dupa titlu sau tag-uri pt ca e mult mai rapid si nu ia toate obiectele si le verifica
    //si baza de date e mult mai rapida, si ne da un singur obiect, deci nu ia 1 GB de ram ca sa afiseze doar un titlu


    Question save(Question question);

    Optional<Question> findById(int id);

    void remove(Question question);

    List<Question> findAll();

}
