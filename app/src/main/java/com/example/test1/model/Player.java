package com.example.test1.model;

public class Player {

    private int id;
    private String fname;
    private String lname;
    private double height_feet;
    private double height_inches;
    private String position;
    private String team;
    private double weight_pounds;

    public Player(){
    }

    public Player(int id, String fname, String lname, double height_feet, double height_inches, String position, String team, double weight_pounds) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.height_feet = height_feet;
        this.height_inches = height_inches;
        this.position = position;
        this.team = team;
        this.weight_pounds = weight_pounds;
    }

    public Player(String fname, String lname, double height_feet, double height_inches, String position, String team, double weight_pounds) {
        this.fname = fname;
        this.lname = lname;
        this.height_feet = height_feet;
        this.height_inches = height_inches;
        this.position = position;
        this.team = team;
        this.weight_pounds = weight_pounds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public double getHeight_feet() {
        return height_feet;
    }

    public void setHeight_feet(double height_feet) {
        this.height_feet = height_feet;
    }

    public double getHeight_inches() {
        return height_inches;
    }

    public void setHeight_inches(double height_inches) {
        this.height_inches = height_inches;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public double getWeight_pounds() {
        return weight_pounds;
    }

    public void setWeight_pounds(double weight_pounds) {
        this.weight_pounds = weight_pounds;
    }

}