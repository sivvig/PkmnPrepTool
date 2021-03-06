package com.pdl.prepTool;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.*;

import java.util.List;

    class TypeCalculator {

    private Type type1;
    private Type type2;

    private int numTypes;

    private int typeId1;
    private int typeId2;

    private List<PokemonType> pokemonTypeList;

    private TypeRelations typeRelations1;
    private List<NamedApiResource> doubleDamageFrom1;
    private List<NamedApiResource> doubleDamageTo1;
    private List<NamedApiResource> halfDamageFrom1;
    private List<NamedApiResource> halfDamageTo1;
    private List<NamedApiResource> immuneFrom1;
    private List<NamedApiResource> immuneTo1;

    private TypeRelations typeRelations2;
    private List<NamedApiResource> doubleDamageFrom2;
    private List<NamedApiResource> doubleDamageTo2;
    private List<NamedApiResource> halfDamageFrom2;
    private List<NamedApiResource> halfDamageTo2;
    private List<NamedApiResource> immuneFrom2;
    private List<NamedApiResource> immuneTo2;


    private PokeApi pokeApi = new PokeApiClient();

    TypeCalculator(){}

     void enterPkmn(Pokemon pokemon){
        pokemonTypeList = pokemon.getTypes();

        numTypes = pokemonTypeList.size();
        if(numTypes > 1){
            typeId1 = pokemonTypeList.get(0).getType().component3();
            typeId2 = pokemonTypeList.get(1).getType().component3();

            type1 = pokeApi.getType(typeId1);
            type2 = pokeApi.getType(typeId2);

            typeRelations1 = type1.getDamageRelations();
            doubleDamageFrom1 = typeRelations1.getDoubleDamageFrom();
            doubleDamageTo1 = typeRelations1.getDoubleDamageTo();
            halfDamageFrom1 = typeRelations1.getHalfDamageFrom();
            halfDamageTo1 = typeRelations1.getHalfDamageTo();
            immuneFrom1 = typeRelations1.getNoDamageFrom();
            immuneTo1 = typeRelations1.getNoDamageTo();

            typeRelations2 = type2.getDamageRelations();
            doubleDamageFrom2 = typeRelations2.getDoubleDamageFrom();
            doubleDamageTo2 = typeRelations2.getDoubleDamageTo();
            halfDamageFrom2 = typeRelations2.getHalfDamageFrom();
            halfDamageTo2 = typeRelations2.getHalfDamageTo();
            immuneFrom2 = typeRelations2.getNoDamageFrom();
            immuneTo2 = typeRelations2.getNoDamageTo();
        } else {
            typeId1 = pokemonTypeList.get(0).getType().component3();
            typeId2 = 0;

            type1 = pokeApi.getType(typeId1);

            typeRelations1 = type1.getDamageRelations();
            doubleDamageFrom1 = typeRelations1.getDoubleDamageFrom();
            doubleDamageTo1 = typeRelations1.getDoubleDamageTo();
            halfDamageFrom1 = typeRelations1.getHalfDamageFrom();
            halfDamageTo1 = typeRelations1.getHalfDamageTo();
            immuneFrom1 = typeRelations1.getNoDamageFrom();
            immuneTo1 = typeRelations1.getNoDamageTo();
        }
    }

    String getPkmn2xWeak(){
        // return this.doubleDamageFrom1;

        StringBuilder weak2xString = new StringBuilder();

        //Needs differing logic when dealing with dual-types
        for (int i=0; i<doubleDamageFrom1.size(); i++) {
            String weakness = doubleDamageFrom1.get(i).getName().substring(0, 1).toUpperCase() + doubleDamageFrom1.get(i).getName().substring(1);
            if(i==doubleDamageFrom1.size()-1) {
                weak2xString.append(weakness);
            } else {
                weak2xString.append(weakness).append(", ");
            }
        }
        return weak2xString.toString();
    }

    String getPkmn4xWeak(){

        if(numTypes>1) {
            return "WIP";
        } else {
            return "N/A";
        }
    }

    String getPkmn2xResist() {

        StringBuilder resist2xString = new StringBuilder();

        for (int i=0; i<halfDamageFrom1.size(); i++) {
            String weakness = halfDamageFrom1.get(i).getName().substring(0, 1).toUpperCase() + halfDamageFrom1.get(i).getName().substring(1);
            if(i==halfDamageFrom1.size()-1) {
                resist2xString.append(weakness);
            } else {
                resist2xString.append(weakness).append(", ");
            }
        }
        return resist2xString.toString();
    }

    String getPkmn4xResist(){
        if (numTypes > 1) {
            return  "WIP";
        } else {
            return "N/A";
        }
    }

    String getPkmnImmunity(){
        return  "WIP";
    }
}
