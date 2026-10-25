package br.pucpr.table.observer;

import br.pucpr.table.model.TableData;

/** Outro componente que reage às mudanças: exibe o total de linhas. */
public class RowCountLabel implements TableDataListener {
  private final String label;

  public RowCountLabel(String label) {
    this.label = label;
  }

  @Override
  public void tableChanged(TableData source) {
    System.out.printf("%s: %d linha(s)%n", label, source.getRowCount());
  }
}
