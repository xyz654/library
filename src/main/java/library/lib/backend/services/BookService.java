package library.lib.backend.services;


import library.lib.backend.models.*;
import library.lib.backend.persistence.AuthorRepository;
import library.lib.backend.persistence.BookRepository;
import library.lib.backend.persistence.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class BookService {

    @PersistenceUnit
    private EntityManagerFactory emf;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    public ReturnModel addBook(Book book, Member member) {
        if (member == null) {
            return new ReturnModel(null, "User not logged in", ReturnCodes.USER_ERROR);
        }

        if (member.getPermission() != Permissions.ADMIN && member.getPermission() != Permissions.WORKER) {
            return new ReturnModel(null, "User not authorized", ReturnCodes.USER_ERROR);
        }
        bookRepository.save(book);
        Author author = book.getAuthor();
        author.addBook(book);
        authorRepository.save(author);
        return new ReturnModel(book, "Book added", ReturnCodes.OK);

    }

    public ReturnModel addCategory(String name) {
        if (categoryRepository.findByName(name).size() > 0) {
            return new ReturnModel(null, "Category already exists", ReturnCodes.USER_ERROR);
        }
        Category category = new Category(name);
        categoryRepository.save(category);
        return new ReturnModel(category, "Category added", ReturnCodes.OK);
    }

    public List<Book> getFreeBooks() {
        return bookRepository.findByNullMember();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).size() > 0 ? categoryRepository.findByName(name).get(0) : null;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Book> getBooksWithCustomQuery(Filters filters) {
        String customQuery = "SELECT b FROM Book b  ";
        boolean first = true;
        if (filters.getAuthor() != null) {
            customQuery += "WHERE b.author = '" + filters.getAuthor().getId() + "'";
            first = false;
        }
        if (filters.getCategory() != null) {
            if (first) {
                customQuery += "WHERE b.category = '" + filters.getCategory().getId() + "'";
                first = false;
            } else {
                customQuery += " AND b.category = '" + filters.getCategory().getId() + "'";
            }
        }

        if (filters.getTitle() != null) {
            if (first) {
                customQuery += "WHERE b.title = '" + filters.getTitle() + "'";
                first = false;
            } else {
                customQuery += " AND b.title = '" + filters.getTitle() + "'";
            }
        }

        if (filters.isFree()) {
            if (first) {
                customQuery += "WHERE b.loaner IS NULL";
                first = false;
            } else {
                customQuery += " AND b.loaner IS NULL";
            }
        }
        if (filters.getMember() != null) {
            if (first) {
                customQuery += "WHERE b.loaner = '" + filters.getMember().getId() + "'";
            } else {
                customQuery += " AND b.loaner = '" + filters.getMember().getId() + "'";
            }
        }
        EntityManager em = emf.createEntityManager();
        List<Book> books = em.createQuery(customQuery).getResultList();
        em.close();
        return books;
    }

    public List<Book> getRecommendedBooks(Member member) {
        if(member.getFavouriteCategory() == null){
            return this.getAllBooks();
        }
        EntityManager em = emf.createEntityManager();
        List<Book> books = em.createQuery("select b from Book b where b.category = '" + member.getFavouriteCategory().getId() + "' " +
                "and id in (Select r.book.id from Rate r where r.book.category = b.category group by r.book having avg(points) >= 4 and count(points) >= 1)").getResultList();
        em.close();
        return books;
    }


    public void loadJson() throws IOException {
        try{
            String content;
            try {
                content = new String(Files.readAllBytes(Paths.get("./" + "amazon.books.json")));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            JSONArray obj = new JSONArray(content);
            for(int i =0 ; i< 100;i++){
                JSONObject book = obj.getJSONObject(i);
                JSONArray category = book.getJSONArray("categories");
                String cat = category.getString(0);
                Category c = this.getCategoryByName(cat);
                if(c == null){
                    c = new Category(cat);
//                    categoryRepository.save(c);
                }
                JSONArray authors = book.getJSONArray("authors");
                String auth = authors.getString(0);
                Author a = authorRepository.findByName(auth).size() > 0 ? authorRepository.findByName(auth).get(0) : null;
                if(a == null){
                    a = new Author(auth);
//                    authorRepository.save(a);
                }
                String url = book.getString("thumbnailUrl");
                if(url == null){
                    url = "https://ecsmedia.pl/c/ksiazka-pod-tytulem-tom-1-w-iext121791863.jpg";
                }
                Book b = new Book(book.getString("title"),a, "", url, "", c);
//                bookRepository.save(b);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
