package br.pucpr.table;

public interface TableData {
  int getColumnCount();

  /** O nome da coluna também define a sua largura na tabela. */
  String getColumnName(int column);

  int getRowCount();

  String getValue(int row, int column);

  default boolean isRightAligned(int column) {
    return false;
  }
}
