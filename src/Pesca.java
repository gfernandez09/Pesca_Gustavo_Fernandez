import java.io.*;
import java.util.*;

public class Pesca {
    String line;
    FileWriter fileWriter;
    String leedorpesca = "";
    String nompescat = "";
    String pesoMax = "";
    String pesoMin = "";
    Calendar fecha = new GregorianCalendar();
    int año = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH);
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int contadorhash = 0;
    double registroporcentaje;
    double leedorpescaD;
    double pesoMinD;
    double pesoMaxD;

    private void altaUsuari() {
        System.out.println("Indique Nombre de Usuario: ");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();
        try {
            fileWriter = new FileWriter("src\\archivos\\usuarios.txt", true);
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

    private void deleteUsuari() {
        System.out.println("Indique Nombre de Usuario: ");
        Scanner du = new Scanner(System.in);
        String usuario = du.nextLine();
        try {
            if (buscadorUsuario(usuario)) {
                FileReader fr = new FileReader("src\\archivos\\usuarios.txt");
                int i = fr.read();
                String conexion = "";
                String line = "";
                while (i != -1) {
                    line += (char) i;
                    if (line.equals("#" + usuario + "#\n")) {
                        line = "";
                        conexion += line;
                    }
                    if (i == '\n') {
                        conexion += line;
                        line = "";
                    }
                    i = fr.read();
                }
                FileWriter fw = new FileWriter("src\\archivos\\usuarios.txt");
                fw.write(conexion);
                fw.close();
                fr.close();
            } else {
                throw new Exception("ERROR-> L'Usuari no està registrat!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectorPesca() throws Exception {
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
        try {
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
                                    FileWriter writer = new FileWriter("src\\archivos\\registre.txt", true);
                                    double pesopez = (Math.random() * ((pesoMaxD - pesoMinD))) + pesoMinD;
                                    String pesoPezS = String.format("%.2f",pesopez);
                                    writer.write("#" + dia + "/" + mes + "/" + año + "#" + nompescat + "#" + usuario + "#" + pesoPezS + "#\n");
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

    private void estatsUsuari() {
        System.out.println("Tal");
    }

    private void estatsGlobals() {
        System.out.println("ESTAS");
    }

    public void menu() throws Exception {
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
    private boolean buscadorUsuario(String usuario) throws IOException {
        FileReader fr = new FileReader("src\\archivos\\usuarios.txt");
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
}
