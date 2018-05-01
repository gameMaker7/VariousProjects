using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class HexCell : MonoBehaviour {

    Terrain info;
    //basic info


    public HexCell()
    {
        info = new Terrain();
        info.roads = new bool[6];
        neighborDistanceValues = new int[6];
    }
    public int Elevation
    {
        get
        {
            return info.elevation;
        }
        set
        {
            if (info.elevation == value) { return; }
            info.elevation = value;
            Vector3 position = transform.localPosition;
            position.y = value * HexMetric.elevationStep;
            position.y += (HexMetric.SampleNoise(position).y * 2f - 1f) * HexMetric.elevationPerturbStrength;
            transform.localPosition = position;

            Vector3 uiPosition = uiRect.localPosition;
            uiPosition.z = (info.elevation * -HexMetric.elevationStep) * 1.1f;
            uiRect.localPosition = uiPosition;

            //river
            ValidateRivers();

            //road destruction based on elavation
            for (int i = 0; i < info.roads.Length; i++)
            {
                if (info.roads[i] && GetElevationDifference((HexDirection)i) > 1)
                {
                    SetRoad(i, false);
                }
            }
            Refresh();
        }
    }

    internal int GetNumNeighbors()
    {
        int count = 0;
        int i = 0;
        foreach(HexCell c in neighbors)
        {
           
            if(c == null || c.GetTerrainType() == TerrainType.Unproductive || c.IsUnderwater || c.GetElevationDifference((HexDirection)i) <= 2)
            {
                continue;
            }
            else
            {
                ++count;
            }
            ++i;
        }
        return count;
    }

    internal void CalcNeighborDistance()
    {
        for(int i = 0; i < neighbors.Length; ++i)
        {
            if(neighbors[i] == null)
            {
                neighborDistanceValues[i] = -1;
                continue;
            }
            if(neighbors[i].IsUnderwater)
            {
                neighborDistanceValues[i] = HexMetric.waterTravel;
                continue;
            }
            if (neighbors[i].GetEdgeType(this) == HexEdgeType.Cliff)
            {
                neighborDistanceValues[i] = HexMetric.cliffTravel;
                continue;
            }
            if (neighbors[i].GetEdgeType(this) == HexEdgeType.Slope)
            {
                neighborDistanceValues[i] = HexMetric.terraceTravel;
                continue;
            }
            
            if (neighbors[i].GetTerrainType() == TerrainType.Rocky)
            {
                neighborDistanceValues[i] = HexMetric.rockyTerrainTravel;
                continue;
            }
            if (neighbors[i].GetTerrainType() == TerrainType.Arid)
            {
                neighborDistanceValues[i] = HexMetric.aridTerrainTravel;
                continue;
            }
            if (neighbors[i].GetTerrainType() == TerrainType.Forest)
            {
                neighborDistanceValues[i] = HexMetric.forestTerrainTravel;
                continue;
            }
            if (neighbors[i].GetTerrainType() == TerrainType.Plains)
            {
                neighborDistanceValues[i] = HexMetric.plainsTerrainTravel;
                continue;
            }
        }
    }

    internal HexDirection GetNeighbor(HexCell pathFrom)
    {
        for (HexDirection d = HexDirection.NE; d < HexDirection.NW; ++d)
        {
            if (neighbors[(int)d] == pathFrom)
            {
                return d;
            }
        }
        return HexDirection.NONE;
    }

    internal void AddRoad(HexCell pathFrom)
    {
        for(HexDirection d = HexDirection.NE; d < HexDirection.NW; ++d)
        {
            if(neighbors[(int)d] == pathFrom)
            {
                AddRoad(d);
            }
        }
    }

    public int TerrainTypeIndex
    {
        get
        {
            return info.terrainTypeIndex;
        }
        set
        {
            if (info.terrainTypeIndex != value)
            {
                info.terrainTypeIndex = value;
                Refresh();
            }
        }
    }
    public Vector3 Position
    {
        get
        {
            return transform.localPosition;
        }
    }

    internal void SetterrainType(TerrainType name)
    {
        info.name = name;
    }



    public HexCoordinates coordinates;
    public RectTransform uiRect;
    public HexGridChunk chunk;
    [SerializeField]
    HexCell[] neighbors;
    public int[] neighborDistanceValues = new int[6];
    public HexCell pathFrom;

    //river info
    public HexDirection RiverBeginOrEndDirection
    {
        get
        {
            return info.RiverBeginOrEndDirection();
        }
    }

    internal TerrainType GetTerrainType()
    {
        return info.name;
    }

    public bool HasIncomingRiver
    {
        get
        {
            return info.hasIncomingRiver;
        }
    }
    public bool HasOutgoingRiver
    {
        get
        {
            return info.hasOutgoingRiver;
        }
    }
    public HexDirection IncomingRiver
    {
        get
        {
            return info.incomingRiver;
        }
    }
    public HexDirection OutgoingRiver
    {
        get
        {
            return info.outgoingRiver;
        }
    }
    public bool HasRiver
    {
        get
        {
            return info.hasIncomingRiver || info.hasOutgoingRiver;
        }
    }
    public bool HasRiverBeginOrEnd
    {
        get
        {
            return info.hasIncomingRiver != info.hasOutgoingRiver;
        }
    }
    public bool HasRiverThroughEdge(HexDirection direction)
    {
        return info.HasRiverThroughEdge(direction);
    }
    public float StreamBedY
    {
        get
        {
            return
                (info.elevation + HexMetric.streamBedElevationOffset) *
                HexMetric.elevationStep;
        }
    }
    public float RiverSurfaceY
    {
        get
        {
            return
                (info.elevation + HexMetric.waterElevationOffset) *
                HexMetric.elevationStep;
        }
    }


    public bool HasRoadThroughEdge(HexDirection direction)
    {
        return info.roads[(int)direction];
    }
    public bool HasRoads
    {
        get
        {
            for (int i = 0; i < info.roads.Length; i++)
            {
                if (info.roads[i])
                {
                    return true;
                }
            }
            return false;
        }
    }
    public void AddRoad(HexDirection direction)
    {
        if(direction == HexDirection.NONE)
        {
            return;
        }
        if (!info.roads[(int)direction] && !HasRiverThroughEdge(direction) && GetElevationDifference(direction) <= 1)
        {
            SetRoad((int)direction, true);
        }
    }
    public void RemoveRoads()
    {
        for (int i = 0; i < neighbors.Length; i++)
        {
            if (info.roads[i])
            {
                SetRoad(i, false);
            }
        }
    }
    void SetRoad(int index, bool state)
    {
        info.roads[index] = state;
        neighbors[index].info.roads[(int)((HexDirection)index).Opposite()] = state;
        neighbors[index].RefreshSelfOnly();
        RefreshSelfOnly();
    }
    public int GetElevationDifference(HexDirection direction)
    {
        int difference = info.elevation - GetNeighbor(direction).info.elevation;
        return difference >= 0 ? difference : -difference;
    }
    public HexCell GetNeighbor(HexDirection direction)
    {
        try
        {
            return neighbors[(int)direction];
        }
        catch (IndexOutOfRangeException e)
        {
            Debug.Log(direction);
        }
        return null;
    }
    public void Refresh()
    {
        if (chunk)
        {
            chunk.Refresh();
            for (int i = 0; i < neighbors.Length; i++)
            {
                HexCell neighbor = neighbors[i];
                if (neighbor != null && neighbor.chunk != chunk)
                {
                    neighbor.chunk.Refresh();
                }
            }
        }
    }
    public void SetNeighbor(HexDirection direction, HexCell newNeighbor)
    {
        neighbors[(int)direction] = newNeighbor;
        newNeighbor.neighbors[(int)direction.Opposite()] = this;

    }
    public HexEdgeType GetEdgeType(HexCell otherCell)
    {
        return HexMetric.GetEdgeType(info.elevation, otherCell.info.elevation);
    }
    public HexEdgeType GetEdgeType(HexDirection direction)
    {
        return HexMetric.GetEdgeType(info.elevation, neighbors[(int)direction].info.elevation);
    }
    void RefreshSelfOnly()
    {
        chunk.Refresh();
    }
    //river functions
    public void RemoveOutgoingRiver()
    {
        if (!info.hasOutgoingRiver)
        {
            return;
        }
        info.hasOutgoingRiver = false;
        HexCell neighbor = GetNeighbor(info.outgoingRiver);
        if (neighbor)
        {
            neighbor.info.hasIncomingRiver = false;
            neighbor.RefreshSelfOnly();
        } else { RefreshSelfOnly(); }
    }
    public void RemoveIncomingRiver()
    {
        if (!info.hasIncomingRiver)
        {
            return;
        }
        info.hasIncomingRiver = false;
        HexCell neighbor = GetNeighbor(info.incomingRiver);
        if (neighbor)
        {
            neighbor.info.hasOutgoingRiver = false;
            neighbor.RefreshSelfOnly();
        } else { RefreshSelfOnly();
        }
    }
    public void RemoveRiver()
    {
        RemoveOutgoingRiver();
        RemoveIncomingRiver();
    }
    public void SetOutgoingRiver(HexDirection direction)
    {
        if (info.hasOutgoingRiver && info.outgoingRiver == direction)
        {
            return;
        }
        HexCell neighbor = GetNeighbor(direction);
        if (!IsValidRiverDestination(neighbor))
        {
            return;
        }
        RemoveOutgoingRiver();
        if (info.hasIncomingRiver && info.incomingRiver == direction)
        {
            RemoveIncomingRiver();
        }
        info.hasOutgoingRiver = true;
        info.outgoingRiver = direction;
        neighbor.RemoveIncomingRiver();
        neighbor.info.hasIncomingRiver = true;
        neighbor.info.incomingRiver = direction.Opposite();
        SetRoad((int)direction, false);
    }

    //features
    public int UrbanLevel
    {
        get
        {
            return urbanLevel;
        }
        set
        {
            if (urbanLevel != value)
            {
                urbanLevel = value;
                RefreshSelfOnly();
            }
        }
    }
    int urbanLevel;

    //water
    public int WaterLevel
    {
        get
        {
            return info.waterLevel;
        }
        set
        {
            if (info.waterLevel == value)
            {
                return;
            }
            info.waterLevel = value;
            ValidateRivers();
            Refresh();
        }
    }
    public bool IsUnderwater
    {
        get
        {
            return info.waterLevel > info.elevation;
        }
    }
    public float WaterSurfaceY
    {
        get
        {
            return
                (info.waterLevel + HexMetric.waterElevationOffset) *
                HexMetric.elevationStep;
        }
    }
    bool IsValidRiverDestination(HexCell neighbor)
    {
        return neighbor && (
            info.elevation >= neighbor.info.elevation || info.waterLevel == neighbor.info.elevation
        );
    }
    void ValidateRivers()
    {
        if (
            info.hasOutgoingRiver && !IsValidRiverDestination(GetNeighbor(info.outgoingRiver))
        )
        {
            RemoveOutgoingRiver();
        }
        if (
            info.hasIncomingRiver && !GetNeighbor(info.incomingRiver).IsValidRiverDestination(this)
        )
        {
            RemoveIncomingRiver();
        }
    }

    public override string ToString()
    {
        return "Terrain Type: " + info.name + "\n Elevation: " + info.elevation + "\n Water Level: " + info.waterLevel + "";
    }

    public void SetTerrain(Terrain newTerrain)
    {
        info = new Terrain(newTerrain);
    }


    //navigation

    int distance = 0;
    public bool pathfinding = false;
    public int Distance
    {
        get
        {
            return distance;
        }
        set
        {
            distance = value;
            if (pathfinding)
            {
                UpdateDistanceLabel();
            }
        }
    }
    void UpdateDistanceLabel()
    {
        Text label = uiRect.GetComponent<Text>();
        label.text = distance == int.MaxValue ? "" : distance.ToString();
    }
    public void DisableHighlight()
    {
        Image highlight = uiRect.GetChild(0).GetComponent<Image>();
        highlight.enabled = false;
    }
    public void EnableHighlight(Color color)
    {
        Image highlight = uiRect.GetChild(0).GetComponent<Image>();
        highlight.color = color;
        highlight.enabled = true;
    }
}