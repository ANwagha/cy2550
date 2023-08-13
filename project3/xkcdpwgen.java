import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class xkcdpwgen {

  private static List<String> wordList = new ArrayList<>();

  public static void main(String[] args) {
    loadWordList();

    int numWords = 4;
    int numCaps = 0;
    int numNumbers = 0;
    int numSymbols = 0;

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-w") | args[i].equals("--words")) {
        numWords = Integer.parseInt(args[i + 1]);
      } else if (args[i].equals("-c") | args[i].equals("--caps")) {
        numCaps = Integer.parseInt(args[i + 1]);
      } else if (args[i].equals("-n") | args[i].equals("--numbers")) {
        numNumbers = Integer.parseInt(args[i + 1]);
      } else if (args[i].equals("-s") | args[i].equals("--symbols")) {
        numSymbols = Integer.parseInt(args[i + 1]);
      } else if (args[i].equals("-h") | args[i].equals("--help")) {
        System.out.println("usage: xkcdpwgen [-h] [-w WORDS] [-c CAPS] [-n NUMBERS] [-s SYMBOLS]\n");
        System.out.println("Generate a secure, memorable password using the XKCD method\n");
        System.out.println("optional arguments:\n");
        System.out.println("    -h, --help            show this help message and exit");
        System.out.println("    -w WORDS, --words WORDS");
        System.out.println("                          include WORDS words in the password (default=4)");
        System.out.println("-c CAPS, --caps CAPS  capitalize the first letter of CAPS random words");
        System.out.println("                          (default=0");
        System.out.println("    -n NUMBERS, --numbers NUMBERS");
        System.out.println("                          insert NUMBERS random numbers in the password");
        System.out.println("                          (default=0)");
        System.out.println("    -s SYMBOLS, --symbols SYMBOLS");
        System.out.println("                          insert SYMBOLS random symbols in the password");
        System.out.println("                          (default=0)");
      }
    }

    generateAndPrintPassword(numWords, numCaps, numNumbers, numSymbols);
  }

  private static void loadWordList() {
    System.out.println(System.getProperty("user.dir"));
    try (BufferedReader reader = new BufferedReader(new FileReader("words.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        wordList.add(line.trim());
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static String generatePassword(int numWords, int numCaps, int numNumbers, int numSymbols) {
    Random random = new Random();
    StringBuilder password = new StringBuilder();

    for (int i = 0; i < numWords; i++) {
      password.append(wordList.get(random.nextInt(wordList.size())));
    }

    for (int i = 0; i < numCaps; i++) {
      int wordIndex = random.nextInt(numWords);
      password.setCharAt(wordIndex, Character.toUpperCase(password.charAt(wordIndex)));
    }

    String symbols = "~!@#$%^&*.:;";
    for (int i = 0; i < numSymbols; i++) {
      int position = random.nextInt(password.length() + 1);
      password.insert(position, symbols.charAt(random.nextInt(symbols.length())));
    }

    for (int i = 0; i < numNumbers; i++) {
      int position = random.nextInt(password.length() + 1);
      password.insert(position, random.nextInt(10));
    }

    return password.toString();
  }

  private static void generateAndPrintPassword(int numWords, int numCaps, int numNumbers, int numSymbols) {
    String password = generatePassword(numWords, numCaps, numNumbers, numSymbols);
    System.out.println(password);
  }
}
