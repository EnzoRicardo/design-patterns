package br.pucpr.table.observer;

import br.pucpr.table.Table;
import br.pucpr.table.Theme;
import br.pucpr.table.model.TableData;

/** Componente que redesenha a tabela sempre que os dados observados mudam. */
public class TableView implements TableDataListener {
  private final Table table = new Table();
  private final boolean alignRight;
  private final Theme theme;

  public TableView(boolean alignRight, Theme theme) {
    this.alignRight = alignRight;
    this.theme = theme;
  }

  public void show(TableData data) {
    table.print(data, alignRight, theme);
  }

  @Override
  public void tableChanged(TableData source) {
    show(source);
  }
}
