package br.pucpr.planet;

import br.pucpr.table.model.TableData;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PlanetsTableData implements TableData {
  private static final Locale PT_BR = Locale.of("pt", "BR");
  private static final double AU_KM = 149_597_870.7;
  private static final String[] COLUMNS = {
    "Nome      ", "Diâmetro  ", "Dist. sol (km)", "Dist. sol (ua)", "Tipo   "
  };

  private final List<Planet> planets;

  public PlanetsTableData(List<Planet> planets) {
    this.planets = planets == null ? List.of() : planets.stream().filter(Objects::nonNull).toList();
  }

  @Override
  public int getColumnCount() {
    return COLUMNS.length;
  }

  @Override
  public String getColumnName(int column) {
    return COLUMNS[column];
  }

  @Override
  public int getRowCount() {
    return planets.size();
  }

  @Override
  public String getValue(int row, int column) {
    final var planet = planets.get(row);
    return switch (column) {
      case 0 -> planet.name();
      case 1 -> formatDiameter(planet.diameterKm());
      case 2 -> formatDistanceKm(planet.sunDistanceKm());
      case 3 -> formatDistanceAu(planet.sunDistanceKm());
      case 4 -> formatType(planet.type());
      default -> throw new IndexOutOfBoundsException(column);
    };
  }

  @Override
  public boolean isRightAligned(int column) {
    return column >= 1 && column <= 3;
  }

  private static String formatDiameter(double diameterKm) {
    return String.format(PT_BR, "%,.1f", diameterKm);
  }

  private static String formatDistanceKm(long distanceKm) {
    return String.format(PT_BR, "%,d", distanceKm);
  }

  private static String formatDistanceAu(long distanceKm) {
    return String.format(PT_BR, "%,.2f", distanceKm / AU_KM);
  }

  private static String formatType(PlanetType type) {
    return switch (type) {
      case ROCK -> "Rochoso";
      case GAS -> "Gasoso";
      case ICE -> "Gelado";
      case DWARF -> "Anão";
    };
  }
}
