package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class CSVDataHandler implements DataHandler {

    private String filePath;

    public CSVDataHandler() {
        this.filePath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getMode() {
        return "CSV";
    }

    @Override
    public ArrayList<Recipe> readData() throws IOException {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 2) {
                    continue;
                }

                String recipeName = parts[0].trim();
                ArrayList<Ingredient> ingredients = new ArrayList<>();

                for (int i = 1; i < parts.length; i++) {
                    ingredients.add(new Ingredient(parts[i].trim()));
                }

                recipes.add(new Recipe(recipeName, ingredients));
            }
        }
        return recipes;
    }

    @Override
    public void writeData(Recipe recipe) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            StringBuilder line = new StringBuilder();
            line.append(recipe.getName());

            for (Ingredient ingredient : recipe.getIngredients()) {
                line.append(",").append(ingredient.getName());
            }

            writer.newLine();
            writer.write(line.toString());
        }
    }

    @Override
    public ArrayList<Recipe> searchData(String keyword) throws IOException {
        return null;
    }
}
