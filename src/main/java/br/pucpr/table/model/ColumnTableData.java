package br.pucpr.table.model;

import br.pucpr.table.observer.ObservableTableData;
import br.pucpr.table.observer.TableDataListener;
import br.pucpr.table.observer.TableDataNotifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ColumnTableData<T> implements ObservableTableData {
  private final List<ColumnData<? super T>> columns;
  private final List<T> data;
  private final TableDataNotifier notifier = new TableDataNotifier();

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

  public void add(T item) {
    data.add(item);
    notifier.fire(this);
  }

  public void set(int row, T item) {
    data.set(row, item);
    notifier.fire(this);
  }

  public T remove(int row) {
    final var removed = data.remove(row);
    notifier.fire(this);
    return removed;
  }

  @Override
  public void addListener(TableDataListener listener) {
    notifier.add(listener);
  }

  @Override
  public void removeListener(TableDataListener listener) {
    notifier.remove(listener);
  }
}
