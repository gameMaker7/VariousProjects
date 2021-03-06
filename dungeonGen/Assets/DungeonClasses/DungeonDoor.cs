﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DungeonDoor : DungeonPassage {

    public Transform hinge;
    private static Quaternion
    normalRotation = Quaternion.Euler(0f, -90f, 0f),
    mirroredRotation = Quaternion.Euler(0f, 90f, 0f);

    private bool isMirrored;
    private DungeonDoor OtherSideOfDoor
    {
        get
        {
            return otherCell.GetEdge(direction.GetOpposite()) as DungeonDoor;
        }
    }
    public override void OnPlayerEntered()
    {
        OtherSideOfDoor.hinge.localRotation = hinge.localRotation = isMirrored? mirroredRotation : normalRotation;
        OtherSideOfDoor.cell.room.Show();
    }

    public override void OnPlayerExited()
    {
        OtherSideOfDoor.hinge.localRotation = hinge.localRotation = Quaternion.identity;
        OtherSideOfDoor.cell.room.Hide();
    }
    public override void Initialize(Cell primary, Cell other, Direction direction)
    {
        base.Initialize(primary, other, direction);
        if (OtherSideOfDoor != null)
        {
            isMirrored = true;
            hinge.localScale = new Vector3(-1f, 1f, 1f);
            Vector3 p = hinge.localPosition;
            p.x = -p.x;
            hinge.localPosition = p;
        }
        for (int i = 0; i < transform.childCount; i++)
        {
            Transform child = transform.GetChild(i);
            if (child != hinge)
            {
                child.GetComponent<Renderer>().material = cell.room.settings.wallMaterial;
            }
        }
    }
}
