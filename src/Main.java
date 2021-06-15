import TADs.hash.HashCerrado;
import TADs.listaSimpleFC.ListaEnlazada;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.*;

import java.io.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {



    static Scanner scanner = new Scanner(System.in);

    // cast members
    static ListaEnlazada<CastMember> peopleList = new ListaEnlazada<>();
    static HashCerrado<String,CastMember> peopleHash = new HashCerrado<>(400000, 1);
    static ListaEnlazada<CauseOfDeath> deathCauses = new ListaEnlazada<>();

    // movies
    static ListaEnlazada<Movie> movies = new ListaEnlazada<>();
    static HashCerrado<String, Movie> moviesHash = new HashCerrado<>(115000,1);

    // movie cast members
    static ListaEnlazada<MovieCastMember> characters = new ListaEnlazada<>();
    static HashCerrado<String, ListaEnlazada<MovieCastMember>> peopleByCountry = new HashCerrado<>(300, 0.75);

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
                System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga:" + (endTime - startTime));
            } else if (seleccion == 2){
                ejectutarConsultas();
            } else if (seleccion == 3){
                break;
            } else {
                throw new RuntimeException("Ha ocurrido un error. Seleccion no puede ser un nro distinto de 1, 2 o 3.");
            }
        }
    }

    private static void ejectutarConsultas() {

        while (true) {
            System.out.println("Seleccione la opción que desee:");
            System.out.println("\t1. Indicar el Top 5 de actores/actrices que más apariciones han tenido a lo largo de los años.");
            System.out.println("\t2. Indicar el Top 5 de las causas de muerte más frecuentes en directores y productores nacidos en Italia, Estados Unidos, Francia y UK.");
            System.out.println("\t3. Mostrar de las 14 películas con más weightedAverage, el promedio de altura de sus actores/actrices si su valor es distinto de nulo.");
            System.out.println("\t4. Indicar el año más habitual en el que nacen los actores y las actrices.");
            System.out.println("\t5. Indicar el Top 10 de géneros de películas más populares, en las cuales al menos un actor/actriz tiene 2 o más hijos.");
            System.out.println("\t6. Salir.");

            System.out.print("Seleccion: ");

            int seleccionConsulta;

            try{
                seleccionConsulta = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Ingrese un numero. Intente de nuevo.");
                scanner.next();
                continue;
            }

            if(!Arrays.asList(1, 2, 3, 4, 5, 6).contains(seleccionConsulta)){
                System.out.println("Seleccion incorrecta. Intente de nuevo.");
                continue;
            }

            if(seleccionConsulta == 1){
//                primeraConsulta();
            } else if (seleccionConsulta == 2){
//                segundaConsulta();
            } else if (seleccionConsulta == 3){
//                terceraConsulta();
            } else if (seleccionConsulta == 4){
//                cuartaConsulta();
            } else if (seleccionConsulta == 5){
//                quintaConsulta();
            } else if (seleccionConsulta == 6){
                break;
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

        //Carga de peliculas
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
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb names.csv")).withSkipLines(1).build()) {
            String[] valores = null;

            int countttt = 0;

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

                    peopleList.add(cm);
                    countttt++;
                    peopleHash.put(cm.getImdbNameId(),cm);
                    if (countttt % 1000 == 0) {
                        System.out.println(countttt);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException | CsvValidationException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }

        //Carga de MovieCastMembers
        try {
            String[] valores;
            String strCurrentLine;
            BufferedReader objReader;
            objReader = new BufferedReader(new InputStreamReader(new FileInputStream("dataset/IMDb title_principals.csv"), "UTF-8"));
            objReader.readLine(); // SALTEO DEL CABEZAL

            int count = 0;

            while ((strCurrentLine = objReader.readLine()) != null) {

                if (count == 1000) {
                    System.out.println(count);
                    count = 0;
                }

                valores = strCurrentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                MovieCastMember movieCM = new MovieCastMember(valores);
                characters.add(movieCM);
                String country = getCountryFromMovieCM(movieCM);
                if (country != null) {
                    if (!peopleByCountry.contains(country)) {
                        ListaEnlazada<MovieCastMember> tempCountryList = new ListaEnlazada<>();
                        tempCountryList.add(movieCM);
                        peopleByCountry.put(country, tempCountryList);

                        count++;

                    } else {
                        ListaEnlazada<MovieCastMember> tempList = peopleByCountry.get(country);
                        tempList.add(movieCM);

                        count++;

                    }
                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("size lista peliculas: "+movies.size() + " y tiene que dar 85855");
        System.out.println("size lista castMembers: "+peopleList.size() + " y tiene que dar 297705");
        System.out.println("size lista movieCastMembers: "+characters.size() + " y tiene que dar 835493");
    }

    private static String getCountryFromMovieCM(MovieCastMember movieCM) {
        String country = null;
        String key = movieCM.getActorID();

        if (peopleHash.contains(key)) {
            country  = peopleHash.get(key).getBirthCountry();
        }

        return country;
    }

    public void segundaConsulta() {
        String[] countries = new String[4];
        countries[0] = "USA";
        countries[1] = "Italy";
        countries[2] = "France";
        countries[3] = "UK";

        for (int i = 0; i < 4; i++) {
            ListaEnlazada<MovieCastMember> tempList = peopleByCountry.get(countries[i]);

            for (int j = 1; i < tempList.size(); i++) {
                if (tempList.get(j).getValue().getCategory().equals("director") || tempList.get(j).getValue().getCategory().equals("producer")) {
                    CauseOfDeath tempCause = peopleHash.get(tempList.get(j).getValue().getActorID()).getCauseOfDeath();
                    tempCause.incrementOcurrencia();
                }
            }

        }

        // ordeno lista y devuelvo top 5

    }


}

