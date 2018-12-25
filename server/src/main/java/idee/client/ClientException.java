package idee.client;

public class ClientException extends Exception {

  public ClientException(String message) {
    super(message);
  }

  public ClientException(String message, Exception error) {
    super(message, error);
  }
}