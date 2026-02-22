public class teste {
  public static void main(String[] args) {
    int[][] ola = new int[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (i == j || i + j == 2) {
          ola[i][j] = 1;
        } else
          ola[i][j] = 0;
        System.out.print(ola[i][j]);
      }
      System.out.println();
    }
  }
}
