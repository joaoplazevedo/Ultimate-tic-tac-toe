import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class JogoGalo {

  private static char player = 'O';
  private static int forcedPlay = -1; // -1 se nao, 1-9 se sim e qual o tabuleiro
  private static int ai = 0;
  private static int maxDepth = 5;

  private static int eval(UltimateTabuleiro t, int forcedPlayCopy, boolean isMax) {
    char end = t.checkUltimateEnd();
    if (end == 'O')
      return 9999999;
    if (end == 'X')
      return -9999999;
    if (end == 'D')
      return 0;
    int score = 0;
    score += 98 * stupidHeuristic(t);
    score += piecesInDiagonals(t);
    return score;
  }

  private static int piecesInDiagonals(UltimateTabuleiro t) {
    int score = 0;
    for (int b = 1; b <= 9; b++) {
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          char play = t.getBoard(b).t[i][j];
          if (play != '-') {
            if (i == 1 && j == 1) {
              score += (play == 'O') ? 2 : -2;
            } else if (i == j || i + j == 2) {
              score += (play == 'O') ? 1 : -1;
            }
          }
        }
      }
    }
    return score;
  }

  private static int stupidHeuristic(UltimateTabuleiro t) {
    int score = 0; // + se o O ganha, - se o X ganha algum tabuleiro
    for (int b = 1; b <= 9; b++) {
      if (t.getBoard(b).checkEnd() == 'O')
        score++;
      if (t.getBoard(b).checkEnd() == 'X')
        score--;
    }
    return score;
  }

  public static int[] betterAi(UltimateTabuleiro t, int forcedPlayCop) {
    List<int[]> moves = t.getValidMoves(forcedPlayCop);
    int bestVal = Integer.MAX_VALUE;
    int[] bestMove = null;

    for (int[] move : moves) {
      // System.out.println(Arrays.toString(move));
      UltimateTabuleiro tCopy = t.copy();
      int forcedPlayCopy = tCopy.play('X', move[0], move[1], move[2]);
      int val = minimax(tCopy, true, forcedPlayCopy, 0);
      if (val < bestVal) {
        // System.out.println("hello");
        bestVal = val;
        bestMove = move;
      }
    }
    forcedPlay = t.play('X', bestMove[0], bestMove[1], bestMove[2]);
    return bestMove;
  }

  private static int minimax(UltimateTabuleiro t, boolean isMax, int forcedPlayCopy, int depth) {
    if (t.isTerminalState() || depth == maxDepth) {
      return eval(t, forcedPlayCopy, isMax);
    }

    if (isMax) {
      int val = Integer.MIN_VALUE;
      List<int[]> moves = t.getValidMoves(forcedPlayCopy);
      for (int[] move : moves) {
        UltimateTabuleiro tCopy = t.copy();
        forcedPlayCopy = tCopy.play('O', move[0], move[1], move[2]);
        val = Math.max(val, minimax(tCopy, false, forcedPlayCopy, depth + 1));
      }
      return val;
    }

    else { // min turn
      int val = Integer.MAX_VALUE;
      List<int[]> moves = t.getValidMoves(forcedPlayCopy);
      for (int[] move : moves) {
        UltimateTabuleiro tCopy = t.copy();
        forcedPlayCopy = tCopy.play('X', move[0], move[1], move[2]);
        val = Math.min(val, minimax(tCopy, true, forcedPlayCopy, depth + 1));
      }
      return val;
    }
  }

  public static int[] randomAi(UltimateTabuleiro t, int forcedPlayCopy) {
    Random r = new Random();
    int idx;
    List<int[]> moves = t.getValidMoves(forcedPlayCopy);
    idx = r.nextInt(moves.size());
    int board = moves.get(idx)[0];
    int i = moves.get(idx)[1];
    int j = moves.get(idx)[2];
    forcedPlay = t.play('X', board, i, j);
    return moves.get(idx);
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
      randomAi(t, forcedPlay);
    if (ai == 2)
      betterAi(t, forcedPlay);
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
