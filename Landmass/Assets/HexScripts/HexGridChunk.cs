using System;
using UnityEngine;
using UnityEngine.UI;

public class HexGridChunk : MonoBehaviour
{
    HexCell[] cells;

    public HexMesh terrain, rivers, roads, water, waterShore, estuaries;
    public FeatureManager features;
    Canvas gridCanvas;
    static Color color1 = new Color(1f, 0f, 0f);
    static Color color2 = new Color(0f, 1f, 0f);
    static Color color3 = new Color(0f, 0f, 1f);

    void Awake()
    {
        gridCanvas = GetComponentInChildren<Canvas>();

        cells = new HexCell[HexMetric.chunkSizeX * HexMetric.chunkSizeZ];
    }

    public void Refresh()
    {
        enabled = true;
    }

    void LateUpdate()
    {
        Triangulate();
        enabled = false;
    }
    public void Triangulate()
    {
        // clear old data
        terrain.Clear();
        rivers.Clear();
        roads.Clear();
        water.Clear();
        waterShore.Clear();
        estuaries.Clear();
        features.Clear();
        // triangulate cells
        for (int i = 0; i < cells.Length; ++i)
        {
            Triangulate(cells[i]);

        }

        //reset mesh
        terrain.Apply();
        rivers.Apply();
        roads.Apply();
        water.Apply();
        waterShore.Apply();
        estuaries.Apply();
        features.Apply();
    }

    internal void GenBiome(TerrainType biome, TerrainCollection terrains)
    {
        foreach (HexCell c in cells)
        {
            if (!c.IsUnderwater)
            { 
                Terrain t = terrains.SelectTerrain(c, biome);
                c.Elevation = t.elevation;
                c.TerrainTypeIndex = t.terrainTypeIndex;
                c.WaterLevel = t.waterLevel;
                c.SetterrainType(t.name);
            }
        }
        foreach (HexCell c in cells)
        {
            c.CalcNeighborDistance();
        }
    }

    public void AddCell(int index, HexCell cell)
    {
        cells[index] = cell;
        cell.chunk = this;
        cell.transform.SetParent(transform, false);
        cell.uiRect.SetParent(gridCanvas.transform, false);
    }

    void Triangulate(HexCell cell)
    {
        for (HexDirection d = HexDirection.NE; d <= HexDirection.NW; d++)
        {
            if (cell != null)
            {
                Triangulate(d, cell);
            }
        }
        if (!cell.IsUnderwater && !cell.HasRiver && !cell.HasRoads)
        {
            features.AddFeature(cell.Position, cell);
        }
    }
    void Triangulate(HexDirection direction, HexCell cell)
    {
        Vector3 center = cell.Position;
        EdgeVertices e = new EdgeVertices(
            center + HexMetric.GetFirstSolidCorner(direction),
            center + HexMetric.GetSecondSolidCorner(direction)
        );

        if (cell.HasRiver)
        {
            if (cell.HasRiverThroughEdge(direction))
            {
                e.v3.y = cell.StreamBedY;
                if (cell.HasRiverBeginOrEnd)
                {
                    TriangulateWithRiverBeginOrEnd(direction, cell, center, e);
                }
                else
                {
                    TriangulateWithRiver(direction, cell, center, e);
                }
            }
            else
            {
                TriangulateAdjacentToRiver(direction, cell, center, e);
            }
        }
        else
        {
            TriangulateWithoutRiver(direction, cell, center, e);
            if (!cell.IsUnderwater && !cell.HasRiverThroughEdge(direction))
            {
                features.AddFeature((center + e.v1 + e.v5) * (1f / 3f), cell);
            }
        }
        if (direction <= HexDirection.SE)
        {
            TriangulateConnection(direction, cell, e);
        }
        if (cell.IsUnderwater)
        {
            TriangulateWater(direction, cell, center);
        }
    }

    private void TriangulateWater(HexDirection direction, HexCell cell, Vector3 center)
    {
        center.y = cell.WaterSurfaceY;

        HexCell neighbor = cell.GetNeighbor(direction);
        if (neighbor != null && !neighbor.IsUnderwater)
        {
            TriangulateWaterShore(direction, cell, neighbor, center);
        }
        else
        {
            TriangulateOpenWater(direction, cell, neighbor, center);
        }
    }
    void TriangulateOpenWater(HexDirection direction, HexCell cell, HexCell neighbor, Vector3 center)
    {
        Vector3 c1 = center + HexMetric.GetFirstWaterCorner(direction);
        Vector3 c2 = center + HexMetric.GetSecondWaterCorner(direction);

        water.AddTriangle(center, c1, c2);

        if (direction <= HexDirection.SE && neighbor != null)
        {
            Vector3 bridge = HexMetric.GetWaterBridge(direction);
            Vector3 e1 = c1 + bridge;
            Vector3 e2 = c2 + bridge;

            water.AddQuad(c1, c2, e1, e2);
            if (direction <= HexDirection.E)
            {
                HexCell nextNeighbor = cell.GetNeighbor(direction.Next());
                if (nextNeighbor == null || !nextNeighbor.IsUnderwater)
                {
                    return;
                }
                water.AddTriangle(
                    c2, e2, c2 + HexMetric.GetWaterBridge(direction.Next())
                );
            }
        }
    }

