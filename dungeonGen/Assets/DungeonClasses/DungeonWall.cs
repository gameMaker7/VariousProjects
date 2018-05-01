using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DungeonWall : CellEdge {

    public Transform wall;

    public override void Initialize(Cell cell, Cell otherCell, Direction direction)
    {
        base.Initialize(cell, otherCell, direction);
        wall.GetComponent<Renderer>().material = cell.room.settings.wallMaterial;
    }
}
