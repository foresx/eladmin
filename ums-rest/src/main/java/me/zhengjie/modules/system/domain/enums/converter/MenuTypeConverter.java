package me.zhengjie.modules.system.domain.enums.converter;

import me.zhengjie.modules.system.domain.enums.MenuType;

public class MenuTypeConverter extends AbstractEnumConverter<MenuType, String> {

  public MenuTypeConverter() {
    super(MenuType.class);
  }
}
