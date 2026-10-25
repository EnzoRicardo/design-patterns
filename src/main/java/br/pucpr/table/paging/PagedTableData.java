package br.pucpr.table.paging;

import br.pucpr.table.model.TableData;
import br.pucpr.table.observer.ObservableTableData;
import br.pucpr.table.observer.TableDataListener;
import br.pucpr.table.observer.TableDataNotifier;

/** Decorator que exibe apenas uma página das linhas de qualquer TableData. */
public final class PagedTableData implements ObservableTableData {
  private final TableData data;
  private final int pageSize;
  private final TableDataNotifier notifier = new TableDataNotifier();
  private int page;

  public PagedTableData(TableData data, int pageSize) {
    this(data, pageSize, 1);
  }

  public PagedTableData(TableData data, int pageSize, int page) {
    if (pageSize <= 0) {
      throw new IllegalArgumentException("O tamanho da página deve ser positivo.");
    }
    this.data = data;
    this.pageSize = pageSize;
    this.page = validPage(page);
    if (data instanceof ObservableTableData observable) {
      observable.addListener(this::sourceChanged);
    }
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    final var newPage = validPage(page);
    if (newPage != this.page) {
      this.page = newPage;
      notifier.fire(this);
    }
  }

  private int validPage(int page) {
    if (page < 1 || page > getPageCount()) {
      throw new IllegalArgumentException(
          "Página %d inválida, deve estar entre 1 e %d.".formatted(page, getPageCount()));
    }
    return page;
  }

  private void sourceChanged(TableData source) {
    page = Math.min(page, getPageCount());
    notifier.fire(this);
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getPageCount() {
    return Math.max(1, (data.getRowCount() + pageSize - 1) / pageSize);
  }

  public boolean nextPage() {
    if (page >= getPageCount()) {
      return false;
    }
    page++;
    notifier.fire(this);
    return true;
  }

  public boolean previousPage() {
    if (page <= 1) {
      return false;
    }
    page--;
    notifier.fire(this);
    return true;
  }

  @Override
  public void addListener(TableDataListener listener) {
    notifier.add(listener);
  }

  @Override
  public void removeListener(TableDataListener listener) {
    notifier.remove(listener);
  }

  private int firstRow() {
    return (page - 1) * pageSize;
  }

  @Override
  public int getColumnCount() {
    return data.getColumnCount();
  }

  @Override
  public String getColumnName(int column) {
    return data.getColumnName(column);
  }

  @Override
  public int getRowCount() {
    return Math.max(0, Math.min(pageSize, data.getRowCount() - firstRow()));
  }

  @Override
  public String getValue(int row, int column) {
    if (row < 0 || row >= getRowCount()) {
      throw new IndexOutOfBoundsException(row);
    }
    return data.getValue(firstRow() + row, column);
  }

  @Override
  public boolean isRightAligned(int column) {
    return data.isRightAligned(column);
  }
}
