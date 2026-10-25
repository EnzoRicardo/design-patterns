package br.pucpr.user;

import br.pucpr.table.model.ColumnData;

public class NameColumn implements ColumnData<User> {
  @Override
  public String header() {
    return "NOME                ";
  }

  @Override
  public String get(User user) {
    var name = user.name();
    if (name == null || name.isEmpty()) {
      return "NÃO INFORMADO";
    }
    if (name.length() > 20) {
      name = name.substring(0, 17) + "...";
    }
    return name;
  }
}
