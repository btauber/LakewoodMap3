package com.example.benjamintauber.lakewoodmap2;

/**
 * Created by benjamintauber on 9/4/14.
 */
public class Person {
    private String last;
    private String first;
    private String spouse;
    private String address;
    private String phone;


    public Person(String last, String first, String spouse, String address, String phone){
        this.last = last;
        this.first = first;
        this.spouse = spouse;
        this.address = address;
        this.phone = phone;
    }

    protected void setLast(String last) {
        this.last = last;
    }
    protected void setFirst(String first) {
        this.first = first;
    }
    protected void setSpouse(String spouse) {
        this.spouse = spouse;
    }
    protected void setAddress(String address) {
        this.address = address;
    }
    protected void setPhone(String phone) {
        this.phone = phone;
    }
    protected String getLast() {
        return last;
    }
    protected String getFirst() {
        return first;
    }
    protected String getSpouse() {
        return spouse;
    }
    protected String getAddress() {
        return address;
    }
    protected String getPhone() {
        return phone;
    }
    protected String getFirstLine(){
        String line;
        if(getFirst().equals("") == false && getSpouse().equals("") == false){
            return line = "Rabbi & Mrs. " + getFirst() + " & " + getSpouse() + " " + getLast();

        }else if(getFirst().equals("") == false && getSpouse().equals("") == true){
            return line = "Rabbi " + getFirst() + " " + getLast();
        }
        else if(getFirst().equals("") == true && getSpouse().equals("") == false){
            return line = "Mrs. " + getSpouse() + " " + getLast();
        }
        return "";
    }
}
