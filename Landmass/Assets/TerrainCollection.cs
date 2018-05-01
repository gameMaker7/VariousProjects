
using System;
using System.Collections.Generic;
using UnityEngine;

//elevation 4
//snow moisture (6-4) 5
//tundra moisture (3) 4
//bare moisture (2) 3
//scorched moisture (1) 2

//elevation 3
//taiga  moisture (6-5) 6
//shrubland moisture (4-3) 7
//crystal desert moisture (2-1) 0

//elevation (2)
// crystal forest moisture (6) 8
//enchanted forest moisture (5-4) 9
//Grasslands moisture (3-2) 10
//crystal desert (1) 0

//elevation (1)
//rain forest moisture (6-5) 12
// forest moisture (4-3) 11
//grassland moisture (2) 10
//desert moisture (1) 1
public class TerrainCollection : MonoBehaviour
{


    List<Terrain> terrains;
    public List<Terrain> Setup()
    {
        terrains = new List<Terrain>();
        return terrains;
    }

    public Terrain GetTerrain(int ID)
    {
        return terrains[ID];
    }
    internal Terrain SelectTerrain(HexCell hexCell, TerrainType biome)
    {

        List<TerrainType> terrains = new List<TerrainType>();
        List<float> chances = new List<float>();
        terrains.Add(biome);
        chances.Add(0.04f);
        float total = 0;
        total += 0.04f;
        for (HexDirection d = HexDirection.NE; d < HexDirection.NW; ++d)
        {
            if (hexCell.GetNeighbor(d) == null || hexCell.GetNeighbor(d).IsUnderwater) continue;
            TerrainType tmp = hexCell.GetNeighbor(d).GetTerrainType();
            if (!terrains.Contains(tmp) && tmp != TerrainType.None)
            {
                terrains.Add(tmp);
                chances.Add(0.36f);
                total+= 0.36f;
            }
            else
            {
                if (tmp != TerrainType.None)
                {
                    chances[terrains.IndexOf(tmp)] += 0.36f;
                    total += 0.36f;
                }
            }
        }
        float chance = UnityEngine.Random.Range(0.0f, total);
        total = 0;
        int a = 0;
        foreach (float f in chances)
        {
            if (chance <= total + f && chance >= total)
            {
                return new Terrain((int)terrains[a], true, 0, terrains[a], hexCell.Elevation);
            }
            else
            {
                total += f;
                ++a;
            }
        }
        return new Terrain((int)terrains[0], true, 0, terrains[0], hexCell.Elevation);
    }
}

    