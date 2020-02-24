package server;

@FunctionalInterface
public interface IExceptionHandler {

	public void handle(HttpContext ctx, AbortException e);

}
