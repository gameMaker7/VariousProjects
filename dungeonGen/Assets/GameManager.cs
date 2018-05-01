using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameManager : MonoBehaviour {

    public DungeonGenerator mazePrefab;
    public Player playerPrefab;
    private Player playerInstance;
    public DungeonGenerator dungeonInstance { get; private set; }

    // Use this for initialization
    private void Start()
    {
        StartCoroutine(BeginGame());
    }

    private IEnumerator BeginGame()
    {
        Camera.main.clearFlags = CameraClearFlags.Skybox;
        Camera.main.rect = new Rect(0f, 0f, 1f, 1f);
        dungeonInstance = Instantiate(mazePrefab) as DungeonGenerator;
        yield return StartCoroutine(dungeonInstance.Generate());
        playerInstance = Instantiate(playerPrefab) as Player;
        playerInstance.SetLocation(dungeonInstance.GetCell(dungeonInstance.RandomCoordinates));
        Camera.main.rect = new Rect(0f, 0f, 0.5f, 0.5f);
        Camera.main.clearFlags = CameraClearFlags.Depth;

    }

    private void RestartGame()
    {
        StopAllCoroutines();
        Destroy(dungeonInstance.gameObject);
        if (playerInstance != null)
        {
            Destroy(playerInstance.gameObject);
        }
        StartCoroutine(BeginGame());
    }

    // Update is called once per frame
    void Update () {
		
        if(Input.GetKeyDown(KeyCode.Space))
        {
            RestartGame();
        }
	}
}
