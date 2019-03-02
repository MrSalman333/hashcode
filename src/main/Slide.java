/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Comparator;

/**
 *
 * @author MrS
 */
public class Slide implements Comparable<Slide> {


    @Override
    public int compareTo(Slide o) {
        return (o.getTagOfSilde().length - this.getTagOfSilde().length);
    }

    private int numberOfPhoto;
    private String[] tagOfSilde;
    private int id1;
    private int id2;
    boolean added;
    Photo photo1, photo2;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public Slide(Photo p) {
        tagOfSilde = p.getTags();
        numberOfPhoto = 1;
        id1 = p.getID();
        p.setChosen(true);
        added = false;
        photo1 = p;
    }

    public Slide(Photo p1, Photo p2) {
        numberOfPhoto = 2;
        id1 = p1.getID();
        id2 = p2.getID();
        tagOfSilde = combainTags(p1, p2);
        p1.setChosen(true);
        p2.setChosen(true);
        added = false;
        photo1 = p1;
        photo2 = p2;

    }

    public int getNumberOfPhoto() {
        return numberOfPhoto;
    }

    public void setNumberOfPhoto(int numberOfPhoto) {
        this.numberOfPhoto = numberOfPhoto;
    }

    public String[] getTagOfSilde() {
        return tagOfSilde;
    }

    public void setTagOfSilde(String[] tagOfSilde) {
        this.tagOfSilde = tagOfSilde;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String[] combainTags(Photo p1, Photo p2) {
        String[] p1t = p1.getTags();
        String[] p2t = p1.getTags();
        String[] result = new String[p1t.length + p2t.length];

        int x = p1t.length;
        for (int i = 0; i < p1t.length; i++) {
            result[i] = p1t[i];
        }
        boolean check;
        for (int i = 0; i < p2t.length; i++) {
            check = false;
            for (int j = 0; j < p1t.length; j++) {
                if (p1t[i].equalsIgnoreCase(p2t[j])) {
                    check = true;
                }
            }
            if (!check) {
                result[x] = p2t[i];
                x++;
            }
        }
        String[] newResult = new String[x];
        for (int i = 0; i < x; i++) {
            newResult[i] = result[i];
        }
        return newResult;
    }

    @Override
    public String toString() {
        if (numberOfPhoto == 2) {
            return "slide=" + photo1.toString() + photo2.toString();
        } else {
            return "slide=" + photo1.toString();
        }

    }
}
