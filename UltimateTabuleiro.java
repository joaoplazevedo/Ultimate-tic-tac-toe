import java.util.ArrayList;
import java.util.List;

public class UltimateTabuleiro {

    private Tabuleiro[][] tabuleiros = new Tabuleiro[3][3];

    public UltimateTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiros[i][j] = new Tabuleiro();
            }
        }
    }

    public int play(char player, int b, int i, int j) {
        Tabuleiro t = getBoard(b);
        t.play(player, i, j);
        int next = i * 3 + j + 1;
        if (getBoard(next).checkEnd() != '-') return -1;
        return next; //retorna o idx do proximo tabuleiro
    }

    private void addMoves(List<int[]> moves, int b, Tabuleiro t) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (t.validPlay(i, j)) moves.add(new int[] { b, i, j });
            }
        }
    }

    public List<int[]> getValidMoves(int forcedBoard) {
        ArrayList<int[]> moves = new ArrayList<>();
        if (forcedBoard == -1) {
            for (int b = 1; b <= 9; b++) {
                Tabuleiro t = getBoard(b);
                if (t.checkEnd() != '-') continue;
                addMoves(moves, b, t);
            }
        } else {
            int b = forcedBoard;
            Tabuleiro t = getBoard(b);
            addMoves(moves, b, t);
        }
        return moves;
    }

    private char checkUltimateWin() {
        char[][] t = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                t[i][j] = tabuleiros[i][j].checkEnd();
            }
        }
        if (
            (t[0][0] != '-' && t[0][0] == t[1][1] && t[1][1] == t[2][2]) || // diagonal principal
            (t[0][2] != '-' && t[0][2] == t[1][1] && t[1][1] == t[2][0]) // diagonal secundaria
        ) return t[1][1];
        for (int i = 0; i < 3; i++) {
            if (
                t[i][0] != '-' && t[i][0] == t[i][1] && t[i][1] == t[i][2] // linhas
            ) return t[i][0];
            if (
                t[0][i] != '-' && t[0][i] == t[1][i] && t[1][i] == t[2][i] // colunas
            ) return t[0][i];
        }
        return '-';
    }

    public char checkUltimateEnd() {
        char winner = checkUltimateWin();
        if (winner != '-' && winner != 'D') return winner;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiros[i][j].checkEnd() == '-') return '-';
            }
        }
        return 'D';
    }

    public Tabuleiro getBoard(int b) {
        return tabuleiros[(b - 1) / 3][(b - 1) % 3];
    }

    public Tabuleiro getBoard(int i, int j) {
        return tabuleiros[i][j];
    }

    public String printInitial() {
        return "--- --- ---\n-1- -2- -3-\n--- --- ---\n           \n--- --- ---\n-4- -5- -6-\n--- --- ---\n           \n--- --- ---\n-7- -8- -9-\n--- --- ---";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    sb.append(tabuleiros[i][k].getLinha(j));
                    // percorrer cada linha(i) do ultimate ir aos 3 tabuleiro (k) dar print na mesma linha (j)
                    if (k != 2) sb.append(" ");
                }
                sb.append("\n");
            }
            if (i != 2) sb.append(" ".repeat(11) + "\n");
        }
        return sb.toString();
    }
}
