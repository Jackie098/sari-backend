package br.com.sari_backend.utils;

public interface IPasswordUtils {
  public String hashPass(String password);

  public boolean checkPass(String plainPassword, String hashedPassword);
}
