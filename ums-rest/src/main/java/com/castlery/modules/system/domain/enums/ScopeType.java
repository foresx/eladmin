package com.castlery.modules.system.domain.enums;

public enum ScopeType implements PersistableEnum<String> {
  SELF_LEVEL,
  DEPT_LEVEL,
  CUSTOMIZE_LEVEL;

  @Override
  public String getValue() {
    return name();
  }
}
