package br.pucpr.user;

import br.pucpr.table.model.ColumnData;

public class EmailColumn implements ColumnData<User> {
  @Override
  public String header() {
    return "EMAIL                 ";
  }

  @Override
  public String get(User user) {
    final var email = user.email();
    return email == null || !email.contains("@") ? "INVÁLIDO" : email;
  }
}
