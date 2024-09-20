import java.util.*;

public class PlayfairCipher {
    private char[][] matrix;

    public PlayfairCipher(String key) {
        createMatrix(key);
    }

    private void createMatrix(String key) {
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        for (char c : key.toCharArray()) {
            set.add(c == 'J' ? 'I' : c);  // J = I
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J') set.add(c);
        }
        Iterator<Character> iterator = set.iterator();
        matrix = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = iterator.next();
            }
        }
    }

    public String encrypt(String text) {
        text = prepareText(text.toUpperCase().replaceAll("[^A-Z]", ""));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char[] pair = new char[] { text.charAt(i), text.charAt(i + 1) };
            int[] pos1 = findPosition(pair[0]);
            int[] pos2 = findPosition(pair[1]);

            if (pos1[0] == pos2[0]) { // same row
                result.append(matrix[pos1[0]][(pos1[1] + 1) % 5]);
                result.append(matrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) { // same column
                result.append(matrix[(pos1[0] + 1) % 5][pos1[1]]);
                result.append(matrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else { // rectangle
                result.append(matrix[pos1[0]][pos2[1]]);
                result.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return result.toString();
    }

    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char[] pair = new char[] { text.charAt(i), text.charAt(i + 1) };
            int[] pos1 = findPosition(pair[0]);
            int[] pos2 = findPosition(pair[1]);

            if (pos1[0] == pos2[0]) { // same row
                result.append(matrix[pos1[0]][(pos1[1] + 4) % 5]);
                result.append(matrix[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) { // same column
                result.append(matrix[(pos1[0] + 4) % 5][pos1[1]]);
                result.append(matrix[(pos2[0] + 4) % 5][pos2[1]]);
            } else { // rectangle
                result.append(matrix[pos1[0]][pos2[1]]);
                result.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return result.toString();
    }

    private String prepareText(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char first = text.charAt(i);
            char second = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';
            if (first == second) {
                result.append(first).append('X');
                i--;
            } else {
                result.append(first).append(second);
            }
        }
        if (result.length() % 2 != 0) {
            result.append('X');
        }
        return result.toString();
    }

    private int[] findPosition(char letter) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == letter) return new int[] { i, j };
            }
        }
        return null;
    }
}
