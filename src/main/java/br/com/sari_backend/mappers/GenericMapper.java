package br.com.sari_backend.mappers;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class GenericMapper implements IGenericMapper {

  private static final ObjectMapper mapper = new ObjectMapper();
  // .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  // .configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, false)
  // .configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false)
  // .configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);

  public static GenericMapper getInstance() {
    return new GenericMapper();
  }

  @Override
  public <T> T toObject(Object obj, Class<T> clazz) {
    if (Objects.isNull(obj)) {
      return null;
    }

    return mapper.convertValue(obj, clazz);
  }

  // TODO: implement toList mapper
  @Override
  public <T> List<T> toList(List<?> list, Class<T> clazz) {
    throw new UnsupportedOperationException("Unimplemented method 'toList'");
  }

}
