
using UnityEngine.UI;
using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;

public class HexGrid : MonoBehaviour {

    public int chunkCountX = 4, chunkCountZ = 3;
    public HexCell tile;
    public Text cellLabel;
    public Texture2D noiseSource;
    public HexGridChunk chunkPrefab;
    public int seed = 0;
    private HexCell[] grid;
    int cellCountX, cellCountZ;
    HexGridChunk[] chunks;
    public TerrainCollection terrains;
    CoherentNoise.Generation.ValueNoise2D heightNoise;
    CoherentNoise.Generation.ValueNoise2D temperatureNoise;
    
    public void CreateBiomes()
    {
        for(int i = 0; i < chunks.Length; ++i)
        {
            TerrainType biome = (TerrainType)UnityEngine.Random.Range(0, 5);
            chunks[i].GenBiome(biome, terrains);
        }
        while(riverCount > 0)
        {
            int from = UnityEngine.Random.Range(0, grid.Length);
            int to = UnityEngine.Random.Range(0, from);
            if (grid[from].Elevation >= grid[to].Elevation)
            {
                BuildRivers(grid[from], grid[to]);
                --riverCount;
            }
        }
        if (ActiveCities)
        { BuildCities(); }
    }
    public void SetRoadActive(bool val)
    {
        ActiveRoads = val;
    }
    public void SetCitiesActive(bool val)
    {
        ActiveCities = val;
    }
    private void BuildCities()
    {
        int tmp = 0;

        List<HexCell> cities = new List<HexCell>();
        while (tmp != cityCount)
        {
            int j = UnityEngine.Random.Range(0, grid.Length);
            if (!grid[j].IsUnderwater && grid[j].GetNumNeighbors() >= 4 && grid[j].GetTerrainType() != TerrainType.Unproductive)
            {
                if (GetDistance(cities, grid[j]) >= minDistance)
                {
                    grid[j].UrbanLevel = 1;
                    cities.Add(grid[j]);
                    ++tmp;
                }
            }
        }
        if (ActiveRoads)
        {
            BuildRoads(cities);
        }
    }

    private void BuildRoads(List<HexCell> cities)
    {
        for(int i = 1; i < cities.Count; ++i)
        {
            connectCities(cities[0], cities[i]);
        }
    }

    private void connectCities(HexCell fromCell, HexCell toCell)
    {
        for (int i = 0; i < grid.Length; i++)
        {
            grid[i].Distance = int.MaxValue;
        }

        for (int i = 0; i < grid.Length; i++)
        {
            grid[i].Distance = int.MaxValue;
            //grid[i].DisableHighlight();
        }

        List<HexCell> openSet = new List<HexCell>();
        List<HexCell> closedSet = new List<HexCell>();
        fromCell.Distance = 0;
        fromCell.EnableHighlight(Color.blue);
        toCell.EnableHighlight(Color.red);
        openSet.Add(fromCell);
        do
        {
            openSet.Sort((x, y) => x.Distance.CompareTo(y.Distance));
            HexCell current = openSet[0];
            openSet.RemoveAt(0);
            closedSet.Add(current);
            if (closedSet.Contains(toCell))
            {
                current = toCell.pathFrom;
                while (current != fromCell)
                    {
                        current.EnableHighlight(Color.white);
                        //current.AddRoad(current.GetNeighbor(current.pathFrom));
                        current = current.pathFrom;
                    }
                    break;
            }
            for (HexDirection d = HexDirection.NE; d < HexDirection.NW; ++d)
            {
                HexCell neighbor = current.GetNeighbor(d);
                if (current.neighborDistanceValues[(int)d] == -1)
                {
                    continue;
                }
                if (closedSet.Contains(neighbor))
                {
                    continue;
                }
                if (openSet.Contains(neighbor))
                {
                    if (neighbor.Distance > current.Distance + current.neighborDistanceValues[(int)d])
                    {
                        neighbor.Distance = current.Distance + current.neighborDistanceValues[(int)d];
                        neighbor.pathFrom = current;
                    }
                }
                else
                {
                    neighbor.Distance = current.Distance + current.neighborDistanceValues[(int)d];
                    openSet.Add(neighbor);
                    neighbor.pathfinding = false;
                    neighbor.pathFrom = current;
                }
            }
        } while (openSet.Count != 0);
        //List<HexCell> frontier = new List<HexCell>();
        //fromCell.Distance = 0;
        //frontier.Add(fromCell);
        //while (frontier.Count > 0)
        //{
        //    HexCell current = frontier[0];
        //    if (current == toCell)
        //    {
        //        current = current.PathFrom;
        //        while (current != fromCell)
        //        {
        //            current.AddRoad(current.GetNeighbor(current.PathFrom));
        //            current = current.PathFrom;
        //        }
        //
        //        break;
        //    }
        //    frontier.RemoveAt(0);
        //    for (HexDirection d = HexDirection.NE; d <= HexDirection.NW; d++)
        //    {
        //        //cells ignored
        //        HexCell neighbor = current.GetNeighbor(d);
        //
        //        if (neighbor == null || neighbor.IsUnderwater)
        //        {
        //            continue;
        //        }
        //        HexEdgeType edgeType = current.GetEdgeType(neighbor);
        //        if (edgeType == HexEdgeType.Cliff)
        //        {
        //            continue;
        //        }
        //        int distance = current.Distance;
        //        if (current.HasRoadThroughEdge(d))
        //        {
        //            distance += 1;
        //        }
        //        else
        //        {
        //            distance += edgeType == HexEdgeType.Flat ? 5 : 10;
        //        }
        //        if (neighbor.Distance == int.MaxValue)
        //        {
        //            neighbor.pathfinding = false;
        //            neighbor.Distance = distance;
        //            neighbor.PathFrom = current;
        //            frontier.Add(neighbor);
        //        }
        //        else if (distance < neighbor.Distance)
        //        {
        //            neighbor.pathfinding = false;
        //            neighbor.Distance = distance;
        //            neighbor.PathFrom = current;
        //
        //        }
        //    }
        //    frontier.Sort((x, y) => x.Distance.CompareTo(y.Distance));
        //}
    }

