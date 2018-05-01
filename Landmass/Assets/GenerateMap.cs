using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class GenerateMap : MonoBehaviour {

    public List<InputField> inputs;
    int x;
    int y;

    TerrainCollection terrains;
	// Use this for initialization
	void Start () {
        terrains = GetComponent<TerrainCollection>();
        terrains.Setup();

	}

    public void Generate()
    {

        ParseSize(GameObject.Find("Map Sizes").GetComponent<Dropdown>().captionText);
        ParseType(GameObject.Find("Land Type").GetComponent<Dropdown>().captionText);
        GameObject.Find("Generation UI").SetActive(false);
        GameObject gen = GameObject.Find("GenerationScript");
            gen.GetComponent<HexGrid>().Refresh(x, y);
        gen.GetComponent<HexGrid>().OceanLevels();
        gen.GetComponent<HexGrid>().CreateBiomes();
        //GameObject.Find("Generation UI").SetActive(true);
    }

    private void SetRoadsAndCities(GameObject roads, GameObject cities)
    {
        GameObject gen = GameObject.Find("GenerationScript");
        HexGrid grid = gen.GetComponent<HexGrid>();
        grid.ActiveRoads = roads.GetComponent<Toggle>().isOn;
        grid.ActiveCities = cities.GetComponent<Toggle>().isOn;
    }

    private void ParseType(Text captionText)
    {
        GameObject gen = GameObject.Find("GenerationScript");
        HexGrid grid = gen.GetComponent<HexGrid>();

        switch (captionText.text)
        {
            case "Normal":
                grid.mountainModifier = 4.0f;
                grid.oceanWaterChance = 0.5f;
                grid.lakeWaterChance = 0.05f;
                break;
            case "Mountainous":
                grid.mountainModifier = 2.0f;
                grid.oceanWaterChance = 0.2f;
                grid.lakeWaterChance = 1.0f;
                break;
            case "Islands":
                grid.mountainModifier = 4.0f;
                grid.oceanWaterChance = 0.9f;
                grid.lakeWaterChance = 0.05f;
                break;
        }
    }

    private void ParseSize(Text captionText)
    {
        string[] s = captionText.text.Split('x');
        x = int.Parse(s[0]);
        y = int.Parse(s[1]);
    }

    // Update is called once per frame
    void Update () {
		
	}
}
