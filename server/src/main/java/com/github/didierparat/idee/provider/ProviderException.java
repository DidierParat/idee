package com.github.didierparat.idee.provider;

public class ProviderException extends RuntimeException {

  public ProviderException(final String message) {
    super(message);
  }

  public ProviderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  private ProviderException() {}
}
