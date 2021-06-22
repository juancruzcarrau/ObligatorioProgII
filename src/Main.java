import TADs.arrayList.ArrayListImpl;
import TADs.hash.HashCerrado;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.*;

import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static boolean datosCargados = false;

    // cast members
    static HashCerrado<String,CastMember> peopleHash = new HashCerrado<>(400000);
    static ArrayListImpl<CauseOfDeath> deathCauses = new ArrayListImpl<>(10000);

    // movies
    static HashCerrado<String, Movie> moviesHash = new HashCerrado<>(115000);

    // movie cast members
    static HashCerrado<String, ArrayListImpl<MovieCastMember>> categoryHash = new HashCerrado<>(15);

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
                if (!datosCargados) {
                    long startTime = System.currentTimeMillis();
                    cargarDatos();
                    long endTime = System.currentTimeMillis();
                    System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga:" + (endTime - startTime));

                    datosCargados = true;
                } else {
                    System.out.println("Los datos ya se han cargado.");
                }
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
                long startTime = System.currentTimeMillis();
                terceraConsulta();
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));
            } else if (seleccionConsulta == 4){
//                cuartaConsulta();
            } else if (seleccionConsulta == 5){
                long startTime = System.currentTimeMillis();
                quintaConsulta();
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));
            } else if (seleccionConsulta == 6){
                break;
            }
        }

    }

    private static void cargarDatos() {

        //Carga de peliculas
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb movies.csv")).withSkipLines(1).build()) {
            String[] valores;

            while ((valores = csvReader.readNext()) != null) {
                Movie movie = new Movie(valores);
                moviesHash.put(movie.getImbdTitleId(), movie);
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
                ArrayListImpl<MovieCastMember> categoryList;
                if(categoryHash.contains(movieCM.getCategory())){
                    categoryList = categoryHash.get(movieCM.getCategory());
                } else {
                    categoryList = new ArrayListImpl<>(1000);
                    categoryHash.put(movieCM.getCategory() ,categoryList);
                }

                categoryList.add(movieCM);
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

    private static void primeraConsulta() {

        HashCerrado<String, Apariciones> hashDeActoresYActrices = new HashCerrado<>(300000);
        ArrayListImpl<MovieCastMember> listaActores = categoryHash.get("actor");
        ArrayListImpl<MovieCastMember> listaDeActrices = categoryHash.get("actress");
        ArrayListImpl<MovieCastMember> listaDeActoresYActrices = listaActores.concatenate(listaDeActrices);

        for (int i = 0; i < listaDeActoresYActrices.size(); i++) {
            MovieCastMember castMember = listaDeActoresYActrices.get(i);
            if (!hashDeActoresYActrices.contains(castMember.getActorID())) {
                hashDeActoresYActrices.put(castMember.getActorID(), new Apariciones(castMember, 1));
            } else {
                Apariciones aparicionesDeCastMember = hashDeActoresYActrices.get(castMember.getActorID());
                aparicionesDeCastMember.agregarAparicion();
            }
        }

        ArrayListImpl<String> aparicionesKeys = hashDeActoresYActrices.getKeys();
        ArrayListImpl<Apariciones> aparicionesList = new ArrayListImpl<>(aparicionesKeys.size());
        for (int i = 0; i < aparicionesKeys.size(); i++) {
            try {
                String key = aparicionesKeys.get(i);
                Apariciones apariciones = hashDeActoresYActrices.get(key);
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

//    public static void segundaConsulta() {
//        String[] countries = new String[4];
//        countries[0] = "USA";
//        countries[1] = "Italy";
//        countries[2] = "France";
//        countries[3] = "UK";
//
//        for (int i = 0; i < 4; i++) {
//            ArrayListImpl<MovieCastMember> tempList = peopleByCountry.get(countries[i]);
//
//            for (int j = 0; i < tempList.size(); i++) {
//                if (tempList.get(j).getCategory().equals("director") || tempList.get(j).getCategory().equals("producer")) {
//                    CauseOfDeath tempCause = peopleHash.get(tempList.get(j).getActorID()).getCauseOfDeath();
//                    tempCause.incrementOcurrencia();
//                }
//            }
//
//        }
//
//        deathCauses.sort(); // falta devolver top 5
//
//    }

    private static void terceraConsulta() {
        ArrayListImpl<Movie> moviesList = moviesHash.getValues();
        ArrayListImpl<Movie> moviesInYears = new ArrayListImpl<>(5000); //Numero inicial arbitrario

        int fechaInicio = 1950; //Corresponde al inicio de 1950
        int fechaFin = 1960; //Corresponde al fin de 1960

        for (int i = 0; i < moviesList.size(); i++) {
            Movie movie = moviesList.get(i);
            if(movie.getYear() >= fechaInicio && movie.getYear() <= fechaFin){
                moviesInYears.add(movie);
            }
        }

        moviesInYears.sort();
        ArrayListImpl<String> moviesIdList = new ArrayListImpl<>(14);
        HashCerrado<String, ArrayListImpl<String>> actorsInMovies = new HashCerrado<>(19);

        for (int i = moviesInYears.size() - 1; i > moviesInYears.size()-15; i--) {
            moviesIdList.add(moviesInYears.get(i).getImbdTitleId());
            actorsInMovies.put(moviesInYears.get(i).getImbdTitleId(), new ArrayListImpl<>(5));
        }

        ArrayListImpl<MovieCastMember> listaActores = categoryHash.get("actor");
        ArrayListImpl<MovieCastMember> listaDeActrices = categoryHash.get("actress");
        ArrayListImpl<MovieCastMember> listaDeActoresYActrices = listaActores.concatenate(listaDeActrices);

        for (int i = 0; i < listaDeActoresYActrices.size(); i++) {
            if(moviesIdList.contains(listaDeActoresYActrices.get(i).getMovieID())){
                actorsInMovies.get(listaDeActoresYActrices.get(i).getMovieID()).add(listaDeActoresYActrices.get(i).getActorID());
            }
        }

        for (int i = moviesInYears.size() - 1; i > moviesInYears.size()-15; i--) {
            ArrayListImpl<String> actoresId = actorsInMovies.get(moviesInYears.get(i).getImbdTitleId());
            ArrayListImpl<CastMember> actores = new ArrayListImpl<>(actoresId.size());

            for (int j = 0; j < actoresId.size(); j++) {
                actores.add(peopleHash.get(actoresId.get(j)));
            }

            int heightSum = 0;
            int amountOfActors = 0;

            for (int j = 0; j < actores.size(); j++) {
                if(actores.get(j).getHeight() != 0){
                    heightSum += actores.get(j).getHeight();
                    amountOfActors++;
                }
            }

            if (amountOfActors != 0){
                System.out.println("Id película: " + moviesInYears.get(i).getImbdTitleId());
                System.out.println("Nombre: " + moviesInYears.get(i).getTitle());
                System.out.println("Altura promedio de actores: " + heightSum/(double) amountOfActors + '\n');
            }

        }

    }

    private static void quintaConsulta(){
        ArrayListImpl<MovieCastMember> listaActores = categoryHash.get("actor");
        ArrayListImpl<MovieCastMember> listaDeActrices = categoryHash.get("actress");
        ArrayListImpl<MovieCastMember> listaDeActoresYActrices = listaActores.concatenate(listaDeActrices);

        ArrayListImpl<Movie> moviesAlreadyAccounted = new ArrayListImpl<>(50000);
        HashCerrado<String, Ocurrencias<String>> hashGeneros = new HashCerrado<>(15);

        for (int i = 0; i < listaDeActoresYActrices.size(); i++) {
            MovieCastMember movieCM = listaDeActoresYActrices.get(i);

            if(peopleHash.get(movieCM.getActorID()).getChildren() >= 2){
                Movie movie = moviesHash.get(movieCM.getMovieID());

                if(!moviesAlreadyAccounted.contains(movie)){
                    moviesAlreadyAccounted.add(movie);

                    for (int j = 0; j < movie.getGenre().size(); j++) {
                        Ocurrencias<String> ocurrenciasGenero;
                        if(hashGeneros.contains(movie.getGenre().get(j))){
                            ocurrenciasGenero = hashGeneros.get(movie.getGenre().get(j));
                        } else {
                            ocurrenciasGenero = new Ocurrencias<>(movie.getGenre().get(j), 0);
                            hashGeneros.put(movie.getGenre().get(j), ocurrenciasGenero);
                        }

                        ocurrenciasGenero.incrementarOcurrencias();
                    }
                }
            }
        }

        ArrayListImpl<Ocurrencias<String>> listaOcurrencias = hashGeneros.getValues();
        listaOcurrencias.sort();

        for (int i = listaOcurrencias.size() - 1; i > listaOcurrencias.size() - 11; i--) {
            System.out.println("Genero pelicula:" + listaOcurrencias.get(i).getObject());
            System.out.println("Cantidad:" + listaOcurrencias.get(i).getOcurrences() + '\n');
        }
    }

}