    private int GetDistance(List<HexCell> cities, HexCell hexCell)
    {
        int distance = int.MaxValue;
       foreach(HexCell c in cities)
        {
            int current = GetDistance(c, hexCell);
            if(current == 0)
            {
                continue;
            }
            if (distance > current)
            {
                distance = current;
            }
        }

        return distance;
        
    }

    private int GetDistance(HexCell fromCell, HexCell toCell)
    {
        for (int i = 0; i < grid.Length; i++)
        {
            grid[i].Distance = int.MaxValue;
        }
        int distanceToCell = 0;

        //List<HexCell> frontier = new List<HexCell>();
        //fromCell.Distance = 0;
        //frontier.Add(fromCell);
        //while (frontier.Count > 0)
        //{
        //    HexCell current = frontier[0];
        //    if (current == toCell)
        //    {
        //        current = current.PathFrom;
        //        while (current != fromCell)
        //        {
        //            distanceToCell += current.Distance;
        //            current = current.PathFrom;
        //        }
        //        break;
        //    }
        //    frontier.RemoveAt(0);
        //    for (HexDirection d = HexDirection.NE; d <= HexDirection.NW; d++)
        //    {
        //        //cells ignored
        //        HexCell neighbor = current.GetNeighbor(d);
        //
        //        if (neighbor == null)
        //        {
        //            continue;
        //        }
        //        HexEdgeType edgeType = current.GetEdgeType(neighbor);
        //        if (edgeType == HexEdgeType.Cliff)
        //        {
        //            continue;
        //        }
        //        int distance = current.Distance;
        //        if (current.HasRoadThroughEdge(d))
        //        {
        //            distance += 1;
        //        }
        //        else if (current.IsUnderwater)
        //        {
        //            distance += 20;
        //        }
        //        else
        //        {
        //            distance += edgeType == HexEdgeType.Flat ? 5 : 10;
        //        }
        //        if (neighbor.Distance == int.MaxValue)
        //        {
        //            neighbor.pathfinding = false;
        //            neighbor.Distance = distance;
        //            neighbor.PathFrom = current;
        //            frontier.Add(neighbor);
        //        }
        //        else if (distance < neighbor.Distance)
        //        {
        //            neighbor.pathfinding = false;
        //            neighbor.Distance = distance;
        //            neighbor.PathFrom = current;
        //
        //        }
        //    }
        //    frontier.Sort((x, y) => x.Distance.CompareTo(y.Distance));
        //}
        return distanceToCell;
    }

    public float oceanWaterChance = 0.5f;
    public float lakeWaterChance = 0.2f;
    public float mountainModifier = 4.0f;
    private int cityCount = 5;
    private int minDistance = 50;
    private int riverCount = 1;

