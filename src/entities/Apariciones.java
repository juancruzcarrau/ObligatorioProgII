package entities;

public class Apariciones implements Comparable<Apariciones>{

    private final MovieCastMember actor;
    private int cantidadDeApariciones;

    public Apariciones(MovieCastMember actor, int cantidadDeApariciones) {
        this.actor = actor;
        this.cantidadDeApariciones = cantidadDeApariciones;
    }

    public MovieCastMember getActor() {
        return actor;
    }

    public int getCantidadDeApariciones() {
        return cantidadDeApariciones;
    }

    public void setCantidadDeApariciones(int cantidadDeApariciones) {
        this.cantidadDeApariciones = cantidadDeApariciones;
    }

    public void agregarAparicion(){
        this.cantidadDeApariciones++;
    }

    @Override
    public int compareTo(Apariciones o) {
        if(this.cantidadDeApariciones > o.cantidadDeApariciones){
            return 1;
        } else if (this.cantidadDeApariciones < o.cantidadDeApariciones){
            return -1;
        }

        return 0;
    }
}
