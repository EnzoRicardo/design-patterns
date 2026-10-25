package br.pucpr;

import static br.pucpr.planet.PlanetType.*;
import static br.pucpr.planet.PlanetType.DWARF;
import static br.pucpr.planet.PlanetType.ICE;
import static br.pucpr.table.Theme.LIGHT;

import br.pucpr.planet.Planet;
import br.pucpr.planet.PlanetaColumns;
import br.pucpr.planet.PlanetsTableData;
import br.pucpr.table.Table;
import br.pucpr.table.TableBuilder;
import br.pucpr.table.Theme;
import br.pucpr.table.model.ColumnTableData;
import br.pucpr.table.observer.RowCountLabel;
import br.pucpr.table.observer.TableView;
import br.pucpr.table.paging.PagedTableData;
import br.pucpr.user.CpfColumn;
import br.pucpr.user.EmailColumn;
import br.pucpr.user.IdColumn;
import br.pucpr.user.NameColumn;
import br.pucpr.user.User;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    final var usuarios = new ArrayList<User>();
    usuarios.add(
        new User(101L, "Carlos Eduardo de Souza", "carlos.souza@email.com", "12345678901"));
    usuarios.add(new User(102L, "Ana Maria Silva", "ana.silva@email.com", "98765432100"));
    usuarios.add(
        new User(103L, "João Pedro de Alcântara Bragança", "joao.pedro@email.com", "45678912345"));
    usuarios.add(new User(104L, "Mariana Costa", "marianacosta.email.com", "11122233344"));
    usuarios.add(new User(105L, "Lucas Mendes", "lucas@email.com", "12345"));
    usuarios.add(new User(106L, "", "beatriz@email.com", "55566677788"));

    System.out.println("IMPRIMINDO USUARIOS");
    System.out.println("-------------------");
    final var usersData =
        new ColumnTableData<User>(
            usuarios, new IdColumn(), new NameColumn(), new EmailColumn(), new CpfColumn(true));
    new Table().print(usersData, true, LIGHT);

    final var planetas = new ArrayList<Planet>();
    planetas.add(new Planet("Mercúrio", 4879, 57_910_000L, ROCK));
    planetas.add(new Planet("Vênus", 12104, 108_200_000L, ROCK));
    planetas.add(new Planet("Terra", 12756, 149_600_000L, ROCK));
    planetas.add(new Planet("Marte", 6792, 227_940_000L, ROCK));
    planetas.add(new Planet("Júpiter", 142984, 778_330_000L, GAS));
    planetas.add(new Planet("Saturno", 120536, 1_429_400_000L, GAS));
    planetas.add(new Planet("Urano", 51118, 2_870_990_000L, ICE));
    planetas.add(new Planet("Netuno", 49528, 4_504_300_000L, ICE));
    planetas.add(new Planet("Plutão", 2376, 5_906_380_000L, DWARF));
    System.out.println();
    System.out.println("IMPRIMINDO PLANETAS");
    System.out.println("-------------------");
    new Table().print(new PlanetsTableData(planetas), false, Theme.NORMAL);

    System.out.println();
    System.out.println("PLANETAS REFLETIDOS");
    System.out.println("-------------------");

    new TableBuilder()
        .light()
        .rightAligned()
        .withData(
            planetas,
            c ->
                c.add(PlanetaColumns.values())
                    .add(
                        "Distance (au)",
                        p -> "%,11.2f".formatted(Planet.kmToAu(p.sunDistanceKm()))))
        .print();

    System.out.println();
    System.out.println("USUARIOS PAGINADOS");
    System.out.println("------------------");
    final var pagedUsers = new PagedTableData(usersData, 4);
    do {
      System.out.printf("Página %d de %d%n", pagedUsers.getPage(), pagedUsers.getPageCount());
      new Table().print(pagedUsers, true, LIGHT);
    } while (pagedUsers.nextPage());

    System.out.println();
    System.out.println("PLANETAS PAGINADOS (página 2, 3 por página)");
    System.out.println("-------------------------------------------");
    new Table().print(new PagedTableData(new PlanetsTableData(planetas), 3, 2), false, Theme.DARK);

    System.out.println();
    System.out.println("PLANETAS REFLETIDOS PAGINADOS (página 3, 4 por página)");
    System.out.println("------------------------------------------------------");
    new TableBuilder().paged(4, 3).withData(planetas, c -> c.add(PlanetaColumns.values())).print();

    System.out.println();
    System.out.println("OBSERVER: COMPONENTES AVISADOS A CADA MUDANÇA");
    System.out.println("---------------------------------------------");
    final var observedUsers =
        new ColumnTableData<User>(
            usuarios.subList(0, 3),
            new IdColumn(),
            new NameColumn(),
            new EmailColumn(),
            new CpfColumn(true));
    final var view = new TableView(false, Theme.NORMAL);
    observedUsers.addListener(view);
    observedUsers.addListener(new RowCountLabel("Usuários"));
    view.show(observedUsers);

    System.out.println("> Adicionando o usuário 107");
    observedUsers.add(new User(107L, "Fernanda Lima", "fernanda@email.com", "99988877766"));
    System.out.println("> Removendo o primeiro usuário");
    observedUsers.remove(0);

    System.out.println();
    System.out.println("> Tabela paginada (2 por página) observando os mesmos dados");
    final var pagedObserved = new PagedTableData(observedUsers, 2);
    pagedObserved.addListener(
        source ->
            System.out.printf(
                "Página %d de %d%n", pagedObserved.getPage(), pagedObserved.getPageCount()));
    pagedObserved.addListener(new TableView(true, Theme.DARK));
    System.out.println("> Indo para a próxima página");
    pagedObserved.nextPage();
  }
}
