package com.rodrigoftw.myworkouttracker.myworkouttracker.model;

public class Exercise {
    private int idExercise;
    private String nameExercise;
    private String typeExercise;
    private int setsExercise;
    private int repsExercise;
    private String restExercise;
    private String imageExercise;
   /* private int colorId1;
    private int colorId2;
    private TimeInterpolator interpolator;*/

    public Exercise(){}

    public Exercise(int idExercise, String nameExercise, int setsExercise, int repsExercise, String restExercise, String imageExercise/*, int colorId1, int colorId2, TimeInterpolator interpolator*/) {
        this.idExercise = idExercise;
        this.nameExercise = nameExercise;
        this.setsExercise = setsExercise;
        this.repsExercise = repsExercise;
        this.restExercise = restExercise;
        this.imageExercise = imageExercise;
        /*this.colorId1 = colorId1;
        this.colorId2 = colorId2;
        this.interpolator = interpolator;*/
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public String getNameExercise() {
        return nameExercise;
    }

    public void setNameExercise(String nameExercise) {
        this.nameExercise = nameExercise;
    }

    public String getTypeExercise() {
        return typeExercise;
    }

    public void setTypeExercise(String typeExercise) {
        this.typeExercise = typeExercise;
    }

    public int getSetsExercise() {
        return setsExercise;
    }

    public void setSetsExercise(int setsExercise) {
        this.setsExercise = setsExercise;
    }

    public int getRepsExercise() {
        return repsExercise;
    }

    public void setRepsExercise(int repsExercise) {
        this.repsExercise = repsExercise;
    }

    public String getRestExercise() {
        return restExercise;
    }

    public void setRestExercise(String restExercise) {
        this.restExercise = "Descanso: " + restExercise + " segundos";
    }

    public String getImageExercise() {
        return imageExercise;
    }

    public void setImageExercise(String imageExercise) {
        this.imageExercise = imageExercise;
    }

    /*public int getColorId1() {
        return colorId1;
    }

    public void setColorId1(int colorId1) {
        this.colorId1 = colorId1;
    }

    public int getColorId2() {
        return colorId2;
    }

    public void setColorId2(int colorId2) {
        this.colorId2 = colorId2;
    }

    public TimeInterpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }*/
}