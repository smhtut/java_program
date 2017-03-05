/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Soe Min Htut
 */
public class CommentRemover {

    private List<Integer> lineNumberList;
    private List<String> textList, textList2;
    private boolean showLineNumber = false;
    private final String srcFile = "test.txt";
    private BufferedReader br;
    public static String slashStar = "/*", //                   block comment start literal
            slashStarText = "<<slash_star>>",
            starSlash = "*/", //                                block comment end literal
            starSlashText = "<<star_slash>>",
            doubleSlash = "//", //                              single line comment literal
            slashDoubleCode = "\\\"", //                        ignore case of string literal
            slashDoubleCodeText = "<<slash_doublecode>>",
            doubleCode = "\""; //                               string literal 

    public CommentRemover(String slashStar,
            String starSlash,
            String doubleSlash,
            String slashDoubleCode,
            String doubleCode) {
        this.slashStar = slashStar;
        this.starSlash = starSlash;
        this.doubleSlash = doubleSlash;
        this.slashDoubleCode = slashDoubleCode;
        this.doubleCode = doubleCode;

//        System.out.println(
//                "slashStar " + slashStar + "\n"
//                + "slashStarText " + slashStarText + "\n"
//                + "starSlash " + starSlash + "\n"
//                + "starSlashText " + starSlashText + "\n"
//                + "doubleSlash " + doubleSlash + "\n"
//                + "slashDoubleCode " + slashDoubleCode + "\n"
//                + "slashDoubleCodeText " + slashDoubleCodeText + "\n"
//                + "doubleCode " + doubleCode + "\n"
//        );
    }