    public bool ActiveRoads { get; internal set; }
    public bool ActiveCities { get; internal set; }

    void OnEnable()
    {
        if (!HexMetric.noiseSource)
        {
            HexMetric.InitializeHashGrid(seed);
            HexMetric.noiseSource = noiseSource;
            //HexMetric.InitializeHashGrid(seed);
        }
    }
    void Awake()
    {
        //component setup
        //HexMetric.noiseSource = noiseSource;
        //HexMetric.InitializeHashGrid(seed);
        //// set grid size
        //grid = new HexCell[cellCountX * cellCountZ];
        //cellCountX = chunkCountX * HexMetric.chunkSizeX;
        //cellCountZ = chunkCountZ * HexMetric.chunkSizeZ;
        //
        //CreateChunks();
        //CreateCells(false);

    }

    internal void Refresh(int newx, int newz)
    {
        heightNoise = new CoherentNoise.Generation.ValueNoise2D(seed);
        temperatureNoise = new CoherentNoise.Generation.ValueNoise2D(seed+1);
        chunkCountX = newx;
        chunkCountZ = newz;
        UnityEngine.Random.InitState(seed);
        //component setup
        HexMetric.noiseSource = noiseSource;
        HexMetric.InitializeHashGrid(seed);
        // set grid size
        grid = new HexCell[cellCountX * cellCountZ];
        cellCountX = chunkCountX * HexMetric.chunkSizeX;
        cellCountZ = chunkCountZ * HexMetric.chunkSizeZ;

        CreateChunks();
        CreateCells(true);
    }
    public void OceanLevels()
    {
        for(int i = 0; i < cellCountX; ++i)
        {
            Flood(grid[i], true, false);
        }
        for (int i = grid.Length-1; i > grid.Length - cellCountX-1; --i)
        {
            Flood(grid[i], true, false);
        }
        for (int i = 0; i < grid.Length - cellCountX - 1; i += cellCountX)
        {
            Flood(grid[i], true, false);
        }
        for (int i = cellCountX-1; i < grid.Length; i += cellCountX)
        {
            Flood(grid[i], true, false);
        }
        for(int i = 0; i < 50; ++i)
        {
            Flood(grid[UnityEngine.Random.Range(0, grid.Length-1)], false, true);
        }
    }
    void CreateChunks()
    {
        chunks = new HexGridChunk[chunkCountX * chunkCountZ];

        for (int z = 0, i = 0; z < chunkCountZ; z++)
        {
            for (int x = 0; x < chunkCountX; x++)
            {
                HexGridChunk chunk = chunks[i++] = Instantiate(chunkPrefab);
                chunk.transform.SetParent(transform);
            }
        }
    }
    void CreateCells(bool Procedural)
    {
        grid = new HexCell[cellCountZ * cellCountX];
        if (Procedural)
        {
            for (int z = 0, i = 0; z < cellCountZ; z++)
            {
                for (int x = 0; x < cellCountX; x++)
                {
                    CreateProcCell(x, z, i++);
                }
            }
        }
        else
        {
            for (int z = 0, i = 0; z < cellCountZ; z++)
            {
                for (int x = 0; x < cellCountX; x++)
                {
                    CreateCell(x, z, i++);
                }
            }
        }
    }

    private void RefineCell(int x, int z, int v)
    {
        
    }

    void CreateCell(int x, int z, int i)
    {
        // create position of cell
        Vector3 position;
        position.x = (x + z * 0.5f - z / 2) * (HexMetric.innerRadius * 2f);
        position.y = 0f;
        position.z = z * (HexMetric.outerRadius * 1.5f);

        //spawn cell
        HexCell cell = grid[i] = Instantiate<HexCell>(tile);
        cell.transform.localPosition = position;
        cell.coordinates = HexCoordinates.FromOffsetCoordinates(x, z);

        // Neighbors Setup
        if (x > 0) 
        {
            // east and west
            cell.SetNeighbor(HexDirection.W, grid[i - 1]);
        }
        if (z > 0)
        {
            if ((z & 1) == 0) // even rows
            {
                //south east and north west
                cell.SetNeighbor(HexDirection.SE, grid[i - cellCountX]);
                if (x > 0)
                {    
                    //south west and north east
                    cell.SetNeighbor(HexDirection.SW, grid[i - cellCountX - 1]);
                }
            }
            else // odd rows
            {       
                //south east and north west
                cell.SetNeighbor(HexDirection.SW, grid[i - cellCountX]);
                if (x < cellCountX - 1)
                {
                    //south west and north east
                    cell.SetNeighbor(HexDirection.SE, grid[i - cellCountX + 1]);
                }
            }
        }

        //spawn and populate label
        Text label = Instantiate<Text>(cellLabel);
        label.rectTransform.anchoredPosition = new Vector2(position.x, position.z);
        label.text = cell.coordinates.ToStringOnSeparateLines();
        cell.uiRect = label.rectTransform;
        cell.Elevation = 0;
        AddCellToChunk(x, z, cell);

    }

