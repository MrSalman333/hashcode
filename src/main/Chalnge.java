/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MrS
 */
public class Chalnge {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<Photo> photos = read("a_example.txt");
        for (Photo p : photos) {
            System.out.println(p);
        }
        ArrayList<Slide> slides = makeSlideList(photos);
        ArrayList<Slide> ordered = makeOrdered(slides);
        write("test1.txt", ordered);
        
    }

    private static ArrayList<Slide> makeSlideList(ArrayList<Photo> photos) {
        ArrayList<Slide> slides = new ArrayList<Slide>();

        for (Photo p : photos) {
            if (p.getCharacter() == 'H') {
                slides.add(new Slide(p));
            } else {
                int index = bestmMtch(photos, p.getID());
                if(index != -1){
                Photo p2 = photos.get(index);
                slides.add(new Slide(p2, p));
                }else{
                    System.out.println("problim");
                }
            }
        }
        return slides;
    }

    private static ArrayList<Slide> makeOrdered(ArrayList<Slide> slides) {
        ArrayList<Slide> ordered = new ArrayList<Slide>(slides.size());

        ordered.add(slides.get(0));

        Slide last = ordered.get(0);
        int bestCaseScoure = -1;
        for (Slide s1 : slides) {

            if (s1.isAdded()) {
                continue;
            }
            Slide bestCase = null;

            for (Slide s2 : slides) {
                if (s2.isAdded()) {
                    continue;
                }
                int scoure = score(last, s2);
                if (scoure > bestCaseScoure) {
                    bestCaseScoure = scoure;
                    bestCase = s2;
                }
            }
            ordered.add(bestCase);
        }
        return ordered;
    }

    public static ArrayList<Photo> read(String fileName) {
        ArrayList<Photo> photos = null;
        try {
            Scanner reader = new Scanner(new File(fileName));
            int numberOfPhotos = reader.nextInt();
            photos = new ArrayList<>(numberOfPhotos);

            for (int i = 0; i < numberOfPhotos; i++) {

                char orintion = reader.next().charAt(0);
                int numberOfTags = reader.nextInt();
                String[] tags = new String[numberOfTags];
                for (int j = 0; j < tags.length; j++) {
                    tags[j] = reader.next();
                }
                photos.add(new Photo(orintion, tags));
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Chalnge.class.getName()).log(Level.SEVERE, null, ex);
        }

        return photos;
    }

    public static void write(String fileName, ArrayList<Slide> list) {

        int numberOfSlides = list.size();
        int ids[][] = {{0}, {3}, {1, 2}};
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(numberOfSlides);
            for (Slide s : list) {
                if (s.getNumberOfPhoto() == 1) {
                    writer.println(s.getId1());
                } else {
                    writer.println(s.getId1() + " " + s.getId2());
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Chalnge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int score(Slide s1, Slide s2) {
        int shared = 0;
        int s1Extra = 0;
        int s2Extra = 0;
        String[] s1Tags = s1.getTagOfSilde();
        String[] s2Tags = s2.getTagOfSilde();

         for (int i = 0; i < s1Tags.length; i++) {
            for (int j = 0; j < s2Tags.length; j++) {
                if (s1Tags[i].equals(s2Tags[j])) {
                    shared++;
                }
            }
            s1Extra = s1Tags.length -shared ;
            s2Extra = s2Tags.length - shared;
        }
        /*boolean isS1Extra;
        boolean isS2Extra;
        for (int i = 0; i < s1Tags.length; i++) {
            isS1Extra = true;
            for (int j = 0; j < s2Tags.length; j++) {
                if (s1Tags[i].equals(s2Tags[j])) {
                    shared++;
                    isS1Extra = false;
                }

            }
            if (isS1Extra) {
                s1Extra++;
            }
        }

        for (int i = 0; i < s2Tags.length; i++) {
            isS2Extra = true;
            for (int j = 0; j < s1Tags.length; j++) {
                if (s2Tags[i].equals(s1Tags[j])) {
                    isS2Extra = false;
                }
            }
            if (isS2Extra) {
                s2Extra++;
            }
        }*/

        return Math.min(Math.min(shared, s1Extra), s2Extra);
    }

    public static int bestmMtch(ArrayList<Photo>photos, int id) {
        String[] tags1 = photos.get(id).getTags();

        int[] scores = new int[photos.size()];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = 9999999;
        }
        int count;
        for (int i = 0; i < photos.size(); i++) {
            count = 0;
            if (photos.get(i).isChosen() || photos.get(i).getCharacter() == 'H' || i == id) {
                continue;
            }
            for (int j = 0; j < tags1.length; j++) {
                for (int k = 0; k < photos.get(i).getTags().length; k++) {
                    if (tags1[j].equalsIgnoreCase(photos.get(i).getTags()[k])) {
                        count++;
                    }
                }

            }
            scores[i] = count;
        }
        int min = 9999999;
        int minIndex = -1;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] < min) {
                min = scores[i];
                minIndex = i;
            }
        }

        return minIndex;

    }

}
