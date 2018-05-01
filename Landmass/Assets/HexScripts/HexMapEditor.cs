using System;
using UnityEngine;
using UnityEngine.EventSystems;

public class HexMapEditor : MonoBehaviour {

    public HexGrid hexGrid;
    int activeElevation, activeWaterLevel;
    bool applyElevation, applyWaterLevel = true;
    int brushSize;
    bool applyUrbanLevel;
    int activeUrbanLevel;

    //nav
    HexCell previousCell, searchFromCell, searchToCell;


    //dragging
    bool isDrag;
    HexDirection dragDirection;
    
    enum OptionalToggle
    {
        Ignore, Yes, No
    }


    OptionalToggle riverMode, roadMode;
    int activeTerrainTypeIndex;
    public Material terrainMaterial;
    private bool current;
    private bool edit;
    private bool navigate;
    public GameObject console;

    void Awake()
    {
        ConsoleCommands.editor = this;
        current = false;
        edit = false;
        navigate = true;
        NavMode(navigate);
        ShowGrid(current);
        EditMode(edit);
    }

    private void NavMode(bool navigate)
    {
    }

    public void ExecuteCommand(string s)
    {
        ConsoleCommands.ConsoleCommand(s);
    }
    public void SetBrushSize(float size)
    {
        brushSize = (int)size;
    }
    public void SetElevation(float elevation)
    {
        activeElevation = (int)elevation;
    }
    public void SetRiverMode(int mode)
    {
        riverMode = (OptionalToggle)mode;
    }
    public void SetRoadMode(int mode)
    {
        roadMode = (OptionalToggle)mode;
    }
    public void SetApplyWaterLevel(bool toggle)
    {
        applyWaterLevel = toggle;
    }
    public void SetWaterLevel(float level)
    {
        activeWaterLevel = (int)level;
    }
    public void SetApplyUrbanLevel(bool toggle)
    {
        applyUrbanLevel = toggle;
    }
    public void SetUrbanLevel(float level)
    {
        activeUrbanLevel = (int)level;
    }
    void Update()
    {

        if (Input.GetKeyDown(KeyCode.G)) //Grid
        {
            current = !current;
            ShowGrid(current);
        }
        if (Input.GetKeyDown(KeyCode.Alpha2)) // Navigate
        {

        }
        if (Input.GetKeyDown(KeyCode.Alpha1)) // Edit
        {
            edit = !edit;
            EditMode(edit);
        }


        if (Input.GetMouseButton(0) && !EventSystem.current.IsPointerOverGameObject())
        {
            HandleInput();
        }
        else
        {
            previousCell = null;
        }
    }

    private void EditMode(bool edit)
    {
        console.SetActive(edit);
    }

    public void ShowGrid(bool visible)
    {
        if (visible)
        {
            terrainMaterial.EnableKeyword("GRID_ON");
        }
        else
        {
            terrainMaterial.DisableKeyword("GRID_ON");
        }
    }
    void HandleInput()
    {


        Ray inputRay = Camera.main.ScreenPointToRay(Input.mousePosition);
        RaycastHit hit;
        if (Physics.Raycast(inputRay, out hit))
        {
            HexCell currentCell = hexGrid.GetCell(hit.point);
            if (previousCell && previousCell != currentCell)
            {
                ValidateDrag(currentCell);
            }
            else
            {
                isDrag = false;
            }
            if (edit)
            {
                EditCells(currentCell);
            }
            else if (Input.GetKey(KeyCode.LeftShift))
            {
                if (searchFromCell)
                {
                    searchFromCell.DisableHighlight();
                }
                searchFromCell = currentCell;
                searchFromCell.EnableHighlight(Color.blue);
                if (searchToCell)
                {
                    hexGrid.FindPath(searchFromCell, searchToCell);
                }
            }
            else if (searchFromCell && searchFromCell != currentCell)
            {
                searchToCell = currentCell;
                hexGrid.FindPath(searchFromCell, searchToCell);
            }
        }
    }
    void ValidateDrag(HexCell currentCell)
    {
        for (dragDirection = HexDirection.NE; dragDirection <= HexDirection.NW; dragDirection++)
        {
            if (previousCell.GetNeighbor(dragDirection) == currentCell)
            {
                isDrag = true;
                return;
            }
        }
        isDrag = false;
    }
    public void SetApplyElevation(bool toggle)
    {
        applyElevation = toggle;
    }
    void EditCells(HexCell center)
    {
        int centerX = center.coordinates.X;
        int centerZ = center.coordinates.Z;
        for (int r = 0, z = centerZ - brushSize; z <= centerZ; z++, r++)
        {
            for (int x = centerX - r; x <= centerX + brushSize; x++)
            {
                EditCell(hexGrid.GetCell(new HexCoordinates(x, z)));
            }
        }
        for (int r = 0, z = centerZ + brushSize; z > centerZ; z--, r++)
        {
            for (int x = centerX - brushSize; x <= centerX + r; x++)
            {
                EditCell(hexGrid.GetCell(new HexCoordinates(x, z)));
            }
        }
    }
    void EditCell(HexCell cell)
    {
        if (cell)
        {
            if (activeTerrainTypeIndex >= 0)
            {
                cell.TerrainTypeIndex = activeTerrainTypeIndex;
            }
            if (applyElevation)
            {
                cell.Elevation = activeElevation;
            }
            if (applyUrbanLevel)
            {
                cell.UrbanLevel = activeUrbanLevel;
            }
            if (applyWaterLevel)
            {
                cell.WaterLevel = activeWaterLevel;
            }
            if (riverMode == OptionalToggle.No)
            {
                cell.RemoveRiver();
            }
            if (roadMode == OptionalToggle.No)
            {
                cell.RemoveRoads();
            }
            if (isDrag)
            {
                HexCell otherCell = cell.GetNeighbor(dragDirection.Opposite());
                if (otherCell)
                {
                    if (riverMode == OptionalToggle.Yes)
                    {
                        otherCell.SetOutgoingRiver(dragDirection);
                    }
                    if (roadMode == OptionalToggle.Yes)
                    {
                        otherCell.AddRoad(dragDirection);
                    }
                }
            }
        }
    }

