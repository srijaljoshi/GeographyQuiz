package edu.uga.cs6060.geographyquiz;

// POJO to hold Question data
public class Question {

    private String country;
    private String continent_answer;
    private String neighbor_answer;

    public Question(String country, String continent_answer, String neighbor_answer) {
        this.country = country;
        this.continent_answer = continent_answer;
        this.neighbor_answer = neighbor_answer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent_answer() {
        return continent_answer;
    }

    public void setContinent_answer(String continent_answer) {
        this.continent_answer = continent_answer;
    }

    public String getNeighbor_answer() {
        return neighbor_answer;
    }

    public void setNeighbor_answer(String neighbor_answer) {
        this.neighbor_answer = neighbor_answer;
    }
}
