import TADs.arrayList.ArrayListImpl;
import TADs.hash.HashCerrado;
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
    static ArrayListImpl<CastMember> peopleList = new ArrayListImpl<>(300000);
    static HashCerrado<String,CastMember> peopleHash = new HashCerrado<>(400000);
    static ArrayListImpl<CauseOfDeath> deathCauses = new ArrayListImpl<>(10000);

    // movies
    static ArrayListImpl<Movie> movies = new ArrayListImpl<>(86000);
    static HashCerrado<String, Movie> moviesHash = new HashCerrado<>(115000);

    // movie cast members
    static ArrayListImpl<MovieCastMember> characters = new ArrayListImpl<>(835494);
    static HashCerrado<String, ArrayListImpl<MovieCastMember>> peopleByCountry = new HashCerrado<>(300, 0.75);

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
                long startTime = System.currentTimeMillis();
                primeraConsulta();
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));
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

    private static void primeraConsulta() {

        HashCerrado<String, Apariciones> hashDeActores = new HashCerrado<>(10000);
        for (int i = 0; i < characters.size(); i++) {
            MovieCastMember castMember = characters.get(i);
            if (castMember.getCategory().equals("actor") || castMember.getCategory().equals("actress")){
                if (!hashDeActores.contains(castMember.getActorID())) {
                    hashDeActores.put(castMember.getActorID(), new Apariciones(castMember, 1));
                } else {
                    Apariciones aparicionesDeCastMember = hashDeActores.get(castMember.getActorID());
                    aparicionesDeCastMember.agregarAparicion();
                }
            }
//            if (castMember.getCategory().equals("actor") || castMember.getCategory().equals("actress")){
//                Apariciones aparicion = null;
//                for (int j = 0; j < listaDeActores.size(); j++) {
//                    Apariciones temp = listaDeActores.get(j);
//                    if(temp.getActor().getActorID().equals(castMember.getActorID())){
//                        aparicion = temp;
//                        break;
//                    }
//                }
//
//                if (aparicion == null){
//                    aparicion = new Apariciones(castMember, 1);
//                    listaDeActores.add(aparicion);
//                }
//
//                aparicion.agregarAparicion();
//            }
        }

        ArrayListImpl<String> aparicionesKeys = hashDeActores.getKeys();
        ArrayListImpl<Apariciones> aparicionesList = new ArrayListImpl<>(aparicionesKeys.size());
        for (int i = 0; i < aparicionesKeys.size(); i++) {
            try {
                String key = aparicionesKeys.get(i);
                Apariciones apariciones = hashDeActores.get(key);
                aparicionesList.add(apariciones);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        aparicionesList.sort();

        for (int i = aparicionesList.size(); i > aparicionesList.size() - 5; i--) {
            CastMember castMember = peopleHash.get(aparicionesList.get(i-1).getActor().getActorID());
            System.out.println("Nombre actor/actriz: " + castMember.getName());
            System.out.println("Cantidad de apariciones: " + aparicionesList.get(i-1).getCantidadDeApariciones());
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

            while ((valores = csvReader.readNext()) != null) {

                try {
                    CastMember cm = new CastMember(valores);
                    CauseOfDeath dc = null;

                    for (int i = 0; i < deathCauses.size(); i++) {
                        if (deathCauses.get(i).getName().equals(valores[11])) {
                            dc = deathCauses.get(i);
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
                    peopleHash.put(cm.getImdbNameId(),cm);

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
            while ((strCurrentLine = objReader.readLine()) != null) {
                valores = strCurrentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                MovieCastMember movieCM = new MovieCastMember(valores);
                characters.add(movieCM);
                String country = getCountryFromMovieCM(movieCM);
                if (country != null) {
                    if (!peopleByCountry.contains(country)) {
                        ArrayListImpl<MovieCastMember> tempCountryList = new ArrayListImpl<>(3000);
                        tempCountryList.add(movieCM);
                        peopleByCountry.put(country, tempCountryList);
                    } else {
                        ArrayListImpl<MovieCastMember> tempList = peopleByCountry.get(country);
                        tempList.add(movieCM);
                    }
                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCountryFromMovieCM(MovieCastMember movieCM) {
        String country = null;
        String key = movieCM.getActorID();

        if (peopleHash.contains(key)) {
            country  = peopleHash.get(key).getBirthCountry();
        }

        return country;
    }

    public static void segundaConsulta() {
        String[] countries = new String[4];
        countries[0] = "USA";
        countries[1] = "Italy";
        countries[2] = "France";
        countries[3] = "UK";

        for (int i = 0; i < 4; i++) {
            ArrayListImpl<MovieCastMember> tempList = peopleByCountry.get(countries[i]);

            for (int j = 0; i < tempList.size(); i++) {
                if (tempList.get(j).getCategory().equals("director") || tempList.get(j).getCategory().equals("producer")) {
                    CauseOfDeath tempCause = peopleHash.get(tempList.get(j).getActorID()).getCauseOfDeath();
                    tempCause.incrementOcurrencia();
                }
            }

        }

        deathCauses.sort(); // falta devolver top 5

    }


}