    void TriangulateWaterShore(HexDirection direction, HexCell cell, HexCell neighbor, Vector3 center)
    {
        EdgeVertices e1 = new EdgeVertices(
            center + HexMetric.GetFirstWaterCorner(direction),
            center + HexMetric.GetSecondWaterCorner(direction)
        );
        water.AddTriangle(center, e1.v1, e1.v2);
        water.AddTriangle(center, e1.v2, e1.v3);
        water.AddTriangle(center, e1.v3, e1.v4);
        water.AddTriangle(center, e1.v4, e1.v5);

        Vector3 center2 = neighbor.Position;
        center2.y = center.y;
        EdgeVertices e2 = new EdgeVertices(
            center2 + HexMetric.GetSecondSolidCorner(direction.Opposite()),
            center2 + HexMetric.GetFirstSolidCorner(direction.Opposite())
        );

        if (cell.HasRiverThroughEdge(direction))
        {
            TriangulateEstuary(e1, e2, cell.IncomingRiver == direction);
        }
        else
        {
            waterShore.AddQuad(e1.v1, e1.v2, e2.v1, e2.v2);
            waterShore.AddQuad(e1.v2, e1.v3, e2.v2, e2.v3);
            waterShore.AddQuad(e1.v3, e1.v4, e2.v3, e2.v4);
            waterShore.AddQuad(e1.v4, e1.v5, e2.v4, e2.v5);
            waterShore.AddQuadUV(0f, 0f, 0f, 1f);
            waterShore.AddQuadUV(0f, 0f, 0f, 1f);
            waterShore.AddQuadUV(0f, 0f, 0f, 1f);
            waterShore.AddQuadUV(0f, 0f, 0f, 1f);
        }

        HexCell nextNeighbor = cell.GetNeighbor(direction.Next());
        if (nextNeighbor != null)
        {
            Vector3 v3 = nextNeighbor.Position + (nextNeighbor.IsUnderwater ? HexMetric.GetFirstWaterCorner(direction.Previous()) : HexMetric.GetFirstSolidCorner(direction.Previous()));
            v3.y = center.y;
            waterShore.AddTriangle(e1.v5, e2.v5, v3); waterShore.AddTriangleUV(new Vector2(0f, 0f), new Vector2(0f, 1f), new Vector2(0f, nextNeighbor.IsUnderwater ? 0f : 1f));
        }
    }

    private void TriangulateEstuary(EdgeVertices e1, EdgeVertices e2, bool incomingRiver)
    {
        waterShore.AddTriangle(e2.v1, e1.v2, e1.v1);
        waterShore.AddTriangle(e2.v5, e1.v5, e1.v4);
        waterShore.AddTriangleUV(
            new Vector2(0f, 1f), new Vector2(0f, 0f), new Vector2(0f, 0f)
        );
        waterShore.AddTriangleUV(
            new Vector2(0f, 1f), new Vector2(0f, 0f), new Vector2(0f, 0f)
        );

        estuaries.AddQuad(e2.v1, e1.v2, e2.v2, e1.v3);
        estuaries.AddTriangle(e1.v3, e2.v2, e2.v4);
        estuaries.AddQuad(e1.v3, e1.v4, e2.v4, e2.v5);

        estuaries.AddQuadUV(
              new Vector2(0f, 1f), new Vector2(0f, 0f),
              new Vector2(1f, 1f), new Vector2(0f, 0f)
          );
        estuaries.AddTriangleUV(
            new Vector2(0f, 0f), new Vector2(1f, 1f), new Vector2(1f, 1f)
        );
        estuaries.AddQuadUV(
            new Vector2(0f, 0f), new Vector2(0f, 0f),
            new Vector2(1f, 1f), new Vector2(0f, 1f));
        if (incomingRiver)
        {
            estuaries.AddQuadUV2(
                new Vector2(1.5f, 1f), new Vector2(0.7f, 1.15f),
                new Vector2(1f, 0.8f), new Vector2(0.5f, 1.1f)
            );
            estuaries.AddTriangleUV2(
                new Vector2(0.5f, 1.1f),
                new Vector2(1f, 0.8f),
                new Vector2(0f, 0.8f)
            );
            estuaries.AddQuadUV2(
                new Vector2(0.5f, 1.1f), new Vector2(0.3f, 1.15f),
                new Vector2(0f, 0.8f), new Vector2(-0.5f, 1f)
            );
        }
        else
        {
            estuaries.AddQuadUV2(
                new Vector2(-0.5f, -0.2f), new Vector2(0.3f, -0.35f),
                new Vector2(0f, 0f), new Vector2(0.5f, -0.3f)
            );
            estuaries.AddTriangleUV2(
                new Vector2(0.5f, -0.3f),
                new Vector2(0f, 0f),
                new Vector2(1f, 0f)
            );
            estuaries.AddQuadUV2(
                new Vector2(0.5f, -0.3f), new Vector2(0.7f, -0.35f),
                new Vector2(1f, 0f), new Vector2(1.5f, -0.2f)
            );
        }
    }
    Vector2 GetRoadInterpolators(HexDirection direction, HexCell cell)
    {
        Vector2 interpolators;
        if (cell.HasRoadThroughEdge(direction))
        {
            interpolators.x = interpolators.y = 0.5f;
        }
        else
        {
            interpolators.x = cell.HasRoadThroughEdge(direction.Previous()) ? 0.5f : 0.25f;
            interpolators.y = cell.HasRoadThroughEdge(direction.Next()) ? 0.5f : 0.25f;
        }
        return interpolators;
    }

