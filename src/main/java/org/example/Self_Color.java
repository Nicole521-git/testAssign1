package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Self_Color extends Frame implements ActionListener, ItemListener {
    List colorList;
    Label colorLabel;
    Button confirmation;
    Button cancel;
    Liunotepad selfColor;
    Color exampleColor;

    Self_Color(Liunotepad selfColor) {
        this.selfColor = selfColor;
        setTitle("Select color");
        colorList = new List();
        colorLabel = new Label("Color type");
        confirmation = new Button("OK");
        cancel = new Button("Cancel");

        colorList.add("Red");
        colorList.add("Black");
        colorList.add("Yellow");
        colorList.add("Blue");
        colorList.add("Magenta");
        colorList.add("Green");
        colorList.add("Gray");

        setLayout(new FlowLayout());
        add(colorLabel);
        add(colorList);
        add(confirmation);
        add(cancel);
        pack();
        setVisible(true);
        confirmation.addActionListener(this);
        cancel.addActionListener(this);
        colorList.addItemListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
    }

    public void actionPerformed(ActionEvent actionevent) {
        if (actionevent.getSource() == confirmation) {
            if (exampleColor != null) {
                selfColor.txt1.setForeground(exampleColor);
            }
            setVisible(false);
        } else if (actionevent.getSource() == cancel) {
            setVisible(false);
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String colorName = (String) colorList.getSelectedItem();
            switch (colorName.toUpperCase()) {
                case "RED":
                    exampleColor = Color.RED;
                    break;
                case "BLACK":
                    exampleColor = Color.BLACK;
                    break;
                case "YELLOW":
                    exampleColor = Color.YELLOW;
                    break;
                case "BLUE":
                    exampleColor = Color.BLUE;
                    break;
                case "MAGENTA":
                    exampleColor = Color.MAGENTA;
                    break;
                case "GREEN":
                    exampleColor = Color.GREEN;
                    break;
                case "GRAY":
                    exampleColor = Color.GRAY;
                    break;
                default:
                    exampleColor = Color.BLACK;
                    break;
            }
        }
    }
}
