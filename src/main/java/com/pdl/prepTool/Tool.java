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
        String DIRECTORY = System.getenv("APP_HOME");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Pokemon List file:");
        FILE_PATH = sc.nextLine();

        //String FILE_PATH = args[0];

        PokeApi pokeApi = new PokeApiClient();

        List<String> pkmnList = Files.readAllLines(new File(DIRECTORY, FILE_PATH).toPath(), Charsets.UTF_8);
        ArrayList<Integer> pkmnIndex = new ArrayList<>();
        ArrayList<Pokemon> party = new ArrayList<>();
        String[] headers = {"Name", "HP", "Attack", "Defense", "Sp.Attack", "Sp.Defense", "Speed"};
        String[][] data = new String[pkmnList.size()][7];

        for(int i=0; i<pkmnList.size(); i++){
            int index = Integer.parseInt(pkmnList.get(i));
            pkmnIndex.add(index);
            Pokemon activePkmn = pokeApi.getPokemon(index);
            party.add(activePkmn);
        }

        for(int i = 0; i<party.size(); i++) {

            Pokemon activePkmn = party.get(i);

            //List<PokemonType> activeTypes = activePkmn.getTypes();
            List<PokemonStat> activePkStats = activePkmn.getStats();

            String name = activePkmn.getName().substring(0, 1).toUpperCase() + activePkmn.getName().substring(1);

            /*int hp = activePkStats.get(5).component3();
            int attack = activePkStats.get(4).component3();
            int defense = activePkStats.get(3).component3();
            int spAtk = activePkStats.get(2).component3();
            int spDef = activePkStats.get(1).component3();
            int speed = activePkStats.get(0).component3();*/

            /*System.out.println("Name: " + name);
            System.out.println("HP: " + hp);
            System.out.println("Attack: " + attack);
            System.out.println("Defense: " + defense);
            System.out.println("Sp. Attack: " + spAtk);
            System.out.println("Sp. Defense: " + spDef);
            System.out.println("Speed: " + speed);
            System.out.println();*/

            //System.out.println(activeTypes);

            String hp = String.valueOf(activePkStats.get(5).component3());
            String attack = String.valueOf(activePkStats.get(4).component3());
            String defense = String.valueOf(activePkStats.get(3).component3());
            String spAtk = String.valueOf(activePkStats.get(2).component3());
            String spDef = String.valueOf(activePkStats.get(1).component3());
            String speed = String.valueOf(activePkStats.get(0).component3());

            String[] rowData = {name, hp, attack, defense, spAtk, spDef, speed};
            data[i] = rowData;
        }

        System.out.println(FlipTable.of(headers,data));
        System.out.println("\nPress ENTER to close.\n");
        sc.nextLine();
    }
}
