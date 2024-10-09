package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Find extends Frame implements ActionListener {
    Button Find_String;
    Button Cancle;
    TextField Input;
    Label Tip_Information;
    Liunotepad liunotepad;

    Find(Liunotepad liunotepad) {
        Find_String = new Button("Search");
        this.liunotepad = liunotepad;
        Cancle = new Button("Cancle");
        Input = new TextField(20);
        Tip_Information = new Label("Please enter the information you are looking for:");
        this.setTitle("Search");
        this.setBackground(Color.BLUE);
        this.setLayout(new FlowLayout());
        this.add(Tip_Information);
        this.add(Input);
        this.add(Find_String);
        this.add(Cancle);
        Find_String.addActionListener(this);
        Cancle.addActionListener(this);
        this.pack();
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                setVisible(false);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Find_String) {
            if (this.Input.getText() != "") {
                String temp = this.liunotepad.txt1.getText();
                int code = temp.indexOf(this.Input.getText());
                System.out.println(code);
                this.liunotepad.txt1.select(code, code + this.Input.getText().length());
                System.out.println(code + this.Input.getText().length());
            }
            this.setVisible(false);
        } else if (e.getSource() == Cancle) {
            this.setVisible(false);
        }
    }
}
