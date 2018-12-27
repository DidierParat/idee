package idee.provider.client;

public class ClientException extends Exception {

  ClientException(String message) {
    super(message);
  }

  ClientException(String message, Exception error) {
    super(message, error);
  }
}