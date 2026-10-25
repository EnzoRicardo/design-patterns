package br.pucpr.user;

import br.pucpr.table.TableData;
import java.util.List;
import java.util.Objects;

public class UsersTableData implements TableData {
  private static final String[] COLUMNS = {
    "ID   ", "NOME                ", "EMAIL                 ", "CPF           "
  };

  private final List<User> users;
  private final boolean maskCpf;

  public UsersTableData(List<User> users, boolean maskCpf) {
    this.users = users == null ? List.of() : users.stream().filter(Objects::nonNull).toList();
    this.maskCpf = maskCpf;
  }

  @Override
  public int getColumnCount() {
    return COLUMNS.length;
  }

  @Override
  public String getColumnName(int column) {
    return COLUMNS[column];
  }

  @Override
  public int getRowCount() {
    return users.size();
  }

  @Override
  public String getValue(int row, int column) {
    final var user = users.get(row);
    return switch (column) {
      case 0 -> formatId(user.id());
      case 1 -> formatName(user);
      case 2 -> validateAndFormatEmail(user.email());
      case 3 -> formatCpf(user.cpf(), maskCpf);
      default -> throw new IndexOutOfBoundsException(column);
    };
  }

  private static String formatId(Long id) {
    return id != null ? id.toString() : "0";
  }

  private static String formatCpf(String cpf, boolean mask) {
    if (cpf == null || cpf.length() != 11) {
      return "CPF INVÁLIDO";
    }
    if (mask) {
      return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
    }
    return cpf.substring(0, 3)
        + "."
        + cpf.substring(3, 6)
        + "."
        + cpf.substring(6, 9)
        + "-"
        + cpf.substring(9, 11);
  }

  private static String validateAndFormatEmail(String email) {
    return email == null || !email.contains("@") ? "INVÁLIDO" : email;
  }

  private static String formatName(User user) {
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
