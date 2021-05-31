import java.io.*;
import java.util.*;

public class Pesca {
    private void altaUsuari() {
        // Si encontramos un usuario existente para poder realizar el insert de un usuario,
        // podremos realizar la creación. En caso de que el usuario que deseamos crear ya esté creado
        // lanzaremos una exception para indicar que ya está creado.
        FileWriter fileWriter;
        System.out.println("Indique Nombre de Usuario: ");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();
        try {
            fileWriter = new FileWriter("src/archivos/usuarios.txt", true);
            if (!buscadorUsuario(usuario)) {
                fileWriter.write("#" + usuario + "#\n");
                fileWriter.close();
            } else {
                throw new Exception("ERROR-> L'Usuari ja està registrat!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void reemplazador() throws IOException {
        /*
        * Método usado para reemplazar el fichero temp.txt dentro de usuarios.txt para realizar el borrado de un usuario*/
        FileReader reader = new FileReader("src/archivos/temp.txt");
        vaciarFichero("src/archivos/usuarios.txt");
        int i = reader.read();
        while(i != -1){
            FileWriter writer = new FileWriter("src/archivos/usuarios.txt",true);
            writer.write((char)i);
            writer.close();
            i = reader.read();
        }
        reader.close();
        vaciarFichero("src/archivos/temp.txt");
    }
    private void deleteUsuari() {
        /*
        * Método usado para borrar un usuario del programa, el cual primero buscaremos que exista, si lo encuetra,
        * no lo seleccionaremos y guardaremos en un fichero llamado temp.txt
        * todos los demas usuarios para realizar el guardado de los otros usuarios.*/
        System.out.println("Indique Nombre de Usuario: ");
        Scanner du = new Scanner(System.in);
        String usuario = du.nextLine();
        try {
            vaciarFichero("src/archivos/temp.txt");
            if (buscadorUsuario(usuario)) {
                FileReader fr = new FileReader("src/archivos/usuarios.txt");
                int i = fr.read();
                int contadorH=0;
                String line = "";
                while (i != -1) {
                    if(i == '#'){
                        contadorH++;
                    }
                    if(contadorH == 1){
                        line += (char)i;
                        if(i == '#'){
                            line="";
                        }
                    }
                    else if (contadorH == 2) {
                        if(!usuario.equals(line)){
                            FileWriter writer = new FileWriter("src/archivos/temp.txt",true);
                            writer.write("#"+line+"#\n");
                            writer.close();
                        }
                        line="";
                        contadorH=0;
                    }
                    i = fr.read();
                }
                fr.close();
                reemplazador();
            } else {
                throw new Exception("ERROR-> L'Usuari no està registrat!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectorPesca() throws Exception {
        // Metodo para seleccionar la pesquera el cual le pasamos un usuario, y si puede pescar,
        // procedemos a seleccionar la pesquera que quiera el usuario.
        System.out.println("Indique Nombre de Usuario: ");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();
        FileReader reader;
        try{
            if (buscadorUsuario(usuario)) {
                System.out.println("En quina pesca desitjaries pescar? ");
                System.out.println("Opcions disponibles: ");
                System.out.println("1) florida.txt | 2) mediterrania.txt | 3) africa.txt |4 ) japo.txt");
                System.out.println("OPCIÓ?");
                Scanner mp = new Scanner(System.in);
                String opcion = mp.nextLine();
                boolean separador = true;
                switch (opcion) {
                    case "1":
                        reader = new FileReader("src/archivos/florida.txt");
                        break;
                    case "2":
                        reader = new FileReader("src/archivos/mediterrania.txt");
                        break;
                    case "3":
                        reader = new FileReader("src/archivos/africa.txt");
                        break;
                    case "4":
                        reader = new FileReader("src/archivos/japo.txt");
                        break;
                    default:
                        throw new Exception("ERROR-> Opció no disponible");
                }
                pescarPesquera(reader,separador,usuario);
            }else{
                throw new Exception("L'Usuari no está registrat");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void pescarPesquera(FileReader reader, boolean separador, String usuario) {
        /*
        * Método para realizar la pesca.
        * Si encontramos que el porcentaje que leemos del archivo registre.txt es mayor al valor generado por el random
        * generaremos una variable llamada registroporcentaje que únicamente se creará si se cumple lo dicho anteriormente.
        * Si se crea, haremos el cálculo para sacar aleatoriamente un peso entre el mínimo y el máximo del pez.
        * Al crear la variable, luego guardaremos el pez pescado junto a la fecha actual, su nombre, el usuario que lo ha pescado
        * y su peso calculado.
        * */
        try {
            String leedorpesca = "";
            String nompescat = "";
            String pesoMax = "";
            String pesoMin = "";
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            int contadorhash = 0;
            double registroporcentaje = 0;
            double leedorpescaD;
            double pesoMinD = 0;
            double pesoMaxD;
            double valorAleatorio = Math.random();
            int i = reader.read();
            boolean banderita = true;
                while (banderita && i != -1) {
                    while (separador) {
                        if (i != 35) {
                            if (contadorhash == 1) {
                                nompescat += (char) i;
                            }
                            if (contadorhash == 2) {
                                leedorpesca += (char) i;
                            }
                            if (contadorhash == 3) {
                                leedorpescaD = Double.parseDouble(leedorpesca);
                                if (leedorpescaD > valorAleatorio) {
                                    registroporcentaje = leedorpescaD;
                                }
                                pesoMin += (char) i;
                            }
                            if (contadorhash == 4) {
                                pesoMinD = Double.parseDouble(pesoMin);
                                pesoMax += (char) i;
                            }
                            if (contadorhash == 5) {
                                pesoMaxD = Double.parseDouble(pesoMax);
                                if (registroporcentaje != 0) {
                                    FileWriter writer = new FileWriter("src/archivos/registre.txt", true);
                                    double pesopez = (Math.random() * ((pesoMaxD - pesoMinD))) + pesoMinD;
                                    writer.write("#" + dia + "/" + mes + "/" + año + "#" + nompescat + "#" + usuario + "#" + (Math.round(pesopez * 100.0) / 100.0) + "#\n");
                                    writer.close();
                                    banderita = false;
                                }
                                separador = false;
                                contadorhash = 0;
                                nompescat = "";
                                pesoMax = "";
                                pesoMin = "";
                                leedorpesca = "";
                                leedorpescaD = 0;
                                pesoMinD = 0;
                                pesoMaxD = 0;
                            }
                        } else {
                            contadorhash++;
                        }
                        separador = false;
                    }
                    separador = true;
                    i = reader.read();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String leerEstadisticas(){
        /*
        * Método para leer el fichero de estadisticas e ir sacando el tipo/nombre del pez y su peso.
        * */
        String devolverLectura="";
        try{
            String nomPezEstats ="";
            String pesoPez ="";
            String fecha="";
            int contadorH = 0;
            FileReader reader = new FileReader("src/archivos/estadisticas.txt");
            int i = reader.read();
            while(i != -1){
                if(i != 35){
                    if (contadorH == 1){
                        fecha += (char)i;
                    }
                    if(contadorH == 2){
                        nomPezEstats += (char)i;
                    }
                    if(contadorH == 3){
                        pesoPez += (char)i;
                    }
                    if(contadorH == 4){
                        devolverLectura += "Fecha de la pesca: "+fecha+" Nom del Peix: "+nomPezEstats+", pes des peix: "+pesoPez+"\n";
                    }
                    if (i == '\n'){
                        nomPezEstats="";
                        pesoPez="";
                        fecha="";
                        contadorH=0;
                    }
                }else{
                    contadorH++;
                }
                i = reader.read();
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return devolverLectura;
    }

    private void estatsGlobals(){
        /*
        * Método para leer las estadisticas totales registradas, dónde leeremos cada pesca máxima realizada por cada usuario.*/
        try{
            vaciarFichero("src/archivos/estadisticas.txt");
            String nomPez ="";
            String pesoPez ="";
            String nUsuario ="";
            String fecha="";
            int contadorH = 0;
            FileReader reader = new FileReader("src/archivos/registre.txt");
            int i = reader.read();
            while (i != -1){
                    if(i != 35){
                        if(contadorH == 1){
                            fecha += (char)i;
                        }
                        if(contadorH == 2) {
                            nomPez += (char) i;
                        }
                        if(contadorH == 3){
                            nUsuario += (char)i;
                        }
                        if(contadorH == 4){
                            pesoPez += (char)i;
                        }
                        if(contadorH == 5){
                            if (!buscarPez(nomPez)){
                                double pesoFinal = sacarPesoMaximo(fecha,nomPez,"");
                                FileWriter rellenarEstats = new FileWriter("src/archivos/estadisticas.txt",true);
                                rellenarEstats.write("#"+fecha+"#"+ nomPez + "#" + pesoFinal+"#\n");
                                rellenarEstats.close();
                            }
                            nomPez="";
                            nUsuario="";
                            pesoPez="";
                            fecha="";
                            contadorH=0;
                        }
                    }else{
                        contadorH++;
                    }
                i = reader.read();
            }
            reader.close();
            System.out.println(leerEstadisticas());
            vaciarFichero("src/archivos/estadisticas.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void estatsPerUsuari(String usuario){
        /*
        Método para filtrar las pescas por el nombre del usuario. Guardarlo en un fechero temporal para luego
        leerlo y mandar la lectura al fichero usuarios.txt
        * */
        try{
            vaciarFichero("src/archivos/estadisticas.txt");
            String nomPez ="";
            String pesoPez ="";
            String nUsuario ="";
            String fecha="";
            int contadorH = 0;
            FileReader reader = new FileReader("src/archivos/registre.txt");
            int i = reader.read();
            while (i != -1){
                if(i != 35){
                    if(contadorH == 1){
                        fecha += (char)i;
                    }
                    if(contadorH == 2) {
                        nomPez += (char) i;
                    }
                    if(contadorH == 3){
                        nUsuario += (char)i;
                    }
                    if(contadorH == 4){
                        pesoPez += (char)i;
                    }
                    if(contadorH == 5){
                        if (!buscarPez(nomPez) && nUsuario.equals(usuario)){
                            double pesoFinal = sacarPesoMaximo(fecha,nomPez,usuario);
                            FileWriter rellenarEstats = new FileWriter("src/archivos/estadisticas.txt",true);
                            rellenarEstats.write("#"+fecha+"#"+nomPez + "#" + pesoFinal+"#\n");
                            rellenarEstats.close();
                        }
                        nomPez="";
                        nUsuario="";
                        pesoPez="";
                        fecha="";
                        contadorH=0;
                    }
                }else{
                    contadorH++;
                }
                i = reader.read();
            }
            reader.close();
            System.out.println(leerEstadisticas());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private double sacarPesoMaximo(String fecha,String nomPeix,String usuario){
        /*
        * Método usado para encontrar el peso maximo de una especie de pez.*/
        double pesoMaximo=0;
        String fechapezGrande="";
        try{
            String nomPez ="";
            String pesoPez ="";
            String nUsuario="";
            String fechaPesca="";
            int contadorH = 0;
            FileReader reader = new FileReader("src/archivos/registre.txt");
            int i = reader.read();
            while(i != -1){
                if(i != 35){
                    if(contadorH == 1){
                        fechaPesca+=(char)i;
                    }
                    if(contadorH == 2){
                        nomPez += (char)i;
                    }
                    if(contadorH == 3){
                        nUsuario += (char)i;
                    }
                    if(contadorH == 4){
                        pesoPez += (char)i;
                    }
                    if (contadorH == 5){
                        double pesoPezD = Double.parseDouble(pesoPez);
                        if(usuario.equals("")){
                            if(pesoPezD > pesoMaximo && nomPeix.equals(nomPez)){
                                pesoMaximo = pesoPezD;
                                fechapezGrande=fecha;
                            }
                        }else if(pesoPezD > pesoMaximo && usuario.equals(nUsuario) && nomPeix.equals(nomPez)){
                                pesoMaximo = pesoPezD;
                                fechapezGrande=fecha;
                            }
                        nomPez="";
                        nUsuario="";
                        pesoPez="";
                        fechaPesca="";
                        contadorH=0;
                    }
                }else{
                    contadorH++;
                }
                i = reader.read();
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return pesoMaximo;
    }

    private boolean buscarPez(String Pez){
        /*
        * Método para el cual nosotros al pasarle el nombre de un pez, busquemos si se encuentra en el archivo
        * estadisticas. Si lo encuentra, retornaremos un boolean true.
        * */
        try{
            String nomPezEstats ="";
            String pesoPez ="";
            int contadorH = 0;
            FileReader reader = new FileReader("src/archivos/estadisticas.txt");
            int i = reader.read();
            while(i != -1){
                if(i != 35){
                    if(contadorH == 2){
                        nomPezEstats += (char)i;
                    }
                    if(contadorH == 3){
                        pesoPez += (char)i;
                    }
                    if(contadorH == 4){
                        if(nomPezEstats.equals(Pez)){
                            return true;
                        }
                    }
                    if (i == '\n'){
                        nomPezEstats="";
                        pesoPez="";
                        contadorH=0;
                    }
                }else{
                    contadorH++;
                }
                i = reader.read();
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void vaciarFichero(String Path) throws IOException {
        // Método para vaciar el fichero de estadisticas para luego,
        // ir rellenandolo únicamente con los peces pescados por un usuario.
        FileWriter vaciarEstats = new FileWriter(Path,false);
        vaciarEstats.write("");
        vaciarEstats.close();
    }

    public void menu() throws Exception {
        /*Menú del programa*/
        boolean bandera = true;
        while (bandera) {
            System.out.println("**************************************************");
            System.out.println("* Benvinguts a el programa de pesca *");
            System.out.println("* Menú principal *");
            System.out.println("**************************************************");
            System.out.println("* 1) Alta d'usuari *");
            System.out.println("* 2) Baixa d'usuari * ");
            System.out.println("* 3) Pescar en una pesquera *");
            System.out.println("* 4) Estadístiques per usuari *");
            System.out.println("* 5) Estadístiques globals *");
            System.out.println("* s) Sortir del programa *");
            System.out.println("**************************************************");
            System.out.println("OPCIÓ?");
            Scanner sc = new Scanner(System.in);
            String opcionElegida = sc.nextLine();
            switch (opcionElegida) {
                case "1":
                    altaUsuari();
                    break;
                case "2":
                    deleteUsuari();
                    break;
                case "3":
                    selectorPesca();
                    break;
                case "4":
                    llamarEstatsPerUsuari();
                    break;
                case "5":
                    estatsGlobals();
                    break;
                case "s":
                    bandera = false;
                    break;
                default:
                    System.out.println("Opció no vàlida.");
            }
        }
    }

    private void llamarEstatsPerUsuari() throws IOException {
        // Método para llamar las estadisticas por un usuario comprobando que dicho usuario exista.
        System.out.println("Indique Nombre de Usuario: ");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();
        try {
            if(buscadorUsuario(usuario)){
                estatsPerUsuari(usuario);
            }else{
                throw new Exception("ERROR-> L'Usuari no està registrat.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean buscadorUsuario(String usuario) throws IOException {
        // Método para buscar que el usuario que le pasamos por parametro, este creado.
        // Si lo encuentra, nos devuelve un true como que está creado.
        String line="";
        FileReader fr = new FileReader("src/archivos/usuarios.txt");
        int i = fr.read();
        while (i != -1) {
            line += (char) i;
            if (line.equals("#" + usuario + "#\n")) {
                return true;
            }
            if (i == '\n') {
                line = "";
            }
            i = fr.read();
        }
        fr.close();
        return false;
    }

    public static void main(String[] args) throws Exception {
        Pesca test1 = new Pesca();
        test1.menu();
    }
}
