public class HillCipher {
    private int[][] keyMatrix;
    private int matrixSize;

    public HillCipher(String key, int size) {
        this.matrixSize = size;
        keyMatrix = generateKeyMatrix(key);
    }

    private int[][] generateKeyMatrix(String key) {
        int[][] matrix = new int[matrixSize][matrixSize];
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        int index = 0;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = key.charAt(index) % 65;
                index++;
            }
        }
        return matrix;
    }

    public String encrypt(String message) {
        message = message.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder cipherText = new StringBuilder();
        int[] vector = new int[matrixSize];
        for (int i = 0; i < message.length(); i += matrixSize) {
            for (int j = 0; j < matrixSize; j++) {
                vector[j] = message.charAt(i + j) % 65;
            }
            for (int k = 0; k < matrixSize; k++) {
                int sum = 0;
                for (int j = 0; j < matrixSize; j++) {
                    sum += keyMatrix[k][j] * vector[j];
                }
                cipherText.append((char) (sum % 26 + 65));
            }
        }
        return cipherText.toString();
    }

    
}
