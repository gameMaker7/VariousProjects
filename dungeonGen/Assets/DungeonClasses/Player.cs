using UnityEngine;
using UnityEngine.UI;

public class Player : MonoBehaviour
{

    private Cell currentCell;
    private Direction currentDirection;
    public float money = 20.00f;
    [SerializeField]UnityEngine.UI.Text text;

    private void Start()
    {
    }

    private void Move(Direction direction)
    {
        CellEdge edge = currentCell.GetEdge(direction);
        if (edge is DungeonPassage)
        {
            SetLocation(edge.otherCell);
        }
    }

    public void LoseMoney(float cost)
    {
        money -= cost;
        if(money <= 0.0f)
        {
            UnityEngine.SceneManagement.SceneManager.LoadScene(UnityEngine.SceneManagement.SceneManager.GetActiveScene().name);
        }
        if(text == null)
        {
            text = GameObject.Find("Text").GetComponent<Text>();
        }
        text.text = "Money: $" + money;
    }

    private void Rotate(Direction direction)
    {
        transform.localRotation = direction.ToRotation();
        currentDirection = direction;
    }
    public void SetLocation(Cell cell)
    {
        if (currentCell != null)
        {
            currentCell.OnPlayerExited();
        }
        currentCell = cell;
        transform.localPosition = cell.transform.localPosition;
        currentCell.OnPlayerEntered();
    }
    private void Update()
    {
        if (Input.GetKeyDown(KeyCode.W) || Input.GetKeyDown(KeyCode.UpArrow))
        {
            Move(currentDirection);
        }
        else if (Input.GetKeyDown(KeyCode.D) || Input.GetKeyDown(KeyCode.RightArrow))
        {
            Move(currentDirection.GetNextClockwise());
        }
        else if (Input.GetKeyDown(KeyCode.S) || Input.GetKeyDown(KeyCode.DownArrow))
        {
            Move(currentDirection.GetOpposite());
        }
        else if (Input.GetKeyDown(KeyCode.A) || Input.GetKeyDown(KeyCode.LeftArrow))
        {
            Move(currentDirection.GetNextCounterclockwise());
        }
        else if (Input.GetKeyDown(KeyCode.Q))
        {
            Rotate(currentDirection.GetNextCounterclockwise());
        }
        else if (Input.GetKeyDown(KeyCode.E))
        {
            Rotate(currentDirection.GetNextClockwise());
        }
    }
}