    internal void SetTerrainTypeIndex(int index)
    {
        activeTerrainTypeIndex = index;
    }
}


public class ConsoleCommands
{
   static char[] split = { ' ', ':', ',', '.' };
    public static HexMapEditor editor;
    // console commands are expected to be in 2 word phrases (the effect needed) (parameter of effect)
    public static void ConsoleCommand(string command)
    {
        string[] words = command.Split(split);
        switch (words[0])
        {
            case "rivers": RiverCommands(words[1]);
                break;
            case "elevation": ElevationCommands(words[1]);
                break;
            case "roads": RoadCommands(words[1]);
                break;
            case "terrain": TerrainCommands(words[1]);
                break;
            case "brushSize": BrushCommands(words[1]);
                break;
            case "water": WaterCommands(words[1]);
                break;
            case "density": DensityCommands(words[1]);
                break;
            default: Debug.Log("Console Command Failure: " + command);
                break;
        }
    }

    private static void DensityCommands(string command)
    {
        int val = 0;
        if (command.Equals("Active"))
        {
            editor.SetApplyUrbanLevel(true);
            return;
        }
        if (command.Equals("Inactive"))
        {
            editor.SetApplyUrbanLevel(false);
            return;
        }
        if (int.TryParse(command, out val))
        {
            editor.SetUrbanLevel(val);
        }
        else
        {
            Debug.Log("Console Command Failure");
        }
    }

    private static void WaterCommands(string command)
    {
        if (command.Equals("Active"))
        {
            editor.SetApplyWaterLevel(true);
        }
        if (command.Equals("Inactive"))
        {
            editor.SetApplyWaterLevel(false);
        }
        int val = 0;
        if (Int32.TryParse(command, out val))
        {
            editor.SetWaterLevel(val);
        }
        else
        {
            Debug.Log("Console Command Failure");
        }
    }

    private static void TerrainCommands(string command)
    {
        int val = 0;
        if (int.TryParse(command, out val))
        {
            editor.SetTerrainTypeIndex(val);
        }
        else
        {
            Debug.Log("Console Command Failure Terrain");
        }
    }

    private static void BrushCommands(string command)
    {
        int val = 0;
        if(int.TryParse(command, out val)){
            editor.SetBrushSize(val); }
        else
        {
            Debug.Log("Console Command Failure");
        }
    }
    private static void RoadCommands(string command)
    {
        switch (command)
        {
            case "Active":
                editor.SetRoadMode(1);
                break;
            case "Inactive":
                editor.SetRoadMode(2);
                break;
            case "Ignore":
                editor.SetRoadMode(0);
                break;
            default: Debug.Log("Console Command Failure");
                break;
        }
    }

    private static void ElevationCommands(string command)
    {
        if (command.Equals("Active"))
        {
            editor.SetApplyElevation(true);
        }
        if (command.Equals("Inactive"))
        {
            editor.SetApplyElevation(false);
        }
        int val = 0;
        if(Int32.TryParse(command, out val))
        {
            editor.SetElevation(val);
        }
        else
        {
            Debug.Log("Console Command Failure");
        }
    }

    private static void RiverCommands(string command)
    {
        switch (command)
        {
            case "Active": editor.SetRiverMode(1);
                break;
            case "Inactive": editor.SetRiverMode(2);
                break;
            case "Ignore": editor.SetRiverMode(0);
                break;
            default: Debug.Log("Console Command Failure");
                break;
        }
    }
}