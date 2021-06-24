import TADs.arrayList.ArrayListImpl;
import TADs.hash.HashCerrado;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.*;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class Main {

    // declaracion de variables estaticas a utilizar durante la carga de datos y consultas:

    // generales
    static Scanner scanner = new Scanner(System.in);
    static boolean datosCargados = false;

    // cast members
    static HashCerrado<String,CastMember> peopleHash = new HashCerrado<>(400000);
    static ArrayListImpl<CauseOfDeath> deathCauses = new ArrayListImpl<>(10000);

    // movies
    static HashCerrado<String, Movie> moviesHash = new HashCerrado<>(115000);

    // movie cast members
    static HashCerrado<String, ArrayListImpl<MovieCastMember>> categoryHash = new HashCerrado<>(15);
    static Calendar calendar = new GregorianCalendar();

    public static void main(String[] args){

        // menu principal
        while(true){
            System.out.println("Seleccione la opción que desee:");
            System.out.println("\t1. Carga de datos");
            System.out.println("\t2. Ejecutar consultas");
            System.out.println("\t3. Salir");
            System.out.print("Seleccion: ");

            int seleccion;

            // se verifica que los datos ingresados sean validos
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

            if(seleccion == 1){ // si se elige opcion 1, se cargan datos. calculando tiempo de duracion del proceso
                if (!datosCargados) {
                    long startTime = System.currentTimeMillis();
                    cargarDatos();
                    long endTime = System.currentTimeMillis();
                    System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga:" + (endTime - startTime));

                    datosCargados = true;
                } else {
                    System.out.println("Los datos ya se han cargado.");
                }
            } else if (seleccion == 2){ // si se elige opcion 2, se va al menu de consultas
                ejectutarConsultas();
            } else if (seleccion == 3){ // si se elige opcion 3, se finaliza el programa
                break;
            } else {
                throw new RuntimeException("Ha ocurrido un error. Seleccion no puede ser un nro distinto de 1, 2 o 3.");
            }
        }
    }

    private static void ejectutarConsultas() {
        // menu de consultas
        while (true) {
            System.out.println("Seleccione la opción que desee:");
            System.out.println("\t1. Indicar el Top 5 de actores/actrices que más apariciones han tenido a lo largo de los años.");
            System.out.println("\t2. Indicar el Top 5 de las causas de muerte más frecuentes en directores y productores nacidos en Italia, Estados Unidos, Francia y UK.");
            System.out.println("\t3. Mostrar de las 14 películas con más weightedAverage, el promedio de altura de sus actores/actrices si su valor es distinto de nulo.");
            System.out.println("\t4. Indicar el año más habitual en el que nacen los actores y las actrices.");
            System.out.println("\t5. Indicar el Top 10 de géneros de películas más populares, en las cuales al menos un actor/actriz tiene 2 o más hijos.");
            System.out.println("\t6. Salir.");

            // se verifica que los datos ingresados sean validos
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

            // previo a la ejecucion, toma el tiempo, y luego de terminar calcula el tiempo que llevo la consulta
            if(seleccionConsulta == 1){
                long startTime = System.currentTimeMillis();
                primeraConsulta();
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));
            } else if (seleccionConsulta == 2){
                long startTime = System.currentTimeMillis();
                segundaConsulta();
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));

            } else if (seleccionConsulta == 3){
                long startTime = System.currentTimeMillis();
                terceraConsulta();
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));

            } else if (seleccionConsulta == 4){
                long startTime = System.currentTimeMillis();
                cuartaConsulta();
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));

            } else if (seleccionConsulta == 5){
                long startTime = System.currentTimeMillis();
                quintaConsulta();
                long endTime = System.currentTimeMillis();
                System.out.println("Tiempo de ejecucion de la consulta:" + (endTime - startTime));

            } else if (seleccionConsulta == 6){ // si no se elige ninguna consulta, se vuelve al menu principal
                break;
            }
        }

    }

    private static void cargarDatos() {

        //Carga de peliculas
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb movies.csv")).withSkipLines(1).build()) {  // creo instancia de CSV Reader que lee el archivo en cuestion
            String[] valores; // array temporal que almacenara una linea leida y procesada por el CSV Reader

            while ((valores = csvReader.readNext()) != null) {  // mientras siga habiendo lineas en el archivo
                Movie movie = new Movie(valores); // creo instancia
                moviesHash.put(movie.getImbdTitleId(), movie); // agrego al hash de movies
            }
        } catch (IOException | CsvValidationException | ParseException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }

        //Carga de ratings a las peliculas
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb ratings.csv")).withSkipLines(1).build()) {  // creo instancia de CSV Reader que lee el archivo en cuestion
            String[] valores; // array temporal que almacenara una linea leida y procesada por el CSV Reader
            while ((valores = csvReader.readNext()) != null) { // mientras siga habiendo lineas en el archivo
                MovieRating rating = new MovieRating(valores); // creo instancia
                moviesHash.get(valores[0]).setMovieRating(rating); // set de MovieRating a su correspondiente movie
            }
        } catch (IOException | CsvValidationException e) {
            //Nunca se deberia llegar aca
            e.printStackTrace();
        }

        //Carga de castMembers y causas de muerte
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("dataset/IMDb names.csv")).withSkipLines(1).build()) {  // creo instancia de CSV Reader que lee el archivo en cuestion
            String[] valores = null; // array temporal que almacenara una linea leida y procesada por el CSV Reader

            while ((valores = csvReader.readNext()) != null) { // mientras siga habiendo lineas en el archivo

                try {
                    CastMember cm = new CastMember(valores); // creo intancia
                    CauseOfDeath dc = null; // declaro variable

                    for (int i = 0; i < deathCauses.size(); i++) { // itero entre todas las causas de muertes
                        if (deathCauses.get(i).getName().equals(valores[11])) { // si ya esta registrada
                            dc = deathCauses.get(i); // puntero a la causa de muerte ya registrada
                            break;
                        }
                    }

                    if (dc != null) {
                        cm.setCauseOfDeath(dc);  // le seteo la susodicha al nuevo CastMember
                    }
                    else { // si no esta registrada la causa de muerte:
                        if (!valores[11].equals("") && !valores[11].equals("undisclosed")) { // no considero causas "" y "undisclosed"
                            dc = new CauseOfDeath(valores[11]); // creo instanncia
                            deathCauses.add(dc); // la agrego al registro de causas de muerte
                            cm.setCauseOfDeath(dc); // se la seteo al CastMember
                        }
                    }

                    peopleHash.put(cm.getImdbNameId(),cm); // agrego el CastMember al Hash de personas

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
            String[] valores; // array temporal que almacenara una linea leida y procesada por el CSV Reader
            String strCurrentLine; // la linea en cuestion
            BufferedReader objReader; // creo instancia de BufferedReader
            objReader = new BufferedReader(new InputStreamReader(new FileInputStream("dataset/IMDb title_principals.csv"), "UTF-8")); // selecciono archivo en cuestion y su codificacion
            objReader.readLine(); // SALTEO DEL CABEZAL
            while ((strCurrentLine = objReader.readLine()) != null) { // mientras siga habiendo lineas en el archivo
                valores = strCurrentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // split por comas, solo si estan fuera de comillas dobles
                MovieCastMember movieCM = new MovieCastMember(valores); // creo instancia
                ArrayListImpl<MovieCastMember> categoryList; // variable que sera una lista con todos los de misma "category" del nuevo movieCM
                if(categoryHash.contains(movieCM.getCategory())){
                    categoryList = categoryHash.get(movieCM.getCategory()); // si la "category" ya existe, agrego a esa lista
                } else {
                    categoryList = new ArrayListImpl<>(1000);
                    categoryHash.put(movieCM.getCategory() ,categoryList); // sino, creo la lista
                }

                categoryList.add(movieCM); // y agrego el movieCM a la lista recien creada
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void segundaConsulta() {
        HashCerrado<String,CastMember> alreadyConsideredPeople = new HashCerrado<>(300000); // se crea Hash que contendra las personas que ya fueron consideradas
        HashCerrado<CauseOfDeath, CauseOcurrence> deathHash = new HashCerrado<>(2500); // se crea hash con causas de muertes y sus respectivas ocurrencias
        ArrayListImpl<MovieCastMember> directors = categoryHash.get("director"); // ArrayList de directores
        ArrayListImpl<MovieCastMember> producers = categoryHash.get("producer"); // ArrayList de productores
        ArrayListImpl<MovieCastMember> dirprod = directors.concatenate(producers); // Concatenacion de las dos anteriores

        for (int i = 0; i < dirprod.size(); i++) { // itero en la lista

            CastMember person = peopleHash.get(dirprod.get(i).getActorID()); // puntero temporal a la persona

            if (!alreadyConsideredPeople.contains(person.getImdbNameId())) { // si la persona todavia no fue considerada
                if (person.getBirthCountry() != null) { // pais de nacimiento no nulo (para evitar inconsistencias)
                    if (!person.getBirthCountry().equals("") && person.getCauseOfDeath() != null) { // pais de nacimiento no vacio y causa no nula
                      // con las dos lineas anteriores, se evitan analizar personas con datos incompletos

                        if (person.getBirthCountry().contains("USA") || person.getBirthCountry().contains("UK") ||
                                person.getBirthCountry().contains("Italy") || person.getBirthCountry().contains("France")) { // si la persona nacio en uno de estos 4 paises

                            if (deathHash.contains(person.getCauseOfDeath())) { // si la causa de muerte ya esta registrada
                                CauseOcurrence ocurrence = deathHash.get(person.getCauseOfDeath());
                                ocurrence.incrementOcurrence(); // sumo una ocurrencia
                            } else { // si no esta registrada, la creo
                                CauseOcurrence ocurrence = new CauseOcurrence(person.getCauseOfDeath());
                                deathHash.put(person.getCauseOfDeath(), ocurrence); // agrego al hash local de causas de muerte
                            }

                            alreadyConsideredPeople.put(person.getImdbNameId(),person); // como la persona no habia sido considerada aun, la agrego al hash de personas ya consideradas
                        }
                    }
                }
            }

            else {
                continue; // si la persona ya fue considerada, paso a la siguiente iteracion
            }
        }

        ArrayListImpl<CauseOcurrence> causesWithOcurrence = deathHash.getValues(); // obtengo las causas de muertes con sus respectivas ocurrencias

        try {
            causesWithOcurrence.sort(); // ordeno por mergesort, comparando por cantidad de ocurrencias
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = causesWithOcurrence.size() - 1; i > causesWithOcurrence.size() - 6; i--) { // devuelvo top 5
            System.out.println("Causa de muerte:" + causesWithOcurrence.get(i).getCause().getName() + "\n" +
                    "Cantidad de personas:" + causesWithOcurrence.get(i).getOcurrence());
        }

    }

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

    public static void cuartaConsulta() {
        ArrayListImpl<MovieCastMember> actors = categoryHash.get("actor"); // creo ArrayList filtrando solo actores
        ArrayListImpl<MovieCastMember> actresses = categoryHash.get("actress"); // creo ArrayList filtrando solo actrices
        HashCerrado<Integer, Year> maleYearsHash = new HashCerrado<>(500); // creo hash que contendra anos de nacimiento de actores y sus ocurrencias
        HashCerrado<Integer, Year> femaleYearsHash = new HashCerrado<>(500); // creo hash que contendra anos de nacimiento de actrices y sus ocurrencias
        HashCerrado<String,CastMember> alreadyConsideredPeople = new HashCerrado<>(300000); // se crea Hash que contendra las personas que ya fueron consideradas

        for (int i = 0; i < actors.size(); i++) { // itero entre actores

            CastMember actor = peopleHash.get(actors.get(i).getActorID()); // puntero al actor temporal

            if (!alreadyConsideredPeople.contains(actor.getImdbNameId())) { // si la persona no fue considerada
                if (actor.getBirthDate() != -1) { // si es -1 es porque el campo estaba vacio

                    int tempYear = actor.getBirthDate(); // puntero al ano del actor temporal

                    if (maleYearsHash.contains(tempYear)) {
                        Year yearWithOc = maleYearsHash.get(tempYear); // si el ano ya estaba, le sumo ocurrencia
                        yearWithOc.incrementOcurrencias();
                    } else {
                        Year yearWithOc = new Year(tempYear);
                        maleYearsHash.put(tempYear, yearWithOc); // si no, lo  creo y lo agrego al hash
                    }

                }
                alreadyConsideredPeople.put(actor.getImdbNameId(), actor); // por no estar considerada de antes, la agrego al hash de considerados
            }
            else {
                continue; // si la persona ya fue considerada, paso a la siguiente iteracion
            }
        }

        ArrayListImpl<Year> menY = maleYearsHash.getValues(); // obtengo anos con sus ocurrencias
        menY.sort(); // mergesort por ocurrencias

        for (int i = 0; i < actresses.size(); i++) {  // itero entre actrices

            CastMember actress = peopleHash.get(actresses.get(i).getActorID());  // puntero a la actriz temporal

            if (!alreadyConsideredPeople.contains(actress.getImdbNameId())) { // si la persona no fue considerada
                if (actress.getBirthDate() != -1) { // si es -1 es porque el campo estaba vacio

                    int tempYear = actress.getBirthDate(); // puntero al ano de la actriz temporal

                    if (femaleYearsHash.contains(tempYear)) {
                        Year yearWithOc = femaleYearsHash.get(tempYear); // si el ano ya estaba, le sumo ocurrencia
                        yearWithOc.incrementOcurrencias();
                    } else {
                        Year yearWithOc = new Year(tempYear);
                        femaleYearsHash.put(tempYear, yearWithOc); // si no, lo  creo y lo agrego al hash
                    }
                }
                alreadyConsideredPeople.put(actress.getImdbNameId(), actress); // por no estar considerada de antes, la agrego al hash de considerados
            }
            else {
                continue; // si la persona ya fue considerada, paso a la siguiente iteracion
            }
        }

        ArrayListImpl<Year> womenY = femaleYearsHash.getValues(); // obtengo anos con sus ocurrencias
        womenY.sort(); // mergesort por ocurrencias

        int anoHombres = menY.get(menY.size()-1).getYear(); // obtengo ano con mas actores nacidos
        int ocuHombres = menY.get(menY.size()-1).getOcurrencias(); // obtengo cantidad de acotres que nacieron ese ano
        int anoMujeres = womenY.get(womenY.size()-1).getYear(); // obtengo ano con mas actrices nacidos
        int ocuMujeres = womenY.get(womenY.size()-1).getOcurrencias(); // obtengo cantidad de actrices que nacieron ese ano

        // prints segun la letra
        System.out.println("Actores:");
        System.out.println('\t' + "Año: " + anoHombres);
        System.out.println('\t' + "Cantidad: " + ocuHombres + '\n');
        System.out.println("Actrices:");
        System.out.println('\t' + "Año: " + anoMujeres);
        System.out.println('\t' + "Cantidad: " + ocuMujeres);
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

