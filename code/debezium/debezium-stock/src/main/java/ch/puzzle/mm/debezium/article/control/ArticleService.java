package ch.puzzle.mm.debezium.article.control;

import ch.puzzle.mm.debezium.article.entity.Article;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@Traced
@ApplicationScoped
public class ArticleService {

    public List<Article> listAll() {
        return Article.findAll().list();
    }
}
