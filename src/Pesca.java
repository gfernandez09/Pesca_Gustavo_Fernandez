import java.io.*;
import java.text.*;
import java.util.*;

public class Pesca {
    String concatenasion;
    FileWriter fileWriter;
    FileReader reader;
    String leedorpesca = "";
    String nompescat = "";
    String pesoMax = "";
    String pesoMin = "";
    Calendar fecha = new GregorianCalendar();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    int año = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH);
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int contadorhash = 0;
    double registroporcentaje;
    double leedorpescaD;
    double pesoMinD;
    double pesoMaxD;

    private void altaUsuari(){
        System.out.println("Indique Nombre de Usuario: ");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();
        try {
            fileWriter = new FileWriter("src\\archivos\\usuarios.txt", true);
            if (!buscadorUsuario(usuario)){
                fileWriter.write("#" + usuario + "#\n");
                fileWriter.close();
            }else{
                throw new Exception("L'Usuari ja està registrat!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteUsuari(){
        System.out.println("Indique Nombre de Usuario: ");
        Scanner du = new Scanner(System.in);
        String usuario = du.nextLine();
        try {
            if (buscadorUsuario(usuario)) {
                FileReader fr = new FileReader("src\\archivos\\usuarios.txt");
                int i = fr.read();
                String conexion = "";
                String linea = "";
                while (i != -1) {
                    linea += (char) i;
                    if (linea.equals("#" + usuario + "#\n")) {
                        linea = "";
                        conexion += linea;
                    }
                    if (i == '\n') {
                        conexion += linea;
                        linea = "";
                    }
                    i = fr.read();
                }
                FileWriter fw = new FileWriter("src\\archivos\\usuarios.txt");
                fw.write(conexion);
                fw.close();
                fr.close();
            } else {
                throw new Exception("L'Usuari no està registrat!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean pescarPesquera() throws IOException {
        System.out.println("Indique Nombre de Usuario: ");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();
        FileReader fr=new FileReader("src\\archivos\\usuarios.txt");
        try{
            FileWriter writer = new FileWriter("src\\archivos\\registre.txt",true);
            if(buscadorUsuario(usuario)){
                menupesquera();
                Scanner mp = new Scanner(System.in);
                String opcion = mp.nextLine();
                boolean separador = true;
                switch(opcion){
                    case "1":
                        reader = new FileReader("src\\archivos\\florida.txt");
                        break;
                    case "2":
                        reader = new FileReader("src\\archivos\\mediterrania.txt");
                        break;
                    case "3":
                        reader = new FileReader("src\\archivos\\africa.txt");
                        break;
                    case "4":
                        reader = new FileReader("src\\archivos\\japo.txt");
                        break;
                    default:
                        System.out.println("Opció no vàlida.");
                }
                double valorAleatorio = Math.random();
                int i = reader.read();
                while(true){
                    while(i!=-1){
                        while(separador){
                            if(i != 35){
                                if (contadorhash == 1){
                                    nompescat += (char)i;
                                }
                                if(contadorhash == 2){
                                    leedorpesca += (char)i;
                                }
                                if(contadorhash == 3){
                                    leedorpescaD = Double.parseDouble(leedorpesca);
                                    if(leedorpescaD > valorAleatorio){
                                        registroporcentaje = leedorpescaD;
                                    }
                                    pesoMin += (char)i;
                                }
                                if(contadorhash == 4){
                                    pesoMinD = Double.parseDouble(pesoMin);
                                    pesoMax += (char)i;
                                }
                                if(contadorhash == 5){
                                    pesoMaxD = Double.parseDouble(pesoMax);
                                    if(registroporcentaje != 0){
                                        double pesopez = (Math.random() * ((pesoMaxD - pesoMinD) + 1)) + pesoMinD;
                                        writer.write("#" + dia +"/"+ mes +"/" + año + "#" + nompescat +"#" + usuario + "#" + pesopez + "#\n");
                                        System.out.println("#" + dia +"/"+ mes +"/" + año + "#" + nompescat +"#" + usuario + "#" + pesopez + "#");
                                        writer.close();
                                        return false;
                                    }
                                    separador = false;
                                    contadorhash = 0;
                                    nompescat="";
                                    pesoMax="";
                                    pesoMin="";
                                    leedorpesca="";
                                }

                            }else{
                                contadorhash++;
                            }
                            separador = false;
                        }
                        separador = true;
                        i = reader.read();
                    }
                    fr.close();
                }

            }else{
                throw new Exception("L'Usuari no està donat d'alta");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private void estatsUsuari() {
        System.out.println("Tal");
    }
    private void estatsGlobals() {
        System.out.println("ESTAS");
    }
    public void menu() throws IOException{
        boolean bandera = true;
        while (bandera){
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
            switch (opcionElegida){
                case "1":
                    altaUsuari();
                    break;
                case "2":
                    deleteUsuari();
                    break;
                case "3":
                    pescarPesquera();
                    break;
                case "4":
                    estatsUsuari();
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
    private void menupesquera(){
        System.out.println("En quina pesca desitjaries pescar? ");
        System.out.println("Opcions disponibles: ");
        System.out.println("1) florida.txt");
        System.out.println("2) mediterrania.txt");
        System.out.println("3) africa.txt");
        System.out.println("4) japo.txt");
        System.out.println("OPCIÓ?");
    }
    private boolean buscadorUsuario(String usuario) throws IOException {
        FileReader fr=new FileReader("src\\archivos\\usuarios.txt");
        int i = fr.read();
        boolean separador = true;
        while (i!=-1){
            while(separador){
                if(i!=35 && i!=-1){
                    concatenasion = concatenasion + (char)i;
                    if(usuario.equals(concatenasion)){
                        return true;
                    }
                }else{
                    separador = false;
                }
                i = fr.read();
            }
            separador = true;
            concatenasion="";
        }
        fr.close();
        return false;
    }
}
