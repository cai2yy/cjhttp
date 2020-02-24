package server;

@FunctionalInterface
public interface HandlerFunction {

	public void handle(HttpContext ctx, HttpRequest req);

}
