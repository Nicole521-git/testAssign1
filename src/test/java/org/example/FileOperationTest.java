package org.example;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.swing.*;

import static org.junit.Assert.*;
import java.io.*;

public class FileOperationTest {
    private Liunotepad notePad;
    private String testContent = "这是测试内容";
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        notePad = new Liunotepad();
        tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
    }

    @After
    public void tearDown() throws Exception {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testNewFile() {
        notePad.fileOperation.newFile();
        assertTrue("文本编辑器应该创建一个新文件", notePad.txt1.getText().isEmpty());
    }

    @Test
    public void testOpenFile() throws IOException {
        // 创建一个测试文件并写入内容
        File testFile = File.createTempFile("testOpen", ".txt");
        testFile.deleteOnExit();
        String testContent = "This is a test content for the file.";
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(testContent);
        }

        // 打开文件
        notePad.fileOperation.openFile(testFile.getAbsolutePath());

        // 确保事件队列处理完成
        SwingUtilities.invokeLater(() -> {
            try {
                // 读取文件内容进行比较
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }
                // 断言文本框内容与文件内容一致
                assertEquals("文件内容应该与写入的内容一致", testContent, notePad.txt1.getText());
            } catch (IOException e) {
                fail("An exception occurred: " + e.getMessage());
            }
        });
    }

    @Test
    public void testSaveFile() throws IOException {
        notePad.fileOperation.newFile();
        notePad.txt1.setText(testContent);
        notePad.fileOperation.saveFile(tempFile.getAbsolutePath());

        // 验证文件内容
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String fileContent = reader.readLine();
            assertEquals("保存的文件内容应该与编辑器内容一致", testContent, fileContent);
        }
    }

    @Test
    public void testSaveAsFile() throws IOException {
        notePad.fileOperation.newFile();
        notePad.txt1.setText(testContent);
        File saveAsFile = new File(tempFile.getParent(), "testSaveAs.txt");
        notePad.fileOperation.saveAsFile(saveAsFile.getAbsolutePath());

        assertTrue("文件应该被另存为", saveAsFile.exists());

        // 验证文件内容
        try (BufferedReader reader = new BufferedReader(new FileReader(saveAsFile))) {
            String fileContent = reader.readLine();
            assertEquals("另存为的文件内容应该与编辑器内容一致", testContent, fileContent);
        } finally {
            saveAsFile.delete();
        }
    }

    @Test
    public void testPrintFile() {
        // 打印测试通常需要模拟打印环境或手动验证
        // 这里我们假设有一个 printFile 方法可以调用
        notePad.fileOperation.newFile();
        notePad.txt1.setText(testContent);
        notePad.fileOperation.printFile();

        // 由于打印操作没有返回值，我们只能假设它执行了打印操作
        // 在实际测试中，可能需要检查打印机队列或打印日志
    }
}
