package com.castlery.modules.system.domain.enums;

public enum MenuType implements PersistableEnum<String> {
  VIEW_PERMISSION,
  OBJECT_PERMISSION;

  @Override
  public String getValue() {
    return name();
  }
}
