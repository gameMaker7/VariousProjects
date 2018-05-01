using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class DungeonGenerator : MonoBehaviour {

    enum genType
    {
        LAST, FIRST, RANDOM
    }

    public int seed = 0;
    [SerializeField]
    genType generationStyle = genType.LAST;
    [SerializeField]
    IntVector2 gridSize = new IntVector2(10, 10);
    [SerializeField]
    Cell cellPrefab;
    [SerializeField]
    float generationStepDelay;

    [SerializeField]
    DungeonPassage passagePrefab;
    [SerializeField]
    DungeonWall[] wallPrefabs;
    [SerializeField]
    DungeonDoor doorPrefab;
    [SerializeField]
    RoomSettings[] roomSettings;
    private List<DungeonRoom> rooms = new List<DungeonRoom>();

    [Range(0f, 1f)]
    public float doorProbability;

    Cell[,] cells;
     
    public IntVector2 RandomCoordinates
    {
        get
        {
            return new IntVector2(Random.Range(0, gridSize.x), Random.Range(0, gridSize.z));
        }
    }

    public bool ContainsCoordinates(IntVector2 coordinate)
    {
        return coordinate.x >= 0 && coordinate.x < gridSize.x && coordinate.z >= 0 && coordinate.z < gridSize.z;
    }

    public Cell GetCell(IntVector2 coordinates)
    {
        return cells[coordinates.x, coordinates.z];
    }
    private void DoFirstGenerationStep(List<Cell> activeCells)
    {
        Cell newCell = CreateCell(RandomCoordinates);
        newCell.Initialize(CreateRoom(-1));
        activeCells.Add(newCell);
    }

    public IEnumerator Generate()
    {
        WaitForSeconds delay = new WaitForSeconds(generationStepDelay);
        cells = new Cell[gridSize.x, gridSize.z];
        List<Cell> activeCells = new List<Cell>();
        DoFirstGenerationStep(activeCells);

        while (activeCells.Count > 0)
        {
            yield return delay;
            DoNextGenerationStep(activeCells);
        }
        for (int i = 0; i < rooms.Count; i++)
        {
            rooms[i].Hide();
        }
    }

    private void DoNextGenerationStep(List<Cell> activeCells)
    {
        int currentIndex = 0;
        switch (generationStyle)
        {
            case genType.FIRST:
                currentIndex = 0;
                break;
            case genType.LAST:
                currentIndex = activeCells.Count - 1;
                break;
            case genType.RANDOM:
                currentIndex = Random.Range(0, activeCells.Count - 1);
                break;
            default:
                break;
        }
        Cell currentCell = activeCells[currentIndex];
        if (currentCell.IsFullyInitialized)
        {
            activeCells.RemoveAt(currentIndex);
            return;
        }
        Direction direction = currentCell.RandomUninitializedDirection;
        IntVector2 coordinates = currentCell.coordinates + direction.ToIntVector2();

        if (ContainsCoordinates(coordinates))
        {
            Cell neighbor = GetCell(coordinates);
            if(neighbor == null)
            {
                neighbor = CreateCell(coordinates);
                CreatePassage(currentCell, neighbor, direction);
                activeCells.Add(neighbor);
            }
            else if (currentCell.room.settingsIndex == neighbor.room.settingsIndex)
            {
                    CreatePassageInSameRoom(currentCell, neighbor, direction);
            }
            else
            {
                CreateWall(currentCell, neighbor, direction);
            }
        }
        else
        {
            CreateWall(currentCell, null, direction);
        }
    }

    private DungeonRoom CreateRoom(int indexToExclude)
    {
        DungeonRoom newRoom = ScriptableObject.CreateInstance<DungeonRoom>();
        newRoom.settingsIndex = Random.Range(0, roomSettings.Length);
        if (newRoom.settingsIndex == indexToExclude)
        {
            newRoom.settingsIndex = (newRoom.settingsIndex + 1) % roomSettings.Length;
        }
        newRoom.settings = roomSettings[newRoom.settingsIndex];
        rooms.Add(newRoom);
        return newRoom;
    }
    private void CreatePassageInSameRoom(Cell cell, Cell otherCell, Direction direction)
    {
        DungeonPassage passage = Instantiate(passagePrefab) as DungeonPassage;
        passage.Initialize(cell, otherCell, direction);
        passage = Instantiate(passagePrefab) as DungeonPassage;
        passage.Initialize(otherCell, cell, direction.GetOpposite());
        if (cell.room != otherCell.room)
        {
            DungeonRoom roomToAssimilate = otherCell.room;
            cell.room.Assimilate(roomToAssimilate);
            rooms.Remove(roomToAssimilate);
            Destroy(roomToAssimilate);
        }
    }

    private void CreatePassage(Cell currentCell, Cell neighbor, Direction direction)
    {
        DungeonPassage prefab = Random.value < doorProbability ? doorPrefab : passagePrefab;
        DungeonPassage passage = Instantiate(prefab) as DungeonPassage;
        passage.Initialize(currentCell, neighbor, direction);
        passage = Instantiate(prefab) as DungeonPassage;
        if (passage is DungeonDoor)
        {
            neighbor.Initialize(CreateRoom(currentCell.room.settingsIndex));
        }
        else
        {
            neighbor.Initialize(currentCell.room);
        }
        passage.Initialize(neighbor, currentCell, direction.GetOpposite());
    }

    private void CreateWall(Cell currentCell, Cell neighbor, Direction direction)
    {
        //current Cell
        DungeonWall wall = Instantiate(wallPrefabs[Random.Range(0, wallPrefabs.Length-1)]) as DungeonWall;
        wall.Initialize(currentCell, neighbor, direction);

        if (neighbor != null)
        {
            //Neighbor
            wall = Instantiate(wallPrefabs[Random.Range(0, wallPrefabs.Length - 1)]) as DungeonWall;
            wall.Initialize(neighbor, currentCell, direction.GetOpposite());
        }
    }

    private Cell CreateCell(IntVector2 coordinates)
    {
        Cell newCell = Instantiate(cellPrefab) as Cell;
        cells[coordinates.x, coordinates.z] = newCell;
        newCell.coordinates = coordinates;
        newCell.name = "Maze Cell " + coordinates.x + ", " + coordinates.z;
        newCell.transform.parent = transform;
        newCell.transform.localPosition = new Vector3(coordinates.x - gridSize.x * 0.5f + 0.5f, 0f, coordinates.z - gridSize.z * 0.5f + 0.5f);
        return newCell;
    }

    // Use this for initialization
    void Start () {
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
