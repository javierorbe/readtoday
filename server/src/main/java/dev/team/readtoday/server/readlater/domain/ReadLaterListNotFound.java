package dev.team.readtoday.server.readlater.domain;

public final class ReadLaterListNotFound extends RuntimeException{
  public ReadLaterListNotFound(String message){
    super (message);
  }
}
