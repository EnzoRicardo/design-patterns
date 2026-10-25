package br.pucpr.table.observer;

import br.pucpr.table.model.TableData;

public interface ObservableTableData extends TableData {
  void addListener(TableDataListener listener);

  void removeListener(TableDataListener listener);
}
