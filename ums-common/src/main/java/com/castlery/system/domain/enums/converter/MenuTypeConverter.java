package com.castlery.system.domain.enums.converter;

import com.castlery.system.domain.enums.MenuType;

public class MenuTypeConverter extends AbstractEnumConverter<MenuType, String> {

  public MenuTypeConverter() {
    super(MenuType.class);
  }
}