    void TriangulateRoadEdge(Vector3 center, Vector3 mL, Vector3 mR)
    {
        roads.AddTriangle(center, mL, mR);
        roads.AddTriangleUV(new Vector2(1f, 0f), new Vector2(0f, 0f), new Vector2(0f, 0f));
    }
    void TriangulateRoadSegment(Vector3 v1, Vector3 v2, Vector3 v3, Vector3 v4, Vector3 v5, Vector3 v6)
    {
        roads.AddQuad(v1, v2, v4, v5);
        roads.AddQuad(v2, v3, v5, v6);
        roads.AddQuadUV(0f, 1f, 0f, 0f);
        roads.AddQuadUV(1f, 0f, 0f, 0f);
    }
    private void TriangulateRoadAdjacentToRiver(HexDirection direction, HexCell cell, Vector3 center, EdgeVertices e)
    {
        bool hasRoadThroughEdge = cell.HasRoadThroughEdge(direction);
        bool previousHasRiver = cell.HasRiverThroughEdge(direction.Previous());
        bool nextHasRiver = cell.HasRiverThroughEdge(direction.Next());
        Vector2 interpolators = GetRoadInterpolators(direction, cell);
        Vector3 roadCenter = center;

        if (cell.HasRiverBeginOrEnd)
        {
            roadCenter += HexMetric.GetSolidEdgeMiddle(cell.RiverBeginOrEndDirection.Opposite()) * (1f / 3f);
        }
        else if (cell.IncomingRiver == cell.OutgoingRiver.Opposite())
        {
            Vector3 corner;
            if (previousHasRiver)
            {
                if (
                    !hasRoadThroughEdge &&
                    !cell.HasRoadThroughEdge(direction.Next())
                )
                {
                    return;
                }
                corner = HexMetric.GetSecondSolidCorner(direction);
            }
            else
            {
                if (
                    !hasRoadThroughEdge &&
                    !cell.HasRoadThroughEdge(direction.Previous())
                )
                {
                    return;
                }
                corner = HexMetric.GetFirstSolidCorner(direction);
            }
            roadCenter += corner * 0.5f;
            center += corner * 0.25f;
        }

        else if (cell.IncomingRiver == cell.OutgoingRiver.Previous())
        {
            roadCenter -= HexMetric.GetSecondCorner(cell.IncomingRiver) * 0.2f;
        }

        else if (cell.IncomingRiver == cell.OutgoingRiver.Next())
        {
            roadCenter -= HexMetric.GetFirstCorner(cell.IncomingRiver) * 0.2f;
        }

        else if (previousHasRiver && nextHasRiver)
        {
            if (!hasRoadThroughEdge)
            {
                return;
            }

            Vector3 offset = HexMetric.GetSolidEdgeMiddle(direction) * HexMetric.innerToOuter;
            roadCenter += offset * 0.7f;
            center += offset * 0.5f;
        }

        else
        {
            HexDirection middle;
            if (previousHasRiver)
            {
                middle = direction.Next();
            }
            else if (nextHasRiver)
            {
                middle = direction.Previous();
            }
            else
            {
                middle = direction;
            }
            if (
                  !cell.HasRoadThroughEdge(middle) &&
                  !cell.HasRoadThroughEdge(middle.Previous()) &&
                  !cell.HasRoadThroughEdge(middle.Next())
              )
            {
                return;
            }
            roadCenter += HexMetric.GetSolidEdgeMiddle(middle) * 0.25f;
        }

        Vector3 mL = Vector3.Lerp(roadCenter, e.v1, interpolators.x);
        Vector3 mR = Vector3.Lerp(roadCenter, e.v5, interpolators.y);
        TriangulateRoad(roadCenter, mL, mR, e, hasRoadThroughEdge);
        if (previousHasRiver)
        {
            TriangulateRoadEdge(roadCenter, center, mL);
        }
        if (nextHasRiver)
        {
            TriangulateRoadEdge(roadCenter, mR, center);
        }
    }
    void TriangulateRiverQuad(Vector3 v1, Vector3 v2, Vector3 v3, Vector3 v4, float y, float v, bool reversed)
    {
        TriangulateRiverQuad(v1, v2, v3, v4, y, y, v, reversed);
    }
        void TriangulateRiverQuad(Vector3 v1, Vector3 v2, Vector3 v3, Vector3 v4, float y1, float y2, float v, bool reversed)
    {
        v1.y = v2.y = y1;
        v3.y = v4.y = y2;
        rivers.AddQuad(v1, v2, v3, v4);
        if (reversed)
        {
            rivers.AddQuadUV(1f, 0f, 0.8f - v, 0.6f - v);
        }
        else{ rivers.AddQuadUV(0f, 1f, v, v + 0.2f);
        }
    }
    private void TriangulateWithoutRiver(HexDirection direction, HexCell cell, Vector3 center, EdgeVertices e)
    {
        TriangulateEdgeFan(center, e, cell.TerrainTypeIndex);
        if (cell.HasRoads) {
            Vector2 interpolators = GetRoadInterpolators(direction, cell);

            TriangulateRoad(center, Vector3.Lerp(center, e.v1, interpolators.x),
                                    Vector3.Lerp(center, e.v5, interpolators.y), e, cell.HasRoadThroughEdge(direction));
        }
    }
    void TriangulateAdjacentToRiver(HexDirection direction, HexCell cell, Vector3 center, EdgeVertices e)
    {
        //alter channel center
        if (cell.HasRoads)
        {
            TriangulateRoadAdjacentToRiver(direction, cell, center, e);
        }
        if (cell.HasRiverThroughEdge(direction.Next()))
        {
           
            if (cell.HasRiverThroughEdge(direction.Previous()))
            {
                center += HexMetric.GetSolidEdgeMiddle(direction) * (HexMetric.innerToOuter * 0.5f);
            }
            else if (cell.HasRiverThroughEdge(direction.Previous2()))
            {
                center += HexMetric.GetFirstSolidCorner(direction) * 0.25f;
            }
        }
        else if (cell.HasRiverThroughEdge(direction.Previous()) && cell.HasRiverThroughEdge(direction.Next2()))
        {
            center += HexMetric.GetSecondSolidCorner(direction) * 0.25f;
        }
        
        EdgeVertices m = new EdgeVertices(Vector3.Lerp(center, e.v1, 0.5f), Vector3.Lerp(center, e.v5, 0.5f));
        TriangulateEdgeStrip(m, color1, cell.TerrainTypeIndex, e, color1, cell.TerrainTypeIndex);
        TriangulateEdgeFan(center, m, cell.TerrainTypeIndex);
        if (!cell.IsUnderwater && !cell.HasRiverThroughEdge(direction))
        {
            features.AddFeature((center + e.v1 + e.v5) * (1f / 3f), cell);
        }
    }


