using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum TerrainType
{
    None, Arid, Forest, Unproductive, Plains, Rocky,
    LakeWater, OceanWater, IcyWater
}

public class Terrain {

    public TerrainType name;
    public int terrainTypeIndex;
    public int elevation = int.MinValue;
    public bool canIncludeRivers;
    public bool hasIncomingRiver, hasOutgoingRiver;
    public HexDirection incomingRiver, outgoingRiver;
    public int waterLevel;
    //roads
    [SerializeField]
    public bool[] roads;


    public Terrain()
    { }

    public Terrain(int terrainIndex, bool canIncludeRivers, int waterLevel, TerrainType name, int elevation)
    {
        this.terrainTypeIndex = terrainIndex;
        this.canIncludeRivers = canIncludeRivers;
        this.waterLevel = waterLevel;
        this.name = name;
        this.elevation = elevation;
        this.roads = new bool[6];
    }

    public Terrain(Terrain terrain)
    {
        //elevation needs to refresh therefore cannot be done here
        this.terrainTypeIndex = terrain.terrainTypeIndex;
        this.canIncludeRivers = terrain.canIncludeRivers;
        this.waterLevel = terrain.waterLevel;
        this.name = terrain.name;
        this.roads = new bool[6];
    }

    public Terrain(int textureIndex, bool riverAccess, TerrainType terrainType)
    {
        this.terrainTypeIndex = textureIndex;
        this.canIncludeRivers = riverAccess;
        this.name = terrainType;
    }

    internal HexDirection RiverBeginOrEndDirection()
    {
        return hasIncomingRiver ? incomingRiver : outgoingRiver;
    }

    public bool HasRiverThroughEdge(HexDirection direction)
    {
        return
            hasIncomingRiver && incomingRiver == direction ||
            hasOutgoingRiver && outgoingRiver == direction;
    }
}
