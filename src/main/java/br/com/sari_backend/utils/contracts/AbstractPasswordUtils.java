package br.com.sari_backend.utils.contracts;

public abstract class AbstractPasswordUtils {
  public abstract String hashPass(String password);

  public abstract boolean checkPass(String plainPassword, String hashedPassword);
}
