package Windows;

import Application.Consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditInfoPage extends JFrame {
    private JPanel MainPanel;
    private JTextField NameField;
    private JTextField SurnameField;
    private JTextField EmailField;
    private JTextField TelephoneField;
    private JTextField SexField;
    private JTextField BirthDateField;
    private JButton saveInfoButton;
    public EditInfoPage(Consumer user, ProfilePage parent) {
        super("Edit Info Page");
        setMinimumSize(new Dimension(720, 480));
        setContentPane(MainPanel);

        // seting the input fields to original data of the user
        NameField.setText(user.getPrenume());
        SurnameField.setText(user.getNume());
        EmailField.setText(user.getEmail());
        TelephoneField.setText(user.getTelefon());
        SexField.setText(user.getSex());
        BirthDateField.setText(user.getBirthday());

        saveInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if there is no empty input, save data
                if (NameField.getText().equals("") || SurnameField.getText().equals("") || EmailField.getText().equals("")
                    || TelephoneField.getText().equals("") || SexField.getText().equals("") || BirthDateField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all data fields to proceed");
                }
                else {
                    user.setPrenume(NameField.getText());
                    user.setNume(SurnameField.getText());
                    user.setEmail(EmailField.getText());
                    user.setTelefon(TelephoneField.getText());
                    user.setSex(SexField.getText());
                    user.setData_de_nastere(BirthDateField.getText());
                    parent.update(user);
                    dispose();
                }
            }
        });

        getRootPane().setDefaultButton(saveInfoButton);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
