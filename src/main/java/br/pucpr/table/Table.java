package br.pucpr.table;

import br.pucpr.table.model.TableData;
import java.util.function.IntFunction;

public class Table {
  public void print(TableData data, boolean alignRight, Theme theme) {
    if (data == null || data.getRowCount() == 0) {
      System.out.println("ERRO: Tabela vazia ou nula.");
      return;
    }
    final var borderChar = theme.getBorderChar();
    final var borderWidth = borderWidth(data);

    // Borda superior e cabeçalho
    var sb = new StringBuilder();
    sb.repeat(borderChar, borderWidth).append("\n");
    appendLine(sb, data, data::getColumnName);
    sb.repeat(borderChar, borderWidth).append("\n");
    for (var row = 0; row < data.getRowCount(); row++) {
      final var r = row;
      appendLine(sb, data, column -> data.getValue(r, column));
    }
    // Borda inferior
    sb.repeat(borderChar, borderWidth).append("\n");

    // Espaçamento
    if (alignRight) {
      var lines = sb.toString().split("\n");
      for (var line : lines) {
        System.out.println("                    " + line);
      }
    } else {
      System.out.print(sb);
    }
  }

  private static int borderWidth(TableData data) {
    var width = 1;
    for (var column = 0; column < data.getColumnCount(); column++) {
      width += data.getColumnName(column).length() + 3;
    }
    return width;
  }

  private static void appendLine(StringBuilder sb, TableData data, IntFunction<String> cell) {
    sb.append("|");
    for (var column = 0; column < data.getColumnCount(); column++) {
      final var width = data.getColumnName(column).length();
      final var align = data.isRightAligned(column) ? "" : "-";
      sb.append(" ").append(String.format("%" + align + width + "s", cell.apply(column)));
      sb.append(" |");
    }
    sb.append("\n");
  }
}
