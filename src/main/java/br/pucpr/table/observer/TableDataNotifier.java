package br.pucpr.table.observer;

import br.pucpr.table.model.TableData;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** Guarda os listeners de um ObservableTableData e os avisa das mudanças. */
public class TableDataNotifier {
  private final List<TableDataListener> listeners = new CopyOnWriteArrayList<>();

  public void add(TableDataListener listener) {
    if (listener != null && !listeners.contains(listener)) {
      listeners.add(listener);
    }
  }

  public void remove(TableDataListener listener) {
    listeners.remove(listener);
  }

  public void fire(TableData source) {
    for (var listener : listeners) {
      listener.tableChanged(source);
    }
  }
}
