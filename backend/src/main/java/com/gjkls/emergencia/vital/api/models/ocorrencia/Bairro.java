package com.gjkls.emergencia.vital.api.models.ocorrencia;

// RS 16
public enum Bairro {
    CENTRO("Centro", 0.5),
    OLIVEIRAS("Bairro das Oliveiras", 1.5),
    APARECIDA("Vila Aparecida", 3.0),
    AUGUSTO( "Jardim Augusto", 4.2),
    SAOJOSE("Distrito de São José", 4.8),
    CANEDO("Alto do Canedo", 5.5),
    ANTONIO("Vale Santo Antônio", 7.0),
    AZUL("Setor Serra Azul", 12.0),
    UNIVERSITARIO("Assentamento Universitário", 16.5),
    NOROESTE("Parque Noroeste", 22.0);

    private final String local;
    private final double distancia;
    Bairro(String local, double distancia) {
        this.local = local;
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return local;
    }

    public double getDistancia() {
        return distancia;
    }
}
