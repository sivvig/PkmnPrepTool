package com.pdl.prepTool;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonType;
import me.sargunvohra.lib.pokekotlin.model.Type;
import me.sargunvohra.lib.pokekotlin.model.TypeRelations;

import java.util.List;

public class TypeCalculator {

    private Type type1;
    private Type type2;

    private int numTypes;

    private int typeId1;
    private int typeId2;

    private TypeRelations typeRelations1;
    private TypeRelations typeRelations2;

    private List<PokemonType> pokemonTypeList;

    private PokeApi pokeApi = new PokeApiClient();

    public TypeCalculator(Pokemon pokemon){
        pokemonTypeList = pokemon.getTypes();

        numTypes = pokemonTypeList.size();
        if(numTypes > 1){
            typeId1 = pokemonTypeList.get(0).getType().component3();
            typeId2 = pokemonTypeList.get(1).getType().component3();

            type1 = pokeApi.getType(typeId1);
            type2 = pokeApi.getType(typeId2);

            typeRelations1 = type1.getDamageRelations();
            typeRelations2 = type2.getDamageRelations();
        } else {
            typeId1 = pokemonTypeList.get(0).getType().component3();
            typeId2 = 0;

            type1 = pokeApi.getType(typeId1);

            typeRelations1 = type1.getDamageRelations();
        }
    }


}