    void TriangulateWithRiver(HexDirection direction, HexCell cell, Vector3 center, EdgeVertices e)
    {
        //outer walls
        Vector3 centerL, centerR;
        if (cell.HasRiverThroughEdge(direction.Opposite()))
        {
            centerL = center +
                HexMetric.GetFirstSolidCorner(direction.Previous()) * 0.25f;
            centerR = center +
                HexMetric.GetSecondSolidCorner(direction.Next()) * 0.25f;
        }
        // sharp turns
        else if (cell.HasRiverThroughEdge(direction.Next()))
        {
            centerL = center;
            centerR = Vector3.Lerp(center, e.v5, 2f / 3f);
        }
        else if (cell.HasRiverThroughEdge(direction.Previous()))
        {
            centerL = Vector3.Lerp(center, e.v1, 2f / 3f);
            centerR = center;
        }
        // two way turns
        else if (cell.HasRiverThroughEdge(direction.Next2()))
        {
            centerL = center;
            centerR = center + HexMetric.GetSolidEdgeMiddle(direction.Next()) * (0.5f * HexMetric.innerToOuter);
        }
        else
        {
            centerL = center + HexMetric.GetSolidEdgeMiddle(direction.Previous()) * (0.5f * HexMetric.innerToOuter);
            centerR = center;
        }
        
        //mid point of channel
        EdgeVertices m = new EdgeVertices(Vector3.Lerp(centerL, e.v1, 0.5f), Vector3.Lerp(centerR, e.v5, 0.5f), 1f / 6f);
        m.v3.y = center.y = e.v3.y;
        //fill area
        TriangulateEdgeStrip(m, color1, cell.TerrainTypeIndex, e, color1, cell.TerrainTypeIndex);
        terrain.AddTriangle(centerL, m.v1, m.v2);
        terrain.AddQuad(centerL, center, m.v2, m.v3);
        terrain.AddQuad(center, centerR, m.v3, m.v4);
        terrain.AddTriangle(centerR, m.v4, m.v5);

        terrain.AddTriangleColor(color1);
        terrain.AddQuadColor(color1);
        terrain.AddQuadColor(color1);
        terrain.AddTriangleColor(color1);

        Vector3 types;
        types.x = types.y = types.z = cell.TerrainTypeIndex;
        terrain.AddTriangleTerrainTypes(types);
        terrain.AddQuadTerrainTypes(types);
        terrain.AddQuadTerrainTypes(types);
        terrain.AddTriangleTerrainTypes(types);

        //river water quads
        if (!cell.IsUnderwater)
        {
            bool reversed = cell.IncomingRiver == direction;

            TriangulateRiverQuad(centerL, centerR, m.v2, m.v4, cell.RiverSurfaceY, 0.4f, reversed);
            TriangulateRiverQuad(m.v2, m.v4, e.v2, e.v4, cell.RiverSurfaceY, 0.6f, reversed);
        }
    }
    void TriangulateWithRiverBeginOrEnd(HexDirection direction, HexCell cell, Vector3 center, EdgeVertices e)
    {
        //cap river channel
        EdgeVertices m = new EdgeVertices(Vector3.Lerp(center, e.v1, 0.5f), Vector3.Lerp(center, e.v5, 0.5f));
        m.v3.y = e.v3.y;
        TriangulateEdgeStrip(m, color1, cell.TerrainTypeIndex, e, color1, cell.TerrainTypeIndex);
        TriangulateEdgeFan(center, m, cell.TerrainTypeIndex);
        //river water quads
        if (!cell.IsUnderwater) {
            bool reversed = cell.HasIncomingRiver;
            TriangulateRiverQuad(m.v2, m.v4, e.v2, e.v4, cell.RiverSurfaceY, 0.6f, reversed);
            center.y = m.v2.y = m.v4.y = cell.RiverSurfaceY;
            rivers.AddTriangle(center, m.v2, m.v4);
            if (reversed)
            {
                rivers.AddTriangleUV(new Vector2(0.5f, 0.4f), new Vector2(1f, 0.2f), new Vector2(0f, 0.2f));
            }
            else
            {
                rivers.AddTriangleUV(new Vector2(0.5f, 0.4f), new Vector2(0f, 0.6f), new Vector2(1f, 0.6f));
            }
        }
    }
    void TriangulateWaterfallInWater(Vector3 v1, Vector3 v2, Vector3 v3, Vector3 v4, float y1, float y2, float waterY)
    {
        v1.y = v2.y = y1;
        v3.y = v4.y = y2;
        v1 = HexMetric.Perturb(v1);
        v2 = HexMetric.Perturb(v2);
        v3 = HexMetric.Perturb(v3);
        v4 = HexMetric.Perturb(v4);
        float t = (waterY - y2) / (y1 - y2);
        v3 = Vector3.Lerp(v3, v1, t);
        v4 = Vector3.Lerp(v4, v2, t);
        rivers.AddQuadUnperturbed(v1, v2, v3, v4);
        rivers.AddQuadUV(0f, 1f, 0.8f, 1f);
    }

