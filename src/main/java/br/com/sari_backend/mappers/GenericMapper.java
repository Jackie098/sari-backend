package br.com.sari_backend.mappers;

import java.lang.reflect.Field;
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

  public <S, D> D toObject(S source, Class<D> destinationClass, boolean isNewRecord) {
    D destination;

    try {
      destination = destinationClass.getDeclaredConstructor().newInstance();

      Field[] sourceFields = source.getClass().getDeclaredFields();

      for (Field sourceField : sourceFields) {
        sourceField.setAccessible(true);

        Object value = sourceField.get(source);

        try {
          Field destinationField = destinationClass.getDeclaredField(sourceField.getName());
          destinationField.setAccessible(true);

          if (value != null) {

            // NOTE: This does not need anymore because the conversion between String to the
            // type Enum, is made automaticly whne i add role with type RoleEnum, and not
            // String in CreateUserDTO
            // if (destinationField.getType().isEnum()) {
            // value = Enum.valueOf((Class<Enum>) destinationField.getType(),
            // value.toString());
            // }
            destinationField.set(destination, value);
          }
        } catch (NoSuchFieldException e) {
          System.out.println(e);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Error mapping objects - " + e, e);
    }

    return destination;
  }

  // TODO: implement toList mapper
  @Override
  public <T> List<T> toList(List<?> list, Class<T> clazz) {
    throw new UnsupportedOperationException("Unimplemented method 'toList'");
  }

}