    void CreateProcCell(int x, int z, int i)
    {
        // create position of cell
        Vector3 position;
        position.x = (x + z * 0.5f - z / 2) * (HexMetric.innerRadius * 2f);
        position.y = 0f;
        position.z = z * (HexMetric.outerRadius * 1.5f);

        //spawn cell
        HexCell cell = grid[i] = Instantiate<HexCell>(tile);
        cell.transform.localPosition = position;
        cell.coordinates = HexCoordinates.FromOffsetCoordinates(x, z);

        // Neighbors Setup
        if (x > 0)
        {
            // east and west
            cell.SetNeighbor(HexDirection.W, grid[i - 1]);
        }
        if (z > 0)
        {
            if ((z & 1) == 0) // even rows
            {
                //south east and north west
                cell.SetNeighbor(HexDirection.SE, grid[i - cellCountX]);
                if (x > 0)
                {
                    //south west and north east
                    cell.SetNeighbor(HexDirection.SW, grid[i - cellCountX - 1]);
                }
            }
            else // odd rows
            {
                //south east and north west
                cell.SetNeighbor(HexDirection.SW, grid[i - cellCountX]);
                if (x < cellCountX - 1)
                {
                    //south west and north east
                    cell.SetNeighbor(HexDirection.SE, grid[i - cellCountX + 1]);
                }
            }
        }

        //spawn and populate label
        Text label = Instantiate<Text>(cellLabel);
        label.rectTransform.anchoredPosition = new Vector2(position.x, position.z);
        cell.uiRect = label.rectTransform;
        float val = (heightNoise.GetValue(x, z, 0));
        int ele = ElevetionClamp(val);
        cell.Elevation = (int)ele;
        //Debug.Log(ele + " " + moisture);

        //cell.SetTerrain(terrains.SelectTerrain());
        AddCellToChunk(x, z, cell);

    }

    private int ElevetionClamp(float val)
    {
        if(val < 0)
        {
            val += 1;
        }
        return Mathf.RoundToInt(Mathf.Lerp(1, 7, Mathf.Pow(val, mountainModifier)));
        
    }

    void AddCellToChunk(int x, int z, HexCell cell)
    {
        int chunkX = x / HexMetric.chunkSizeX;
        int chunkZ = z / HexMetric.chunkSizeZ;
        HexGridChunk chunk = chunks[chunkX + chunkZ * chunkCountX];

        int localX = x - chunkX * HexMetric.chunkSizeX;
        int localZ = z - chunkZ * HexMetric.chunkSizeZ;
        chunk.AddCell(localX + localZ * HexMetric.chunkSizeX, cell);
    }

    public HexCell GetCell(Vector3 position)
    {
        position = transform.InverseTransformPoint(position);
        HexCoordinates coordinates = HexCoordinates.FromPosition(position);
        int index = coordinates.X + coordinates.Z * cellCountX + coordinates.Z / 2;
        return grid[index];
    }
    public HexCell GetCell(HexCoordinates coordinates)
    {
        int z = coordinates.Z;
        if (z < 0 || z >= cellCountZ)
        {
            return null;
        }
        int x = coordinates.X + z / 2;
        if (x < 0 || x >= cellCountX)
        {
            return null;
        }
        return grid[x + z * cellCountX];
    }
    public void FindPath(HexCell fromCell, HexCell toCell)
    {
        StopAllCoroutines();
        StartCoroutine(Search(fromCell, toCell));
    }