    void TriangulateRoad(Vector3 center, Vector3 mL, Vector3 mR, EdgeVertices e, bool hasRoadThroughCellEdge)
    {
        if (hasRoadThroughCellEdge)
        {
            Vector3 mC = Vector3.Lerp(mL, mR, 0.5f);
            TriangulateRoadSegment(mL, mC, mR, e.v2, e.v3, e.v4);
            roads.AddTriangle(center, mL, mC);
            roads.AddTriangle(center, mC, mR);
            roads.AddTriangleUV(new Vector2(1f, 0f), new Vector2(0f, 0f), new Vector2(1f, 0f));
            roads.AddTriangleUV(new Vector2(1f, 0f), new Vector2(1f, 0f), new Vector2(0f, 0f));
        }else
        {
            TriangulateRoadEdge(center, mL, mR);
        }
    }

    void TriangulateConnection(HexDirection direction, HexCell cell, EdgeVertices e1)
    {
        //Is border cell?
        HexCell neighbor = cell.GetNeighbor(direction);
        if (neighbor == null)
        {
            return;
        }
        //create bridge between cells
        Vector3 bridge = HexMetric.GetBridge(direction);
        bridge.y = neighbor.Position.y - cell.Position.y;
        EdgeVertices e2 = new EdgeVertices(e1.v1 + bridge, e1.v5 + bridge);
        if (cell.HasRiverThroughEdge(direction))
        {
            e2.v3.y = neighbor.StreamBedY;
            if (!cell.IsUnderwater)
            {
                if (!neighbor.IsUnderwater)
                {
                    TriangulateRiverQuad(e1.v2, e1.v4, e2.v2, e2.v4, cell.RiverSurfaceY, neighbor.RiverSurfaceY, 0.8f, cell.HasIncomingRiver && cell.IncomingRiver == direction);
                }
                else if (cell.Elevation > neighbor.WaterLevel)
                {
                    TriangulateWaterfallInWater(e1.v2, e1.v4, e2.v2, e2.v4, cell.RiverSurfaceY, neighbor.RiverSurfaceY, neighbor.WaterSurfaceY);
                }
            }
            else if (
                !neighbor.IsUnderwater && neighbor.Elevation > cell.WaterLevel)
            {
                TriangulateWaterfallInWater(e2.v4, e2.v2, e1.v4, e1.v2, neighbor.RiverSurfaceY, cell.RiverSurfaceY, cell.WaterSurfaceY);
            }
        }
        if (cell.GetEdgeType(direction) == HexEdgeType.Slope)
        {
            TriangulateEdgeTerraces(e1, cell, e2, neighbor, cell.HasRoadThroughEdge(direction));
        }
        else // cliffs or flat cells
        {
            TriangulateEdgeStrip(e1, color1, cell.TerrainTypeIndex, e2, color2, cell.TerrainTypeIndex, cell.HasRoadThroughEdge(direction));
        }
        //create triangle that connects three cells together
        HexCell nextNeighbor = cell.GetNeighbor(direction.Next());
        if (direction <= HexDirection.E && nextNeighbor != null)
        {
            Vector3 v5 = e1.v5 + HexMetric.GetBridge(direction.Next());
            v5.y = nextNeighbor.Position.y;
            if (cell.Elevation <= neighbor.Elevation)
            {
                if (cell.Elevation <= nextNeighbor.Elevation)
                {
                    TriangulateCorner(e1.v5, cell, e2.v5, neighbor, v5, nextNeighbor);
                }
                else
                {
                    TriangulateCorner(v5, nextNeighbor, e1.v5, cell, e2.v5, neighbor);
                }
            }
            else if (neighbor.Elevation <= nextNeighbor.Elevation)
            {
                TriangulateCorner(e2.v5, neighbor, v5, nextNeighbor, e1.v5, cell);
            }
            else
            {
                TriangulateCorner(v5, nextNeighbor, e1.v5, cell, e2.v5, neighbor);
            }
        }
    }
    void TriangulateEdgeTerraces(EdgeVertices begin, HexCell beginCell, EdgeVertices end, HexCell endCell, bool hasRoad)
    {
        EdgeVertices e2 = EdgeVertices.TerraceLerp(begin, end, 1);
        Color c2 = HexMetric.TerraceLerp(color1, color2, 1);
        float t1 = beginCell.TerrainTypeIndex;
        float t2 = endCell.TerrainTypeIndex;
        //first task
        TriangulateEdgeStrip(begin, color1, t1, e2, c2, t2, hasRoad);

        //process of creating actual steps
        for (int i = 2; i < HexMetric.terraceSteps; i++)
        {
            EdgeVertices e1 = e2;
            Color c1 = c2;
            e2 = EdgeVertices.TerraceLerp(begin, end, i);
            c2 = HexMetric.TerraceLerp(color1, color2, i);
            TriangulateEdgeStrip(e1, c1, t1, e2, c2, t2, hasRoad);
        }

        //last task
        TriangulateEdgeStrip(e2, c2, t1, end, color2, t2, hasRoad);
    }
    void TriangulateCornerTerraces(Vector3 begin, HexCell beginCell, Vector3 left, HexCell leftCell, Vector3 right, HexCell rightCell)
    {
        Vector3 v3 = HexMetric.TerraceLerp(begin, left, 1);
        Vector3 v4 = HexMetric.TerraceLerp(begin, right, 1);
        Color c3 = HexMetric.TerraceLerp(color1, color2, 1);
        Color c4 = HexMetric.TerraceLerp(color1, color3, 1);

        Vector3 types;
        types.x = beginCell.TerrainTypeIndex;
        types.y = leftCell.TerrainTypeIndex;
        types.z = rightCell.TerrainTypeIndex;

        terrain.AddTriangle(begin, v3, v4);
        terrain.AddTriangleColor(color1, c3, c4);
        terrain.AddTriangleTerrainTypes(types);

        for (int i = 2; i < HexMetric.terraceSteps; i++)
        {
            Vector3 v1 = v3;
            Vector3 v2 = v4;
            Color c1 = c3;
            Color c2 = c4;
            v3 = HexMetric.TerraceLerp(begin, left, i);
            v4 = HexMetric.TerraceLerp(begin, right, i);
            c3 = HexMetric.TerraceLerp(color1, color2, i);
            c4 = HexMetric.TerraceLerp(color1, color3, i);
            terrain.AddQuad(v1, v2, v3, v4);
            terrain.AddQuadColor(c1, c2, c3, c4);
            terrain.AddQuadTerrainTypes(types);
        }

        terrain.AddQuad(v3, v4, left, right);
        terrain.AddQuadColor(c3, c4, color2, color3);
        terrain.AddQuadTerrainTypes(types);
    }
    void TriangulateCorner(Vector3 bottom, HexCell bottomCell, Vector3 left, HexCell leftCell, Vector3 right, HexCell rightCell)
    {
        //find edge types of neighboring edges
        HexEdgeType leftEdgeType = bottomCell.GetEdgeType(leftCell);
        HexEdgeType rightEdgeType = bottomCell.GetEdgeType(rightCell);

        if (leftEdgeType == HexEdgeType.Slope)
        {
            if (rightEdgeType == HexEdgeType.Slope)
            {
                // Slope, Slope Flat
                TriangulateCornerTerraces(bottom, bottomCell, left, leftCell, right, rightCell);
            }
            else if (rightEdgeType == HexEdgeType.Flat)
            {
                //Slope, Flat, Slope
                TriangulateCornerTerraces(left, leftCell, right, rightCell, bottom, bottomCell);
            }
            else
            {
                TriangulateCornerTerracesCliff(bottom, bottomCell, left, leftCell, right, rightCell);
            }
        }
        else if (rightEdgeType == HexEdgeType.Slope)
        {
            if (leftEdgeType == HexEdgeType.Flat)
            {
                // Flat, Slope, Slope
                TriangulateCornerTerraces(right, rightCell, bottom, bottomCell, left, leftCell);
            }
            else
            {
                TriangulateCornerCliffTerraces(bottom, bottomCell, left, leftCell, right, rightCell);
            }
        }
        else if (leftCell.GetEdgeType(rightCell) == HexEdgeType.Slope)
        {
            if (leftCell.Elevation < rightCell.Elevation)
            {
                // Cliff, Cliff, Slope Right
                TriangulateCornerCliffTerraces(right, rightCell, bottom, bottomCell, left, leftCell);
            }
            else
            {
                // Cliff, Cliff, Slope Left
                TriangulateCornerTerracesCliff(left, leftCell, right, rightCell, bottom, bottomCell);
            }
        }
        else
        {
            // FFF, CCF, CCCR, and CCCL
            terrain.AddTriangle(bottom, left, right);
            terrain.AddTriangleColor(color1, color2, color3);

            Vector3 types;
            types.x = bottomCell.TerrainTypeIndex;
            types.y = leftCell.TerrainTypeIndex;
            types.z = rightCell.TerrainTypeIndex;
            terrain.AddTriangleTerrainTypes(types);
        }
        //features.AddWall(bottom, bottomCell, left, leftCell, right, rightCell);
    }

