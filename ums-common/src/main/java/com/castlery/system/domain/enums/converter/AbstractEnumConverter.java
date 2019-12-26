package com.castlery.system.domain.enums.converter;

import com.castlery.system.domain.enums.PersistableEnum;
import javax.persistence.AttributeConverter;

public abstract class AbstractEnumConverter<T extends Enum<T> & PersistableEnum<E>, E>
    implements AttributeConverter<T, E> {
  private final Class<T> clazz;

  public AbstractEnumConverter(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public E convertToDatabaseColumn(T attribute) {
    return attribute != null ? attribute.getValue() : null;
  }

  @Override
  public T convertToEntityAttribute(E dbData) {
    T[] enums = clazz.getEnumConstants();

    for (T e : enums) {
      if (e.getValue().equals(dbData)) {
        return e;
      }
    }
    return null;
  }
}
