package org.models;

public class OcupacionAsiento {
    String id;
    String idAsiento;
    String idEvento;
    String estadoAsiento;

    public OcupacionAsiento(String id, String idAsiento, String idEvento, String estadoAsiento) {
        this.id = id;
        this.idAsiento = idAsiento;
        this.idEvento = idEvento;
        this.estadoAsiento = estadoAsiento;
    }
}
