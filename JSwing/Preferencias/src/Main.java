import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Preferencias preferencias = new Preferencias();
        Frame frame = preferencias.getFrame();
        frame.setMinimumSize(new Dimension(350, 250));
        frame.pack();
        frame.setVisible(true);
    }
}
