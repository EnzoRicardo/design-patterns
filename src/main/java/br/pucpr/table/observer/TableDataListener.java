package br.pucpr.table.observer;

import br.pucpr.table.model.TableData;

@FunctionalInterface
public interface TableDataListener {
  void tableChanged(TableData source);
}
