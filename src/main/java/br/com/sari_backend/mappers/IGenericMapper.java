package br.com.sari_backend.mappers;

import java.util.List;

public interface IGenericMapper {
  <T> T toObject(Object obj, Class<T> clazz);

  <D> D toObject(Object soruce, Class<D> destinationClass, boolean isNewRecord);

  <T> List<T> toList(List<?> list, Class<T> clazz);

  // <T> Page<T> toPage(Page<?> pageable, Class<T> clazz);
}
