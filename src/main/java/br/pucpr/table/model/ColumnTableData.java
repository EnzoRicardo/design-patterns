package br.pucpr.table.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ColumnTableData<T> implements TableData {
  private final List<ColumnData<? super T>> columns;
  private final List<T> data;

  public ColumnTableData(
      Collection<? extends T> data, Collection<? extends ColumnData<? super T>> columns) {
    this.columns = new ArrayList<>(columns);
    this.data = new ArrayList<>(data);
  }

  @SafeVarargs
  public ColumnTableData(Collection<? extends T> data, ColumnData<? super T>... columns) {
    this(data, Arrays.asList(columns));
  }

  @Override
  public int getColumnCount() {
    return columns.size();
  }

  @Override
  public String getColumnName(int column) {
    return columns.get(column).header();
  }

  @Override
  public int getRowCount() {
    return data.size();
  }

  @Override
  public String getValue(int row, int column) {
    var line = data.get(row);
    return columns.get(column).get(line);
  }

  @Override
  public boolean isRightAligned(int column) {
    return columns.get(column).isRightAligned();
  }
}
