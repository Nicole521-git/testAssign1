package org.example;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;
public class EditOperationTest {
    private Liunotepad liunote;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        this.tempFile = File.createTempFile("prefix", ".tmp");
        assertTrue("Temporary file should be created", this.tempFile.exists());

        liunote = new Liunotepad();
    }

    @Test
    public void testCopy() {
        SwingUtilities.invokeLater(() -> {
            liunote.txt1.setText("Hello, World!");
            liunote.txt1.select(0, 5); // 选择 "Hello"
            liunote.actionPerformed(new ActionEvent(liunote.Copy, ActionEvent.ACTION_PERFORMED, "Copy"));
            // 验证复制操作是否成功，可能需要额外的逻辑
        });
    }

    @Test
    public void testPaste() {
        SwingUtilities.invokeLater(() -> {
            liunote.txt1.setText("Hello, World!");
            liunote.txt1.select(0, 5); // 选择 "Hello"
            liunote.actionPerformed(new ActionEvent(liunote.Copy, ActionEvent.ACTION_PERFORMED, "Copy"));
            liunote.txt1.select(7, 12); // 选择 "World!"
            liunote.actionPerformed(new ActionEvent(liunote.Paste, ActionEvent.ACTION_PERFORMED, "Paste"));
            assertEquals("Hello, WorldHello!", liunote.txt1.getText());
        });
    }

    @Test
    public void testCut() {
        Liunotepad liunote = new Liunotepad();
        liunote.txt1.setText("Hello, World!");
        liunote.txt1.select(7, 12); // 选择 "World!"
        liunote.actionPerformed(new ActionEvent(liunote.Cut, ActionEvent.ACTION_PERFORMED, "Cut"));
        assertEquals("Hello, !", liunote.txt1.getText());
    }

    @Test
    public void testSelectAll() {
        Liunotepad liunote = new Liunotepad();
        liunote.txt1.setText("Hello, World!");
        liunote.actionPerformed(new ActionEvent(liunote.SelectAll, ActionEvent.ACTION_PERFORMED, "Select All"));
        assertEquals("Hello, World!", liunote.txt1.getSelectedText());
    }
}
