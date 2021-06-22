package entities;

public class Ocurrencias<T> implements Comparable<Ocurrencias<T>>{

    private T object;
    private int ocurrences;

    public Ocurrencias(T object, int ocurrences) {
        this.object = object;
        this.ocurrences = ocurrences;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public int getOcurrences() {
        return ocurrences;
    }

    public void setOcurrences(int ocurrences) {
        this.ocurrences = ocurrences;
    }

    public void incrementarOcurrencias(){
        this.ocurrences++;
    }

    @Override
    public int compareTo(Ocurrencias<T> that) {
        if(this.ocurrences > that.ocurrences){
            return 1;
        } else if (this.ocurrences < that.ocurrences){
            return -1;
        }
        return 0;
    }
}
