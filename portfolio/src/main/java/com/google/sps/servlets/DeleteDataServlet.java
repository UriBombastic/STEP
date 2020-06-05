import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

   
@WebServlet("/delete-data") 
public class DeleteDataServlet extends HttpServlet {
  private final String COMMENT_ENTITY_NAME = "Comment";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query(COMMENT_ENTITY_NAME);
    PreparedQuery allComments = datastore.prepare(query);
    for(Entity entity : allComments.asIterable()){
      datastore.delete(entity.getKey());
    }  
    
    response.setContentType("text-html");
    response.getWriter().println("<p> Wow. You just deleted all the comments. From everyone. Good job.");
  }


}