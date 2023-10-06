import java.awt.*;

public class Main {
    public static void main(String[] args) {
        InterfazContacto contacto = new InterfazContacto();
        Frame frame = contacto.getFrame();
        frame.setMinimumSize(new Dimension(800, 500));
        frame.pack();
        frame.setVisible(true);

    }
}