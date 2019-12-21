package com.castlery.modules.system.domain.enums.converter;

import com.castlery.modules.system.domain.enums.MenuType;

public class MenuTypeConverter extends AbstractEnumConverter<MenuType, String> {

  public MenuTypeConverter() {
    super(MenuType.class);
  }
}
