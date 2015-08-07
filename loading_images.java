import java.nio.file.*;
import java.nio.*;
import java.io.*;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.*;
import java.util.*;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.*;

class ImageFolderAccess{
    public static void main(String[] args) {
        List<Path> image_paths = null;

        try(Stream<Path> paths = Files.list(Paths.get("images"))){
            image_paths = paths.collect(Collectors.toList());
        }
        catch(IOException e){
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Image loading demo");
        frame.setSize(800,800);
        frame.setLayout(new GridLayout(4,2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setBorder(BorderFactory.createLineBorder(Color.black));



        // images.stream().forEach(System.out::println);f
        for(Path p: image_paths){

        try {
         BufferedImage img = ImageIO.read(new File(p.toString()));

         frame.add(new JLabel(new ImageIcon(img)));
         frame.add(new JLabel(new ImageIcon(img)));

         // JOptionPane.showMessageDialog(null, label);
      } catch (IOException e) {
         e.printStackTrace();
      }
        }

        frame.pack();
        frame.setVisible(true);

        // String[] words = {"hello", "hi", "chandan"};
        // Arrays.stream(words).sorted().forEach(System.out::println);
    }
}
