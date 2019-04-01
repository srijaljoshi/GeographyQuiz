package edu.uga.cs6060.geographyquiz;

// POJO to hold Question data
public class Question {
    String country;
    String correct_answer;

    public Question(String country, String correct_answer) {
        this.country = country;
        this.correct_answer = correct_answer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }
}
