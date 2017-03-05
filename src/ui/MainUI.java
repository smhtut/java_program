/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import comment.CommentRemover;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Soe Min Htut
 */
public class MainUI {

    private JCheckBox jCheckBox;
    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel northPanel, centerPanel, zeroPanel, firstPanel, secondPanel, thirdPanel, fourthPanel, fifthPanel;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JTextField txtBlkCmtStart, txtBlkCmtEnd, txtSnglCmt, txtStrngLtrl, txtIgnrCase;
    private CommentRemover commentRemover;
    private java.io.File file;
    private List<String> textList;

    public MainUI() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Universal Comment Remover");
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(new BorderLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        textArea = new JTextArea();
        textArea.setEditable(true);
        scrollPane = new JScrollPane(textArea);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane);

//        northPanel = new JPanel(new GridLayout(1, 1));
        northPanel = new JPanel(new GridLayout(6, 1));

        zeroPanel = new JPanel(new FlowLayout());

        txtBlkCmtStart = new JTextField(CommentRemover.slashStar);
        txtBlkCmtStart.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textArea.setText("");
            }
        });
        firstPanel = new JPanel(new GridLayout(1, 2));
        firstPanel.add(new JLabel("block comment start literal "));
        firstPanel.add(txtBlkCmtStart);

        txtBlkCmtEnd = new JTextField(CommentRemover.starSlash);
        txtBlkCmtEnd.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textArea.setText("");
            }
        });
        secondPanel = new JPanel(new GridLayout(1, 2));
        secondPanel.add(new JLabel("block comment end literal "));
        secondPanel.add(txtBlkCmtEnd);

        txtSnglCmt = new JTextField(CommentRemover.doubleSlash);
        txtSnglCmt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textArea.setText("");
            }
        });
        thirdPanel = new JPanel(new GridLayout(1, 2));
        thirdPanel.add(new JLabel("single line comment literal "));
        thirdPanel.add(txtSnglCmt);

        txtStrngLtrl = new JTextField(CommentRemover.doubleCode);
        txtStrngLtrl.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textArea.setText("");
            }
        });
        fourthPanel = new JPanel(new GridLayout(1, 2));
        fourthPanel.add(new JLabel("string literal "));
        fourthPanel.add(txtStrngLtrl);

        txtIgnrCase = new JTextField(CommentRemover.slashDoubleCode);
        txtIgnrCase.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textArea.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textArea.setText("");
            }
        });
        fifthPanel = new JPanel(new GridLayout(1, 2));
        fifthPanel.add(new JLabel("ignore case of string literal "));
        fifthPanel.add(txtIgnrCase);

        /*
         textArea
         */
        jCheckBox = new JCheckBox(" Show Line Number ");
        jCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setText("");
            }
        });

        scrollPane.setEnabled(true);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.add(statusLabel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        MainUI instance = new MainUI();
        instance.showFileChooser();
    }

    private void showFileChooser() {
        final JFileChooser fileDialog = new JFileChooser();
        JButton showFileDialogButton = new JButton("Open File");

        showFileDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileDialog.showOpenDialog(mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileDialog.getSelectedFile();

                    // -- start to proceed CommentRemover
                    /*
                     CommentRemover(String slashStar,
                     String starSlash,
                     String doubleSlash,
                     String slashDoubleCode,
                     String doubleCode)
                     */
                    commentRemover = new CommentRemover(txtBlkCmtStart.getText(),
                            txtBlkCmtEnd.getText(),
                            txtSnglCmt.getText(),
                            txtIgnrCase.getText(),
                            txtStrngLtrl.getText());

//                    commentRemover = new CommentRemover();
                    commentRemover.readText(file, jCheckBox.isSelected());

                    commentRemover.replace_SlashDoubleCode();
                    commentRemover.replace_SlashStar_StarSlash();

                    commentRemover.removeSingleComment();
                    commentRemover.removeMultipleComment();

                    commentRemover.replace_SlashStar_StarSlash_Back();
                    commentRemover.replace_SlashDoubleCode_Back();

                    textList = commentRemover.getTextList();

                    if (textList != null && !textList.isEmpty()) {
                        textArea.setText("");
                        for (int index = 0; index < textList.size(); index++) {
                            if (!textList.get(index).trim().isEmpty()) {
                                textArea.append(textList.get(index));
                                textArea.append("\n");
                            }
                        }
                    } else {
                        textArea.setText("");
                        textArea.setText(" empty at " + Calendar.getInstance().getTime());
                    }

                    // -- finish CommentRemover
                    statusLabel.setText("File Selected :" + file.getName());
                } else {
                    statusLabel.setText("Open command cancelled by user.");
                }
            }
        });

        zeroPanel.add(showFileDialogButton);
        zeroPanel.add(jCheckBox);

        northPanel.add(zeroPanel);
        northPanel.add(firstPanel);
        northPanel.add(secondPanel);
        northPanel.add(thirdPanel);
        northPanel.add(fourthPanel);
        northPanel.add(fifthPanel);

        mainFrame.setVisible(true);
    }
}
