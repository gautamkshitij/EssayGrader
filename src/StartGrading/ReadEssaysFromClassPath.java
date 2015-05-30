/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StartGrading;

import java.io.*;

/**
 *
 * @author kshitijgautam
 */
public class ReadEssaysFromClassPath {

    public void help() {
        String url = this.getClass().getResource("").getPath();
    }

    // This code is nasty and not exception safe. Just demo code!

    public static void main(String[] args) throws Exception {

        //  System.out.println(in != null);
//        stream = ReadEssaysFromClassPath.class.getClassLoader()
//                .getResourceAsStream("input/test/original/hight/11580.txt");
//        System.out.println(stream != null);
    }
}
