package edu.uga.cs6060.geographyquiz;

/**
 * @author Srijal
 * This POJO class facilitates retrieving quiz results from the database
 * to populate into the recyclerview
 */
public class Quiz {

    private Integer _id;
    private String date;
    private Integer result;

    public Quiz() {
    }

    public Quiz(int _id, String date, Integer result) {
        this._id = _id;
        this.date = date;
        this.result = result;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "_id=" + _id +
                ", date='" + date + '\'' +
                ", result=" + result +
                '}';
    }
}
