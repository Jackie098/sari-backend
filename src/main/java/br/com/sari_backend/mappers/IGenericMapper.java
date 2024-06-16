package br.com.sari_backend.mappers;

import java.util.List;

public interface IGenericMapper {
  <T> T toObject(Object obj, Class<T> clazz);

  <T> List<T> toList(List<?> list, Class<T> clazz);
}
