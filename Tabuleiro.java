public class Tabuleiro {

  public char[][] t = new char[3][3];
  private int moveCount = 0;

  public Tabuleiro() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        t[i][j] = '-';
      }
    }
  }

  public Tabuleiro copy() {
    Tabuleiro copia = new Tabuleiro();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        copia.t[i][j] = this.t[i][j];
      }
    }
    return copia;
  }

  public boolean validPlay(int i, int j) {
    if (i < 0 || j < 0 || i > 2 || j > 2 || t[i][j] != '-')
      return false;
    return true;
  }

  public void play(char player, int i, int j) {
    t[i][j] = player;
    moveCount++;
  }

  public String getLinha(int linha) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      sb.append(t[linha][i]);
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder(); // ganhar o habito de usar esta classe para ser mais eficiente no uso de
                                             // memória
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        str.append(t[i][j]);
      }
      str.append("\n"); // %n so funciona dentro do sout
    }
    return str.toString();
  }

  public char checkEnd() {
    if (moveCount == 0)
      return '-'; // ainda nao houve nenhum movimento feito
    if ((t[0][0] != '-' && t[0][0] == t[1][1] && t[1][1] == t[2][2]) || // diagonal principal
        (t[0][2] != '-' && t[0][2] == t[1][1] && t[1][1] == t[2][0]) // diagonal secundaria
    )
      return t[1][1];
    for (int i = 0; i < 3; i++) {
      if (t[i][0] != '-' && t[i][0] == t[i][1] && t[i][1] == t[i][2] // linhas
      )
        return t[i][0];
      if (t[0][i] != '-' && t[0][i] == t[1][i] && t[1][i] == t[2][i] // colunas
      )
        return t[0][i];
    }
    if (moveCount == 9) {
      return 'D';
    }
    return '-'; // nao existe linha/coluna/diagonal com o msm simbolo
  }
}
