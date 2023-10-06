import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestion De Contactos");
        GestionDeContactos gestionDeContactos = new GestionDeContactos();
        frame.getContentPane().add(gestionDeContactos.getPanel());
        frame.setMinimumSize(new Dimension(500, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
