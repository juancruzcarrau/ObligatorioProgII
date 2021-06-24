import TADs.arrayList.ArrayListImpl;
import TADs.hash.HashCerrado;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import entities.*;

import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;

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
                        if (!valores[11].equals("") && !valores[11].equals("undisclosed")) {
                            dc = new CauseOfDeath(valores[11]);
                            deathCauses.add(dc);
                            cm.setCauseOfDeath(dc);
                        }
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

    private static void primeraConsulta() {

        HashCerrado<String, Ocurrencias<MovieCastMember>> hashDeActoresYActrices = new HashCerrado<>(300000);    //ArrayList que contentra las apariciones de los actores
        ArrayListImpl<MovieCastMember> listaActores = categoryHash.get("actor");        //ArrayList de castmembers que son actores
        ArrayListImpl<MovieCastMember> listaDeActrices = categoryHash.get("actress");   //ArrayList de castmembers que son actrices
        ArrayListImpl<MovieCastMember> listaDeActoresYActrices = listaActores.concatenate(listaDeActrices); //Concatencacion de las dos ArrayList anteriores

        for (int i = 0; i < listaDeActoresYActrices.size(); i++) {  //Se itera en la lista
            MovieCastMember castMember = listaDeActoresYActrices.get(i);
            if (!hashDeActoresYActrices.contains(castMember.getActorID())) {
                //Si es la primera vez que aparece, se crea una nueva instancia de Ocurrencias
                hashDeActoresYActrices.put(castMember.getActorID(), new Ocurrencias<>(castMember, 1));
            } else {
                //Si ya aparecio antes, se le incrementa las ocurrencias
                Ocurrencias<MovieCastMember> aparicionesDeCastMember = hashDeActoresYActrices.get(castMember.getActorID());
                aparicionesDeCastMember.incrementarOcurrencias();
            }
        }

        ArrayListImpl<Ocurrencias<MovieCastMember>> aparicionesList = hashDeActoresYActrices.getValues();   //Se obtiene una ArrayList con todas las apariciones
        aparicionesList.sort(); //y se la ordena en orden ascendente

        for (int i = aparicionesList.size() - 1; i > aparicionesList.size() - 6; i--) {
            //Se itera en los ultimos  valores de la ArrayList ordenada y se imprime la informacion
            CastMember castMember = peopleHash.get(aparicionesList.get(i).getObject().getActorID());
            System.out.println("Nombre actor/actriz: " + castMember.getName());
            System.out.println("Cantidad de apariciones: " + aparicionesList.get(i).getOcurrences());
        }
    }

    public static void segundaConsulta() {
        HashCerrado<String,CastMember> alreadyConsideredPeople = new HashCerrado<>(300000);
        HashCerrado<CauseOfDeath, CauseOcurrence> deathHash = new HashCerrado<>(5000);
        ArrayListImpl<MovieCastMember> directors = categoryHash.get("director");
        ArrayListImpl<MovieCastMember> producers = categoryHash.get("producer");
        ArrayListImpl<MovieCastMember> dirprod = directors.concatenate(producers);

        for (int i = 0; i < dirprod.size(); i++) {

            CastMember person = peopleHash.get(dirprod.get(i).getActorID());

            if (!alreadyConsideredPeople.contains(person.getImdbNameId())) {
                if (person.getBirthCountry() != null) {
                    if (!person.getBirthCountry().equals("") && person.getCauseOfDeath() != null) {

                        if (person.getBirthCountry().contains("USA") || person.getBirthCountry().contains("UK") ||
                                person.getBirthCountry().contains("Italy") || person.getBirthCountry().contains("France")) {

                            if (deathHash.contains(person.getCauseOfDeath())) { // si la causa de muerte ya esta registrada
                                CauseOcurrence ocurrence = deathHash.get(person.getCauseOfDeath());
                                ocurrence.incrementOcurrence();
                            } else { // si no esta registrada, la creo
                                CauseOcurrence ocurrence = new CauseOcurrence(person.getCauseOfDeath());
                                deathHash.put(person.getCauseOfDeath(), ocurrence);
                            }

                            alreadyConsideredPeople.put(person.getImdbNameId(),person);
                        }
                    }
                }
            }

            else {
                continue;
            }
        }

        ArrayListImpl<CauseOcurrence> causesWithOcurrence = deathHash.getValues();

        try {
            causesWithOcurrence.sort();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = causesWithOcurrence.size() - 1; i > causesWithOcurrence.size() - 6; i--) {
            System.out.println("Causa de muerte:" + causesWithOcurrence.get(i).getCause().getName() + "\n" +
                    "Cantidad de personas:" + causesWithOcurrence.get(i).getOcurrence());
        }

    }

    private static void terceraConsulta() {
        ArrayListImpl<Movie> moviesList = moviesHash.getValues();   //Se obtiene una ArrayList de todas las peliculas
        ArrayListImpl<Movie> moviesInYears = new ArrayListImpl<>(5000); //Numero inicial arbitrario

        int fechaInicio = 1950;
        int fechaFin = 1960;

        for (int i = 0; i < moviesList.size(); i++) { //Se itera en las peliculas
            Movie movie = moviesList.get(i);
            if(movie.getYear() >= fechaInicio && movie.getYear() <= fechaFin){
                moviesInYears.add(movie);   //Si la pelicula esta entre 1950 y 1960 se la agrega a la lista
            }
        }

        moviesInYears.sort();   //Se ordena la lista en orden ascendente
        ArrayListImpl<String> moviesIdList = new ArrayListImpl<>(14);   //Se crea una ArrayLis que guardara las 14 peliculas con mayor weightedAverage
        HashCerrado<String, ArrayListImpl<String>> actorsInMovies = new HashCerrado<>(19);  //Hash que tendra ArrayLists para cada pelicula, en la que cada arraylist
                                                                                                // tiene los actores de cada pelicula. Se inicializa en 1 para que no haya ue hacer un rehash

        for (int i = moviesInYears.size() - 1; i > moviesInYears.size()-15; i--) {
            moviesIdList.add(moviesInYears.get(i).getImbdTitleId());    //Se guarda el ImbdTitleId en la lista
            actorsInMovies.put(moviesInYears.get(i).getImbdTitleId(), new ArrayListImpl<>(5));  //Se crea y guarda en el hash el arraylist para cada pelicula, mencionado previamente.
        }

        ArrayListImpl<MovieCastMember> listaActores = categoryHash.get("actor");        //ArrayList de castmembers que son actores
        ArrayListImpl<MovieCastMember> listaDeActrices = categoryHash.get("actress");   //ArrayList de castmembers que son actrices
        ArrayListImpl<MovieCastMember> listaDeActoresYActrices = listaActores.concatenate(listaDeActrices); //Concatencacion de las dos ArrayList anteriores

        for (int i = 0; i < listaDeActoresYActrices.size(); i++) {
            //Se itera entre todos los actores y actrices
            if(moviesIdList.contains(listaDeActoresYActrices.get(i).getMovieID())){ //Si el titulo de la pelicula en que tuvo el rol conincide con una de las 14
                actorsInMovies.get(listaDeActoresYActrices.get(i).getMovieID()).add(listaDeActoresYActrices.get(i).getActorID());   //Entonces se la agrega a la arraylist correspondiente
            }
        }

        for (int i = moviesInYears.size() - 1; i > moviesInYears.size()-15; i--) {  //Se itera en las ultimas 14 peliculas
            ArrayListImpl<String> actoresId = actorsInMovies.get(moviesInYears.get(i).getImbdTitleId());    //Se obtiene la lista de id de actores que estaba guardada en el hash
            ArrayListImpl<CastMember> actores = new ArrayListImpl<>(actoresId.size());  //Se crea una lista que contendra las entidades de actores de la pelicula

            for (int j = 0; j < actoresId.size(); j++) {
                actores.add(peopleHash.get(actoresId.get(j)));  //Se agregan las entidades de actores a la lista
            }

            int heightSum = 0;      //Suma de alturas inicializado en cero
            int amountOfActors = 0; //Cantidad de actores que tienen alturas distintas de cero inicializado en cero

            for (int j = 0; j < actores.size(); j++) {
                if(actores.get(j).getHeight() != 0){    //Si el actor tiene una altura distinta de cero
                    heightSum += actores.get(j).getHeight();       //Se agrega su altura para el promedio
                    amountOfActors++;                              //Se lo cuenta para el promedio
                }
            }

            if (amountOfActors != 0){       //Si tiene al menos un actor que tiene una altura distinta de cero, se imprime sus datos
                System.out.println("Id película: " + moviesInYears.get(i).getImbdTitleId());
                System.out.println("Nombre: " + moviesInYears.get(i).getTitle());
                System.out.println("Altura promedio de actores: " + heightSum/(double) amountOfActors); //Se hace el promedio haciendo la suma de altura de actores divido la cantidad de actores cuyas alturas eran distintas de 0
            }

        }

    }

    public static void cuartaConsulta() {
        ArrayListImpl<MovieCastMember> actors = categoryHash.get("actor");
        ArrayListImpl<MovieCastMember> actresses = categoryHash.get("actress");
        ArrayListImpl<Year> maleYears = new ArrayListImpl<>(500);
        ArrayListImpl<Year> femaleYears = new ArrayListImpl<>(500);
        HashCerrado<Integer, Year> maleYearsHash = new HashCerrado<>(500);
        HashCerrado<Integer, Year> femaleYearsHash = new HashCerrado<>(500);
        HashCerrado<String,CastMember> alreadyConsideredPeople = new HashCerrado<>(300000);


        for (int i = 0; i < actors.size(); i++) {

            CastMember actor = peopleHash.get(actors.get(i).getActorID());

            if (!alreadyConsideredPeople.contains(actor.getImdbNameId())) {
                if (actor.getBirthDate() != -1) {
                    //calendar.setTime(actor.getBirthDate());
                    //int tempYear = calendar.get(Calendar.YEAR);
                    int tempYear = actor.getBirthDate();

                    if (maleYearsHash.contains(tempYear)) {
                        Year yearWithOc = maleYearsHash.get(tempYear);
                        yearWithOc.incrementOcurrencias();
                    } else {
                        Year yearWithOc = new Year(tempYear);
                        maleYearsHash.put(tempYear, yearWithOc);
                    }

                }
                alreadyConsideredPeople.put(actor.getImdbNameId(), actor);
            }
            else {
                continue;
            }
        }

        ArrayListImpl<Year> menY = maleYearsHash.getValues();
        menY.sort();

        for (int i = 0; i < actresses.size(); i++) {

            CastMember actress = peopleHash.get(actresses.get(i).getActorID());

            if (!alreadyConsideredPeople.contains(actress.getImdbNameId())) {
                if (actress.getBirthDate() != -1) {
                    //calendar.setTime(actress.getBirthDate());
                    //int tempYear = calendar.get(Calendar.YEAR);
                    int tempYear = actress.getBirthDate();

                    if (femaleYearsHash.contains(tempYear)) {
                        Year yearWithOc = femaleYearsHash.get(tempYear);
                        yearWithOc.incrementOcurrencias();
                    } else {
                        Year yearWithOc = new Year(tempYear);
                        femaleYearsHash.put(tempYear, yearWithOc);
                    }
                }
                alreadyConsideredPeople.put(actress.getImdbNameId(), actress);
            }
            else {
                continue;
            }
        }

        ArrayListImpl<Year> womenY = femaleYearsHash.getValues();
        womenY.sort();
        int anoHombres = menY.get(menY.size()-1).getYear();
        int ocuHombres = menY.get(menY.size()-1).getOcurrencias();
        int anoMujeres = womenY.get(womenY.size()-1).getYear();
        int ocuMujeres = womenY.get(womenY.size()-1).getOcurrencias();

        System.out.println("Actores:");
        System.out.println('\t' + "Año: " + anoHombres);
        System.out.println('\t' + "Cantidad: " + ocuHombres + '\n');
        System.out.println("Actrices:");
        System.out.println('\t' + "Año: " + anoMujeres);
        System.out.println('\t' + "Cantidad: " + ocuMujeres);
    }

    private static void quintaConsulta(){
        ArrayListImpl<MovieCastMember> listaActores = categoryHash.get("actor");        //ArrayList de castmembers que son actores
        ArrayListImpl<MovieCastMember> listaDeActrices = categoryHash.get("actress");   //ArrayList de castmembers que son actrices
        ArrayListImpl<MovieCastMember> listaDeActoresYActrices = listaActores.concatenate(listaDeActrices); //Concatencacion de las dos ArrayList anteriores

        ArrayListImpl<Movie> moviesAlreadyAccounted = new ArrayListImpl<>(50000);   //Arraylist que contiene las peliculas que cumplen la condiccion. La idea es que aca no hayan peliculas repetidas.
        HashCerrado<String, Ocurrencias<String>> hashGeneros = new HashCerrado<>(15);   //Un hash con los generos como key y sus ocurrencias

        for (int i = 0; i < listaDeActoresYActrices.size(); i++) { //Se itera en los actores
            MovieCastMember movieCM = listaDeActoresYActrices.get(i);

            if(peopleHash.get(movieCM.getActorID()).getChildren() >= 2){       //En caso de que tengan dos o mas hijos, se procede a agregar la peliucula
                Movie movie = moviesHash.get(movieCM.getMovieID());            // Se obtiene la pelicula desde el hash

                if(!moviesAlreadyAccounted.contains(movie)){    //Si es la primera vez que aparece esta pelicula, se procede a agregarla
                    moviesAlreadyAccounted.add(movie);          //Se la agrega

                    for (int j = 0; j < movie.getGenre().size(); j++) {     //Se itera en los generos de la pelicula
                        Ocurrencias<String> ocurrenciasGenero;
                        if(hashGeneros.contains(movie.getGenre().get(j))){  //Si este genero ya existe en el hash de generos
                            ocurrenciasGenero = hashGeneros.get(movie.getGenre().get(j));   //Se pide la instancia que cuenta sus ocurrencias
                            ocurrenciasGenero.incrementarOcurrencias();     //Y se la incrementa
                        } else {
                            ocurrenciasGenero = new Ocurrencias<>(movie.getGenre().get(j), 1);  //En caso de que no exista se la crea, inicializado en 1
                            hashGeneros.put(movie.getGenre().get(j), ocurrenciasGenero);                 //Y esta instancia es agregada al hash de generos con el genero como key
                        }
                    }
                }
            }
        }

        ArrayListImpl<Ocurrencias<String>> listaOcurrencias = hashGeneros.getValues();  //Se obtiene una ArrayList de todas las ocurrencias de los generos
        listaOcurrencias.sort();    //Y se los ordena en orden ascendente

        for (int i = listaOcurrencias.size() - 1; i > listaOcurrencias.size() - 11; i--) {  //Se itera en los ultimos 10 y se imprimen los ultimos 10
            System.out.println("Genero pelicula:" + listaOcurrencias.get(i).getObject());
            System.out.println("Cantidad:" + listaOcurrencias.get(i).getOcurrences());
        }
    }

}

