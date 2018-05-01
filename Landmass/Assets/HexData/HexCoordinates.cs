using System;
using UnityEngine;


[System.Serializable]
public struct HexCoordinates{
    [SerializeField]
    private int x, z;

    public int X
    {
        get
        {
            return x;
        }
    }

    public int Z
    {
        get
        {
            return z;
        }
    }

    public int Y
    {
        get
        {
            return -X - Z;
        }
    }

    public HexCoordinates(int x, int z)
    {
        this.x = x;
        this.z = z;
    }
    public static HexCoordinates FromOffsetCoordinates(int x, int z)
    {
        return new HexCoordinates(x - z / 2, z);
    }

    public override string ToString()
    {
        return "(" +
            X.ToString() + ", " + Y.ToString() + ", " + Z.ToString() + ")";
    }

    public string ToStringOnSeparateLines()
    {
        return X.ToString() + "\n" + Y.ToString() + "\n" + Z.ToString();
    }

    internal static HexCoordinates FromPosition(Vector3 position)
    {
        // value if z == 0
        float x = position.x / (HexMetric.innerRadius * 2f);
        float y = -x;

        //actual value
        float offset = position.z / (HexMetric.outerRadius * 3f);
        x -= offset;
        y -= offset;

        // round to hex coords and derive z
        int iX = Mathf.RoundToInt(x);
        int iY = Mathf.RoundToInt(y);
        int iZ = Mathf.RoundToInt(-x - y);

        // check for rounding errors
        if(iX + iY + iZ != 0)
        {
            // find coordinate with the most rounding error and reconstruct it
            float dx = Mathf.Abs(x - iX);
            float dy = Mathf.Abs(y - iY);
            float dz = Mathf.Abs(-x - y - iZ);
            if(dx > dy && dx > dz)
            {
                iX = -iY - iZ;
            }else if(dz > dy)
            {
                iZ = -iX - iY;
            }
        }
        return new HexCoordinates(iX, iZ);
    }

    internal int DistanceTo(HexCoordinates other)
    {
        return ((x < other.x ? other.x - x : x - other.x) +
               (Y < other.Y ? other.Y - Y : Y - other.Y) +
               (z < other.z ? other.z - z : z - other.z)) / 2;
    }
}
