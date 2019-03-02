/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author MrS
 */
public class Photo implements Comparable<Photo> {

    @Override
    public int compareTo(Photo o) {
        return -(this.getTags().length - o.getTags().length);
    }
    private char character;
    private boolean chosen;
    private String[] tags;
    private int ID;
    private static int count = 0;

    public Photo() {

        this.character = ' ';
        this.chosen = false;
        this.tags = null;
        this.ID = count;
        count++;
    }

    public static void clearIds() {
        count = 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Photo.count = count;
    }

    public Photo(char character, String[] tags) {
        this.character = character;
        this.tags = tags;
        this.chosen = false;
        this.ID = count;
        count++;

    }

    public Photo(char character, boolean chosen, String[] tags) {
        this.character = character;
        this.chosen = chosen;
        this.tags = tags;
        this.ID = count;
        count++;

    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        String tags = "{";
        for (int i = 0; i < this.tags.length; i++) {
            tags += this.tags[i] + ", ";
        }
        tags += "}";
        return "Photo{" + "character=" + character + ", chosen=" + chosen + ", tags=" + tags + ", ID=" + ID + '}';
    }

}
