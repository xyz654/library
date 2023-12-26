package library.lib.backend.services;

import library.lib.backend.models.Author;
import library.lib.backend.models.ReturnCodes;
import library.lib.backend.models.ReturnModel;
import library.lib.backend.persistence.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService( AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public List<Author> getAuthorByName(String authorName) {
        return authorRepository.findByName(authorName);
    }

    public ReturnModel addAuthor(Author author){
        authorRepository.save(author);
        return new ReturnModel(author, "Author added", ReturnCodes.OK);
    }
}
