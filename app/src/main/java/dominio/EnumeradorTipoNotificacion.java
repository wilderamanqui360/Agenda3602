package dominio;

/**
 * Created by REAPRO on 21/05/2015.
 */
public enum EnumeradorTipoNotificacion {
    HORA_ENTRADA(""),
    HORA_SALIDA(""),
    MICCION("NO|CONTROLADO|NOCONTROLADO"),
    DEPOSICION("NO|DURO|BLANDO|ESTREIDO"),
    LONCHERA("TODO|POCO|NADA"),
    INICIO_SUENIO(""),
    FIN_SUENIO(""),
    FOTO(""),
    UBICACION("");

    private String calificadores;

    private EnumeradorTipoNotificacion(String calificadores){
        this.calificadores= calificadores;
    }

    public String getCalificadores() {
        return calificadores;
    }
}
