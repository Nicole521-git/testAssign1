package org.example;


import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

class Self_Font extends Frame implements ActionListener, ItemListener {
    Button confirmation;
    Button cancel;
    Label fontNameLabel;
    Label fontStyleLabel;
    Label fontSizeLabel;
    List fontNameList;
    List fontStyleList;
    List fontSizeList;
    TextArea example;
    Liunotepad selfFont;
    String fontName;
    int fontStyle;
    int fontSize;

    Self_Font(Liunotepad selfFont) {
        this.selfFont = selfFont;
        setTitle("Set Font");
        confirmation = new Button("OK");
        cancel = new Button("Cancel");
        fontNameLabel = new Label("Typeface");
        fontStyleLabel = new Label("Font");
        fontSizeLabel = new Label("Type size");
        fontName = "Courier";
        fontStyle = Font.BOLD;
        fontSize = 18;
        fontNameList = new List();
        fontStyleList = new List();
        fontSizeList = new List();
        setBackground(Color.CYAN);
        example = new TextArea("Text test", 3, 25);
        example.setEnabled(false);

        setLayout(new FlowLayout());
        add(fontNameLabel);
        add(fontNameList);
        add(fontStyleLabel);
        add(fontStyleList);
        add(fontSizeLabel);
        add(fontSizeList);
        add(example);
        add(confirmation);
        add(cancel);

        // Add font names
        fontNameList.add("Courier");
        fontNameList.add("Roman");
        fontNameList.add("Verdana");
        fontNameList.add("Serif");
        fontNameList.add("SansSerif");
        fontNameList.add("Monospaced");

        // Add font styles
        fontStyleList.add("Plain");
        fontStyleList.add("Bold");
        fontStyleList.add("Italic");
        fontStyleList.add("Bold Italic");

        // Add font sizes
        fontSizeList.add("8");
        fontSizeList.add("12");
        fontSizeList.add("16"); // Added a new size
        fontSizeList.add("20");
        fontSizeList.add("24");
        fontSizeList.add("30");
        fontSizeList.add("40");

        pack();
        setVisible(true);
        fontNameList.addItemListener(this);
        fontStyleList.addItemListener(this);
        fontSizeList.addItemListener(this);
        confirmation.addActionListener(this);
        cancel.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

    }

    public void actionPerformed(ActionEvent actionevent) {
        if (actionevent.getSource() == confirmation) {
            applyFont();
        } else if (actionevent.getSource() == cancel) {
            setVisible(false);
        }
    }

    public void itemStateChanged(ItemEvent e) {
        updateFontExample();
    }

    public void applyFont() {
        try {
            selfFont.txt1.setFont(new Font(fontName, fontStyle, fontSize));
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            setVisible(false);
        }
    }

    private void updateFontExample() {
        fontName = fontNameList.getSelectedItem();
        fontStyle = getFontStyle(fontStyleList.getSelectedItem());
        fontSize = Integer.parseInt(fontSizeList.getSelectedItem());
        example.setFont(new Font(fontName, fontStyle, fontSize));
    }

    private int getFontStyle(String style) {
        Map<String, Integer> styleMap = new HashMap<>();
        styleMap.put("Plain", Font.PLAIN);
        styleMap.put("Bold", Font.BOLD);
        styleMap.put("Italic", Font.ITALIC);
        styleMap.put("Bold Italic", Font.BOLD + Font.ITALIC);

        return styleMap.getOrDefault(style, Font.PLAIN);
    }

}
