package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class about extends Frame implements ActionListener {
    Button b;
    TextArea information;
    boolean isDisposed = false;

    about() {
        String author = "Writer: Yiwen Zhang  23010547\nWriter: Sen Niu  ";
        this.setLayout(new BorderLayout());
        information = new TextArea(author);
        information.setFont(new Font("Courier", Font.BOLD, 18));
        b = new Button("Close");
        this.setTitle("Notebook");
        this.setBackground(Color.BLUE);
        this.setSize(300, 200);
        this.add(information, BorderLayout.CENTER);
        this.add(b, BorderLayout.SOUTH);
        this.information.setEnabled(false);
        this.pack();
        this.setVisible(true);

        b.addActionListener(this);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                setVisible(false);
                disvisible();
            }
        });
    }

    void disvisible() {
        this.dispose();
        isDisposed = true;
    }

    public void actionPerformed(ActionEvent ac) {
        if (ac.getSource() == b) {
            this.setVisible(false);
            disvisible();
        }
    }

    public boolean isDisposed() {
        return isDisposed;
    }
}
