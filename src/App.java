import entities.Product;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class App {

  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    Locale.setDefault(Locale.US);
    List<Product> list = new ArrayList<>();
    System.out.println("******************************************");

    System.out.print("Insira o caminho do arquivo: ");
    String sourceFileStr = sc.nextLine();

    File sourceFile = new File(sourceFileStr);
    String sourceFolderStr = sourceFile.getParent();

    boolean success = new File(sourceFolderStr + "\\out").mkdir();

    String targetFileStr = sourceFolderStr + "\\out\\summary.csv";

    try (
      BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))
    ) {
      String itemCsv = br.readLine();
      while (itemCsv != null) {
        String[] fieldes = itemCsv.split(",");
        String name = fieldes[0];
        double price = Double.parseDouble(fieldes[1]);
        int quantity = Integer.parseInt(fieldes[2]);

        list.add(new Product(name, price, quantity));

        itemCsv = br.readLine();
      }

      try (
        BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))
      ) {
        for (Product item : list) {
          bw.write(item.getName() + "," + String.format("%.2f", item.total()));
          bw.newLine();
        }
        System.out.println(targetFileStr + " CREATED");
      } catch (Exception e) {
        System.out.println("Erro ao gravar arquivo :" + e.getMessage());
      }
    } catch (Exception e) {
      System.out.println("Erro ao gravar arquivo :" + e.getMessage());
    }

    System.out.println("******************************************");
    sc.close();
  }
}
