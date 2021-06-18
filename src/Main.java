import TADs.arrayList.ArrayListImpl;
import TADs.hash.HashCerrado;
import TADs.listaSimpleFC.ListaEnlazada;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.*;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class Main {



    static Scanner scanner = new Scanner(System.in);

    // cast members
    static ArrayListImpl<CastMember> peopleList = new ArrayListImpl<>(300000);
    static HashCerrado<String,CastMember> peopleHash = new HashCerrado<>(400000, 1);
    static ArrayListImpl<CauseOfDeath> deathCauses = new ArrayListImpl<>(10000);

    // movies
    static ListaEnlazada<Movie> movies = new ListaEnlazada<>();
    static HashCerrado<String, Movie> moviesHash = new HashCerrado<>(115000,1);

    // movie cast members
    static ArrayListImpl<MovieCastMember> characters = new ArrayListImpl<>(850000);
    static HashCerrado<String, ArrayListImpl<MovieCastMember>> peopleByCountry = new HashCerrado<>(300, 0.75);

    // otros
    static Calendar calendar = new GregorianCalendar();

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
                segundaConsulta();
            } else if (seleccionConsulta == 3){
//                terceraConsulta();
            } else if (seleccionConsulta == 4){
                cuartaConsulta();
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

            //int countttt = 0;

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
                        if (!valores[11].equals("") && !valores[11].equals("undisclosed")) {
                            dc = new CauseOfDeath(valores[11]);
                            deathCauses.add(dc);
                            cm.setCauseOfDeath(dc);
                        }
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

        //System.out.println("size lista peliculas: "+movies.size() + " y tiene que dar 85855");
        //System.out.println("size lista castMembers: "+peopleList.size() + " y tiene que dar 297705");
        //System.out.println("size lista movieCastMembers: "+characters.size() + " y tiene que dar 835493");
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
        long startTime = System.currentTimeMillis();
        String[] countries = new String[4];
        countries[0] = "USA";
        countries[1] = "Italy";
        countries[2] = "France";
        countries[3] = "UK";

        for (int i = 0; i < 4; i++) {
            ArrayListImpl<MovieCastMember> tempList = peopleByCountry.get(countries[i]);

            for (int j = 0; j < tempList.size(); j++) {
                if (tempList.get(j).getCategory().equals("director") || tempList.get(j).getCategory().equals("producer")) {
                    CauseOfDeath tempCause = peopleHash.get(tempList.get(j).getActorID()).getCauseOfDeath();
                    if (tempCause != null) {
                        tempCause.incrementOcurrencia();
                    }
                }
            }

        }

        try {
            deathCauses.sort();
        }
        catch (Exception e) {
            int size = deathCauses.size();
            e.printStackTrace();
        }

        for (int i = deathCauses.size() - 1; i > deathCauses.size() - 6; i--) {
            System.out.println("Causa de muerte:" + deathCauses.get(i).getName() + "\n" +
                    "Cantidad de personas:" + deathCauses.get(i).getOcurrencia());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));

        // FALTA RESETEAR OCURRENCIAS

    }

    public static void cuartaConsulta() {
        long startTime = System.currentTimeMillis();
        ArrayListImpl<Year> maleYears = new ArrayListImpl<>(500);
        ArrayListImpl<Year> femaleYears = new ArrayListImpl<>(500);

        for (int i = 0; i < characters.size(); i++) {

            if (characters.get(i).getCategory().equals("actor")) {
                CastMember person = peopleHash.get(characters.get(i).getActorID());

                if (person.getBirthDate() != null) {
                    calendar.setTime(person.getBirthDate());
                    int tempYear = calendar.get(Calendar.YEAR);
                    Year year = new Year(tempYear);
                    if (!maleYears.contains(year)) {
                        maleYears.add(year);
                    } else {
                        maleYears.get(tempYear).incrementOcurrencias();     // FIXME: NUNCA ENTRA
                    }
                }


            }

            if (characters.get(i).getCategory().equals("actress")) {
                CastMember person = peopleHash.get(characters.get(i).getActorID());

                if (person.getBirthDate() != null) {
                    calendar.setTime(person.getBirthDate());
                    int tempYear = calendar.get(Calendar.YEAR);
                    Year year = new Year(tempYear);
                    if (!femaleYears.contains(year)) {
                        femaleYears.add(year);
                    } else {
                        femaleYears.get(tempYear).incrementOcurrencias();
                    }
                }

            }

        }

        maleYears.sort();
        femaleYears.sort();

        System.out.println("hombres:" + maleYears.get(maleYears.size()-1).getYear());
        System.out.println("hombres:" + maleYears.get(maleYears.size()-1).getOcurrencias());
        System.out.println("mujeres:" + femaleYears.get(femaleYears.size()-1).getYear());
        System.out.println("mujeres:" + femaleYears.get(femaleYears.size()-1).getOcurrencias());

        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));
    }


}

