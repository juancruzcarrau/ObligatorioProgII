import TADs.listaSimple.ListaEnlazada;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.CastMember;
import entities.CauseOfDeath;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static ListaEnlazada<CastMember> people = new ListaEnlazada<>();
    static ListaEnlazada<CauseOfDeath> deathCauses = new ListaEnlazada<>();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        while(true){
            System.out.println("Seleccione la opción que desee:");
            System.out.println("\t1. Carga de datos");
            System.out.println("\t2. Ejecutar consultas");
            System.out.println("\t3. Salir");
            System.out.print("Seleccion: ");

            int seleccion;

            try{
                seleccion = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Ingrese un numero. Intente de nuevo.");
                scanner.next();
                continue;
            }

            if(!Arrays.asList(1, 2, 3).contains(seleccion)){
                System.out.println("Seleccion incorrecta. Intente de nuevo.");
                continue;
            }

            if(seleccion == 1){
                cargarDatos();
            } else if (seleccion == 2){
//                ejectutarConsultas();
            } else if (seleccion == 3){
                break;
            } else {
                throw new RuntimeException("Ha ocurrido un error. Seleccion no puede ser un nro distinto de 1, 2 o 3.");
            }
        }
    }

    private static void cargarDatos() {

        try(CSVReader csvReader = new CSVReader(new FileReader("dataset/IMDb movies.csv"))) {
            String[] valores = null;
            while((valores = csvReader.readNext()) != null){
            }
        } catch (IOException | CsvValidationException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }

        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb names.csv")).withSkipLines(1).build()) {
            String[] valores = null;

            while((valores = csvReader.readNext()) != null) {

                try {
                        CastMember cm = new CastMember(valores);
                        CauseOfDeath dc = new CauseOfDeath(valores[10]);

                        int count = 0;
                        for (int i = 0; i < deathCauses.size(); i++) {
                            if (!dc.equals(deathCauses.get(i).getValue())) {
                                count++;
                            }
                            else if (dc.equals(deathCauses.get(i).getValue())) {
                                cm.setCauseOfDeath(deathCauses.get(i).getValue());
                            }
                            else if (count == deathCauses.size()) {
                                deathCauses.add(dc);
                                cm.setCauseOfDeath(dc);
                            }
                        }

                        people.add(cm);
                }

                catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        catch (IOException | CsvValidationException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }

    }

    public ListaEnlazada<String> listFromArray (String[] array) {
        ListaEnlazada<String> list = new ListaEnlazada<>();

        for (String s : array) {
            list.add(s);
        }

        return list;
    }
}
