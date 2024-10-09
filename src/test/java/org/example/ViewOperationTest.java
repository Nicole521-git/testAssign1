package org.example;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.Assert.assertEquals;

public class ViewOperationTest {
    private Liunotepad liunotepad;
    private Self_Font selfFont;
    private Self_Color selfColor;

    @Before
    public void setUp() {
        liunotepad = new Liunotepad();
        selfFont = new Self_Font(liunotepad);
        selfColor = new Self_Color(liunotepad);
    }

    @Test
    public void testFontSelection() {
        // 模拟用户选择字体
        selfFont.fontNameList.select(0);
        selfFont.fontStyleList.select(0);
        selfFont.fontSizeList.select(0);
        selfFont.applyFont();

        // 验证字体是否被正确应用
        Font currentFont = liunotepad.txt1.getFont();
        assertEquals("Courier", currentFont.getName());
        Object selectedStyleItem = selfFont.fontStyleList.getSelectedItem();
        assertEquals("Plain", selectedStyleItem.toString());
        Object selectedItem = selfFont.fontSizeList.getSelectedItem();
        assertEquals("8", selectedItem.toString());
    }

    @Test
    public void testColorSelection() {
        // 模拟用户选择颜色
        selfColor.colorList.select(1);
        selfColor.actionPerformed(new ActionEvent(selfColor.confirmation, ActionEvent.ACTION_PERFORMED, "OK"));

        // 验证颜色是否被正确应用
        Color currentColor = liunotepad.txt1.getForeground();
        assertEquals(Color.BLACK, currentColor);
    }
}
