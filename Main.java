import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private JFrame frame;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JTextField keyField;
    private JComboBox<String> cipherSelection;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton uploadButton;

    public static void main(String[] args) {
        Main window = new Main();
        window.frame.setVisible(true);
    }

    public Main() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Cipher Program");
        frame.setBounds(100, 100, 600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblKey = new JLabel("Key (min 12 characters):");
        lblKey.setBounds(10, 10, 200, 25);
        frame.getContentPane().add(lblKey);

        keyField = new JTextField();
        keyField.setBounds(210, 10, 300, 25);
        frame.getContentPane().add(keyField);
        keyField.setColumns(12);

        inputArea = new JTextArea();
        inputArea.setBounds(10, 50, 560, 100);
        frame.getContentPane().add(inputArea);

        JLabel lblInput = new JLabel("Input Text:");
        lblInput.setBounds(10, 30, 200, 25);
        frame.getContentPane().add(lblInput);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 250, 560, 100);
        frame.getContentPane().add(outputArea);

        JLabel lblOutput = new JLabel("Output Text:");
        lblOutput.setBounds(10, 230, 200, 25);
        frame.getContentPane().add(lblOutput);

        encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(10, 360, 100, 25);
        frame.getContentPane().add(encryptButton);

        decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(120, 360, 100, 25);
        frame.getContentPane().add(decryptButton);

        uploadButton = new JButton("Upload File");
        uploadButton.setBounds(240, 360, 120, 25);
        frame.getContentPane().add(uploadButton);

        JLabel lblCipher = new JLabel("Select Cipher:");
        lblCipher.setBounds(10, 160, 100, 25);
        frame.getContentPane().add(lblCipher);

        cipherSelection = new JComboBox<>(new String[]{"Vigenere", "Playfair", "Hill"});
        cipherSelection.setBounds(120, 160, 150, 25);
        frame.getContentPane().add(cipherSelection);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = inputArea.getText();
                String key = keyField.getText();
                if (key.length() < 12) {
                    JOptionPane.showMessageDialog(frame, "Key must be at least 12 characters.");
                    return;
                }
                String cipherType = (String) cipherSelection.getSelectedItem();
                String encryptedText = "";
                if ("Vigenere".equals(cipherType)) {
                    encryptedText = VigenereCipher.encrypt(text, key);
                } else if ("Playfair".equals(cipherType)) {
                    PlayfairCipher playfairCipher = new PlayfairCipher(key);
                    encryptedText = playfairCipher.encrypt(text);
                } else if ("Hill".equals(cipherType)) {
                    HillCipher hillCipher = new HillCipher(key, 2);  // Example: 2x2 Hill Cipher
                    encryptedText = hillCipher.encrypt(text);
                }
                outputArea.setText(encryptedText);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = inputArea.getText();
                String key = keyField.getText();
                if (key.length() < 12) {
                    JOptionPane.showMessageDialog(frame, "Key must be at least 12 characters.");
                    return;
                }
                String cipherType = (String) cipherSelection.getSelectedItem();
                String decryptedText = "";
                if ("Vigenere".equals(cipherType)) {
                    decryptedText = VigenereCipher.decrypt(text, key);
                } else if ("Playfair".equals(cipherType)) {
                    PlayfairCipher playfairCipher = new PlayfairCipher(key);
                    decryptedText = playfairCipher.decrypt(text);
                } else if ("Hill".equals(cipherType)) {
                    // HillCipher.decrypt() implementation is needed here.
                    JOptionPane.showMessageDialog(frame, "Hill Cipher decryption is not yet implemented.");
                }
                outputArea.setText(decryptedText);
            }
        });

        // Upload button functionality
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        inputArea.setText(content.toString());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error reading file.");
                    }
                }
            }
        });
    }
}
