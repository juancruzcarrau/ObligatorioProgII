import TADs.hash.HashCerrado;
import TADs.listaSimpleFC.ListaEnlazada;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static ListaEnlazada<CastMember> people = new ListaEnlazada<>();
    static ListaEnlazada<CauseOfDeath> deathCauses = new ListaEnlazada<>();
    static ListaEnlazada<Movie> movies = new ListaEnlazada<>();
    static HashCerrado<String, Movie> moviesHash = new HashCerrado<>(86000,1);

    static ListaEnlazada<MovieCastMember> characters = new ListaEnlazada<>();

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
                long startTime = System.currentTimeMillis();
                cargarDatos();
                long endTime = System.currentTimeMillis();
                System.out.println("→ Tiempo de carga: " + (endTime - startTime) + " milisegundos");
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

        //Template carga
        try (CSVReader csvReader = new CSVReader(new FileReader("dataset/IMDb movies.csv"))) {
            String[] valores = null;
            while ((valores = csvReader.readNext()) != null) {
            }
        } catch (IOException | CsvValidationException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }

//      Carga de peliculas
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb movies.csv")).withSkipLines(1).build()) {
            String[] valores;

            while ((valores = csvReader.readNext()) != null) {
                Movie movie = new Movie(valores);
                moviesHash.put(movie.getImbdTitleId(), movie);
                movies.add(movie);
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }

        //Carga de ratings a las peliculas
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb ratings.csv")).withSkipLines(1).build()) {
            String[] valores;
            while ((valores = csvReader.readNext()) != null) {
                MovieRating rating = new MovieRating(valores);
                moviesHash.get(valores[0]).setMovieRating(rating);
            }
        } catch (IOException | CsvValidationException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }

        //Carga de castMembers y causas de muerte



        // ANTERIOR CON CSV READER:
        /*try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb names.csv")).withSkipLines(1).build()) {
            String[] valores = null;

            while ((valores = csvReader.readNext()) != null) {

                try {
                    CastMember cm = new CastMember(valores);
                    CauseOfDeath dc = null;

                    for (int i = 1; i < deathCauses.size() + 1; i++) {
                        if (deathCauses.get(i).getValue().getName().equals(valores[11])) {
                            dc = deathCauses.get(i).getValue();
                            break;
                        }
                    }

                    if (dc != null) {
                        cm.setCauseOfDeath(dc);
                    }
                    else {
                        dc = new CauseOfDeath(valores[11]);
                        deathCauses.add(dc);
                        cm.setCauseOfDeath(dc);
                    }

                    people.add(cm);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException | CsvValidationException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }
*/
        // ACTUAL CON BUFF. READER
        /*try {
            String[] valores;
            String strCurrentLine;
            BufferedReader objReader;
            objReader = new BufferedReader(new FileReader ("dataset/IMDb names.csv"));
            objReader.readLine(); // SALTEO DEL CABEZAL

            while ((strCurrentLine = objReader.readLine()) != null) {

                try {
                    valores = strCurrentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    CastMember cm = new CastMember(valores);
                    CauseOfDeath dc = null;

                    for (int i = 1; i < deathCauses.size() + 1; i++) {
                        if (deathCauses.get(i).getValue().getName().equals(valores[11])) {
                            dc = deathCauses.get(i).getValue();
                            break;
                        }
                    }

                    if (dc != null) {
                        cm.setCauseOfDeath(dc);
                    }
                    else {
                        dc = new CauseOfDeath(valores[11]);
                        deathCauses.add(dc);
                        cm.setCauseOfDeath(dc);
                    }

                    people.add(cm);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        catch (Exception e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }*/

        //Carga de MovieCastMembers
        try {
            String[] valores;
            String strCurrentLine;
            BufferedReader objReader;
            objReader = new BufferedReader(new FileReader ("dataset/IMDb title_principals.csv"));
            objReader.readLine(); // SALTEO DEL CABEZAL
            while ((strCurrentLine = objReader.readLine()) != null) {
                valores = strCurrentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                MovieCastMember movieCM = new MovieCastMember(valores);
                characters.add(movieCM);
                }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("size lista peliculas: "+movies.size() + " y tiene que dar 85855");
        System.out.println("size lista castMembers: "+people.size() + " y tiene que dar 297710");
        System.out.println("size lista movieCastMembers: "+characters.size() + " y tiene que dar 835493");
    }

}

