package org.example;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class cls extends WindowAdapter {

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
