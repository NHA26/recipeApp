package com.example.lenovo.pecs;

public class Recipe {
    private String id;
    private String name;
    private byte[] image;
    private String ingredient;
    private String instruction;


    public Recipe(String id, String name, byte[] image) {

        this.id = id;
        this.name = name;
        this.image = image;
//        this.ingredient = ingredient;
//        this.instruction = instruction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

//    public String getIngredient() {
//        return ingredient;
//    }
//
//    public void setIngredient(String ingredient) {
//        this.ingredient = ingredient;
//    }
//
//    public String getInstruction() {
//        return instruction;
//    }
//
//    public void setInstruction(String instruction) {
//        this.instruction = instruction;
//    }

}