    IEnumerator Search(HexCell fromCell, HexCell toCell)
    {
        for (int i = 0; i < grid.Length; i++)
        {
            grid[i].Distance = int.MaxValue;
            grid[i].DisableHighlight();
        }
        
        
        WaitForSeconds delay = new WaitForSeconds(1 / 60f);
        List<HexCell> openSet = new List<HexCell>();
        List<HexCell> closedSet = new List<HexCell>();
        fromCell.Distance = 0;
        fromCell.EnableHighlight(Color.blue);
        toCell.EnableHighlight(Color.red);
        openSet.Add(fromCell);
        //do
        //{
        //    yield return delay;
        //    openSet.Sort((x, y) => x.Distance.CompareTo(y.Distance));
        //    HexCell current = openSet[0];
        //    openSet.RemoveAt(0);
        //    closedSet.Add(current);
        //    current.EnableHighlight(Color.green);
        //    if(closedSet.Contains(toCell))
        //    {
        //        current = toCell.pathFrom;
        //        while (current != fromCell)
        //        {
        //            current.EnableHighlight(Color.white);
        //            current = current.pathFrom;
        //        }
        //        break;
        //    }
        //    for (HexDirection d = HexDirection.NE; d < HexDirection.NW; ++d)
        //    {
        //        HexCell neighbor = current.GetNeighbor(d);
        //        if(current.neighborDistanceValues[(int)d] == -1)
        //        {
        //            continue;
        //        }
        //        if (closedSet.Contains(neighbor))
        //        {
        //            continue;
        //        }
        //        if(openSet.Contains(neighbor))
        //        {
        //            if(neighbor.Distance > current.Distance + current.neighborDistanceValues[(int)d])
        //            {
        //                neighbor.Distance = current.Distance + current.neighborDistanceValues[(int)d];
        //                neighbor.pathFrom = current;
        //            }
        //        }
        //        else
        //        {
        //            neighbor.Distance = current.Distance + current.neighborDistanceValues[(int)d];
        //            openSet.Add(neighbor);
        //            neighbor.pathfinding = true;
        //            neighbor.pathFrom = current;
        //        }
        //    }
        //} while (openSet.Count != 0);
        while (openSet.Count > 0)
        {
            yield return delay;
            HexCell current = openSet[0];
            if (current == toCell)
                {
                current = current.pathFrom;
                while (current != fromCell)
                {
                    current.EnableHighlight(Color.white);
                    current = current.pathFrom;
                }
                break;
                }
            openSet.RemoveAt(0);
            for (HexDirection d = HexDirection.NE; d <= HexDirection.NW; d++)
            {
                HexCell neighbor = current.GetNeighbor(d);
                if(current.neighborDistanceValues[(int)d] == -1)
                {
                    continue;
                }
                if (neighbor.Distance == int.MaxValue)
                {
                    neighbor.pathfinding = true;
                    neighbor.Distance = current.Distance + current.neighborDistanceValues[(int)d];
                    neighbor.pathFrom = current;
                    openSet.Add(neighbor);       

                }
                else if (current.Distance + current.neighborDistanceValues[(int)d] < neighbor.Distance)
                {
                    neighbor.pathfinding = true;
                    neighbor.Distance = current.Distance + current.neighborDistanceValues[(int)d];
                    neighbor.pathFrom = current;
        
                }
            }
            openSet.Sort((x, y) => x.Distance.CompareTo(y.Distance));
        }
    }


