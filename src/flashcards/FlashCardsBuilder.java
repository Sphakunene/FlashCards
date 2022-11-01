/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flashcards;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

/**
 *
 * @author Coleen Kunene
 */
public class FlashCardsBuilder {

    private JTextArea questions;
    private JTextArea answers;
    private ArrayList<FlashCard> cardList;
    JFrame frame;

    public FlashCardsBuilder() {
        frame = new JFrame("Flash Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        Font font = new Font("Helvetica Neue", Font.BOLD, 21);
        questions = new JTextArea(6, 20);
        answers = new JTextArea(6, 20);

        questions.setLineWrap(true);
        questions.setWrapStyleWord(true);
        questions.setFont(font);

        JScrollPane aScroll = new JScrollPane(answers);

        aScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next Button");
        cardList = new ArrayList<>();

        JLabel aLabel = new JLabel("Answer");
        JLabel qLabel = new JLabel("Question");

        JScrollPane qScroll = new JScrollPane(questions);

        answers.setLineWrap(true);
        answers.setWrapStyleWord(true);
        answers.setFont(font);

        qScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(qLabel);
        panel.add(qScroll);
        panel.add(aLabel);
        panel.add(aScroll);
        panel.add(nextButton);

        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.getContentPane().add(BorderLayout.CENTER, panel);

        nextButton.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("save");

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuItem());

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardsBuilder();

            }
        });
    }

    class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCard card = new FlashCard(questions.getText(), answers.getText());
            cardList.add(card);
            clearCard();
        }
    }
    
     private void clearCard() {

            questions.setText("");
            answers.setText("");
            questions.requestFocus();
        }

    class NewMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("newItem clicked");

        }

    }

    class SaveMenuItem implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            FlashCard card = new FlashCard(questions.getText(), answers.getText());
            cardList.add(card);
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());

        }

        private void saveFile(File selectedFile) {

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                Iterator<FlashCard> cardIterator = cardList.iterator();

                while (cardIterator.hasNext()) {

                    FlashCard card = (FlashCard) cardIterator.next();
                    writer.write(card.getQuestion() + "/");
                    writer.write(card.getAnswer() + "\n");
                }
                writer.close();

            } catch (Exception e) {
                System.out.println("Couldn't write to a file");
                e.printStackTrace();

            }

        }

    }
}
