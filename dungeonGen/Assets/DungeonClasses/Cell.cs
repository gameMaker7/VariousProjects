using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Cell : MonoBehaviour {


    public IntVector2 coordinates;
    private CellEdge[] edges = new CellEdge[Directions.Count];
    private int initializedEdgeCount;

    public DungeonRoom room;
    public void Initialize(DungeonRoom room)
    {
        room.Add(this);
        transform.GetChild(0).GetComponent<Renderer>().material = room.settings.floorMaterial;
    }
    public CellEdge GetEdge(Direction direction)
    {
        return edges[(int)direction];
    }
    public void Show()
    {
        gameObject.SetActive(true);
    }

    public void Hide()
    {
        gameObject.SetActive(false);
    }
    public bool IsFullyInitialized
    {
        get
        {
            return initializedEdgeCount == Directions.Count;
        }
    }

    public void OnPlayerExited()
    {
        room.Hide();
        for (int i = 0; i < edges.Length; i++)
        {
            edges[i].OnPlayerExited();
        }
    }

    public void OnPlayerEntered()
    {
        room.Show();
        for (int i = 0; i < edges.Length; i++)
        {
            edges[i].OnPlayerEntered();
        }
    }

    public void SetEdge(Direction direction, CellEdge edge)
    {
        edges[(int)direction] = edge;
        initializedEdgeCount += 1;
    }

    public Direction RandomUninitializedDirection
    {
        get
        {
            int skips = UnityEngine.Random.Range(0, Directions.Count - initializedEdgeCount);
            for (int i = 0; i < Directions.Count; i++)
            {
                if (edges[i] == null)
                {
                    if (skips == 0)
                    {
                        return (Direction)i;
                    }
                    skips -= 1;
                }
            }
            throw new System.InvalidOperationException("Cell has no uninitialized directions left.");
        }
    }
}
