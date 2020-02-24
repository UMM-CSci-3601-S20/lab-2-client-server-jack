package umm3601.todo;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import umm3601.user.Database;


public class TodoController {

  private TodoDatabase database;

  /**
   * Construct a controller for todos.
   * <p>
   * This loads the "database" of todo info from a JSON file and stores that
   * internally so that (subsets of) todos can be returned in response to
   * requests.
   *
   * @param todoDatabase the `Database` containing todo data
   */
  public TodoController(TodoDatabase todoDatabase) {
    this.database = todoDatabase;
  }

  /**
   * Get the single todo specified by the owner parameter in the request.
   *
   * @param ctx a Javalin HTTP context
   */
  public void getTodo(Context ctx) {
    String owner = ctx.pathParam("owner", String.class).get();
    Todo todo = database.getTodo(owner);
    if (owner != null) {
      ctx.json(todo);
      ctx.status(201);
    } else {
      throw new NotFoundResponse("No todo with owner " + owner + " was found.");
    }
  }

  /**
   * Get a JSON response with a list of all the todos in the "database".
   *
   * @param ctx a Javalin HTTP context
   */
  public void getTodos(Context ctx) {
    Todo[] todos = database.listTodos(ctx.queryParamMap());
    ctx.json(todos);
  }

}
