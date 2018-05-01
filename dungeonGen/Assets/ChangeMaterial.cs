using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ChangeMaterial : MonoBehaviour {

    [SerializeField]
    Material[] materialArray;

	// Use this for initialization
	void Start () {
        Material newMat = materialArray[Random.Range(0, materialArray.Length)];
        gameObject.GetComponent<MeshRenderer>().material = newMat;
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
