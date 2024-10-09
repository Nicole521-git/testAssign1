package org.example;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
public class printDocument {
    /**
     * Prints the given text to a printer using the system's default printer settings.
     *
     * @param text The text to be printed.
     */
    public static void printText(String text) {
        // Get the default PrinterJob object
        PrinterJob printerJob = PrinterJob.getPrinterJob();

        // Set the Printable instance that handles the printing
        printerJob.setPrintable(new Printable() {
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                // If the page index is not zero, there is no such page to print
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }

                // Get the Graphics2D object to draw on the page
                Graphics2D g2 = (Graphics2D) graphics;

                // Set the font to Courier Bold size 18
                g2.setFont(new Font("Courier", Font.BOLD, 18));

                // Split the text into lines based on newline characters
                String[] lines = text.split("\n");

                // Start from the bottom of the printable area and move upwards
                int y = (int) pageFormat.getImageableHeight() - 50;

                // Loop through each line and draw it on the page
                for (String line : lines) {
                    y -= g2.getFontMetrics().getHeight(); // Move upwards for the next line
                    g2.drawString(line, (int) pageFormat.getImageableWidth() / 2, y); // Draw the line
                }

                // Indicate that this page is part of the print job
                return PAGE_EXISTS;
            }
        });

        // Show a print dialog to the user and if the user proceeds, print the document
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException pe) {
                // If there's an exception during printing, print the stack trace
                pe.printStackTrace();
            }
        }
    }
}
