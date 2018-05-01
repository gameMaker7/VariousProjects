using UnityEngine;
using System.Collections.Generic;

public class DungeonRoom : ScriptableObject
{

    public int settingsIndex;

    public RoomSettings settings;

    private List<Cell> cells = new List<Cell>();
    public void Assimilate(DungeonRoom room)
    {
        for (int i = 0; i < room.cells.Count; i++)
        {
            Add(room.cells[i]);
        }
    }
    public void Add(Cell cell)
    {
        cell.room = this;
        cells.Add(cell);
    }
    public void Hide()
    {
        for (int i = 0; i < cells.Count; i++)
        {
            cells[i].Hide();
        }
    }

    public void Show()
    {
        for (int i = 0; i < cells.Count; i++)
        {
            cells[i].Show();
        }
    }
}