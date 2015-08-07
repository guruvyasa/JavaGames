import javax.swing.*;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.stream.*;
import java.nio.file.*;
import java.nio.*;
import java.io.*;
import java.util.*;

class Board extends JPanel{
    //make tile part of the board
    private class Tile extends JButton {
        private String icon_path;

        private State state = State.REVEALED;
        private ImageIcon icon = null;
        private int position;

        public Tile(ImageIcon icon, String icon_path) throws IOException{
            super(icon);
            this.icon_path = icon_path;
            this.icon = new ImageIcon(ImageIO.read(new File(icon_path)));
        }
        public String getIconPath(){
            return icon_path;
        }

        public int getPosition(){
            return position;
        }

        public void setPostition(int position){
            this.position = position;
        }

        public State getState(){
            return state;
        }

        public void toggleState(){
            State current_state = this.getState();
            System.out.println(current_state);
            switch(current_state){
                case REVEALED: setState(State.CLOSED);
                                         break;
                case CLOSED: setState(State.REVEALED);
                                        break;
                default: break;
            }
        }


        public void setState(State s){
            state = s;
            switch(s){
                case REVEALED: setIcon(icon);

                                         break;
                case CLOSED: setIcon(wrapper_image);
                                      break;
                default: break;
            }

            }


        public void updateImage(ImageIcon image){
            setIcon(image);
        }
    } //end of Tile class


    class TileToggler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(Tile t: tiles){
                t.toggleState();
            }
        }
    }

    public static enum State{
            REVEALED,
            CLOSED,
            FINALIZED;
        };

    private ImageIcon wrapper_image = null;
    private int num_rows=4;
    private int num_columns = 2;
    private boolean playable = true; //playable tells us whether the board is playable, true means game not over
    private List<Tile> tiles = new LinkedList<Tile>();
    private int previous_selected = -1;

    public boolean isPlayable(){
        return playable;
    }

    public Board(int rows, int columns) throws IOException{
        this();
        this.num_rows = rows;
        this.num_columns = columns;
        wrapper_image = new ImageIcon(ImageIO.read(new File("wrapper_image.png")));
        javax.swing.Timer timer = new javax.swing.Timer(1000, new TileToggler());

        timer.setRepeats(false);
        timer.start();
    }

    public Board(){
        setLayout(new GridLayout(num_rows, num_columns));
        fillBoard();
    }

    private void fillBoard(){
        List<Path> image_paths = null;
        try(Stream<Path> paths = Files.list(Paths.get("images"))){
            image_paths = paths.collect(Collectors.toList());
        }
        catch(IOException e){
            e.printStackTrace();
        }
        // Tile temp_tile = null;

        for(Path p: image_paths){

            try {
             BufferedImage img = ImageIO.read(new File(p.toString()));
             Tile temp_tile1 = new Tile(new ImageIcon(img), p.toString());
             Tile temp_tile2 = new Tile(new ImageIcon(img), p.toString());

             tiles.add(temp_tile1);
             tiles.add(temp_tile2);

          }
          catch (IOException e) {
             e.printStackTrace();
          }
        }//end of for loop
        int pos=0;
        Collections.shuffle(tiles);
        for(Tile t: tiles){
            t.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    t.toggleState();
                    if (previous_selected < 0){
                        previous_selected = t.getPosition();
                    // System.out.println(previous_selected.getIconPath());

                    }
                    else{
                        Tile previous_tile = tiles.get(previous_selected);
                        if(previous_tile.getIconPath().equals(t.getIconPath())){
                            previous_tile.setState(State.FINALIZED);
                            t.setState(State.FINALIZED);
                        }
                        else{
                            t.toggleState();
                            previous_tile.toggleState();
                        }

                    previous_selected = -1;


                    }
                    // System.out.println(t.getIconPath());
                }
            });
            t.setPostition(pos);
            pos++;
            add(t);
        }
    }// end of fill board

}
