import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LecturaEscritura {

    private JPanel panel;
    private JTextArea textArea;
    private JScrollPane scrollpane;

    public JPanel getPanel() {
        return panel;
    }

    JFrame frame = new JFrame("Lectura Escritura");

    public JFrame getFrame() {
        return frame;
    }

    public LecturaEscritura(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem openItem = new JMenuItem("Abrir");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        textArea.setText("");
                        java.util.Scanner scanner = new java.util.Scanner(selectedFile);
                        while (scanner.hasNextLine()) {
                            textArea.append(scanner.nextLine() + "\n");
                        }
                        scanner.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JMenuItem saveItem = new JMenuItem("Guardar");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        FileWriter newFile = new FileWriter(selectedFile);
                        newFile.write(textArea.getText());
                        newFile.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        fileMenu.add(openItem);
        fileMenu.add(saveItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.add(scrollpane);


    }
}