    private void Flood(HexCell cell, bool OceanWater, bool LakeWater)
    {
        cell.SetTerrain(new Terrain(0, true, 1, TerrainType.OceanWater, 0));
        cell.Elevation = 0;

        List<HexCell> frontier = new List<HexCell>();
        frontier.Add(cell);
        while (frontier.Count > 0)
        {
            HexCell current = frontier[0];
            frontier.RemoveAt(0);
            for (HexDirection d = HexDirection.NE; d <= HexDirection.NW; d++)
            {
                //cells ignored
                HexCell neighbor = current.GetNeighbor(d);

                if (neighbor == null || neighbor.IsUnderwater)
                {
                    continue;
                }
                float x = UnityEngine.Random.Range(0.0f, 1.0f);
                if (LakeWater)
                {
                    if (x <= lakeWaterChance)
                    {
                        frontier.Add(neighbor);
                        neighbor.SetTerrain(new Terrain(0, true, 1, TerrainType.LakeWater, 0));
                        neighbor.Elevation = 0;
                    }
                }
                HexEdgeType edgeType = current.GetEdgeType(neighbor);
                if (edgeType == HexEdgeType.Cliff)
                {
                    continue;
                }
              
                if (neighbor.Elevation <= 1)
                {
                    if (OceanWater)
                    {
                        if (x <= oceanWaterChance)
                        {
                            frontier.Add(neighbor);
                            neighbor.SetTerrain(new Terrain(0, true, 1, TerrainType.OceanWater, 0));
                            neighbor.Elevation = 0;
                        }
                    }
                    
                }
            }
        }
    }
    private void BuildRivers(HexCell fromCell, HexCell toCell)
    {
        for (int i = 0; i < grid.Length; i++)
        {
            grid[i].Distance = int.MaxValue;
        }

        
        List<HexCell> frontier = new List<HexCell>();
        fromCell.Distance = 0;
        frontier.Add(fromCell);
        HexCell current = frontier[0];
        //while (frontier.Count > 0)
        //{
        //    current = frontier[0];
        //
        //    if (current == toCell)
        //    {
        //        while (current != fromCell)
        //        {
        //            if (current.PathFrom == null)
        //            {
        //                break;
        //            }
        //            //current.EnableHighlight(Color.white);
        //            current.SetOutgoingRiver(current.GetNeighbor(current.PathFrom));                   
        //            current = current.PathFrom;
        //        }
        //        //while (current != toCell)
        //        //{
        //        //   current = current.PathTo;
        //        //}
        //        break;
        //    }
        //    frontier.RemoveAt(0);
        //    for (HexDirection d = HexDirection.NE; d <= HexDirection.NW; d++)
        //    {
        //        //cells ignored
        //        HexCell neighbor = current.GetNeighbor(d);
        //
        //        if (neighbor == null || neighbor.HasOutgoingRiver)
        //        {
        //            continue;
        //        }
        //        if (current.Elevation < neighbor.Elevation)
        //        {
        //            continue;
        //        }
        //        if (d == HexDirection.NE || d == HexDirection.NW)
        //        {
        //            continue;
        //        }
        //        int distance = current.Distance + 10;
        //        if (d == HexDirection.SE || d == HexDirection.SW)
        //        {
        //            distance -= 5;
        //        }
        //        if (neighbor.Distance == int.MaxValue)
        //        {
        //            neighbor.Distance = distance;
        //            current.PathTo = neighbor;
        //            neighbor.PathFrom = current;
        //            frontier.Add(neighbor);
        //        }
        //        else if (distance < neighbor.Distance)
        //        {
        //            neighbor.Distance = distance;
        //            neighbor.PathFrom = current;
        //            current.PathTo = neighbor;
        //        }
        //    }
        //    frontier.Sort((x, y) => x.Distance.CompareTo(y.Distance));
        //
        //    if (frontier.Count == 0)
        //    {
        //        {
        //            while (current != fromCell)
        //            {
        //                if (current.PathFrom == null)
        //                {
        //                    break;
        //                }
        //                //current.EnableHighlight(Color.white);
        //                current.SetOutgoingRiver(current.GetNeighbor(current.PathFrom));
        //                current = current.PathFrom;
        //            }
        //
        //            //while (current != toCell)
        //            //{
        //            //    if(current.PathTo == null)
        //            //    {
        //            //        Debug.Log("break");
        //            //        break;
        //            //    }
        //            //    current.EnableHighlight(Color.white);
        //            //    current.SetOutgoingRiver(current.GetNeighbor(current.PathTo));
        //            //    current = current.PathTo;
        //            //    
        //            //}
        //        }
        //    }
        //}
    }
    private void BiomeGen(HexCell cell, TerrainType biome)
    {
        cell.SetTerrain(new Terrain((int)biome, true, 0, biome, cell.Elevation));

        List<HexCell> frontier = new List<HexCell>();
        frontier.Add(cell);
        while (frontier.Count > 0)
        {
            HexCell current = frontier[0];
            frontier.RemoveAt(0);
            for (HexDirection d = HexDirection.NE; d <= HexDirection.NW; d++)
            {
                //cells ignored
                HexCell neighbor = current.GetNeighbor(d);
                float x = UnityEngine.Random.Range(0.0f, 1.0f);
                if (neighbor == null || neighbor.IsUnderwater)
                {
                    continue;
                }
                if (neighbor.GetTerrainType() == TerrainType.None)
                {
                    continue;
                }

                if (x <= 0.5f)
                {
                    frontier.Add(neighbor);
                    neighbor.SetTerrain(new Terrain((int)biome, true, 0, biome, neighbor.Elevation));
                }
            }
        }
    }
}
