package ch.puzzle.mm.monolith.article.boundary;

import ch.puzzle.mm.monolith.article.control.ArticleService;
import ch.puzzle.mm.monolith.article.entity.Article;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticleService articleService;

    @GET
    public List<Article> getAllArticles() {
        return articleService.listAll();
    }

}
