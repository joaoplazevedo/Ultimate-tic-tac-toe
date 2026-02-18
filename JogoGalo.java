import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JogoGalo {

  private static char player = 'O';
  private static int forcedPlay = -1; // -1 se nao, 1-9 se sim e qual o tabuleiro
  private static int ai = 0;

  private static void betterAi(UltimateTabuleiro t) {
    List<int[]> moves = t.getValidMoves(forcedPlay);
    for (int[] move : moves) {

    }
  }

  private static void randomAi(UltimateTabuleiro t) {
    Random r = new Random();
    int idx;
    List<int[]> moves = t.getValidMoves(forcedPlay);
    idx = r.nextInt(moves.size());
    int board = moves.get(idx)[0];
    int i = moves.get(idx)[1];
    int j = moves.get(idx)[2];
    forcedPlay = t.play('X', board, i, j);
  }

  private static int[] readInput(UltimateTabuleiro t) {
    Scanner sc = new Scanner(System.in);
    if (forcedPlay == -1) {
      System.out.println("Choose the board you want to play on");
      int b = sc.nextInt();
      sc.nextLine();
      while (b < 1 || b > 9 || t.getBoard(b).checkEnd() != '-') {
        System.out.println("Invalid board, choose again:");
        b = sc.nextInt();
        sc.nextLine();
      }
      forcedPlay = b;
    }
    Tabuleiro playBoard = t.getBoard(forcedPlay);
    System.out.println(
        "Choose the coordinates (i j), separated by a space or a new line: ");
    int i = sc.nextInt();
    int j = sc.nextInt();
    sc.nextLine();
    while (!playBoard.validPlay(i, j)) {
      System.out.println("Invalid play, choose again:");
      i = sc.nextInt();
      j = sc.nextInt();
      sc.nextLine();
    }
    return new int[] { forcedPlay, i, j };
  }

  private static boolean validChoice(Scanner sc, String choice) {
    sc.nextLine();
    if (choice.equals("0")) {
      ai = 0; // redundante
      return true;
    }
    if (choice.equals("1")) {
      ai = 1;
      return true;
    }
    if (choice.equals("2")) {
      ai = 2;
      return true;
    }
    return false;
  }

  private static void chooseMode() {
    Scanner sc = new Scanner(System.in);
    do {
      System.out.println("Do you want to play against an AI?:");
      System.out.println("0: no    1: easy     2:impossible");
    } while (!validChoice(sc, sc.next().toLowerCase()));
  }

  public static boolean checkWinOrDraw(UltimateTabuleiro t) {
    char winner = t.checkUltimateEnd();
    if (winner == 'D') {
      System.out.println("Empate!");
      return true;
    } else if (winner == 'X') {
      System.out.println("O Jogador 2 ganhou!");
      return true;
    } else if (winner == 'O') {
      System.out.println("O Jogador 1 ganhou!");
      return true;
    }
    return false;
  }

  private static void aiMove(UltimateTabuleiro t) {
    System.out.println("AI is thinking...");
    if (ai == 1)
      randomAi(t);
    if (ai == 2)
      betterAi(t);
    player = (player == 'O') ? 'X' : 'O';
    return;
  }

  private static void HumanMove(UltimateTabuleiro t) {
    int[] move = readInput(t);
    forcedPlay = t.play(player, move[0], move[1], move[2]);
    player = (player == 'O') ? 'X' : 'O';
  }

  public static void main(String[] args) {
    System.out.println("Jogo do Galo:");
    chooseMode();
    System.out.println("Player 1: O |V.S| Player 2: X");
    System.out.println();
    UltimateTabuleiro t = new UltimateTabuleiro();
    System.out.println(t.printInitial());

    while (!checkWinOrDraw(t)) {
      System.out.println(
          "Player " + ((player == 'O') ? "1" : "2") + " turn: "); // ? tem precedencia sobre o +:w
      if (player != 'O' && ai != 0)
        aiMove(t);
      else
        HumanMove(t);
      System.out.println(t);
    }
  }
}
