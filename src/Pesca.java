import java.io.*;
import java.util.*;

public class Pesca {
    String concatenasion;
    FileWriter fileWriter = null;
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
    private void pescarPesquera() throws IOException {
        System.out.println("Indique Nombre de Usuario: ");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();
        FileReader fr=new FileReader("src\\archivos\\usuarios.txt");
        try{
            if(buscadorUsuario(usuario)){

            }else{
                throw new Exception("L'Usuari no està donat d'alta");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
