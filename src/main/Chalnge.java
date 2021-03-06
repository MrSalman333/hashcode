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
        String[] files = {"a_example.txt", "b_lovely_landscapes.txt", "c_memorable_moments.txt", "d_pet_pictures.txt", "e_shiny_selfies.txt"};
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            System.out.println("starrted reading");
            ArrayList<Photo> photos = read(file);
            System.out.println("done reading start making slides");
            ArrayList<Slide> slides = makeSlideList(photos);
            System.out.println("done slides and started ordaring");
            ArrayList<Slide> ordered = makeOrdered(slides);
            System.out.println("done ordring now writing");
            write("output" + file, ordered);
            Photo.clearIds();
        }
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

    private static ArrayList<Slide> makeSlideList(ArrayList<Photo> photos) {
        ArrayList<Slide> slides = new ArrayList<Slide>();

        for (Photo p : photos) {
            if (p.getID() % 1000 == 0) {
                System.out.println("doing photo number " + p.getID());
            }
            if (p.isChosen()) {
                continue;
            }

            if (p.getCharacter() == 'H') {
                slides.add(new Slide(p));
            } else {
                int index = bestmMtch(photos, p.getID());
                if (index != -1) {
                    Photo p2 = photos.get(index);
                    Slide s = new Slide(p2, p);
                    slides.add(s);
                } else {
                    System.out.println("problim");
                }
            }
        }
        return slides;
    }

    private static ArrayList<Slide> makeOrdered(ArrayList<Slide> slides) {
        ArrayList<Slide> ordered = new ArrayList<Slide>(slides.size());

        Slide first = slides.get(0);
        int minNumOfTags = 99999;
        for (int i = 0; i < slides.size(); i++) {
            if(slides.get(i).getTagOfSilde().length < minNumOfTags){
                minNumOfTags = slides.get(i).getTagOfSilde().length ;
                first = slides.get(i);
            }
                
        }
        ordered.add(first);
        first.setAdded(true);

        Slide last = first;
        System.out.println("size should be " + slides.size());
        while (slides.size() != ordered.size()) {
            if (ordered.size() % 100 == 0) {
                System.out.println("done ordring " + ordered.size());
            }
            int bestCaseScoure = -1;
            int bestCaseDif = 9999;
            int bestCaseMidDif = 9999;
            Slide bestCase = null;

            for (int i = 0; i < slides.size(); i += 1) {
                Slide s2 = slides.get(i);
                if (s2.isAdded()) {
                    continue;
                }
                int scoures[] = scores(last, s2);
                int thisCaseDif =max(scoures[0],scoures[1],scoures[2]) - min(scoures[0],scoures[1],scoures[2]) ;
                int thisCaseMidDif = max(scoures[0],scoures[1],scoures[2]) - mid(scoures[0],scoures[1],scoures[2]) ;
                if (scoures[0] > bestCaseScoure || (scoures[0] == bestCaseScoure && (thisCaseDif < bestCaseDif || (thisCaseDif == bestCaseDif && thisCaseMidDif > bestCaseMidDif))  )  ) {
                    bestCaseScoure = scoures[0];
                    bestCase = s2;
                    bestCaseDif =thisCaseDif ;
                    bestCaseMidDif = thisCaseMidDif;
                }
            }

            if (bestCase == null) {
                System.out.println("null best case");
                for (Slide s : slides) {
                    if (!s.isAdded()) {
                        ordered.add(s);
                        s.setAdded(true);
                    }
                }
            } else {
                ordered.add(bestCase);
                bestCase.setAdded(true);
                last = bestCase;
            }

        }

        System.out.println("last size of ordered = " + ordered.size());

        return ordered;
    }
    
    public static int max(int n1,int n2,int n3){
        return Math.max(n1, Math.max(n2, n3));
    }
    
    public static int min(int n1,int n2,int n3){
        return Math.min(n1, Math.min(n2, n3));
    }
    
        public static int mid(int n1,int n2,int n3){
            if(n1 == max(n1,n2,n3)){
                if(Math.min(n2, n3) == n2){
                    return n3;
                }else{
                    return n2;
                }   
            }else if(n2 == max(n1, n2, n3)){
                if(Math.min(n1, n3) == n1){
                    return n3;
                }else{
                    return n1;
                }   
            }else{
                return n3;
            }
        }
    public static void write(String fileName, ArrayList<Slide> list) {

        int numberOfSlides = list.size();
        System.out.println("size is = " + list.size());
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

    public static int[] scores(Slide s1, Slide s2) {
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
            s1Extra = s1Tags.length - shared;
            s2Extra = s2Tags.length - shared;
        }
        int returned[] = {shared,s1Extra,s2Extra};
        return returned;
    }

    public static int bestmMtch(ArrayList<Photo> photos, int id) {
        String[] tags1 = photos.get(id).getTags();

        int[] scores = new int[photos.size()];
        int count;
        int checkCounter = 0;
        for (int i = id; i < photos.size(); i++) {
            scores[i] = 9999999;

            count = 0;
            if (photos.get(i).isChosen() || photos.get(i).getCharacter() == 'H' || i == id) {
                continue;
            }

            for (int j = 0; j < tags1.length; j++) {
                Photo p2 = photos.get(i);
                for (int k = 0; k < p2.getTags().length; k++) {
                    if (tags1[j].equals(p2.getTags()[k])) {
                        count++;
                        break;
                    }
                }
            }
            scores[i] = count;
        }
        int min = scores[id];
        int minIndex = id;
        for (int i = id; i < scores.length; i++) {
            if (scores[i] < min ||(scores[i] == min && photos.get(minIndex).getTags().length < photos.get(i).getTags().length  ) ) {
                min = scores[i];
                minIndex = i;
            }
        }

        return minIndex;

    }

}
