package com.pdl.prepTool;

import com.jakewharton.fliptables.FlipTable;
import kotlin.text.Charsets;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonStat;
import me.sargunvohra.lib.pokekotlin.model.PokemonType;
//import org.apache.commons.io.FileUtils;
//import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tool {
    public static void main(String[] args) throws IOException {
        String FILE_PATH;


        //Production
        //String DIRECTORY = System.getenv("APP_HOME");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Pokemon List file:");

        //For testing
        FILE_PATH = args[0];

        //Production
        //FILE_PATH = sc.nextLine();


        PokeApi pokeApi = new PokeApiClient();
        TypeCalculator typeCalculator = new TypeCalculator();

        //For testing
        List<String> pkmnList = Files.readAllLines(new File(FILE_PATH).toPath(), Charsets.UTF_8);

        //Production
        //List<String> pkmnList = Files.readAllLines(new File(DIRECTORY, FILE_PATH).toPath(), Charsets.UTF_8);

        ArrayList<Integer> pkmnIndex = new ArrayList<>();
        ArrayList<Pokemon> party = new ArrayList<>();
        String[] headers = {"Name", "Type", "HP", "Attack", "Defense", "Sp.Attack", "Sp.Defense", "Speed", "2x Weak", "4x Weak", "2x Resist", "4x Resist", "Immune"};
        String[][] data = new String[pkmnList.size()][8];

        for(int i=0; i<pkmnList.size(); i++){
            int index = Integer.parseInt(pkmnList.get(i));
            pkmnIndex.add(index);
            Pokemon activePkmn = pokeApi.getPokemon(index);
            party.add(activePkmn);
        }

        for(int i = 0; i<party.size(); i++) {

            Pokemon activePkmn = party.get(i);
            typeCalculator.enterPkmn(activePkmn);

            List<PokemonType> activeTypes = activePkmn.getTypes();
            List<PokemonStat> activePkStats = activePkmn.getStats();

            String name = activePkmn.getName().substring(0, 1).toUpperCase() + activePkmn.getName().substring(1);

            String type1;
            String type2;
            String type;

            String weak2x;
            String weak4x;

            String resist2x;
            String resist4x;

            String immune;

            //Example:
            //int hp = activePkStats.get(5).component3();

            if(activeTypes.size()>1) {
                type1 = activeTypes.get(1).component2().component1().substring(0, 1).toUpperCase() + activeTypes.get(1).component2().component1().substring(1);
                type2 = activeTypes.get(0).component2().component1().substring(0,1).toUpperCase() + activeTypes.get(0).component2().component1().substring(1);

                type = type1 + "/" + type2;

                weak2x = "WIP";
                weak4x = "WIP";

                resist2x = "WIP";
                resist4x = "WIP";

                immune = "WIP";
            } else {
                type1 = activeTypes.get(0).component2().component1().substring(0,1).toUpperCase() + activeTypes.get(0).component2().component1().substring(1);
                type2 = "";

                type = type1 + type2;

                weak2x = typeCalculator.getPkmn2xWeak();
                weak4x = typeCalculator.getPkmn4xWeak();

                resist2x = typeCalculator.getPkmn2xResist();
                resist4x = typeCalculator.getPkmn4xResist();

                immune = typeCalculator.getPkmnImmunity();
            }

            String hp = String.valueOf(activePkStats.get(5).component3());
            String attack = String.valueOf(activePkStats.get(4).component3());
            String defense = String.valueOf(activePkStats.get(3).component3());
            String spAtk = String.valueOf(activePkStats.get(2).component3());
            String spDef = String.valueOf(activePkStats.get(1).component3());
            String speed = String.valueOf(activePkStats.get(0).component3());

            String[] rowData = {name, type, hp, attack, defense, spAtk, spDef, speed, weak2x, weak4x, resist2x, resist4x, immune};
            data[i] = rowData;
        }

        System.out.println(FlipTable.of(headers,data));

        System.out.println("\nPress ENTER to close.\n");
        sc.nextLine();
    }
}
