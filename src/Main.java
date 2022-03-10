import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //CSV fil
        String filename = "imdb-data.scv";
        //Definer CSV fil path - som vi skal læse oplysninger fra
        File imdbFil = new File("resources/imdb-data.csv");

        //Lav DDL delen --> CREATE TABLE (overskrifterne til kolonnerne)
        try {
            //Læs cvs fil
            Scanner sc = new Scanner(imdbFil);
            //Læs en linje i filen, den første linje kun
            String line = sc.nextLine();
            //Del(split) linjen ved ';'
            String [] categories = line.split(";");

            //Definer en String - så kolonne overskrifter kan blive gemt
            String sql = "";

            //Gem hver ord fra categories arrayet i Stringen sql
            for (String category:categories) {
                sql = sql + category + " varchar(255), \n";
            }

            //Opretter Create table String - så den kan blive gemt i sql fil efter
            String createTable = "CREATE TABLE " + filename + " (\n" + sql + ");";
            System.out.println(createTable);  //Print for at se at det er skrevet korrekt i consolen

            //Opret DDL.sql fil og gem createTable
            File file = new File("resources/DDL.sql");
            FileWriter categoriesDDL = new FileWriter(file,true);
            categoriesDDL.write( createTable + "\n");
            categoriesDDL.close();

        } catch (FileNotFoundException e) {
            System.out.println("Movie File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Lav DML.sql filen
        try {
            //Læs csv fil
            Scanner sc1 = new Scanner(imdbFil);
            //Spring første linje over
            sc1.nextLine();

            //Loop igennem csv filen så længe der er en linje med data
            while (sc1.hasNextLine()) {
                //læs en linje
                String currentLine = sc1.nextLine();

                //Gem værdierne i et array og split værdierne ved ';'
                String[] datas = currentLine.split(";");

                //lav datalinjen som skal gemmes i de forskellige kolonner
                String dataLine = datas[0] + "," + datas[1] + "," + datas[2] + "," + datas[3] + "," + datas[4] + "," + datas[5];

                //Definer hvordan linien skal skrives i MySQL Workbench
                String insertInto = "INSERT INTO " + filename + "\nVALUES(" + dataLine + ");";
                System.out.println(insertInto);  //Se i consolen eks.:  Insert into imdb-data.scv VALUES(1963,138,8 1/2,Drama,80,Yes);

                //Gem til DML.sql
                File file2 = new File("resources/DML.sql");
                FileWriter dataDML = new FileWriter(file2,true);
                dataDML.write( insertInto + "\n");
                dataDML.close();

            }
        } catch (FileNotFoundException e) {
            System.out.println("Movie File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