    private void TriangulateCornerTerracesCliff(Vector3 begin, HexCell beginCell, Vector3 left, HexCell leftCell, Vector3 right, HexCell rightCell)
    {
        float b = 1f / (rightCell.Elevation - beginCell.Elevation);
        if (b < 0) { b = -b; }
        Vector3 boundary = Vector3.Lerp(HexMetric.Perturb(begin), HexMetric.Perturb(right), b);
        Color boundaryColor = Color.Lerp(color2, color3, b);
        Vector3 types;
        types.x = beginCell.TerrainTypeIndex;
        types.y = leftCell.TerrainTypeIndex;
        types.z = rightCell.TerrainTypeIndex;
        //lower connection
        TriangulateBoundaryTriangle(begin, color1, left, color2, boundary, boundaryColor, types);

        //upper terrace connection 
        if (leftCell.GetEdgeType(rightCell) == HexEdgeType.Slope)
        {
            TriangulateBoundaryTriangle(left, color2, right, color3, boundary, boundaryColor, types);
        }
        else //upper cliff connection
        {
            terrain.AddTriangleUnperturbed(HexMetric.Perturb(left), HexMetric.Perturb(right), boundary);
            terrain.AddTriangleColor(color2, color3, boundaryColor);
            terrain.AddTriangleTerrainTypes(types);
        }
    }

