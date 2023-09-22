import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lista {
    private JPanel panel;
    private JList list;
    private JButton borrar;
    private JButton agregar;
    private JTextField textField;

    public JPanel getPanel() {
        return panel;
    }
   private DefaultListModel<String> listModel = new DefaultListModel<>();

    public Lista(){
        list.setModel(listModel);
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItem = textField.getText().trim();
                if (!newItem.isEmpty()) {
                    listModel.addElement(newItem);
                    textField.setText("");
                }
            }
        });
        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex =list.getSelectedIndex();
                if (selectedIndex != -1){
                    listModel.remove(selectedIndex);
                }
            }
        });
    }
}
