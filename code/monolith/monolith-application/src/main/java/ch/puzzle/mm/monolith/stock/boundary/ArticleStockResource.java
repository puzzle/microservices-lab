package ch.puzzle.mm.monolith.stock.boundary;

import ch.puzzle.mm.monolith.stock.entity.ArticleStock;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/article-stocks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArticleStockResource {

    @GET
    public Response listAll() {
        return Response.ok(ArticleStock.listAll()).build();
    }
}