    private void TriangulateCornerCliffTerraces(Vector3 begin, HexCell beginCell, Vector3 left, HexCell leftCell, Vector3 right, HexCell rightCell)
    {
        float b = 1f / (leftCell.Elevation - beginCell.Elevation);
        if (b < 0) { b = -b; }
        Vector3 boundary = Vector3.Lerp(HexMetric.Perturb(begin), HexMetric.Perturb(left), b);
        Color boundaryColor = Color.Lerp(color1, color2, b);
        Vector3 types;
        types.x = beginCell.TerrainTypeIndex;
        types.y = leftCell.TerrainTypeIndex;
        types.z = rightCell.TerrainTypeIndex;

        //lower connection
        TriangulateBoundaryTriangle(right, color3, begin, color1, boundary, boundaryColor, types);

        //upper terrace connection 
        if (leftCell.GetEdgeType(rightCell) == HexEdgeType.Slope)
        {
            TriangulateBoundaryTriangle(left, color2, right, color3, boundary, boundaryColor, types);
        }
        else //upper cliff connection
        {
            terrain.AddTriangleUnperturbed(HexMetric.Perturb(left), HexMetric.Perturb(right), boundary);
            terrain.AddTriangleColor(color2, color3, boundaryColor);
            terrain.AddTriangleTerrainTypes(types);
        }
    }

