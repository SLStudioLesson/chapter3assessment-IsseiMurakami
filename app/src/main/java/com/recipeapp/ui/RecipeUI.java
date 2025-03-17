package com.recipeapp.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class RecipeUI {
    private BufferedReader reader;
    private DataHandler dataHandler;

    // コンストラクタ
    public RecipeUI(DataHandler dataHandler) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.dataHandler = dataHandler;
    }

    // メインメニュー表示と入力処理
    public void displayMenu(BufferedReader reader) {

        while (true) {

            try {

                System.out.println("Current mode: " + dataHandler.getMode());

                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");
                // ユーザー入力を取得
                String choice = reader.readLine();
                // 入力に応じた処理を実行
                switch (choice) {
                    case "1":
                        displayRecipes();
                        break;
                    case "2":
                        addNewRecipe(reader);
                        break;
                    case "3":
                        break;
                    case "4":
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }

                System.out.println();

            } catch (IOException e) {
                System.err.println("Input error: " + e.getMessage());
            }
        }
    }

    // レシピ一覧表示
    private void displayRecipes() {
        try {
            ArrayList<Recipe> recipes = dataHandler.readData();

            if (recipes == null || recipes.isEmpty()) {
                System.out.println("No recipes available.");
                return;
            }

            System.out.println("\nRecipes");
            for (Recipe recipe : recipes) {
                System.out.println("---------------------------------");
                System.out.println("Recipe Name: " + recipe.getName());

                ArrayList<Ingredient> ingredients = recipe.getIngredients();
                StringBuilder ingredientNames = new StringBuilder();

                for (int i = 0; i < ingredients.size(); i++) {
                    ingredientNames.append(ingredients.get(i).getName());
                    if (i != ingredients.size() - 1) {
                        ingredientNames.append(", ");
                    }
                }

                System.out.println("Main Ingredients: " + ingredientNames.toString());
            }

        } catch (IOException e) {
            System.out.println("Error reading input from user: " + e.getMessage());
        }
    }

    // 新規登録機能
    private void addNewRecipe(BufferedReader reader) {
        try {
            System.out.println("Adding a new recipe.");
            System.out.print("Enter recipe name: ");
            String recipeName = reader.readLine();

            ArrayList<Ingredient> ingredients = new ArrayList<>();

            System.out.println("Enter ingredients (type 'done' when finished):");

            while (true) {
                System.out.print("Ingredient: ");
                String ingredientName = reader.readLine();

                if ("done".equalsIgnoreCase(ingredientName)) {
                    break;
                }

                if (ingredientName != null && !ingredientName.trim().isEmpty()) {
                    ingredients.add(new Ingredient(ingredientName.trim()));
                } else {
                    System.out.println("");
                }
            }
            // 入力されたレシピ名と材料でRecipeを作成
            Recipe newRecipe = new Recipe(recipeName, ingredients);
            // DataHandlerを使ってrecipes.csvに追加
            dataHandler.writeData(newRecipe);

            System.out.println("Recipe added successfully.");
        } catch (IOException e) {
            System.err.println("Failed to add new recipe: " + e.getMessage());
        }
    }

}