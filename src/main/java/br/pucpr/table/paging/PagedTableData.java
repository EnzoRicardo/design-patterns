package br.pucpr.table.paging;

import br.pucpr.table.model.TableData;

/** Decorator que exibe apenas uma página das linhas de qualquer TableData. */
public final class PagedTableData implements TableData {
  private final TableData data;
  private final int pageSize;
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
    setPage(page);
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    if (page < 1 || page > getPageCount()) {
      throw new IllegalArgumentException(
          "Página %d inválida, deve estar entre 1 e %d.".formatted(page, getPageCount()));
    }
    this.page = page;
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
    return true;
  }

  public boolean previousPage() {
    if (page <= 1) {
      return false;
    }
    page--;
    return true;
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