    void TriangulateBoundaryTriangle(Vector3 begin, Color beginColor, Vector3 left, Color leftColor, Vector3 boundary, Color boundaryColor, Vector3 types)
    {
        Vector3 v2 = HexMetric.Perturb(HexMetric.TerraceLerp(begin, left, 1));
        Color c2 = HexMetric.TerraceLerp(beginColor, leftColor, 1);

        // first triangle
        terrain.AddTriangleUnperturbed(HexMetric.Perturb(begin), v2, boundary);
        terrain.AddTriangleColor(beginColor, c2, boundaryColor);
        terrain.AddTriangleTerrainTypes(types);

        //connect terrace with cliff
        for (int i = 2; i < HexMetric.terraceSteps; i++)
        {
            Vector3 v1 = v2;
            Color c1 = c2;
            v2 = HexMetric.Perturb(HexMetric.TerraceLerp(begin, left, i));
            c2 = HexMetric.TerraceLerp(beginColor, leftColor, i);
            terrain.AddTriangleUnperturbed(v1, v2, boundary);
            terrain.AddTriangleColor(c1, c2, boundaryColor);
            terrain.AddTriangleTerrainTypes(types);
        }
        //last triangle
        terrain.AddTriangleUnperturbed(v2, HexMetric.Perturb(left), boundary);
        terrain.AddTriangleColor(c2, leftColor, boundaryColor);
        terrain.AddTriangleTerrainTypes(types);
    }
    void TriangulateEdgeFan(Vector3 center, EdgeVertices edge, float type)
    {
        terrain.AddTriangle(center, edge.v1, edge.v2);
        terrain.AddTriangle(center, edge.v2, edge.v3);
        terrain.AddTriangle(center, edge.v2, edge.v4);
        terrain.AddTriangle(center, edge.v4, edge.v5);

        terrain.AddTriangleColor(color1);
        terrain.AddTriangleColor(color1);
        terrain.AddTriangleColor(color1);
        terrain.AddTriangleColor(color1);

        Vector3 types;
        types.x = types.y = types.z = type;
        terrain.AddTriangleTerrainTypes(types);
        terrain.AddTriangleTerrainTypes(types);
        terrain.AddTriangleTerrainTypes(types);
        terrain.AddTriangleTerrainTypes(types);
    }

    void TriangulateEdgeStrip(EdgeVertices e1, Color c1, float type1, 
                              EdgeVertices e2, Color c2, float type2, bool hasRoad = false)
    {
        terrain.AddQuad(e1.v1, e1.v2, e2.v1, e2.v2);
        terrain.AddQuad(e1.v2, e1.v3, e2.v2, e2.v3);
        terrain.AddQuad(e1.v3, e1.v4, e2.v3, e2.v4);
        terrain.AddQuad(e1.v4, e1.v5, e2.v4, e2.v5);

        terrain.AddQuadColor(c1, c2);
        terrain.AddQuadColor(c1, c2);
        terrain.AddQuadColor(c1, c2);
        terrain.AddQuadColor(c1, c2);

        Vector3 types;
        types.x = types.z = type1;
        types.y = type2;
        terrain.AddQuadTerrainTypes(types);
        terrain.AddQuadTerrainTypes(types);
        terrain.AddQuadTerrainTypes(types);
        terrain.AddQuadTerrainTypes(types);

        if (hasRoad)
        {
            TriangulateRoadSegment(e1.v2, e1.v3, e1.v4, e2.v2, e2.v3, e2.v4);
        }
    }

}