    public void readText(File srcFile, boolean showLineNumber) {

        textList = new ArrayList<String>();
        this.showLineNumber = showLineNumber;

        try {
            /*
             BufferedReader br = new BufferedReader(new InputStreamReader(
             new FileReader(myFile), "UTF-8"));
             */
            //br = new BufferedReader(new FileReader(srcFile));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile),"UTF-8"));
            String original_text;

            while ((original_text = br.readLine()) != null) {
                textList.add(original_text);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        textList2 = (ArrayList<String>) textList;

    }

    public void readText() {

        textList = new ArrayList<String>();

        try {
            br = new BufferedReader(new FileReader(srcFile));
            String original_text;

            while ((original_text = br.readLine()) != null) {
                textList.add(original_text);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void replace_SlashDoubleCode() {

        if (slashDoubleCode == null || slashDoubleCode.isEmpty()) {
            return;
        }

        textList2 = new ArrayList<String>();

        String oneLineText, prefix, suffix;

        int postion;

        if (textList != null && !textList.isEmpty()) {

            for (int index = 0; index < textList.size(); index++) {

                oneLineText = textList.get(index);

                postion = oneLineText.indexOf(slashDoubleCode);

                while (postion != -1) {
                    suffix = oneLineText.substring(postion + slashDoubleCode.length());
                    prefix = oneLineText.substring(0, postion).concat(slashDoubleCodeText);
                    oneLineText = prefix + suffix;
                    postion = oneLineText.indexOf(slashDoubleCode);
                }

                textList2.add(oneLineText);

            }
        }

        textList = (ArrayList<String>) textList2;

    }

    public void replace_SlashDoubleCode_Back() {

        if (slashDoubleCode == null || slashDoubleCode.isEmpty()) {
            return;
        }

        textList2 = new ArrayList<String>();

        String oneLineText, prefix, suffix;

        int postion;

        if (textList != null && !textList.isEmpty()) {

            for (int index = 0; index < textList.size(); index++) {

                oneLineText = textList.get(index);

                postion = oneLineText.indexOf(slashDoubleCodeText);

                while (postion != -1) {
                    suffix = oneLineText.substring(postion + slashDoubleCodeText.length());
                    prefix = oneLineText.substring(0, postion).concat(slashDoubleCode);
                    oneLineText = prefix + suffix;
                    postion = oneLineText.indexOf(slashDoubleCodeText);
                }

                textList2.add(oneLineText);

            }

            textList = (ArrayList<String>) textList2;
        }
    }

    public void replace_SlashStar_StarSlash() {

        if (slashStar == null || slashStar.isEmpty()
                || starSlash == null || starSlash.isEmpty()) {
            return;
        }

        textList = new ArrayList<String>();

        String oneLineText, oneLineEditedText, prefix, suffix;

        int postion;

        if (textList2 != null && !textList2.isEmpty()) {

            for (int index = 0; index < textList2.size(); index++) {

                oneLineText = textList2.get(index);

                oneLineEditedText = "";

                String[] strAry = oneLineText.split(doubleCode);

                if (strAry != null && strAry.length != 0) {

                    for (int innerIndex = 0; innerIndex < strAry.length; innerIndex++) {

                        if (innerIndex % 2 == 0) {

                            oneLineEditedText += strAry[innerIndex];

                        } else {

                            // -- start <<slash star>>
                            postion = strAry[innerIndex].indexOf(slashStar);

                            while (postion != -1) {
                                suffix = strAry[innerIndex].substring(postion + slashStar.length());
                                prefix = strAry[innerIndex].substring(0, postion).concat(slashStarText);
                                strAry[innerIndex] = prefix + suffix;
                                postion = strAry[innerIndex].indexOf(slashStar);
                            }
                            // -- end <<slash star>>

                            // -- start <<star slash>>
                            postion = strAry[innerIndex].indexOf(starSlash);

                            while (postion != -1) {
                                suffix = strAry[innerIndex].substring(postion + starSlash.length());
                                prefix = strAry[innerIndex].substring(0, postion).concat(starSlashText);
                                strAry[innerIndex] = prefix + suffix;
                                postion = strAry[innerIndex].indexOf(starSlash);
                            }

                            oneLineEditedText += strAry[innerIndex];

                            // -- end <<star slash>>
                        }

                        if ((innerIndex + 1) < strAry.length) {
                            oneLineEditedText += doubleCode;
                        }
                    }

                    // <<< insert start
                    if (oneLineText.endsWith(doubleCode)) {
                        oneLineEditedText += doubleCode;
                    }
                    // <<< insert end

                    textList.add(oneLineEditedText);

                } else {

                    textList.add(oneLineText);

                }

            }

            textList2 = (ArrayList<String>) textList;
        }
    }

    public void replace_SlashStar_StarSlash_Back() {

        if (slashStar == null || slashStar.isEmpty()
                || starSlash == null || starSlash.isEmpty()) {
            return;
        }

        textList = new ArrayList<String>();

        String oneLineText, oneLineEditedText, prefix, suffix;

        int postion;

        if (textList2 != null && !textList2.isEmpty()) {

            for (int index = 0; index < textList2.size(); index++) {

                oneLineText = textList2.get(index);

                oneLineEditedText = "";

                String[] strAry = oneLineText.split(doubleCode);

                if (strAry != null && strAry.length != 0) {

                    for (int innerIndex = 0; innerIndex < strAry.length; innerIndex++) {

                        if (innerIndex % 2 == 0) {

                            oneLineEditedText += strAry[innerIndex];

                        } else {

                            // -- start <<slash star>>
                            postion = strAry[innerIndex].indexOf(slashStarText);

                            while (postion != -1) {
                                suffix = strAry[innerIndex].substring(postion + slashStarText.length());
                                prefix = strAry[innerIndex].substring(0, postion).concat(slashStar);
                                strAry[innerIndex] = prefix + suffix;
                                postion = strAry[innerIndex].indexOf(slashStarText);
                            }
                            // -- end <<slash star>>

                            // -- start <<star slash>>
                            postion = strAry[innerIndex].indexOf(starSlashText);

                            while (postion != -1) {
                                suffix = strAry[innerIndex].substring(postion + slashStarText.length());
                                prefix = strAry[innerIndex].substring(0, postion).concat(starSlash);
                                strAry[innerIndex] = prefix + suffix;
                                postion = strAry[innerIndex].indexOf(starSlashText);
                            }

                            oneLineEditedText += strAry[innerIndex];

                            // -- end <<star slash>>
                        }

                        if ((innerIndex + 1) < strAry.length) {
                            oneLineEditedText += doubleCode;
                        }

                    }

                    // <<< insert start
                    if (oneLineText.endsWith(doubleCode)) {
                        oneLineEditedText += doubleCode;
                    }
                    // <<< insert end

                    textList.add(oneLineEditedText);

                } else {

                    textList.add(oneLineText);

                }

            }

            textList2 = (ArrayList<String>) textList;
        }
    }

    public void removeSingleComment() {

        if (doubleSlash == null || doubleSlash.isEmpty()) {
            return;
        }

        textList2 = new ArrayList<String>();

        String oneLineText, oneOutput = "";

        if (textList != null && !textList.isEmpty()) {

            for (int index = 0; index < textList.size(); index++) {

                oneLineText = textList.get(index);

                String[] strAry = oneLineText.split(doubleSlash);

                int numOfSlashDoubleCode, numOfDoubleCode, remainingNumOfDoubleCodeToAddOn = 0;

                if (strAry != null && strAry.length != 0) {

                    oneLine:
                    for (int innerIndex = 0; innerIndex < strAry.length; innerIndex++) {
                        numOfDoubleCode = StringUtils.countMatches(strAry[innerIndex], doubleCode);
                        numOfDoubleCode += remainingNumOfDoubleCodeToAddOn;

                        // to remove the slashDoubleCode
                        numOfSlashDoubleCode = StringUtils.countMatches(strAry[innerIndex], slashDoubleCode);
                        if (!(numOfSlashDoubleCode < 0)) {
                            numOfDoubleCode = numOfDoubleCode - numOfSlashDoubleCode;
                        }

                        if (numOfDoubleCode % 2 != 0) {
                            //
                            // print 
                            // assign pre = 1 if has next chunk in the same line
                            //        pre = 0 else
                            // go to next chunk or next line
                            //
//                            System.out.print(strAry[innerIndex]);
                            oneOutput += strAry[innerIndex];

                            if (innerIndex + 1 < strAry.length) {
                                remainingNumOfDoubleCodeToAddOn = 1;

                                // <<< insert
                                oneOutput += doubleSlash;

                            } else {
                                remainingNumOfDoubleCodeToAddOn = 0;
                            }

//                            System.out.print(doubleSlash);
                            // >>> comment
                            // oneOutput += doubleSlash;
                        } else {
                            //
                            // print and go to next line
                            // assign pre = 0
                            //
//                            System.out.print(strAry[innerIndex]);
                            oneOutput += strAry[innerIndex];

                            remainingNumOfDoubleCodeToAddOn = 0;

                            break oneLine;

                        }
                    }
//                    System.out.println();
                    textList2.add(oneOutput);
                    oneOutput = "";

                } else {

//                    System.out.println(oneLineText);
                    textList2.add(oneOutput);
                    oneOutput = "";

                }
            }

            textList = (ArrayList<String>) textList2;
        }
    }

    public void removeMultipleComment() {

        if (slashStar == null || starSlash.isEmpty()
                || starSlash == null || starSlash.isEmpty()) {
            return;
        }

        textList = new ArrayList<String>();

        lineNumberList = new ArrayList<Integer>();
        int lineNumber = 0;

        String original_text, text = "";

        boolean stillIn = false;

        int start_index = 0, end_index = 0,
                numOfDoubleCode = 0, remainingNumOfDoubleCodeToAddOn = 0,
                numOfSlashDoubleCode = 0;

        slashStar = slashStar.toLowerCase();
        starSlash = starSlash.toLowerCase();

        /*
         * -- to find text in multiple line
         */
        for (int index = 0; index < textList2.size(); index++) {

            original_text = textList2.get(index);

            lineNumber++;

            text = original_text;

            while (true) {

                if (stillIn == false) {

                    start_index = text.indexOf(slashStar);
                    end_index = text.indexOf(starSlash, start_index + slashStar.length());

                    /**/
                    numOfDoubleCode = StringUtils.countMatches(text.substring(0, (start_index == -1 ? 0 : start_index)), doubleCode);
                    numOfDoubleCode += remainingNumOfDoubleCodeToAddOn;

                    // to remove the slashDoubleCode
                    numOfSlashDoubleCode = StringUtils.countMatches(text.substring(0, (start_index == -1 ? 0 : start_index)), slashDoubleCode);
                    if (!(numOfSlashDoubleCode < 0)) {
                        numOfDoubleCode = numOfDoubleCode - numOfSlashDoubleCode;
                    }

                    if (numOfDoubleCode % 2 != 0) {
                        start_index = -1;
                        end_index = -1;

                    }

                    /**/
                } else {

                    start_index = 0;
                    end_index = text.indexOf(starSlash);
                }

                if (stillIn == false && start_index != -1 && end_index != -1) {
                    lineNumberList.add(lineNumber);
                    textList.add(showLineNumber
                            ? lineNumber + "    " + text.substring(0, start_index) + text.substring(end_index + starSlash.length())
                            : text.substring(0, start_index) + text.substring(end_index + starSlash.length()));

                } else if (stillIn == false && start_index != -1 && end_index == -1) {
                    stillIn = true;
                    lineNumberList.add(lineNumber);
                    textList.add(showLineNumber ? lineNumber + "    " + text.substring(0, start_index)
                            : text.substring(0, start_index));

                } else if (stillIn && end_index == -1) {

                } else if (stillIn && end_index != -1) {
                    stillIn = false;
                    lineNumberList.add(lineNumber);
                    textList.add(showLineNumber ? lineNumber + "    " + text.substring(end_index + starSlash.length())
                            : text.substring(end_index + starSlash.length()));

                } else {
                    if (start_index != -1) {
                        lineNumberList.add(lineNumber);
                        textList.add(showLineNumber ? lineNumber + "    " + text.substring(0, start_index)
                                : text.substring(0, start_index));

                    } else if (text != null && !text.isEmpty()) {

                        lineNumberList.add(lineNumber);
                        textList.add(showLineNumber ? lineNumber + "    " + text
                                : text);

                    }
                }

                if (stillIn == false && end_index != -1 && text.indexOf(slashStar, end_index) != -1) {
                    text = textList.get(textList.size() - 1);
                    textList.remove(textList.size() - 1);
                    continue;

                } else {
                    break;
                }

            }
        }

        textList2 = (ArrayList<String>) textList;
    }

    public List<String> getTextList() {
        return textList;
    }

    private void printTextList() {
        if (textList != null && !textList.isEmpty()) {
            for (int index = 0; index < textList.size(); index++) {
                if (!textList.get(index).trim().isEmpty()) {
                    System.out.println(textList.get(index));
                }
            }
        }
    }

//    public static void main(String[] args) {
//        removeSingleComment();
//        removeSingleComment();
//        readText();
//        removeSingleComment();
//        removeMultipleComment();
//        printTextList();
//
//    }
}
