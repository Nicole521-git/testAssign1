package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.lang.*;
import java.io.*;
import java.io.File;
import java.io.FileWriter;


public class Liunotepad extends Frame implements ActionListener, ItemListener {
    MenuBar mnub;
    Menu file, search, view, manage, help;
    MenuItem New, NewWindow, Open, Save, SaveAs, Print, Exit;
    MenuItem Search;
    MenuItem font, color;
    MenuItem SelectAll, Copy, Paste, Cut, Delete, TD;
    MenuItem About;

    CheckboxMenuItem item8;
    Clipboard clp = null; // Shear plate
    RSyntaxTextArea txt1;
    FileDialog open, save;
    Scrollbar scrollbar;
    ScrollPane scrollpane;
    Image First_Image;


    public class FileOperation {
        public void newFile() {
            txt1.setText("");
        }

        public void openFile(String filePath) {
            // Handle file opening, read the content based on file type
            try {
                File file = new File(filePath);
                if (filePath.endsWith(".odt")) {
                    // Use Apache POI to read .odt files
                    try (OPCPackage container = OPCPackage.open(file);
                         XWPFDocument document = new XWPFDocument(container)) {
                        StringBuilder sb = new StringBuilder();
                        for (XWPFParagraph para : document.getParagraphs()) {
                            for (XWPFRun run : para.getRuns()) {
                                String text = run.getText(-1);
                                if (text != null) {
                                    sb.append(text).append("\n");
                                }
                            }
                        }
                        txt1.setText(sb.toString());
                    }
                } else if (filePath.endsWith(".rtf")) {
                    // Use RTFEditorKit to read .rtf files
                    try (InputStream inputStream = new FileInputStream(file)) {
                        StyledDocument doc = new DefaultStyledDocument();
                        EditorKit editorKit = new RTFEditorKit();
                        editorKit.read(inputStream, doc, 0);
                        txt1.setText(doc.getText(0, doc.getLength()));
                    } catch (IOException | BadLocationException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try (FileReader fileReader = new FileReader(file);
                         BufferedReader br = new BufferedReader(fileReader)) {
                        txt1.setText("");
                        String str;
                        while ((str = br.readLine()) != null) {
                            txt1.append(str + "\n");
                        }
                    }
                }
            } catch (Exception fe) {
                fe.printStackTrace();
            }
        }

        public void saveFile(String filePath) {
            try {
                // Save
                // Save the current text to a file
                File file = new File(filePath);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                String temp = txt1.getText();
                bw.write(temp, 0, temp.length());
                bw.flush();
                bw.close();
            } catch (Exception fge) {
                fge.printStackTrace();
            }
        }

        public void saveAsFile(String filePath) {
            try {
                // 'Save As' functionality allowing different file formats
                File file = new File(filePath);
                String temp = txt1.getText();
                if (filePath.endsWith(".java") || filePath.endsWith(".txt")) {
                    FileWriter fw = new FileWriter(file);
                    fw.write(temp);
                    fw.close();
                } else if (filePath.endsWith(".doc")) {
                    // Use Apache POI to write .doc files
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        XWPFDocument document = new XWPFDocument();
                        XWPFParagraph paragraph = document.createParagraph();
                        XWPFRun run = paragraph.createRun();
                        run.setText(temp);
                        document.write(fos);
                    }
                } else if (filePath.endsWith(".pdf")) {
                    try (PDDocument document = new PDDocument()) {
                        PDPage page = new PDPage();
                        document.addPage(page);
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        PDFont font = PDType1Font.HELVETICA;
                        contentStream.beginText();
                        contentStream.setFont(font, 12);
                        contentStream.newLineAtOffset(40, 750);
                        contentStream.showText(temp);
                        contentStream.endText();
                        contentStream.close();
                        document.save(file);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void printFile() {
            // Print the text using the printDocument utility class
            String textToPrint = txt1.getText();
            printDocument.printText(textToPrint);
        }
    }

   public FileOperation fileOperation;

    public Liunotepad() {
        super("Notebook");
        this.setVisible(true);
        this.setBackground(Color.blue);
        this.setResizable(true);
        setLayout(new FlowLayout());
        scrollpane = new ScrollPane();
        scrollbar = new Scrollbar();
        setBounds(10, 10, 100, 60);
        txt1 = new RSyntaxTextArea(20, 60);
        txt1.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA); // 默认使用 Java 语法高亮
        RTextScrollPane sp = new RTextScrollPane(txt1);
        this.add(sp);

        this.scrollpane.add(scrollbar);
        this.scrollpane.add(txt1);
        this.scrollpane.setSize(1000, 680);
        mnub = new MenuBar();
        this.setMenuBar(mnub);

        open = new FileDialog(this, "Open", FileDialog.LOAD);
        save = new FileDialog(this, "Save", FileDialog.SAVE);
        First_Image = this.getIconImage();
        file = new Menu("File");
        search = new Menu("Search");
        view = new Menu("View");
        manage = new Menu("Manage");
        help = new Menu("Help");

        New = new MenuItem("New");
        NewWindow = new MenuItem("New Window");
        Open = new MenuItem("Open");
        Save = new MenuItem("Save");
        Print = new MenuItem("Print");

        Exit = new MenuItem("Exit");

        Search = new MenuItem("Search");

        font = new MenuItem("Font");
        color = new MenuItem("Color");

        SelectAll = new MenuItem("Select All");
        Copy = new MenuItem("Copy");
        Cut = new MenuItem("Cut");
        Paste = new MenuItem("Paste");
        Delete = new MenuItem("Delete");
        TD = new MenuItem("Time and Date");

        About = new MenuItem("About");

        mnub.add(file);
        mnub.add(search);
        mnub.add(view);
        mnub.add(manage);
        mnub.add(help);

        file.add(New);
        file.add(NewWindow);
        file.add(Open);
        file.add(Save);
        file.add(Print);
        file.add(Exit);

        search.add(Search);

        view.add(font);
        view.add(color);

        manage.add(SelectAll);
        manage.add(Copy);
        manage.add(Paste);
        manage.add(Cut);
        manage.add(Delete);
        manage.add(TD);

        help.add(About);

        add(scrollpane);
        pack();
        clp = getToolkit().getSystemClipboard();
        file.addActionListener(this);
        search.addActionListener(this);
        view.addActionListener(this);
        manage.addActionListener(this);
        help.addActionListener(this);
        New.addActionListener(this);
        NewWindow.addActionListener(this);
        Open.addActionListener(this);
        Save.addActionListener(this);
        Print.addActionListener(this);
        Exit.addActionListener(this);
        Search.addActionListener(this);
        font.addActionListener(this);
        color.addActionListener(this);
        SelectAll.addActionListener(this);
        Copy.addActionListener(this);
        Paste.addActionListener(this);
        Cut.addActionListener(this);
        Delete.addActionListener(this);
        TD.addActionListener(this);
        About.addActionListener(this);
        addWindowListener(new cls());

        fileOperation = new FileOperation();
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public void actionPerformed(ActionEvent e) { // Clear the text area when the New menu item is selected
        if (e.getSource() == New) {
            fileOperation.newFile();
        } else if (e.getSource() == NewWindow) {
            new Liunotepad().setVisible(true);
        } else if (e.getSource() == Open) {
            open.setVisible(true);
            if (open.getFile() != null) {
                String filePath = open.getDirectory() + open.getFile();
                fileOperation.openFile(filePath);
            }
        } else if (e.getSource() == Save) {
            save.setVisible(true);
            if (save.getFile() != null) {
                String filePath = save.getDirectory() + save.getFile();
                fileOperation.saveFile(filePath);
            }
        } else if (e.getSource() == SaveAs) {
            save.setVisible(true);
            if (save.getFile() != null) {
                String filePath = save.getDirectory() + save.getFile();
                fileOperation.saveAsFile(filePath);
            }
        }else if (e.getSource() == Print) {
            fileOperation.printFile();
        } else if (e.getSource() == Exit) {
            // Exit the application
            System.exit(0);
        }  else if (e.getSource() == font) {
            // Open font selection dialog
            Self_Font myFont = new Self_Font(this);
        } else if (e.getSource() == color) {
            // Open color selection dialog
            Self_Color mycolor = new Self_Color(this);
        } else if (e.getSource() == Search) {
            // Open search dialog
            Find find = new Find(this);
        } else if (e.getSource() == TD) {
            // Insert date and time at the beginning of the text area
            mydatetime t = new mydatetime();
            txt1.insert("\nData:" + t.year + " " + t.month + " " + t.day + "   Time: " + t.datetime + "  " + t.week, 0);
        } else if (e.getSource() == SelectAll) {
            // Select all text in the text area
            txt1.selectAll();
        }else if (e.getSource() == Copy) {
            // Copy selected text to clipboard
            String selectedText = txt1.getSelectedText();
            clp.setContents(new StringSelection(selectedText), null);
        } else if (e.getSource() == Paste) {
            // Paste text from clipboard to text area
            Transferable tText = clp.getContents(null);
            try {
                if (tText != null) {
                    String text = (String) tText.getTransferData(DataFlavor.stringFlavor);
                    txt1.insert(text, txt1.getCaretPosition());
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == Cut) {
            String selectedText = txt1.getSelectedText();
            clp.setContents(new StringSelection(selectedText), null);
            txt1.replaceRange("", txt1.getSelectionStart(), txt1.getSelectionEnd());
        } else if (e.getSource() == Delete) {
            txt1.replaceRange("", txt1.getSelectionStart(), txt1.getSelectionEnd());
        } else if (e.getSource() == font) {
            Self_Font myFont = new Self_Font(this);
        } else if (e.getSource() == color) {
            Self_Color mycolor = new Self_Color(this);
        } else if (e.getSource() == About) {
            about d = new about();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (item8.getState() == true) {
            try {
                Toolkit toolkit = getToolkit();
                Image image = toolkit.getImage("liunotepad.ico");
                this.setIconImage(image);
            } catch (Exception exception) {
                System.out.println("Image file not found");
            }
        } else {
            this.setIconImage(First_Image);
        }
    }




        public RSyntaxTextArea txt1() {
            return txt1;
        }

    public static void main(String args[]) {
        Liunotepad s = new Liunotepad();
    }
}
