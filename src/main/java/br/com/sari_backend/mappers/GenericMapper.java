package br.com.sari_backend.mappers;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.jsonwebtoken.lang.Collections;

public class GenericMapper implements IGenericMapper {

  private static final ObjectMapper mapper = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, false)
      .configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false)
      .configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true)
      .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

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

  @Override
  public <D> D toObject(Object source, Class<D> destinationClass, boolean isNewRecord) {
    D destination;

    try {
      destination = destinationClass.getDeclaredConstructor().newInstance();

      return mapper.updateValue(destination, source);

      // NOTE: I need only instantiate a destination class and after use generated
      // object to mapper.
      // It doesn't necessary to set direcly the value of a source from a destination
      // with reflections

      // NOTE: DO NOT DELETE BELOW COMMENTED CODE
      // Field[] sourceFields = source.getClass().getDeclaredFields();

      // for (Field sourceField : sourceFields) {
      // sourceField.setAccessible(true);

      // Object value = sourceField.get(source);

      // try {
      // Field destinationField =
      // destinationClass.getDeclaredField(sourceField.getName());
      // destinationField.setAccessible(true);

      // if (value != null) {

      // // NOTE: This does not need anymore because the conversion between String to
      // the
      // // type Enum, is made automaticly whne i add role with type RoleEnum, and not
      // // String in CreateUserDTO
      // // if (destinationField.getType().isEnum()) {
      // // value = Enum.valueOf((Class<Enum>) destinationField.getType(),
      // // value.toString());
      // // }
      // destinationField.set(destination, value);
      // }
      // } catch (NoSuchFieldException e) {
      // System.out.println(e);
      // }
      // }
    } catch (Exception e) {
      throw new RuntimeException("Error mapping objects - " + e, e);
    }

    // return destination;
  }

  @Override
  public <T> List<T> toList(List<?> list, Class<T> clazz) {
    if (Objects.isNull(list) || list.isEmpty()) {
      return Collections.emptyList();
    }

    return list.stream().map(obj -> toObject(obj, clazz)).toList();
  }

  // @Override
  // public <T> Page<T> toPage(Page<?> pageable, Class<T> clazz) {
  // if(Objects.isNull(pageable)) return Page.<T>empty();

  // List<T> mappedPage = toList(pageable.toList(),
  // clazz).stream().filter(Objects::nonNull).collect(Collectors.toList());

  // return new PageImp<>(mappedPage, pageable.getPageable(),
  // pageable.getTotalElements());
  // }

}
