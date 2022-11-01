/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flashcards;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.SwingUtilities;

public class FlashCardPlayer {

    private JTextArea display;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private Iterator cardIterator;
    private FlashCard currentCard;
    private int currentIndex;
    private boolean isShowAnswer;
    JFrame frame;
    private JButton showAnswer;

    public FlashCardPlayer() {
        frame = new JFrame("Flash Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        Font font = new Font("Helvetica Neue", Font.BOLD, 21);
        display = new JTextArea(10, 20);
        // answer = new JTextArea(6,20);

        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setFont(font);

        JScrollPane dScroll = new JScrollPane(display);

        dScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        dScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        showAnswer = new JButton("Show Answer");
        panel.add(dScroll);
        panel.add(showAnswer);

        showAnswer.addActionListener(new NextCardEventListener());
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(640, 600);
        frame.setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
        loadMenuItem.addActionListener(new OpenMenuListener());

        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardPlayer();
            }
        });
    }

    class OpenMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }

    }

    private void loadFile(File selectedFile) {
        cardList = new ArrayList<FlashCard>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                makecard(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not read the file");
            e.printStackTrace();
        }
        cardIterator = cardList.iterator();
        showNextCard();
    }

    private void makecard(String lineToParse) {
        String[] result = lineToParse.split("/");
        FlashCard card = new FlashCard(result[0], result[1]);
        cardList.add(card);
        System.out.println("you made a card");
    }

    class NextCardEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isShowAnswer) {
                // Show the answer since they've seen the question
                display.setText(currentCard.getAnswer());
                showAnswer.setText("Next Card");
                isShowAnswer = false;
            } else {
                // show next question
                if (cardIterator.hasNext()) {

                    //we know our arraylist of cards is not empty, so show next card
                    showNextCard();
                } else {

                    // no more cards to show
                    display.setText("That was last card.");
                    showAnswer.setEnabled(false);

                }
            }
        }

    }

    private void showNextCard() {
        currentCard = (FlashCard) cardIterator.next();
        display.setText(currentCard.getQuestion());
        showAnswer.setText("Show Answer");
        isShowAnswer = true;

    }
}
